/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.stuy.commands.Autonomous;
import edu.stuy.commands.DriveManualJoystickControl;
import edu.stuy.util.VictorRobotDrive;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Kevin Wang
 */
public class Drivetrain extends Subsystem {
    public RobotDrive drive;
    
    Solenoid gearShiftLow;
    Solenoid gearShiftHigh;
    Gyro gyro;
    PIDController controller;
    double p,i,d,angle;

    public Compressor compressor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Drivetrain() {
        drive = new VictorRobotDrive(RobotMap.FRONT_LEFT_MOTOR, RobotMap.REAR_LEFT_MOTOR, RobotMap.FRONT_RIGHT_MOTOR, RobotMap.REAR_RIGHT_MOTOR);
        drive.setSafetyEnabled(false);

       // In "2nd" cRio slot, or 4th physical
       gearShiftLow = new Solenoid(2, RobotMap.GEAR_SHIFT_LOW); 
       gearShiftHigh = new Solenoid(2, RobotMap.GEAR_SHIFT_HIGH);
       gyro = new Gyro(RobotMap.GYRO_CHANNEL);
       
       
        compressor = new Compressor(RobotMap.PRESSURE_SWITCH_CHANNEL, RobotMap.COMPRESSOR_RELAY_CHANNEL);
        compressor.start();

        SmartDashboard.putDouble("Rotate P", p);
        SmartDashboard.putDouble("Rotate I", i);
        SmartDashboard.putDouble("Rotate D", d);
        SmartDashboard.putDouble("Rotate angle", angle);

        controller = new PIDController(p,i,d,gyro,new PIDOutput(){
            public void pidWrite(double output){
                drive.arcadeDrive(0, output);
            }
        },0.005);
        controller.setInputRange(-360.0, 360.0);
        controller.setTolerance(1/90. *100);
        controller.disable();

    }


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveManualJoystickControl());
    }

    public Command getDefaultCommand() {
        return super.getDefaultCommand();
    }

    public void tankDrive(double leftValue, double rightValue) {
        drive.tankDrive(leftValue, rightValue);
    }

    /**
     * Sets high gear if high is true; else low
     * @param high true if drivetrain should be in high gear
     */
    public void setGear(boolean high) {
        gearShiftHigh.set(high);
        gearShiftLow.set(!high);
    }

    /**
     * Gear state
     * @return true if in high gear; else false
     */
    public boolean getGear() {
        return gearShiftHigh.get();
    }
    
    public boolean setAngle() {
        controller.setPID(p, i, d);
        controller.enable();
        controller.setSetpoint(angle);
        return controller.onTarget();
    }
    
}
