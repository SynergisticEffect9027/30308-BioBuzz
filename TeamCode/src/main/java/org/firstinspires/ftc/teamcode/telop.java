package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.comp.driveTrain;
import org.firstinspires.ftc.teamcode.comp.topBot;


@TeleOp(name="test", group="test")
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

        if(gamepad2.a){
            top.launch();
        }
        if (gamepad2.b){
            top.stopLaunch();
        }
        if (gamepad2.x){
            top.intake();
        }
        if (gamepad2.y){
            top.stopIntake();
        }
    }
}
