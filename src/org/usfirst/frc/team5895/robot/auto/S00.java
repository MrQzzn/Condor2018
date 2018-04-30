package org.usfirst.frc.team5895.robot.auto;

import org.usfirst.frc.team5895.robot.Drivetrain;
import org.usfirst.frc.team5895.robot.Intake;
import org.usfirst.frc.team5895.robot.framework.Waiter;
import edu.wpi.first.wpilibj.DriverStation;
/**
 * center of field, left switch, & left scale.
 * @author lalewis-19
 */
public class S00 {
	
	public static final void run(Drivetrain drive, Intake intake) {
		
		drive.resetNavX();
		intake.intake();
		Waiter.waitFor(200);
		DriverStation.reportError("" + drive.getAngle(), false);
		drive.autoForwardStraight();
		Waiter.waitFor(drive::isPFinished, 5000);
		DriverStation.reportError("" + drive.getAngle(), false);
		drive.arcadeDrive(0, 0);
	}

}
