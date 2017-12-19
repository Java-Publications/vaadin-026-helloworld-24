package org.rapidpm.vaadin.trainer.backend.security.user;

import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.api.model.User;
import org.rapidpm.vaadin.trainer.api.security.user.UserService;
import org.rapidpm.vaadin.trainer.backend.speedment.SpeedmentSingleton;
import org.rapidpm.vaadin.trainer.persistence.speedment.CRUDFunctions;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplication;

import java.util.stream.Stream;

/**
 *
 */
public class UserServiceSpeedment implements UserService {

  private static final VaadinApplication APPLICATION = SpeedmentSingleton.instance();

  //How to hold in sync with Shiro.ini ??? next part
  @Override
  public Result<User> loadUser(String login) {
    return ((CRUDFunctions) () -> APPLICATION)
        .userWithLogin()
        .apply(login);
  }

  @Override
  public Stream<User> loadAllUsersStream() {
    return ((CRUDFunctions) () -> APPLICATION).allUsers();
  }

}
