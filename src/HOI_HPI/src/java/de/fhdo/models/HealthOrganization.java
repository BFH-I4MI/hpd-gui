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
public class HealthOrganization
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  private String gln, name, language, status, description;
  private List<TermConcept> type, speciality;
  private List<Address> address;

  private List<HealthOrganization> subOrganizations;
  private List<HealthProfessional> healthProfessionals;
  private List<String> memberOf;
  private List<HealthOrganization> organizations; // upper organizations
  private List<Language> languages;

  public HealthOrganization()
  {
    languages = new LinkedList<Language>();
    //organizations = new LinkedList<HealthOrganization>();
    subOrganizations = null;
    healthProfessionals = null;
    //subOrganizations = new LinkedList<HealthOrganization>();
    organizations = null;
    memberOf = new LinkedList<String>();
    type = new LinkedList<TermConcept>();
    speciality = new LinkedList<TermConcept>();
    address = new LinkedList<Address>();
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
   * @return the language
   */
  public String getLanguage()
  {
    return language;
  }

  /**
   * @param language the language to set
   */
  public void setLanguage(String language)
  {
    this.language = language;
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

  public String getPrimaryAddressString()
  {
    Address addr = getPrimaryAddress();
    if (addr != null)
      return addr.toString();
    return "";
  }

  public String getSecondaryAddressString()
  {
    Address addr = getSecondaryAddress();
    if (addr != null)
      return addr.toString();
    return "";
  }

  public Address getPrimaryAddress()
  {
    logger.debug("getPrimaryAddress(), size: " + address.size());
    for (Address addr : address)
    {
      if (addr.getPrimary() != null && addr.getPrimary().booleanValue())
      {
        return addr;
      }
    }
    if (address.size() > 0)
      return address.get(0);

    logger.debug("getPrimaryAddress is null");
    return null;
  }

  public Address getSecondaryAddress()
  {
    for (Address addr : address)
    {
      if (addr.getPrimary() != null && addr.getPrimary().booleanValue() == false)
      {
        return addr;
      }
    }

    return null;
  }

  /*public String getFullAddress()
   {
   String s = stre;
    
   if(s.length() > 0)
   s += ", ";
    
   if(zip != null && zip.length() > 0)
   s += zip + " ";
   if(city != null)
   s += city;
    
   return s;
   }*/
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
   * @return the subOrganizations
   */
  public List<HealthOrganization> getSubOrganizations()
  {
    if (subOrganizations == null)
      initSubEntries();

    return subOrganizations;
  }

  /**
   * @param subOrganizations the subOrganizations to set
   */
  public void setSubOrganizations(List<HealthOrganization> subOrganizations)
  {
    this.subOrganizations = subOrganizations;
  }

  /**
   * @return the healthProfessionals
   */
  public List<HealthProfessional> getHealthProfessionals()
  {
    if (healthProfessionals == null)
      initSubEntries();

    return healthProfessionals;
  }

  /**
   * @param healthProfessionals the healthProfessionals to set
   */
  public void setHealthProfessionals(List<HealthProfessional> healthProfessionals)
  {
    this.healthProfessionals = healthProfessionals;
  }

  /**
   * @return the memberOf
   */
  public List<String> getMemberOf()
  {
    return memberOf;
  }

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
   * @return the organizations
   */
  public List<HealthOrganization> getOrganizations()
  {
    if (organizations == null)
    {
      logger.debug("load upper organizations for gln: " + gln);
      try
      {
        organizations = new LinkedList<HealthOrganization>();

        // load upper organizsations
        if (memberOf != null && memberOf.size() > 0)
        {
          // load organisations via hpd
          for (String s : memberOf)
          {
            DN dn = new DN(s);
            logger.debug("[HealthProfessional.java] initialize organisation with gln: " + dn.getCn());

            if (dn.getCn().equals(gln))
            {
              // same gln, this search would end in an endless loop
              logger.warn("circular reference, same gln");
              continue;
            }

            HpdSearch search = new HpdSearch();
            search.setGln(dn.getCn());

            List<Object> list = search.performHOISearch();

            if (list != null)
              logger.debug("[HealthProfessional.java] organization search count: " + list.size());
            else
              logger.debug("[HealthProfessional.java] organization search count: list is null");

            if (list != null && list.size() > 0)
            {
              //organizations = new LinkedList<HealthOrganization>();

              // handle results
              for (Object obj : list)
              {
                if (obj instanceof HealthOrganization)
                {
                  HealthOrganization upperOrg = (HealthOrganization) obj;
                  logger.debug("Organization found: " + upperOrg.getGln() + ", " + upperOrg.getName());

                  // check if same upper org exists in tree
                  boolean upperOrgExists = false;
                  for (HealthOrganization existingUpperOrg : organizations)
                  {
                    if (existingUpperOrg.getGln().equals(upperOrg.getGln()))
                    {
                      upperOrgExists = true;
                      break;
                    }
                  }

                  if (upperOrgExists == false)
                  {
                    organizations.add(upperOrg);

                    // add this as a sub organization
                    logger.debug("add this as a sub organization, gln: " + this.getGln());
                    upperOrg.setSubOrganizations(new LinkedList<HealthOrganization>());
                    upperOrg.getSubOrganizations().add(this);
                  }

                  /*boolean found = false;
                   for(HealthOrganization horg : org.getSubOrganizations())
                   {
                   if(horg.getGln().equals(this.getGln()))
                   {
                   found = true;
                   break;
                   }
                   }
                  
                   if(found == false)
                   org.getSubOrganizations().add(this);*/
                  // TODO recursive call ?...
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
        logger.debug("no further upper organizations -> root");
        organizations = new LinkedList<HealthOrganization>();
      }

    }
    else
    {
      logger.debug("upper organization exist: " + organizations.size());
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

  public void initSubEntries()
  {
    if (subOrganizations == null || healthProfessionals == null)
    {
      try
      {
        // load upper organizsations
        if (StringHelper.isNullOrEmpty(gln) == false)
        {
          // load sub organisations and health professionals via hpd

          DN dn = new DN(gln, "Relationship", "HPD", "ehealth-suisse", "ch");
          logger.debug("[HealthOrganization.java] initSubEntries with dn: " + dn.toString());

          HpdSearch search = new HpdSearch();
          search.setMemberOf(dn.toString());

          List<Object> list = search.performHOI_HPISearch();

          if (list != null)
            logger.debug("[HealthOrganization.java] search count: " + list.size());
          else
            logger.debug("[HealthOrganization.java] search count: list is null");

          subOrganizations = new LinkedList<HealthOrganization>();
          healthProfessionals = new LinkedList<HealthProfessional>();

          if (list != null && list.size() > 0)
          {
            // handle results
            for (Object obj : list)
            {
              if (obj instanceof HealthOrganization)
              {
                HealthOrganization subOrg = (HealthOrganization) obj;

                if (StringHelper.isNullOrEmpty(subOrg.getGln()) == false && subOrg.getGln().equals(gln) == false)
                {
                  logger.debug("Organization found: " + subOrg.getGln() + ", " + subOrg.getName());
                  subOrganizations.add(subOrg);
                }
              }
              if (obj instanceof HealthProfessional)
              {
                HealthProfessional subHP = (HealthProfessional) obj;

                logger.debug("HP found: " + subHP.getGln() + ", " + subHP.getName());
                healthProfessionals.add(subHP);
              }
            }
          }

        }
      }
      catch (Exception ex)
      {
        LoggingOutput.outputException(ex, this);
      }

      if (subOrganizations == null)
      {
        // no sub organizations available, initialize list
        subOrganizations = new LinkedList<HealthOrganization>();
      }

    }
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
