package org.rapidpm.vaadin.trainer.modules.mainview.report;

import com.vaadin.ui.*;
import org.rapidpm.vaadin.trainer.api.model.CalcResult;
import org.rapidpm.vaadin.trainer.api.model.User;
import org.rapidpm.vaadin.trainer.api.property.PropertyService;
import org.rapidpm.vaadin.trainer.api.report.ReportService;
import org.rapidpm.vaadin.trainer.api.security.user.UserService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.rapidpm.vaadin.ComponentIDGenerator.comboBoxID;
import static org.rapidpm.vaadin.ComponentIDGenerator.gridID;

/**
 *
 */
public class ReportComponent extends Composite {
  public static final String REPORT_STATISTITICS_CB_USER                  = "report.component.statistics.cb.userselect";
  public static final String REPORT_STATISTITICS_CB_USER_NOSELECTION      = "report.component.statistics.cb.userselect.noselection";
  public static final String REPORT_STATISTITICS_CB_USER_NOSELECTION_DESC = "report.component.statistics.cb.userselect.noselection.description";

  public static final String REPORT_STATISTITICS_GRID    = "report.component.statistics.grid";
  public static final String REPORT_STATISTITICS_GRID_ID = "report.component.statistics.grid.column.id";

  public static final String REPORT_STATISTITICS_GRID_OP_A           = "report.component.statistics.grid.column.op.a";
  public static final String REPORT_STATISTITICS_GRID_OPERATOR       = "report.component.statistics.grid.column.operator";
  public static final String REPORT_STATISTITICS_GRID_OP_B           = "report.component.statistics.grid.column.op.b";
  public static final String REPORT_STATISTITICS_GRID_RESULT_HUMAN   = "report.component.statistics.grid.column.result.human";
  public static final String REPORT_STATISTITICS_GRID_RESULT_MACHINE = "report.component.statistics.grid.column.result.machine";
  public static final String REPORT_STATISTITICS_GRID_RESULT_OK      = "report.component.statistics.grid.column.result.ok";

  private @Inject PropertyService propertyService;
  private @Inject ReportService   reportService;
  private @Inject UserService     userService;

  private final Grid<CalcResult> statisticsGrid = new Grid<>();
  private final Button           clickMe        = new Button("load data");
  private final Button           clearData      = new Button("clear grid");
  private final ComboBox<String> userComboBox   = new ComboBox<>();


  public ReportComponent() {
    statisticsGrid.setColumnReorderingAllowed(true);
    statisticsGrid.setSizeFull();

    final VerticalLayout layout = new VerticalLayout(
        new HorizontalLayout(userComboBox, clickMe, clearData),
        statisticsGrid
    );

    setCompositionRoot(layout);
  }

  private String property(String key) { return propertyService.resolve(key); }

  @PostConstruct
  private void postConstruct() {

    statisticsGrid.setId(gridID().apply(this.getClass(), REPORT_STATISTITICS_GRID));

    statisticsGrid.addColumn(CalcResult::getId)
                  .setCaption(property(REPORT_STATISTITICS_GRID_ID));
    statisticsGrid.addColumn(CalcResult::getOpA)
                  .setCaption(property(REPORT_STATISTITICS_GRID_OP_A));
    statisticsGrid.addColumn(CalcResult::getOperator)
                  .setCaption(property(REPORT_STATISTITICS_GRID_OPERATOR));
    statisticsGrid.addColumn(CalcResult::getOpB)
                  .setCaption(property(REPORT_STATISTITICS_GRID_OP_B));
    statisticsGrid.addColumn(CalcResult::getResultHuman)
                  .setCaption(property(REPORT_STATISTITICS_GRID_RESULT_HUMAN));
    statisticsGrid.addColumn(CalcResult::getResultMachine)
                  .setCaption(property(REPORT_STATISTITICS_GRID_RESULT_MACHINE));
    statisticsGrid.addColumn(CalcResult::getResultOK)
                  .setCaption(property(REPORT_STATISTITICS_GRID_RESULT_OK));

    clickMe.addClickListener((Button.ClickListener) event -> {
      final String login = userComboBox.getSelectedItem().orElse("");
      if (login.isEmpty()) Notification.show(
          property(REPORT_STATISTITICS_CB_USER_NOSELECTION),
          property(REPORT_STATISTITICS_CB_USER_NOSELECTION_DESC),
          Notification.Type.WARNING_MESSAGE
      );
      else {
        Stream<CalcResult> data = reportService.loadAllResultsFor(login);
        statisticsGrid.setItems(data);
      }

    });

    clearData.addClickListener((Button.ClickListener) event -> statisticsGrid.setItems(emptyList()));


    userComboBox.setId(comboBoxID().apply(this.getClass(), REPORT_STATISTITICS_CB_USER));
    userComboBox.setCaption(property("report.component.statistics.userselect"));

    userComboBox.setItems(userService.loadAllUsersStream().map(User::login));

  }
}
