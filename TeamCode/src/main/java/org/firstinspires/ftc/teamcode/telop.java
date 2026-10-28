package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subClasses.driveTrain;
import org.firstinspires.ftc.teamcode.subClasses.topBot;


@TeleOp(name="telop")
public class telop extends OpMode {

    double foward, strafe, rotate;

    driveTrain drive = new driveTrain();
    topBot top = new topBot();
    @Override
    public void init(){
        drive.init(hardwareMap);
        top.init(hardwareMap);
    }
    @Override
    public void loop(){
        foward = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        strafe =  gamepad1.left_stick_x;
        rotate =  gamepad1.right_stick_x;

        drive.drive(foward,strafe,rotate);

        if(gamepad2.a){ //start launch
            top.startLaunch();
        }
        if (gamepad2.aWasReleased()){ //stop launch
            top.stopLaunch();
        }
        if (gamepad2.x){ //start intake
            top.startIntake();
        }
        if (gamepad2.xWasReleased()){ //stop intake
            top.stopIntake();
        }
        if (gamepad2.y){ //start servo
            top.startServo();
        }
        if (gamepad2.yWasReleased()){ //stop servo
            top.stopServo();
        }
        if (gamepad2.b){ //start servo and motor
            top.startPass();
        }
        if (gamepad2.bWasReleased()){ //stop servo and motor
            top.stopPass();
        }
        if (gamepad2.dpad_up){
            top.launch(true); //just pass
        }
        if (gamepad2.dpad_down){
            top.launch(1); //just servo
        }
        if (gamepad2.dpad_right){
            top.launch(0.5); //servo and motor
        }
        /*
        a: shooter
        x: intake
        y: servo
        b: pass
        up: launch intake
        down: launch sevo
        right: launch pass and motor
         */
    }
}
