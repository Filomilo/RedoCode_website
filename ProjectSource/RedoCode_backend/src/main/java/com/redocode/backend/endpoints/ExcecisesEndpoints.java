package com.redocode.backend.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redocode.backend.Messages.ExcerciseDataMessage;
import com.redocode.backend.Messages.ExcerciseTestMessage;
import com.redocode.backend.database.Excersize;
import com.redocode.backend.database.ExerciseRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@Log
public class ExcecisesEndpoints {

    @Autowired
    ExerciseRepository exerciseRepository;

    @GetMapping("/exerciseData/")
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

}
