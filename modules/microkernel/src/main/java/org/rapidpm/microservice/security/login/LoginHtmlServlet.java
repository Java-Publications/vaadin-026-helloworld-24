package org.rapidpm.microservice.security.login;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.functions.CheckedBiFunction;
import org.rapidpm.frp.functions.CheckedFunction;
import org.rapidpm.frp.model.Result;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

import static org.apache.commons.io.IOUtils.copy;
import static org.rapidpm.frp.StringFunctions.notEmpty;
import static org.rapidpm.frp.matcher.Case.match;
import static org.rapidpm.frp.matcher.Case.matchCase;
import static org.rapidpm.frp.model.Result.failure;
import static org.rapidpm.frp.model.Result.success;
import static org.rapidpm.microservice.security.login.LoginHtmlServlet.*;

@WebServlet(
    asyncSupported = true,
    urlPatterns = {
        SLASH + LOGIN_HTML,
        SLASH + LOGIN_HTML_JUMPSTART,
        SLASH + LOGO_96X96_PNG,
        SLASH + LOGO_128X128_PNG,
        SLASH + LOGO_192X192_PNG,
        SLASH + LOGO_96X96_PNG_JUMPSTART,
        SLASH + LOGO_128X128_PNG_JUMPSTART,
        SLASH + LOGO_192X192_PNG_JUMPSTART
    })
public class LoginHtmlServlet extends HttpServlet implements HasLogger {

  public static final String SLASH           = "/";
  public static final String ICONS           = "icons" + SLASH;
  public static final String ICONS_JUMPSTART = "jumpstart" + SLASH + ICONS;

  public static final String LOGIN_HTML                 = "login.html";
  public static final String LOGIN_HTML_JUMPSTART       = "jumpstart" + SLASH + "login.html";
  public static final String LOGO_96X96_PNG             = ICONS + "icon-96x96.png";
  public static final String LOGO_128X128_PNG           = ICONS + "icon-128x128.png";
  public static final String LOGO_192X192_PNG           = ICONS + "icon-192x192.png";
  public static final String LOGO_96X96_PNG_JUMPSTART   = ICONS_JUMPSTART + "icon-96x96.png";
  public static final String LOGO_128X128_PNG_JUMPSTART = ICONS_JUMPSTART + "icon-128x128.png";
  public static final String LOGO_192X192_PNG_JUMPSTART = ICONS_JUMPSTART + "icon-192x192.png";


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    serveLoginHtml(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    final String username = req.getParameter("username");
    final String password = req.getParameter("password");


    if (notEmpty().test(username) && notEmpty().test(password)) {
      //login and redirect
      System.out.println("validate against [username/password] " + username + " / " + password);
//      RequestDispatcher dispatcher = req.getRequestDispatcher("demo.jsp");
//      dispatcher.forward(req, resp);
      serveLoginHtml(req, resp);
    } else {
      serveLoginHtml(req, resp);
    }
  }

  private CheckedFunction<String, InputStream> asStream() {
    return LoginHtmlServlet.class::getResourceAsStream;
  }

  private CheckedBiFunction<InputStream, HttpServletResponse, Integer> write() {
    return (inputStream, httpServletResponse) -> copy(inputStream, httpServletResponse.getOutputStream());
  }

  private void serveLoginHtml(HttpServletRequest request, HttpServletResponse response) {
    response.setCharacterEncoding("utf-8");
    final String url = request.getRequestURL().toString();
    logger().info("url for = LoginHtmlServlet " + url);
    match(
        matchCase(() -> failure("nothing matched " + url)),
        matchCase(() -> url.contains(LOGIN_HTML), () -> success(SLASH + LOGIN_HTML)),
        matchCase(() -> url.contains(LOGO_96X96_PNG), () -> success(SLASH + LOGO_96X96_PNG)),
        matchCase(() -> url.contains(LOGO_192X192_PNG), () -> success(SLASH + LOGO_192X192_PNG))
    ).ifPresentOrElse(
        resourceToLoad -> {
          final Result<InputStream> ressourceStream = asStream().apply(resourceToLoad);
          ressourceStream.ifPresent(inputStream -> write().apply(inputStream, response));

          ressourceStream.ifAbsent(() -> {
            logger().warning("resource was not available try to load default..");
            String resourceDefault = (resourceToLoad.contains(LOGIN_HTML))
                                     ? resourceToLoad.replace(LOGIN_HTML, LOGIN_HTML_JUMPSTART)
                                     : resourceToLoad.replace(ICONS, ICONS_JUMPSTART);
            final Result<InputStream> defaultResourceStream = asStream().apply(resourceDefault);
            defaultResourceStream.ifPresent(inputStream -> write().apply(inputStream, response));
            defaultResourceStream.ifAbsent(() -> logger().warning("no default resource available .. "));
          });
        },
        failed -> {
          logger().warning("wrong ressource requested .. failed .. " + failed);
        }
    );
  }
}
