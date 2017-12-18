package junit.org.rapidpm.vaadin.trainer.persistence.speedment.core;

import junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentApp;
import junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentExtension;
import junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentParameterResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rapidpm.vaadin.trainer.persistence.speedment.CRUDFunctions;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplication;
import org.rapidpm.vaadin.trainer.persistence.speedment.core.user.User;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasic;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 *
 */
@ExtendWith(SpeedmentExtension.class)
@ExtendWith(SpeedmentParameterResolver.class)
public class UserTest {

  @Test
  void test001(@SpeedmentApp VaadinApplication app) {
    Assertions.assertEquals(
        2l,
        ((CRUDFunctions) () -> app).allUsers().count()
    );
  }

  @Test
  void test002(@SpeedmentApp VaadinApplication app) {

    final List<CompMathBasic> svenMathBasicList = ((CRUDFunctions) () -> app)
        .mathBasicsForLogin()
        .apply("sven")
        .collect(toList());

    Assertions.assertFalse(svenMathBasicList.isEmpty());

    Assertions.assertEquals(
        5,
        svenMathBasicList.size()
    );
  }


  @Test
  void test003(@SpeedmentApp VaadinApplication app) {
    final Optional<User> sven = ((CRUDFunctions) () -> app)
        .filteredUserByLogin()
        .apply("sven")
        .findFirst();

    Assertions.assertTrue(sven.isPresent());
  }


  @Test
  void test004(@SpeedmentApp VaadinApplication app) {

    final User newUser = new User(null, "XX", "YY", "ZZ");
    ((CRUDFunctions) () -> app)
        .saveOrUpdateUser()
        .accept(newUser);

    final Optional<User> user = ((CRUDFunctions) () -> app)
        .filteredUserByLogin()
        .apply("XX")
        .findFirst();

    Assertions.assertTrue(user.isPresent());

  }
}
