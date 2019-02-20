package org.usfirst.frc.team6969.robot.commands;

import edu.wpi.first.wpilibj.command.Command;


import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6969.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.*;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

public class AutoFoward extends Command {
	private final double speed = 0.5;
	private static DifferentialDrive robotDrive;
	private double distanceToTravel;
	private double rotationsNeeded;
	private double targetPositionRotations;
	private double initPosLeft;
	private double initPosRight;
	private boolean hasDone;
	private int counter;
	private int times;
	
	private final int timeout = 10;
    int leftCurPos = 0;
    public AutoFoward(double distance) 
    {
    	targetPositionRotations = 0;
    	this.distanceToTravel = distance;
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
    	rotationsNeeded = distanceToTravel/ RobotMap.driveWheelCircumferance;
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
		targetPositionRotations = (rotationsNeeded * 4096);
		
		
		System.out.println("rotations Needed: " + rotationsNeeded);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	/*
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
