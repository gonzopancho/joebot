package edu.stuy;

import edu.stuy.commands.DrivetrainSetGear;
import edu.stuy.commands.ShooterShoot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;
import edu.stuy.subsystems.Shooter;

public class OI {
    private Joystick leftStick;
    private Joystick rightStick;
    public double distanceInches;
    
    // Process operator interface input here.
    
    public OI() {
        leftStick = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
        rightStick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);
        double distanceInches = Shooter.distances[Shooter.FENDER_INDEX];

        if (!Devmode.DEV_MODE) {
            new JoystickButton(leftStick, 1).whileHeld(new ShooterShoot(distanceInches));
            new JoystickButton(leftStick, 2).whenPressed(new DrivetrainSetGear(false));
            new JoystickButton(rightStick, 2).whenPressed(new DrivetrainSetGear(true));
        }
    }
    
    public Joystick getLeftStick() {
        return leftStick;
    }
    
    public Joystick getRightStick() {
        return rightStick;
    }

    public boolean isConveyorStopButtonPressed() {
        return false;
    }
}

