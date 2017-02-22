// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2876.Steamworks2017.subsystems;

import org.usfirst.frc2876.Steamworks2017.RobotMap;
import org.usfirst.frc2876.Steamworks2017.commands.*;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Shooter extends Subsystem {
	public Shooter(){
		talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		//talon.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		//talon.setFeedbackDevice(FeedbackDevice.PulseWidth);
		
		talon.changeControlMode(TalonControlMode.Speed);
		talon.setInverted(true);
	}

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS
	public final double MAX_RPM = 120;
	public final double MIN_RPM = 0;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final CANTalon talon = RobotMap.shooterTalon;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final CANTalon loaderTalon = RobotMap.fuelTankAgitator;
    


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ShooterIdle());
    }
    public void shooterStart(double rpm){
    	talon.set(rpm);
    	//talon.set(1.0);
    }
    public void shooterStop(){
    	talon.set(0);
    }
    public void loaderStart(){
    	loaderTalon.set(.35);
    }
    public void loaderStop(){
    	loaderTalon.set(0);
    }
    public void loaderInverse(){
    	loaderTalon.set(-.5);
    }
}

