package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.ConnectionCotrollers.MessageSender;
import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.CodeRunningMessages.TestResultsMessage;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ITestResultsRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.SpringContextUtil;
import com.redocode.backend.VmAcces.CodeRunners.Program.TestResults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SendTestResultsHandler extends BaseRequestHandler {
  protected static final MessageSender messageSender =
      (MessageSender) SpringContextUtil.getApplicationContext().getBean(MessageSender.class);

  @Override
  String getChainNodeName() {
    return "Send program results";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {
    ITestResultsRequest codeResultsRequest = (ITestResultsRequest) (request);
    log.info("SendProgramResultsHandler: " + request.toString());
    List<TestResults> testResults=codeResultsRequest.getProgramResults().values().stream().findFirst().get().stream().map(x->{
     return  (TestResults)x;
    }).toList();
    TestResultsMessage programResultsMessage =
        TestResultsMessage.builder()
            .results(testResults)
            .build();

    messageSender.sendMessage(
        request.getUser(), "/public/topic/codeRunnerResults", programResultsMessage);

    return request;
  }

  @Override
  void exceptionHandling(Exception exception) {}
}
