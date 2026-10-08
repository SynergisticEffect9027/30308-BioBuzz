package org.firstinspires.ftc.teamcode.comp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class driveTrain {
    public DcMotor frontLeftDrive , frontRightDrive  , backLeftDrive  , backRightDrive ;

    public void init(HardwareMap hwMap){
        frontLeftDrive = hwMap.get(DcMotor.class, "frontLeftDrive");
        backLeftDrive = hwMap.get(DcMotor.class, "backLeftDrive");
        frontRightDrive = hwMap.get(DcMotor.class, "frontRightDrive");
        backRightDrive = hwMap.get(DcMotor.class, "backRightDrive");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void drive(double foward, double strafe, double rotate){

        double max;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double frontLeftPower  = foward + strafe + rotate;
        double frontRightPower = foward - strafe - rotate;
        double backLeftPower   = foward - strafe + rotate;
        double backRightPower  = foward + strafe - rotate;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }

        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backLeftDrive.setPower(backLeftPower);
        backRightDrive.setPower(backRightPower);

    }
}
