package de.fhdo.helper;

import de.fhdo.logging.LoggingOutput;
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

  /**
   * @return the logger
   */
  public static org.apache.log4j.Logger getLogger()
  {
    return logger;
  }

  /**
   * @param aLogger the logger to set
   */
  public static void setLogger(org.apache.log4j.Logger aLogger)
  {
    logger = aLogger;
  }

  private String menuMdiUrl;
  private String menuMdiImage;

  private String menuHpiUrl;
  private String menuHpiImage;

  private String menuHoiUrl;
  private String menuHoiImage;

  private String termserverUrl;
  private String termserverLoginName;
  private String termserverLoginPassword;

  public static PropertiesHelper getInstance()
  {
    if (instance == null)
      instance = new PropertiesHelper();

    return instance;
  }

  public PropertiesHelper()
  {
    loadData();
  }

  private void loadData()
  {
    getLogger().debug("Load properties...");

    Properties config = new Properties();
    try
    {
      String filename = System.getProperty("catalina.base") + "/conf/epd-demo.properties";
      getLogger().debug("filename: " + filename);

      config.load(new FileInputStream(filename));

      // load properties
      menuHoiImage = config.getProperty("menu.hoi.image", "/rsc/img/symbols/org_32x32.png");
      menuHpiImage = config.getProperty("menu.hpi.image", "/rsc/img/symbols/people_32x32.png");
      menuMdiImage = config.getProperty("menu.mdi.image", "/rsc/img/symbols/metadata_32x32.png");

      menuHoiUrl = config.getProperty("menu.hoi.url", "http://localhost:8080/HOI_HPI/");
      menuHpiUrl = config.getProperty("menu.hpi.url", "http://localhost:8080/HOI_HPI/");
      menuMdiUrl = config.getProperty("menu.mdi.url", "http://localhost:8080/TermBrowser/");

      if (menuHoiUrl.endsWith("/") == false)
        menuHoiUrl = menuHoiUrl + "/";
      if (menuHpiUrl.endsWith("/") == false)
        menuHpiUrl = menuHpiUrl + "/";
      if (menuMdiUrl.endsWith("/") == false)
        menuMdiUrl = menuMdiUrl + "/";

      termserverLoginName = config.getProperty("termserver.login.name", "");
      termserverLoginPassword = config.getProperty("termserver.login.password", "");
      
      termserverUrl = config.getProperty("termserver.server.url", "http://localhost:8080/TermServer/");
      
      logger.debug("menuHoiUrl: " + menuHoiUrl);
      logger.debug("menuHpiUrl: " + menuHpiUrl);
      logger.debug("menuMdiUrl: " + menuMdiUrl);
      
      logger.debug("menuHoiImage: " + menuHoiImage);
      logger.debug("menuHpiImage: " + menuHpiImage);
      logger.debug("menuMdiImage: " + menuMdiImage);

      logger.debug("termserverUrl: " + termserverUrl);
      
      //login_classname = config.getProperty("login.classname", "kjshdf");
      //logger.debug("login_classname: " + login_classname);
    }
    catch (Exception e)
    {
      LoggingOutput.outputException(e, this);
      //logger.error("[PropertiesHelper] error: " + e.getMessage());
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
    catch (Exception ex)
    {

    }

    return false;
  }

  /**
   * @return the menuMdiUrl
   */
  public String getMenuMdiUrl()
  {
    return menuMdiUrl;
  }

  /**
   * @return the menuMdiImage
   */
  public String getMenuMdiImage()
  {
    return menuMdiImage;
  }

  /**
   * @return the menuHpiUrl
   */
  public String getMenuHpiUrl()
  {
    return menuHpiUrl;
  }

  /**
   * @return the menuHpiImage
   */
  public String getMenuHpiImage()
  {
    return menuHpiImage;
  }

  /**
   * @return the menuHoiUrl
   */
  public String getMenuHoiUrl()
  {
    return menuHoiUrl;
  }

  /**
   * @return the menuHoiImage
   */
  public String getMenuHoiImage()
  {
    return menuHoiImage;
  }

  /**
   * @return the termserverLoginName
   */
  public String getTermserverLoginName()
  {
    return termserverLoginName;
  }

  /**
   * @return the termserverLoginPassword
   */
  public String getTermserverLoginPassword()
  {
    return termserverLoginPassword;
  }

  /**
   * @return the termserverUrl
   */
  public String getTermserverUrl()
  {
    return termserverUrl;
  }

}
