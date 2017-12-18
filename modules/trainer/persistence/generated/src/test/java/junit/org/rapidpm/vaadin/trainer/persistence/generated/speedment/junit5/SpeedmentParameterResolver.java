package junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5;

import org.junit.jupiter.api.extension.*;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.trainer.persistence.speedment.VaadinApplication;

import static junit.org.rapidpm.vaadin.trainer.persistence.generated.speedment.junit5.SpeedmentExtension.SPEEDMENT;
import static org.rapidpm.vaadin.addons.testbench.junit5.extensions.ExtensionFunctions.store;

/**
 *
 */
public class SpeedmentParameterResolver implements ParameterResolver {

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.getParameter().isAnnotationPresent(SpeedmentApp.class);
  }

  @Override
  public VaadinApplication resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return
        ((Result<VaadinApplication>) store()
            .apply(extensionContext)
            .get(SPEEDMENT))
            .get();
  }


}
