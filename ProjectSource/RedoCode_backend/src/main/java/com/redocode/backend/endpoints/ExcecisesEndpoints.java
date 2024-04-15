package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.ExcerciseDataMessage;
import com.redocode.backend.Messages.ExcerciseTestMessage;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@Log
public class ExcecisesEndpoints {

    @GetMapping("/exerciseData/")
    public ExcerciseDataMessage getExceciseData(int id)
    {
        log.info("Getting ExcerciseDataMessage reuqest for" + String.valueOf(id));
        ExcerciseDataMessage excerciseDataMessage=ExcerciseDataMessage.builder()
                .availbleCodeRunners(new ArrayList<>(Arrays.asList("cpp", "js","test")))
                .desc("Example test")
                .automaticTests(new ArrayList<ExcerciseTestMessage>())
                .tests(new ArrayList<ExcerciseTestMessage>(Arrays.asList(
                        ExcerciseTestMessage.builder()
                                .input(4)
                                .output(null)
                                .consoleOutput("")
                                .isSolved(null)
                                .errorOutput("")
                                .expectedOutput(Arrays.asList(0,0,1,2))
                                .build()
                )))
                .outputType("int[]")
                .inputType("int")
                .startingFunction("aaaaaaaa")
                .title("TTTTTTIIIIIIIIIIIIITTTTTTTTTTTTTTLLLLLLLLLLEEEEEEE")
                .build();
        return excerciseDataMessage;
    }

}
