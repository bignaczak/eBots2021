package org.firstinspires.ftc.teamcode;

public enum AutonParameters {
    SIMULATED_TWO_WHEEL(Speed.SLOW, GyroSetting.NONE, Accuracy.STANDARD, SoftStart.MEDIUM, EncoderSetup.TWO_WHEELS),
    SIMULATED_THREE_WHEEL(Speed.SLOW, GyroSetting.NONE, Accuracy.STANDARD, SoftStart.MEDIUM, EncoderSetup.THREE_WHEELS),
    DEBUG_TWO_WHEEL(Speed.SLOW, GyroSetting.EVERY_LOOP, Accuracy.STANDARD, SoftStart.SLOW_START, EncoderSetup.TWO_WHEELS),
    DEBUG_THREE_WHEEL(Speed.SLOW, GyroSetting.NONE, Accuracy.STANDARD, SoftStart.SLOW_START, EncoderSetup.THREE_WHEELS),
    STANDARD_TW0_WHEEL(Speed.MEDIUM, GyroSetting.NONE, Accuracy.STANDARD, SoftStart.MEDIUM, EncoderSetup.TWO_WHEELS),
    STANDARD_THREE_WHEEL(Speed.MEDIUM, GyroSetting.EVERY_LOOP, Accuracy.STANDARD, SoftStart.MEDIUM, EncoderSetup.THREE_WHEELS);

    private Speed speed;
    private GyroSetting gyroSetting;
    private Accuracy accuracy;
    private SoftStart softStart;
    private EncoderSetup encoderSetup;

    AutonParameters(Speed speedIn, GyroSetting gyroIn, Accuracy accuracyIn, SoftStart softStartIn, EncoderSetup encoderSetupIn){
        this.speed = speedIn;
        this.gyroSetting = gyroIn;
        this.accuracy = accuracyIn;
        this.softStart = softStartIn;
        this.encoderSetup = encoderSetupIn;
    }

    public Speed getSpeed() {
        return speed;
    }

    public GyroSetting getGyroSetting() {
        return gyroSetting;
    }

    public Accuracy getAccuracy() {
        return accuracy;
    }

    public SoftStart getSoftStart() {
        return softStart;
    }

    public EncoderSetup getEncoderSetup() {
        return encoderSetup;
    }

    public boolean usesSimulatedEncoders(){
        boolean returnValue = false;
        if(this == AutonParameters.SIMULATED_TWO_WHEEL || this==AutonParameters.SIMULATED_THREE_WHEEL){
            returnValue = true;
        }
        return returnValue;
    }
}
