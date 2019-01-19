package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by robot3050 on 10/26/2018.
 */
@Autonomous(name="Rover: OLDARRLeft", group="Rover")
public class OLDARRLeft extends LinearOpMode
{
    Rover robot = new Rover();
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_REV = 1120;
    static final double DRIVE_GEAR_REDUCTION = 1;
    static final double WHEEL_DIAMETER_MM = 101; //in mm
    static final double COUNTS_PER_MM = (COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_MM * Math.PI);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.3;

    public void runOpMode()
    {
        robot.init(hardwareMap);

        //Reseting encoders
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //robot.leftMotor.setDirection(DcMotor.Direction.REVERSE);
        robot.rightMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Path0", "Starting at %7d :%7d",
                robot.leftMotor.getCurrentPosition(),
                robot.rightMotor.getCurrentPosition());

        waitForStart();

        encoderDrive(-DRIVE_SPEED, -DRIVE_SPEED, -100, -100, 5.0); // straight
        encoderDrive(TURN_SPEED, -TURN_SPEED, 250, -250, 5.0); // 304.8 = 1 Foot, Turn left 45 degrees166
        encoderDrive(-DRIVE_SPEED, -DRIVE_SPEED, -920, -920, 5.0); // Straight 990
        encoderDrive(TURN_SPEED, -TURN_SPEED, 280, -280, 5.0); // 304.8 = 1 Foot, Turn left 45 degrees166
        encoderDrive(-DRIVE_SPEED, -DRIVE_SPEED, -1524, -1524, 5.0); // Straight 1524
        encoderDrive(0.8, 0.8,2438, 2438, 5.0); // Reverse 2438k

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
            robot.leftMotor.setPower(Math.abs(leftspeed));
            robot.rightMotor.setPower(Math.abs(rightspeed));

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
}
