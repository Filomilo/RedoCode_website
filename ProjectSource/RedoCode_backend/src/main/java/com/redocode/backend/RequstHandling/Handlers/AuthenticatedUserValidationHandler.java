package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.database.User;

/**
 * Handler vefication used in chain of resposiblity to check if user who sned this request is
 * authentnicated
 */
public class AuthenticatedUserValidationHandler extends MessageRequestHandler {

  @Override
  String getChainNodeName() {
    return "Validating user permissions";
  }

  @Override
  RequestBase handle(RequestBase request) throws RequestHadndlingException {
    this.nodeUpdate(request, "Checking user type", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);
    if (request.getUser().getId() != null
        && request.getUser().getId() > 0
        && request.getUser().getUserType().get_nmType()
            < User.USER_TYPE.UNAUTHENTICATED.get_nmType()) {
      this.nodeUpdate(request, "Correct user type", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);
      return request;
    } else {
      this.nodeUpdate(request, "Incorrect user type", ChainNodeInfo.CHAIN_NODE_STATUS.FAILED);
      return null;
    }
  }
}
