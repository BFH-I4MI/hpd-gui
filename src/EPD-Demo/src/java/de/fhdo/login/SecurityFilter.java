/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.login;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Login Filter. Leitet auf die Loginseite um wenn der Benutzer nicht
 * eingelogged ist.
 *
 * @author mathias aschhoff
 * @see http://forums.sun.com/thread.jspa?threadID=5377392
 * @see Markus Stäuble, Hans Jürgen Schumacher -ZK Developers Guide 2008 Packt
 * Publishing Ltd. S 100ff
 */
public class SecurityFilter implements Filter
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  private static String lastreq = "";

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {

    logger.debug("doFilter()");
    /**
     * Wenn benutzer nicht eingelogged -> Loginpage Andernfalls -> Anfrage
     * beantworten
     */

    lastreq = (((HttpServletRequest) request).getRequestURI());

    //if (!Authentication.getInstance().isLoggedIn(request, response))
    /*if (isMaintenance(request))
    {
      request.getRequestDispatcher("/index.zul").forward(request, response);
    }
    else */
    if (isLoggedIn(request, response))
    {
      chain.doFilter(request, response);
    }
    else
    {
      request.getRequestDispatcher("/index.zul").forward(request, response);
    }

  }

  /*private boolean isMaintenance(ServletRequest request)
  {
    if (SystemSettings.getInstance().isMaintenance())
    {
      HttpSession lsession = ((HttpServletRequest) request).getSession();
      if (lsession != null)
      {
        if (lsession.getAttribute("logged_in_user") != null)
        {
          if (lsession.getAttribute("is_admin") != null)
          {
            boolean isAdmin = (Boolean)lsession.getAttribute("is_admin");
            if(isAdmin)
              return false;
          }
        }
      }

      return true;
    }

    return false;
  }*/

  private boolean isLoggedIn(ServletRequest request, ServletResponse response)
  {
    HttpSession lsession = ((HttpServletRequest) request).getSession();
    if (lsession == null)
    {
      logger.debug("SecurityFilter: isLoggedIn() - lsession ist null");
      return false;
    }
    else if (lsession.getAttribute("session_id") != null)
    {
      logger.debug("SecurityFilter: isLoggedIn() - logged_in_user != null, also Erfolg");
      return true;
    }
    else
    {
      logger.debug("SecurityFilter: isLoggedIn() - kein Erfolg");
    }

    return false;
    //return true;
  }

  /*
   *  Werden vorerst nicht benötigt
   */
  public void init(FilterConfig filterConfig) throws ServletException
  {

  }

  public void destroy()
  {

  }

  /**
   * @return the lastreq
   */
  public static String getLastreq()
  {
    return lastreq;
  }

}
