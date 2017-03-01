package org.usfirst.frc2876.Steamworks2017.commands;

import org.usfirst.frc2876.Steamworks2017.Robot;
import org.usfirst.frc2876.Steamworks2017.subsystems.GearTarget;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGGearCenter extends CommandGroup {

	public CGGearCenter() {
		addSequential(new AutoDriveStraightDistance(-96));
		addSequential(new AutoToCenterPeg());
		GearTarget t = Robot.vision.getGearTargetFiltered();
		if (t != null) {
//			addPar
			addSequential(new AutoDriveStraightDistance(t.distance()));
		}
	}
}
