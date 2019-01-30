package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;

public class Sensors extends TimedRobot {

    Ultrasonic ultra = new Ultrasonic(1,1);
    /* uses DigitalOutput 1 for the echo pulse and DigitalInput 1 for the trigger pulse */
    public void robotInit() {
        //ultra.setAutomaticMode(true);
    }

    public void ultrasonicSample() {
        double range = ultra.getRangeInches();
        System.out.println(range);
    }
}
