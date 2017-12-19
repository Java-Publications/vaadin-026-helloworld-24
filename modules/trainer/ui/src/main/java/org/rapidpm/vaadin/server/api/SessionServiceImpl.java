package org.rapidpm.vaadin.server.api;

import com.vaadin.server.VaadinSession;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.api.model.User;

import static org.rapidpm.vaadin.server.SessionAttributes.SESSION_ATTRIBUTE_USER;

/**
 *
 */
public class SessionServiceImpl implements SessionService<User> {
  @Override
  public boolean isUserPresent() {
    return
        (VaadinSession
             .getCurrent()
             .getAttribute(User.class) != null);
  }

  @Override
  public Result<User> actualUser() {
    final Result<User> sessionUser = (Result<User>) VaadinSession.getCurrent().getAttribute(SESSION_ATTRIBUTE_USER);
    return (sessionUser == null)
           ? Result.failure("no user in session found")
           : sessionUser;
  }

  @Override
  public void setActiveUser(Result<User> user) {
    VaadinSession.getCurrent().setAttribute(SESSION_ATTRIBUTE_USER, user);
  }


}
