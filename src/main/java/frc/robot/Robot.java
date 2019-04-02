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

public class Robot extends TimedRobot {
  public boolean startValue; // Start button on the Logitech Gamepad
  public boolean bValue; // B button on the Logitech Gamepad
  public boolean xValue; // X button on the Logitech Gamepad
  public boolean thumbValue;
  public boolean innerLValue;
  public boolean outerLValue;
  public boolean stopValue;
  public boolean hatchButton;
  public boolean firstSensor;
  public boolean secondSensor;
  public boolean sensorGroup1;
  public boolean sensorGroup2;
  public boolean ballSwitch;
  public boolean hatchSwitch;
  public boolean elevatorOneSwitch;
  public boolean elevatorTwoSwitch;
  public boolean elevatorThreeSwitch;
  public boolean gripperPressed;
  public boolean thumbPressed;
  public boolean triggerPressed;
  public boolean innerLPressed;
  public boolean outerLPressed;
  public boolean stopPressed;
  public boolean firstLevel;
  public boolean secondLevel;
  public boolean thirdLevel;
  public boolean secondUp;
  public boolean secondDown;
  public boolean finalHatchSwitch;
  public boolean hatchValueOne;
  public boolean hatchValueTwo;

  public Compressor c;

  public DifferentialDrive robotDrive; // Combines both motor groups to allow driving via joystick

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

  public final double DEPLOY_SPEED = 0.5;
  public final double DRIVE_DEADBAND = 0.025;
  public double getY; // Pull Y axis value of drive gamepad's joystick
  public double getX; // Pull X axis value of drive gamepad's joystick
  public double leftTrig; // Pull the left trigger's axis value
  public double rightTrig; // Pull the right trigger's axis value
  public final double SCAN_ASSIST = 0.1;
  public final double SCAN_SPEED = 0.55;
  public double scanValue;
  public double sliderValue;
  public double remapX;
  public double remapY;

  public DoubleSolenoid gripper; // Solenoid that controls the gripper pneumatic
  public DoubleSolenoid fingers; // Solenoid that controls the gripper's fingers pneumatic
  public Solenoid intake; // Solenoid that controls the intake arm

  public Joystick lStick; // Logitech Gamepad
  public Joystick xStick; // Logitech Extreme

  public SpeedControllerGroup leftSide; // Combines both motors on the left side of the robot into one group
  public SpeedControllerGroup rightSide; // Combines both motors on the right side of the robot into one group
  public SpeedControllerGroup elevDrive;
  public SpeedControllerGroup ballIntake; // Combines both ball intake motors into a group

  public String fingerString; // Allow driver to know finger status

  public Value finger; // Allow driver to know finger status

  public WPI_TalonSRX drive1; // First drivetrain motor
  public WPI_TalonSRX drive2; // Second drivetrain motor
  public WPI_TalonSRX drive3; // Third drivetrain motor
  public WPI_TalonSRX drive4; // Fourth drivetrain motor
  public WPI_TalonSRX elevTalonSRX; // Elevator motor

  public WPI_VictorSPX firstBallIntake; // First ball intake motor
  public WPI_VictorSPX secondBallIntake; // Second ball intake motor
  public WPI_VictorSPX ballLauncher; // Ball Launcher motor

  @Override
  public void robotInit() {
    finalHatchSwitch = false;
    hatchValueOne = false;
    hatchValueTwo = false;

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

    lStick = new Joystick(0); // Logitech Gamepad
    xStick = new Joystick(1); // Logitech Xtreme Joystick
    // Declares controllers

    drive1 = new WPI_TalonSRX(1);
    drive2 = new WPI_TalonSRX(2);
    drive3 = new WPI_TalonSRX(3);
    drive4 = new WPI_TalonSRX(4);

    elevTalonSRX = new WPI_TalonSRX(5);
    ballLauncher = new WPI_VictorSPX(6);
    firstBallIntake = new WPI_VictorSPX(7);
    secondBallIntake = new WPI_VictorSPX(8);
    ballIntake = new SpeedControllerGroup(firstBallIntake, secondBallIntake);
    // Victor 7 & 8 -> intake motors, create a SpeedControllerGroup for them

    leftSide = new SpeedControllerGroup(drive1, drive3);
    rightSide = new SpeedControllerGroup(drive2, drive4);
    robotDrive = new DifferentialDrive(leftSide, rightSide);
    elevDrive = new SpeedControllerGroup(leftSide, rightSide);
    // Control which motors control which sides of the robot in terms of movement
    // Assign both of these sides to differential drive method

    gripper = new DoubleSolenoid(0, 2, 3);
    fingers = new DoubleSolenoid(0, 4, 5);
    intake = new Solenoid(0, 7);

    elevTalonSRX.configContinuousCurrentLimit(60);
  }

