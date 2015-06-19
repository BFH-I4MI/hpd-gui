/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.gui;

import de.fhdo.helper.PropertiesHelper;
import de.fhdo.helper.SessionHelper;
import de.fhdo.logging.LoggingOutput;
import java.util.Locale;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class Index extends Window implements AfterCompose
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  Menu menu;

  public Index()
  {
    // always german language
    Locale prefer_locale = new Locale("de");
    SessionHelper.setValue(org.zkoss.web.Attributes.PREFERRED_LOCALE, prefer_locale);
  }

  public void afterCompose()
  {
    menu = new Menu(getFellow("incMenu"), this);

    String src = SessionHelper.getString("content");
    if (src != null && src.length() > 0)
    {
      changeModule(src, SessionHelper.getString("contentTitle"));
    }
  }

  public void changeModule(String src, String title)
  {
    logger.debug("changeModule, src: " + src);

    //SessionHelper.setValue(org.zkoss.web.Attributes.PREFERRED_LOCALE, prefer_locale);
    try
    {
      String src_to = src;

      Object o = SessionHelper.getValue(org.zkoss.web.Attributes.PREFERRED_LOCALE);
      if (o != null)
      {
        Locale locale = (Locale) o;
        logger.debug("locale: " + locale.getLanguage());
        src_to = src_to + "&locale=" + locale.getLanguage();
      }
      
      String sessionId = SessionHelper.getSessionId();
      if(sessionId != null && sessionId.length() > 0)
      {
        src_to = src_to + "&sessionId=" + sessionId;
      }
      
//      String username = PropertiesHelper.getInstan+

      logger.debug("src: " + src);
      logger.debug("src_to: " + src_to);
      
      // src: http://localhost:8080/TermBrowser/gui/main/main.zul?hideMenu=true&amp;hideStatusbar=true

      //CookieHelper.setCookie("test", "1234");
      //HttpSession session = (HttpSession) Sessions.getCurrent().getNativeSession();
      //session.setAttribute("test", "1234");
      if (src.startsWith("http"))
      {
        //Clients.showBusy("Bitte warten...");

        Iframe mainFrame = (Iframe) getFellow("mainFrame");
        mainFrame.setSrc(null);
        mainFrame.setSrc(src_to);
        mainFrame.setVisible(true);
        //mainFrame.setAttribute("test", "12345");
        
        
//        Clients.evalJavaScript("mainFrame.contentWindow.postMessage('hello', '*');");
//
//        for (Component comp : mainFrame.getChildren())
//        {
//          logger.debug("Parent: " + comp.getClass().getCanonicalName());
//
//          for (Component comp2 : comp.getChildren())
//          {
//            logger.debug("Parent 2: " + comp2.getClass().getCanonicalName());
//          }
//        }

        getFellow("mainWindowCenter").setVisible(false);
      }
      else
      {
        Include inc = (Include) getFellow("mainWindowCenter");
        inc.setSrc(null);  // needed for reloading source
        inc.setSrc(src_to);
        inc.setVisible(true);
        getFellow("mainFrame").setVisible(false);
      }

      menu.setTitle(title);

      SessionHelper.setValue("content", src);
      SessionHelper.setValue("contentTitle", title);
    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, this);
    }

  }

  public void openURL(String url)
  {

    //Executions.sendRedirect(url, "_blank");
    Executions.getCurrent().sendRedirect(url, "_blank");
    //Executions.getCurrent().s
  }

}
