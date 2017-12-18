package org.rapidpm.vaadin.trainer.persistence.speedment;

import org.rapidpm.frp.Transformations;
import org.rapidpm.frp.functions.TriFunction;

import java.util.function.Function;

/**
 *
 */
public interface SpeedmentFunctions {

//  String JDBC_URL = "jdbcurl";
//  String USERNAME = "username";
//  String PASSWD   = "passwd";
//
//  //TODO !!!!  not secure !!!!
//  Speedment app = newInstanceCurried()
//      .apply(System.getProperty(JDBC_URL, "jdbc:postgresql://localhost:5432/postgres"))
//      .apply(System.getProperty(USERNAME, "postgres"))
//      .apply(System.getProperty(PASSWD, "postgres"));


  public static TriFunction<String, String, String, VaadinApplication> newInstance(){
    return (url, user, passwd) -> new VaadinApplicationBuilder()
        .withPassword(passwd)
        .withUsername(user)
        .withConnectionUrl(url)
        .build();
  }

  public static Function<String, Function<String, Function<String, VaadinApplication>>> newInstanceCurried(){
    return Transformations
        .<String, String, String, VaadinApplication>curryTriFunction()
        .apply(newInstance());
  }

}
