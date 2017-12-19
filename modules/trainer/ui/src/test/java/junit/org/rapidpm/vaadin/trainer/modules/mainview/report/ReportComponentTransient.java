package junit.org.rapidpm.vaadin.trainer.modules.mainview.report;

import com.vaadin.ui.*;
import org.rapidpm.vaadin.trainer.api.model.Statistics;
import org.rapidpm.vaadin.trainer.api.property.PropertyService;
import org.rapidpm.vaadin.trainer.api.report.ReportService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.rapidpm.vaadin.ComponentIDGenerator.gridID;

/**
 *
 */
public class ReportComponentTransient extends Composite {

  public static final String REPORT_STATISTITICS_GRID            = "report.component.statistics.grid";
  public static final String REPORT_STATISTITICS_GRID_ID         = "report.component.statistics.grid.column.id";
  public static final String REPORT_STATISTITICS_GRID_USERNAME   = "report.component.statistics.grid.column.username";
  public static final String REPORT_STATISTITICS_GRID_FORENAME   = "report.component.statistics.grid.column.forename";
  public static final String REPORT_STATISTITICS_GRID_FAMILYNAME = "report.component.statistics.grid.column.familyname";
  public static final String REPORT_STATISTITICS_GRID_LAST_LOGIN = "report.component.statistics.grid.column.lastlogin";

  private @Inject PropertyService propertyService;
  private @Inject ReportServiceInMemory   reportService;

  private final Grid<Statistics> statisticsGrid = new Grid<>();
  private final Button           clickMe        = new Button("consume ReportService");
  private final Button           clearData      = new Button("clear data");


  public ReportComponentTransient() {
    statisticsGrid.setColumnReorderingAllowed(true);
    statisticsGrid.setSizeFull();

    final VerticalLayout layout = new VerticalLayout(
        new HorizontalLayout(clickMe, clearData),
        statisticsGrid
    );

    setCompositionRoot(layout);
  }

  private String property(String key) { return propertyService.resolve(key); }

  @PostConstruct
  private void postConstruct() {

    statisticsGrid.setId(gridID().apply(ReportComponentTransient.class, REPORT_STATISTITICS_GRID));

    statisticsGrid.addColumn(Statistics::id)
                  .setCaption(property(REPORT_STATISTITICS_GRID_ID));
    statisticsGrid.addColumn(Statistics::foreName)
                  .setCaption(property(REPORT_STATISTITICS_GRID_FORENAME));
    statisticsGrid.addColumn(Statistics::familyName)
                  .setCaption(property(REPORT_STATISTITICS_GRID_FAMILYNAME));
    statisticsGrid.addColumn(Statistics::lastLogin)
                  .setCaption(property(REPORT_STATISTITICS_GRID_LAST_LOGIN));

    clickMe.addClickListener((Button.ClickListener) event -> {
      Stream<Statistics> data = reportService.loadStatistics();
      statisticsGrid.setItems(data);
    });

    clearData.addClickListener((Button.ClickListener) event -> statisticsGrid.setItems(emptyList()));
  }

}

