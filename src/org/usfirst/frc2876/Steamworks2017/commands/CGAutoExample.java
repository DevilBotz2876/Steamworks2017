package org.usfirst.frc2876.Steamworks2017.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGAutoExample extends CommandGroup {

	public CGAutoExample() {
		addSequential(new AutoDriveStraightDistance(-36));
		addSequential(new AutoToCenterPeg());
		// TODO: Maybe we need to call autotoCenterPeg a few times?
		// In perfect world our PID/robot would line up with one call.

		// TODO: add command that determines distance to peg and drives
		//
		// GearTarget t = Robot.vision.getGearTarget();
		// if (t == null) {
		// SmartDashboard.putString("GTStatus", "null");
		// } else {
		// SmartDashboard.putString("GT", t.toString());
		// SmartDashboard.putString("GTStatus", "ok");
		// }
		// addSequential(new AutoDriveStraightDistance(t.distance()));

		// Just for testing pretend we need to drive 12 inches to get to peg.
		// This should get replaced by above code once we can determine distance
		// using vision/target info.
		addSequential(new AutoDriveStraightDistance(-12));

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
	}
}
