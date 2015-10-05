/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.gui;

import de.fhdo.helper.PropertiesHelper;
import de.fhdo.logging.LoggingOutput;
import de.fhdo.login.UsernamePasswordLogin;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class Menu
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  private Component window;
  private boolean initialized = false;
  Index parentWindow;

  public Menu(Component _window, Index _parentWindow)
  {
    window = _window;
    parentWindow = _parentWindow;
    init();
  }

  public void init()
  {
    if (initialized)
      return;

    //Menubar menubar = (Menubar) window.getFellow("menubar");
    //System.out.println("ID: " + menubar.getId());
    logger.debug("getRemoteAddr: " + Executions.getCurrent().getRemoteAddr());
    logger.debug("getRemoteHost: " + Executions.getCurrent().getRemoteHost());
    logger.debug("getRemoteUser: " + Executions.getCurrent().getRemoteUser());

    logger.debug("getLocalAddr: " + Executions.getCurrent().getLocalAddr());
    logger.debug("getLocalName: " + Executions.getCurrent().getLocalName());
    logger.debug("getLocalPort: " + Executions.getCurrent().getLocalPort());

    logger.debug("getContextPath: " + Executions.getCurrent().getContextPath());
    logger.debug("getBrowser: " + Executions.getCurrent().getBrowser());
    logger.debug("getScheme: " + Executions.getCurrent().getScheme());
    logger.debug("getServerName: " + Executions.getCurrent().getServerName());
    logger.debug("getUserAgent: " + Executions.getCurrent().getUserAgent());

    EventListener mdiEvent = new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        //String url = "http://" + Executions.getCurrent().getServerName() + ":8080/TermBrowser/gui/main/main.zul?hideMenu=true&amp;hideStatusbar=true";
        String url = PropertiesHelper.getInstance().getMenuMdiUrl() + "gui/main/main.zul?hideMenu=true&hideStatusbar=true";
        logger.debug("URL: " + url);
        parentWindow.changeModule(url, Labels.getLabel("mdiLong"));
      }
    };

    EventListener hoiEvent = new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        //String url = "http://" + Executions.getCurrent().getServerName() + ":8080/HOI_HPI/Portal/listview.zul?mode=hoi";
        String url = PropertiesHelper.getInstance().getMenuHoiUrl() + "Portal/listview.zul?mode=hoi";
        logger.debug("URL: " + url);
        parentWindow.changeModule(url, Labels.getLabel("hoiLong"));
      }
    };

    EventListener hpiEvent = new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        //String url = "http://" + Executions.getCurrent().getServerName() + ":8080/HOI_HPI/Portal/listview.zul?mode=hpi";
        String url = PropertiesHelper.getInstance().getMenuHpiUrl() + "Portal/listview.zul?mode=hpi";
        logger.debug("URL: " + url);
        parentWindow.changeModule(url, Labels.getLabel("hpiLong"));
      }
    };

    EventListener logoutEvent = new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        logger.debug("logout...");
        UsernamePasswordLogin.doLogout();
      }
    };

    // Show help configured in property file
    boolean showHelp = PropertiesHelper.getInstance().getMenuHelpUrl() != null
            && PropertiesHelper.getInstance().getMenuHelpUrl().length() > 0;
    Menuitem miHelp = ((Menuitem) window.getFellow("menuItemHelp"));
    if (showHelp)
    {
      miHelp.setHref(PropertiesHelper.getInstance().getMenuHelpUrl()); 
    }
    miHelp.setVisible(showHelp);
    window.getFellow("menuHelpSeparator").setVisible(showHelp);

    Toolbarbutton tbbMDI = (Toolbarbutton) window.getFellow("tbbMDI");
    Toolbarbutton tbbHOI = (Toolbarbutton) window.getFellow("tbbHOI");
    Toolbarbutton tbbHPI = (Toolbarbutton) window.getFellow("tbbHPI");
    tbbMDI.addEventListener(Events.ON_CLICK, mdiEvent);
    tbbHOI.addEventListener(Events.ON_CLICK, hoiEvent);
    tbbHPI.addEventListener(Events.ON_CLICK, hpiEvent);

    ((Menuitem) window.getFellow("menuItemMDI")).addEventListener(Events.ON_CLICK, mdiEvent);
    ((Menuitem) window.getFellow("menuItemHOI")).addEventListener(Events.ON_CLICK, hoiEvent);
    ((Menuitem) window.getFellow("menuItemHPI")).addEventListener(Events.ON_CLICK, hpiEvent);

    ((Menuitem) window.getFellow("menuItemLogout")).addEventListener(Events.ON_CLICK, logoutEvent);

    EventListener aboutEvent = new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        try
        {
          Window win = (Window) Executions.createComponents(
                  "/Portal/about.zul", parentWindow, null);

          win.doModal();
          //win.doHighlighted();
        }
        catch (Exception ex)
        {
          LoggingOutput.outputException(ex, this);
        }
      }
    };
    ((Menuitem) window.getFellow("menuItemAbout")).addEventListener(Events.ON_CLICK, aboutEvent);
  }

  public void setTitle(String title)
  {
    String s = "EPD-Demo - " + title;
    ((Label) window.getFellow("labelTitle")).setValue(s);
  }

}
