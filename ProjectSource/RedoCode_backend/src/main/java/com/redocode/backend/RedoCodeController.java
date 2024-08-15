package com.redocode.backend;

import com.redocode.backend.database.User;

import com.redocode.backend.VmAcces.CodeRunnersController;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@Scope("singleton")
public class RedoCodeController {


    @Autowired
    private CodeRunnersController codeRunnersController;

    private RedoCodeController() {
    }

    HashMap<String, User> connectedUsers = new HashMap<>();

    public void addConnectedUser(User user) {
        log.info("adding connete user: " + user);
        connectedUsers.put(user.getSessionID(), user);
        log.info("connected user list: " + Arrays.toString(connectedUsers.keySet().toArray()));
    }

    public void removeConnectedUser(User user) {
        log.info("Removing user : " + user + " from connected users : " + Arrays.toString(connectedUsers.keySet().toArray()));

        this.connectedUsers.remove(user.getSessionID());
        log.info("Removed user : " + user + " from connected users : " + Arrays.toString(connectedUsers.keySet().toArray()));

        codeRunnersController.deregisterUser(user);
    }

    public void removeConnectedUser(String uuid) {
        log.info("Removing user of id: " + uuid + " from connected users");
        this.removeConnectedUser(new User(uuid));
    }

    public User getUserByConnectionUUID(String uuid) {

        return this.connectedUsers.get(uuid);
    }


    //    testing purpuses only
    @PreDestroy
    public void reset() {
        log.info("Destrying Redocode controller");
        connectedUsers.values().stream()
                .forEach(user -> codeRunnersController.deregisterUser(user));

        connectedUsers.clear();
    }


}
