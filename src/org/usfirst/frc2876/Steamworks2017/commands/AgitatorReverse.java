package org.usfirst.frc2876.Steamworks2017.commands;

import org.usfirst.frc2876.Steamworks2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AgitatorReverse extends Command {

    public AgitatorReverse() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.loaderInverse();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.loaderStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
