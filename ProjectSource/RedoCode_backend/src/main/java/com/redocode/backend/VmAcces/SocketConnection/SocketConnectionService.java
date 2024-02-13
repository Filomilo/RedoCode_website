package com.redocode.backend.VmAcces.SocketConnection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SocketConnectionService {


    private SimpMessagingTemplate simpMessagingTemplate;
@Autowired
    public SocketConnectionService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }







}
