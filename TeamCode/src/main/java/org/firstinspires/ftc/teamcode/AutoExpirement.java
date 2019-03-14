package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


import java.util.List;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * Created by robot3050 on 10/26/2018.
 */
@Autonomous(name = "Rover: AutoExpirement", group = "Rover")
public class AutoExpirement extends LinearOpMode {
    Rover robot = new Rover();
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_REV = 1120;
    static final double DRIVE_GEAR_REDUCTION = 1;
    static final double WHEEL_DIAMETER_MM = 101; //in mm
    static final double COUNTS_PER_MM = (COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_MM * Math.PI);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.3;
    static final double MAX_SPEED = 1.0;
    public int wristtarget = 0;


    public void runOpMode() {
        robot.init(hardwareMap);

        //Reseting encoders
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int pLacement = 3;

        //robot.leftMotor.setDirection(DcMotor.Direction.REVERSE);
        //robot.rightMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Path0", "Starting at %7d :%7d",
                robot.leftMotor.getCurrentPosition(),
                robot.rightMotor.getCurrentPosition());

        waitForStart();


        //Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorRange;


        //liftDrive(-MAX_SPEED, -9000, 15.0);// for extending the scissor lift -180
        // hookDrive(-(MAX_SPEED * 0.8), -3700, 5);// disengage the hook
        //liftDrive(MAX_SPEED, 0, 15.0);// for contracting the scissor lift*/


        /** This auto drive works for when we can detect the position of the gold
         * if (pLacement == 1) {// red right blue right for left block
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 100, 100, 3.0);
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -150, 150, 3.0);
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 1000, 1000, 3.0);
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 330, -330, 5.0);
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 800, 800, 3.0);
         } else if (pLacement == 2) { //red right blue right for right block
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 100, 100, 3.0);
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 150, -150, 3.0);
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 1000, 1000, 3.0);
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -330, 330, 5.0);
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 800, 800, 3.0);
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -15000, -15000, 5.0);

         }else {//for center
         encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 1400, 1400, 3.0); // Straight 1524
         }**/
        // This auto drive works for when we can detect the position of the gold
        if (pLacement == 1) {// red Left blue leftt for left block
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 100, 100, 3.0);
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -150, 150, 3.0);
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 1000, 1000, 3.0);
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -332, 332, 5.0);
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 1900, 1900, 5.0);
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -2000, -2000, 5.0);
        } else if (pLacement == 2) { //redleft blue left for right block
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 100, 100, 3.0);
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 150, -150, 3.0);
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 800, 800, 3.0);
            //encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -330, 330, 5.0);
           // encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 800, 800, 3.0);


        } else {//for center
            encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 900, 900, 3.0); // Straight 1524
        }


        //ARMdeployment(1260,600); //this  lifts the arm and kicks out the wrist//+for up on the arm ,+ for up on the wrist

    }

    public void encoderDrive(double leftspeed, double rightspeed, double leftMM,
                             double rightMM, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftMotor.getCurrentPosition() + (int) (leftMM * COUNTS_PER_MM);
            newRightTarget = robot.rightMotor.getCurrentPosition() + (int) (rightMM * COUNTS_PER_MM);

            robot.leftMotor.setTargetPosition(newLeftTarget);
            robot.rightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftMotor.setPower(leftspeed);
            robot.rightMotor.setPower(rightspeed);

            //        keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (robot.leftMotor.isBusy() && robot.rightMotor.isBusy())) {
                robot.Arm.setPower(0.4);
                robot.Arm.setTargetPosition(600);
                robot.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        robot.leftMotor.getCurrentPosition(),
                        robot.rightMotor.getCurrentPosition());
            }

            // Stop all motion;
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move
        }
    }

    public void liftDrive(double MAX_SPEED, int limit, double timeoutS) {

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            robot.Lift.setTargetPosition(limit);
            // Turn On RUN_TO_POSITION
            robot.Lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            robot.Lift.setPower(MAX_SPEED);
            //        keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (robot.Lift.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", limit, 0);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        robot.Lift.getCurrentPosition());
            }
            // Stop all motion;
            robot.Lift.setPower(0);
            // Turn off RUN_TO_POSITION
            robot.Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(250);   // optional pause after each move
            }
    }
    //public void hookDrive(double hookspeed, int hooklimit, double htimeoutS) {

    public void hookDrive(double MAX_SPEED, int limit, int timeoutS) {

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            robot.Hook.setTargetPosition(limit);
            // Turn On RUN_TO_POSITION
            robot.Hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            robot.Hook.setPower(MAX_SPEED);
            //        keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (robot.Hook.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", limit, 0);
                telemetry.addData("Path2", "Running at %7d :%7d", robot.Lift.getCurrentPosition());
            }
            // Stop all motion;
            robot.Hook.setPower(0);
            // Turn off RUN_TO_POSITION
            robot.Hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(250);   // optional pause after each move
        }
    }

    public void ARMdeployment(int armtarget, int wristtarget) {
        //if (opModeIsActive() ) {//&& (robot.Arm.isBusy()||robot.Wrist.isBusy())) {
        do {
            robot.Arm.setTargetPosition(armtarget);
            robot.Wrist.setTargetPosition(wristtarget);
            robot.Arm.setPower(0.6);
            robot.Wrist.setPower(0.8);
            robot.Arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (robot.Arm.getCurrentPosition() > 1000) {
                wristtarget = 200;
                robot.Wrist.setTargetPosition(wristtarget);
                robot.Wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }

        while (opModeIsActive() && (robot.Arm.isBusy() || robot.Wrist.isBusy()));
        // telemetry.addData("Arm", "Running to %7d :%7d",robot.Arm.getCurrentPosition());
        // telemetry.addData("Wrist", "Running at %7d :%7d",robot.Wrist.getCurrentPosition());


        robot.Arm.setPower(0);
        robot.Wrist.setPower(0);


    }
    //robot.Arm.setPower(0);
    //robot.Wrist.setPower(0);


}


