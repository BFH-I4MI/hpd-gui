package de.fhdo.helper;

import de.fhdo.logging.LoggingOutput;
import java.util.Map;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Robert Mützner (robert.muetzner@fh-dortmund.de)
 */
public class ArgumentHelper
{
  public static Object getWindowArgument(String argName)
  {
    try
    {
      Map args = Executions.getCurrent().getArg();

      if (args.get(argName) != null)
        return args.get(argName);
    }
    catch (Exception e)
    {
      LoggingOutput.outputException(e, ArgumentHelper.class);
    }

    return null;
  }
  
  public static long getWindowArgumentLong(String argName)
  {
    long l = 0;
    try
    {
      Object o = getWindowArgument(argName);

      if (o != null)
        l = (Long) o;
    }
    catch (Exception e)
    {
      LoggingOutput.outputException(e, ArgumentHelper.class);
    }

    return l;
  }
  
  public static boolean getWindowArgumentBool(String argName)
  {
    boolean b = false;
    try
    {
      Object o = getWindowArgument(argName);

      if (o != null)
        b = (Boolean) o;
    }
    catch (Exception e)
    {
      LoggingOutput.outputException(e, ArgumentHelper.class);
    }

    return b;
  }
  
  public static int getWindowArgumentInt(String argName)
  {
    int l = 0;
    try
    {
      Object o = getWindowArgument(argName);

      if (o != null)
        l = (Integer) o;
    }
    catch (Exception e)
    {
      LoggingOutput.outputException(e, ArgumentHelper.class);
    }

    return l;
  }
  
  public static String getWindowArgumentString(String argName)
  {
    try
    {
      Object o = getWindowArgument(argName);

      if (o != null)
        return o.toString();
    }
    catch (Exception e)
    {
      LoggingOutput.outputException(e, ArgumentHelper.class);
    }

    return "";
  }
  
  public static Object getWindowParameter(String argName)
  {
    try
    {
      return Executions.getCurrent().getParameter(argName);
    }
    catch (Exception e)
    {
      LoggingOutput.outputException(e, ArgumentHelper.class);
    }

    return null;
  }
  
  public static String getWindowParameterString(String argName)
  {
    try
    {
      Object o = Executions.getCurrent().getParameter(argName);
      if(o != null)
        return o.toString();
    }
    catch (Exception e)
    {
      LoggingOutput.outputException(e, ArgumentHelper.class);
    }

    return "";
  }
  
  public static boolean getWindowParameterBool(String argName)
  {
    try
    {
      Object o = Executions.getCurrent().getParameter(argName);
      if(o != null)
        return Boolean.parseBoolean(o.toString());
    }
    catch (Exception e)
    {
      LoggingOutput.outputException(e, ArgumentHelper.class);
    }

    return false;
  }
}
