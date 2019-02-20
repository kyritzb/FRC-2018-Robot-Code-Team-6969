package org.usfirst.frc.team6969.robot.commands;

import org.usfirst.frc.team6969.robot.Robot;
import org.usfirst.frc.team6969.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoRaiseFork extends Command {

	public static Spark flMotor;
	private final int miliSecTime = 2800;
	private boolean isDone;
    public AutoRaiseFork() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	requires(Robot.forkLift);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isDone = false;
    	flMotor = RobotMap.flMotor;
    	flMotor.set(-1);
    	try 
        { 
                Thread.sleep(miliSecTime); 
        } 
         catch(Exception e){} 
    	System.out.println("Done");
    	flMotor.set(0);
    	isDone = true;
    	this.end();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
