package com.redocode.backend.VmAcces.CodeRunners;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class CodeRunnerBuilder {

    CodeRunner instance;

    public CodeRunnerBuilder(CODE_RUNNER_TYPE type) {
        switch (type)
        {
            case CPP_RUNNER -> instance=new CppCodeRunner();
            case JS_RUNNER -> instance=new JsCodeRunner();
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
