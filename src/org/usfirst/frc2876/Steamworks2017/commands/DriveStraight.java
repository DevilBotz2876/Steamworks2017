package org.usfirst.frc2876.Steamworks2017.commands;

import org.usfirst.frc2876.Steamworks2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {

    public DriveStraight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.navx.reset();
    	Robot.driveTrain.startStraight();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double rightX;
    	double leftY;
    	if (Robot.driveTrain.toggleInverseDrive() == false) {
			leftY = Robot.oi.getLeftY();
			rightX = Robot.oi.getRightX();
		} else {
			leftY = Robot.oi.getLeftY() * -1;
			rightX = Robot.oi.getRightX() * -1;
		}
    	if (Robot.driveTrain.isTurnRunning() == false) {
			Robot.driveTrain.velocityTankStraightJoysticks(leftY);
		}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stopStraight();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
