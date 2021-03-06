package org.rapidpm.vaadin.trainer.modules.mainview.menu;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.rapidpm.frp.model.Pair;
import org.rapidpm.vaadin.trainer.api.property.PropertyService;
import org.rapidpm.vaadin.trainer.modules.mainview.calc.CalcComponent;
import org.rapidpm.vaadin.trainer.modules.mainview.dashboard.DashboardComponent;
import org.rapidpm.vaadin.trainer.modules.mainview.report.ReportComponent;
import org.rapidpm.vaadin.trainer.modules.mainview.write.WriteComponent;
import org.vaadin.dialogs.ConfirmDialog;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.vaadin.icons.VaadinIcons.*;
import static com.vaadin.ui.themes.ValoTheme.*;
import static org.apache.shiro.SecurityUtils.getSubject;
import static org.rapidpm.ddi.DI.activateDI;
import static org.rapidpm.vaadin.ComponentIDGenerator.buttonID;
import static org.rapidpm.vaadin.server.SessionAttributes.SESSION_ATTRIBUTE_USER;

/**
 *
 */
public class MenuComponent extends Composite {

  public static final String MENU_POINT_DASHBOARD    = "menu.point.dashboard";
  public static final String MENU_POINT_CALCULATE    = "menu.point.calculate";
  public static final String MENU_POINT_WRITE        = "menu.point.write";
  public static final String MENU_POINT_REPORT       = "menu.point.report";
  public static final String MENU_POINT_EXIT         = "menu.point.exit";
  public static final String MENU_POINT_EXIT_MESSAGE = "menu.point.exit.message";
  public static final String MENU_BTN_WIDTH          = "100%";

  public static final String MENU_BUTTON_ID_CACULATE  = buttonID().apply(MenuComponent.class, MENU_POINT_CALCULATE);
  public static final String MENU_BUTTON_ID_WRITE     = buttonID().apply(MenuComponent.class, MENU_POINT_WRITE);
  public static final String MENU_BUTTON_ID_REPORT    = buttonID().apply(MenuComponent.class, MENU_POINT_REPORT);
  public static final String MENU_BUTTON_ID_DASHBOARD = buttonID().apply(MenuComponent.class, MENU_POINT_DASHBOARD);
  public static final String MENU_BUTTON_ID_EXIT      = buttonID().apply(MenuComponent.class, MENU_POINT_EXIT);


  private final Layout contentLayout;

  public MenuComponent(Layout contentLayout) {this.contentLayout = contentLayout;}


  @Inject private PropertyService propertyService;

  private String property(String key) {
    return propertyService.resolve(key);
  }

  @PostConstruct
  private void postConstruct() {
    final CssLayout menuButtons = new CssLayout();
    menuButtons.setSizeFull();
    menuButtons.addStyleName(MENU_PART);
    menuButtons.addStyleName(MENU_PART_LARGE_ICONS);
    menuButtons.addComponents(getComponents());
    setCompositionRoot(menuButtons);
  }

  private Component[] getComponents() {
    return Stream
        .of(
            createMenuButton(VIEWPORT, MENU_POINT_DASHBOARD, DashboardComponent::new),
            createMenuButton(ABACUS, MENU_POINT_CALCULATE, CalcComponent::new),
            createMenuButton(EDIT, MENU_POINT_WRITE, WriteComponent::new),
            createMenuButton(BAR_CHART, MENU_POINT_REPORT, ReportComponent::new),

            createMenuButtonForNotification(EXIT, MENU_POINT_EXIT, MENU_POINT_EXIT_MESSAGE)
        )
        .filter(p -> getSubject().isPermitted(p.getT1()))
        .map(Pair::getT2)
        .map(Component.class::cast)
        .toArray(Component[]::new);
  }


  //TODO more generic - refactoring
  private Pair<String, Button> createMenuButtonForNotification(VaadinIcons icon,
                                                               String caption,
                                                               String message) {
    final Button button
        = new Button(property(caption),
                     (e) -> {
                       UI ui = UI.getCurrent();
                       ConfirmDialog.show(
                           ui,
                           property(message),
                           (ConfirmDialog.Listener) dialog -> {
                             if (dialog.isConfirmed()) {
                               getSubject().logout();
                               VaadinSession vaadinSession = ui.getSession();
                               vaadinSession.setAttribute(SESSION_ATTRIBUTE_USER, null);
                               vaadinSession.close();
                               ui.getPage().setLocation("/");
                             } else {
                               // VaadinUser did not confirm
                               // CANCEL STUFF
                             }
                           });
                     });

    button.setIcon(icon);
    button.addStyleName(BUTTON_HUGE);
    button.addStyleName(BUTTON_ICON_ALIGN_TOP);
    button.addStyleName(BUTTON_BORDERLESS);
    button.addStyleName(MENU_ITEM);
    button.setWidth(MENU_BTN_WIDTH);

    button.setId(buttonID().apply(MenuComponent.class, caption));

    return new Pair<>(mapToShiroRole(caption), button);

  }


  private Pair<String, Button> createMenuButton(VaadinIcons icon,
                                                String caption,
                                                Supplier<Composite> content) {
    final Button button = new Button(property(caption), (e) -> {
      contentLayout.removeAllComponents();
      contentLayout.addComponent(activateDI(content.get()));
    });
    button.setIcon(icon);
    button.addStyleName(BUTTON_HUGE);
    button.addStyleName(BUTTON_ICON_ALIGN_TOP);
    button.addStyleName(BUTTON_BORDERLESS);
    button.addStyleName(MENU_ITEM);
    button.setWidth(MENU_BTN_WIDTH);

    button.setId(buttonID().apply(this.getClass(), caption));
    return new Pair<>(mapToShiroRole(caption), button);
  }

  //not nice
  //TODO how to connect to Shiro?
  private String mapToShiroRole(String caption) {
    return (caption.equals(MENU_POINT_CALCULATE)) ? "math:CalcComponent" :
           (caption.equals(MENU_POINT_WRITE)) ? "write:WriteComponent" :
           (caption.equals(MENU_POINT_REPORT)) ? "report:*" :
           "all:default";
  }

}
