package org.rapidpm.microservice.security.login;

import org.apache.shiro.realm.SimpleAccountRealm;

/**
 *
 */
public class JumpstartShiroRealm extends SimpleAccountRealm {


  public JumpstartShiroRealm() {
    super();
    addRole("admin");
    addAccount("sven", "sven");
    addAccount("max", "max");
  }
}
