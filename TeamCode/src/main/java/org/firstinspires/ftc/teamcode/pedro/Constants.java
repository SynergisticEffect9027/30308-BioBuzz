package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Localizer;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.OTOSConfig;
import com.pedropathing.revhub.localizers.OTOSLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontLeftDrive");
        c.frontRightName.set("frontRightDrive");
        c.backLeftName.set("backLeftDrive");
        c.backRightName.set("backRightDrive");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.manualBrakeMode.set(true);
    });
    public static OTOSConfig localizerConfig = new OTOSConfig(c -> {
        c.name.set("otos");
        c.linearScalar.set(28.741543597122302);
        c.angularScalar.set(0.9302338785341762);
        c.offset.set(new Pose(-0, -0, Math.PI/2));
        c.linearUnit.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.19758010681243754);
                Controller secondaryTranslationalForward = Controller.proportional(0.07300057002950885);
                Controller primaryTranslationalLateral = Controller.proportional(0.37396789633016947);
                Controller secondaryTranslationalLateral = Controller.proportional(0.13817114508777123);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.020428350483748168));
                c.brake.set(Controller.proportionalFeedforward(0.01736409791118594));

                c.headingFeedback.set(Controller.proportional(3.1246547013007135));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.2607277555506169, -0.05588216891260627));

                c.linearBrakeCoefficients.set(Matrix.diag(0.07929816515178256, 0.04212098247707868));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0012290872243569315, 0.0010003363059485986));

                c.maxAchievableForwardVelocity.set(58.41754008191176);
                c.maxAchievableStrafeVelocity.set(46.87264277089071);
                c.naturalForwardDeceleration.set(44.53839864327805);
                c.naturalStrafeDeceleration.set(81.20459722181991);
            }
    );
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight)
        return new Follower(
                 new OTOSLocalizer(h,localizerConfig),
                new Mecanum(h,drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}