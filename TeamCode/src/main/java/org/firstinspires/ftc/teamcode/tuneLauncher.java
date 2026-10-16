package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class tuneLauncher extends OpMode {
    private DcMotorEx launch;
    private double highVelocity = 1500;
    private double lowVelocity = 900;
    private double curTagetVelocity = highVelocity;
    private double F = 0;
    private double P = 0;

    double[] stepSizes = {10.0, 1.0, 0.1, 0.01};

    @Override
    public void init(){

    }

    @Override
    public void loop(){

    }
}
