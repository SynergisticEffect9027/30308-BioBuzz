package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subClasses.driveTrain;
import org.firstinspires.ftc.teamcode.subClasses.topBot;


@TeleOp(name="telop")
public class telop extends OpMode {

    double foward, strafe, rotate;

    driveTrain drive = new driveTrain();
    topBot top = new topBot();
    private ElapsedTime time = new ElapsedTime();
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

        if (gamepad2.left_trigger_pressed){
            top.startIntakeR(); //servo and motor
        }
        else if (gamepad2.x){ //start intake
            top.startIntake();
        }
        else{ //stop intake
            top.stopIntake();
        }
        if (gamepad2.b){
            top.startPass();
        }
        if (gamepad2.bWasReleased()) {
            top.stopPass();
        }
        if (gamepad2.right_trigger_pressed){
            top.startShooterR(); //servo and motor
        }
        else {
            if(gamepad2.a && gamepad2.b){
                top.startServo();
                top.startLaunch();
            }
            else if (gamepad2.a) { //start launch
                top.startLaunch();
                top.stopServo();
            }
            else if (gamepad2.y) { //start servo
                top.startServo();
                top.stopLaunch();
            }
            else{
                top.stopLaunch();
                top.stopServo();
            }
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
        x: intake & pass
        y: servo
        b: pass
        right trigger: reverse shooter
        left trigger: reverse intake
        don't use? :
            up: launch intake
            down: launch sevo
            right: launch pass and motor
         */
        telemetry.addData("velocity",top.getVe());
        telemetry.update();
    }
}
