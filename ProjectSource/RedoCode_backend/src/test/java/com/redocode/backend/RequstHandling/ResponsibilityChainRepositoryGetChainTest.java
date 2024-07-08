package com.redocode.backend.RequstHandling;

import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Handlers.BaseRequestHandler;
import com.redocode.backend.WebSocketTestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ContextConfiguration
@SpringBootTest
class ResponsibilityChainRepositoryGetChainTest  {

    @Test
    void createNewExerciseChainTest(){
        BaseRequestHandler startNode=ResponsibilityChainRepository.createNewExercise;
        List<ChainNodeInfo> chain=startNode.getChainList();


        List<ChainNodeInfo> correctNewWxecisehainList=new ArrayList<>();
        correctNewWxecisehainList.add(ChainNodeInfo.builder()
                .nodeName("Validating user permissions")
                .processingMessage("Pending")
                .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                .build());
        correctNewWxecisehainList.add(ChainNodeInfo.builder()
                        .nodeName("Validating exercise information")
                        .processingMessage("Pending")
                        .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                .build());
        correctNewWxecisehainList.add(ChainNodeInfo.builder()
                .nodeName("Generating automatic tests")
                .processingMessage("Pending")
                .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                .build());
        correctNewWxecisehainList.add(ChainNodeInfo.builder()
                .nodeName("Preparing tests")
                .processingMessage("Pending")
                .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                .build());
        correctNewWxecisehainList.add(ChainNodeInfo.builder()
                .nodeName("Code testing")
                .processingMessage("Pending")
                .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                .build());
        correctNewWxecisehainList.add(ChainNodeInfo.builder()
                .nodeName("Saving exercise")
                .processingMessage("Pending")
                .status(ChainNodeInfo.CHAIN_NODE_STATUS.PENDING)
                .build());



        assertEquals(correctNewWxecisehainList.size(), chain.size());
        for (int i = 0; i <correctNewWxecisehainList.size() ; i++) {
            assertEquals(correctNewWxecisehainList.get(i),chain.get(i));
        }

    }

}