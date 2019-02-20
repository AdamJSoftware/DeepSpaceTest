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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Robot extends TimedRobot {
  public DifferentialDrive m_myRobot;
  public SpeedControllerGroup left_side;
  public SpeedControllerGroup right_side;
  public SpeedControllerGroup BallIntake;
  public WPI_VictorSPX FirstBallIntake;
  public WPI_VictorSPX SecondBallIntake;
  public WPI_VictorSPX BallLauncher;
  public Joystick Lstick;
  public Joystick Xstick;
  public Joystick Nstick;
  // Defines the joysticks: Lstick = Logitech; Xstick = standing/Xtreme
  public DigitalInput digi;
  public DigitalInput digi2;
  public DigitalInput digi3;
  public DigitalInput digi4;
  public DigitalInput digi5;
  public DigitalInput digi6;
  public boolean YValue;
  public boolean BValue;
  public boolean LBValue;
  public boolean TriggerValue;
  public boolean ThumbValue;
  public boolean InnerLValue;
  public boolean OuterLValue;
  public boolean InnerRValue;
  public boolean OuterRValue;
  public boolean firstdigi;
  public boolean secondigi;
  public boolean thirddigi;
  public boolean fourthdigi;
  public boolean BallSwitch;
  public boolean HatchSwitch;
  public boolean enabled;
  public boolean pressureSwitch;
  public boolean XValue;
  public boolean AValue;
  // public boolean NinB;
  // If the Nintendo controller is used, activate this variable
  public double speed1;
  public double speed2;
  public double speed3;
  public double speed4;
  public double speed5;
  public double speed6;
  public double speed7;
  public double gety;
  public double getx;
  public double BallSpeed;
  public double left_trig;
  public double right_trig;
  public double ScanValue;
  public double SliderValue;
  public double Reset;
  public Value grip;
  public TimeUnit TimeU;
  public Compressor c;
  public DoubleSolenoid soli;
  public DoubleSolenoid Gripper;
  public DoubleSolenoid Fingers;
  public DoubleSolenoid Elevator;
  public DoubleSolenoid Intake;
  // public int POVValue;
  // If the joystick controller is no longer used, activate this variable

  @Override
  public void robotInit() {

    SmartDashboard.putNumber("Robot Speed: ", 0);
    SmartDashboard.putBoolean("Limit Switch: ", true);

    c = new Compressor(0);
    // Creates compressor object

    digi = new DigitalInput(0); // DIO 0
    digi2 = new DigitalInput(1); // DIO 1
    digi3 = new DigitalInput(2); // DIO 2
    digi4 = new DigitalInput(3); // DIO 3
    digi5 = new DigitalInput(4); // DIO 4
    digi6 = new DigitalInput(5); // DIO 5
    // Defines the sensors

    Lstick = new Joystick(0); // Logitech Joystick
    Xstick = new Joystick(1); // Logitech Xtreme Joystick
    Nstick = new Joystick(2); // Nintendo Joystick
    // Declares controllers as a joystick

    FirstBallIntake = new WPI_VictorSPX(2);
    SecondBallIntake = new WPI_VictorSPX(7);
    BallLauncher = new WPI_VictorSPX(6);

    left_side = new SpeedControllerGroup(new WPI_VictorSPX(3), new WPI_VictorSPX(4));
    right_side = new SpeedControllerGroup(new WPI_VictorSPX(1), new WPI_VictorSPX(5));
    // Control which motors control which sides of the robot in terms of movement

    m_myRobot = new DifferentialDrive(left_side, right_side);
    // Assigns both sides to differential drive method

    BallIntake = new SpeedControllerGroup(FirstBallIntake, SecondBallIntake);

    speed1 = 0;
    speed2 = -0.46;
    speed3 = 0;
    speed4 = 0.46;
    speed5 = 0.5;
    speed6 = 0.5;
    speed7 = 0.5;
    BallSpeed = 0;
    // Do we need these defined speeds?!?!
    c.setClosedLoopControl(false);

    soli = new DoubleSolenoid(1, 0, 1);
    Gripper = new DoubleSolenoid(1, 2, 3);
    Fingers = new DoubleSolenoid(1, 4, 5);
    Elevator = new DoubleSolenoid(1, 6, 7);
    Intake = new DoubleSolenoid(0, 6, 7);
    // Define the double solenoids

  }

  @Override
  public void teleopPeriodic() {

    gety = Lstick.getY();
    getx = Lstick.getX();

    if (getx > -0.1 && getx < 0.1) {
      getx = 0;
    }

    if (gety > -0.1 && gety < 0.1) {
      gety = 0;
    }
    // Accounts for axis inaccuracy

    left_trig = Lstick.getRawAxis(2);
    right_trig = Lstick.getRawAxis(3);
    // Define the left and right triggers

    firstdigi = digi.get();
    secondigi = digi2.get();
    thirddigi = digi3.get();
    fourthdigi = digi4.get();
    BallSwitch = digi5.get();
    HatchSwitch = digi6.get();
    // Get a boolean value from the sensors and limit switches

    LBValue = Lstick.getRawButton(5);
    YValue = Lstick.getRawButton(4);
    BValue = Lstick.getRawButton(2);
    XValue = Lstick.getRawButton(3);
    AValue = Lstick.getRawButton(1);
    // POVValue = Lstick.getPOV();
    // Assigning the handheld Logitech controller's buttons

    TriggerValue = Xstick.getRawButton(1);
    ThumbValue = Xstick.getRawButton(2);
    InnerLValue = Xstick.getRawButton(3);
    InnerRValue = Xstick.getRawButton(4);
    OuterLValue = Xstick.getRawButton(5);
    OuterRValue = Xstick.getRawButton(6);
    SliderValue = Xstick.getRawAxis(3);
    // Assigning the Xtreme joystick's buttons

    ScanValue = Xstick.getRawAxis(2);
    // Defines the scan value as the standing joystick's Z axis

    pressureSwitch = c.getPressureSwitchValue();

    grip = Gripper.get();
    System.out.println("Gripper Value " + grip);

    // Define the pressure switch and gets it's value

    // Makes the B button launch the ball

    System.out.println(BallSwitch);
    System.out.println(HatchSwitch);

    if (XValue && !BallSwitch) {
      BallIntake.set(0.8);
      BallLauncher.set(0.4);
    } else if (XValue && BallSwitch) {
      BallIntake.set(0);
      BallLauncher.set(0);
    } else if (BValue) {
      BallLauncher.set(1);
    } else if (!XValue) {
      BallLauncher.set(0);
      BallIntake.set(0);
    }

    if (getx != 0 || gety != 0) {
      m_myRobot.arcadeDrive(Lstick.getY() * -1, Lstick.getX());
    } else if (left_trig != 0 || right_trig != 0) {
      System.out.println("MOVING ROBOT");
      m_myRobot.tankDrive(left_trig / 2, right_trig / 2);
    } else {

    }
    // Controls the robot's movement

    if (!pressureSwitch) {
      System.out.println("RUNNING COMPRESSOR");
      c.enabled();
      c.setClosedLoopControl(false);
    } else {
      System.out.println("COMPRESSOR FULL");
      c.setClosedLoopControl(true);
    }

    // If the A button is pressed. Run the compressor.

    if (getx != 0 || gety != 0)

    {
      m_myRobot.arcadeDrive(Lstick.getY() * -1, Lstick.getX());
    } else if (left_trig != 0 || right_trig != 0) {
      System.out.println("MOVING ROBOT");
      m_myRobot.tankDrive(right_trig, left_trig);
    } else {

    }
    // Basic robot movement with the axis as well as with the tiggers

    Reset = 0;

    if (Xstick.getRawButtonPressed(1)) { // change to TriggerValue after a test
      Reset++;
      if (Reset == 1) {
        System.out.println("Firing solenoid 1");
        Intake.set(Value.kForward);
        // Engage the first pneumatic
      } else if (Reset == 2)
        Intake.set(Value.kReverse);
    } else {

    }

    if (SliderValue == -1) {
      // if (TriggerValue) {
      // System.out.println("Firing solenoid 1");
      // Intake.set(Value.kForward);
      // // Engage the first pneumatic
      // } else {
      // Intake.set(Value.kReverse);
      // }

      if (ThumbValue) {
        if (!HatchSwitch) {
          System.out.println("Firing gripper");
          Gripper.set(Value.kReverse);
          // Engage the first pneumatic
        } else {
          Fingers.set(Value.kForward);
        }
      } else {
        Gripper.set(Value.kForward);
      }

      if (InnerLValue) {
        System.out.println("Fingers out");
        Fingers.set(Value.kForward);
        // Engage the third pneumatic
      } else {
        Fingers.set(Value.kReverse);
      }
      // Engage the fifth pneumatic

      if (InnerRValue) {
        System.out.println("Elevator up");
        Elevator.set(Value.kReverse);
        // Engage the elevator pneumatic
      } else {
        Elevator.set(Value.kForward);
      }

    } else

    {
      soli.set(Value.kOff);
      Gripper.set(Value.kOff);
      Fingers.set(Value.kOff);
      Elevator.set(Value.kOff);
      Intake.set(Value.kOff);
      // Turns off all solenoids if the standing joystick's slider is set to --
    }

    if (ScanValue > -0.1 && ScanValue < 0.1) {
      ScanValue = 0;
    } else {

    }
    // Account for the possible inaccuracy of the joystick's Z axis value

    if (ScanValue == 1) {
      if (thirddigi && !fourthdigi) {
        m_myRobot.arcadeDrive(0.5, 0);

      } else {
        System.out.println("Robot is moving");
        m_myRobot.arcadeDrive(0.1, 0.55);
      }
      // Scan routine RIGHT, based on the standing controller's Z axis value
    } else if (ScanValue == -1) {
      if (thirddigi && !fourthdigi) {
        m_myRobot.arcadeDrive(0.5, 0);
      } else {
        System.out.println("Robot is moving");
        m_myRobot.arcadeDrive(0.1, -0.55);
      }
      // Scan routine LEFT, based on the standing controller's Z axis value
    } else {

    }

    SmartDashboard.putNumber("Robot Speed: ", (getx + gety) / 2);
    SmartDashboard.putBoolean("Sensor1: ", digi.get());
    SmartDashboard.putBoolean("Sensor2: ", digi2.get());
    SmartDashboard.putBoolean("Sensor3: ", digi3.get());
    SmartDashboard.putBoolean("Sensor4: ", digi4.get());
    SmartDashboard.putBoolean("Ball Switch: ", BallSwitch);
    // SmartDashboard.putBoolean("Gripper: ", Gripper.get());

  }

  public void autonomousInit() {

  }

  public void autonomousPeriodic() {

  }
}