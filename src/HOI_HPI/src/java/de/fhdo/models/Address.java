/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.models;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class Address
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  private String streetName, streetNumber, postalCode, city, country, state;
  private Boolean primary;

  public Address()
  {
  }

  public Address(String ldapString)
  {
    if(ldapString == null)
      return;
    // status=Primary $ streetNumber=10 $ streetName=Bahnhofstrasse $ city= Bern $ postalCode=3000
    logger.debug("parse LDAP address: " + ldapString);

    //String streetNumber = "", streetName = "", city = "", state = "",
    //    postalCode = "", country = "", addr = "";
    
    String[] split = ldapString.split("\\$");
    
    logger.debug("length: " + split.length);

    for (String s : split)
    {
      s = s.trim();

      logger.debug("s: " + s);

      String[] duo = s.split("=");
      if (duo != null && duo.length == 2)
      {
        String key = duo[0].trim();
        String value = duo[1].trim();

        logger.debug("key: " + key + ", value: " + value);

        if (value == null)
          continue;

        if (key.equalsIgnoreCase("status"))
        {
          // primary, other, inactive
          if (value.equalsIgnoreCase("primary"))
          {
            primary = true;
          }
          else primary = false;
        }
        else if (key.equalsIgnoreCase("city"))
        {
          city = value;
        }
        else if (key.equalsIgnoreCase("streetName"))
        {
          streetName = value;
        }
        else if (key.equalsIgnoreCase("addr"))
        {
          streetName = value;
        }
        else if (key.equalsIgnoreCase("streetNumber"))
        {
          streetNumber = value;
        }
        else if (key.equalsIgnoreCase("streetName"))
        {
          streetName = value;
        }
        else if (key.equalsIgnoreCase("state"))
        {
          state = value;
        }
        else if (key.equalsIgnoreCase("postalCode"))
        {
          postalCode = value;
        }
        else if (key.equalsIgnoreCase("country"))
        {
          country = value;
        }
      }

    }

    logger.debug("primary: " + primary);
    logger.debug("result: " + this.toString());

  }
  
  @Override
  public String toString()
  {
    String s = "";
    if(streetName != null)
      s = streetName;
    
    if(streetNumber != null && streetNumber.length() > 0)
      s += " " + streetNumber;
    
    if(s.length() > 0)
      s += ", ";
    
    if(postalCode != null && postalCode.length() > 0)
      s += postalCode + " ";
    
    if(city != null && city.length() > 0)
      s += city;
    
    if(country != null && country.length() > 0)
      s += " - " + country;
    
    return s;
  }
  
  public String getStreetLine()
  {
    String s = "";
    if(streetName != null)
      s = streetName;
    
    if(streetNumber != null && streetNumber.length() > 0)
      s += " " + streetNumber;
    
    return s;
  }

  /**
   * @return the streetName
   */
  public String getStreetName()
  {
    return streetName;
  }

  /**
   * @param streetName the streetName to set
   */
  public void setStreetName(String streetName)
  {
    this.streetName = streetName;
  }

  /**
   * @return the streetNumber
   */
  public String getStreetNumber()
  {
    return streetNumber;
  }

  /**
   * @param streetNumber the streetNumber to set
   */
  public void setStreetNumber(String streetNumber)
  {
    this.streetNumber = streetNumber;
  }

  

  /**
   * @return the city
   */
  public String getCity()
  {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city)
  {
    this.city = city;
  }

  /**
   * @return the country
   */
  public String getCountry()
  {
    return country;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country)
  {
    this.country = country;
  }

  /**
   * @return the primary
   */
  public Boolean getPrimary()
  {
    return primary;
  }

  /**
   * @param primary the primary to set
   */
  public void setPrimary(Boolean primary)
  {
    this.primary = primary;
  }

  /**
   * @return the state
   */
  public String getState()
  {
    return state;
  }

  /**
   * @param state the state to set
   */
  public void setState(String state)
  {
    this.state = state;
  }

  /**
   * @return the postalCode
   */
  public String getPostalCode()
  {
    return postalCode;
  }

  /**
   * @param postalCode the postalCode to set
   */
  public void setPostalCode(String postalCode)
  {
    this.postalCode = postalCode;
  }
}
