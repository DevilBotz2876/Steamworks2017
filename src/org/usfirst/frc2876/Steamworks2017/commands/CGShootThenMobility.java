package org.usfirst.frc2876.Steamworks2017.commands;

import org.usfirst.frc2876.Steamworks2017.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGShootThenMobility extends CommandGroup {

	public CGShootThenMobility() {
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
		addSequential(new AutoShoot());
		if (Robot.IS_TURN_PID_FUNCTIONAL) {
			addSequential(new AutoTurning(60));
		} else {
			double startTime = Timer.getFPGATimestamp();
			while (Timer.getFPGATimestamp() - startTime > 1){
				Robot.driveTrain.myRobot.arcadeDrive(0, .5);
			}
			Robot.driveTrain.myRobot.arcadeDrive(0, 0);
		}
		addSequential(new AutoMobility());
	}
}
