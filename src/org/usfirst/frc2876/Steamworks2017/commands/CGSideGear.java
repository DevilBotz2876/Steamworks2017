/* Minimized is the previous code if you hate what I did
package org.usfirst.frc2876.Steamworks2017.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CGGearRight extends CommandGroup {

    public CGGearRight() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new AutoDriveStraightDistance(36));
    	addSequential(new AutoTurning(-40));
    	addSequential(new AutoDriveStraightDistance(82));
    	addSequential(new ShootBall());
//    	addSequential(new AutoDriveStraightDistance(-6));
    }
}*/
package org.usfirst.frc2876.Steamworks2017.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Same as CGGearRight was except now customizable for all side gear autonoma
 */
public class CGSideGear extends CommandGroup {

    public CGSideGear(int initialDistance, int turnDegrees, int finalDistance, boolean shoot) {
    	addSequential(new AutoDriveStraightDistance(initialDistance)); 	//RedRight = 36
    	addSequential(new AutoTurning(turnDegrees));					//RedRight = -40
    	addSequential(new AutoDriveStraightDistance(finalDistance));	//RedRight = 82
    	if (shoot) addSequential(new ShootBall());						//RedRight = true
    }
}