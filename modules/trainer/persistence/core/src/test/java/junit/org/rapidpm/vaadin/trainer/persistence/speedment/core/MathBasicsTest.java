package junit.org.rapidpm.vaadin.trainer.persistence.speedment.core;

import junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentApp;
import junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentExtension;
import junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentParameterResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rapidpm.vaadin.trainer.persistence.speedment.CRUDFunctions;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplication;
import org.rapidpm.vaadin.trainer.persistence.speedment.mainview.calc.CalcResult;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasic;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@ExtendWith(SpeedmentExtension.class)
@ExtendWith(SpeedmentParameterResolver.class)
public class MathBasicsTest {

  @Test
  void test001(@SpeedmentApp VaadinApplication app) {

    final CRUDFunctions crudFunctions = () -> app;


    final CalcResult result = new CalcResult();
//    result.setId();
    result.setOpA(2d);
    result.setOpB(2d);
    result.setOperator("+");
    result.setResultHuman("3");
    result.setResultMachine(4d);
    result.setResultOK(false);
    result.setTimestamp(LocalDateTime.now());

    crudFunctions
        .userWithID()
        .apply(1l)
        .ifPresentOrElse(
            result::setUser,
            Assertions::fail
        );

    crudFunctions
        .saveOrUpdateCalcResult()
        .accept(result);

    final List<CompMathBasic> l = crudFunctions
        ._mathBasics()
        .apply(app)
        .filter(e -> !e.getResultOk())
        .collect(Collectors.toList());


    Assertions.assertFalse(l.isEmpty());
    Assertions.assertEquals(1, l.size());

    final CompMathBasic c = l.get(0);

    Assertions.assertAll(
        ()-> Assertions.assertEquals(2d, c.getOpA()),
        ()-> Assertions.assertEquals(2d, c.getOpB()),
        ()-> Assertions.assertTrue(c.getResultHuman().isPresent()),
        ()-> Assertions.assertEquals("3", c.getResultHuman().get())
    );

  }
}
