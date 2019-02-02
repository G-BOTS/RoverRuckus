package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot.MID_SERVO;


/**
 * Created by robot3050 on 10/26/2018.
 */

public class Rover
{

    public DcMotor leftMotor   = null;
    public DcMotor rightMotor  = null;
    public DcMotor Lift = null;
    public DcMotor Hook = null;
    public DcMotor Arm  = null;
    public DcMotor Wrist = null;
    public DcMotor Intake= null;
    public DistanceSensor sensorRange = null;
    public Servo    leftClaw    = null;
    //public Servo    rightClaw   = null;


     //public DcMotor LiftMotor   = null;

    HardwareMap ahwmap = null;


    public void init(HardwareMap hwmap)
    {
        ahwmap = hwmap;

        leftMotor = ahwmap.get(DcMotor.class, "left_drive");
        rightMotor = ahwmap.get(DcMotor.class, "right_drive");
        Lift = ahwmap.get(DcMotor.class, "lift");
        Hook = ahwmap.get(DcMotor.class, "hook");
        Arm = ahwmap.get(DcMotor.class,"arm");
        Wrist = ahwmap.get(DcMotor.class,"wrist");
        Intake  =  ahwmap.get(DcMotor.class,"intake");
        //rightClaw = ahwmap.get(Servo.class, "right_hand");
        leftClaw  = ahwmap.get(Servo.class, "left_hand");

        //sensorRange = ahwmap.get(DistanceSensor.class,"sensor_range");

        // LiftMotor = ahwmap.get(DcMotor.class,"LiftMotor");

        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        Lift.setDirection(DcMotor.Direction.FORWARD);
        Hook.setDirection(DcMotor.Direction.FORWARD);
        Arm.setDirection(DcMotor.Direction.FORWARD);
        Wrist.setDirection(DcMotor.Direction.FORWARD);
        Intake.setDirection(DcMotor.Direction.FORWARD);



        leftClaw.setPosition(MID_SERVO);
        //rightClaw.setPosition(MID_SERVO);

        leftMotor.setPower(0);
        rightMotor.setPower(0);
        Lift.setPower(0);
        Hook.setPower(0);
        Arm.setPower(0);
        Wrist.setPower(0);
        Intake.setPower(0);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        //Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorRange;

    }
}
