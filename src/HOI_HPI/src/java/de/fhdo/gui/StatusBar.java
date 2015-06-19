/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.gui;

import de.fhdo.helper.SessionHelper;
import de.fhdo.logging.LoggingOutput;
import java.util.Locale;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class StatusBar extends Window
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  public StatusBar()
  {
  }

  public void showInfos()
  {
    try
    {
      logger.debug("create window...");

      Window win = (Window) Executions.createComponents("/Portal/infos.zul", this, null);

      logger.debug("open window...");
      win.doModal();
    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, this);
    }
  }

}
