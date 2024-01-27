package com.redocode.backend;


import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExcersizeListEntry;
import com.redocode.backend.database.ExerciseRepository;
import com.redocode.backend.database.User;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
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
    public List<?> getExercises()
    {
    logger.info("experiences list request");

        return  exerciseRepository.getSimpleExcersizeList();
    }






}
