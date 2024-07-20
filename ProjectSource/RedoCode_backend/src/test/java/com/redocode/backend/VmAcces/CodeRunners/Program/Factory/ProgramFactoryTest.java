package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
@Disabled("Islotating specific test for debugging")
class ProgramFactoryTest {

    @Test
    void createRawProgram() {
        assertSame(RawProgramFactory.class,ProgramFactory.createRawProgram().getClass(),"expected raw program factory");
    }

    @Test
    void createSolutionProgram() {
        assertSame(SolutionProgramFactory.class,ProgramFactory.createSolutionProgram().getClass(),"expected solution program factory");
    }
}