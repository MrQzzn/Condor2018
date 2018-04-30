package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;
/**
 * center of field, left switch, & left scale.
 * @author lalewis-19
 */
public class RR0 {
	
	public static final void run(Drivetrain drive, Intake intake) {
		
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		intake.down();
		drive.autoRightRightSwitchFront();
		Waiter.waitFor(drive::isPFinished, 4000);
		intake.ejectFast();
		
	}

}
