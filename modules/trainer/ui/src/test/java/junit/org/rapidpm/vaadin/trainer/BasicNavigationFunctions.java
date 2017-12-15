package junit.org.rapidpm.vaadin.trainer;

import junit.org.rapidpm.vaadin.trainer.login.LoginUITest;
import junit.org.rapidpm.vaadin.trainer.modules.mainview.menu.MenuPageObject;
import org.rapidpm.vaadin.addons.testbench.junit5.pageobject.AbstractVaadinPageObject;

import java.util.function.Function;

/**
 *
 */
public interface BasicNavigationFunctions {


  static <T extends AbstractVaadinPageObject> Function<T, T> restart() {
    return (po) -> {
      po.restartApplication();
      return po;
    };
  }

  static <T extends AbstractVaadinPageObject> Function<T, T> load() {
    return (po) -> {
      po.loadPage();
      return po;
    };
  }

  static <T extends AbstractVaadinPageObject> Function<T, MenuPageObject> login() {
    return (po) -> {
      final LoginUITest.LoginUIPageObject login = new LoginUITest.LoginUIPageObject(po.getDriver());
      login.login.get().setValue("sven");
      login.password.get().setValue("sven");
      login.buttonOK.get().click();
      return new MenuPageObject(po.getDriver());
    };
  }

  static <T extends AbstractVaadinPageObject> Function<T, MenuPageObject> loginToMenue() {
    return (pageObject) -> restart()
        .andThen(load())
        .andThen(login())
        .apply(pageObject);
  }


}
