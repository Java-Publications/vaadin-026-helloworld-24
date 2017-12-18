package org.rapidpm.vaadin.trainer.persistence.speedment.mainview.calc;

import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.manager.Manager;
import org.rapidpm.vaadin.trainer.persistence.speedment.HasSpeedmentApp;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasic;
import org.rapidpm.vaadin.trainer.persistence.speedment.postgres.public_.comp_math_basic.CompMathBasicManager;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 */
public interface CalcResultFunctions extends HasSpeedmentApp {


  //TODO should be private in Java9
  default Function<Speedment, Stream<CompMathBasic>> _mathBasics() {
    return _mathBasicManager().andThen(_mathBasicStream());
  }


  default Function<CompMathBasicManager, Stream<CompMathBasic>> _mathBasicStream() {
    return Manager::stream;
  }


  default Function<Speedment, CompMathBasicManager> _mathBasicManager() {
    return (app) -> app.getOrThrow(CompMathBasicManager.class);
  }
}
