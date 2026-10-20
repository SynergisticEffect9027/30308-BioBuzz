package org.firstinspires.ftc.teamcode.subClasses;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class topBot {

    private ElapsedTime time = new ElapsedTime();
    public DcMotor  intakeMotor;
    public DcMotorEx lancherMotor;
    public CRServo intakeServo;
    public void init(HardwareMap hwMap) {

        lancherMotor = hwMap.get(DcMotorEx.class, "lancher");
        intakeMotor = hwMap.get(DcMotor.class, "intake");
        intakeServo = hwMap.get(CRServo.class, "intakeServo");

        lancherMotor.setDirection(DcMotorEx.Direction.FORWARD);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeServo.setDirection(DcMotor.Direction.FORWARD);

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
    public Command launch(boolean motor){
        // untested
        time.reset();
        while (time.seconds() < 6){
            startLaunch();
            if (time.seconds() > 3){
                startIntake();
            }
            if (time.seconds() > 5){
                stopLaunch();
                stopIntake();
            }
        }
        return null;
    }
    public Command launch(int servo){
        // untested
        time.reset();
        while (time.seconds() < 6){
            startLaunch();
            if (time.seconds() > 3){
                startServo();
            }
            if (time.seconds() > 5){
                stopLaunch();
                stopServo();
            }
        }
        return null;
    }
    public Command launch(double servoMotor){
        // untested
        time.reset();
        while (time.seconds() < 6){
            startLaunch();
            if (time.seconds() > 3){
                startIntakeServo();
            }
            if (time.seconds() > 5){
                stopLaunch();
                stopIntakeServo();
            }
        }
        return null;
    }
    public void startServo() {intakeServo.setPower(0.5);}
    public void stopServo() {intakeServo.setPower(0);}
    public void startIntakeServo(){
        startIntake();
        startServo();
    }
    public void stopIntakeServo(){
        stopIntake();
        stopServo();
    }

}
