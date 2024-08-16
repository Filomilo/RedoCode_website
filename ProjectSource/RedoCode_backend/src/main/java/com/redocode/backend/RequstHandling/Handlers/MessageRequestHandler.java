package com.redocode.backend.RequstHandling.Handlers;

import lombok.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class MessageRequestHandler extends BaseRequestHandler {

  // todo: to remvoe classs

  void exceptionHandling(Exception exception) {
    log.info("Expection on Test: " + exception.getMessage());
  }
}
