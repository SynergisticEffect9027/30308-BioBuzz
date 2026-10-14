package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subClasses.Webcam;
import org.firstinspires.ftc.teamcode.subClasses.driveTrain;
import org.firstinspires.ftc.teamcode.subClasses.topBot;


@TeleOp(name="telop")
public class telop extends OpMode {

    double foward, strafe, rotate;

//    driveTrain drive = new driveTrain();
//    topBot top = new topBot();

    Webcam cam = new Webcam();
    @Override
    public void init(){
//        drive.init(hardwareMap);
//        top.init(hardwareMap);
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

//        drive.drive(foward,strafe,rotate);
//
//        if(gamepad2.a){
//            top.startLaunch();
//        }
//        if (gamepad2.b){
//            top.stopLaunch();
//        }
//        if (gamepad2.x){
//            top.startIntake();
//        }
//        if (gamepad2.y){
//            top.stopIntake();
//        }
        if (gamepad1.dpad_down) {
            cam.stopStreaming();
        } else if (gamepad1.dpad_up) {
            cam.resumeStreaming();
        }
    }
}
