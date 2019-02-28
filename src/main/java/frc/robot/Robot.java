package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import java.util.concurrent.TimeUnit;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Robot extends TimedRobot {
  public DifferentialDrive m_myRobot;
  public SpeedControllerGroup left_side;
  public SpeedControllerGroup right_side;
  public SpeedControllerGroup BallIntake; // Combines both ball intakes to a group
  public WPI_VictorSPX FirstBallIntake; // Second ball intake motor
  public WPI_VictorSPX SecondBallIntake; // First ball intake motor
  public WPI_VictorSPX BallLauncher; // Ball Launch motor
  public Joystick Lstick; // Logitech Gamepad
  public Joystick Xstick; // Logitech Extreme Stick
  public DigitalInput digi0; // Digital Input - 0
  public DigitalInput digi1; // Digital Input - 1
  public DigitalInput digi2; // Digital Input - 2
  public DigitalInput digi3; // Digital Input - 3
  public DigitalInput digi4; // Digital Input - 4
  public DigitalInput digi5; // Digital Input - 5
  public DigitalInput digi6; // Digital Input - 6
  public DigitalInput digi7; // Digital Input - 7
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
  public boolean fifthdigi;
  public boolean sixdigi;
  public boolean BallSwitch;
  public boolean HatchSwitch;
  public boolean enabled;
  public boolean pressureSwitch;
  public boolean XValue;
  public boolean AValue;
  public boolean triggerPressed;
  public boolean thumbPressed;
  public boolean innerLPressed;
  public boolean innerRPressed;
  public boolean HatchButton;
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
  public Value grip;
  public TimeUnit TimeU;
  public Compressor c;
  public DoubleSolenoid soli;
  public DoubleSolenoid Gripper;
  public DoubleSolenoid Fingers;
  public DoubleSolenoid Elevator;
  public DoubleSolenoid Intake;

  private JoystickButton testButton;
  // public int POVValue;
  // If the joystick controller is no longer used, activate this variable

  @Override
  public void robotInit() {

    c = new Compressor(0);
    // Creates compressor object

    digi0 = new DigitalInput(0); // DIO 0
    digi1 = new DigitalInput(1); // DIO 1
    digi2 = new DigitalInput(2); // DIO 2
    digi3 = new DigitalInput(3); // DIO 3
    digi4 = new DigitalInput(4); // DIO 4
    digi5 = new DigitalInput(5); // DIO 5
    digi6 = new DigitalInput(6);
    digi7 = new DigitalInput(7);
    // Defines the sensors

    Lstick = new Joystick(0); // Logitech Joystick
    Xstick = new Joystick(1); // Logitech Xtreme Joystick
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

    testButton = new JoystickButton(Xstick, 7);

    testButton.whileHeld(new Command() {

      protected void execute() {
        System.out.println("Test Command Execute");
      }

      @Override
      protected boolean isFinished() {
        return false;
      }
    });
  }

  @Override
  public void teleopPeriodic() {

    HatchButton = Xstick.getRawButton(6);

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

    firstdigi = digi0.get();
    secondigi = digi1.get();
    thirddigi = digi2.get();
    fourthdigi = digi3.get();
    fifthdigi = digi4.get();
    sixdigi = digi5.get();
    BallSwitch = digi6.get();
    HatchSwitch = digi7.get();

    LBValue = Lstick.getRawButton(5);
    YValue = Lstick.getRawButton(4);
    BValue = Lstick.getRawButton(2);
    XValue = Lstick.getRawButton(3);
    AValue = Lstick.getRawButton(1);
    // Assigning the handheld Logitech controller's buttons

    TriggerValue = Xstick.getRawButtonPressed(1);
    ThumbValue = Xstick.getRawButtonPressed(2);
    InnerLValue = Xstick.getRawButtonPressed(3);
    InnerRValue = Xstick.getRawButtonPressed(4);
    OuterLValue = Xstick.getRawButton(5);
    OuterRValue = Xstick.getRawButton(6);
    SliderValue = Xstick.getRawAxis(3);
    // Assigning the Xtreme joystick's buttons

    ScanValue = Xstick.getRawAxis(2);
    // Defines the scan value as the standing joystick's Z axis

    pressureSwitch = c.getPressureSwitchValue();

    grip = Gripper.get();

    // Define the pressure switch and gets it's value

    // Makes the B button launch the ball

    if (XValue && BallSwitch) {
      BallIntake.set(0.8);
      BallLauncher.set(0.3);
    } else if (XValue && !BallSwitch) {
      BallIntake.set(0);
      BallLauncher.set(0);
    } else if (BValue) {
      BallLauncher.set(1);
    } else if (!XValue && BallSwitch) {
      BallLauncher.set(0);
      BallIntake.set(0);
    }

    if (getx != 0 || gety != 0) {
      m_myRobot.arcadeDrive(Lstick.getY() * -1, Lstick.getX());
    } else if (left_trig != 0 || right_trig != 0) {
      // System.out.println("MOVING ROBOT");
      m_myRobot.tankDrive(left_trig / 2, right_trig / 2);
    } else {

    }
    // Controls the robot's movement

    if (!pressureSwitch) {
      c.enabled();
      c.setClosedLoopControl(false);
    } else {
      c.setClosedLoopControl(true);
    }

    // If the A button is pressed. Run the compressor.
    // Basic robot movement with the axis as well as with the tiggers

    if (SliderValue == 1) {
      if (TriggerValue && triggerPressed) {
        triggerPressed = false;
      } else if (TriggerValue && !triggerPressed) {
        triggerPressed = true;
      }

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

      if (InnerRValue && innerRPressed) {
        innerRPressed = false;
      } else if (InnerRValue && !innerRPressed) {
        innerRPressed = true;
      }

      if (triggerPressed) {
        System.out.println("Firing solenoid 1");
        Gripper.set(Value.kReverse);
        // Engage the Intake pneumatic
      } else {
        Gripper.set(Value.kForward);
      }

      if (thumbPressed) {
        System.out.println("Firing gripper");
        Fingers.set(Value.kForward);
        // Engage the Gripper pneumatic
      } else {
        Fingers.set(Value.kReverse);
      }

      if (innerLPressed) {
        System.out.println("Fingers out");
        Intake.set(Value.kForward);
        // Engage the Gripper Fingers pneumatic
      } else {
        Intake.set(Value.kReverse);
      }

      if (innerRPressed) {
        System.out.println("Elevator up");
        Elevator.set(Value.kReverse);
        // Engage the Elevator pneumatic
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

    if (ScanValue == 1 && !HatchButton) {
      if (firstdigi && secondigi) {
        m_myRobot.arcadeDrive(0.5, 0);

      } else {
        System.out.println("Robot is moving");
        m_myRobot.arcadeDrive(0.1, 0.55);
      }
      // Scan routine RIGHT, based on the standing controller's Z axis value
    } else if (ScanValue == -1 && !HatchButton) {
      if (firstdigi && secondigi) {
        m_myRobot.arcadeDrive(0.5, 0);
      } else {
        System.out.println("Robot is moving");
        m_myRobot.arcadeDrive(0.1, -0.55);
      }
      // Scan routine LEFT, based on the standing controller's Z axis value
    } else if (ScanValue == 1 && HatchButton) {
      if ((thirddigi || fourthdigi) && (fifthdigi || sixdigi)) {
        m_myRobot.arcadeDrive(0.5, 0);

      } else {
        System.out.println("Robot is moving");
        m_myRobot.arcadeDrive(0.1, 0.55);
      }
    } else if (ScanValue == -1 && HatchButton) {
      if ((thirddigi || fourthdigi) && (fifthdigi || sixdigi)) {
        m_myRobot.arcadeDrive(0.5, 0);
      } else {
        System.out.println("Robot is moving");
        m_myRobot.arcadeDrive(0.1, -0.55);
      }
    } else {

    }

    SmartDashboard.putNumber("Robot Speed: ", (getx + gety) / 2);
    SmartDashboard.putBoolean("Sensor1: ", digi0.get());
    SmartDashboard.putBoolean("Sensor2: ", digi1.get());
    SmartDashboard.putBoolean("Sensor3: ", digi2.get());
    SmartDashboard.putBoolean("Sensor4: ", digi3.get());
    SmartDashboard.putBoolean("Sensor5: ", digi4.get());
    SmartDashboard.putBoolean("Sensor6: ", digi5.get());
    SmartDashboard.putBoolean("Ball Switch: ", digi6.get());
    SmartDashboard.putBoolean("Hatch Switch: ", digi7.get());
  }

  public void autonomousInit() {

  }

  public void autonomousPeriodic() {

  }

  public static class TestCommand extends Command {
    public void initialize() {
      System.out.println("Test Command Initialized");
    }

    public void execute() {
      System.out.println("Test Command Firing");
    }

    @Override
    protected boolean isFinished() {
      return false;
    }

  }
}