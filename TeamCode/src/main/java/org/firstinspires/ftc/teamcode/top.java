package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="topTest", group="test")
public class top extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor intakeMotor = null;
    private DcMotorEx lancherMotor;

    Servo intakeServo;
    @Override
    public void runOpMode() {
        lancherMotor = hardwareMap.get(DcMotorEx.class, "lancher");
        intakeMotor = hardwareMap.get(DcMotor.class, "intake");
        intakeServo = hardwareMap.get(Servo.class, "servo");

        lancherMotor.setDirection(DcMotorEx.Direction.FORWARD);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (gamepad1.a){
                    lancherMotor.setPower(.7);
                }
                else {
                    lancherMotor.setPower(0);
                }
                if (gamepad1.b){
                    intakeMotor.setPower(.7);
                }
                else {
                    intakeMotor.setPower(0);
                }
                if (gamepad1.x){
                    intakeServo.setPosition(1);
                }
                if (gamepad1.y){
                    intakeServo.setPosition(0);
                }
            }
        }
    }
}
