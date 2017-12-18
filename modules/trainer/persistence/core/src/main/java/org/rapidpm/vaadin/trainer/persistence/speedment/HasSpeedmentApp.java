package org.rapidpm.vaadin.trainer.persistence.speedment;

import com.speedment.runtime.core.Speedment;

/**
 *
 */
@FunctionalInterface
public interface HasSpeedmentApp {
  public Speedment app();
}
