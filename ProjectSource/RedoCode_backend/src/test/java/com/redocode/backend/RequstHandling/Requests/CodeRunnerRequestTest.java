package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Auth.*;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
// @Disabled("Islotating specific test for debugging")
class CodeRunnerRequestTest {

  @Test
  void compareTo() throws InterruptedException {
    CodeRunnerRequest thridInORder =
        CodeRunnerRequest.builder()
            .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
            .user(new User("2"))
            //                .requestTime(new Date())
            .build();
    Thread.sleep(100);
    CodeRunnerRequest secondInOrder =
        CodeRunnerRequest.builder()
            .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
            .user(new User("2", "nick", User.USER_TYPE.AUTHENTICATED))
            // .requestTime(new Date())
            .build();

    Thread.sleep(100);

    CodeRunnerRequest firstInOrder =
        CodeRunnerRequest.builder()
            .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
            .user(new User("2", "123", User.USER_TYPE.PREMIUM))
            // .requestTime(new Date())
            .build();

    assertTrue(firstInOrder.compareTo(secondInOrder) < 0);
    assertTrue(firstInOrder.compareTo(thridInORder) < 0);

    assertTrue(secondInOrder.compareTo(firstInOrder) > 0);
    log.info("order: " + secondInOrder.compareTo(thridInORder));
    assertTrue(secondInOrder.compareTo(thridInORder) < 0);

    assertTrue(thridInORder.compareTo(firstInOrder) > 0);
    assertTrue(thridInORder.compareTo(secondInOrder) > 0);
  }

  @Test
  void priorityQueueTEtst() throws InterruptedException {
    CodeRunnerRequest fourthInORder =
        CodeRunnerRequest.builder()
            .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
            .user(new User("2"))
            //                .requestTime(new Date())
            .build();
    Thread.sleep(100);
    CodeRunnerRequest thridInORder =
        CodeRunnerRequest.builder()
            .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
            .user(new User("2", "nick", User.USER_TYPE.AUTHENTICATED))
            //                .requestTime(new Date())
            .build();

    Thread.sleep(100);

    CodeRunnerRequest firstInOrder =
        CodeRunnerRequest.builder()
            .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
            .user(new User("2", "nick", User.USER_TYPE.PREMIUM))
            //                .requestTime(new Date())
            .build();

    Thread.sleep(100);
    CodeRunnerRequest secondInOrder =
        CodeRunnerRequest.builder()
            .codeRunnerType(CODE_RUNNER_TYPE.JS_RUNNER)
            .user(new User("22", "nick", User.USER_TYPE.PREMIUM))
            //                .requestTime(new Date())
            .build();
    PriorityBlockingQueue<CodeRunnerRequest> requestQueue = new PriorityBlockingQueue<>();
    printQueuinOrder(requestQueue);
    requestQueue.add(fourthInORder);
    printQueuinOrder(requestQueue);
    requestQueue.add(firstInOrder);
    printQueuinOrder(requestQueue);
    requestQueue.add(secondInOrder);

    printQueuinOrder(requestQueue);
    requestQueue.add(thridInORder);
    printQueuinOrder(requestQueue);

    assertEquals(firstInOrder, requestQueue.poll());
    assertEquals(secondInOrder, requestQueue.poll());
    assertEquals(thridInORder, requestQueue.poll());
    assertEquals(fourthInORder, requestQueue.poll());
  }

  void printQueuinOrder(PriorityBlockingQueue<CodeRunnerRequest> requestQueue) {
    PriorityBlockingQueue<CodeRunnerRequest> queue = new PriorityBlockingQueue<>();
    queue.addAll(requestQueue);
    log.info("queue: \n");

    int i = 0;

    while (queue.size() > 0) {
      log.info(i++ + ": " + queue.poll());
    }
  }

  List<User> unathenicatedUsers = new ArrayList<User>();
  List<User> athenicatedUsers = new ArrayList<User>();
  List<User> premiumUsers = new ArrayList<User>();
  List<User> adminsUsers = new ArrayList<User>();

