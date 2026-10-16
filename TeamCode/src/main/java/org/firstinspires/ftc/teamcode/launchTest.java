package org.firstinspires.ftc.teamcode;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class launchTest {

    private ElapsedTime time = new ElapsedTime();
    public DcMotorEx lancherMotor;
    public void init(HardwareMap hwMap) {

        lancherMotor = hwMap.get(DcMotorEx.class, "lancher");

        lancherMotor.setDirection(DcMotorEx.Direction.FORWARD);

        lancherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void startLaunch(){
        double targetRPM = 5000; //shooter
        double ticksPerRev = 28;
        double launch = targetRPM/ticksPerRev;
        lancherMotor.setVelocity(launch);
    }
    public void stopLaunch(){
        lancherMotor.setVelocity(0);
    }
    public Command launch(){
        // untested
        time.reset();
        while (time.seconds() < 6){
            startLaunch();
            if (time.seconds() > 3){
            }
            if (time.seconds() > 5){
                stopLaunch();
            }
        }
        return null;
    }
}
