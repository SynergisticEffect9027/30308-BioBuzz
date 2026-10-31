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
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.commands.Commands.waitUntil;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import static com.pedropathing.ivy.groups.Groups.sequential;


@Autonomous
public class launchFirstGrab extends OpMode {
    //Goes from starting on the higher hive on the red side, shoots, then moves to the garden to collect pollen
    private Follower follower;

    topBot top = new topBot();
    ElapsedTime time = new ElapsedTime();
    private final PoseFactory p = PoseFactory.degrees();
    //plug poses into https://live.turtletracer.com/ to visalize path
    private final Pose startPose = p.of(56.35, 9.05, 0);//two tiles away from red garden. On the line connecting the second and third tile. facing red goal
    private final Pose garden = p.of(10, 9.05, 0);

    private final Pose controlPoseFromStart = p.of(23, 33, 0);

    private Path garden() {
        return line(startPose, garden).linear(startPose, garden);
    }
    private Command autoRoutine() {
        return sequential(
                launchYay,
                sequential(
                        parallel(
                                follow(follower, garden()),
                                waitMs(1000),
                                intake
                        )

        ));
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
    Command intake = Command.build()
            .setExecute(() -> {
                time.reset();
                while (time.seconds() < 6){
                    top.startIntake();
                }
            })
            .setDone(() -> time.seconds() > 6)
            .setEnd(endCondition -> top.stopIntake())
            .requiring(top.intakeMotor);
}

//Things ava needs to do:
//Shoot from wall then move: Red1, Red2, Blue1, Blue2
//Wait then shoot from wall then move: Red1, Red2, Blue1, Blue2
//Move forward and then shoot: Red1, Red2, Blue1, Blue2
//Wait, then forward and shoot: Red1, Red2, Blue1, Blue2