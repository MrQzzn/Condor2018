package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;
/**
 * Left side of field, right switch, & right scale.
 * @author lalewis-19
 */
public class LRR {
	
	public static final void run(Drivetrain drive, Intake intake) {
		
		// switch > far
		// scale > far
		
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		drive.autoLeftRightScale();
		Waiter.waitFor(3000);
		Waiter.waitFor(drive::isPFinished, 4000);
		intake.ejectSlow();
		Waiter.waitFor(500);
		drive.turnTo(-165);
		Waiter.waitFor(drive::atAngle, 3000);
		drive.stopTurning();
		drive.arcadeDrive(0,0);
		Waiter.waitFor(500);
		Waiter.waitFor(1000);
		drive.arcadeDrive(0.1, 0);
		Waiter.waitFor(500);
		drive.arcadeDrive(0, 0);
		Waiter.waitFor(1500);
		intake.ejectFast();
		
	}

}
