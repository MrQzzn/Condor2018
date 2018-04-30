package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class L0L {

	public static final void run(Drivetrain drive, Intake intake) {
		
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		drive.autoLeftLeftScale();
		Waiter.waitFor(1000);
		Waiter.waitFor(drive::isPFinished, 5000);
		intake.ejectSlow();
		Waiter.waitFor(500);
	}
}
