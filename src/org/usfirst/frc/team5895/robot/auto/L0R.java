package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;

class L0R {
	
	public static final void run(Drivetrain drive, Intake intake) {
		
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		drive.autoLeftRightScale();
		Waiter.waitFor(3000);
		Waiter.waitFor(drive::isPFinished, 7000);
		intake.ejectSlow();
		drive.autoRightScaleBackwards();
		Waiter.waitFor(drive::isPFinished, 4000);
		drive.arcadeDrive(0, 0);
	}
}
