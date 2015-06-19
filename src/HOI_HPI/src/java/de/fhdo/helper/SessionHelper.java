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
package de.fhdo.helper;


import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author Robert Mützner, Sven Becker
 */
public class SessionHelper
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  public static void reset()
  {
    logger.debug("reset()");
    org.zkoss.zk.ui.Session session = Sessions.getCurrent();

    //session.setAttribute("user_id", 0);
    /*session.setAttribute("user_name", "");
    session.setAttribute("is_admin", false);
    session.setAttribute("session_id", "");*/

    // RightsHelper.getInstance().clear();
  }

  /*public static boolean isUserLoggedIn()
  {
    String s = getSessionId();
    if (s != null && s.length() > 0)
    {
      return true;
    }
    return false;
  }*/

  

  public static String getServerName()
  {
    return Sessions.getCurrent().getServerName();
  }

  

  public static void setValue(String Name, Object Value)
  {
    org.zkoss.zk.ui.Session session = Sessions.getCurrent();
    if (session != null)
      session.setAttribute(Name, Value);
  }

  public static Object getValue(String Name)
  {
    org.zkoss.zk.ui.Session session = Sessions.getCurrent();
    if (session != null)
      return session.getAttribute(Name);
    else
      return null;
  }

  public static Object getValue(String Name, HttpSession httpSession)
  {
    if (httpSession != null)
    {
      return httpSession.getAttribute(Name);
    }

    org.zkoss.zk.ui.Session session = Sessions.getCurrent();
    if (session != null)
      return session.getAttribute(Name);
    else
      return null;
  }
  
  public static boolean getBoolean(String paramName)
  {
    Object o = getValue(paramName);
    if (o != null)
    {
      return (Boolean)o;
    }
    return false;
  }
  
  public static String getString(String paramName)
  {
    Object o = getValue(paramName);
    if (o != null)
    {
      return o.toString();
    }
    return "";
  }

  
}
