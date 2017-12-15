package junit.org.rapidpm.vaadin.trainer.modules.mainview.menu;

import com.vaadin.testbench.elements.ButtonElement;
import org.openqa.selenium.WebDriver;
import org.rapidpm.vaadin.addons.testbench.junit5.pageobject.AbstractVaadinPageObject;
import org.rapidpm.vaadin.trainer.modules.mainview.menu.MenuComponent;

/**
 *
 */
public class MenuPageObject extends AbstractVaadinPageObject {

  public MenuPageObject(WebDriver webDriver) {
    super(webDriver);
  }

  public ButtonElement dashboardButton(){
    return btn().id(MenuComponent.MENU_BUTTON_ID_DASHBOARD);
  }

  public ButtonElement calculateButton(){
    return btn().id(MenuComponent.MENU_BUTTON_ID_CACULATE);
  }

  public ButtonElement reportButton(){
    return btn().id(MenuComponent.MENU_BUTTON_ID_REPORT);
  }

  public ButtonElement writeButton(){
    return btn().id(MenuComponent.MENU_BUTTON_ID_WRITE);
  }

  public ButtonElement exitButton(){
    return btn().id(MenuComponent.MENU_BUTTON_ID_EXIT);
  }

}
