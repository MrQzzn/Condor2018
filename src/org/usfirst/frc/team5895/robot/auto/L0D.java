package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;

import edu.wpi.first.wpilibj.DriverStation;

public class L0D {

	public static final void run(Drivetrain drive, Intake intake) {
		
		DriverStation.reportError("boop", false);
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		drive.autoLeftRightDrive();
		Waiter.waitFor(3000);
		Waiter.waitFor(drive::isPFinished, 7000);
	}
	
}
