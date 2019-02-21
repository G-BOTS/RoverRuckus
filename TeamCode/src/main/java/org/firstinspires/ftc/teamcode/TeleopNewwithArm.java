package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Rover: TeleopNewwithArm", group = "Rover")
//@Disabled
public class TeleopNewwithArm  extends OpMode {

    Rover robot = new Rover();
    public ElapsedTime runtime = new ElapsedTime();


    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.leftMotor.setDirection(DcMotor.Direction.FORWARD);
        robot.rightMotor.setDirection(DcMotor.Direction.REVERSE);
        robot.Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Wrist.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           }

    @Override
       public void loop() {
        robot.leftMotor.setPower(gamepad1.left_stick_y);
        robot.rightMotor.setPower(gamepad1.right_stick_y);
        robot.Arm.setPower(gamepad2.left_stick_y);
        robot.Wrist.setPower(gamepad2.right_stick_y);

        if (gamepad1.right_bumper) {
            robot.Lift.setPower(-1);
        } else if (gamepad1.right_trigger > .01) {
            robot.Lift.setPower(1);
        } else {
            robot.Lift.setPower(0);
        }

        if (gamepad1.left_bumper) {

            robot.Hook.setPower(-1);
        } else if (gamepad1.left_trigger > .01) {
            robot.Hook.setPower(1);
        } else {
            robot.Hook.setPower(0);
        }

        if (gamepad1.x) {

            //robot.Arm.setPower(-0.2);
            //robot.Arm.setTargetPosition(200);
            robot.Arm.setPower(-0.1);
            //robot.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        } else if (gamepad1.y) {

            //robot.Arm.setPower(0.6);
            //robot.Arm.setTargetPosition(840);//for 90 deg
            robot.Arm.setPower(0.6);
            /*robot.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Wrist.setTargetPosition(1120); //for 10deg
            robot.Wrist.setPower(0.8);
            robot.Wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/


        } else {

            robot.Arm.setPower(0.00); // just enough to keep the arm from falling


        }
        if (gamepad1.a) {
            //robot.Wrist.setTargetPosition(1120); //for 10deg
            robot.Wrist.setPower(0.3);
            //robot.Wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            /*robot.Wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Wrist.setPower(-0.3);*/
        } else if (gamepad1.b) {
            //robot.Wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Wrist.setPower(-0.3);
        } else {
            robot.Wrist.setPower(0);

        }
        if (gamepad1.dpad_up) {

            robot.Intake.setPower(-0.4);
        } else if (gamepad1.dpad_down) {

            robot.Intake.setPower(0.4);
        } else {
            robot.Intake.setPower(0);
        }
        if (gamepad1.dpad_left) {

            robot.Tipper.setPosition(0.2);
        } else if (gamepad1.dpad_right) {

            robot.Tipper.setPosition(0.4);

        } else {
            robot.Tipper.setPosition(0.5);
        }


    if(gamepad2.left_trigger > 0.1){
        robot.Intake.setPower(0.5);
    }
    else if (gamepad2.right_trigger > 0.1){
        robot.Intake.setPower(-0.5);
    }
    else {
        robot.Intake.setPower(0);
    }

    if (gamepad2.x) // Main Game pad 2 controls.

    {

        ARMdeployment(1000,600,3);//set arm and wrist to possition.

    }
    else if (gamepad2.y) // Main Game Pad 2 controls.
    {
        ARMdeployment(1600,1000,3);//set arm and wrist to possition.

    }
    else
        {

        robot.Arm.setPower(0.00); // just enough to keep the arm from fallin
             robot.Wrist.setPower(0);


    }
        if (gamepad2.a) {
            ARMdeployment(-100, 800,3);
        }
        else if (gamepad2.b) {
            ARMdeployment(1260, 0,3);
        }
         else {
        robot.Wrist.setPower(0);
        robot.Arm.setPower(0);

    }

        telemetry.addData("Lift Encoder", robot.Lift.getCurrentPosition());
        telemetry.addData("Hook Encoder", robot.Hook.getCurrentPosition());
        telemetry.addData("Arm Encoder", robot.Arm.getCurrentPosition());
        telemetry.addData("Wrist encoder", robot.Wrist.getCurrentPosition());
        // telemetry.addData("ArmTarget", ArmTarget);
    }
    public void ARMdeployment(int armtarget,int wristtarget, double timeoutS) {
    runtime.reset();
    do {
        robot.Arm.setTargetPosition(armtarget);
        robot.Wrist.setTargetPosition(wristtarget);
        robot.Arm.setPower(0.6);
        robot.Wrist.setPower(1);
        robot.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(robot.Arm.getCurrentPosition()>400){
            robot.Wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);}
    }

    while ((runtime.seconds()< timeoutS)&&(robot.Arm.isBusy() || robot.Wrist.isBusy()));
    // telemetry.addData("Arm", "Running to %7d :%7d",robot.Arm.getCurrentPosition());
    // telemetry.addData("Wrist", "Running at %7d :%7d",robot.Wrist.getCurrentPosition());


    robot.Arm.setPower(0);
    robot.Wrist.setPower(0);


}}