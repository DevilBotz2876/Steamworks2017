package org.usfirst.frc2876.Steamworks2017.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCGGearRedBoiler extends CommandGroup {

	public AutoCGGearRedBoiler() {

		addSequential(new AutoDriveStraightDistance(12));
		addSequential(new AutoTurning(-40));
		// Start the shooter while driving. It takes a few seconds to get the
		// shooter up to speed and balls fed into the shooter. We want to start
		// shooting just as we get to the peg.
		addParallel(new AutoDriveStraightDistance(80));
		addParallel(new AutoShootBall(50));
	}
}
