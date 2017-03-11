package org.usfirst.frc2876.Steamworks2017.commands;

import org.usfirst.frc2876.Steamworks2017.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CGPegNoTurnPid extends CommandGroup {

    public CGPegNoTurnPid(boolean isRightPeg) {
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
    	double distanceBetweenWheels = 26; //in inches
		addSequential(new AutoDriveStraightDistance(90));
		Robot.driveTrain.turnSide(isRightPeg, Math.PI * distanceBetweenWheels / 3.0);
    }
}
