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
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = " Adiq0Gb/////AAAAme76+E2WhUFamptVVqcYOs8rfAWw8b48caeMVM89dEw04s+/mRV9TqcNvLkSArWax6t5dAy9ISStJNcnGfxwxfoHQIRwFTqw9i8eNoRrlu+8X2oPIAh5RKOZZnGNM6zNOveXjb2bu8yJTQ1cMCdiydnQ/Vh1mSlku+cAsNlmfcL0b69Mt2K4AsBiBppIesOQ3JDcS3g60JeaW9p+VepTG1pLPazmeBTBBGVx471G7sYfkTO0c/W6hyw61qmR+y7GJwn/ECMmXZhhHkNJCmJQy3tgAeJMdKHp62RJqYg5ZLW0FsIh7cOPRkNjpC0GmMCMn8AbtfadVZDwn+MPiF02ZbthQN1N+NEUtURP0BWB1CmA\n ";
    private TFObjectDetector tfod;
    private VuforiaLocalizer vuforia;

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
        initVuforia();

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


        //robot.leftMotor.setDirection(DcMotor.Direction.REVERSE);
        //robot.rightMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Path0", "Starting at %7d :%7d",
                robot.leftMotor.getCurrentPosition(),
                robot.rightMotor.getCurrentPosition());

        waitForStart();

        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -200, -200, 5.0);
                                    encoderDrive(TURN_SPEED, TURN_SPEED, 220, -220, 5.0);
                                    telemetry.addData("Gold Mineral Position", "Left");
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -200, -200, 5.0);
                                    encoderDrive(TURN_SPEED, TURN_SPEED, -220, 220, 5.0);
                                    telemetry.addData("Gold Mineral Position", "Right");
                                } else {
                                    encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -200, -200, 5.0);
                                    encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -220, 220, 5.0);
                                    telemetry.addData("Gold Mineral Position", "Center");
                                }
                            }
                        }
                        telemetry.update();
                    }
                }
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    //Rev2mDistanceSensor sensorTimeOfFlight = (Rev2mDistanceSensor)sensorRange;


    //liftDrive(-MAX_SPEED, -9000, 15.0);// for extending the scissor lift -180
    // hookDrive(-(MAX_SPEED * 0.8), -3700, 5);// disengage the hook
    //liftDrive(MAX_SPEED, 0, 15.0);// for contracting the scissor lift*/


    //encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -600, -600, 5.0);
    //encoderDrive(TURN_SPEED, TURN_SPEED, -220, 220, 5.0); // 304.8 = 1 Foot, Turn left 45 degrees
    //encoderDrive(DRIVE_SPEED, DRIVE_SPEED, -1005, -1005, 5.0); // Straight 1524
    //encoderDrive(TURN_SPEED, TURN_SPEED, 390, -390, 5.0); // Left 90
    //encoderDrive(0.8, 0.8, -914, -914, 5.0); // Straight 914
    //encoderDrive(0.8, 0.8,2438, 2438, 5.0); // Reverse 2438

    //ARMdeployment(1260,600); //this  lifts the arm and kicks out the wrist//+for up on the arm ,+ for up on the wrist


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


    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

/*
  Initialize the Tensor Flow Object Detection engine.
 */

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}

