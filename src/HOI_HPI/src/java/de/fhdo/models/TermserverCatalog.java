package de.fhdo.models;

import de.fhdo.helper.PropertiesHelper;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class TermserverCatalog
{
  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  
  // singleton
  private static TermserverCatalog instance;
  
  public static TermserverCatalog getInstance()
  {
    if(instance == null)
      instance = new TermserverCatalog();
    return instance;
  }

  
  // Class
  private Map<String, TermserverCatalogValues> map;
  //private TermserverCatalogValues authorSpecialty;
  //private TermserverCatalogValues healthcareFacilityTypeCode;
  //private TermserverCatalogValues practiceSettingCode;
          
  public TermserverCatalog()
  {
    map = new HashMap<String, TermserverCatalogValues>();
  }
  
  public TermserverCatalogValues getCatalogFromOID(String oid)
  {
    logger.debug("getCatalogFromOID: " + oid);
    
    if(map.containsKey(oid) == false)
    {
      logger.debug("no catalog found, create new...");
      
      TermserverCatalogValues values = new TermserverCatalogValues(oid);
      map.put(oid, values);
      return values;
    }
    else
    {
      logger.debug("catalog found, return existing");
      return map.get(oid);
    }
  }

//  /**
//   * @return the authorSpecialtyValues
//   */
//  public TermserverCatalogValues getAuthorSpecialty()
//  {
//    if(authorSpecialty == null)
//    {
//      authorSpecialty = new TermserverCatalogValues(PropertiesHelper.getInstance().getOidAuthorSpeciality());
//    }
//    
//    authorSpecialty.checkUpdate();
//    
//    return authorSpecialty;
//  }
//
//  /**
//   * @return the healthcareFacilityTypeCode
//   */
//  public TermserverCatalogValues getHealthcareFacilityTypeCode()
//  {
//    if(healthcareFacilityTypeCode == null)
//    {
//      healthcareFacilityTypeCode = new TermserverCatalogValues(PropertiesHelper.getInstance().getOidHealthcareFacilityTypeCode());
//    }
//    
//    healthcareFacilityTypeCode.checkUpdate();
//    
//    return healthcareFacilityTypeCode;
//  }
//
//  /**
//   * @return the practiceSettingCode
//   */
//  public TermserverCatalogValues getPracticeSettingCode()
//  {
//    if(practiceSettingCode == null)
//    {
//      practiceSettingCode = new TermserverCatalogValues(PropertiesHelper.getInstance().getOidPracticeSettingCode());
//    }
//    
//    practiceSettingCode.checkUpdate();
//    
//    return practiceSettingCode;
//  }
  
  
  
  
}
