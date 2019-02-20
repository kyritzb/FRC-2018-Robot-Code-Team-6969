package org.usfirst.frc.team6969.robot.commands;

import org.usfirst.frc.team6969.robot.Robot;
import org.usfirst.frc.team6969.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoLowerForkStart extends Command {

	public static Spark flMotor;
	private final int delay = 1200;
	private boolean hasDone;
    public AutoLowerForkStart() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.forkLift);
    	requires(Robot.forkLift);
    	hasDone = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	flMotor = RobotMap.flMotor;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(hasDone)
    	{
    		flMotor.set(0);
    	}
    	else
    	{
    		flMotor.set(1);
    		hasDone = true;
    	}
    	try 
        { 
                Thread.sleep(delay); 
        } 
         catch(Exception e){} 
    	flMotor.set(0);
    	this.end();
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
