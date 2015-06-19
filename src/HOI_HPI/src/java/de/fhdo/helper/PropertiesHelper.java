
package de.fhdo.helper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */

public class PropertiesHelper
{
  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  private static PropertiesHelper instance;


  private String hpdUrl;
  private String termserverUrl;
  
  private String hpdUsername;
  private String hpdPassword;
  
//  private String oidAuthorSpeciality;
//  private String oidHealthcareFacilityTypeCode;
//  private String oidPracticeSettingCode;
  
  private String oidOrganizationSpeciality;
  private String oidOrganizationType;
  private String oidHealthProfessionalSpeciality;
  private String oidHealthProfessionalType;
  
  private boolean infosVisible;
  
  
  public static PropertiesHelper getInstance()
  {
    if (instance == null)
    {
      logger.debug("PropertiesHelper, create new instance...");
      instance = new PropertiesHelper();
    }

    return instance;
  }

  public PropertiesHelper()
  {
    loadData();
  }
  
  public void reload()
  {
    loadData();
  }

  private void loadData()
  {
    logger.debug("Load properties...");

    Properties config = new Properties();
    try
    {
      String filename = System.getProperty("catalina.base") + "/conf/hpd.properties";
      logger.debug("filename: " + filename);

      config.load(new FileInputStream(filename));

      
      // load properties
      hpdUrl = config.getProperty("hpd.url", "");
      
      hpdUsername = config.getProperty("hpd.username", "");
      hpdPassword = config.getProperty("hpd.password", "");
      
      termserverUrl = config.getProperty("hpd.termserver.url", "http://localhost:8080/TermServer/");
      if(termserverUrl != null && termserverUrl.endsWith("/") == false)
        termserverUrl = termserverUrl + "/";
      
//      oidAuthorSpeciality = config.getProperty("oid.authorSpeciality", "");
//      oidHealthcareFacilityTypeCode = config.getProperty("oid.healthcareFacilityTypeCode", "");
//      oidPracticeSettingCode = config.getProperty("oid.practiceSettingCode", "");
      
      oidOrganizationSpeciality = config.getProperty("oid.organization.specialty", "");
      if(StringHelper.isNullOrEmpty(oidOrganizationSpeciality))
        oidOrganizationSpeciality = config.getProperty("oid.organization.speciality", "");
      
      oidOrganizationType = config.getProperty("oid.organization.type", "");
      
      oidHealthProfessionalSpeciality = config.getProperty("oid.healthProfessional.specialty", "");
      if(StringHelper.isNullOrEmpty(oidHealthProfessionalSpeciality))
        oidHealthProfessionalSpeciality = config.getProperty("oid.healthProfessional.speciality", "");
      
      oidHealthProfessionalType = config.getProperty("oid.healthProfessional.type", "");

      infosVisible = getBooleanValue(config.getProperty("infos.visible", "false"));
      
      logger.debug("hpdUrl: " + hpdUrl);
      logger.debug("infosVisible: " + infosVisible);
      
      logger.debug("oidOrganizationSpeciality: " + oidOrganizationSpeciality);
      logger.debug("oidOrganizationType: " + oidOrganizationType);
      logger.debug("oidHealthProfessionalSpeciality: " + oidHealthProfessionalSpeciality);
      logger.debug("oidHealthProfessionalType: " + oidHealthProfessionalType);
    }
    catch (Exception e)
    {
      logger.error("[PropertiesHelper] error: " + e.getMessage());
    }

  }

  private boolean getBooleanValue(String s)
  {
    if (s == null || s.length() == 0)
      return false;

    try
    {
      return Boolean.parseBoolean(s);
    }
    catch(Exception ex)
    {
      
    }

    return false;
  }

  /**
   * @return the hpdUrl
   */
  public String getHpdUrl()
  {
    return hpdUrl;
  }

  /**
   * @return the termserverUrl
   */
  public String getTermserverUrl()
  {
    return termserverUrl;
  }

  /**
   * @return the oidAuthorSpeciality
   */
//  public String getOidAuthorSpeciality()
//  {
//    return oidAuthorSpeciality;
//  }
//
//  /**
//   * @return the oidHealthcareFacilityTypeCode
//   */
//  public String getOidHealthcareFacilityTypeCode()
//  {
//    return oidHealthcareFacilityTypeCode;
//  }
//
//  /**
//   * @return the oidPracticeSettingCode
//   */
//  public String getOidPracticeSettingCode()
//  {
//    return oidPracticeSettingCode;
//  }

  /**
   * @return the oidOrganizationSpeciality
   */
  public String getOidOrganizationSpeciality()
  {
    return oidOrganizationSpeciality;
  }

  /**
   * @return the oidOrganizationType
   */
  public String getOidOrganizationType()
  {
    return oidOrganizationType;
  }

  /**
   * @return the oidHealthProfessionalSpeciality
   */
  public String getOidHealthProfessionalSpeciality()
  {
    return oidHealthProfessionalSpeciality;
  }

  /**
   * @return the oidHealthProfessionalType
   */
  public String getOidHealthProfessionalType()
  {
    return oidHealthProfessionalType;
  }

  /**
   * @return the infosVisible
   */
  public boolean isInfosVisible()
  {
    return infosVisible;
  }

  /**
   * @param infosVisible the infosVisible to set
   */
  public void setInfosVisible(boolean infosVisible)
  {
    this.infosVisible = infosVisible;
  }

  /**
   * @return the hpdUsername
   */
  public String getHpdUsername()
  {
    return hpdUsername;
  }

  /**
   * @return the hpdPassword
   */
  public String getHpdPassword()
  {
    return hpdPassword;
  }

  

  
}
