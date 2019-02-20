package org.usfirst.frc.team6969.robot.commands;

import org.usfirst.frc.team6969.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
/**
 *
 */

public class boi extends Command {
	DifferentialDrive robotDrive;
	
	private boolean hasDone;
    public boi() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

    	this.initialize();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	hasDone = false;
    	if(!hasDone){
    		
    	
		      robotDrive = RobotMap.drive;
		  	  robotDrive.tankDrive(0.63,0.6);
		      try 
		      { 
		              Thread.sleep(2500); 
		      } 
      
      catch(Exception e){} 
      robotDrive.tankDrive(0,0);
      hasDone = true;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return hasDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
