package org.usfirst.frc.team6969.robot.subsystems;

import org.usfirst.frc.team6969.robot.RobotMap;
import org.usfirst.frc.team6969.robot.commands.*;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ForkLift extends Subsystem {
	// DriveTrain component
	public static Spark flMotor;
	
	public static boolean goingDown;
	public static boolean goingUp;
	private boolean isAtTop;
	private boolean isAtBottom;
	private boolean LUp; //Last Up
	private boolean LDown; //Last Down
	private boolean changeDir; //changing direction(used for acceleration)
	private double curSpeed; //current speed
	private int counter;
	private final double maxSpeed = 1;
	public static DigitalInput magLimit;
	
    public void initDefaultCommand() {
    	setDefaultCommand(new RaiseForkLift());
    	flMotor = RobotMap.flMotor;
    	magLimit = RobotMap.magLimit;
    	goingDown = false;
    	goingUp = false;
    	setCurSpeed(0.0);
    	counter = 0;
    	changeDir = false;
    	LUp = false;
    	LDown = false;
    	isAtBottom = false;
    	isAtTop = false;
    }
    
    public void move(XboxController controller) {
    	if(flMotor == null ) // prevents ftDrive from being null
    	{
    		this.initDefaultCommand();
    	}

    	if(!magLimit.get()  && goingUp) //is at top
    		isAtTop = true;
    	if(!magLimit.get() && goingDown) //is at bottom
    		isAtBottom = true;
    	
    	
    	
    	LUp = goingUp;
    	LDown = goingDown;
    	goingDown = false;
    	goingUp = false;
    	
    	//--------------------------------------------------Checks if changing direction
    	if(LUp != goingUp)
    		changeDir = true;
    	else if(LDown != goingDown)
    		changeDir = true;
    	//--------------------------------------------------Acceleration if changed direction
    	
    	if(changeDir) //acceleration
    	{
    		counter++;
    		if(counter < 100)
    		{
    			setCurSpeed(counter / 100 * maxSpeed);
    		}
    		else
    		{
    			changeDir = false;
    		}
    	}
    	else //at max speed
    	{
    		setCurSpeed(maxSpeed);
    		counter = 0;
    	}
    	//-------------------------------------------------------------Controller Inputs
    	if(controller.getPOV() == 0 && !isAtTop) //going up
    	{
    		goingUp = true;
    		isAtBottom = false;
    	}
    	else if(controller.getPOV() == 180 && !isAtBottom) //going down
    	{
    		goingDown = true;
    		isAtTop = false;
    	}
    	else //not moving; not clicking dpad up for down
    		flMotor.set(0);
    	
    	//----------------------------------------------------------------Sets Motor Speeds
	    if(goingUp)
	    	flMotor.set(-1);
	    else if(goingDown)
	    	flMotor.set(1);
    }

    
    public void stop() {
    	flMotor.set(0);
    	
    }

	public double getCurSpeed() {
		return curSpeed;
	}

	public void setCurSpeed(double curSpeed) {
		this.curSpeed = curSpeed;
	}
    
}

