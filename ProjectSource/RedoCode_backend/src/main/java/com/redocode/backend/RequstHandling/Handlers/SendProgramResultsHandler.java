package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.ConnectionCotrollers.MessageSender;
import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.CodeRunningMessages.ProgramResultsMessage;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeResultsRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendProgramResultsHandler extends BaseRequestHandler {
  protected static final MessageSender messageSender =
      (MessageSender) SpringContextUtil.getApplicationContext().getBean(MessageSender.class);

  @Override
  String getChainNodeName() {
    return "Send program results";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {
    ICodeResultsRequest codeResultsRequest = (ICodeResultsRequest) (request);
    log.info("SendProgramResultsHandler: " + request.toString());

    ProgramResultsMessage programResultsMessage =
        ProgramResultsMessage.builder().results(codeResultsRequest.getProgramResults().values().stream().findFirst().get()).build();

    messageSender.sendMessage(
        request.getUser(), "/public/topic/codeRunnerResults", programResultsMessage);

    return request;
  }

  @Override
  void exceptionHandling(Exception exception) {}
}
