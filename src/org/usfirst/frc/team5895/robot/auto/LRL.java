package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;
/**
 * Left side of field, right switch, & left scale.
 * @author lalewis-19
 */
public class LRL {
	
	public static final void run(Drivetrain drive, Intake intake) {
		
		// switch > far
		// scale > near
		
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		drive.autoLeftLeftScale();
		Waiter.waitFor(1000);
		intake.up();
		Waiter.waitFor(drive::isPFinished, 2500);
		intake.ejectFast();
		Waiter.waitFor(500);
		drive.turnTo(120);
		Waiter.waitFor(drive::atAngle,3000);
		drive.stopTurning();
		drive.arcadeDrive(0, 0);
		drive.autoLeftLeftScaleRightSwitch();
		intake.down();
		intake.intake();
		Waiter.waitFor(drive::isPFinished, 3000);
		drive.arcadeDrive(0, 0);
		Waiter.waitFor(1000);
		drive.arcadeDrive(-0.1, 0);
		Waiter.waitFor(200);
		drive.arcadeDrive(0.1, 0);
		Waiter.waitFor(200);
		intake.ejectFast();
	}

}
