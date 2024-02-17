package com.redocode.backend;

import com.redocode.backend.Auth.UnauthenticatedUser;
import com.redocode.backend.Auth.User;

import com.redocode.backend.VmAcces.CodeRunnersController;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.*;

@Slf4j
@Component
@Scope(value= ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RedoCodeController {

    @Getter
    private static RedoCodeController instance=new RedoCodeController();

    @Autowired
    private CodeRunnersController codeRunnersController;
    private RedoCodeController()
    {}

     HashMap<User,User> connectedUsers=new HashMap<>();


    public void addConnectedUser(User user)
    {
        connectedUsers.put(user,user);
    }

    public void removeConnectedUser(User user)
    {
//        log.info("Removing user : "+ user+ " from connected users");

        this.connectedUsers.remove(user);
        codeRunnersController.deregisterUser(user);
    }
    public void removeConnectedUser(String id)
    {
//        log.info("Removing user of id: "+ id+ " from connected users");
        this.removeConnectedUser(new UnauthenticatedUser(id));
    }

    public User getUserById(String id)
    {
        User usertmp=new UnauthenticatedUser(id);
        return (User) this.connectedUsers.get(usertmp);
    }


//    testing purpuses only
    @PreDestroy
public void reset() {
        log.info("Destrying Redocode controller");
        connectedUsers.keySet().stream()
                .forEach(user ->    codeRunnersController.deregisterUser(user));

        connectedUsers.clear();
    }


}
