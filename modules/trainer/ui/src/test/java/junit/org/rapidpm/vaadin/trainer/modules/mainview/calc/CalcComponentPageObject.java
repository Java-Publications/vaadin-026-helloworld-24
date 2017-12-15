package junit.org.rapidpm.vaadin.trainer.modules.mainview.calc;

import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.TextFieldElement;
import junit.org.rapidpm.vaadin.trainer.login.LoginUITest;
import junit.org.rapidpm.vaadin.trainer.modules.mainview.menu.MenuPageObject;
import org.openqa.selenium.WebDriver;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.addons.testbench.junit5.extensions.unittest.VaadinUnitTest;
import org.rapidpm.vaadin.addons.testbench.junit5.pageobject.AbstractVaadinPageObject;
import org.rapidpm.vaadin.trainer.modules.mainview.calc.CalcComponent;

import java.util.function.Function;

import static org.rapidpm.vaadin.addons.testbench.WebElementFunctions.floatOfTextField;
import static org.rapidpm.vaadin.addons.testbench.WebElementFunctions.intOfTextField;
import static org.rapidpm.vaadin.trainer.modules.mainview.calc.CalcComponent.*;

/**
 *
 */

@VaadinUnitTest
public class CalcComponentPageObject extends AbstractVaadinPageObject {
  public CalcComponentPageObject(WebDriver webDriver) {
    super(webDriver);
  }

  public <T extends AbstractVaadinPageObject> Function<T, T> restart() {
    return (po) -> {
//      po.restartApplication();
      return po;
    };
  }

  public <T extends AbstractVaadinPageObject> Function<T, T> load() {
    return (po) -> {
      po.loadPage();
      return po;
    };
  }

  public <T extends AbstractVaadinPageObject> Function<T, MenuPageObject> login() {
    return (po) -> {
      final LoginUITest.LoginUIPageObject login = new LoginUITest.LoginUIPageObject(po.getDriver());
      login.login.get().setValue("sven");
      login.password.get().setValue("sven");
      login.buttonOK.get().click();
      return new MenuPageObject(po.getDriver());
    };
  }


  public void start() {
    restart()
        .andThen(load())
        .andThen(login())
        .apply(this)
        .calculateButton()
        .click();
  }


  public ButtonElement buttonOK() {
    return btn().id(CalcComponent.BTN_CALC_ID);
  }

  public ButtonElement buttonNext() {
    return btn().id(CalcComponent.BTN_NEXT_ID);
  }

  public TextFieldElement tfValueOne() {
    return textField().id(TF_VALUE_ONE_ID);
  }

  public TextFieldElement tfValueTwo() {
    return textField().id(TF_VALUE_TWO_ID);
  }

  public TextFieldElement tfValueResultUser() {
    return textField().id(TF_RESULT_USER_ID);
  }

  public TextFieldElement tfValueResultMachine() {
    return textField().id(TF_RESULT_MACHINE_ID);
  }

  public LabelElement labelOperator() {
    return label().id(LABEL_OPERATOR_ID);
  }


  //  Converter
  public Result<Integer> valueOneAsInt() {
    return intOfTextField().apply(tfValueOne());
  }

  public Result<Integer> valueTwoAsInt() {
    return intOfTextField().apply(tfValueTwo());
  }

  public Result<Float> valueResultHumanAsFloat() {
    return floatOfTextField().apply(tfValueResultUser());
  }

  public Result<Float> valueResultMachineAsFloat() {
    return floatOfTextField().apply(tfValueResultMachine());
  }


}
