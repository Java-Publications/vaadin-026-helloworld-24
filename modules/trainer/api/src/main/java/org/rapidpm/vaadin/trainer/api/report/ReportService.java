package org.rapidpm.vaadin.trainer.api.report;

import org.rapidpm.vaadin.trainer.api.model.CalcResult;
import org.rapidpm.vaadin.trainer.api.model.Statistics;

import java.util.stream.Stream;

/**
 *
 */
public interface ReportService {
  Stream<Statistics> loadStatistics();

  Stream<CalcResult> loadAllResultsFor(String login);

  void storeCalcResult(CalcResult result);

}
