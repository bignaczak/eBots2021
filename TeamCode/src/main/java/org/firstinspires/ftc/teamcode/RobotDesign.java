package org.firstinspires.ftc.teamcode;

public enum RobotDesign {
    //TODO:  Wire this into the initializaton of encoders for the robolt
    /**
     * This enumeration is used to capture design and configuration differences between robots
     * These differences include:
     *      Encoder Model
     *      EncoderTracker wheel diameter
     *      EncoderTracker Wiring
     */

    SEASON_2019 (EncoderModel.CTR, 3.0, RobotOrientation.LATERAL),
    SEASON_2020 (EncoderModel.REV, 1.875, RobotOrientation.NONE);

    // **************************************************************
    //  CLASS ATTRIBUTES
    // **************************************************************

    private EncoderModel encoderModel;
    private double wheelDiameter;
    private RobotOrientation thirdEncoderOrientation;

    // **************************************************************
    //  CONSTRUCTOR
    // **************************************************************

    RobotDesign(EncoderModel encoderModelIn, double wheelDiameterIn, RobotOrientation orientationIn) {
        this.encoderModel = encoderModelIn;
        this.wheelDiameter = wheelDiameterIn;
        this.thirdEncoderOrientation = orientationIn;
    }

    // **************************************************************
    //  GETTERS
    // **************************************************************
    public EncoderModel getEncoderModel() {
        return encoderModel;
    }

    public double getWheelDiameter() {
        return wheelDiameter;
    }

    public RobotOrientation getThirdEncoderOrientation() {
        return thirdEncoderOrientation;
    }

}
