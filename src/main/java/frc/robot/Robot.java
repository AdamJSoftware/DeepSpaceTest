package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Robot extends TimedRobot {
  public DifferentialDrive m_myRobot;
  public SpeedControllerGroup left_side;
  public SpeedControllerGroup right_side;
  public double gety;
  public double getx;
  public Joystick Lstick;
  public Joystick Xstick;
  public DigitalInput digi;
  public DigitalInput digi2;
  public DigitalInput digi3;
  public DigitalInput digi4;
  public boolean YValue;
  public boolean BValue;
  public boolean TriggerValue;
  public boolean ThumbValue;
  public boolean InnerLValue;
  public boolean OuterLValue;
  public boolean InnerRValue;
  public boolean OuterRValue;
  public int POVValue;
  public WPI_VictorSPX m_1;
  public WPI_VictorSPX m_2;
  public double left_trig;
  public double right_trig;
  public boolean pressureSwitch;
  public boolean XValue;
  public boolean AValue;
  public double speed1;
  public double speed2;
  public double speed3;
  public double speed4;
  public double speed5;
  public double speed6;
  public double speed7;
  public boolean firstdigi;
  public boolean secondigi;
  public TimeUnit TimeU;
  public boolean thirddigi;
  public boolean fourthdigi;
  public Compressor c;
  public boolean enabled;
  public DoubleSolenoid solenoid;

  @Override
  public void robotInit() {

    Compressor c = new Compressor(0);

    c.setClosedLoopControl(true);

    digi = new DigitalInput(0); //
    digi2 = new DigitalInput(2); //
    digi3 = new DigitalInput(1); //
    digi4 = new DigitalInput(3); //
    // Define the sensors

    Lstick = new Joystick(0);
    Xstick = new Joystick(1);
    // Declares controllers as a joystick

    left_side = new SpeedControllerGroup(new WPI_VictorSPX(1), new WPI_VictorSPX(2));
    right_side = new SpeedControllerGroup(new WPI_VictorSPX(3), new WPI_VictorSPX(4));
    // Control which motors control which sides of the robot in terms of movement

    m_1 = new WPI_VictorSPX(1);
    m_2 = new WPI_VictorSPX(3);
    m_myRobot = new DifferentialDrive(left_side, right_side);

    speed1 = 0;
    speed2 = -0.46;
    speed3 = 0;
    speed4 = 0.46;
    speed5 = 0.5;
    speed6 = 0.5;
    speed7 = 0.5;
    // Assign a speed value for the sensor routine.

  }

  @Override
  public void teleopPeriodic() {

    solenoid = new DoubleSolenoid(0, 1);

    if (solenoid.get().equals(Value.kForward)) {
      solenoid.set(Value.kReverse);
    } else {
      solenoid.set(Value.kForward);
    }
    // Bad test program for the solenoids

    pressureSwitch = c.getPressureSwitchValue();

    System.out.println("Pressure switch value " + pressureSwitch);

    gety = Lstick.getY();
    // gety = gety * -1;
    getx = Lstick.getX();
    // getx = getx * -1;

    if (getx > -0.1 && getx < 0.1) {
      getx = 0;
    }

    if (gety > -0.1 && gety < 0.1) {
      gety = 0;
    }

    left_trig = Lstick.getRawAxis(2);
    right_trig = Lstick.getRawAxis(3);
    // Define the left and right triggers

    firstdigi = digi.get();
    secondigi = digi2.get();
    thirddigi = digi3.get();
    fourthdigi = digi4.get();
    // Get a boolean value from the sensors

    // System.out.println("DIGI 1 " + firstdigi);
    // System.out.println("DIGI 2 " + secondigi);
    // System.out.println("DIGI 3 " + thirddigi);
    // System.out.println("DIGI 4 " + fourthdigi);

    YValue = Lstick.getRawButton(4);
    BValue = Lstick.getRawButton(3);
    XValue = Lstick.getRawButton(2);
    AValue = Lstick.getRawButton(1);
    POVValue = Lstick.getPOV();
    System.out.println("POVVALUE IS " + POVValue);
    // Assigning the console controller's buttons

    TriggerValue = Xstick.getRawButton(1);
    ThumbValue = Xstick.getRawButton(2);
    InnerLValue = Xstick.getRawButton(3);
    OuterLValue = Xstick.getRawButton(5);
    InnerRValue = Xstick.getRawButton(4);
    OuterRValue = Xstick.getRawButton(6);
    // Assigning the standing joystick's buttons

    System.out.println(BValue);
    System.out.println(XValue);
    // Output the values of the X and B buttons

    if (getx != 0 || gety != 0) {
      m_myRobot.arcadeDrive(Lstick.getY() * -1, Lstick.getX());
    } else if (left_trig != 0 || right_trig != 0) {
      System.out.println("MOVING ROBOT");
      m_myRobot.tankDrive(left_trig, right_trig);
    } else if (AValue) {
		pressureSwitch = c.getPressureSwitchValue();
		if (pressureSwitch){
			c.enabled();
		}
		else{
		System.out.println("Finisehd compressing"}
		}
      System.out.println("Firing solenoid 1");
      // Engage the first pneumatic
    } else if (YValue) {
      System.out.println("Firing solenoid 2");
      // Engage the second pneumatic
    } else if (InnerLValue) {
      System.out.println("Firing solenoid 3");
      // Engage the third pneumatic
    } else if (OuterLValue) {
      System.out.println("Firing solenoid 4");
      // Engage the fourth pneumatic
    } else if (InnerRValue) {
      System.out.println("Firing solenoid 5");
      // Engage the fifth pneumatic
    } else if (OuterRValue) {
      System.out.println("Firing solenoid 6");
      // Engage the sixth pneumatic
      // } else if (POVValue == 0) {
      // System.out.println("Firing solenoid 3");
      // // Engage the third pneumatic
      // } else if (POVValue == 90) {
      // System.out.println("Firing solenoid 4");
      // // Engage the fourth pneumatic
      // } else if (POVValue == 180) {
      // System.out.println("Firing solenoid 5");
      // // Engage the fifth pneumatic
      // } else if (POVValue == 270) {
      // System.out.println("Firing solenoid 6");
      // // Engage the sixth pneumatic

      // Above code only to be activated if stand-up joystick is no longer used
    } else {
      if (BValue == true) {
        if (firstdigi && secondigi) {
          m_myRobot.tankDrive(speed6, speed5);
          // Scan routine right
        } else {
          if (firstdigi || secondigi) {
            m_myRobot.arcadeDrive(speed7, speed2);
          } else {
            System.out.println("Robot is moving");
            m_myRobot.arcadeDrive(speed1, speed2);
          }

        }
      }

      if (XValue == true) {
        if (firstdigi && secondigi) {
          m_myRobot.tankDrive(speed6, speed5);
          // Scan routine left
        } else {
          if (firstdigi || secondigi) {
            m_myRobot.arcadeDrive(speed7, speed4);
          } else {
            System.out.println("Robot is moving");
            m_myRobot.arcadeDrive(speed3, speed4);
          }

        }

      }

    }
  }
}