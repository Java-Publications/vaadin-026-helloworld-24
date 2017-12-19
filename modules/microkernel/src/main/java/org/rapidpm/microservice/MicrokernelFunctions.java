package org.rapidpm.microservice;

import org.rapidpm.frp.functions.CheckedExecutor;
import org.rapidpm.frp.functions.CheckedFunction;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.lang.System.setProperty;

/**
 *
 */
public interface MicrokernelFunctions {


  static CheckedExecutor readDatabaseProperties() {
    return () -> propertyReader()
        .apply("_data/database/database.properties")
        .ifPresent(p -> p.forEach((key, value) -> setProperty((String) key, (String) value))
        );
  }


  static CheckedFunction<String, Properties> propertyReader() {
    return (filename) -> {
      try (
          final FileInputStream fis = new FileInputStream(new File(filename));
          final BufferedInputStream bis = new BufferedInputStream(fis)) {
        final Properties properties = new Properties();
        properties.load(bis);

        return properties;
      } catch (IOException e) {
        e.printStackTrace();
        throw e;
      }
    };
  }
}
