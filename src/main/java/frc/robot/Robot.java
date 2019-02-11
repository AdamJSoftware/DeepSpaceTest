package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends TimedRobot {
  public DifferentialDrive m_myRobot;
  public SpeedControllerGroup left_side;
  public SpeedControllerGroup right_side;
  public double gety;
  public double getx;
  public Joystick Xstick;
  public DigitalInput digi;
  public DigitalInput digi2;
  public DigitalInput digi3;
  public DigitalInput digi4;
  public boolean BValue;
  public WPI_VictorSPX m_1;
  public WPI_VictorSPX m_2;
  public double left_trig;
  public double right_trig;
  public boolean XValue;
  public double speed1;
  public double speed2;
  public double speed3;
  public double speed4;
  public double speed5;
  public double speed6;
  public boolean firstdigi;
  public boolean secondigi;
  public TimeUnit TimeU;
  public boolean thirddigi;
  public boolean fourthdigi;

  @Override
  public void robotInit() {

    digi = new DigitalInput(0); // 0
    digi2 = new DigitalInput(2); // 2
    digi3 = new DigitalInput(1); // 1
    digi4 = new DigitalInput(3); // 3

    Xstick = new Joystick(0);

    left_side = new SpeedControllerGroup(new WPI_VictorSPX(1));
    right_side = new SpeedControllerGroup(new WPI_VictorSPX(3));

    m_1 = new WPI_VictorSPX(1);
    m_2 = new WPI_VictorSPX(3);
    m_myRobot = new DifferentialDrive(m_1, m_2);

    speed1 = -0.3;
    speed2 = -0.8;
    speed3 = -0.3;
    speed4 = 0.8;
    speed5 = 0.6;
    speed6 = 0.6;

  }

  @Override
  public void teleopPeriodic() {

    gety = Xstick.getY();
    // gety = gety * -1;
    getx = Xstick.getX();
    // getx = getx * -1;

    if (getx > -0.1 && getx < 0.1) {
      getx = 0;
    }

    if (gety > -0.1 && gety < 0.1) {
      gety = 0;
    }

    left_trig = Xstick.getRawAxis(2);
    right_trig = Xstick.getRawAxis(3);

    firstdigi = digi.get();
    secondigi = digi2.get();
    thirddigi = digi3.get();
    fourthdigi = digi4.get();

    System.out.println("DIGI 1 " + firstdigi);
    System.out.println("DIGI 2 " + secondigi);
    System.out.println("DIGI 3 " + thirddigi);
    System.out.println("DIGI 4 " + fourthdigi);

    BValue = Xstick.getRawButton(3);
    XValue = Xstick.getRawButton(2);

    System.out.println(BValue);
    System.out.println(XValue);

    left_trig = left_trig / 1.5;
    // left_trig = left_trig + 20;
    right_trig = right_trig / 1.5;
    // right_trig = right_trig + 20;

    // System.out.println("Trigger 1 " + left_trig);
    // System.out.println("Trigger 2 " + right_trig);

    // System.out.println("GETX " + getx);
    // System.out.println("GETY " + gety);

    if (getx != 0 || gety != 0) {
      m_myRobot.arcadeDrive(Xstick.getY(), Xstick.getX() * -1);
    } else if (left_trig != 0 || right_trig != 0) {
      System.out.println("MOVING ROBOT");
      m_myRobot.tankDrive(left_trig, right_trig);
    } else {
      if (BValue == true) {
        if (firstdigi && secondigi) {
          m_myRobot.tankDrive(speed6, speed5);
        } else {
          System.out.println("Robot is moving");
          m_myRobot.arcadeDrive(speed1, speed2);
        }
      }

      if (XValue == true) {
        if (firstdigi && secondigi) {
          System.out.println("FOUND WHITE TAPE");
          m_myRobot.tankDrive(speed6, speed5);
        } else {
          System.out.println("ROBOT IS MOVING");
          m_myRobot.arcadeDrive(speed3, speed4);
        }

      }

    }
  }
}