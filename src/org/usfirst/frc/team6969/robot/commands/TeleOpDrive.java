package org.usfirst.frc.team6969.robot.commands;

import org.usfirst.frc.team6969.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TeleOpDrive extends Command {

	//public ADXL345_I2C accel;
    public TeleOpDrive() {
    	requires(Robot.driveTrain);
    	SmartDashboard.putData(Robot.driveTrain);
    	this.initialize(); // after the TeleopDrive object is declared
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    //	System.out.println("x:" + accel.getX() + "|y:" + accel.getY() + "|z:" + accel.getZ());
    	Robot.driveTrain.takeJoystickInputs(Robot.oi.getController());

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
