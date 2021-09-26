package de.idealo.idealochallenge.service;

import de.idealo.idealochallenge.model.Position;
import de.idealo.idealochallenge.model.Robot;
import de.idealo.idealochallenge.service.impl.RobotServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@DisplayName("RobotServiceTests")
public class RobotServiceTests {

    @DisplayName("turnAroundWithMock")
    @Test
    public void executeCommandsWithMock(){
        RobotService robotService = Mockito.mock(RobotService.class);
        Robot robot = new Robot(new Position(1,4),"WEST");
        String commands = "POSITION 1 3 EAST,FORWARD 1,WAIT,TURNAROUND";
        Mockito.when(robotService.executeCommands(commands)).thenReturn(robot);
        Assertions.assertEquals("WEST",robot.getDirection());
    }

    @DisplayName("turnAround")
    @Test
    public void executeCommands(){
        RobotService robotService = new RobotServiceImpl();
        String commands = "POSITION 1 3 EAST,FORWARD 1,WAIT,TURNAROUND";
        Robot robot = robotService.executeCommands(commands);
        Assertions.assertEquals("WEST",robot.getDirection());
    }

}
