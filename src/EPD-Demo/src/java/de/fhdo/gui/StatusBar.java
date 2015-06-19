/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.gui;

import de.fhdo.helper.SessionHelper;
import java.util.Locale;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class StatusBar extends Window
{

  public StatusBar()
  {
  }

  public void switchLanguage(String Code)
  {
    Locale prefer_locale = new Locale(Code);
    SessionHelper.setValue(org.zkoss.web.Attributes.PREFERRED_LOCALE, prefer_locale);
    Executions.sendRedirect("");    // reload page
  }

}
