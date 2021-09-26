package de.idealo.idealochallenge.service;

import de.idealo.idealochallenge.model.Robot;

public interface RobotService {
    Robot executeCommands(String commands);
}
