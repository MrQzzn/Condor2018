package org.usfirst.frc.team5895.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class Arm {
	private TalonSRX armMotor;
	private double armSpeed;
	private enum Position {BACK, UP, DOWN};
	private Position pos = Position.DOWN;
	int whateverPort;
	Encoder encoder;
		
	public Arm () {
		armMotor = new TalonSRX(whateverPort);
		armSpeed = 0.0;
		armMotor.config_kF(0, 0.33, 10);
		armMotor.config_kP(0, 0.5, 10);
		armMotor.config_kI(0, 0, 10);
		armMotor.config_kD(0, 0, 10);
	
	}
	
	public void run(int armMode) {
	
		int downSpeed = 0;
		
		
		switch (armMode) {
			case 0:
				armMotor.set(ControlMode.PercentOutput, 0.5);
				break;
			case 1:
				armMotor.set(ControlMode.PercentOutput, 0.75);
				break;
			case 2:
				armMotor.set(ControlMode.PercentOutput, 0.25);
				break;
			default:
				DriverStation.reportError("nothing",  false);
				break;
			
		}
	}
}