package de.fhdo.security;

import de.fhdo.helper.ArgumentHelper;
import de.fhdo.helper.SessionHelper;
import de.fhdo.helper.StringHelper;
import de.fhdo.terminologie.ws.authorization.AuthenticateResponse;
import de.fhdo.wsclient.WebserviceHelper;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class SecurityFilter implements Filter
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  private static String lastreq = "";

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
//    logger.debug("doFilter()");
//    logger.debug("getContextPath: " + ((HttpServletRequest) request).getContextPath());
//    logger.debug("getPathInfo: " + ((HttpServletRequest) request).getPathInfo());
//    logger.debug("getRequestURI: " + ((HttpServletRequest) request).getRequestURI());
//    logger.debug("getRequestedSessionId: " + ((HttpServletRequest) request).getRequestedSessionId());
//    logger.debug("getServletPath: " + ((HttpServletRequest) request).getServletPath());
//    logger.debug("getMethod: " + ((HttpServletRequest) request).getMethod());
//    logger.debug("getLocalAddr: " + ((HttpServletRequest) request).getLocalAddr());
//    logger.debug("getLocalName: " + ((HttpServletRequest) request).getLocalName());
//    logger.debug("getParameter: " + ((HttpServletRequest) request).getParameter("sessionId"));
//    logger.debug("getRemoteAddr: " + ((HttpServletRequest) request).getRemoteAddr());
    

    HttpSession session = ((HttpServletRequest) request).getSession(false);
    if (session == null)
      logger.debug("Filter-Session ist null");

    /**
     * Wenn benutzer nicht eingelogged -> Loginpage Andernfalls -> Anfrage
     * beantworten
     */
    lastreq = (((HttpServletRequest) request).getRequestURI().toString());

    if (isLoggedIn(request, response))
    {
      logger.debug("login OK (doFilter)");
      chain.doFilter(request, response);
    }
    else
    {
      logger.debug("login nicht OK (doFilter)");
      request.getRequestDispatcher("/denied.zul").forward(request, response);
    }
  }

  private boolean isLoggedIn(ServletRequest request, ServletResponse servletResponse)
  {
    logger.debug("SecurityFilter - isLoggedIn...");
    
//    HttpSession lsession = ((HttpServletRequest) request).getSession(false);
//    if (lsession == null)
//    {
//      logger.debug("SecurityFilter.java:isLoggedIn() - keine Session");
//      return false;
//    }

    String sessionId = SessionHelper.getString("sessionId");
    if (StringHelper.isNullOrEmpty(sessionId))
    {
      // Versuchen am Terminologieserver anzumelden
      logger.debug("no sessionId found, try logging in...");
      
      
//      String sessionIdParameter = ArgumentHelper.getWindowParameterString("sessionId");
//      logger.debug("sessionIdParameter: " + sessionIdParameter);
      //String sessionIdParameter = SessionHelper.getString("sessionIdParameter");
      String sessionIdParameter = ((HttpServletRequest) request).getParameter("sessionId");
      //logger.debug("getParameter: " + ((HttpServletRequest) request).getParameter("sessionId"));
      logger.debug("sessionIdParameter: " + sessionIdParameter);
      
      if(StringHelper.isNullOrEmpty(sessionIdParameter))
        return false;
      
      AuthenticateResponse.Return response = WebserviceHelper.authenticate(sessionIdParameter);
      
      logger.debug("WS response: " + response.getReturnInfos().getMessage());
      //SessionHelper.setValue("authorizationResponse", response.getReturnInfos().getMessage());

      if (response.getReturnInfos().getStatus() == de.fhdo.terminologie.ws.authorization.Status.OK)
      {
        if (response.getParameterList() != null && response.getParameterList().size() > 0)
          SessionHelper.setValue("sessionId", response.getParameterList().get(0));
        else
          SessionHelper.setValue("sessionId", sessionIdParameter);

        logger.debug("sessionId accepted: " + SessionHelper.getString("sessionId"));
        return true;
      }
      else return false;
    }
    else
    {
      logger.debug("sessionId found -> ok");
      return true;
    }

    //return SessionHelper.isUserLoggedIn(lsession);
  }

  /*
   *  Werden vorerst nicht benötigt
   */
  public void init(FilterConfig filterConfig) throws ServletException
  {
  }

  public void destroy()
  {
    SessionHelper.setValue("sessionId", null);
  }

  /**
   * @return the lastreq
   */
  public static String getLastreq()
  {
    return lastreq;
  }

}
