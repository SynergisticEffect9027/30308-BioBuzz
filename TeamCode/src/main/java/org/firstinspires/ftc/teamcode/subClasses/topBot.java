package org.firstinspires.ftc.teamcode.subClasses;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Queue;

public class topBot {

    private ElapsedTime time = new ElapsedTime();
    public DcMotor  intakeMotor;
    public DcMotorEx lancherMotor;
    public void init(HardwareMap hwMap){

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
        lancherMotor.setVelocity(0.0);
    }
    public void startIntake(){
        intakeMotor.setPower(.4);
    }
    public void stopIntake(){
        intakeMotor.setPower(0);
    }
    public Runnable launch(){
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
    public Command lanch(){
        return Command.build()
                .setStart(launch())
        .setBlockedBehavior(BlockedBehavior.QUEUE);
    }
}
