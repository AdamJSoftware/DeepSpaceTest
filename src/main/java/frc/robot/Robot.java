package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Robot extends TimedRobot {

  public DifferentialDrive robotDrive; // Combines both motor groups to allow driving via joystick
  public Joystick lStick; // Logitech Gamepad
  public double leftj;
  public double rightj;
  public SpeedControllerGroup leftSide; // Combines both motors on the left side of the robot into one group
  public SpeedControllerGroup rightSide; // Combines both motors on the right side of the robot into one group
  public WPI_TalonSRX drive1; // First drivetrain motor
  public WPI_TalonSRX drive2; // Second drivetrain motor
  public WPI_TalonSRX drive3; // Third drivetrain motor
  public WPI_TalonSRX drive4; // Fourth drivetrain motor

  @Override
  public void robotInit() {

    lStick = new Joystick(0); // Logitech Gamepad

    drive1 = new WPI_TalonSRX(1);
    drive2 = new WPI_TalonSRX(2);
    drive3 = new WPI_TalonSRX(3);
    drive4 = new WPI_TalonSRX(4);

    leftSide = new SpeedControllerGroup(drive1, drive3);
    rightSide = new SpeedControllerGroup(drive2, drive4);
    robotDrive = new DifferentialDrive(leftSide, rightSide);
  }

  @Override
  public void teleopPeriodic() {
    robotDrive.arcadeDrive(lStick.getY(), lStick.getX());

  }

  @Override
  public void autonomousPeriodic() {
    System.out.println(lStick.getRawAxis(3));
    if (lStick.getRawAxis(3) == -1) {
      NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
      NetworkTableEntry tx = table.getEntry("tx");
      NetworkTableEntry ty = table.getEntry("ty");
      NetworkTableEntry ta = table.getEntry("ta");

      // read values periodically
      double x = tx.getDouble(0.0);
      double y = ty.getDouble(0.0);
      double area = ta.getDouble(0.0);
      if (area == 0) {
        robotDrive.arcadeDrive(0, 0.4);
      } else {
        if (x < 0) {
          robotDrive.arcadeDrive(-0.67, -0.4);
        } else {
          robotDrive.arcadeDrive(-0.67, 0.4);
        }
      }
    } else {
      robotDrive.arcadeDrive(lStick.getY(), lStick.getX());
    }

  }

}