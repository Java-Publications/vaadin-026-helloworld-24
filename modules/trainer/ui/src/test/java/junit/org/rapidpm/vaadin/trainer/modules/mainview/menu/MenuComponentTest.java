package junit.org.rapidpm.vaadin.trainer.modules.mainview.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.rapidpm.vaadin.addons.testbench.junit5.extensions.unittest.VaadinUnitTest;
import org.rapidpm.vaadin.addons.testbench.junit5.pageobject.PageObject;

/**
 *
 */
@VaadinUnitTest
public class MenuComponentTest {


  @Test
  @DisplayName("exit and login again")
  void test002(@PageObject MenuPageObject pageObject) {
    pageObject.start();
    pageObject.exitButton().click();

    pageObject.exitCancelButton().click();
    pageObject.exitButton().click();
    pageObject.exitOKButton().click();

    pageObject.start();
  }


  @Test
  @DisplayName("menu - dashboard button")
  void test003(@PageObject MenuPageObject pageObject) {
    pageObject.start();
    pageObject.dashboardButton().click();
    // check something usefull
  }

  @Test
  @DisplayName("menu - calculate button")
  void test004(@PageObject MenuPageObject pageObject) {
    pageObject.start();
    pageObject.calculateButton().click();
    // check something usefull
  }

  @Test
  @DisplayName("menu - write button")
  void test005(@PageObject MenuPageObject pageObject) {
    pageObject.start();
    pageObject.writeButton().click();
    // check something usefull
  }

  @Test
  @DisplayName("menu - report button")
  void test006(@PageObject MenuPageObject pageObject) {
    pageObject.start();
    pageObject.reportButton().click();
    // check something usefull
  }




}
