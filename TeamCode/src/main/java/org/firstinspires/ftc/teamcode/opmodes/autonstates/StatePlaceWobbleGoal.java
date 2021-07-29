package org.firstinspires.ftc.teamcode.opmodes.autonstates;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.EbotsRobot;
import org.firstinspires.ftc.teamcode.StopWatch;

public class StatePlaceWobbleGoal implements AutonState {

    LinearOpMode opMode;
    EbotsRobot robot;
    AutonStateEnum currentAutonStateEnum;
    AutonStateEnum nextAutonStateEnum;
    long stateTimeLimit;
    StopWatch stateStopWatch;

    // ***********   CONSTRUCTOR   ***********************
    public StatePlaceWobbleGoal(LinearOpMode opModeIn, EbotsRobot robotIn){
        this.opMode = opModeIn;
        this.robot = robotIn;
        this.currentAutonStateEnum = AutonStateEnum.PLACE_WOBBLE_GOAL;
        this.nextAutonStateEnum = AutonStateEnum.MOVE_TO_LAUNCH_LINE;
        stateTimeLimit = 750L;
        stateStopWatch = new StopWatch();
        robot.toggleGripper();
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
        // Time limit is a dummy condition until manip mech ready
        return (stateStopWatch.getElapsedTimeMillis() > stateTimeLimit | !opMode.opModeIsActive());
    }

    @Override
    public void performStateSpecificTransitionActions() {

    }

    @Override
    public void performStateActions() {
//        robot.getEbotsMotionController().moveToTargetPose(robot, stateStopWatch);
        //report telemetry

        opMode.telemetry.addData("Current State ", currentAutonStateEnum.toString());
        opMode.telemetry.addLine(stateStopWatch.toString() + " time limit " + stateTimeLimit);
        opMode.telemetry.update();
    }
}