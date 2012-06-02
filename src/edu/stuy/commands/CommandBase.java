package edu.stuy.commands;

import edu.stuy.Devmode;
import edu.stuy.OI;
import edu.stuy.subsystems.*;
import edu.stuy.subsystems.fake.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Servo;
import edu.stuy.RobotMap;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use
 * CommandBase.exampleSubsystem
 *
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Drivetrain drivetrain;
    public static FakeFlywheel flywheel;
    public static FakeTusks tusks;
    public static FakeAcquirer acquirer;
    public static FakeConveyor conveyor;
    public static FakeStinger stinger;
    public static FakeBallLight ballLight;
    public static Servo servLeft;
    public static Servo servRight;


    static {
        drivetrain = new Drivetrain();
        conveyor = new FakeConveyor();
        flywheel = new FakeFlywheel();
        acquirer = new FakeAcquirer();
        servLeft = new Servo(RobotMap.SHIFTERLEFT_CHANNEL);
        servRight = new Servo(RobotMap.SHIFTERRIGHT_CHANNEL);
        if (!Devmode.DEV_MODE) {
            tusks = new FakeTusks();
        }
        stinger = new FakeStinger();
        ballLight = new FakeBallLight();
    }

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        if (!Devmode.DEV_MODE) {
            // Show what command your subsystem is running on the SmartDashboard
            SmartDashboard.putData(drivetrain);
            SmartDashboard.putData(flywheel);
            SmartDashboard.putData(tusks);
            SmartDashboard.putData(acquirer);
            SmartDashboard.putData(conveyor);
            SmartDashboard.putData(stinger);
            SmartDashboard.putData(ballLight);
        }
    }


    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
