package de.fhdo.helper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Robert Mützner (robert.muetzner@fh-dortmund.de)
 */
public class CookieHelper
{
  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  public static void setCookie(String name, String value)
  {
    logger.debug("Speicher Cookie: " + name + ", mit Wert: " + value);

    ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(new Cookie(
      name, value));
  }

  public static String getCookie(String name)
  {
    try
    {
      Cookie[] cookies = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();

      logger.debug("Suche Cookie mit Name: " + name);

      if (cookies != null)
      {
        for (Cookie cookie : cookies)
        {
          if (cookie.getName().equals(name))
          {
            if (cookie.getValue() != null && cookie.getValue().length() > 0)
            {
              logger.debug("Cookie '" + name + "' gefunden, Wert: " + cookie.getValue());

              return cookie.getValue();
            }
          }
        }
      }
      else
      {
      }

      logger.debug("Kein Cookie fuer '" + name + "' gefunden!");
    }
    catch (Exception ex)
    {
      logger.debug("Cookie konnte nicht geladen werden, evtl. deaktiviert? Fehler: " + ex.getLocalizedMessage());
    }

    return null;
  }

  public static void removeCookie(String name)
  {
    logger.debug("Loesche Cookie: " + name);

    Cookie c = new Cookie(name, "");
    c.setMaxAge(0);

    ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(c);
  }
}
