package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class StateMoveToLaunchLine implements AutonState{

    LinearOpMode opMode;
    Robot robot;
    AutonStateEnum currentAutonStateEnum;
    AutonStateEnum nextAutonStateEnum;
    long stateTimeLimit;
    StopWatch stateStopWatch;

    // ***********   CONSTRUCTOR   ***********************
    public StateMoveToLaunchLine(LinearOpMode opModeIn, Robot robotIn){
        this.opMode = opModeIn;
        this.robot = robotIn;
        this.currentAutonStateEnum = AutonStateEnum.MOVE_TO_LAUNCH_LINE;
        this.nextAutonStateEnum = AutonStateEnum.SHOOT_POWER_SHOTS;

        //Create a new target pose on the launch line in the center of field
        double xCoord = (new LaunchLine()).getX() - (robot.getSizeCoordinate(CsysDirection.X) / 2);
        double yCoord = 36;
        if (robot.getAlliance()==Alliance.RED){
            yCoord *= -1;
        }
        Pose targetPose = new Pose(xCoord, yCoord, 0);
        robot.setTargetPose(targetPose);
        stateTimeLimit = robot.getEbotsMotionController().calculateTimeLimitMillis(robot);
        stateStopWatch = new StopWatch();
    }

    // ***********   GETTERS    ***********************
    @Override
    public AutonStateEnum getNextAutonStateEnum() {
        return this.nextAutonStateEnum;
    }

    @Override
    public AutonStateEnum getCurrentAutonStateEnum() {
        return this.currentAutonStateEnum;
    }

    // ***********   INTERFACE METHODS   ***********************
    @Override
    public boolean areExitConditionsMet() {
        return (robot.getEbotsMotionController().isTargetPoseReached(robot)
                | stateStopWatch.getElapsedTimeMillis() > stateTimeLimit);
    }

    @Override
    public void performStateSpecificTransitionActions() {
        robot.stop();
    }

    @Override
    public void performStateActions() {
        robot.getEbotsMotionController().moveToTargetPose(robot, stateStopWatch);
        //report telemetry
        opMode.telemetry.addData("Current State ", currentAutonStateEnum.toString());
        opMode.telemetry.addLine(stateStopWatch.toString(robot.getEbotsMotionController().getLoopCount()));
        opMode.telemetry.addData("actual pose: ", robot.getActualPose().toString());
        opMode.telemetry.addData("Target Pose: ", robot.getTargetPose().toString());
        opMode.telemetry.addData("Error: ", robot.getPoseError().toString());
        opMode.telemetry.update();
    }
}
