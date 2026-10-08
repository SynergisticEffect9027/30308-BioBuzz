package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.comp.driveTrain;

public class telop extends OpMode {

    double foward, strafe, rotate;

    driveTrain drive = new driveTrain();
    @Override
    public void init(){
        drive.init(hardwareMap);
    }
    @Override
    public void loop(){
        foward = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        strafe =  gamepad1.left_stick_x;
        rotate =  gamepad1.right_stick_x;

        drive.drive(foward,strafe,rotate);
    }
}
