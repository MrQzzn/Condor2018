package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;

public class R0L {

	public static final void run(Drivetrain drive, Intake intake) {
		
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		drive.autoRightLeftScale();
		Waiter.waitFor(3000);
		Waiter.waitFor(drive::isPFinished, 4000);
		Waiter.waitFor(1000);
		intake.ejectCustom(0.7);
		drive.autoLeftScaleBackwards();
		Waiter.waitFor(drive::isPFinished, 4000);
		drive.arcadeDrive(0, 0);
}
}
