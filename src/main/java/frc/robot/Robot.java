package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  public DifferentialDrive m_myRobot;
  public SpeedControllerGroup left_side;
  public SpeedControllerGroup right_side;
  private Joystick Xstick;

  @Override
  public void robotInit() {

    Xstick = new Joystick(0);


    left_side = new SpeedControllerGroup(new WPI_VictorSPX(6), new WPI_VictorSPX(4));
    right_side = new SpeedControllerGroup(new WPI_VictorSPX(2), new WPI_VictorSPX(5));
    m_myRobot = new DifferentialDrive(left_side, right_side);
  }

  @Override
  public void teleopPeriodic() {
    

    m_myRobot.arcadeDrive(Xstick.getY(), Xstick.getX());
  }

  
}