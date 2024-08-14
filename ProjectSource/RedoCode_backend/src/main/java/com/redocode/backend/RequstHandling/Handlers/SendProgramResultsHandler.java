package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.ConnectionCotrollers.MessageSender;
import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.CodeRunningMessages.ProgramResultsMessage;
import com.redocode.backend.RequstHandling.Requests.PorgramReusltsSendRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunnersController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendProgramResultsHandler extends BaseRequestHandler {
    protected static final MessageSender messageSender = (MessageSender) SpringContextUtil.getApplicationContext().getBean(MessageSender.class);

    @Override
    String getChainNodeName() {
        return "Send program results";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {
        PorgramReusltsSendRequest porgramReusltsSendRequest = (PorgramReusltsSendRequest) (request);
        log.info("SendProgramResultsHandler: " + porgramReusltsSendRequest.toString());

        ProgramResultsMessage programResultsMessage = ProgramResultsMessage.builder()
                .results(porgramReusltsSendRequest.getProgramResults())
                .build();

        messageSender.sendMessage(porgramReusltsSendRequest.getUser(), "/public/topic/codeRunnerResults", programResultsMessage);

        return request;
    }

    @Override
    void exceptionHandling(Exception exception) {

    }
}
