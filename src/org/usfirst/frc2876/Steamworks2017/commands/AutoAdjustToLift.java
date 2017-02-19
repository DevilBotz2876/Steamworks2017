package org.usfirst.frc2876.Steamworks2017.commands;

import org.usfirst.frc2876.Steamworks2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoAdjustToLift extends Command {

    public AutoAdjustToLift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//adjusts angle to have the robot straight towards the gear lift.
    	if(Robot.vision.packet1[0].X < 159)
    		Robot.driveTrain.myRobot.setLeftRightMotorOutputs(-.3, -.3);
    	else if(Robot.vision.packet1[0].X > 161)   
    		Robot.driveTrain.myRobot.setLeftRightMotorOutputs(.3, .3);
    	//if signature's avg width and height < set average move straight forward
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
        //return if we met the set average of the set width and height for when we are close enough for the gear to be placed
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
