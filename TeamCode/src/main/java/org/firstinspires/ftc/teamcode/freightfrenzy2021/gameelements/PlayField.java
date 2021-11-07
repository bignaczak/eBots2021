package org.firstinspires.ftc.teamcode.freightfrenzy2021.gameelements;

import org.firstinspires.ftc.teamcode.ultimategoal2020.EbotsRobot2020;

public class PlayField {
    private final double fieldWidth = 142;
    private final double fieldHeight= 142;

    public PlayField(){}

    public double getFieldWidth() {
        return fieldWidth;
    }

    public double getFieldHeight() {
        return fieldHeight;
    }

    public double getYCoordTouchingWall(double headingAngle){
        double yCoord = -fieldHeight / 2;
        if(headingAngle == 0 | headingAngle == 180){
            yCoord += (EbotsRobot2020.RobotSize2020.xSize.getSizeValue()/2);
        } else if(yCoord == 90 | yCoord == -90){
            yCoord += (EbotsRobot2020.RobotSize2020.ySize.getSizeValue()/2);
        }
        return yCoord;
    }
}