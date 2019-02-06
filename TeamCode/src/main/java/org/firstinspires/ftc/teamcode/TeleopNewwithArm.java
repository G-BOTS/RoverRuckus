package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Rover: TeleopNewwithArm", group = "Rover")
//@Disabled
public class TeleopNewwithArm  extends OpMode
{

    Rover robot = new Rover();


    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

            //robot.Arm.setPower(-0.2);
            robot.Arm.setTargetPosition(-200);
            robot.Arm.setPower(-0.2);
            robot.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        } else if (gamepad1.y) {

            //robot.Arm.setPower(0.6);
            robot.Arm.setTargetPosition(840);//for 90 deg
            robot.Arm.setPower(0.6);
            robot.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Wrist.setTargetPosition(-200); //for 10deg
            robot.Wrist.setPower(-0.8);
            robot.Wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        } else {

            robot.Arm.setPower(0.00); // just enough to keep the arm from falling


        }
        if (gamepad1.a) {
            robot.Wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Wrist.setPower(-0.2);
        }

        else if (gamepad1.b) {
            robot.Wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Wrist.setPower(0.2);
        }
        else {
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

            robot.Tipper.setPosition(-0.2);
        } else if (gamepad1.dpad_right) {

            robot.Tipper.setPosition(-0.4);

        } else {
            robot.Tipper.setPosition(0.20);
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
        // telemetry.addData("ArmTarget", ArmTarget);
    }
}
