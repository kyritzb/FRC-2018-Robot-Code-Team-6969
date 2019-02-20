/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6969.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	/*
	 * F310 controller button mappings
	 * 1: X
	 * 2: A
	 * 3: B
	 * 4: Y
	 * 5: Left Bumper
	 * 6: Right Bumper
	 * 7: Back Left Trigger
	 * 8: Back Right Trigger
	 * 9: Back
	 * 10: Start
	 * 
	 */
	
	/*
	 * F310 controller Joystick axis mappings
	 * 1: Left Joystick Y axis
	 * 5: Right Joystick Y Axis
	 */
	//private XboxController controller = new XboxController(0); // Logitech
	
	//private XboxController controller = new XboxController(2); // PS4 plugged in
	
	private XboxController controller = new XboxController(1); // PS4 wireless with DS4
	/*
    public Button xButton = new JoystickButton(controller, 1),
    		aButton = new JoystickButton(controller, 2),
    		bButton = new JoystickButton(controller, 3),
    		yButton = new JoystickButton(controller, 4),
    		LBumper = new JoystickButton(controller, 5),
    		RBumper = new JoystickButton(controller, 6),
    		LTrigger = new JoystickButton(controller, 7),
    		RTrigger = new JoystickButton(controller, 8),
    		backButton = new JoystickButton(controller, 9),
    		startButton = new JoystickButton(controller, 10);
	*/
	//PS4 controller running DS4 for windows
	public Button xButton = new JoystickButton(controller, 1),
			circleButton = new JoystickButton(controller, 2),
			squareButton = new JoystickButton(controller, 3),
			triangleButton = new JoystickButton(controller, 4),
			leftBumper = new JoystickButton(controller,5),
			rightBumper = new JoystickButton(controller, 6),
			shareButton = new JoystickButton(controller, 7),
			optionButton = new JoystickButton(controller, 8),
			leftJoystickButton = new JoystickButton(controller, 9),
			rightJoystickButton = new JoystickButton(controller, 10);
    
    public OI() {

  			
    }
    
    public XboxController getController() {
        return controller;
    }
    
        
}
