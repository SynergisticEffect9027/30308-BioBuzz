package org.firstinspires.ftc.teamcode.avaAutos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.subClasses.Constants;
import org.firstinspires.ftc.teamcode.subClasses.topBot;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;

import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.pedropathing.ivy.groups.Groups.sequential;

import org.firstinspires.ftc.teamcode.subClasses.Webcam;

import java.util.Queue;

@Autonomous
public class launchFirstRed extends OpMode {
    //Goes from starting on the higher hive on the red side, forward to shoot, then to the side, and then up to get to parking
    private Follower follower;

    private ElapsedTime time = new ElapsedTime();

    topBot top = new topBot();

    Webcam cam = new Webcam();
    private final PoseFactory p = PoseFactory.degrees();
    //plug poses into https://live.turtletracer.com/ to visalize path
    private final Pose startPose = p.of(56.35, 9.05, 0);//two tiles away from red garden. On the line connecting the second and third tile. facing red goal
    private final Pose launch = p.of(60.00, 25.00, 0);
    private final Pose park = p.of(10.00, 98.16, 0);
    private final Pose controlPose = p.of(13.133723935895226, 42.447010167154914, 0); //for curve

    private Path launchPath() {
        return line(startPose, launch).linear(startPose, launch);
    }

    private Path parkPath() {
        return curve(launch, controlPose, park).linear(launch, park);
    }

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        top.init(hardwareMap);
        cam.init(hardwareMap, telemetry);
        follower.setPose(startPose);
        follower.update();
        cam.update();
    }


    @Override
    public void start() {
        schedule(
                sequential(
                    Webcam.redCheck(cam),
                    top.lanch()
                    //    follow(follower, launchPath()),
    //                follow(follower, parkPath())
                )
        );
        time.reset();
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
        cam.display();
        telemetry.addData("",cam.data());
        telemetry.update();
    }
}

//Things ava needs to do:
//Shoot from wall then move: Red1, Red2, Blue1, Blue2
//Wait then shoot from wall then move: Red1, Red2, Blue1, Blue2
//Move forward and then shoot: Red1, Red2, Blue1, Blue2
//Wait, then forward and shoot: Red1, Red2, Blue1, Blue2