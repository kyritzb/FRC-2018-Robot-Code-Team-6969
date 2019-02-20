package org.usfirst.frc.team6969.robot.commands;

import org.usfirst.frc.team6969.robot.Robot;
import org.usfirst.frc.team6969.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class AutoOpenClaw extends Command {

	private static double speed = 0.75; // speed of the claw (this ranges from -1 through 1 where 0 is stopped and 1 is full speed foward and -1 is full speed backwards
	public static Spark clawMotor;
	public static DigitalInput clawLimit;
	public static boolean opening;
	public static boolean isOpened;
	
    public AutoOpenClaw() {
        // Use requires() here to declare subsystem dependencies
    	///requires(Robot.claw);
    	SmartDashboard.putData(Robot.claw);
    	this.initialize(); // after the TeleopDrive object is declared
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	opening = true;
    	isOpened = false;
    	clawMotor = RobotMap.clawMotor;
    	clawLimit = RobotMap.clawLimit;
    	clawMotor.set(speed * -1);
    	while(!isOpened)
    	{
        	if(clawLimit.get())
        		isOpened = true;
    	}
    	clawMotor.set(0);
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
