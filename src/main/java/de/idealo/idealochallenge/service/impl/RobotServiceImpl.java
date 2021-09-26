package de.idealo.idealochallenge.service.impl;

import de.idealo.idealochallenge.model.Position;
import de.idealo.idealochallenge.model.Robot;
import de.idealo.idealochallenge.service.RobotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Service
@Slf4j
public class RobotServiceImpl implements RobotService {

    private Robot robot;

    @Value("${row_size}")
    private int rowSize;

    @Value("${column_size}")
    private int columnSize;

    @Override
    public Robot executeCommands(String commands) {

        String seperatedCommands[] = commands.split(",");

        Arrays.stream(seperatedCommands).forEach(command -> execute(command));

        return robot;
    }

    private void execute(String command) {
        String[] usableCommands = command.split(" ");

        switch (usableCommands[0]) {
            case "POSITION":
                initializeRobot(usableCommands);
                break;
            case "FORWARD":
                move(usableCommands);
                break;
            case "WAIT":
                break;
            case "TURNAROUND":
                turnaround();
                break;
            case "RIGHT":
            case "LEFT":
                turn(usableCommands);
                break;
            default:
                log.error("invalid command!");
        }
    }

    private void initializeRobot(String[] usableCommands) {
        int xPosition = Integer.valueOf(usableCommands[2]); //row
        int yPosition = Integer.valueOf(usableCommands[1]); //column
        String direction = usableCommands[3];
        robot = new Robot(new Position(xPosition, yPosition), direction);
    }

    private void move(String[] usableCommands) {
        if (Objects.isNull(robot)) {
            log.error("First create a Robot!");
        } else {
            checkBoundaries(usableCommands);
        }
    }

    private void checkBoundaries(String[] usableCommands) {
        int sizeOfMovement = Integer.valueOf(usableCommands[1]);
        if ("EAST".equalsIgnoreCase(robot.getDirection())) {
            int tempXPosition = robot.getPosition().getXPos() + sizeOfMovement;
            if (tempXPosition <= columnSize - 1) {
                robot.getPosition().setXPos(tempXPosition);
            } else {
                robot.getPosition().setXPos(columnSize - 1);
                log.info("You cannot pass the right boundary!");
            }
        } else if ("WEST".equalsIgnoreCase(robot.getDirection())) {
            int tempXPosition = robot.getPosition().getXPos() - sizeOfMovement;
            if (tempXPosition >= 0) {
                robot.getPosition().setXPos(tempXPosition);
            } else {
                robot.getPosition().setXPos(0);
                log.info("You cannot pass the left boundary!");
            }
        } else if ("NORTH".equalsIgnoreCase(robot.getDirection())) {
            int tempYPosition = robot.getPosition().getYPos() - sizeOfMovement;
            if (tempYPosition >= 0) {
                robot.getPosition().setYPos(tempYPosition);
            } else {
                robot.getPosition().setYPos(0);
                log.info("You cannot pass the top boundary!");
            }
        } else if ("SOUTH".equalsIgnoreCase(robot.getDirection())) {
            int tempYPosition = robot.getPosition().getYPos() + sizeOfMovement;
            if (tempYPosition <= rowSize - 1) {
                robot.getPosition().setYPos(tempYPosition);
            } else {
                robot.getPosition().setYPos(rowSize - 1);
                log.info("You cannot pass the bottom boundary!");
            }
        } else {
            log.error("invalid movement!");
        }
    }

    private void turnaround() {
        if ("EAST".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("WEST");
        } else if ("WEST".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("EAST");
        } else if ("NORTH".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("SOUTH");
        } else {
            robot.setDirection("WEST");
        }
    }

    private void turn(String[] usableCommands) {
        if ("RIGHT".equalsIgnoreCase(usableCommands[0])) {
            turnRight();
        } else if ("LEFT".equalsIgnoreCase(usableCommands[0])) {
            turnLeft();
        }
    }

    private void turnRight() {
        if ("EAST".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("SOUTH");
        } else if ("SOUTH".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("WEST");
        } else if ("WEST".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("NORTH");
        } else if ("NORTH".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("EAST");
        }
    }

    private void turnLeft() {
        if ("EAST".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("NORTH");
        } else if ("NORTH".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("WEST");
        } else if ("WEST".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("SOUTH");
        } else if ("SOUTH".equalsIgnoreCase(robot.getDirection())) {
            robot.setDirection("EAST");
        }
    }
}