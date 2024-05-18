package com.redocode.backend.VmAcces.CodeRunners.Program;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.Factory.ProgramFactory;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import com.redocode.backend.database.SolutionPrograms;
import com.redocode.backend.database.SolutionProgramsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@ContextConfiguration
class CppSolutionProgramTest {


    @Autowired
    SolutionProgramsRepository solutionProgramsRepository;






}