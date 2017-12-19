package junit.org.rapidpm.vaadin.trainer.modules.mainview.report;

import org.rapidpm.vaadin.trainer.api.model.CalcResult;
import org.rapidpm.vaadin.trainer.api.model.Statistics;
import org.rapidpm.vaadin.trainer.api.report.ReportService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 */
public class ReportServiceInMemory implements ReportService {


  @Override
  public Stream<Statistics> loadStatistics() {
    return Stream.of(
        new Statistics(1L, "Sven", "Ruppert", LocalDateTime.now()),
        new Statistics(1L, "Max", "Rimkus", LocalDateTime.now())
    );
  }

  @Override
  public Stream<CalcResult> loadAllResultsFor(String login) {
    return Stream.empty();
  }

  @Override
  public void storeCalcResult(CalcResult result) {

  }

}
