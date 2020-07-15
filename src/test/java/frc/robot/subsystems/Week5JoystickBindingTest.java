package frc.robot.subsystems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.team7419.PaddedXbox;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import frc.robot.RealFactory;
import frc.robot.RobotContainer;
import frc.robot.subsystems.intake.IntakeSub;
import frc.robot.subsystems.intake.RunIntake;

public class Week5JoystickBindingTest {

    /**
     * Checks if RobotContainer's setDefaultCommands method sets a default command to IntakeSub
     */
    @Test
    public void defaultCommandTest() {
        SimFactory simFactory = new SimFactory();
        RobotContainer robotContainer = new RobotContainer(simFactory);
        IntakeSub intake = simFactory.getIntakeSub();
        robotContainer.setDefaultCommands();
        assertNotEquals(null, intake.getDefaultCommand());
    }

    /**
     * Checks that intake's default command is RunIntake (and takes a param joystick)
     */
    @Test
    public void runIntakeWithJoystick(){
        SimFactory simFactory = new SimFactory();
        RobotContainer robotContainer = new RobotContainer(simFactory);
        IntakeSub intake = simFactory.getIntakeSub();
        PaddedXbox joystick = simFactory.getPaddedXbox();
        robotContainer.setDefaultCommands();
        assertEquals(simFactory.getRunIntakeWithJoystick(joystick).getClass(), intake.getDefaultCommand().getClass());
    }

    /**
     * checks that moving the joystick up (or down) will correlate to speed
     */
    @Test
    public void intakeIsControlledWithJoystick(){
        SimFactory simFactory = new SimFactory();
        IntakeSub intake = mock(IntakeSub.class);
        PaddedXbox joystick = simFactory.getPaddedXbox();
        RunIntake runIntake = new RunIntake(intake, joystick);
        when(joystick.getLeftY()).thenReturn(0.75);
        runIntake.execute();
        ArgumentCaptor<Double> arguments = ArgumentCaptor.forClass(Double.class);
        verify(intake).setPower(arguments.capture());
        assertEquals(0.75, arguments.getValue(), 0);
    }
}
