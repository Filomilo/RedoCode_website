package com.redocode.backend.VmAcces.CodeRunners;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class ContainerControllerTest {

    static ContainerController containerController;

    @BeforeEach
    void createController()
    {
        assertDoesNotThrow(()->{
            containerController=new ContainerController("nginx:stable");
        });
        assertNotNull(containerController);
        containerController.start();
    }


    @Test
    void crateListRemoveFile() {
        String fileName="fileTest.txt";
        String fileContent="test\n  file\n conent \n\n \t test \n\n";
        String[] fileListBeforeCreation=containerController.listFiles();
        containerController.createFile(fileName, fileContent);
        String[] fileListAfterCreation=containerController.listFiles();
        String recivedFileConenent=containerController.getFileContnt(fileName);
        containerController.removeFile(fileName);
        String[] fileListAfterRemoval=containerController.listFiles();


        assertEquals(fileListBeforeCreation.length+1,fileListAfterCreation.length);
        assertEquals(fileContent.trim(),recivedFileConenent);
        assertEquals(fileListBeforeCreation.length,fileListAfterRemoval.length);
    }
    @ParameterizedTest
    @MethodSource("com.redocode.backend.DataProviders.ValuesProvider#singleStringProvider")
    void createFile(String content) {
        containerController.createFile("test.txt",content);
        String fileContentCreated=containerController.getFileContnt("test.txt");
        assertEquals(content,fileContentCreated,"Created file contend do not match");
    }




    @AfterEach
    public void destroy()
    {
     containerController.destroy();
    }



}