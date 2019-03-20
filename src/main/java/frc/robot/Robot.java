package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Robot extends TimedRobot {
  public boolean BValue; // Returns value of the B button on the Logitech Gamepad
  public boolean XValue; // Returns value of the X button on the Logitech Gamepad
  public boolean TriggerValue;
  public boolean ThumbValue;
  public boolean InnerLValue;
  public boolean OuterLValue;
  public boolean StopValue;
  public boolean HatchButton;
  public boolean FirstSensor;
  public boolean SecondSensor;
  public boolean ThirdSensor;
  public boolean FourthSensor;
  public boolean FifthSensor;
  public boolean SixthSensor;
  public boolean switchpressed;
  public boolean BallSwitch;
  public boolean HatchSwitch;
  public boolean ElevatorOneSwitch;
  public boolean ElevatorTwoSwitch;
  public boolean enabled;
  public boolean pressureSwitch;
  public boolean gripperPressed;
  public boolean thumbPressed;
  public boolean triggerPressed;
  public boolean innerLPressed;
  public boolean stopPressed;
  public boolean HatchValue;
  public boolean firstLevel;
  public boolean secondLevel;
  public boolean thirdLevel;
  public boolean HatchPressed;
  public Compressor c;
  public DifferentialDrive m_myRobot; // Combines both motor groups to allow driving via joystick
  public DigitalInput digi0; // Digital Input - 0
  public DigitalInput digi1; // Digital Input - 1
  public DigitalInput digi2; // Digital Input - 2
  public DigitalInput digi3; // Digital Input - 3
  public DigitalInput digi4; // Digital Input - 4
  public DigitalInput digi5; // Digital Input - 5
  public DigitalInput digi6; // Digital Input - 6
  public DigitalInput digi7; // Digital Input - 7
  public DigitalInput digi8; // Digital Input - 8
  public DigitalInput digi9; // Digital Input - 9
  public double gety; // Pull Y axis value of drive gamepad's joystick
  public double getx; // Pull X axis value of drive gamepad's joystick
  public double left_trig; // Pull the left trigger's axis value
  public double right_trig; // Pull the right trigger's axis value
  public double ScanValue;
  public double SliderValue;
  public DoubleSolenoid Gripper; // Solenoid that controls the gripper pneumatic
  public DoubleSolenoid Fingers; // Solenoid that controls the gripper's fingers pneumatic
  public DoubleSolenoid Intake; // Solenoid that controls the intake arm pneumatic
  public Joystick Lstick; // Logitech Gamepad
  public Joystick Xstick; // Logitech Extreme
  public Preferences prefs;
  public SpeedControllerGroup left_side; // Combines both motors on the left side of the robot into one group
  public SpeedControllerGroup right_side; // Combines both motors on the right side of the robot into one group
  public SpeedControllerGroup BallIntake; // Combines both ball intake motors into a group
  public String FingerString;
  public Value finger;
  public WPI_VictorSPX FirstBallIntake; // First ball intake motor
  public WPI_VictorSPX SecondBallIntake; // Second ball intake motor
  public WPI_VictorSPX BallLauncher; // Ball Launcher motor
  public WPI_VictorSPX Elevator; // Elevator motor
  public WPI_TalonSRX Drive1; // First drivetrain motor
  public WPI_TalonSRX Drive2; // Second drivetrain motor
  public WPI_TalonSRX Drive3; // Third drivetrain motor
  public WPI_TalonSRX Drive4; // Fourth drivetrain motor

  @Override
  public void robotInit() {
    HatchValue = false;

    prefs = Preferences.getInstance();

    c = new Compressor(0);
    // Creates compressor object

    digi0 = new DigitalInput(0); // DIO 0
    digi1 = new DigitalInput(1); // DIO 1
    digi2 = new DigitalInput(2); // DIO 2
    digi3 = new DigitalInput(3); // DIO 3
    digi4 = new DigitalInput(4); // DIO 4
    digi5 = new DigitalInput(5); // DIO 5
    digi6 = new DigitalInput(6); // DIO 6
    digi7 = new DigitalInput(7); // DIO 7
    digi8 = new DigitalInput(8); // DIO 8
    digi9 = new DigitalInput(9); // DIO 9
    // Defines the digital inputs (for sensors/switches)

    Lstick = new Joystick(0); // Logitech Gamepad
    Xstick = new Joystick(1); // Logitech Xtreme Joystick
    // Declares controllers

    Drive1 = new WPI_TalonSRX(1);
    Drive2 = new WPI_TalonSRX(2);
    Drive3 = new WPI_TalonSRX(3);
    Drive4 = new WPI_TalonSRX(4);
    // New drive Talons

    Elevator = new WPI_VictorSPX(5);
    BallLauncher = new WPI_VictorSPX(6);
    FirstBallIntake = new WPI_VictorSPX(7);
    SecondBallIntake = new WPI_VictorSPX(8);
    BallIntake = new SpeedControllerGroup(FirstBallIntake, SecondBallIntake);
    // Victor 7 & 8 -> Intake motors, create a SpeedControllerGroup for them

    left_side = new SpeedControllerGroup(Drive1, Drive3);
    right_side = new SpeedControllerGroup(Drive2, Drive4);
    m_myRobot = new DifferentialDrive(left_side, right_side);
    // Control which motors control which sides of the robot in terms of movement
    // Assign both of these sides to differential drive method

    c.setClosedLoopControl(false);

    Gripper = new DoubleSolenoid(1, 2, 3);
    Fingers = new DoubleSolenoid(1, 4, 5);
    Intake = new DoubleSolenoid(0, 6, 7);

    if (!prefs.containsKey("Scan Speed Hatch")) {
      prefs.putDouble("Scan Speed Hatch", 0.0);
    }
  }

  @Override
  public void teleopPeriodic() {
    HatchValue = false;

    gety = Lstick.getY();
    getx = Lstick.getX();

    finger = Fingers.get();

    if (getx > -0.1 && getx < 0.1) {
      getx = 0;
    }

    if (gety > -0.1 && gety < 0.1) {
      gety = 0;
    }
    // Accounts for axis inaccuracy (deadband)

    left_trig = Lstick.getRawAxis(2);
    right_trig = Lstick.getRawAxis(3);
    // Define the left and right triggers

    if (ScanValue != 1 || ScanValue != -1) {
      if (getx != 0 || gety != 0) {
        m_myRobot.arcadeDrive(Lstick.getY() * -1, Lstick.getX());
      } else if (left_trig != 0 || right_trig != 0) {
        m_myRobot.tankDrive(left_trig / 2, right_trig / 2);
      } else {

      }
    }
    // Controls the robot's movement

    FirstSensor = digi0.get();
    SecondSensor = digi1.get();
    ThirdSensor = digi2.get();
    FourthSensor = digi3.get();
    FifthSensor = digi4.get();
    SixthSensor = digi5.get();
    BallSwitch = digi6.get();
    HatchSwitch = digi7.get();
    ElevatorOneSwitch = digi8.get();
    ElevatorTwoSwitch = digi9.get();

    BValue = Lstick.getRawButton(2);
    XValue = Lstick.getRawButton(3);
    // Assigning the handheld Logitech controller's buttons (B & X)

    // TriggerValue = Xstick.getRawButtonPressed(1);
    triggerPressed = Xstick.getRawButton(1);
    ThumbValue = Xstick.getRawButtonPressed(2);
    InnerLValue = Xstick.getRawButtonPressed(3);
    HatchButton = Xstick.getRawButton(4);
    ScanValue = Xstick.getRawAxis(2);
    SliderValue = Xstick.getRawAxis(3);
    firstLevel = Xstick.getRawButton(11);
    secondLevel = Xstick.getRawButtonPressed(9);
    thirdLevel = Xstick.getRawButton(7);
    StopValue = Xstick.getRawButton(8);
    // Assigning variables to the Xtreme joystick's buttons and axes

    if (StopValue && stopPressed) {
      stopPressed = false;
    } else if (StopValue && !stopPressed) {
      stopPressed = true;
    }

    if (firstLevel && !ElevatorOneSwitch) {
      System.out.println("Running elevator to 1");
      Elevator.set(0.4);
    } else if (secondLevel && ElevatorOneSwitch && ElevatorTwoSwitch) {
      System.out.println("Running elevator from 1 to 2");
      Elevator.set(-0.4);
    } else if (secondLevel && !ElevatorOneSwitch && !ElevatorTwoSwitch) {
      System.out.println("Running elevator from 3 to 2");
      Elevator.set(0.4);
    } else if (thirdLevel) {
      System.out.println("Running elevator to 3");
      Elevator.set(-0.4);
    } else if (thirdLevel && !ElevatorOneSwitch && !ElevatorTwoSwitch) {
      Elevator.stopMotor();
    } else if (stopPressed) {
      Elevator.stopMotor();
      // Stop the elevator in the case of an error to avoid damage
    } else if (ElevatorOneSwitch && ElevatorTwoSwitch) {
      Elevator.set(0);
    } else {
      Elevator.set(0);
    }

    // if (XValue && !BallSwitch) {
    // BallIntake.set(0.8);
    // BallLauncher.set(0.3);
    // } else if (XValue && BallSwitch) {
    // BallIntake.set(0);
    // BallLauncher.set(0);
    // } else if (BValue) {
    // BallLauncher.set(1);
    // } else if (!XValue && !BallSwitch) {
    // BallLauncher.set(0);
    // BallIntake.set(0);
    // }
    // Switch currently not connected, PRIORITY

    if (XValue) {
      BallIntake.set(0.8);
      BallLauncher.set(0.3);
    } else if (!XValue) {
      BallIntake.set(0);
      BallLauncher.set(0);
    } else if (BValue) {
      BallLauncher.set(1);
    } else if (!BValue) {
      BallLauncher.set(0);
    }
    // Controls for picking up and launching Ball

    if (SliderValue != 1) {
      if (ThumbValue && thumbPressed) {
        thumbPressed = false;
      } else if (ThumbValue && !thumbPressed) {
        thumbPressed = true;
      }

      if (InnerLValue && innerLPressed) {
        innerLPressed = false;
      } else if (InnerLValue && !innerLPressed) {
        innerLPressed = true;
      }
      // Allow toggles for Xtreme stick's controls

      if (triggerPressed) {
        Gripper.set(Value.kReverse);
        // Engage the Gripper pneumatic
      } else {
        Gripper.set(Value.kForward);
      }
      // Set to hold instead of toggle to avoid accidental damage

      if (HatchSwitch) {
        HatchValue = true;
        if (HatchValue) {
          if (Fingers.get() == Value.kForward) {
            Fingers.set(Value.kReverse);
            System.out.println("RUNNING FINGERS IN");
            HatchValue = false;
            Timer.delay(0.75);
          } else {
            Fingers.set(Value.kForward);
            System.out.println("RUNNING FINGERS OUT");
            HatchValue = false;
            Timer.delay(0.75);
          }
        }
      }
      // HatchValue -> false by default; set back to false should prevent the loop

      if (innerLPressed) {
        Intake.set(Value.kForward);
        // Engage the Ball Intake pneumatic
      } else {
        Intake.set(Value.kReverse);
      }

      if (thumbPressed) {
        Fingers.set(Value.kReverse);
        // Engage the Fingers pneumatic
      } else {
        Fingers.set(Value.kForward);
      }
    } else {
      Gripper.set(Value.kOff);
      Intake.set(Value.kOff);
      Fingers.set(Value.kOff);
      // Turns off all solenoids if the standing joystick's slider is set to off
    }

    if (ScanValue == 1 && !HatchButton) {
      if (FirstSensor && SecondSensor) {
        m_myRobot.arcadeDrive(0.5, 0);
      } else {
        m_myRobot.arcadeDrive(0.1, 0.55);
      }
      // Scan routine RIGHT, based on the standing controller's Z axis value
    } else if (ScanValue == -1 && !HatchButton) {
      if (FirstSensor && SecondSensor) {
        m_myRobot.arcadeDrive(0.5, 0);
      } else {
        m_myRobot.arcadeDrive(0.1, -0.55);
      }
      // Scan routine LEFT, based on the standing controller's Z axis value
    } else if (ScanValue == 1 && HatchButton) {
      if ((ThirdSensor || FourthSensor) && (FifthSensor || SixthSensor)) {
        m_myRobot.arcadeDrive(0.5, 0);
      } else {
        m_myRobot.arcadeDrive(0.1, 0.55);
      }
    } else if (ScanValue == -1 && HatchButton) {
      if ((ThirdSensor || FourthSensor) && (FifthSensor || SixthSensor)) {
        m_myRobot.arcadeDrive(0.5, 0);
      } else {
        m_myRobot.arcadeDrive(0.1, -0.55);
      }
    } else {

    }

    if (finger == Value.kForward) {
      FingerString = "Fingers out";
    } else {
      FingerString = "Fingers in";
    }

    SmartDashboard.putBoolean("Sensor1: ", digi0.get());
    SmartDashboard.putBoolean("Sensor2: ", digi1.get());
    SmartDashboard.putBoolean("Sensor3: ", digi2.get());
    SmartDashboard.putBoolean("Sensor4: ", digi3.get());
    SmartDashboard.putBoolean("Sensor5: ", digi4.get());
    SmartDashboard.putBoolean("Sensor6: ", digi5.get());
    SmartDashboard.putBoolean("Ball Switch: ", digi6.get());
    SmartDashboard.putBoolean("Hatch Switch: ", digi7.get());
    SmartDashboard.putBoolean("Elevator Switch 1: ", ElevatorOneSwitch);
    SmartDashboard.putBoolean("Elevator Switch 2: ", ElevatorTwoSwitch);
    SmartDashboard.putBoolean("Elevator Stop: ", stopPressed);
    SmartDashboard.putString("Fingers: ", FingerString);
  }

  @Override
  public void autonomousPeriodic() {
    teleopPeriodic();
    // Do we need to add RobotInit?
  }

}