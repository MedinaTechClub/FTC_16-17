/*
 * Created by Brian Towne on 11/20/2016.
 * For FTC Team 6184
 */

// import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Omnidrive Teleop for a Omnidrive bot
 * It includes all the skeletal structure that all iterative OpModes contain.
 */

@TeleOp(name= "Omnidrive Bot: Teleop", group= "Omnidrive")
// @Disabled
public class Teleop2016 extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private HardwareOmnidrive robot     = new HardwareOmnidrive();

    /* Used for Controlling the Base of the Robot */
    private void Drive()
    {
        double deadzone = 0.2;

        float xValueRight = -gamepad1.right_stick_x;
        float yValueRight = -gamepad1.right_stick_y;
        float xValueLeft = gamepad1.left_stick_x;
        float yValueLeft = -gamepad1.left_stick_y;

        // Group a is Front Left and Rear Right, Group b is Front Right and Rear Left
        float a;
        float b;
        float turnPower;

        if (Math.abs(xValueRight) <= deadzone && Math.abs(yValueRight) <= deadzone){
            // And is used here because both the y and x values of the right stick should be less than the deadzone

            a = Range.clip(yValueLeft + xValueLeft, -1,1);
            b = Range.clip(yValueLeft - xValueLeft, -1,1);


            robot.frontLeft.setPower(a);
            robot.frontRight.setPower(b);
            robot.rearLeft.setPower(b);
            robot.rearRight.setPower(a);

            telemetry.addData("a",  "%.2f", a);
            telemetry.addData("b", "%.2f", b);
        }


        else if(Math.abs(xValueRight) > deadzone || Math.abs(yValueRight) > deadzone){
            // Or is used here because only one of the y and x values of the right stick needs to be greater than the deadzone

            turnPower = Range.clip(xValueRight, -1,1);

            robot.frontLeft.setPower(-turnPower);
            robot.frontRight.setPower(turnPower);
            robot.rearLeft.setPower(-turnPower);
            robot.rearRight.setPower(turnPower);
        }


        else {


            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.rearLeft.setPower(0);
            robot.rearRight.setPower(0);
        }

    }

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        robot.init(hardwareMap);

        telemetry.addData("Status", "Teleop Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        telemetry.addData("Status", "Running: " + runtime.toString());

        // Put further code below for Teleop
        Drive();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
