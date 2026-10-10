package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class topBot {

    private ElapsedTime time = new ElapsedTime();
    public DcMotor  intakeMotor;
    public DcMotorEx lancherMotor;
    public void init(HardwareMap hwMap) {

        lancherMotor = hwMap.get(DcMotorEx.class, "lancher");
        intakeMotor = hwMap.get(DcMotor.class, "intake");

        lancherMotor.setDirection(DcMotorEx.Direction.FORWARD);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);

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
    public void startIntake(){
        intakeMotor.setPower(.4);
    }
    public void stopIntake(){
        intakeMotor.setPower(0);
    }
    public Command launch(){
        time.reset();
        while (time.seconds() < 5){
            startLaunch();
            if (time.seconds() > 3){
                stopLaunch();
            }
        }
        return null;
    }
}