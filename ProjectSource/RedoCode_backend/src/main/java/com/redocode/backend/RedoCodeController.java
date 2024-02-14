package com.redocode.backend;

import com.redocode.backend.Auth.UnauthenticatedUser;
import com.redocode.backend.Auth.User;

import com.redocode.backend.VmAcces.CodeRunnersController;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class RedoCodeController {

    @Getter
    private static RedoCodeController instance=new RedoCodeController();

    private RedoCodeController()
    {}

     HashMap<User,User> connectedUsers=new HashMap<>();

    public void addConnectedUser(User user)
    {
        connectedUsers.put(user,user);
    }

    public void removeConnectedUser(User user)
    {
        log.info("Removing user : "+ user+ " from connected users");

        this.connectedUsers.remove(user);
    }
    public void removeConnectedUser(String id)
    {
        log.info("Removing user of id: "+ id+ " from connected users");
        this.removeConnectedUser(new UnauthenticatedUser(id));
    }

    public User getUserById(String id)
    {
        User usertmp=new UnauthenticatedUser(id);
        return (User) this.connectedUsers.get(usertmp);
    }


//    testing purpuses only
public void reset() {
        connectedUsers.keySet().stream()
                .forEach(user ->    CodeRunnersController.getInstance().deregisterUser(user));

        connectedUsers.clear();
    }


}
