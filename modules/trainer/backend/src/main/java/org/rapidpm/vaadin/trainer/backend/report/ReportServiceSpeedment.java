package org.rapidpm.vaadin.trainer.backend.report;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.vaadin.trainer.api.model.CalcResult;
import org.rapidpm.vaadin.trainer.api.model.Statistics;
import org.rapidpm.vaadin.trainer.api.report.ReportService;
import org.rapidpm.vaadin.trainer.backend.speedment.SpeedmentSingleton;
import org.rapidpm.vaadin.trainer.persistence.speedment.CRUDFunctions;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplication;

import java.util.stream.Stream;

/**
 *
 */
public class ReportServiceSpeedment implements
                                    HasLogger,
                                    ReportService {

  private static final VaadinApplication APPLICATION = SpeedmentSingleton.instance();

  @Override
  public Stream<Statistics> loadStatistics() {
    return Stream.empty();
  }

  public Stream<CalcResult> loadAllResultsFor(String login) {
    return ((CRUDFunctions) () -> APPLICATION)
        .calResultsForLogin()
        .apply(login);
  }

  @Override
  public void storeCalcResult(CalcResult result) {
    ((CRUDFunctions) () -> APPLICATION)
        ._persistCalcResult()
        .accept(result);
  }

}
