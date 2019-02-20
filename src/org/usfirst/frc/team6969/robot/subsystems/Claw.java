package org.usfirst.frc.team6969.robot.subsystems;

import org.usfirst.frc.team6969.robot.Robot;
import org.usfirst.frc.team6969.robot.RobotMap;
import org.usfirst.frc.team6969.robot.commands.*;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Claw extends Subsystem {
	
	private static double speed = 1; // speed of the claw (this ranges from -1 through 1 where 0 is stopped and 1 is full speed foward and -1 is full speed backwards
	private static double maxCurrentIn = 10; // this is the current limit when the claw stops squeezing. With the redline, the claw burns at 110 Amps

	private static DigitalInput clawLimit; 
	public static Spark clawMotor;
	public static boolean opening;
	public static boolean closing;
	public static boolean isClosed;
	public static boolean isOpened;
	
	public static boolean hasDropped;
	private static double current;
    public void initDefaultCommand() {
    	setDefaultCommand(new OpenClaw());
    	opening = false;
    	closing = false;
    	isOpened = false;
    	hasDropped = false;
    	current = 0.0;
    	isClosed = false;
    	clawMotor = RobotMap.clawMotor;
    	clawLimit = RobotMap.clawLimit;
    }
    
    public void move() {
    	if(clawMotor == null ) // prevents clawMotor from being null
    	{
    		this.initDefaultCommand();
    	}
    	
    	//---------------------------------------------------------------------------------------- Checks the Current and determinds if it should shut off the motor
    	//if(Robot.pdp.getCurrent(1) != 0)
    		//System.out.println(Robot.pdp.getCurrent(1));
    	current = Robot.pdp.getCurrent(1);
    	if(current > maxCurrentIn && closing)
    	{
    		//Robot.oi.getController().setRumble(RumbleType.kLeftRumble, 1);
    		
    	}
    	/*
    	if(current != 0)
    		System.out.println(current);*/
    		//isClosed = true;
    	//System.out.println(clawLimit.get());
    	//if(clawLimit.get())
    		//isOpened = true;
    	//---------------------------------------------------------------------------------------- Controller inputs
    	opening = false;
    	if(Robot.oi.xButton.get()) // if x is clicked
    	{
    	  	opening = true;
    	  	closing = false;
    	  	
    	}
    	if(Robot.oi.squareButton.get() && !closing) // if o is clicked
    	{
    		closing = true;
    		opening = false;
    		
    		
    	}
    	
    	if(closing)
    	{
    		clawMotor.set(speed * -1);
    	}
    	else if(opening)
    	{
    		clawMotor.set(speed);
    	}
    	else
    	{
    		clawMotor.set(0);
    	}
    		
    	//else
    		//closing = false;
    	//----------------------------------------------------------------------------------------Setting Motor Speeds

    	/*
    	if(!opening && !closing)
    		clawMotor.set(0);
    	else if(opening && !closing)
    		clawMotor.set(speed * -1);
    	else if(!opening && closing)
    		clawMotor.set(speed );
    	else
    		clawMotor.set(0);*/

    }
    
    public void stop() {
    	clawMotor.set(0);
    }
    
}

