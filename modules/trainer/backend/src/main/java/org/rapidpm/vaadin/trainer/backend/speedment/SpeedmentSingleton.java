package org.rapidpm.vaadin.trainer.backend.speedment;

import org.rapidpm.vaadin.trainer.persistence.speedment.SpeedmentFunctions;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplication;

/**
 *
 */
public class SpeedmentSingleton {

  public static final String USERNAME = "database.username";
  public static final String PASSWD = "database.password";
  public static final String JDBCURL = "database.jdbcurl";

  private SpeedmentSingleton() {
  }

  private static VaadinApplication app;

  public static VaadinApplication instance() {

    //TODO not thread save
    if(app == null){
      app = SpeedmentFunctions
          .newInstance()
          .apply(
              System.getProperty(JDBCURL),
              System.getProperty(USERNAME),
              System.getProperty(PASSWD)
              );
    }
    return app;
  }
}
