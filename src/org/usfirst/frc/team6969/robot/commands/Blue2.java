package org.usfirst.frc.team6969.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team6969.robot.RobotMap;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Blue2 extends CommandGroup {

    public Blue2() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	/*
    	DifferentialDrive robotDrive = RobotMap.drive;
    	String gameData = null;
    	while(gameData == null || gameData.length() == 0)
    	{
    		gameData = DriverStation.getInstance().getGameSpecificMessage();
    	}
		
          if(gameData.length() > 0)
          {
        	  //-----------------------------------Jerk Foward
        	  robotDrive.tankDrive(1,1);
              try 
              { 
                      Thread.sleep(50); 
              } 
              catch(Exception e){} 
              robotDrive.tankDrive(0,0);
              
              //----------------------------------Start the actual auto boi
			  if(gameData.charAt(0) == 'L')
			  {
		    	this.addSequential(new AutoLowerForkStart());
		    	this.addSequential(new AutoCloseClaw());
		    	this.addSequential(new AutoRaiseFork());
				this.addSequential(new AutoFoward(60));
				this.addSequential(new AutoTurn(90, true));
				this.addSequential(new AutoFoward(36));
				this.addSequential(new AutoTurn(90, false));
				this.addSequential(new AutoFoward(60 + 23.56 ));
				this.addSequential(new AutoOpenClaw());

			  } else {
				  
		    	this.addSequential(new AutoLowerForkStart());
		    	this.addSequential(new AutoCloseClaw());
		    	this.addSequential(new AutoRaiseFork());
				this.addSequential(new AutoFoward(60));
				this.addSequential(new AutoTurn(90, false));
				this.addSequential(new AutoFoward(36));
				this.addSequential(new AutoTurn(90, true));
				this.addSequential(new AutoFoward(60 + 23.56 ));
				this.addSequential(new AutoOpenClaw());
			  }
          }*/
    }
}
