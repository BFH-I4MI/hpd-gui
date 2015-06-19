/* 
 * CTS2 based Terminology Server and Terminology Browser
 * Copyright (C) 2014 FH Dortmund: Peter Haas, Robert Muetzner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.fhdo.login;

import de.fhdo.helper.CookieHelper;
import de.fhdo.helper.MD5;
import de.fhdo.helper.SessionHelper;
import de.fhdo.helper.WebserviceTermserverHelper;
import de.fhdo.logging.LoggingOutput;
import de.fhdo.terminologie.ws.authorization.LoginResponse;
import de.fhdo.terminologie.ws.authorization.LogoutResponseType;
import de.fhdo.terminologie.ws.authorization.Status;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class UsernamePasswordLogin extends Window implements org.zkoss.zk.ui.ext.AfterCompose
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  private String username;
  private int loginCount = 0;
  private boolean activated = false;

  public UsernamePasswordLogin()
  {
    logger.debug("Login()");

    username = "";

    if (SessionHelper.isUserLoggedIn())
    {
      openPortal();
    }
    else
    {

      // Cookies lesen
      String user = CookieHelper.getCookie("username");

      if (user != null && user.length() > 0)
        username = user;

      // Parameter lesen
//    String logout = "" + (String) Executions.getCurrent().getParameter("do");
//    if (logout.equals("logout"))
//      Authorization.logout();
      String userParam = "" + (String) Executions.getCurrent().getParameter("user");

      if (userParam.length() > 0 && userParam.equals("null") == false)
        username = userParam;
    }
  }
  
  public void openPortal()
  {
    Executions.sendRedirect("/Portal/index.zul");
  }

  public static boolean doLogout()
  {
    //SessionHelper.reset();
    //Executions.sendRedirect("/index.zul");
    //return true;
    logger.debug("[UsernamePasswordMethod] doLogout()");

    // Webservice aufrufen
    logger.debug("Authorization.logout()-Webservice wird aufgerufen");

    // Generische Parameterliste füllen (hier nur SessionID)
    List<String> parameterList = new LinkedList<String>();
    parameterList.add(SessionHelper.getSessionId());

    LogoutResponseType response = WebserviceTermserverHelper.logout(parameterList);
    logger.debug("Antwort: " + response.getReturnInfos().getMessage());

    if (response.getReturnInfos().getStatus() == Status.OK)
    {
      SessionHelper.reset();
      Executions.sendRedirect("/index.zul");

      return true;
    }
    else
    {
      try
      {
        Messagebox.show(Labels.getLabel("loginHelper.loggingOffError") + ": " + response.getReturnInfos().getMessage());
      }
      catch (Exception ex)
      {
        LoggingOutput.outputException(ex, UsernamePasswordLogin.class);
      }
    }
    return false;
  }

  public String getVersion()
  {
    ResourceBundle rb = ResourceBundle.getBundle("version");
    return rb.getString("application.version");
  }

  public void afterCompose()
  {
    // Fokus auf Eingabefeld setzen
    Textbox tbFocus = null;

    if (username.length() > 0)
      tbFocus = (Textbox) getFellow("tfPassword");
    else
      tbFocus = (Textbox) getFellow("tfUser");

    if (tbFocus != null)
      tbFocus.setFocus(true);

    if (activated)
    {
      Row row = (Row) getFellow("activationRow");
      row.setVisible(true);
    }

    String s_captcha = CookieHelper.getCookie("show_captcha");
    if (s_captcha != null && s_captcha.equals("1"))
    {
      showCaptcha();
    }

    loadInfo();

    // Wartemeldung entfernen
    Clients.clearBusy();
  }

  private void loadInfo()
  {
    String infoText = "";

    Label label = (Label) getFellow("infoLabel");
    label.setValue(infoText);
  }

  /**
   * Im Anmeldefenster wurde "Return" gedrückt
   *
   * @param event
   */
  public void onKeyPressed(KeyEvent event)
  {
    if (logger.isDebugEnabled())
      logger.debug("Enter gedrueckt!");

    Button b = (Button) getFellow("loginButton");
    b.setDisabled(true);

    login();

    b.setDisabled(false);
  }

  /**
   * Führt einen Loginversuch mit den Werten aus den Textfeldern durch. Gibt
   * eine Fehlermeldung bei nicht korrekten Anmeldedaten aus.
   *
   */
  public void login()
  {
    try
    {
      // Daten aus dem Formular auslesen
      Textbox tbUser = (Textbox) getFellow("tfUser");
      Textbox tbPass = (Textbox) getFellow("tfPassword");

      if (logger.isDebugEnabled())
        logger.debug("Login wird durchgefuehrt...");

      /*if (tbUser.getText().equals("user") && tbPass.getText().equals("test"))
      {
        CookieHelper.removeCookie("show_captcha");
        CookieHelper.setCookie("username", tbUser.getText());
        
        SessionHelper.setValue("session_id", "1234");
        Clients.showBusy("Login erfolgreich\n\nEPD-Demo wird geladen...");  // TODO übersetzen
        openPortal();
      }
      else
      {
        // Login falsch
        loginCount++;

        showRow("warningRow", true);
      }*/

      // Webservice-Aufruf
      // Generische Parameterliste füllen (hier nur SessionID)
      List<String> parameterList = new LinkedList<String>();
      parameterList.add(tbUser.getText());
      parameterList.add(MD5.getMD5(tbPass.getText()));
      //parameterList.add("true"); // Require admin

      LoginResponse.Return response = WebserviceTermserverHelper.login(parameterList);
      logger.debug("Antwort: " + response.getReturnInfos().getMessage());

      if (response.getReturnInfos().getStatus() == Status.OK
              && response.getParameterList() != null && response.getParameterList().size() > 0)
      {
        // Hauptseite aufrufen
        CookieHelper.removeCookie("show_captcha");
        CookieHelper.setCookie("username", tbUser.getText());

        logger.debug("Login erfolgreich, Session-ID: " + response.getParameterList().get(0));
        SessionHelper.setValue("session_id", response.getParameterList().get(0));
        SessionHelper.setValue("user_id", Long.parseLong(response.getParameterList().get(1)));
        SessionHelper.setValue("user_name", tbUser.getText());
        //SessionHelper.setValue("is_admin", true);

        logger.debug("Login successfull");
        logger.debug("session_id: " + response.getParameterList().get(0));
        logger.debug("user_id: " + Long.parseLong(response.getParameterList().get(1)));

        Clients.showBusy("Login erfolgreich\n\nEPD-Demo wird geladen...");  // TODO übersetzen
        openPortal();
      }
      else
      {
        // Login falsch
        loginCount++;
        showRow("warningRow", true);
      }
    }
    catch (Exception e)
    {
      logger.error(e.getLocalizedMessage());
      LoggingOutput.outputException(e, this);
    }

  }

  /**
   * Sendet das Passwort an die angegebene Email-Adresse
   */
  public void sendPassword() throws InterruptedException
  {
    /*if (Messagebox.show("Möchten Sie ein neues, zufällig generiertes Passwort an Ihre Email-Adresse geschickt bekommen?",
     "Neues Passwort", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES)
     {

     boolean erfolg = Authorization.resendPassword(username);

     if (erfolg)
     {
     Messagebox.show("Neues Passwort erfolgreich verschickt",
     "Neues Passwort", Messagebox.OK, Messagebox.INFORMATION);
     }
     else
     {
     Messagebox.show("Fehler beim Verschicken des neuen Passworts. Bitte wenden Sie sich an den Administrator.",
     "Neues Passwort", Messagebox.OK, Messagebox.EXCLAMATION);
     }
     }*/
    try
    {
      logger.debug("erstelle Fenster...");

      Map map = new HashMap();
      map.put("username", username);

      Window win = (Window) Executions.createComponents(
          "/gui/authorization/resendPasswordDialog.zul", null, map);

      //((ResendPasswordDialog) win).setUpdateListInterface(this);
      logger.debug("öffne Fenster...");
      win.doModal();
    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, this);
    }
  }

  private void showRow(String RowID, boolean Visible)
  {
    Row row = (Row) getFellow(RowID);
    row.setVisible(Visible);
  }

  private void showCaptcha()
  {
    SessionHelper.setValue("captcha_correct", false);
  }

  /**
   * @return the username
   */
  public String getUsername()
  {
    return username;
  }

  /**
   * @param username the username to set
   */
  public void setUsername(String username)
  {
    this.username = username;
  }

}
