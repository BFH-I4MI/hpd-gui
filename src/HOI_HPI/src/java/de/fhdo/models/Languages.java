/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fhdo.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class Languages
{
  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  private static Languages instance;
  
  public static Languages getInstance()
  {
    if(instance == null)
      instance = new Languages();
    return instance;
  }

  private List<Language> languages;
  
  public Languages()
  {
    init();
  }
  
  private void init()
  {
    languages = new LinkedList<Language>();
    
    languages.add(new Language(Labels.getLabel("language.german"), "/rsc/img/countries/16/Germany.png", "de-DE"));
    languages.add(new Language(Labels.getLabel("language.english"), "/rsc/img/countries/16/United States of America.png", "en-US"));
    languages.add(new Language(Labels.getLabel("language.french"), "/rsc/img/countries/16/France.png", "fr-FR"));
    languages.add(new Language(Labels.getLabel("language.italian"), "/rsc/img/countries/16/Italy.png", "it-IT"));
    languages.add(new Language(Labels.getLabel("language.spanish"), "/rsc/img/countries/16/Spain.png", "es-ES"));
    
    Collections.sort(languages);
    
    //languages.add(0, new Language(Labels.getLabel("all"), "", ""));
    
  }
  
  public Language getLanguageByCode2(String code)
  {
    for(Language l : languages)
    {
      if(l.getCode().startsWith(code))
        return l;
    }
    
    return new Language(code, "", code);
  }
  
  public void fillCombobox(final Combobox cb, final String SelectedLanguageCode, boolean all)
  {
    logger.debug("fillCombobox with SelectedLanguageCode: " + SelectedLanguageCode);

    if(all)
      languages.add(0, new Language(Labels.getLabel("all"), "", ""));
    
    // Renderer erstellen
    cb.setItemRenderer(new ComboitemRenderer<Language>()
    {
      public void render(Comboitem item, Language lang, int i) throws Exception
      {
        if (lang != null)
        {
          item.setValue(lang);
          item.setLabel(lang.getLanguage());
          //item.setImage(lang.getImage());
          
          if (SelectedLanguageCode != null && lang.getCode()!= null && SelectedLanguageCode.equals(lang.getCode()))
          {
            cb.setSelectedItem(item);
            cb.setText(item.getLabel());
          }
        }
        else
          item.setLabel("");
      }
    });
    
    // Model erstellen und setzen
    cb.setModel(new ListModelList<Language>(languages));

    if(all)
      languages.remove(0);
  }

  /**
   * @return the languages
   */
  public List<Language> getLanguages()
  {
    return languages;
  }

  
}
