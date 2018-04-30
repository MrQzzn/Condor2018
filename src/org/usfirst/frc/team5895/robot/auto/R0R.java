package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class R0R {

	public static final void run(Drivetrain drive, Intake intake) {
		
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		drive.autoRightRightScale();
		Waiter.waitFor(1000);
		Waiter.waitFor(drive::isPFinished, 2500);
		intake.ejectFast();
		Waiter.waitFor(500);
	}
	
}
