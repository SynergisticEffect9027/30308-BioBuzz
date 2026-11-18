package org.firstinspires.ftc.teamcode.autos.avaAutos;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import static com.pedropathing.ivy.groups.Groups.sequential;


@Autonomous
@Disabled
public class forwardSecond extends OpMode {
    //Goes from starting on the higher hive on the red side, forward to shoot, then to the side, and then up to get to parking
    //Will this also work on Blue since they're flipped???
    private Follower follower;

    topBot top = new topBot();
    ElapsedTime time = new ElapsedTime();
    private final PoseFactory p = PoseFactory.degrees();
    //plug poses into https://live.turtletracer.com/ to visalize path
    private final Pose startPose = p.of(64, 134.80, 0);//three tiles away from blue line
    private final Pose park = p.of(64, 120, 0);

    private final Pose controlPoseFromStart = p.of(23, 33, 0);

    private Path parkPathFromStart(){return line(startPose, park).linear(startPose, park);}

    private Command autoRoutine() {
        return sequential(
                follow(follower, parkPathFromStart())
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
        Scheduler.execute();
    }

}

//Things ava needs to do:
//Shoot from wall then move: Red1, Red2, Blue1, Blue2
//Wait then shoot from wall then move: Red1, Red2, Blue1, Blue2
//Move forward and then shoot: Red1, Red2, Blue1, Blue2
//Wait, then forward and shoot: Red1, Red2, Blue1, Blue2