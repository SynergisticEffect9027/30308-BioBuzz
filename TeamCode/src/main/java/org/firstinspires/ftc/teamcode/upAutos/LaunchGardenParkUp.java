package org.firstinspires.ftc.teamcode.upAutos;

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
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;

import static com.pedropathing.ivy.groups.Groups.sequential;

@Autonomous
@Disabled
public class LaunchGardenParkUp extends OpMode {
    //Goes from starting on the higher hive on the red side, forward to shoot, then to the side, and then up to get to parking
    //Will this also work on Blue since they're flipped???
    private Follower follower;

    private ElapsedTime time = new ElapsedTime();

    topBot top = new topBot();
    //    ElapsedTime time = new ElapsedTime();
    private final PoseFactory p = PoseFactory.degrees();
    //plug poses into https://live.turtletracer.com/ to visalize path
    private final Pose startPose = p.of(56.35, 9.05, 0);//two tiles away from red garden. On the line connecting the second and third tile. facing red goal
    private final Pose park = p.of(10.00, 98.16, 0);
    private final Pose controlPoseFromStart = p.of(23, 33, 0);
    private final Pose garden = p.of(11, 9, 0);
    //HAVE TO FIND OUT HOW TO RUN MOTORS WHILE MOVING
    //Maybe use follower comands in motor comands?
    private final Pose controlGardenPark = p.of(49, 43, 0);

    private Path parkPathFromStart(){return curve(startPose, controlPoseFromStart, park).constant(0);}
    private Path garden(){return line(startPose, garden).constant(-90);}
    private Path parkFromGarden(){return curve(startPose, controlPoseFromStart, park).constant(0);}

    private Command autoRoutine() {
        return sequential(
                launchYay,  // untested
                // Add mechanism commands here.
                moveAndIntake,
                follow(follower, parkFromGarden())

        );
    }

    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        top.init(hardwareMap);
//        cam.init(hardwareMap, telemetry);
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
//        telemetry.addData("hi", cam.data());
        telemetry.update();
    }

    Command launchYay = Command.build()
            .setExecute(() -> {
                time.reset();
                while (time.seconds() < 6){
                    top.startLaunch();
                    if (time.seconds() > 3){
                        top.startServo();
                    }
                }
            })
            .setDone(() -> time.seconds() > 6)
            .setEnd(endCondition -> top.stopPass())
            .requiring(top.passMotor);

    Command intake = Command.build()
            .setExecute(() -> {
                time.reset();
                top.startIntake();
            })
            .setDone(() -> time.seconds() > 6)
            .setEnd(endCondition -> top.stopIntake());

    Command moveAndIntake = parallel(
        intake,
        follow(follower, garden())
    );
}


//Things ava needs to do:
//Shoot from wall then move: Red1, Red2, Blue1, Blue2
//Wait then shoot from wall then move: Red1, Red2, Blue1, Blue2
//Move forward and then shoot: Red1, Red2, Blue1, Blue2
//Wait, then forward and shoot: Red1, Red2, Blue1, Blue2