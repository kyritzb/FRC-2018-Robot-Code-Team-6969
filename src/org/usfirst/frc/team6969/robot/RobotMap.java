/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6969.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	public static double driveWheelRadius = 3.0; //inches
	public static double driveWheelDiameter = driveWheelRadius * 2; //6 inches
	public static double driveWheelCircumferance = driveWheelRadius * 2 * Math.PI; // 18.84956inches
	public static double wheelRotation360degree = 6.97814; //amount of wheel rotations the robot will make 360* turn in when both sides are driving opposite ways 
	
    public static SpeedController driveTrainLeftFront;
    public static SpeedController driveTrainLeftBack;
    public static SpeedController driveTrainRightFront;
    public static SpeedController  driveTrainRightBack;
    
    public static Spark flMotor;
    public static Spark clawMotor;
    public static Spark dropMotor;
    
    public static DigitalInput magLimit;
    public static DigitalInput  clawLimit;
    
    public static ADXL345_I2C accel; 
    public static GyroItg3200 gyro;
   // public static GyroAccumulator gyroAccum;
	public static DifferentialDrive drive = null;
	public static void init() {
			/*
			 * 
			 * WPI_TalonSRX(int deviceNumber)
			 * 
			 * 
		Talon 12 - Back Right
		Talon 13 - Front Right
		Talon 14 - Front Left
		Talon 15 - Back Left
		*/
		driveTrainLeftBack = new WPI_TalonSRX(15); 
		driveTrainLeftFront =  new WPI_TalonSRX(14);
		driveTrainRightBack = new WPI_TalonSRX(13); 
		driveTrainRightFront =  new WPI_TalonSRX(12);
		
		
		flMotor = new Spark(0);
		clawMotor = new Spark(1); //1
		dropMotor = new Spark(2); //2
		//topForkLiftButton = new DigitalInput(3);
		//bottomForkLiftButton = new DigitalInput(3);
	    //Drive Train
	    
	    /*definitions-----------------------------------------------------------------------------------------------
	    SpeedController Definition: Interface for speed controlling devices.(Electric output)
	    
	    Actuator picture: https://goo.gl/mUYxDT
	    Actuator definition: a component of a machine that is responsible for moving and controlling a mechanism or system
	    
	    Talon: Cross the Road Electronics (CTRE) Talon and Talon SR Speed Controller.
	    
	    
	    Channel: The PWM channel that the Talon is attached to. 0-9 are on-board, 10-19 are on the MXP port
	    **Declaration** SpeedControllerObject = new Talon(Channel);
	    
	    A differential drive robot has left and right wheels separated by an arbitrary width.
	
											Drive base diagram:
											
											 |_______|
											 | |   | |
											   |   |
											 |_|___|_|
											 |       |
	    
			public void tankDrive(double leftSpeed,
			                      double rightSpeed,
			                      boolean squaredInputs)
			                      
			                      
			Tank drive method for differential drive platform.
			Parameters:
			leftSpeed - The robot left side's speed along the X axis [-1.0..1.0]. Forward is positive.
			rightSpeed - The robot right side's speed along the X axis [-1.0..1.0]. Forward is positive.
			squaredInputs - If set, decreases the input sensitivity at low speeds.
	
	
	
	    -----------------------------------------------------------------------------------------------*/
	    LiveWindow.add( (WPI_TalonSRX) driveTrainLeftFront);
	    LiveWindow.add( (WPI_TalonSRX) driveTrainLeftBack);
	    LiveWindow.add( (WPI_TalonSRX) driveTrainRightFront);
	    LiveWindow.add( (WPI_TalonSRX) driveTrainRightBack);

	    magLimit = new  DigitalInput(0);
	    clawLimit = new DigitalInput(1);
	    final SpeedControllerGroup m_left = new SpeedControllerGroup(driveTrainLeftFront, driveTrainLeftBack); //left drivetrain motors
	    
	    final SpeedControllerGroup m_right = new SpeedControllerGroup(driveTrainRightFront, driveTrainRightBack); //Right drivetrain motors
	   
		
	    drive = new DifferentialDrive(m_left, m_right); // entire drive train (this is the object that you call the tankdrive method )
	    
		flMotor.setSafetyEnabled(false);
		flMotor.setExpiration(0.1);
		
		clawMotor.setSafetyEnabled(false);
		clawMotor.setExpiration(0.1);
		
	    drive.setSafetyEnabled(false);
	    drive.setExpiration(0.1);
	    drive.setMaxOutput(1.0);

	    accel = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G, 0x53);
	    gyro = new GyroItg3200(I2C.Port.kOnboard, false);
	    
	   //gyroAccum = new GyroAccumulator();
	}	
}
