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
                Controller primaryTranslationalForward = Controller.proportional(0.2186557619536192);
                Controller secondaryTranslationalForward = Controller.proportional(0.08078746145229838);
                Controller primaryTranslationalLateral = Controller.proportional(0.3166201500685909);
                Controller secondaryTranslationalLateral = Controller.proportional(0.11698268520411992);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.017970121677958018));
                c.brake.set(Controller.proportionalFeedforward(0.015274603426264315));

                c.headingFeedback.set(Controller.proportional(3.2544508838526687));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.27068498908310423, -0.060128574386715074));

                c.linearBrakeCoefficients.set(Matrix.diag(0.051108906713010924, 0.06697948030722564));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0016293999617324282, -2.476280141896318E-4));

                c.maxAchievableForwardVelocity.set(60.293580034888954);
                c.maxAchievableStrafeVelocity.set(44.79445998293831);
                c.naturalForwardDeceleration.set(45.781761626341805);
                c.naturalStrafeDeceleration.set(86.43521838352112);
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