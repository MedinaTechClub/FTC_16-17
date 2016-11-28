/*
 * Created by Brian Towne on 11/20/2016.
 * For FTC Team 6184
 */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Omnidrive Bot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 *
 * Motor channel:  Front Left  drive motor:        "frontLeft"
 * Motor channel:  Front Right drive motor:        "frontRight"
 * Motor channel:  Rear Left drive motor:          "rearLeft"
 * Motor channel:  Rear Right drive motor:         "rearRight"
 * Motor channel:  Launch Motor:                   "launchMotor"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */

public class HardwareOmnidrive
{
    /* Public OpMode members. */
    public DcMotor  frontLeft   = null;
    public DcMotor  frontRight  = null;
    public DcMotor  rearLeft    = null;
    public DcMotor  rearRight   = null;
    public DcMotor  launch      = null;
    public Servo    leftPick    = null;
    public Servo    rightPick   = null;

    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareOmnidrive(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        frontLeft   = hwMap.dcMotor.get("frontleft");
        frontRight  = hwMap.dcMotor.get("frontright");
        rearLeft    = hwMap.dcMotor.get("frontleft");
        rearRight   = hwMap.dcMotor.get("frontright");
        launch      = hwMap.dcMotor.get("launhMotor");
        frontLeft.setDirection(DcMotor.Direction.FORWARD);  // Set to REVERSE if using AndyMark motors
        frontRight.setDirection(DcMotor.Direction.REVERSE); // Set to FORWARD if using AndyMark motors
        rearLeft.setDirection(DcMotor.Direction.FORWARD);   // Set to REVERSE if using AndyMark motors
        rearRight.setDirection(DcMotor.Direction.REVERSE);  // Set to FORWARD if using AndyMark motors
        launch.setDirection(DcMotor.Direction.FORWARD);     // Set to REVERSE if using AndyMark motors

        // Set all motors to zero power
        frontLeft.setPower(0);
        frontRight.setPower(0);
        rearLeft.setPower(0);
        rearRight.setPower(0);
        launch.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launch.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        leftPick = hwMap.servo.get("leftPick");
        rightPick = hwMap.servo.get("rightPick");
        leftPick.setPosition(MID_SERVO);
        rightPick.setPosition(MID_SERVO);
    }

    /*
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

