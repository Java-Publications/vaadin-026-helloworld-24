package org.rapidpm.vaadin.trainer.api.security.user;

import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.api.model.User;

import java.util.stream.Stream;

/**
 *
 */
public interface UserService {
  Result<User> loadUser(String login);
  Stream<User> loadAllUsersStream();
}
