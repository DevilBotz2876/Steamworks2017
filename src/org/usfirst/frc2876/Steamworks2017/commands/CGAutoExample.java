package org.usfirst.frc2876.Steamworks2017.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class CGAutoExample extends CommandGroup {

	public CGAutoExample() {
		//addSequential(new AutoDriveStraightDistance(42));
		//addSequential(new AutoDriveStraightDistance(20));
		addSequential(new AutoDriveStraightDistance(76));
//		addSequential(new TimedCommand(2));
//		addSequential(new AutoDriveStraightDistance(-30));
		
		//addSequential(new AutoToCenterPeg());
	

	}
}
 