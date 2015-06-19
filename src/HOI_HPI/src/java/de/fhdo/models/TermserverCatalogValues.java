/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.models;

import de.fhdo.helper.PropertiesHelper;
import de.fhdo.terminologie.ws.search.ListCodeSystemConceptsResponse;
import de.fhdo.terminologie.ws.search.Status;
import de.fhdo.wsclient.WebserviceHelper;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.ListModelList;
import types.termserver.fhdo.de.CodeSystemConcept;
import types.termserver.fhdo.de.CodeSystemEntity;
import types.termserver.fhdo.de.CodeSystemEntityVersion;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class TermserverCatalogValues
{

  private static final int HOURS_TO_REFRESH = 24;
  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  String oid;
  Calendar lastUpdate;

  private List<CodeSystemEntity> values = null;
  private Map<String, TermConcept> valueMap = null;
  
  private ListCodeSystemConceptsResponse.Return wsResponse;

  public TermserverCatalogValues(String _oid)
  {
    oid = _oid;
  }

  private void loadValues()
  {
    logger.debug("TermserverCatalogValues - loadValues()");

    wsResponse = WebserviceHelper.listCodeSystemConcepts(oid);

    logger.debug("webservice result: " + wsResponse.getReturnInfos().getMessage());

    if (wsResponse.getReturnInfos().getStatus() == Status.OK)
    {
      values = wsResponse.getCodeSystemEntity();

      // sort values
      Collections.sort(values, new CodeSystemEntityComparator());

      // set current date
      lastUpdate = Calendar.getInstance();
      lastUpdate.setTime(new Date());
    }
    else
    {
      values = new LinkedList<CodeSystemEntity>();
    }
  }

  private void checkUpdate()
  {
    if (lastUpdate != null)
    {
      // update values after a certain period
      logger.debug("checkUpdate for Termserver values for OID: " + oid);

      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.HOUR, HOURS_TO_REFRESH * (-1));

      if (cal.after(lastUpdate))
      {
        // need to refresh
        logger.debug("need to refresh Termserver values for OID: " + oid);

        values = null;
        valueMap = null;

        loadValues();
      }
      else
      {
        logger.debug("Termserver values are cached with oid: " + oid);
      }
    }

  }

  private void initMap()
  {
    if (values == null)
      loadValues();
    
    checkUpdate();

    if (valueMap == null)
    {
      valueMap = new HashMap<String, TermConcept>();

      for (CodeSystemEntity cse : values)
      {
        CodeSystemEntityVersion csev = cse.getCodeSystemEntityVersions().get(0);
        CodeSystemConcept csc = csev.getCodeSystemConcepts().get(0);

        TermConcept concept = new TermConcept();
        concept.setConceptId(csev.getVersionId());
        concept.setCode(csc.getCode());
        concept.setTerm(csc.getTerm());
        concept.setTermAbbrevation(csc.getTermAbbrevation());

        valueMap.put(csc.getCode(), concept);
      }
    }
  }

  public TermConcept getConceptFromString(String str)
  {
    initMap();
    
    logger.debug("getConceptFromString: " + str + ", oid: " + oid);

    TermConcept concept = null;

    if (valueMap.containsKey(str))
    {
      concept = valueMap.get(str);
      logger.debug("concept from map: " + concept.getTerm());
    }

    if (concept == null)
    {
      concept = new TermConcept();
      concept.setTerm(str);
      
      logger.debug("concept not found: " + concept.getTerm());
    }

    return concept;
  }

  /**
   * @return the values
   */
  public List<CodeSystemEntity> getValues()
  {
    if (values == null)
      loadValues();
    
    checkUpdate();

    return values;
  }

  public CodeSystemConcept getCodeSystemConceptFromCombobox(Combobox cb)
  {
    CodeSystemConcept csc = null;
    if(cb != null)
    {
      logger.debug("getCodeSystemConceptFromCombobox: " + cb.getId());
      
      Comboitem item = cb.getSelectedItem();
      if(item != null)
      {
        if(item.getValue() != null && item.getValue() instanceof CodeSystemEntity)
        {
          CodeSystemEntity cse = item.getValue();
          csc = cse.getCodeSystemEntityVersions().get(0).getCodeSystemConcepts().get(0);
          
          logger.debug("found code: " + csc.getCode());
        }
      }
    }
    
    return csc;
  }
  
  public void fillCombobox(final Combobox cb, final String Selected, boolean all)
  {
    if (values == null)
      loadValues();
    
    checkUpdate();

    logger.debug("fillCombobox with Selection: " + Selected);

    if (all)
    {
      CodeSystemEntity cseAll = new CodeSystemEntity();
      CodeSystemEntityVersion csevAll = new CodeSystemEntityVersion();
      CodeSystemConcept csc = new CodeSystemConcept();
      csc.setCode("");
      csc.setTerm(Labels.getLabel("all"));
      csevAll.getCodeSystemConcepts().add(csc);
      cseAll.getCodeSystemEntityVersions().add(csevAll);

      values.add(0, cseAll);
    }

    // create renderer
    cb.setItemRenderer(new ComboitemRenderer<CodeSystemEntity>()
    {
      public void render(Comboitem item, CodeSystemEntity cse, int i) throws Exception
      {
        if (cse != null)
        {
          item.setValue(cse);

          CodeSystemConcept csc = cse.getCodeSystemEntityVersions().get(0).getCodeSystemConcepts().get(0);
          item.setLabel(csc.getTerm());

          if (Selected != null && csc.getTerm() != null && Selected.equals(csc.getTerm()))
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
    cb.setModel(new ListModelList<CodeSystemEntity>(values));

    if (all)
      values.remove(0);
  }

  /**
   * @return the wsResponse
   */
  public ListCodeSystemConceptsResponse.Return getWsResponse()
  {
    return wsResponse;
  }

}
