/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.models;

import de.fhdo.helper.StringHelper;
import de.fhdo.logging.LoggingOutput;
import de.fhdo.wsclient.HpdSearch;
import java.util.LinkedList;
import java.util.List;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class HealthProfessional
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  private String gln, name, gender, status, description;
  private List<TermConcept> type, speciality;
  private List<Address> address;

  private List<HealthOrganization> organizations;
  private List<Language> languages;
  private List<String> memberOf;

  public HealthProfessional()
  {
    languages = new LinkedList<Language>();
    //organizations = new LinkedList<HealthOrganization>();
    organizations = null;
    memberOf = new LinkedList<String>();
    
    type = new LinkedList<TermConcept>();
    speciality = new LinkedList<TermConcept>();
    
    address = new LinkedList<Address>();
  }

  
  public String getPrimaryAddressString()
  {
    Address addr = getPrimaryAddress();
    if(addr != null)
      return addr.toString();
    return "";
  }
  public String getSecondaryAddressString()
  {
    Address addr = getSecondaryAddress();
    if(addr != null)
      return addr.toString();
    return "";
  }
  
  public Address getPrimaryAddress()
  {
    for(Address addr : address)
    {
      if(addr.getPrimary() != null && addr.getPrimary().booleanValue())
      {
        return addr;
      }
    }
    if(address.size() > 0)
      return address.get(0);
    
    return null;
  }
  
  public Address getSecondaryAddress()
  {
    for(Address addr : address)
    {
      if(addr.getPrimary() != null && addr.getPrimary().booleanValue() == false)
      {
        return addr;
      }
    }
    
    return null;
  }
  
  /**
   * @return the gln
   */
  public String getGln()
  {
    return gln;
  }

  /**
   * @param gln the gln to set
   */
  public void setGln(String gln)
  {
    this.gln = gln;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the gender
   */
  public String getGender()
  {
    return gender;
  }

  public Listcell getGenderCell()
  {
    Listcell lc = new Listcell();

    if (StringHelper.isNullOrEmpty(gender) == false)
    {
      if (gender.equalsIgnoreCase("f") || gender.equalsIgnoreCase("w"))
      {
        Image img = new Image("/rsc/img/symbols/female_16x16.png");
        img.setTooltiptext(Labels.getLabel("female"));
        lc.appendChild(img);
      }
      else if (gender.equalsIgnoreCase("m"))
      {
        Image img = new Image("/rsc/img/symbols/male_16x16.png");
        img.setTooltiptext(Labels.getLabel("male"));
        lc.appendChild(img);
      }
      else
      {
        Label label = new Label(gender);
        lc.appendChild(label);
      }
    }

    return lc;
  }

  /**
   * @param gender the gender to set
   */
  public void setGender(String gender)
  {
    this.gender = gender;
  }

  /**
   * @return the status
   */
  public String getStatus()
  {
    return status;
  }
  
  public boolean isActive()
  {
    if (StringHelper.isNullOrEmpty(status) == false)
    {
      return status.equalsIgnoreCase("active");
    }
    return false;
  }

  public boolean isInactive()
  {
    if (StringHelper.isNullOrEmpty(status) == false)
    {
      return status.equalsIgnoreCase("inactive");
    }
    return false;
  }
  
  public boolean isRetired()
  {
    if (StringHelper.isNullOrEmpty(status) == false)
    {
      return status.equalsIgnoreCase("retired");
    }
    return false;
  }
  
  public boolean isDeceased()
  {
    if (StringHelper.isNullOrEmpty(status) == false)
    {
      return status.equalsIgnoreCase("deceased");
    }
    return false;
  }

  public String getLocalizedStatus()
  {
    if (status == null)
      return "";

    String s = Labels.getLabel(status.toLowerCase());
    if (StringHelper.isNullOrEmpty(s))
      return status;
    else
      return s;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status)
  {
    this.status = status;
  }


  /**
   * @return the organizations
   */
  public List<HealthOrganization> getOrganizations()
  {
    if (organizations == null)
    {
      try
      {
        // load upper organizsations
        if (memberOf != null && memberOf.size() > 0)
        {
          organizations = new LinkedList<HealthOrganization>();

          // load organisations via hpd
          for (String s : memberOf)
          {
            DN dn = new DN(s);
            logger.debug("[HealthProfessional.java] initialize organisation with gln: " + dn.getCn());

            HpdSearch search = new HpdSearch();
            search.setGln(dn.getCn());

            List<Object> list = search.performHOISearch();

            if (list != null)
              logger.debug("[HealthProfessional.java] organization search count: " + list.size());
            else
              logger.debug("[HealthProfessional.java] organization search count: list is null");

            if (list != null && list.size() > 0)
            {
              // handle results
              for (Object obj : list)
              {
                if (obj instanceof HealthOrganization)
                {
                  HealthOrganization org = (HealthOrganization) obj;
                  logger.debug("Organization found: " + org.getGln() + ", " + org.getName());
                  organizations.add(org);

                  // TODO recursive call...
                  //org.get
                }
              }
            }
          }
        }
      }
      catch (Exception ex)
      {
        LoggingOutput.outputException(ex, this);
      }

      if (organizations == null)
      {
        // no upper organizations available, initialize list
        organizations = new LinkedList<HealthOrganization>();
      }

    }
    return organizations;
  }


  /**
   * @return the languages
   */
  public List<Language> getLanguages()
  {
    return languages;
  }

  /**
   * @param languages the languages to set
   */
  public void setLanguages(List<Language> languages)
  {
    this.languages = languages;
  }

  /*public Listcell getLanguageCell()
   {
    
   }*/
  public String getLanguageString()
  {
    String s = "";

    for (Language l : languages)
    {
      if (s.length() > 0)
        s += ", ";

      s += l.getLanguage();
    }

    return s;
  }

  public String getLanguageCodeString()
  {
    String s = "";

    for (Language l : languages)
    {
      if (s.length() > 0)
        s += ", ";

      s += l.get2LetterCode();
    }

    return s;
  }


  /*public Listcell getLanguageCell()
   {
   Listcell lc = new Listcell();

   for (Language l : languages)
   {
   if (StringHelper.isNullOrEmpty(l.getImage()))
   {
   Label label = new Label(l.getLanguage());
   lc.appendChild(label);
   }
   else
   {
   Image img = new Image(l.getImage());
   img.setTooltiptext(l.getLanguage());
   lc.appendChild(img);
   }
   }

   return lc;
   }*/
  /**
   * @return the description
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
   * @return the memberOf
   */
  public List<String> getMemberOf()
  {
    return memberOf;
  }

  public String getTypesString()
  {
    String s = "";

    if (type != null)
    {
      for (TermConcept temp : type)
      {
        if (s.length() > 0)
          s += ", ";

        s += temp.getTerm();
      }
    }

    return s;
  }

  /**
   * @return the type
   */
  public List<TermConcept> getType()
  {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(List<TermConcept> type)
  {
    this.type = type;
  }

  public String getSpecialitiesString()
  {
    String s = "";

    if (speciality != null)
    {
      for (TermConcept temp : speciality)
      {
        if (s.length() > 0)
          s += ", ";

        s += temp.getTerm();
      }
    }

    return s;
  }

  /**
   * @return the speciality
   */
  public List<TermConcept> getSpeciality()
  {
    return speciality;
  }

  /**
   * @param speciality the speciality to set
   */
  public void setSpeciality(List<TermConcept> speciality)
  {
    this.speciality = speciality;
  }

  /**
   * @return the address
   */
  public List<Address> getAddress()
  {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(List<Address> address)
  {
    this.address = address;
  }

}
