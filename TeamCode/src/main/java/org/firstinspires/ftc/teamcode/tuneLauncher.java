package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class tuneLauncher extends OpMode {
    private DcMotorEx launch;
    private double highVelocity = 1500;
    private double lowVelocity = 900;
    private double curTagetVelocity = highVelocity;
    private double F = 0;
    private double P = 0;

    double[] stepSizes = {10.0, 1.0, 0.1, 0.01};

    int stepIndex = 1;

    @Override
    public void init(){
        launch = hardwareMap.get(DcMotorEx.class, "launcher");
        launch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launch.setDirection(DcMotorEx.Direction.REVERSE);

        PIDFCoefficients flywheel = new PIDFCoefficients(P , 0 , 0 ,F);
        launch.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, flywheel);

    }

    @Override
    public void loop(){
        if (gamepad1.yWasPressed()) {
            if (curTagetVelocity == lowVelocity){
                curTagetVelocity = highVelocity;
            }
            else {
                curTagetVelocity = lowVelocity;
            }
        }

        if (gamepad1.bWasPressed()){
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (gamepad1.dpadLeftWasPressed()){
            F += stepSizes[stepIndex];
        }
        if (gamepad1.dpadRightWasPressed()){
            F -= stepSizes[stepIndex];
        }
        if (gamepad1.dpadUpWasPressed()){
            P += stepSizes[stepIndex];
        }
        if (gamepad1.dpadDownWasPressed()){
            P -= stepSizes[stepIndex];
        }

        PIDFCoefficients a = new PIDFCoefficients(P, 0, 0, F);

        launch.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, a);
        launch.setVelocity(curTagetVelocity);

        double curVelocity = launch.getVelocity();
        double error = curTagetVelocity - curVelocity;

        telemetry.addData("target volocity" ,curTagetVelocity);
        telemetry.addData("current volocity" , "%.2f",curVelocity);
        telemetry.addData("error" , "%.2f", error);
        telemetry.addData("tunning p","%.4f (d-pad U/D)" ,P);
        telemetry.addData("tunning f","%.4f (d-pad L/R)" ,F);
        telemetry.addData("step size","%.4f (b button)" ,stepSizes[stepIndex]);
        telemetry.update();
    }
    @Override
    public void stop(){
        telemetry.addData("tunning p","%.4f (d-pad U/D)" ,P);
        telemetry.addData("tunning f","%.4f (d-pad L/R)" ,F);
        telemetry.update();
    }
}
