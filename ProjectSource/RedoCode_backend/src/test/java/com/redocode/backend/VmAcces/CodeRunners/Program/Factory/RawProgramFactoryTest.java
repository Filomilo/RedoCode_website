package com.redocode.backend.VmAcces.CodeRunners.Program.Factory;

import com.redocode.backend.VmAcces.CodeRunners.Program.Program;
import com.redocode.backend.VmAcces.CodeRunners.Program.RawProgram;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RawProgramFactoryTest {

    @Test
    void buildFailed() {
        assertThrows(RuntimeException.class,()->{
            Program program= ProgramFactory.createRawProgram().build();
        });
    }

    @Test
    void buildCorrect() {
assertDoesNotThrow(()->{
    Program program= ProgramFactory.createRawProgram().setRawProgramCode("test").build();
    assertSame(RawProgram.class,program.getClass());
});




    }
}