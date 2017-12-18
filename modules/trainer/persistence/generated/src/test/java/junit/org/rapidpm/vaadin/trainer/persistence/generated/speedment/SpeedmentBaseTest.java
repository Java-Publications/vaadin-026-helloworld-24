package junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment;

import junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentApp;
import junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentExtension;
import junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentParameterResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplication;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasicImpl;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasicManager;

import java.sql.Timestamp;

import static java.util.stream.Collectors.toList;
import static org.rnorth.visibleassertions.VisibleAssertions.assertEquals;

/**
 *
 */
@ExtendWith(SpeedmentExtension.class)
@ExtendWith(SpeedmentParameterResolver.class)
public class SpeedmentBaseTest implements HasLogger {

  @Test
  void test001(@SpeedmentApp VaadinApplication app) {
    assertEquals(
        "amount should be 5 ",
        5l,
        app
            .getOrThrow(CompMathBasicManager.class)
            .stream()
            .count()
    );
  }

  @Test
  void test002(@SpeedmentApp VaadinApplication app) {
    assertEquals(
        "amount of result > 3",
        3,
        app
            .getOrThrow(CompMathBasicManager.class)
            .stream()
            .filter(e -> e.getResultMachine() > 3)
            .collect(toList())
            .size()
    );
  }

  @Test
  void test003(@SpeedmentApp VaadinApplication app) {
    final CompMathBasicImpl newEntity = new CompMathBasicImpl();
    newEntity
        .setCreated(new Timestamp(System.nanoTime()))
        .setOp("+")
        .setOpA(2)
        .setOpB(2)
        .setResultHuman("xx")
        .setResultMachine(4)
        .setResultOk(false);


    assertEquals(
        "amount of result > 3",
        3,
        app
            .getOrThrow(CompMathBasicManager.class)
            .stream()
            .filter(e -> e.getResultMachine() > 3)
            .collect(toList())
            .size()
    );

  }


}
