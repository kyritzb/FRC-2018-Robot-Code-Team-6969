package org.usfirst.frc.team6969.robot.commands;

import org.usfirst.frc.team6969.robot.Robot;
import org.usfirst.frc.team6969.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoLowerFork extends Command {

	public static Spark flMotor;
	public static boolean goingDown;
	private boolean isAtBottom;
	private final int miliSecTime = 2500;
    public AutoLowerFork() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	//requires(Robot.forkLift);
    	requires(Robot.forkLift);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	flMotor = RobotMap.flMotor;
    	isAtBottom = false;
    	goingDown = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	flMotor.set(1);
    	try 
        { 
                Thread.sleep(miliSecTime); 
        } 
         catch(Exception e){} 
    	flMotor.set(0);
    	this.end();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
