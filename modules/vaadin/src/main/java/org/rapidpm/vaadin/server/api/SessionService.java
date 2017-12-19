package org.rapidpm.vaadin.server.api;

import org.rapidpm.frp.model.Result;

/**
 *
 */
public interface SessionService<USER> {
  boolean isUserPresent();
  Result<USER> actualUser();
  void setActiveUser(Result<USER> user);
}