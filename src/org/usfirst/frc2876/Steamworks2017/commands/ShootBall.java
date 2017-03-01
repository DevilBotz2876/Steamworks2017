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

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;

import org.usfirst.frc2876.Steamworks2017.Robot;

/**
 *
 */
public class ShootBall extends Command {

	double startTime;
	boolean hasLoaderStarted = false;
	boolean isLoaderInversed = false;
	final double LOADER_WAIT = 10.0;
	final int LOADER_INVERSE_INTERVAL = 20; // time between inverting. Used in
											// modulus, so it needs to be an int
	final double LOADER_INVERSE_DURATION = 6;
	double loaderInverseStart;

	public ShootBall() {
		requires(Robot.shooter);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.shooter.shooterStart(Robot.shooter.MAX_RPM);
		startTime = Timer.getFPGATimestamp() * 10;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double currentTime = Timer.getFPGATimestamp() * 10;
		double currentDiff = currentTime - startTime;
		if (currentDiff > LOADER_WAIT && !hasLoaderStarted) {
			Robot.shooter.loaderStart();
			hasLoaderStarted = true;
		}
		if (!isLoaderInversed && (int) currentDiff % LOADER_INVERSE_INTERVAL == 0 && currentTime > startTime + 10) {
			Robot.shooter.loaderInverse();
			isLoaderInversed = true;
			loaderInverseStart = currentTime;
		}
		if (isLoaderInversed && currentTime - loaderInverseStart > LOADER_INVERSE_DURATION) {
			Robot.shooter.loaderStart();
			isLoaderInversed = false;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.shooter.loaderStop();
		Robot.shooter.shooterStop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
