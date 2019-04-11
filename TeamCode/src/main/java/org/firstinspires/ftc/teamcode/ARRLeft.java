package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by robot3050 on 10/26/2018.
 */
@Autonomous(name="Rover: ARRLeft", group="Rover")
public class ARRLeft extends LinearOpMode {
    Rover robot = new Rover();
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_REV = 1120;
    static final double DRIVE_GEAR_REDUCTION = 1;
    static final double WHEEL_DIAMETER_MM = 101; //in mm
    static final double COUNTS_PER_MM = (COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_MM * Math.PI);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.3;
    static final double MAX_SPEED = 1.0;

    public void runOpMode() {
        robot.init(hardwareMap);

//robot.leftMotor.setDirection(DcMotor.Direction.REVERSE);
//robot.rightMotor.setDirection(DcMotor.Direction.FORWARD);


        //Reseting encoders
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        telemetry.addData("Path0", "Starting at %7d :%7d",
                robot.leftMotor.getCurrentPosition(),
                robot.rightMotor.getCurrentPosition());

        waitForStart();

        //Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorRange;


        liftDrive(-MAX_SPEED,-12500, 15.0);// for extending the scissor lift -6720
        hookDrive(-MAX_SPEED,-5500,5);// disengage the hook
        liftDrive(MAX_SPEED,0, 15.0);// for contracting the scissor lift*/


        encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 120, 120, 5.0); //go straight
        encoderDrive(TURN_SPEED, TURN_SPEED, -220, 220, 5.0); // 304.8 = 1 Foot, Turn left 45 degrees
        encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 910, 910, 5.0); // Straight 1524
        encoderDrive(TURN_SPEED, TURN_SPEED, -260, 260, 5.0); // Left 90
       // encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 200, 200, 5.0); // Straight 1524
       // encoderDrive(TURN_SPEED, TURN_SPEED, 100, -100, 5.0); // right chicane
        //encoderDrive(DRIVE_SPEED, DRIVE_SPEED, 200, 200, 5.0); // straight
       // encoderDrive(TURN_SPEED, TURN_SPEED, -100, 100, 3.0); // left chicane



        encoderDrive(0.8, 0.8, 1350, 1350, 5.0); // Straight 914
        //sleep(50
        robot.Tipper.setPosition(0.1);
        //sleep(  500);
        encoderDrive(0.8, 0.75, -2300, -2300, 5.0); // Reverse 2438
        //encoderDrive(TURN_SPEED, TURN_SPEED, -200, 200, 1.0); // Left 90
        //encoderDrive(0.8, 0.7, -1100, -1100, 5.0); // Reverse 2438

        encoderDrive(0.2, 0.2, -200, -200, 1.0); // Reverse 2438


    }

    public void encoderDrive(double leftspeed, double rightspeed, double leftMM, double rightMM, double timeoutS) {
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

    public void liftDrive(double MAX_SPEED, int Llimit, double timeoutS) {

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            robot.Lift.setTargetPosition(Llimit);
            // Turn On RUN_TO_POSITION
            robot.Lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            robot.Lift.setPower(MAX_SPEED);
            //        keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (robot.Lift.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", Llimit, 0);
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

    public void hookDrive( double MAX_SPEED,int limit,int timeoutS) {

        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            robot.Hook.setTargetPosition(limit);
            // Turn On RUN_TO_POSITION
            robot.Hook.setPower(MAX_SPEED);
            robot.Hook.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            //robot.Hook.setPower(MAX_SPEED);
            //        keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (robot.Hook.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", robot.Hook.getCurrentPosition());
                telemetry.addData("Path2", "Running at %7d :%7d", robot.Lift.getCurrentPosition());
            }
            // Stop all motion;
            robot.Hook.setPower(0);
            robot.Hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(250);   // optional pause after each move
        }
    }
}
