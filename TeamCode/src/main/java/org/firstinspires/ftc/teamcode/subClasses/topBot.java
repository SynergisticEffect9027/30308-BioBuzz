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
    public DcMotor  passMotor;
    public DcMotorEx lancherMotor;
    public CRServo intakeServo;
    public void init(HardwareMap hwMap) {

        lancherMotor = hwMap.get(DcMotorEx.class, "launcher");
        intakeMotor = hwMap.get(DcMotor.class, "intake");
        intakeServo = hwMap.get(CRServo.class, "passServo");
        passMotor = hwMap.get(DcMotor.class, "pass");

        lancherMotor.setDirection(DcMotorEx.Direction.REVERSE);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeServo.setDirection(DcMotor.Direction.FORWARD);
        passMotor.setDirection(DcMotor.Direction.REVERSE);

        lancherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void startLaunch(){
        double targetRPM = 5000; //shooter
        double ticksPerRev = 28;
        double launch = targetRPM * ticksPerRev / 60;
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
    public void startPass(){
        passMotor.setPower(.4);
    }
    public void stopPass(){
        passMotor.setPower(0);
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
                startPass();
            }
            if (time.seconds() > 5){
                stopLaunch();
                stopPass();
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
                startPassServo();
            }
            if (time.seconds() > 5){
                stopLaunch();
                stopPassServo();
            }
        }
        return null;
    }
    public void startServo() {intakeServo.setPower(1);}
    public void stopServo() {intakeServo.setPower(0);}
    public void startPassServo(){
        startPass();
        startServo();
    }
    public void stopPassServo(){
        stopPass();
        stopServo();
    }

}
