package org.usfirst.frc.team5895.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

public class Arm {
	private enum Mode_Type {MOVING, DISENGAGING, PERCENT, DISABLED};
	private Mode_Type mode = Mode_Type.DISABLED;
	private TalonSRX armMotor;
	private AnalogPotentiometer armAngleSensor;
	
	int talonPort;
	int sensorPort;

	private double targetPos;
	private double brakeTimestamp;
	private double percentSetting;
	
	Encoder encoder;
	
	public static final int kSlotIdx = 0;
	public static final int kPIDLoopIdx = 0;
	public static final int kTimeoutMs = 10;
		
	public Arm () {
		armMotor = new TalonSRX(talonPort);
		armAngleSensor = new AnalogPotentiometer(sensorPort, 360, 30); //port, degrees, initial value of installation
	
		/* set sensor */
		armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPIDLoopIdx, kTimeoutMs);
		armMotor.setSensorPhase(false);
		armMotor.setInverted(false);
		
		armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMs);
		armMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMs);

		armMotor.configNominalOutputForward(0, kTimeoutMs);
		armMotor.configNominalOutputReverse(0, kTimeoutMs);
		armMotor.configPeakOutputForward(1, kTimeoutMs);
		armMotor.configPeakOutputReverse(-1, kTimeoutMs);
	
		/* Set current limiting */
		armMotor.configContinuousCurrentLimit(30, 0);
		armMotor.configPeakCurrentLimit(35, 0);
		armMotor.configPeakCurrentDuration(100, 0);
		armMotor.enableCurrentLimit(true);
		
		/* set closed loop gains in slot0 - see documentation */
		armMotor.selectProfileSlot(kSlotIdx, kPIDLoopIdx);
		armMotor.config_kF(0, 0.33, kTimeoutMs);
		armMotor.config_kP(0, 0.5, kTimeoutMs);
		armMotor.config_kI(0, 0, kTimeoutMs);
		armMotor.config_kD(0, 0, kTimeoutMs);
		
		/* set acceleration and vcruise velocity - see documentation */
		armMotor.configMotionCruiseVelocity(3900, kTimeoutMs);
		armMotor.configMotionAcceleration(7000, kTimeoutMs);
	}
	
	public double getAngle() {
		return armAngleSensor.get();
	}
	
	public void setTargetAngle(double targetAngle) {
		
		targetPos = targetAngle;
		
		if(targetPos > 180.0) {
			targetPos = 180.0;
		}
		else if(targetPos < 0.0) {
			targetPos = 0.0;
		}
		
		brakeTimestamp = Timer.getFPGATimestamp();
		mode = Mode_Type.DISENGAGING;
	}
	
	public boolean atTarget() {
		return ((Math.abs(armMotor.getSelectedSensorPosition(0) - targetPos) < 200.0) 
				&& (Math.abs(armMotor.getSelectedSensorVelocity(0)) < 1.0));
	}
	
	public double getSpeed() {
		return armMotor.getSelectedSensorVelocity(0);
	}
	
	public void percentDrive(double speed) {
		percentSetting = speed;
		mode = Mode_Type.PERCENT;
	}
	
	public void disable() {
		mode = Mode_Type.DISABLED;
	}
	
	public void update() {
	
		switch (mode) {
		
		
		case DISENGAGING:
			armMotor.set(ControlMode.PercentOutput, 0);
			
			if(Timer.getFPGATimestamp() - brakeTimestamp > 0.1) {
				mode = Mode_Type.MOVING;
			}
			
			break;
		
		case MOVING:
			armMotor.set(ControlMode.MotionMagic, targetPos); 
			
			if(atTarget()) {
				mode = Mode_Type.DISABLED;
			}
			
			break;
			
		case PERCENT:
			armMotor.set(ControlMode.PercentOutput, percentSetting);
			
			break;
			
		case DISABLED:
			armMotor.set(ControlMode.PercentOutput, 0);
			break;
			
		}
	}
}