  List<User> allUsers = new ArrayList<User>();
  Random random = new Random();

  @BeforeEach
  void setUp() {
    log.info("code runner controllwe test");
    int amountOfUnauth = random.nextInt(10, 20);
    int authenticated = random.nextInt(10, 20);
    int amountPremieum = random.nextInt(10, 20);
    int amountAdmin = random.nextInt(10, 20);
    for (int i = 0; i < amountOfUnauth; i++) {
      User user = new User(UUID.randomUUID().toString());
      unathenicatedUsers.add(user);
      allUsers.add(user);
    }

    for (int i = 0; i < authenticated; i++) {
      User user = new User(UUID.randomUUID().toString(), "nikc", User.USER_TYPE.AUTHENTICATED);
      athenicatedUsers.add(user);
      allUsers.add(user);
    }

    for (int i = 0; i < amountPremieum; i++) {
      User user = new User(UUID.randomUUID().toString(), "nick", User.USER_TYPE.PREMIUM);
      premiumUsers.add(user);
      allUsers.add(user);
    }

    for (int i = 0; i < amountAdmin; i++) {
      User user = new User(UUID.randomUUID().toString(), "nick", User.USER_TYPE.ADMIN);
      adminsUsers.add(user);
      allUsers.add(user);
    }
    log.info("finshed setup");
  }

  @Test
  void queTest() throws InterruptedException {
    List<User> bufferFillUser = new ArrayList<>();
    PriorityBlockingQueue<CodeRunnerRequest> requestQueue = new PriorityBlockingQueue<>();

    for (User user : unathenicatedUsers) {
      CodeRunnerRequest req =
          CodeRunnerRequest.builder()
              .user(user)
              .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
              //                    .requestTime(new Date())
              .build();
      requestQueue.add(req);
      Thread.sleep(100);
    }

    for (User user : adminsUsers) {

      CodeRunnerRequest req =
          CodeRunnerRequest.builder()
              .user(user)
              .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
              //                    .requestTime(new Date())
              .build();
      requestQueue.add(req);
      printQueuinOrder(requestQueue);
      Thread.sleep(1000);
    }

    for (User user : athenicatedUsers) {
      CodeRunnerRequest req =
          CodeRunnerRequest.builder()
              .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
              .user(user)
              //                    .requestTime(new Date())
              .build();
      requestQueue.add(req);
      printQueuinOrder(requestQueue);
      Thread.sleep(100);
    }
    for (User user : premiumUsers) {
      CodeRunnerRequest req =
          CodeRunnerRequest.builder()
              .user(user)
              .codeRunnerType(CODE_RUNNER_TYPE.CPP_RUNNER)
              //                    .requestTime(new Date())
              .build();
      requestQueue.add(req);
      printQueuinOrder(requestQueue);
      Thread.sleep(100);
    }

    int queueSizeAfterAding = requestQueue.size();

    for (User user : adminsUsers) {
      User retrivedUser = requestQueue.poll().getUser();
      assertEquals(user, retrivedUser, "expected first added admin");
    }

    for (User user : premiumUsers) {
      CodeRunnerRequest rq = requestQueue.poll();
      assert rq != null;
      User retrivedUser = rq.getUser();
      assertEquals(user, retrivedUser, "expected first added premium: ");
    }

    for (User user : athenicatedUsers) {
      User retrivedUser = requestQueue.poll().getUser();
      assertEquals(user, retrivedUser, "expected first added authenticated user");
    }
    for (User user : unathenicatedUsers) {
      User retrivedUser = requestQueue.poll().getUser();
      assertEquals(user, retrivedUser, "expected first added una unauthenticated user");
    }

    assertEquals(
        unathenicatedUsers.size()
            + athenicatedUsers.size()
            + premiumUsers.size()
            + adminsUsers.size(),
        queueSizeAfterAding);
  }

  @AfterEach
  void tearDown() {
    log.info("Startign teradown");
    unathenicatedUsers.clear();
    athenicatedUsers.clear();
    premiumUsers.clear();
    allUsers.clear();
    log.info("finshed teradown");
  }
}
