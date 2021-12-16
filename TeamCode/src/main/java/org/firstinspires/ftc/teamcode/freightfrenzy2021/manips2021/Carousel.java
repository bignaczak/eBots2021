package org.firstinspires.ftc.teamcode.freightfrenzy2021.manips2021;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ebotsenums.Alliance;
import org.firstinspires.ftc.teamcode.ebotsutil.AllianceSingleton;
import org.firstinspires.ftc.teamcode.ebotsutil.StopWatch;

public class Carousel {
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Attributes
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    double speed;
    StopWatch stopWatchInput;
    StopWatch stopWatchDeliver = new StopWatch();
    boolean isTouching;
    int encoderClicks;
    DcMotorEx carouselMotor;
    private static Carousel carouselInstance = null;
    private double targetVelocity = 800;
    private CarouselState carouselState = CarouselState.OFF;
    private final double startVelocity = 800;

    public enum CarouselState{
        ON,
        OFF
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Constructors
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private Carousel(HardwareMap hardwareMap){
        initMotor(hardwareMap);
        stopWatchInput = new StopWatch();
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Getters & Setters
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void setVelocity(double amount){
        double newVelocity = carouselMotor.getVelocity() + amount;
        targetVelocity = newVelocity;
        carouselMotor.setVelocity(targetVelocity);
        if(targetVelocity == 0.0){
            carouselState = CarouselState.OFF;
        } else {
            carouselState = CarouselState.ON;
        }
    }
    public double getPower(){
        return carouselMotor.getPower();
    }

    public double getMotorVelocity() {
        return carouselMotor.getVelocity();
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Static Methods
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    // Create a Carousel, if not present.
    public static Carousel getInstance(HardwareMap hardwareMap){
        if (carouselInstance == null){
            carouselInstance = new Carousel(hardwareMap);
        }
        return carouselInstance;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Instance Methods
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void initMotor (HardwareMap hardwareMap){
         carouselMotor = hardwareMap.get(DcMotorEx.class,"carouselMotor");
         carouselMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
         carouselMotor.setDirection(DcMotorSimple.Direction.REVERSE);
         speed = carouselMotor.getPower();
    }

    public void startMotor (){
        int spinSign = 1;
        if(AllianceSingleton.getAlliance() == Alliance.BLUE){
            spinSign = -1;
        }

//        carouselMotor.setPower(0.27 * spinSign);
        carouselMotor.setVelocity(targetVelocity * spinSign);
        carouselState = CarouselState.ON;
        stopWatchDeliver.reset();
    }

    public void deliverDuckAuton(){
        int spinSign = AllianceSingleton.isBlue() ? -1 : 1;
        targetVelocity = startVelocity;
        carouselMotor.setVelocity(targetVelocity * spinSign);
        carouselState = CarouselState.ON;
        stopWatchDeliver.reset();
    }

    public void stopMotor (){
//        carouselMotor.setPower(0);
        carouselMotor.setVelocity(0.0);
        carouselState = CarouselState.OFF;
    }

    public void handleUserInput(Gamepad gamepad){
        long timeOut = 400;
        boolean lockoutActive = stopWatchInput.getElapsedTimeMillis() < timeOut;
        updateTargetVelocity();
        if (lockoutActive) return;

        int spinSign = 1;
        if(AllianceSingleton.getAlliance() == Alliance.BLUE){
            spinSign = -1;
        }
        double increment = 50 * spinSign;
      if(gamepad.right_bumper && carouselState == CarouselState.OFF){
          startMotor();
          stopWatchInput.reset();
      } else if(gamepad.dpad_up){
          setVelocity(increment);
          stopWatchInput.reset();
      } else if(gamepad.dpad_down){
          setVelocity(-increment);
          stopWatchInput.reset();
      }else if(gamepad.left_bumper && gamepad.left_trigger < 0.3){
          // left bumper with left trigger is reserved for intake
          stopMotor();
      }

    }

    public void updateTargetVelocity(){
        double rampTime = 2500;
        double postDeliveryTime = rampTime + 500;
        double velocityIncrease = 1000;
        boolean inDeliveryMode = carouselState == CarouselState.ON &&
                stopWatchDeliver.getElapsedTimeMillis() < rampTime;
        boolean postDeliveryMode = carouselState == CarouselState.ON &&
                stopWatchDeliver.getElapsedTimeMillis() > postDeliveryTime;

        if(inDeliveryMode){
            double rampPercentage = stopWatchDeliver.getElapsedTimeMillis() / rampTime;
            rampPercentage = Math.min(rampPercentage, 1);
            targetVelocity = startVelocity + (rampPercentage * velocityIncrease);
            carouselMotor.setVelocity(targetVelocity);
//            Log.d("EBOTS", "targetVelocity: " + String.format("%.1f", targetVelocity));
        }
        if (postDeliveryMode && targetVelocity != startVelocity){
            targetVelocity = startVelocity;
        }

    }

}
