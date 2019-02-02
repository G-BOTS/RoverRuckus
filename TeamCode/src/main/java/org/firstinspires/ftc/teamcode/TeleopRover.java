package org.firstinspires.ftc.teamcode;

/**
 * Created by robot3050 on 10/26/2018.
 */

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Rover: TeleopRover", group = "Rover")
//@Disabled
public class TeleopRover  extends OpMode
{

    Rover robot = new Rover();


    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        robot.leftMotor.setPower(gamepad1.left_stick_y);
        robot.rightMotor.setPower(gamepad1.right_stick_y);

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

            robot.Arm.setPower(-0.2);

        } else if (gamepad1.y) {

            robot.Arm.setPower(0.2);

        } else {

            robot.Arm.setPower(-0.01); // just enough to keep the arm from falling


        }
        if (gamepad1.a) {

            robot.Wrist.setPower(-0.2);
        } else if (gamepad1.b) {

            robot.Wrist.setPower(0.2);
        } else {
            robot.Wrist.setPower(0);
        }
        if (gamepad1.dpad_up) {

            robot.Intake.setPower(-0.2);
        } else if (gamepad1.dpad_down) {

            robot.Intake.setPower(0.2);
        } else {
            robot.Intake.setPower(0);
        }
        if (gamepad1.dpad_left) {

            robot.leftClaw.setPosition(-0.2);
        } else if (gamepad1.dpad_right) {

            robot.leftClaw.setPosition(-0.4);

        } else {
            robot.leftClaw.setPosition(0.20);
        }
        /* if (gamepad1.y) {
            robot.Lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Lift.setTargetPosition(-12000);
        } else if (gamepad1.a) {
            robot.Lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Lift.setTargetPosition(-100);
        }

        while (robot.Lift.getCurrentPosition() < robot.Lift.getTargetPosition()) {
            robot.Lift.setPower(1.0);
        }

        while (robot.Lift.getCurrentPosition() > robot.Lift.getTargetPosition()) {
            robot.Lift.setPower(-1.0);
        }

        robot.Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        if (gamepad1.x) {
            robot.Hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Hook.setTargetPosition(-1000);
        } else if (gamepad1.b) {
            robot.Hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Hook.setTargetPosition(-10);
        }


        while (robot.Hook.getCurrentPosition() < robot.Hook.getTargetPosition()) {
            robot.Hook.setPower(1.0);
        }

        while (robot.Hook.getCurrentPosition() > robot.Hook.getTargetPosition()) {
            robot.Hook.setPower(-1.0);
        }

        robot.Hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/

        telemetry.addData("Lift Encoder", robot.Lift.getCurrentPosition());
        telemetry.addData("Hook Encoder", robot.Hook.getCurrentPosition());
        telemetry.addData("Arm Encoder", robot.Arm.getCurrentPosition());
        //telemetry.addData("ArmTarget", ArmTarget);
    }
}
