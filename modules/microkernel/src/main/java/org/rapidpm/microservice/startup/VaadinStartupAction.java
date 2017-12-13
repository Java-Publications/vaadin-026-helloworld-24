package org.rapidpm.microservice.startup;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.rapidpm.microservice.Main;
import org.rapidpm.microservice.MainUndertow;

import java.util.Optional;

import static java.lang.System.setProperty;
import static org.rapidpm.microservice.MainUndertow.SHIRO_ACTIVE_PROPERTY;

/**
 *
 */
public class VaadinStartupAction implements Main.MainStartupAction {
  @Override
  public void execute(Optional<String[]> args) {
    setProperty(SHIRO_ACTIVE_PROPERTY, "true");
    // activate Shiro
    IniSecurityManagerFactory factory         = new IniSecurityManagerFactory();
    SecurityManager           securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);
  }
}
