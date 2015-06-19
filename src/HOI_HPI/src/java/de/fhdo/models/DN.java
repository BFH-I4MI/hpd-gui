package de.fhdo.models;

import de.fhdo.helper.StringHelper;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class DN
{

  private String cn, ou, dc, o, c;

  public DN(String cn, String ou, String dc, String o, String c)
  {
    this.cn = cn;
    this.ou = ou;
    this.dc = dc;
    this.o = o;
    this.c = c;
  }
  
  public DN(String dn)
  {

    String[] split = dn.split(",");

    for (String s : split)
    {
      s = s.trim();

      String[] duo = s.split("=");
      if (duo != null && duo.length == 2)
      {
        String key = duo[0].trim();
        String value = duo[1].trim();

        if (value == null)
          continue;

        if (key.equalsIgnoreCase("cn"))
        {
          cn = value;
        }
        else if (key.equalsIgnoreCase("ou"))
        {
          ou = value;
        }
        else if (key.equalsIgnoreCase("dc"))
        {
          dc = value;
        }
        else if (key.equalsIgnoreCase("o"))
        {
          o = value;
        }
        else if (key.equalsIgnoreCase("c"))
        {
          c = value;
        }
      }

    }

  }

  @Override
  public String toString()
  {
    String s = "";

    if (StringHelper.isNullOrEmpty(cn) == false)
      s += "cn=" + cn;
    if (StringHelper.isNullOrEmpty(ou) == false)
      s += (s.length() > 0 ? "," : "") + "ou=" + ou;
    if (StringHelper.isNullOrEmpty(dc) == false)
      s += (s.length() > 0 ? "," : "") + "dc=" + dc;
    if (StringHelper.isNullOrEmpty(o) == false)
      s += (s.length() > 0 ? "," : "") + "o=" + o;
    if (StringHelper.isNullOrEmpty(c) == false)
      s += (s.length() > 0 ? "," : "") + "c=" + c;

    return s;
  }

  /**
   * @return the cn
   */
  public String getCn()
  {
    return cn;
  }

  /**
   * @param cn the cn to set
   */
  public void setCn(String cn)
  {
    this.cn = cn;
  }

  /**
   * @return the ou
   */
  public String getOu()
  {
    return ou;
  }

  /**
   * @param ou the ou to set
   */
  public void setOu(String ou)
  {
    this.ou = ou;
  }

  /**
   * @return the dc
   */
  public String getDc()
  {
    return dc;
  }

  /**
   * @param dc the dc to set
   */
  public void setDc(String dc)
  {
    this.dc = dc;
  }

  /**
   * @return the o
   */
  public String getO()
  {
    return o;
  }

  /**
   * @param o the o to set
   */
  public void setO(String o)
  {
    this.o = o;
  }

  /**
   * @return the c
   */
  public String getC()
  {
    return c;
  }

  /**
   * @param c the c to set
   */
  public void setC(String c)
  {
    this.c = c;
  }

}
