package org.usfirst.frc.team6969.robot.subsystems;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.ADXL345_I2C;

import org.usfirst.frc.team6969.robot.GyroItg3200;
import org.usfirst.frc.team6969.robot.RobotMap;
import org.usfirst.frc.team6969.robot.commands.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {
	// DriveTrain component
	private static DifferentialDrive robotDrive;
	private static boolean goThirdSpeed;
	private static boolean goFullSpeed;
	private static int leftYAxis;
	private static int rightYAxis;
	public static GyroItg3200 gyro;
	public static ADXL345_I2C accel; 
    public void initDefaultCommand() {
    	robotDrive =  RobotMap.drive;
    	gyro = RobotMap.gyro;
    	accel = RobotMap.accel;
        setDefaultCommand(new TeleOpDrive());
        System.out.println("Drive train INIT");
        goThirdSpeed = false;
        goFullSpeed = false;
        leftYAxis = 1;
        rightYAxis = 5;
    }
    
    public void takeJoystickInputs(XboxController controller) {

    	if(robotDrive == null ) // prevents robotDrive from being null
    	{
    		this.initDefaultCommand();
    	}
    	//----------------------------------------------------------------------------------------- Gyro Output
    	gyro.updateDashboard("", false);
    	SmartDashboard.putNumber("X-rotation", gyro.getRotationX().getAngle());
    	SmartDashboard.putNumber("Y-rotation", gyro.getRotationY().getAngle());
    	SmartDashboard.putNumber("Z-rotation", gyro.getRotationZ().getAngle());
    	//----------------------------------------------------------------------------------------- Gyro Output
    	SmartDashboard.putNumber("X-Acceleration", accel.getX());
    	SmartDashboard.putNumber("Y-Acceleration", accel.getY());
    	SmartDashboard.putNumber("Z-Acceleration", accel.getZ());
    	
    	//----------------------------------------------------------------------------------------- Speed Controls
    	if(controller.getBumperPressed(GenericHID.Hand.kLeft))
    			goThirdSpeed = true;
    	if(controller.getBumperReleased(GenericHID.Hand.kLeft))
    			goThirdSpeed = false;
    	if(controller.getBumperPressed(GenericHID.Hand.kRight))
    			goFullSpeed = true;    		
    	if(controller.getBumperReleased(GenericHID.Hand.kRight))
    			goFullSpeed = false;
    	//----------------------------------------------------------------------------------------- Sets motor speeds
    	if(!goThirdSpeed && !goFullSpeed) {//going half speed (NORMAL)
	    	robotDrive.tankDrive(controller.getRawAxis(leftYAxis) * -1 * 0.75, controller.getRawAxis(rightYAxis) * -1 *  0.75);
    	}
    	if(goThirdSpeed) {
    		robotDrive.tankDrive(controller.getRawAxis(leftYAxis) * -1 / 2, controller.getRawAxis(rightYAxis) * -1 / 2);
    	}
    	if(goFullSpeed) {
    		robotDrive.tankDrive(controller.getRawAxis(leftYAxis) * -1 , controller.getRawAxis(rightYAxis) * -1);
    	}
    }
    
    public void takeArcadeInputs(double move, double rotate) {
    	// Arcade Drive
    	robotDrive.arcadeDrive(move, rotate);
    }
    

    public void stop() {
    	// Stops DriveTrain
    	robotDrive.stopMotor();
    	
    }
    
}

