package com.redocode.backend.VmAcces.CodeRunners;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class CodeRunnerBuilder {

    CodeRunner instance;

    public CodeRunnerBuilder(CodeRunner.CoderunnerTypes type) {
        switch (type)
        {
            case CPP_RUNNER -> instance=new CppCodeRunner();
            case JS_RUNNER -> instance=new CppCodeRunner();
        }
    }


    CodeRunner build()
    {
        return instance;
    }


    public static CodeRunner build(CodeRunnerRequest codeRunnerRequest)
    {
        return new CodeRunnerBuilder(codeRunnerRequest.getCodeRunnerType())
                .build();
    }


}