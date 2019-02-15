// package frc.robot;

// import edu.wpi.first.wpilibj.Solenoid;
// import edu.wpi.first.wpilibj.Compressor;
// import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

// public class Pneumatics extends TimedRobot {

// public Compressor c;
// public DoubleSolenoid soli;
// public DoubleSolenoid poli;
// public DoubleSolenoid holi;
// public DoubleSolenoid roli;
// public boolean pressureSwitch;
// public boolean AValue;
// public boolean TriggerValue;
// public boolean ThumbValue;
// public boolean InnerLValue;
// public boolean OuterLValue;
// public boolean InnerRValue;
// public boolean OuterRValue;
// public double SliderValue;

// public void RobotInit() {
// c = new Compressor(0);
// }

// public void teleopPeriodic() {
// pressureSwitch = c.getPressureSwitchValue();
// // Define the pressure switch

// if (AValue) {
// if (!pressureSwitch) {
// System.out.println("RUNNING COMPRESSOR");
// c.enabled();
// c.setClosedLoopControl(true);
// } else {
// System.out.println("COMPRESSOR FULL");
// c.setClosedLoopControl(false);
// }
// } else {
// c.setClosedLoopControl(false);
// }

// if (SliderValue == -1) {
// if (TriggerValue) {
// System.out.println("Firing solenoid 1");
// soli.set(Value.kForward);
// // Engage the first pneumatic
// } else {
// soli.set(Value.kReverse);
// }

// if (ThumbValue) {
// System.out.println("Firing solenoid 2");
// poli.set(Value.kForward);
// // Engage the second pneumatic
// } else {
// poli.set(Value.kReverse);
// }

// if (InnerLValue) {
// System.out.println("Firing solenoid 3");
// holi.set(Value.kForward);
// // Engage the third pneumatic
// } else {
// holi.set(Value.kReverse);
// }
// // Engage the fifth pneumatic

// if (OuterLValue) {
// System.out.println("Firing solenoid 4");
// roli.set(Value.kForward);
// // Engage the fourth pneumatic
// } else {
// roli.set(Value.kReverse);
// }

// if (InnerRValue) {
// System.out.println("Firing solenoid 5");
// // Engage the fifth pneumatic
// } else {

// }

// if (OuterRValue) {
// System.out.println("Firing solenoid 6");
// } else {
// }
// }

// else {
// soli.set(Value.kOff);
// poli.set(Value.kOff);
// holi.set(Value.kOff);
// roli.set(Value.kOff);
// // Turns off all solenoids
// }
// }

// }