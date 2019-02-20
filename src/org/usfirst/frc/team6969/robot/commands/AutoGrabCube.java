package org.usfirst.frc.team6969.robot.commands;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team6969.robot.commands.*;
/**
 *Uses "CubePipeline" to find a cube, move to it, and grab the cube
 */
public class AutoGrabCube extends CommandGroup {

	//https://wpilib.screenstepslive.com/s/currentCS/m/vision/l/674733-using-generated-code-in-a-robot-program
	private final double cubeAreaThreshold = 50;
	
    public AutoGrabCube() {
    	//start CubeVisionPipline, return x, y
    	//Parrallel, Follow Cube, get center x, and center y
    	//once cube contours take up entire screen, auto close claw
    	
    	/*
    	this.addParallel(new AutoOpenClaw());
    	//--------------------------------------------------------------Robot drives to nearest Cube 
    	CubeVision cubeVision = new CubeVision();// cube vision starts
    	
    	while(cubeVision.getCubeArea() <=  cubeVision.getImageHeight() * cubeVision.getImageWidth() - cubeAreaThreshold )
    	{
    		this.addParallel(new FollowCube( (int) cubeVision.getCenterX()));
    	}
    	//--------------------------------------------------------------at this point the Robot has moved to the cube and needs to pick it up
    	this.addParallel(new AutoCloseClaw());
    	*/
    }
}
