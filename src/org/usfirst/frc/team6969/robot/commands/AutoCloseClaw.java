package org.usfirst.frc.team6969.robot.commands;

import org.usfirst.frc.team6969.robot.Robot;
import org.usfirst.frc.team6969.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AutoCloseClaw extends Command {

	private static double speed = 1; // speed of the claw (this ranges from -1 through 1 where 0 is stopped and 1 is full speed foward and -1 is full speed backwards
	private static double maxCurrentIn = 50; // this is the current limit when the claw stops squeezing. With the redline, the claw burns at 110 Amps
	public static Spark clawMotor;
	private int delay = 200;
	public static boolean isClosed;
	private boolean hasDone;
	private Timer timer;
	private double current;
    public AutoCloseClaw() {
        // Use requires() here to declare subsystem dependencies
    	//requires(Robot.claw);
    	current = 0;
    	isClosed = false;
    	hasDone = false;

    	clawMotor = RobotMap.clawMotor;
    	SmartDashboard.putData(Robot.claw);
    	this.initialize(); // after the TeleopDrive object is declared
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer = new Timer();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	//System.out.println((curTime - time) / 1000);
    	//curTime =  System.currentTimeMillis(); 
    	//if(!hasDone)
    	//{
	    	//if( (curTime - time ) /1000< 1)
	    		
	    	//	hasDone = true;
    	//}
    	if(!hasDone)
    	{

	   
	    	
	    	clawMotor.set(speed);
	    	try 
	        { 
	                Thread.sleep(delay); 
	        } 
	         catch(Exception e){} 
	    	while(!isClosed || timer.get()<4)
	    	{
	    		current = Robot.pdp.getCurrent(1);
	    	
	        	if(current > maxCurrentIn)
	        		isClosed = true;
	        	System.out.println(current > maxCurrentIn);
	        	if(timer.get()<4)
	        		isClosed = true;
	    	}
	    	clawMotor.set(0);
	    	hasDone = true;
	    	this.end();
	    	
	    	
    	}
    	else
    	{
    		clawMotor.set(0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return hasDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
