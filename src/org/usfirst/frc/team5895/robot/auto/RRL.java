package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;
/**
 * Right side of field, right switch, & left scale.
 * @author lalewis-19
 */
public class RRL {

	public static final void run(Drivetrain drive, Intake intake) {
		
		// switch > near
		// scale > far
		
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		drive.autoRightLeftScale();
		Waiter.waitFor(3000);
		Waiter.waitFor(drive::isPFinished, 4000);
		intake.ejectSlow();
		
	}

}
