package com.redocode.backend.VmAcces.CodeRunners;

import com.redocode.backend.VmAcces.Messages.CodeRunnerRequestMessage;
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


    public static CodeRunner build(CodeRunnerRequestMessage codeRunnerRequestMessage)
    {
        return new CodeRunnerBuilder(codeRunnerRequestMessage.getCodeRunnerType())
                .build();
    }


}
