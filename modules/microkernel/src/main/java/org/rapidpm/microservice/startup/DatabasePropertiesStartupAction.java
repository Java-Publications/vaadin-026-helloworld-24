package org.rapidpm.microservice.startup;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.microservice.Main;

import java.util.Optional;

import static org.rapidpm.microservice.MicrokernelFunctions.readDatabaseProperties;

/**
 *
 */
public class DatabasePropertiesStartupAction implements Main.MainStartupAction , HasLogger {
  @Override
  public void execute(Optional<String[]> args) {
    //reading database properties from some path..
    // here only the provided one
    logger().info("reading database properties ..");
    readDatabaseProperties().execute();
  }
}
