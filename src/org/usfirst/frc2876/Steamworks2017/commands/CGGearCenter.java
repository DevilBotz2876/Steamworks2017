package org.usfirst.frc2876.Steamworks2017.commands;

import org.usfirst.frc2876.Steamworks2017.Robot;
import org.usfirst.frc2876.Steamworks2017.subsystems.GearTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGGearCenter extends CommandGroup {
	final double DISTANCE_TO_RANGE = -96;

	public CGGearCenter() {
		addSequential(new AutoDriveStraightDistance(DISTANCE_TO_RANGE));
		GearTarget t = Robot.vision.getGearTargetFiltered();
		if (t != null) {
			addSequential(new AutoToCenterPeg());
			addSequential(new AutoDriveStraightDistance(-t.distance()));
		}
	}
}
