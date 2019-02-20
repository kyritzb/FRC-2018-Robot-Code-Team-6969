package org.usfirst.frc.team6969.robot.commands;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team6969.robot.CubePipeline;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */
public class CubeVision extends Command {

	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private final int fps = 24;
	
	private VisionThread visionThread;
	public static int centerX;
	public static int centerY;
	private Object imgLock;
	private UsbCamera camera;
	private int cubeCandidates;
	private double cubeArea;
	/*
	 * The VisionThread is a WPILib class makes it easy to do your camera processing in a separate thead from the rest of the robot program.
		centerX value will be the computed center X value of the detected target.
		RobotDrive encapsulates the 4 motors on this robot and allows simplified driving.
		imgLock: is a variable to synchronize access to the data being simultaneously updated with each image aquisition pass and the code that's processing the corrdinates and steering the robot.
	 */

	 
    public CubeVision() {
    	
	    camera = CameraServer.getInstance().startAutomaticCapture();
	    camera.setResolution(IMG_WIDTH, IMG_HEIGHT);
	    camera.setFPS(fps);

	    //FollowCube followCube = new FollowCube(IMG_WIDTH/2);
	    visionThread = new VisionThread(camera, new CubePipeline(), pipeline -> {
	    	//---------------------------------------------------------------------------Processing 
	        if (!pipeline.filterContoursOutput().isEmpty()) {
		    	//get index of bounding box of the closest box
	        	Rect firstCube = Imgproc.boundingRect(pipeline.convexHullsOutput().get(0));
		    	double biggestArea = firstCube.area();
		    	int biggestCubeIndex = 0;
		    	cubeCandidates = pipeline.convexHullsOutput().size();
		    	//------------------------------------------------------
		    	//Sorts through all the convuxHullsOutput and gets the index of the biggest area store in "biggestCubeIndex"
		    	for(int i = 1; i<cubeCandidates; i++)
		    	{
		    		Rect candidatebox = Imgproc.boundingRect(pipeline.convexHullsOutput().get(i));
		    		if(candidatebox.area() > biggestArea)
		    		{
		    			biggestArea = candidatebox.area();
		    			biggestCubeIndex = i;
		    		}
		    	}
	        	//------------------------------------------------------
	        	//gets centerX of box
	            Rect selectedBox = Imgproc.boundingRect(pipeline.convexHullsOutput().get(biggestCubeIndex));
	            synchronized (imgLock) {
	            	cubeArea = selectedBox.area();
	                centerX = selectedBox.x + (selectedBox.width / 2);
	                centerY = selectedBox.y + (selectedBox.height / 2);
	                //followCube.updateCoords(centerX);
					SmartDashboard.putNumber("Cube Candidates", pipeline.convexHullsOutput().size());
					SmartDashboard.putNumber("CenterX", centerX);
					SmartDashboard.putNumber("CenterY", centerY);
					SmartDashboard.putNumber("Selected Cube Area", cubeArea);
					
				
	            }
	        }
	        else
	        {
	        	SmartDashboard.putNumber("Cube Candidates", 0);
	        }
	    });
	    visionThread.start();
	    
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
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
    
	public Object getImgLock() {
		return imgLock;
	}
	public double getCubeArea() {
		return cubeArea;
	}
	public double getCenterX() {
		return centerX;
	}
	public double getCenterY() {
		return centerY;
	}
	public int getCubeCandidates(){
		return cubeCandidates;
	}
	public int getImageWidth() {
		return IMG_WIDTH;
	}
	
	public int getImageHeight(){
		return IMG_HEIGHT;
	}
}
