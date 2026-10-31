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
import com.qualcomm.robotcore.util.ElapsedTime;

import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import static com.pedropathing.ivy.groups.Groups.sequential;


@Autonomous
public class launchFirstRed extends OpMode {
    //Goes from starting on the higher hive on the red side, forward to shoot, then to the side, and then up to get to parking
    //Will this also work on Blue since they're flipped???
    private Follower follower;

    topBot top = new topBot();
    ElapsedTime time = new ElapsedTime();
    private final PoseFactory p = PoseFactory.degrees();
    //plug poses into https://live.turtletracer.com/ to visalize path
    private final Pose startPose = p.of(56.35, 9.05, 0);//two tiles away from red garden. On the line connecting the second and third tile. facing red goal
    private final Pose launch = p.of(60.00, 25.00, 0);
    private final Pose park = p.of(10.00, 98.16, 0);
    private final Pose controlPose = p.of(13, 42, 0); //for curve

    private final Pose controlPoseFromStart = p.of(23, 33, 0);

    private Path launchPath() {
        return line(startPose, launch).linear(startPose, launch);
    }

    private Path parkPath() {
        return curve(launch, controlPose, park).linear(launch, park);
    }
    private Path parkPathFromStart(){return curve(startPose, controlPoseFromStart, park);}

    private Command autoRoutine() {
        return sequential(
                launchYay,  // untested
                // Add mechanism commands here.
                follow(follower, parkPath())
                //follow(follower, parkPathFromStart();
        );
    }

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        top.init(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());

    }

    @Override
    public void loop() {
        follower.update();
        Pose robotPose = follower.pose(); // returns a Pose object
        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));
        Scheduler.execute();
    }

    Command launchYay = Command.build()
            .setExecute(() -> {
                time.reset();
                while (time.seconds() < 6){
                    top.startLaunch();
                    if (time.seconds() > 3){
                        top.startPass();
                    }
                }
            })
            .setDone(() -> time.seconds() > 6)
            .setEnd(endCondition -> top.stopPass())
            .requiring(top.passMotor);
}

//Things ava needs to do:
//Shoot from wall then move: Red1, Red2, Blue1, Blue2
//Wait then shoot from wall then move: Red1, Red2, Blue1, Blue2
//Move forward and then shoot: Red1, Red2, Blue1, Blue2
//Wait, then forward and shoot: Red1, Red2, Blue1, Blue2