/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6969.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import org.usfirst.frc.team6969.robot.subsystems.*;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team6969.robot.RobotMap;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.usfirst.frc.team6969.robot.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	
	Command autonomousCommand;
    SendableChooser<Command> autoChooser;
    private static DifferentialDrive robotDrive;
	public static OI oi;
	public static DriveTrain driveTrain;
	public static ForkLift forkLift;
	public static Claw claw;
	public static DriverStation ds;
	public static PowerDistributionPanel pdp;
	public static ClawDrop clawDrop;
	//public static CubeVision cubeVision;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		RobotMap.init();
		
		pdp = new PowerDistributionPanel(30);
		driveTrain = new DriveTrain();
		ds = DriverStation.getInstance();
		forkLift = new ForkLift();   
		claw = new Claw();
		clawDrop = new ClawDrop();
		pdp.clearStickyFaults();
		//cubeVision = new CubeVision();
		CameraServer.getInstance().startAutomaticCapture();
		robotDrive =  RobotMap.drive;
		autoChooser = new SendableChooser<Command>();
		
		SmartDashboard.putData("Autonomous Chooser", autoChooser);
		
		oi = new OI();

		
		/*
        //Driver Station autonomous options
        autoChooser = new SendableChooser<Command>();
        autoChooser.addDefault("1", new Blue1());
        autoChooser.addObject("2", new Blue2());
        autoChooser.addObject("3", new Blue3());     
        
        //Buttons to test
        SmartDashboard.putData("Red1", new Red1());
        SmartDashboard.putData("Red2", new Red2());
        SmartDashboard.putData("Red3", new Red3());
        SmartDashboard.putData("Blue1", new Blue1());
        SmartDashboard.putData("Blue2", new Blue2());
        SmartDashboard.putData("Blue3", new Blue3());
        SmartDashboard.putData("Chase Cube", new AutoGrabCube());
     // Smart Dashboard Info
        SmartDashboard.putData(Scheduler.getInstance());
        */
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		/*
		autonomousCommand = new Blue1();
		autonomousCommand.start();
		*/
	
		autonomousCommand = new AutoFoward(12);
		autonomousCommand.start();
		
		/*
		boolean isSides = true;

		if(isSides)
		{
	    	RobotMap.driveTrainLeftFront.setInverted(false);
	    	RobotMap.driveTrainRightFront.setInverted(false);
	    	RobotMap.driveTrainLeftBack.setInverted(false);
	    	RobotMap.driveTrainRightBack.setInverted(false);
			robotDrive.tankDrive(0.6, 0.6);
            try
            { 
                    Thread.sleep(5000); 
            } 
            catch(Exception e){} 
		}
		robotDrive.tankDrive(0, 0);
		*/
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out. 
		
		if(autonomousCommand != null)
			autonomousCommand.cancel();


		System.out.println("TELEOPINIT");
    	RobotMap.driveTrainLeftFront.setInverted(false);
    	RobotMap.driveTrainRightFront.setInverted(false);
    	RobotMap.driveTrainLeftBack.setInverted(false);
    	RobotMap.driveTrainRightBack.setInverted(false);
		driveTrain.initDefaultCommand();
		forkLift.initDefaultCommand();
		claw.initDefaultCommand();
		clawDrop.initDefaultCommand();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() 
	{
	}
}
