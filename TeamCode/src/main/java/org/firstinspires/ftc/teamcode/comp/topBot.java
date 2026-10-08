package org.firstinspires.ftc.teamcode.comp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class topBot {
    public DcMotor  intakeMotor;
    public DcMotorEx lancherMotor;
    public void init(HardwareMap hwMap) {

        lancherMotor = hwMap.get(DcMotorEx.class, "lancher");
        intakeMotor = hwMap.get(DcMotor.class, "intake");

        lancherMotor.setDirection(DcMotorEx.Direction.FORWARD);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);

        lancherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void launch(){
        double targetRPM = 5000; //shooter
        double ticksPerRev = 28;
        double launch = targetRPM/ticksPerRev;
        lancherMotor.setVelocity(launch);
    }
    public void stopLaunch(){
        lancherMotor.setVelocity(0);
    }
    public void intake(){
        intakeMotor.setPower(.4);
    }
    public void stopIntake(){
        intakeMotor.setPower(0);
    }
}

