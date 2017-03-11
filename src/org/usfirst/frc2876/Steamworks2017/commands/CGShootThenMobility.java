package org.usfirst.frc2876.Steamworks2017.commands;

import org.usfirst.frc2876.Steamworks2017.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGShootThenMobility extends CommandGroup {

	public CGShootThenMobility(boolean isRightSide) {
		int angleFromShot = 60;
		double turningValue = .5; //like from a joystick (-1 to 1)
		
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
//		TODO once we have functional pixy shooting software, uncomment below and try out with proper angle value
//		if (Robot.IS_TURN_PID_FUNCTIONAL) addSequential(new AutoTurning(0/*Shooter Pixy Angle Once existant, */));
		addSequential(new AutoShoot(8.0));
//		if (Robot.IS_TURN_PID_FUNCTIONAL) {
//			addSequential(new AutoTurning(angleFromShot));
//		} else {
//			double startTime = Timer.getFPGATimestamp();
//			while (Timer.getFPGATimestamp() - startTime > 1){
//				Robot.driveTrain.myRobot.arcadeDrive(0, turningValue);
//			}
//			Robot.driveTrain.myRobot.arcadeDrive(0, 0);
//		}
		double distanceBetweenWheels = 26;
		Robot.driveTrain.turnSide(isRightSide, Math.PI * distanceBetweenWheels / 3.0);
		addSequential(new AutoMobility());
	}
}
