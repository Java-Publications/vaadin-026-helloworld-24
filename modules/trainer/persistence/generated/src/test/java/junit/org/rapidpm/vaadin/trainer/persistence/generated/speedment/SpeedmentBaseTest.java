package junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment;

import com.speedment.runtime.core.Speedment;
import org.flywaydb.core.Flyway;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.functions.CheckedSupplier;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplication;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplicationBuilder;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasicImpl;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasicManager;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Timestamp;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static org.rapidpm.frp.model.Result.success;
import static org.rnorth.visibleassertions.VisibleAssertions.assertEquals;

/**
 *
 */
public class SpeedmentBaseTest implements HasLogger {

  private Result<PostgreSQLContainer> postgreSQLContainer;
  private Result<VaadinApplication>   speedmentAppl;

  public Function<JdbcDatabaseContainer, VaadinApplication> app() {
    return (container) -> new VaadinApplicationBuilder()
        .withPassword(container.getPassword())
        .withUsername(container.getUsername())
        .withConnectionUrl(container.getJdbcUrl())
        .build();
  }

  public Function<JdbcDatabaseContainer, Flyway> flyway() {
    return (container) -> {
      final Flyway flyway = new Flyway();
      flyway.setDataSource(container.getJdbcUrl(),
                           container.getUsername(),
                           container.getPassword()
      );
      return flyway;
    };
  }

  @BeforeEach
  void setUp() {
    postgreSQLContainer = ((CheckedSupplier<PostgreSQLContainer>) PostgreSQLContainer::new).get();
    postgreSQLContainer.ifPresentOrElse(
        GenericContainer::start,
        (Runnable) Assert::fail
    );
    postgreSQLContainer.ifPresent(c -> {
      final Flyway flyway = flyway().apply(c);
      flyway.clean();
//      flyway.baseline();
      flyway.migrate();
    });

    postgreSQLContainer.ifPresent(c -> speedmentAppl = success(app().apply(c)));
  }

  @AfterEach
  void tearDown() {

    speedmentAppl.ifPresent(Speedment::stop);

//    postgreSQLContainer.ifPresentOrElse(
//        GenericContainer::stop,
//        logger()::warning
//    );
  }

  @Test
  void test001() {
    final VaadinApplication app = speedmentAppl.get();
    assertEquals(
        "amount should be 5 ",
        5,
        app
            .getOrThrow(CompMathBasicManager.class)
            .stream()
            .count()
    );
  }

  @Test
  void test002() {
    final VaadinApplication app = speedmentAppl.get();

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
  void test003() {
    final VaadinApplication app = speedmentAppl.get();

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
