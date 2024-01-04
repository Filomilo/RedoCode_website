package com.coding_exercise.coding_exercise_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class tesetEndPoint {


    @GetMapping("/hello")
    public String hello()
    {
        return " helllo3 ";
    }


}
