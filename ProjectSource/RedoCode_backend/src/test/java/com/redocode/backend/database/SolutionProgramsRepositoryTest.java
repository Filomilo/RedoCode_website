package com.redocode.backend.database;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ContextConfiguration
@Log
public class SolutionProgramsRepositoryTest {

    @Autowired
    private SolutionProgramsRepository solutionProgramsRepository;

    @Test
    public void testGetFibonachiExercise() {
        List<SolutionPrograms> list=solutionProgramsRepository.findAll();
        String code="int solution(int val)\n" +
                "{\n" +
                "    int* arr=new int[val];\n" +
                "\n" +
                "if(val>=0)\n" +
                "  arr[0]=0;\n" +
                "if (val>=2)\n" +
                "   arr[1]=1;\n" +
                "for(int i=2;i<val;i++)\n" +
                "    {\n" +
                "        arr[i]=arr[i-1]+arr[i-2];\n" +
                "}\n" +
                "    return arr[val-1];\n" +
                "}";
        SolutionPrograms solutionCpp=list.get(1);
        log.info("solution cpp: "+solutionCpp.toString());

        assertEquals("cpp",solutionCpp.getLanguage().getName());
        assertEquals(code,solutionCpp.getCode());
    }

}
