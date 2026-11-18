package org.firstinspires.ftc.teamcode.subClasses;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
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
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.manualBrakeMode.set(true);
    });
    public static OTOSConfig localizerConfig = new OTOSConfig(c -> {
        c.name.set("otos");
//        c.linearScalar.set(24.066714216867467);
//        c.angularScalar.set(4.000000000000001);
        c.offset.set(new Pose(-8, -4, Math.PI*3/2));
        c.linearUnit.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.1988460697776478);
                Controller secondaryTranslationalForward = Controller.proportional(0.07346830951800062);
                Controller primaryTranslationalLateral = Controller.proportional(0.32667271310444923);
                Controller secondaryTranslationalLateral = Controller.proportional(0.1206968386364382);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.03316924611723104));
                c.brake.set(Controller.proportionalFeedforward(0.02819385919964638));

                c.headingFeedback.set(Controller.proportional(2.7340961829075296));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.20129812842921177, -0.03109708240087236));

                c.linearBrakeCoefficients.set(Matrix.diag(0.12369530813177111, 0.15716710108775614));
                c.quadraticBrakeCoefficients.set(Matrix.diag(-5.849740719023716E-4, -5.648553621324377E-4));

                c.maxAchievableForwardVelocity.set(53.69203079438902);
                c.maxAchievableStrafeVelocity.set(33.84417725102152);
                c.naturalForwardDeceleration.set(56.867833686451675);
                c.naturalStrafeDeceleration.set(97.42576243581563);
            }
    );
    public static Follower create(HardwareMap h) {
//         return new Follower(Drivetrain, Localizer, Foresight)
        return new Follower(
                 new OTOSLocalizer(h,localizerConfig),
                new Mecanum(h,drivetrainConfig),
                new Foresight(foresightConfig)
        );
//        return null;
    }
}