package junit.org.rapidpm.vaadin.junit5.extensions.pageobject;

import junit.org.rapidpm.vaadin.junit5.AbstractVaadinPageObject;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import org.rapidpm.dependencies.core.logger.HasLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import static junit.org.rapidpm.vaadin.junit5.VaadinPageObject.KEY_VAADIN_SERVER_IP;
import static junit.org.rapidpm.vaadin.junit5.extensions.ExtensionFunctions.ipSupplierLocalIP;
import static junit.org.rapidpm.vaadin.junit5.extensions.testcontainers.TestcontainersExtension.webdriver;

/**
 *
 */
public class PageObjectExtension implements ParameterResolver, BeforeAllCallback, HasLogger {

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return parameterContext.getParameter().isAnnotationPresent(PageObject.class);
  }

  @Override
  public AbstractVaadinPageObject resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {

    Supplier<WebDriver> webDriverSupplier = webdriver().apply(extensionContext);

    Class<?> pageObject = parameterContext.getParameter().getType();
    try {
      Constructor<?> constructor =
          pageObject.getConstructor(WebDriver.class);
      return AbstractVaadinPageObject.class.cast(constructor.newInstance(webDriverSupplier.get()));
    } catch (NoSuchMethodException
        | IllegalAccessException
        | InstantiationException
        | InvocationTargetException e) {
      e.printStackTrace();
      throw new ParameterResolutionException("was not able to create PageObjectInstance ", e);
    }
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    final String userVaadinServerIP = ipSupplierLocalIP.get();
    logger().info(KEY_VAADIN_SERVER_IP + " will be -> " + userVaadinServerIP);
    System.setProperty(KEY_VAADIN_SERVER_IP, userVaadinServerIP);
  }

}
