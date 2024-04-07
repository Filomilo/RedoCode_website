package com.redocode.backend.ConnectionCotrollers;


import com.redocode.backend.Messages.ExerciseListRequestMessage;
import com.redocode.backend.database.ExerciseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ExercisesController {

    @Autowired
    ExerciseRepository exerciseRepository;

    Logger logger= LoggerFactory.getLogger(ExercisesController.class);
    @GetMapping("/exercises")
    public List<?> getExercises(ExerciseListRequestMessage mes)
    {
    logger.info("experiences list request: "+ mes);

        return  exerciseRepository.getSimpleExcersizeList();
    }






}
