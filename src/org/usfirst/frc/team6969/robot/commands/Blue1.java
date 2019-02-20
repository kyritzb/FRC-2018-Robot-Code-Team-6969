package org.usfirst.frc.team6969.robot.commands;

import org.usfirst.frc.team6969.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


/**
 *
 */
public class Blue1 extends CommandGroup {

    public Blue1() {
    	/*
    	//This is Red3
    	DifferentialDrive robotDrive = RobotMap.drive;
      	String gameData = null;
    	while(gameData == null || gameData.length() == 0)
    	{
    		gameData = DriverStation.getInstance().getGameSpecificMessage();
    	}
    	
          if(gameData.length() > 0)
          {
				  System.out.println("1 Left");

	              /*
	              robotDrive.tankDrive(0.8, 0.8);
				    try 
		              { 
		                      Thread.sleep(5000); 
		              } 
		              
		              catch(Exception e){} 
				    robotDrive.tankDrive(0,0); 
				    */
    			this.addSequential(new boi());
			    this.addSequential(new AutoLowerForkStart());
			   
			    this.addSequential(new AutoCloseClaw());
			    this.addSequential(new AutoRaiseFork());


	             
	              /*
			    robotDrive.tankDrive(0.8, 0.8);
			    try 
	              { 
	                      Thread.sleep(200); 
	              } 
	              
	              catch(Exception e){} 
	              
	              robotDrive.tankDrive(0,0);*/
			    
			    
/*

          }
    */
                
    }
}
