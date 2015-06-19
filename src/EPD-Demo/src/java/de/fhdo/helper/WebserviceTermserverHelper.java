/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.helper;

import de.fhdo.terminologie.ws.authorization.Authorization;
import de.fhdo.terminologie.ws.authorization.Authorization_Service;
import de.fhdo.terminologie.ws.authorization.LoginResponse;
import de.fhdo.terminologie.ws.authorization.LogoutResponseType;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class WebserviceTermserverHelper
{
  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  
  public static LoginResponse.Return login(List<String> parameter)
  {
    Authorization_Service service;
    Authorization port;
    try
    {
      // Service mit bestimmter URL oeffnen
      String url = optimizeUrl(PropertiesHelper.getInstance().getTermserverUrl()) + "Authorization?wsdl";
      logger.debug("url: " + url);
      service = new Authorization_Service(new URL(url),
              new QName("http://authorization.ws.terminologie.fhdo.de/", "Authorization"));
    }
    catch (Exception ex)
    {
      Logger.getLogger(WebserviceTermserverHelper.class.getName()).log(Level.SEVERE, null, ex);

      // Standard Service oeffnen
      service = new Authorization_Service();
    }
    port = service.getAuthorizationPort();
    return port.login(parameter);
  }
  
  public static LogoutResponseType logout(List<String> parameter)
  {
    Authorization_Service service;
    Authorization port;
    try
    {
      // Service mit bestimmter URL oeffnen
      service = new Authorization_Service(new URL(optimizeUrl(PropertiesHelper.getInstance().getTermserverUrl()) + "Authorization?wsdl"),
              new QName("http://authorization.ws.terminologie.fhdo.de/", "Authorization"));
    }
    catch (Exception ex)
    {
      Logger.getLogger(WebserviceTermserverHelper.class.getName()).log(Level.SEVERE, null, ex);

      // Standard Service oeffnen
      service = new Authorization_Service();
    }
    port = service.getAuthorizationPort();
    return port.logout(parameter);
  }
  
  private static String optimizeUrl(String url)
  {
    if (url.startsWith("http://") == false)
      return "http://" + url;
    if (url.endsWith("/") == false)
      return url + "/";
    if (url.startsWith("/"))
      return url.substring(1);
    
    return url;
  }
  
}
