package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subClasses.Webcam;
import org.firstinspires.ftc.teamcode.subClasses.driveTrain;
import org.firstinspires.ftc.teamcode.subClasses.topBot;


@TeleOp(name="telop")
public class telop extends OpMode {

    double foward, strafe, rotate;

    Webcam cam = new Webcam();
    driveTrain drive = new driveTrain();
    topBot top = new topBot();
    private ElapsedTime time = new ElapsedTime();
    @Override
    public void init(){
        drive.init(hardwareMap);
        top.init(hardwareMap);
        cam.init(hardwareMap, telemetry);
    }
    @Override
    public void loop(){
        cam.update();
        cam.display();
        telemetry.update();

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
        if (gamepad2.a){
            top.startShooterR(); //servo and motor
        }
        else {
            if (gamepad2.right_trigger_pressed && gamepad2.right_stick_y > .1){
                top.startLaunch();
                top.startServo();
                top.startPass();
            }
            else {
                if (gamepad2.x){
                    top.startPass();
                }
                else{
                    if(gamepad2.right_bumper && gamepad2.right_stick_y >.1){
                        top.startServo();
                        top.startLaunch();
                        top.stopPass();
                    }
                    else if (gamepad2.right_stick_y > .1) { //start launch
                        top.startLaunch();
                        top.stopServo();
                        top.stopPass();
                    }
                    else if (gamepad2.right_bumper) { //start servo
                        top.stopServo();
                        top.stopLaunch();
                        top.startPass();
                    }
                    else{
                        top.stopLaunch();
                        top.stopServo();
                        top.stopPass();
                    }
                }

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
