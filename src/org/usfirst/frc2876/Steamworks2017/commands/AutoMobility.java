package org.usfirst.frc2876.Steamworks2017.commands;

import org.usfirst.frc2876.Steamworks2017.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutoMobility extends Command {
	
	private final int DISTANCE_IN_INCHES = 132;
	private final double SPEED = .8; //speed out of 1
	private final double INCHES_PER_SECOND = SPEED * 125.0; //TODO change this value after testing
	private double startTime;
	
    public AutoMobility() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain); 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.stopAllPID();
    	if (Robot.IS_STRAIGHT_PID_FUNCTIONAL) {
    		Robot.driveTrain.startStraight(false);
    	}
    	if (Robot.IS_DISTANCE_PID_FUNCTIONAL) {
    		Robot.driveTrain.startDistance(-DISTANCE_IN_INCHES);
    	} else {
        	startTime = Timer.getFPGATimestamp();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.IS_DISTANCE_PID_FUNCTIONAL && Robot.IS_STRAIGHT_PID_FUNCTIONAL){
    		Robot.driveTrain.velocityDistanceStraight();
    	} else if (Robot.IS_STRAIGHT_PID_FUNCTIONAL) {
    		Robot.driveTrain.velocityTankStraightJoysticks(SPEED);
    	} else if (Robot.IS_DISTANCE_PID_FUNCTIONAL) {
    		Robot.driveTrain.velocityDistance(-1);
    	} else {
    		Robot.driveTrain.setVelocityArcadeJoysticks(SPEED, 0);
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.IS_DISTANCE_PID_FUNCTIONAL){
    		return Robot.driveTrain.isDistanceDone();
    	} else {
    		return (Timer.getFPGATimestamp() - startTime > DISTANCE_IN_INCHES / INCHES_PER_SECOND);
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stopAllPID();
		Robot.driveTrain.setVelocityArcadeJoysticks(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
