package com.redocode.backend.coderunners;

import com.redocode.backend.coderunners.vmConnection.VmConnector;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;

import java.util.HashMap;

public class CodeRunnerController {




    private VmConnector connector;
    private static CodeRunnerController instance;

    private CodeRunnerController()
    {

    }

    public static CodeRunnerController getInstance()
    {
        if(instance!=null)
            throw new RuntimeException("Code runner controller should be initialized only once with vm connector");
    return instance;
    }










}
