package org.usfirst.frc.team5895.robot;

import java.util.HashMap;

import org.usfirst.frc.team5895.robot.auto.*;
import org.usfirst.frc.team5895.robot.framework.Looper;
import org.usfirst.frc.team5895.robot.framework.Recorder;
import org.usfirst.frc.team5895.robot.lib.BetterJoystick;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Looper loop;
	Arm arm;
	Intake intake;
	Drivetrain drive;
	GameData gameData;
	PowerDistributionPanel pdp;
	Recorder r;
	HashMap<String, Runnable> autoRoutines;
	
	BetterJoystick leftJoystick;
	BetterJoystick rightJoystick;

	public void robotInit() {
		
		leftJoystick = new BetterJoystick(0);
		rightJoystick = new BetterJoystick(1);

		intake = new Intake();
		arm = new Arm();
		drive = new Drivetrain();
		gameData = new GameData();
		pdp = new PowerDistributionPanel();
		
		//set up recorder
		r = new Recorder(10);
		r.add("Time", Timer::getFPGATimestamp);
		r.add("Drive Distance", drive::getDistanceTraveled);
		r.add("Drive Velocity", drive::getVelocity);
/*		r.add("Drive Left Velocity", drive::getLeftVelocity);
		r.add("Drive Right Velocity", drive::getRightVelocity);
		r.add("Intake LeftClawSensor", intake::getLeftVoltage);
		r.add("Intake RightClawSensor", intake::getRightVoltage);
		r.add("Intake State", intake::getState);
		for (int i = 0; i < 16; i++) {
			final int x = i;
			r.add("Current " + i, () -> pdp.getCurrent(x));
		}
		r.add("Auto Routine", gameData::getAutoRoutine);
		r.add("Game Data", gameData::getGameData);
*/		
		loop = new Looper(10);
		loop.add(intake::update);
		loop.add(arm::update);
		loop.add(drive::update);
//		loop.add(r::record);
		loop.start();
		
		//set up auto map
		autoRoutines = new HashMap<String, Runnable>();
		autoRoutines.put("S00", () -> S00.run(drive, intake));
		autoRoutines.put("CR0", () -> CR0.run(drive, intake));
		autoRoutines.put("CL0", () -> CL0.run(drive, intake));
		
		autoRoutines.put("L0R", () -> L0R.run(drive, intake));
		autoRoutines.put("L0L", () -> L0L.run(drive, intake));
		autoRoutines.put("R0R", () -> R0R.run(drive, intake));
		autoRoutines.put("R0L", () -> R0L.run(drive, intake));
		
		autoRoutines.put("LLL", () -> LLL.run(drive, intake));
		autoRoutines.put("LRL", () -> LLL.run(drive, intake));
		autoRoutines.put("LRR", () -> L0D.run(drive, intake));
		autoRoutines.put("LLR", () -> L0D.run(drive, intake));
		
		autoRoutines.put("RRR", () -> RRR.run(drive, intake));
		autoRoutines.put("RLL", () -> R0L.run(drive, intake));
		autoRoutines.put("RLR", () -> RRR.run(drive, intake));
		autoRoutines.put("RRL", () -> R0L.run(drive, intake));
		
	}

	public void autonomousInit() {
		
//		r.startRecording();
//		drive.resetNavX();
//		drive.autoForwardStraight();
		String autoRoutine = gameData.getAutoRoutine();
		
		DriverStation.reportError("" + gameData.getAutoRoutine(), false);
		
		if (autoRoutines.containsKey(autoRoutine)) {
			autoRoutines.get(autoRoutine).run();
		} else {
			DriverStation.reportError("Auto Error: " + autoRoutine, false);
			S00.run(drive, intake);
		}	
	}

	public void teleopPeriodic() {

		DriverStation.reportError("" + intake.getLeftVoltage(), false);
		
		//teleop drive
		drive.arcadeDrive(leftJoystick.getRawAxis(1), rightJoystick.getRawAxis(0));
		
		//left joystick controls
		if(leftJoystick.getRisingEdge(1)) {
			if((arm.getAngle() < 45.0 && arm.getAngle() > 0.0) || (arm.getAngle() < 180.0 && arm.getAngle() > 135.0)) {
				intake.openIntaking();
			}
			else {
				intake.drop();
			}	
		} else if(leftJoystick.getFallingEdge(2)) {
			intake.intake();
		}
		else if(leftJoystick.getRisingEdge(3)) {
			arm.setTargetAngle(0.0);
		}
		else if(leftJoystick.getRisingEdge(4)) {
			arm.setTargetAngle(180.0);
		}
		
		//right joystick controls
		if(rightJoystick.getRisingEdge(1)) {
				intake.eject();		
		} else if(rightJoystick.getRisingEdge(2)) {
			arm.setTargetAngle(45.0);
		} else if(rightJoystick.getRisingEdge(3)) {
			arm.setTargetAngle(90.0);
		} else if(rightJoystick.getRisingEdge(4)) {
			arm.setTargetAngle(135.0);
		}
	}

	public void disabledInit() {
//		r.stopRecording();
		drive.arcadeDrive(0, 0);
	}

}
