package org.usfirst.frc.team6969.robot.commands;

import edu.wpi.first.wpilibj.command.Command;


import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6969.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.*;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public class AutoTurn extends Command {
	private final double speed = 0.5;
	private static DifferentialDrive robotDrive;
	private double distanceToTravel;
	private double degree;
	private boolean isLeft;
	private double rotationsNeeded;
	private double targetPositionRotations;
	private double initPosLeft;
	private double initPosRight;
	private boolean hasDone;
	private int counter;
	private int times;
	
	private final int timeout = 10;
    int leftCurPos = 0;
    public AutoTurn(double degree, boolean isLeft) 
    {
    	targetPositionRotations = 0;
    	this.degree= degree;
    	this.isLeft = isLeft;
    	
		this.initialize();
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	/*
    	 * public void set(ControlMode mode,
                double demand0,
                double demand1)
				Specified by:
				set in interface IMotorController
				Overrides:
				set in class BaseMotorController
				Parameters:
				mode - Sets the appropriate output on the talon, depending on the mode.
				demand0 - The output value to apply. such as advanced feed forward and/or auxiliary close-looping in firmware. In PercentOutput, the output is between -1.0 and 1.0, with 0.0 as stopped. In Current mode, output value is in amperes. In Velocity mode, output value is in position change / 100ms. In Position mode, output value is in encoder ticks or an analog value, depending on the sensor. See In Follower mode, the output value is the integer device ID of the talon to duplicate.
				demand1 - Supplemental value. This will also be control mode specific for future features.
    	 */
    	rotationsNeeded = 13.6935*15/360;
    	hasDone = false;
    	robotDrive = RobotMap.drive;
    	leftCurPos = 0;
    	((BaseMotorController) RobotMap.driveTrainLeftBack).follow((IMotorController) RobotMap.driveTrainLeftFront); //makes "driveTrainLeftBack" a slave to "driveTrainLeftFront", so slave follows whatever master does
    	((BaseMotorController) RobotMap.driveTrainRightBack).follow((IMotorController) RobotMap.driveTrainRightFront);//makes "driveTrainRightBack" a slave to "driveTrainRightFront", so slave follows whatever master does
    	//----------------------------
    	RobotMap.driveTrainLeftFront.setInverted(false);
    	RobotMap.driveTrainRightFront.setInverted(false);
    	RobotMap.driveTrainLeftBack.setInverted(false);
    	RobotMap.driveTrainRightBack.setInverted(false);
    	if(isLeft)
    	{
    		RobotMap.driveTrainLeftFront.setInverted(true);
    		RobotMap.driveTrainLeftBack.setInverted(true);
    	}
    	else
    	{
    		RobotMap.driveTrainRightFront.setInverted(true);
    		RobotMap.driveTrainRightBack.setInverted(true);
    	}
    		
    	//----------------------------
    	((BaseMotorController) RobotMap.driveTrainLeftFront).configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute ,0,10);
    	((BaseMotorController) RobotMap.driveTrainRightFront).configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute ,0,10);
    	//----------------------------
    	((BaseMotorController) RobotMap.driveTrainLeftFront).configAllowableClosedloopError(0, 0, timeout);
    	((BaseMotorController) RobotMap.driveTrainRightFront).configAllowableClosedloopError(0, 0, timeout);
    	//----------------------------Configuring Voltage
		((BaseMotorController) RobotMap.driveTrainLeftFront).configNominalOutputForward(0, timeout);
		((BaseMotorController) RobotMap.driveTrainLeftFront).configNominalOutputReverse(0, timeout);
		((BaseMotorController) RobotMap.driveTrainLeftFront).configPeakOutputForward(speed, timeout); // speed
		((BaseMotorController) RobotMap.driveTrainLeftFront).configPeakOutputReverse(-1 * speed, timeout); // speed
		((BaseMotorController) RobotMap.driveTrainRightFront).configNominalOutputForward(0, timeout);
		((BaseMotorController) RobotMap.driveTrainRightFront).configNominalOutputReverse(0, timeout);
		((BaseMotorController) RobotMap.driveTrainRightFront).configPeakOutputForward(speed, timeout); // speed
		((BaseMotorController) RobotMap.driveTrainRightFront).configPeakOutputReverse(-1 * speed, timeout); // speed
		//----------------------------
    	/*
    	 	|---------------------------------------------------------------|
			|ErrorCode	config_kD(int slotIdx, double value, int timeoutMs)	|	
			|Sets the 'D' constant in the given parameter slot.				|
			|---------------------------------------------------------------|
			|ErrorCode	config_kF(int slotIdx, double value, int timeoutMs) |
			|Sets the 'F' constant in the given parameter slot.				|
			|---------------------------------------------------------------|
			|ErrorCode	config_kI(int slotIdx, double value, int timeoutMs) |
			|Sets the 'I' constant in the given parameter slot.				|
			|---------------------------------------------------------------|
			|ErrorCode	config_kP(int slotIdx, double value, int timeoutMs) |
			|Sets the 'P' constant in the given parameter slot.				|
			----------------------------------------------------------------|
    	 */
    	//----------------------------------------------------------------------------------
		/* set closed loop gains in slot0 for Left*/
		((BaseMotorController) RobotMap.driveTrainLeftFront).config_kF(0, 0.0, timeout);
		((BaseMotorController) RobotMap.driveTrainLeftFront).config_kP(0, 0.1, timeout);
		((BaseMotorController) RobotMap.driveTrainLeftFront).config_kI(0, 0.0, timeout);
		((BaseMotorController) RobotMap.driveTrainLeftFront).config_kD(0, 0.0, timeout);
		/* set closed loop gains in slot0 for Right*/
		((BaseMotorController) RobotMap.driveTrainRightFront).config_kF(0, 0.0, timeout);
		((BaseMotorController) RobotMap.driveTrainRightFront).config_kP(0, 0.1, timeout);
		((BaseMotorController) RobotMap.driveTrainRightFront).config_kI(0, 0.0, timeout);
		((BaseMotorController) RobotMap.driveTrainRightFront).config_kD(0, 0.0, timeout);
		//----------------------------------------------------------------------------------
		int absolutePositionLeft = ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition();
		int absolutePositionRight = ((BaseMotorController) RobotMap.driveTrainRightFront).getSensorCollection().getPulseWidthPosition();
		
		initPosLeft = ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition();
		initPosRight = ((BaseMotorController) RobotMap.driveTrainRightFront).getSensorCollection().getPulseWidthPosition();
		/* mask out overflows, keep bottom 12 bits */
		absolutePositionLeft &= 0xFFF;
		absolutePositionRight &= 0xFFF;
		/* set the quadrature (relative) sensor to match absolute */
		((BaseMotorController) RobotMap.driveTrainLeftFront).setSelectedSensorPosition(absolutePositionLeft, 0, timeout);
		((BaseMotorController) RobotMap.driveTrainRightFront).setSelectedSensorPosition(absolutePositionRight, 0, timeout);
    	//-------------------------------Zeros position
    	((BaseMotorController) RobotMap.driveTrainLeftFront).setSelectedSensorPosition(0,0,10);
    	((BaseMotorController) RobotMap.driveTrainRightFront).setSelectedSensorPosition(0,0,10);
		//----------------------------------------------------------------------------------
		/* Rotations * 4096 u/rev in either direction */
		targetPositionRotations = (rotationsNeeded * 4000 );
		
		
		System.out.println("rotations Needed: " + rotationsNeeded);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(!isLeft)
    	{
	    	robotDrive.tankDrive(0.5,0.5);
	    	if(hasDone)
	    		robotDrive.tankDrive(0, 0);
	    	if(!hasDone)
	    	{
		    	while( ((BaseMotorController)RobotMap.driveTrainRightFront).getSensorCollection().getPulseWidthPosition() < targetPositionRotations)
					{
		    		SmartDashboard.putNumber("Left", ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition());
			    	SmartDashboard.putNumber("Right", ((BaseMotorController) RobotMap.driveTrainRightFront).getSensorCollection().getPulseWidthPosition());
					}
			
	    	}
	    	hasDone = true;
	    	SmartDashboard.putNumber("Left", ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition());
	    	SmartDashboard.putNumber("Right", ((BaseMotorController) RobotMap.driveTrainRightFront).getSensorCollection().getPulseWidthPosition());
	    	robotDrive.tankDrive(0, 0);
	    	this.end();
    	}
    	else
    	{
	    	robotDrive.tankDrive(0.5,0.5);
	    	if(hasDone)
	    		robotDrive.tankDrive(0, 0);
	    	if(!hasDone)
	    	{
		    	while( ((BaseMotorController)RobotMap.driveTrainRightFront).getSensorCollection().getPulseWidthPosition() > -1 * targetPositionRotations)
					{
		    		SmartDashboard.putNumber("Left", ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition());
			    	SmartDashboard.putNumber("Right", ((BaseMotorController) RobotMap.driveTrainRightFront).getSensorCollection().getPulseWidthPosition());
					}
			
	    	}
	    	hasDone = true;
	    	SmartDashboard.putNumber("Left", ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition());
	    	SmartDashboard.putNumber("Right", ((BaseMotorController) RobotMap.driveTrainRightFront).getSensorCollection().getPulseWidthPosition());
	    	robotDrive.tankDrive(0, 0);
	    	this.end();
    	}
    	/*
    	if(!hasDone)
    	{
    		//((BaseMotorController)RobotMap.driveTrainLeftFront).set(ControlMode.Position, targetPositionRotations);
    		//((BaseMotorController)RobotMap.driveTrainRightFront).set(ControlMode.Position, targetPositionRotations);
    		hasDone = true;
    	}
    	*/
		

    	
    	
    	/*
		int absolutePositionLeft;
		int absolutePositionRight;
		
		absolutePositionLeft=((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition();
		absolutePositionLeft &= 0xFFF;
		
		if(absolutePositionLeft != 0)
			leftCurPos+=absolutePositionLeft;
		
		
		
		*/
    	
    	/*
		System.out.println(leftCurPos);
		if(leftCurPos  > 20000)
			robotDrive.tankDrive(0, 0);
		else
			robotDrive.tankDrive(speed, speed);*/
	/*
	    	do
	    	{
	    		System.out.println("hi");
	    		absolutePositionLeft= ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition();
	    		absolutePositionRight=((BaseMotorController) RobotMap.driveTrainRightFront).getSensorCollection().getPulseWidthPosition();
	    		// mask out overflows, keep bottom 12 bits 
	    		absolutePositionLeft &= 0xFFF;
	    		absolutePositionRight &= 0xFFF;
	    		
	    		leftCurPos +=absolutePositionLeft;
	    		
	    		
	    		if(leftCurPos >= targetPositionRotations + initPosLeft)
	    		{
	    			hasDone = true;
	    			robotDrive.tankDrive(0, 0);
	    			
	    		}
		    	System.out.println("INIT:" + initPosLeft);
		    	System.out.println("Target:" + (targetPositionRotations + initPosLeft));
		    	System.out.println("Right:" + absolutePositionLeft);
	    		
	    	}
	    	while(!hasDone);
	    	*/
    	/*
    	if(!hasDone)
    	{
		((BaseMotorController)RobotMap.driveTrainLeftFront).set(ControlMode.Position, targetPositionRotations );
		((BaseMotorController)RobotMap.driveTrainRightFront).set(ControlMode.Position, targetPositionRotations );
		System.out.println("DONE!");
    	}
    	hasDone = true;
    	*/
    	/*
		int posLeft = ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition();

		/* mask out overflows, keep bottom 12 bits */
    	/*
		posLeft &= 0xFFF;
		hasDone = false;
		counter = 0;
		
		int lastPosition = ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition();
		robotDrive.tankDrive(speed, speed);
		int amntNeeded =(int) rotationsNeeded;

			while(counter!=amntNeeded )
			{
				
				
				//posLeft =  ((BaseMotorController) RobotMap.driveTrainRightFront).getSelectedSensorPosition(0);
				posLeft = ((BaseMotorController) RobotMap.driveTrainLeftFront).getSensorCollection().getPulseWidthPosition();
				posLeft &= 0xFFF;
				
				if( lastPosition > posLeft)
					counter++;
				
			   	SmartDashboard.putNumber("LEft pos", posLeft);
		    	SmartDashboard.putNumber("left target", targetPositionRotations);
		    	SmartDashboard.putNumber("counter", counter);
			}
			
			hasDone = true;
			
		
		
		robotDrive.tankDrive(0, 0);
		*/
    	//if(!hasDone)
    	//{
	    	/* enter closed-loop mode on target position */
	    	//RobotMap.driveTrainLeftFront.set(ControlMode.Position, targetPositionRotations);
	    	//.tankDrive(speed, speed);
	    	//initPosLeft = ((BaseMotorController)RobotMap.driveTrainRightFront).getSelectedSensorPosition(0);
	    	/*
	    	//hasDone = true;
	    	
	    	while(((BaseMotorController)RobotMap.driveTrainRightFront).getSelectedSensorPosition(0) <= targetPositionRotations + initPosLeft)
	    	{
	    		System.out.println("Driving");
		    	//((BaseMotorController)RobotMap.driveTrainLeftFront).set(ControlMode.Position, targetPositionRotations);
		    	//((BaseMotorController)RobotMap.driveTrainRightFront).set(ControlMode.Position, targetPositionRotations);
		    	//hasDone = true;
	    		//robotDrive.tankDrive(speed, speed);
	    		
		    	System.out.println("INIT:" + initPosLeft);
		    	System.out.println("Target:" + (targetPositionRotations + initPosLeft));
		    	System.out.println("Right:" + ((BaseMotorController)RobotMap.driveTrainRightFront).getSelectedSensorPosition(0));
		 		
		    	
	            try 
	            { 
	                    Thread.sleep(10); 
	            } 
	            catch(Exception e){} 
	            */
    		
    		
	    	//}
			
	    	//robotDrive.tankDrive(0, 0);
	    	//System.out.println("LEFT:" + ((BaseMotorController)RobotMap.driveTrainLeftFront).getSelectedSensorPosition(0));
	    	//System.out.println("RIGHT:" + ((BaseMotorController)RobotMap.driveTrainRightFront).getSelectedSensorPosition(0));
	    	/*System.out.println("INIT:" + initPosLeft);
	    	System.out.println("Target:" + (targetPositionRotations + initPosLeft));
	    	System.out.println("Right:" + ((BaseMotorController)RobotMap.driveTrainRightFront).getSelectedSensorPosition(0));
	    	this.end();
	    	*/
		
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return hasDone;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	robotDrive.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
