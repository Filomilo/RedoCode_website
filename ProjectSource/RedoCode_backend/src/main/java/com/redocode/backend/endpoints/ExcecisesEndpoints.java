package com.redocode.backend.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Messages.ExcerciseDataMessage;
import com.redocode.backend.Messages.ExcerciseTestMessage;
import com.redocode.backend.Messages.ExerciseListRequestMessage;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExcersizeListEntry;
import com.redocode.backend.database.ExerciseRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/public/exercises")
public class ExcecisesEndpoints {

    @Autowired
    ExerciseRepository exerciseRepository;

    @GetMapping("/data")
    public ExcerciseDataMessage getExceciseData(Long id)
    {
        log.info("Getting ExcerciseDataMessage reuqest for" + String.valueOf(id));
        Excersize exercise=exerciseRepository.findById(id).orElse(null);
        ExcerciseDataMessage excerciseDataMessage= null;
        try {
            excerciseDataMessage = new ExcerciseDataMessage(exercise);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return excerciseDataMessage;
    }

    @GetMapping("/list")
    public   List<ExcersizeListEntry> getExercises(ExerciseListRequestMessage mes)
    {
        log.info("experiences list request: "+ mes);
        log.info("sending exercse list: "+ exerciseRepository.getSimpleExcersizeList());
        return  exerciseRepository.getSimpleExcersizeList();
    }

}
