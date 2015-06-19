/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.gui;

import de.fhdo.helper.ArgumentHelper;
import de.fhdo.helper.StringHelper;
import de.fhdo.logging.LoggingOutput;
import de.fhdo.models.HealthProfessional;
import de.fhdo.tree.GenericTree;
import de.fhdo.tree.GenericTreeHeaderType;
import de.fhdo.tree.GenericTreeRowType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class HpiDetails extends Window implements AfterCompose
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  private boolean allowEditing;
  private String name;
  private boolean allowDetails;
  private HealthProfessional healthProfessional;
  private Timer timer;
  private GenericTree genericTree;

  public HpiDetails()
  {
    allowEditing = ArgumentHelper.getWindowArgumentBool("allowEditing");
    logger.debug("allowEditing: " + allowEditing);

    //name = ArgumentHelper.getWindowArgumentString("name");
    Object o = ArgumentHelper.getWindowArgument("data");
    if (o != null && o instanceof HealthProfessional)
    {
      healthProfessional = (HealthProfessional) o;
      logger.debug("healthProfessional: " + healthProfessional.getName());
      
      if(healthProfessional.getMemberOf() != null)
      {
        for(String s : healthProfessional.getMemberOf())
        {
          logger.debug("memberof: " + s);
        }
      }
    }
    else logger.debug("healthProfessional is null");

    allowDetails = ArgumentHelper.getWindowArgumentBool("allowDetails");

    initOrganisations();
  }

  public void afterCompose()
  {
    loadData();
  }
  
  private void loadData()
  {
    getFellow("buttonOk").setVisible(allowEditing);
    ((Button) getFellow("buttonCancel")).setLabel(Labels.getLabel(allowEditing ? "cancel" : "back"));

    int genderSel = -1;
    if (StringHelper.isNullOrEmpty(healthProfessional.getGender()) == false)
    {
      if (healthProfessional.getGender().equalsIgnoreCase("m"))
        genderSel = 0;
      else if (healthProfessional.getGender().equalsIgnoreCase("f") || healthProfessional.getGender().equalsIgnoreCase("w"))
        genderSel = 1;
    }

    ((Radiogroup) getFellow("rgGender")).setSelectedIndex(genderSel);
    
    ((Combobox) getFellow("cbType")).setValue(healthProfessional.getTypesString());
    ((Combobox) getFellow("cbSpeciality")).setValue(healthProfessional.getSpecialitiesString());
    
    ((Checkbox) getFellow("cbStatusActive")).setChecked(healthProfessional.isActive());
    ((Checkbox) getFellow("cbStatusInactive")).setChecked(healthProfessional.isInactive());
    ((Checkbox) getFellow("cbStatusRetired")).setChecked(healthProfessional.isRetired());
    ((Checkbox) getFellow("cbStatusDeceased")).setChecked(healthProfessional.isDeceased());
  }

  public void onOkClicked()
  {

  }

  public void onCancelClicked()
  {
    this.detach();
  }

  public void onOrgDetailsClicked()
  {
    String zul = "/Portal/hoiDetails.zul";
    Map map = new HashMap();
    map.put("name", "Details"); // TODO Daten übergeben
    map.put("hpiSearch", false);
    map.put("allowEditing", false);
    map.put("allowDetails", false);

    try
    {
      Window win = (Window) Executions.createComponents(zul, this, map);
      win.doModal();
    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, this);
    }

  }

  private void initOrganisations()
  {
    logger.debug("initOrganisations()");

    final org.zkoss.zk.ui.Desktop desktop = Executions.getCurrent().getDesktop();
    if (desktop.isServerPushEnabled() == false)
      desktop.enableServerPush(true);

    try
    {
      // use a thread to do webservice calls in background
      timer = new Timer();
      timer.schedule(new TimerTask()
      {
        @Override
        public void run()
        {
          // Header
          List<GenericTreeHeaderType> header = new LinkedList<GenericTreeHeaderType>();
          header.add(new GenericTreeHeaderType(Labels.getLabel("organisation"), 0, "", true, "String", false, true, false));
          header.add(new GenericTreeHeaderType(Labels.getLabel("gln"), 115, "", true, "String", false, true, false));

          // create data list (root elements)
          List<GenericTreeRowType> dataList = HoiDetails.createOrgTree(null, healthProfessional);

          // update gui
          try
          {
            Executions.activate(desktop);
            
            Include inc = (Include) getFellow("incTree");
            Window winGenericTree = (Window) inc.getFellow("winGenericTree");
            genericTree = (GenericTree) winGenericTree;

            genericTree.setMultiple(false);
            genericTree.setButton_new(false);
            genericTree.setButton_edit(false);
            genericTree.setButton_delete(false);
            genericTree.setShowRefresh(false);
            genericTree.setShowFilter(false);
            genericTree.setShowExpandCollapse(false);
            genericTree.setAutoExpandAll(true);
            genericTree.setListHeader(header);
            genericTree.setDataList(dataList);

            getFellow("divProgress").setVisible(false);
            getFellow("incTree").setVisible(true);

            Executions.deactivate(desktop);
          }
          catch (Exception ex)
          {
            LoggingOutput.outputException(ex, this);
          }
        }
      }, 50);  // start after 50ms

    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, this);
    }
  }

  /**
   * @return the healthProfessional
   */
  public HealthProfessional getHealthProfessional()
  {
    return healthProfessional;
  }

  /**
   * @param healthProfessional the healthProfessional to set
   */
  public void setHealthProfessional(HealthProfessional healthProfessional)
  {
    this.healthProfessional = healthProfessional;
  }
}
