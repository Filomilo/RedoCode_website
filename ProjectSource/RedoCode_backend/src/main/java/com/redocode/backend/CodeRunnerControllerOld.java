package com.redocode.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class CodeRunnerControllerOld {
//
//    private final SimpMessagingTemplate template;
//
//    @Autowired
//    CodeRunnerController(SimpMessagingTemplate template){
//        this.template = template;
//    }
//    @MessageMapping("/CodeRunnerTest")
//    @SendToUser("/topic/messages")
//    public String test(Principal principal, SimpMessageHeaderAccessor sha, @Header("simpSessionId")String sessionIdProvider, String test) throws Exception{
//
//        log.info("atrtributes : "+ Arrays.toString(sha.getSessionAttributes().keySet().toArray()));
//        String userId=principal.getName();
//        String  sessionId=userId;
//        log.info("activated codeRuner test: "+ sessionId);
//        log.info("user id: "+userId);
////        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
////                .create(SimpMessageType.MESSAGE);
////        headerAccessor.setSessionId(sessionid);
////        headerAccessor.setLeaveMutable(true);
//
//
//        Thread th =new Thread(()->{
//            for(int i=0;i<10;i++)
//            {
//                log.info("sending messaage");
//                try {
//                    Thread.sleep(1000);
////                    this.template.convertAndSend("/codeRunners/messages",  i);
//                    String payload="Messege from : "+sessionId+": "+test+" send conut: "+i;
////                    template.convertAndSendToUser(sessionId,"/codeRunners/messages", payload, headerAccessor.getMessageHeaders());
//                    this.template.convertAndSendToUser(sessionId,"/queue/messages",payload,createHeaders(sessionId));
//                    this.template.convertAndSendToUser(userId,"/queue/messages",payload,createHeaders(userId));
//                    this.template.convertAndSendToUser(userId,"/queue/messages",payload);
//                    sendMesseage(payload);
//                    //                    this.sendMesseage("Messege from : "+sessionid+": "+test+" send conut: "+i);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        });
//        th.start();
//        log.info("reurne");
//        return "correct";
//
//    }
//
//
//    @MessageMapping("/messages")
//    @SendToUser
//    public String sendMesseage(String mes)
//    {
//        log.info("senidng mesge tes: "+ mes);
//        return mes;
//    }
//    private MessageHeaders createHeaders(String sessionId) {
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//        headerAccessor.setSessionId(sessionId);
//        headerAccessor.setLeaveMutable(true);
//        return headerAccessor.getMessageHeaders();
//    }
}
