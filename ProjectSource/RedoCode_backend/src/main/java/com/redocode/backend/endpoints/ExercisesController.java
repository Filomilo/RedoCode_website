package com.redocode.backend.endpoints;

import com.redocode.backend.database.ExerciseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExercisesController {

  @Autowired ExerciseRepository exerciseRepository;

  Logger logger = LoggerFactory.getLogger(ExercisesController.class);
}
