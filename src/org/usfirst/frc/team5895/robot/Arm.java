package org.usfirst.frc.team5895.robot;

import edu.wpi.first.wpilibj.Talon;

public class Arm {
	private Talon armMotor;
	private double armSpeed;
	private enum Position {BACK, UP, DOWN};
	private Position pos = Position.DOWN;
	int whateverPort;
	
	public Arm () {
		armMotor = new Talon(whateverPort);
		armSpeed = 0.0;
	}
	
	public void run() {
		
	}
}
