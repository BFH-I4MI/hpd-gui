/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.gui;

import de.fhdo.helper.PropertiesHelper;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Window;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class Infos extends Window implements AfterCompose
{
  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  
  private PropertiesHelper properties;

  public Infos()
  {
    properties = PropertiesHelper.getInstance();
  }

  public void afterCompose()
  {
    
  }

  /**
   * @return the properties
   */
  public PropertiesHelper getProperties()
  {
    return properties;
  }

  /**
   * @param properties the properties to set
   */
  public void setProperties(PropertiesHelper properties)
  {
    this.properties = properties;
  }
  
  
  
}
