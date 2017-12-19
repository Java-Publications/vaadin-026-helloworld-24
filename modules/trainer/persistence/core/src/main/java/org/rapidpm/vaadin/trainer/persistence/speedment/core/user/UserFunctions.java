package org.rapidpm.vaadin.trainer.persistence.speedment.core.user;

import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.manager.Manager;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.api.model.User;
import org.rapidpm.vaadin.trainer.persistence.speedment.HasSpeedmentApp;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.core_user.CoreUser;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.core_user.CoreUserImpl;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.core_user.CoreUserManager;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.rapidpm.frp.model.Result.failure;
import static org.rapidpm.frp.model.Result.success;
import static org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.core_user.generated.GeneratedCoreUser.LOGIN;

/**
 *
 */
public interface UserFunctions extends HasSpeedmentApp {


  default Function<CoreUser, User> mapCoreUser() {
    return (core) -> new User((long) core.getId(),
                              core.getLogin(),
                              core.getForename(),
                              core.getFamilyname()
    );
  }

  default Function<User, CoreUser> mapUser() {
    return (user) -> new CoreUserImpl()
        .setId(user.id())
        .setLogin(user.login())
        .setForename(user.forename())
        .setFamilyname(user.familyname());
  }

  default Function<Long, Result<User>> userWithID() {
    return (id) -> _coreUsers()
        .apply(app())
        .filter(CoreUser.ID.equal(id))
        .findFirst()
        .map(cu -> success(mapCoreUser().apply(cu)))
        .orElseGet(() -> failure("no user found for id : " + id));
  }

  default Function<String, Result<User>> userWithLogin() {
    return (login) -> _coreUsers()
        .apply(app())
        .filter(CoreUser.LOGIN.equal(login))
        .findFirst()
        .map(cu -> success(mapCoreUser().apply(cu)))
        .orElseGet(() -> failure("no user found for login : " + login));
  }



  default Function<String, Stream<User>> filteredUserStreamByLogin() {
    return (login) -> _coreUsers()
        .apply(app())
        .filter(LOGIN.equal(login))
        .map(mapCoreUser());
  }

  default Stream<User> allUsers() {
    return _coreUsers()
        .apply(app())
        .map(mapCoreUser());
  }

  default Function<Speedment, Stream<CoreUser>> _coreUsers() {
    return _coreUserManager().andThen(_coreUsersStream());
  }

  default Function<CoreUserManager, Stream<CoreUser>> _coreUsersStream() {
    return Manager::stream;
  }

  default Function<Speedment, CoreUserManager> _coreUserManager() {
    return (app) -> app.getOrThrow(CoreUserManager.class);
  }

  default Consumer<User> saveOrUpdateUser() {
    return (user) -> ((user.id() == null || user.id() == -1L)
                      ? _persistUser()
                      : _updateUser())
        .accept(user);
  }


  default Consumer<User> _persistUser() {
    return (user) -> _coreUserManager()
        .andThen(Manager::persister)
        .andThen(coreUserConsumer -> {
          coreUserConsumer.accept(mapUser().apply(user));
          return null;
        })
        .apply(app());
  }

  default Consumer<User> _updateUser() {
    return (user) -> _coreUserManager()
        .andThen(Manager::updater)
        .andThen(coreUserConsumer -> {
          coreUserConsumer.accept(mapUser().apply(user));
          return null;
        })
        .apply(app());
  }

}
