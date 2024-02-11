package com.redocode.backend.coderunners.CodeRunners;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ContainerControllerTest {

    static ContainerController containerController;

    @BeforeEach
    void createController()
    {
        assertDoesNotThrow(()->{
            containerController=new ContainerController("nginx");
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





    @AfterEach
    public void destroy()
    {
      containerController.destroy();
    }


}