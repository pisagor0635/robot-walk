package de.idealo.idealochallenge.controller;

import de.idealo.idealochallenge.model.Robot;
import de.idealo.idealochallenge.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/robot")
public class RobotController {

    @Autowired
    private RobotService robotService;

    @PostMapping("/movement")
    public ResponseEntity<Robot> move(@RequestParam String commands) {

        return ResponseEntity.ok(robotService.executeCommands(commands));
    }

}