  @Override
  public void teleopPeriodic() {

    if (hatchSwitch && !hatchValueTwo) {
      finalHatchSwitch = true;
      hatchValueTwo = true;
    } else if (!hatchSwitch && hatchValueTwo) {
      hatchValueTwo = false;
    }
    // Confirm that this makes sense with Adam

    // if (!hatchSwitch && hatchValueTwo) {
    // hatchValueTwo = false;
    // }

    getX = lStick.getX();
    getY = lStick.getY();
    remapX = lStick.getX();
    remapY = lStick.getY();

    if (getX > -DRIVE_DEADBAND && getX < DRIVE_DEADBAND) {
      getX = 0;
    }

    if (getY > -DRIVE_DEADBAND && getY < DRIVE_DEADBAND) {
      getY = 0;
    }
    // Accounts for axis inaccuracy for movement (deadband)

    leftTrig = lStick.getRawAxis(2);
    rightTrig = lStick.getRawAxis(3);
    // Define the left and right triggers

    // if (!elevatorOneSwitch || elevatorTwoSwitch || elevatorThreeSwitch) {
    // robotDrive.arcadeDrive(lStick.getY() / -3, lStick.getX() / 3);
    // } else if (elevatorOneSwitch || (!elevatorTwoSwitch && !elevatorThreeSwitch))
    // {

    // // if (getX != 0 || getY != 0) {
    // // robotDrive.arcadeDrive(lStick.getY() / -1.05, lStick.getX() / 1.05);
    // // } else if (leftTrig != 0 || rightTrig != 0) {
    // // robotDrive.tankDrive(leftTrig / 2, rightTrig / 2);
    // // }
    // }
    // } else if (!elevatorOneSwitch && !elevatorTwoSwitch && !elevatorThreeSwitch)
    // {
    // robotDrive.arcadeDrive(lStick.getY() / -3, lStick.getX() / 3);
    // } else {
    // System.out.println("NOT MOVING ROBOT FOR SAFETY REASONS?");
    // }
    // // Controls the robot's movement

    if (scanValue != 1 || scanValue != -1) {
      if (lStick.getX() > 0 && lStick.getX() < 0.8) {
        remapX = remapX * 0.8 + 0.2;
      } else if (lStick.getX() > 0.8) {
        remapX = remapX * 0.8 + 0.2;
      } else if (lStick.getX() < 0 && lStick.getX() > -0.8) {
        remapX = remapX * 0.8 - 0.2;
      } else if (lStick.getX() < -0.8) {
        remapX = remapX * 0.8 - 0.2;
      } else {

      }

      if (lStick.getY() > 0 && lStick.getY() < 0.8) {
        remapY = remapY * 0.375 + 0.25;
      } else if (lStick.getY() > 0.8) {
        remapY = remapY * 2.25 - 1.25;
      } else if (lStick.getY() < 0 && lStick.getY() > -0.8) {
        remapY = remapY * 0.375 - 0.25;
      } else if (lStick.getY() < -0.8) {
        remapY = remapY * 2.25 + 1.25;
      } else {

      }

      robotDrive.arcadeDrive(-remapY, remapX);
    }

    if (leftTrig != 0 || rightTrig != 0) {
      robotDrive.tankDrive(leftTrig / 2, rightTrig / 2);
    }

    firstSensor = digi0.get();
    secondSensor = digi1.get();
    sensorGroup1 = digi2.get(); // Sensors 3 & 4 grouped into one DIO
    sensorGroup2 = digi4.get(); // Sensors 5 & 6 grouped into one DIO
    elevatorOneSwitch = digi5.get();
    ballSwitch = digi6.get();
    hatchSwitch = digi7.get();
    elevatorTwoSwitch = digi8.get();
    elevatorThreeSwitch = digi9.get();
    // Switches and sensors

    startValue = lStick.getRawButton(8);
    bValue = lStick.getRawButton(2);
    xValue = lStick.getRawButton(3);
    // Assigning the handheld Logitech controller's buttons (B & X)

    triggerPressed = xStick.getRawButton(1);
    thumbValue = xStick.getRawButtonPressed(2);
    innerLValue = xStick.getRawButtonPressed(3);
    hatchButton = xStick.getRawButton(4);
    outerLValue = xStick.getRawButtonPressed(5);
    scanValue = xStick.getRawAxis(2);
    sliderValue = xStick.getRawAxis(3);
    firstLevel = xStick.getRawButton(11);
    secondLevel = xStick.getRawButton(9);
    secondUp = xStick.getRawButton(8);
    secondDown = xStick.getRawButton(12);
    thirdLevel = xStick.getRawButton(7);
    stopValue = xStick.getRawButtonPressed(10);
    // Assigning variables to the Xtreme joystick's buttons and axes

    if (scanValue == 1 && !hatchButton) {
      if (firstSensor && secondSensor) {
        robotDrive.arcadeDrive(DEPLOY_SPEED, 0);
      } else {
        robotDrive.arcadeDrive(SCAN_ASSIST, SCAN_SPEED);
        // robotDrive.tankDrive(SCAN_SPEED, 0);
      }
      // Scan routine RIGHT, based on the standing controller's Z axis value
    } else if (scanValue == -1 && !hatchButton) {
      if (firstSensor && secondSensor) {
        robotDrive.arcadeDrive(DEPLOY_SPEED, 0);
      } else {
        robotDrive.arcadeDrive(SCAN_ASSIST, -SCAN_SPEED);
        // robotDrive.tankDrive(0, SCAN_SPEED);
      }
      // Scan routine LEFT, based on the standing controller's Z axis value
    } else if (scanValue == 1 && hatchButton) {
      if (sensorGroup1 && sensorGroup2) {
        robotDrive.arcadeDrive(DEPLOY_SPEED, 0);
      } else {
        robotDrive.arcadeDrive(SCAN_ASSIST, SCAN_SPEED);
        // robotDrive.tankDrive(SCAN_SPEED, 0);
      }
    } else if (scanValue == -1 && hatchButton) {
      if (sensorGroup1 && sensorGroup2) {
        robotDrive.arcadeDrive(DEPLOY_SPEED, 0);
      } else {
        robotDrive.arcadeDrive(SCAN_ASSIST, -SCAN_SPEED);
        // robotDrive.tankDrive(0, SCAN_SPEED);
      }
    }

    if (stopValue && stopPressed) {
      stopPressed = false;
    } else if (stopValue && !stopPressed) {
      stopPressed = true;
    }

    if (firstLevel) {
      System.out.println("Running elevator to 1");
      if (!elevatorOneSwitch) {
        elevTalonSRX.set(1);
      } else {
        elevTalonSRX.stopMotor();
      }
    } else if (secondLevel && secondUp) {
      System.out.println("Running elevator from 1 to 2");
      if (!elevatorTwoSwitch) {
        elevTalonSRX.set(-1);
      } else {
        elevTalonSRX.stopMotor();
      }
    } else if (secondLevel && secondDown) {
      System.out.println("Running elevator from 3 to 2");
      if (!elevatorTwoSwitch) {
        elevTalonSRX.set(1);
      } else {
        elevTalonSRX.stopMotor();
      }
    } else if (thirdLevel) {
      System.out.println("Running elevator to 3");
      if (!elevatorThreeSwitch) {
        elevTalonSRX.set(-1);
      } else {
        elevTalonSRX.stopMotor();
      }
    } else if (stopPressed) {
      elevTalonSRX.stopMotor();
    } else {
      elevTalonSRX.set(0);
    }

    if (xValue && !ballSwitch) {
      ballIntake.set(0.8);
      ballLauncher.set(-0.3);
    } else if (xValue && ballSwitch && !bValue) {
      ballIntake.stopMotor();
      ballLauncher.stopMotor();
    } else if (ballSwitch && !bValue && !xValue) {
      ballIntake.stopMotor();
      ballLauncher.stopMotor();
    } else if (!xValue && !ballSwitch) {
      ballLauncher.set(0);
      ballIntake.set(0);
    } else if (startValue) {
      System.out.println("Entering cargo ship mode");
      gripper.set(Value.kForward);
      ballLauncher.set(-1);
    } else if (!startValue) {
      gripper.set(Value.kReverse);
      ballLauncher.stopMotor();
    }

    if (bValue) {
      ballLauncher.set(-1);
    } else if (!bValue && !xValue) {
      ballLauncher.set(0);
    }
    // Controls for picking up and launching Balls

    if (sliderValue != 1) {
      if (thumbValue && thumbPressed) {
        thumbPressed = false;
      } else if (thumbValue && !thumbPressed) {
        thumbPressed = true;
      }

      if (outerLValue && outerLPressed) {
        outerLPressed = false;
      } else if (outerLValue && !outerLPressed) {
        outerLPressed = true;
      }
      // Allow toggles for Xtreme stick's controls

      if (triggerPressed) {
        gripper.set(Value.kReverse);
        // Engage the gripper pneumatic
      } else {
        gripper.set(Value.kForward);
      }

      if (thumbPressed) {
        intake.set(true);
        // Engage the ball intake pneumatic
      } else {
        intake.set(false);
      }

      if (outerLPressed) {
        if (fingers.get() == Value.kForward) {
          fingers.set(Value.kReverse);
        } else {
          fingers.set(Value.kForward);
        }
        outerLPressed = false;
      }

      if (finalHatchSwitch) {
        finalHatchSwitch = false;
        hatchValueOne = true;
        if (hatchValueOne) {
          if (fingers.get() == Value.kForward) {
            fingers.set(Value.kReverse);
            System.out.println("RUNNING FINGERS IN");
            // Timer.delay(0.25);
            hatchValueOne = false;
            // Engage the fingers pneumatic
          } else {
            fingers.set(Value.kForward);
            System.out.println("RUNNING FINGERS OUT");
            // Timer.delay(0.25);
            hatchValueOne = false;
            // Engage the fingers pneumatic
          }
        }
      }
      // hatchValueOne -> false by default; revert to false prevents the loop

    } else {
      gripper.set(Value.kOff);
      intake.set(false);
      fingers.set(Value.kOff);
      // Turns off all solenoids if the standing joystick's slider is set to off
    }

    finger = fingers.get();

    if (finger == Value.kForward) {
      fingerString = "D E P L O Y E D";
    } else {
      fingerString = "R E T R A C T E D";
    }

    SmartDashboard.putBoolean("Sensor1: ", digi0.get());
    SmartDashboard.putBoolean("Sensor2: ", digi1.get());
    SmartDashboard.putBoolean("sensorGroup1 : ", sensorGroup1);
    SmartDashboard.putBoolean("sensorGroup2 : ", sensorGroup2);
    SmartDashboard.putBoolean("Elevator Stop: ", stopPressed);
    SmartDashboard.putBoolean("Ball Switch: ", digi6.get());
    SmartDashboard.putBoolean("Hatch Switch: ", digi7.get());
    SmartDashboard.putBoolean("Elevator Switch 1: ", elevatorOneSwitch);
    SmartDashboard.putBoolean("Elevator Switch 2: ", elevatorTwoSwitch);
    SmartDashboard.putBoolean("Elevator Switch 3: ", elevatorThreeSwitch);
    SmartDashboard.putString("fingers: ", fingerString);

    System.out.println("X: " + lStick.getX());
    System.out.println("Y: " + lStick.getY());
  }

  @Override
  public void autonomousPeriodic() {
    teleopPeriodic();
  }

}