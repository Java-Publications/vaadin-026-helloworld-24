package junit.org.rapidpm.vaadin.trainer.modules.mainview.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.addons.testbench.junit5.extensions.unittest.VaadinUnitTest;
import org.rapidpm.vaadin.addons.testbench.junit5.pageobject.PageObject;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.failure;
import static org.rapidpm.frp.model.Result.success;

/**
 *
 */
@VaadinUnitTest
public class CalcComponentTest {

  //  @DisplayName("5 + 5 = 10")
  @Test
  void test001(@PageObject CalcComponentPageObject pageObject) {

    pageObject.start();

    final Result<Integer> rValueA = pageObject.valueOneAsInt();
    final Result<Integer> rValueB = pageObject.valueTwoAsInt();
    final String          op      = pageObject.labelOperator().getText();

    assertAll(
        "Check if values are present",
        () -> assertTrue(rValueA.isPresent()),
        () -> assertTrue(rValueB.isPresent()),
        () -> assertTrue(op != null)
    );

    Result<BiFunction<Integer, Integer, Float>> result =
        match(
            matchCase(() -> failure("no matching operator found for input " + op)),
            matchCase(op::isEmpty, () -> failure("should not be empty")),
            matchCase(() -> op.equals("+"), () -> success((a, b) -> (float) a + b)),
            matchCase(() -> op.equals("*"), () -> success((a, b) -> (float) a * b)),
            matchCase(() -> op.equals(":"), () -> success((a, b) -> (float) a / b)),
            matchCase(() -> op.equals("-"), () -> success((a, b) -> (float) a - b))
        );

    result.ifFailed(Assertions::fail);

    final Float expectedValue = result
        .get()
        .apply(rValueA.get(), rValueB.get());

    pageObject
        .tfValueResultUser()
        .setValue(String.valueOf(expectedValue));

    pageObject.buttonOK().click();

    final Result<Float> rValueResultHuman   = pageObject.valueResultHumanAsFloat();
    final Result<Float> rValueResultMachine = pageObject.valueResultMachineAsFloat();

    assertAll(
        "Check if result values are present",
        () -> assertTrue(rValueResultMachine.isPresent()),
        () -> assertTrue(rValueResultHuman.isPresent())
    );

    Assertions.assertEquals(rValueResultHuman.get(),
                            rValueResultMachine.get());

  }
}
