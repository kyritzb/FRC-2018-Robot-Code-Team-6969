package org.usfirst.frc.team6969.robot.commands;

import org.usfirst.frc.team6969.robot.CubePipeline;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team6969.robot.RobotMap;
/**
 *
 */
public class FollowCube extends Command {
	private int x; // xCenter of the cube
	private int y; // yCenter of the cube
	private final double speed = 0.6; //speed the robot will drive
	private final double xOffset = 0; //x- offset to deal with claw(if the cube is more right of the screen then use  a negative number, if on the left of the screen, use a positive number
	private final double centerThreshold = 10; // amount off from the exact center of the camera where the robot will will drive straight
	private static final int IMG_WIDTH = 320; //width of the camera
	private static final int IMG_HEIGHT = 240; //height of the camera
	private double middleX; // middle pixel of the x (width)
	private boolean goLeft;
	private boolean goStraight;
	private static DifferentialDrive robotDrive;
	
    public FollowCube(int x) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.x = x;
    	goLeft = false;
    	goStraight = false;
    	this.initialize();
    }

    public void updateCoords(int x)
    {
    	this.x = x;

    }
    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	middleX = IMG_WIDTH/2;

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	goStraight = false;
    	if(x  + xOffset - centerThreshold >= middleX && x+ xOffset+  centerThreshold <= middleX)
    		goLeft = true;
    	else if(x + xOffset> middleX)
    		goLeft = false;
    	else
    		goStraight = true;
    	
    	if(goStraight)
    		robotDrive.tankDrive(speed, speed);
    	else if(goLeft)
    		robotDrive.tankDrive(0, speed);
    	else if(!goLeft)
    		robotDrive.tankDrive(speed, 0);
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
