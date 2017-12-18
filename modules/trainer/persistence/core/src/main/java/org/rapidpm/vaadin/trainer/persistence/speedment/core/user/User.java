package org.rapidpm.vaadin.trainer.persistence.speedment.core.user;

import org.rapidpm.frp.model.serial.Quad;

/**
 *
 */
public class User extends Quad<Long, String, String, String> {
  public User(Long id, String login, String forename, String familyname) {
    super((id == null) ? -1L : id, login, forename, familyname);
  }

  public Long id() { return getT1();}

  public String login() {return getT2();}

  public String forename() {return getT3();}

  public String familyname() {return getT4();}


}
