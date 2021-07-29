package org.firstinspires.ftc.teamcode.opmodes.autonstates;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.EbotsRobot;
import org.firstinspires.ftc.teamcode.EncoderTracker;

public class StateAwaitUserFeedback implements AutonState {
    LinearOpMode opMode;
    EbotsRobot robot;
    AutonStateEnum currentAutonStateEnum;
    AutonStateEnum nextAutonStateEnum;

    final boolean debugOn = false;
    final String logTag = "EBOTS";


    // ***********   CONSTRUCTOR   ***********************
    public StateAwaitUserFeedback(LinearOpMode opModeIn, EbotsRobot robotIn) {

        this.opMode = opModeIn;
        this.robot = robotIn;
        this.currentAutonStateEnum = AutonStateEnum.AWAIT_USER_FEEDBACK;
        this.nextAutonStateEnum = AutonStateEnum.MOVE_FOR_CALIBRATION;
        if(debugOn) Log.d(logTag, currentAutonStateEnum + ": Instantiating class");

    }


    // ***********   GETTERS   ***********************
    @Override
    public AutonStateEnum getNextAutonStateEnum() {
        return nextAutonStateEnum;
    }

    @Override
    public AutonStateEnum getCurrentAutonStateEnum() {
        return currentAutonStateEnum;
    }



    // ***********   INTERFACE METHODS   ***********************
    @Override
    public boolean areExitConditionsMet() {
        // No exit condition in this method because relies on
        return (opMode.gamepad1.left_bumper && opMode.gamepad1.x);
    }

    @Override
    public void performStateSpecificTransitionActions() {
    }

        @Override
    public void performStateActions() {
        Telemetry t = opMode.telemetry;
        t.addLine("Push Left Bumper + X on Gamepad1 to proceed");
        t.addData("Current State ", currentAutonStateEnum.toString());
        t.addData("actual pose: ", robot.getActualPose().toString());
        t.addData("Target Pose: ", robot.getTargetPose().toString());
        t.addData("Error: ", robot.getPoseError().toString());
        for(EncoderTracker e: robot.getEncoderTrackers()){
            t.addLine(e.toString());
            t.addLine("Effective Radius: " + String.format("%.2f", e.getCalculatedSpinRadius()));
        }
        t.update();

    }

}