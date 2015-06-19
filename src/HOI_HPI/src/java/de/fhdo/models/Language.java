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
public class Language implements Comparable<Language>
{
  private String language;
  private String image;
  private String code;

  public Language(String language, String image, String code)
  {
    this.language = language;
    this.image = image;
    this.code = code;
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

  /**
   * @return the image
   */
  public String getImage()
  {
    return image;
  }

  /**
   * @param image the image to set
   */
  public void setImage(String image)
  {
    this.image = image;
  }

  /**
   * @return the code
   */
  public String getCode()
  {
    return code;
  }
  
  /**
   * @return the code
   */
  public String get2LetterCode()
  {
    if(code != null && code.length() > 1)
    {
      return code.substring(0, 2);
    }
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code)
  {
    this.code = code;
  }

  public int compareTo(Language lang)
  {
    return language.compareTo(lang.getLanguage());
  }
}
