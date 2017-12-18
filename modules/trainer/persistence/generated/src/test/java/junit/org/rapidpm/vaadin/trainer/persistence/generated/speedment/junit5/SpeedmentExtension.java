package junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5;

import com.speedment.runtime.core.Speedment;
import org.flywaydb.core.Flyway;
import org.junit.Assert;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.functions.CheckedSupplier;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplication;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.function.Function;

import static org.rapidpm.frp.model.Result.success;
import static org.rapidpm.vaadin.addons.testbench.junit5.extensions.ExtensionFunctions.store;
import static org.rapidpm.vaadin.trainer.persistence.speedment.SpeedmentFunctions.newInstanceCurried;

/**
 *
 */
public class SpeedmentExtension implements
                                BeforeEachCallback, AfterEachCallback,
                                HasLogger {


  public static final String DB_CONTAINER = "db-container";
  public static final String SPEEDMENT    = "speedment";


  public Function<JdbcDatabaseContainer, VaadinApplication> app() {
    return (container) -> newInstanceCurried()
        .apply(container.getJdbcUrl())
        .apply(container.getUsername())
        .apply(container.getPassword());
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


  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    final ExtensionContext.Store store = store().apply(context);
    if (store.get(SPEEDMENT) != null)
      ((Result<Speedment>) store.get(SPEEDMENT)).ifPresent(Speedment::stop);

//    postgreSQLContainer.ifPresentOrElse(
//        GenericContainer::stop,
//        logger()::warning
//    );
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    Result<PostgreSQLContainer> postgreSQLContainer = ((CheckedSupplier<PostgreSQLContainer>) PostgreSQLContainer::new).get();
    postgreSQLContainer.ifPresentOrElse(
        GenericContainer::start,
        (Runnable) Assert::fail
    );

    postgreSQLContainer.ifPresent(c -> store().apply(context).put(DB_CONTAINER, postgreSQLContainer));

    postgreSQLContainer.ifPresent(c -> {
      final Flyway flyway = flyway().apply(c);
      flyway.clean();
//      flyway.baseline();
      flyway.migrate();
    });

    postgreSQLContainer.ifPresent(c -> store().apply(context).put(SPEEDMENT, success(app().apply(c))));


  }

}
