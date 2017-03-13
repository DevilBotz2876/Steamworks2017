// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc2876.Steamworks2017.commands;

import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2876.Steamworks2017.Robot;
import org.usfirst.frc2876.Steamworks2017.RobotMap;
import org.usfirst.frc2876.Steamworks2017.subsystems.GearTarget;

/**
 *
 */
public class UpdateForPegAngle extends Command {

	int counter;

	public UpdateForPegAngle() {
		// This is suppose to turn to the peg
		// TODO - rename this class
		requires(Robot.vision);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		GearTarget t = Robot.vision.getGearTarget();
		if (t == null) {
			SmartDashboard.putString("Peg Angle Status: ", "null ");
		} else {
			SmartDashboard.putNumber("Peg Angle: ", t.angle());
			SmartDashboard.putString("Peg Angle Status: ", "ok");
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}