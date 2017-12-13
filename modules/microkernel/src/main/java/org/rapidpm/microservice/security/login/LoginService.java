package org.rapidpm.microservice.security.login;

/**
 *
 */
public interface LoginService {
  boolean check(String login, String password);
}
