package de.fhdo.gui;

import de.fhdo.helper.ArgumentHelper;
import de.fhdo.helper.IUpdate;
import de.fhdo.helper.SessionHelper;
import de.fhdo.helper.StringHelper;
import de.fhdo.list.GenericList;
import de.fhdo.list.GenericListCellType;
import de.fhdo.list.GenericListHeaderType;
import de.fhdo.list.GenericListRowType;
import de.fhdo.logging.LoggingOutput;
import de.fhdo.models.DN;
import de.fhdo.models.HealthOrganization;
import de.fhdo.models.HealthProfessional;
import de.fhdo.tree.GenericTree;
import de.fhdo.tree.GenericTreeCellType;
import de.fhdo.tree.GenericTreeHeaderType;
import de.fhdo.tree.GenericTreeRowType;
import de.fhdo.tree.IGenericTreeActions;
import de.fhdo.wsclient.HpdSearch;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class HoiDetails extends Window implements AfterCompose, IGenericTreeActions
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  private boolean allowEditing;
  private boolean hpiSearch;
  private boolean allowDetails;
  private HealthOrganization healthOrganization;
  private Timer timer;
  private GenericList genericListHP;
  private GenericList genericListOrg;
  private GenericTree genericTree;

  public HoiDetails()
  {
    Map args = Executions.getCurrent().getArg();
    if (args.get("winID") == null)
    {
      //Map arg = new HashMap();
      args.put("winID", "winHOIDetails");
      Executions.getCurrent().pushArg(args);
    }

    logger.debug("Win-ID: " + ArgumentHelper.getWindowArgumentString("winID"));
    //this.setId(ArgumentHelper.getWindowArgumentString("winID"));

    allowEditing = ArgumentHelper.getWindowArgumentBool("allowEditing");
    logger.debug("allowEditing: " + allowEditing);

    hpiSearch = ArgumentHelper.getWindowArgumentBool("hpiSearch");

    Object o = ArgumentHelper.getWindowArgument("data");
    if (o != null && o instanceof HealthOrganization)
    {
      healthOrganization = (HealthOrganization) o;
      logger.debug("HealthOrganization: " + healthOrganization.getName());
    }
    else
      logger.debug("HealthOrganization is null");

    allowDetails = ArgumentHelper.getWindowArgumentBool("allowDetails");
  }

  /**
   * Here you have access to gui components
   */
  public void afterCompose()
  {
    loadData(true);

    ((Button) getFellow("buttonApply")).addEventListener(Events.ON_CLICK, new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        applyForHPSearch();
      }
    });

    ((Button) getFellow("buttonOk")).addEventListener(Events.ON_CLICK, new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        onOkClicked();
      }
    });

    ((Button) getFellow("buttonCancel")).addEventListener(Events.ON_CLICK, new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        onCancelClicked();
      }
    });

  }

  private void loadData(boolean initializeOrgTree)
  {
    logger.debug("loadData()");

    getFellow("buttonOk").setVisible(allowEditing);
    ((Button) getFellow("buttonCancel")).setLabel(Labels.getLabel(allowEditing ? "cancel" : "back"));

    ((Textbox) getFellow("tbName")).setText(healthOrganization.getName());

    ((Checkbox) getFellow("cbStatusActive")).setChecked(healthOrganization.isActive());
    ((Checkbox) getFellow("cbStatusInactive")).setChecked(healthOrganization.isInactive());

    ((Textbox) getFellow("tbGLN")).setText(healthOrganization.getGln());
    //((Combobox) getFellow("cbLanguage")).setValue(healthOrganization.getLanguageString());

    //((Combobox) getFellow("cbType")).setValue(healthOrganization.getType().getTerm());
    ((Combobox) getFellow("cbType")).setValue(healthOrganization.getTypesString());
    ((Combobox) getFellow("cbSpeciality")).setValue(healthOrganization.getSpecialitiesString());

    //((Textbox) getFellow("tbAddress")).setText(healthOrganization.getAddress());
    ((Textbox) getFellow("tbAddress")).setText(healthOrganization.getPrimaryAddressString());
    ((Textbox) getFellow("tbAddress2")).setText(healthOrganization.getSecondaryAddressString());

//    ((Textbox) getFellow("tbZIP")).setText(healthOrganization.getZip());
//    ((Textbox) getFellow("tbCity")).setText(healthOrganization.getCity());
    getFellow("buttonApply").setVisible(SessionHelper.getValue("updateListenerListview") != null);

    // init by thread
    if (initializeOrgTree)
      initOrganisations();

    initSubLists();
  }

  public void onOkClicked()
  {

  }

  public void onCancelClicked()
  {
    this.detach();
  }

  private void initSubLists()
  {
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
          List<GenericListHeaderType> headerHP = new LinkedList<GenericListHeaderType>();
          headerHP.add(new GenericListHeaderType(Labels.getLabel("healthProfessional"), 0, "", false, "String", true, true, false, false));
          headerHP.add(new GenericListHeaderType(Labels.getLabel("type"), 220, "", false, "String", true, true, false, false));
          headerHP.add(new GenericListHeaderType(Labels.getLabel("details"), 60, "", false, "String", true, true, false, false));

          List<GenericListHeaderType> headerOrg = new LinkedList<GenericListHeaderType>();
          headerOrg.add(new GenericListHeaderType(Labels.getLabel("suborganisation"), 0, "", false, "String", true, true, false, false));
          headerOrg.add(new GenericListHeaderType(Labels.getLabel("type"), 220, "", false, "String", true, true, false, false));
          headerOrg.add(new GenericListHeaderType(Labels.getLabel("details"), 60, "", false, "String", true, true, false, false));

          // load data
          logger.debug("load Sub Organizations...");
          List<GenericListRowType> dataListHP = new LinkedList<GenericListRowType>();
          List<GenericListRowType> dataListOrg = new LinkedList<GenericListRowType>();

          logger.debug("Anz. HP: " + healthOrganization.getHealthProfessionals().size());

          for (HealthProfessional hp : healthOrganization.getHealthProfessionals())
          {
            dataListHP.add(createRowHP(hp));
          }

          logger.debug("Anz. Orgs: " + healthOrganization.getSubOrganizations().size());

          //healthOrganization.
          for (HealthOrganization org : healthOrganization.getSubOrganizations())
          {
            dataListOrg.add(createRowOrg(org));
          }

          // update gui
          try
          {
            Executions.activate(desktop);

            // init generic list
            Include inc = (Include) getFellow("incListHP");
            Window winGenericList = (Window) inc.getFellow("winGenericList");
            genericListHP = (GenericList) winGenericList;
            genericListHP.setButton_new(false);
            genericListHP.setButton_edit(false);
            genericListHP.setListHeader(headerHP);
            genericListHP.setDataList(dataListHP);

            Include incOrg = (Include) getFellow("incListOrg");
            Window winGenericListOrg = (Window) incOrg.getFellow("winGenericList");
            genericListOrg = (GenericList) winGenericListOrg;
            genericListOrg.setButton_new(false);
            genericListOrg.setButton_edit(false);
            genericListOrg.setListHeader(headerOrg);
            genericListOrg.setDataList(dataListOrg);

            // show components
            getFellow("gbHP").setVisible(false);
            getFellow("gbOrg").setVisible(false);
            getFellow("incListHP").setVisible(true);
            getFellow("incListOrg").setVisible(true);

            // deactivate desktop in Task
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

  private GenericListRowType createRowOrg(final HealthOrganization item)
  {
    GenericListRowType row = new GenericListRowType();

    int index = 0;
    GenericListCellType[] cells = new GenericListCellType[3];

    cells[index++] = new GenericListCellType(item.getName(), false, "");
    cells[index++] = new GenericListCellType(item.getTypesString(), false, "");

    Listcell lc = new Listcell();
    Button button = new Button("", "/rsc/img/symbols/forward_blue_16x16.png");

    button.addEventListener(Events.ON_CLICK, new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        onSubOrgDetailsClicked(item);
      }
    });

    lc.appendChild(button);
    cells[index++] = new GenericListCellType(lc, false, "");

    row.setData(item);
    row.setCells(cells);

    return row;
  }

  private GenericListRowType createRowHP(final HealthProfessional item)
  {
    GenericListRowType row = new GenericListRowType();

    int index = 0;
    GenericListCellType[] cells = new GenericListCellType[3];

    cells[index++] = new GenericListCellType(item.getName(), false, "");

    String type = "";
    if (item.getType() != null)
      type = item.getTypesString();

    cells[index++] = new GenericListCellType(type, false, "");

    Listcell lc = new Listcell();
    Button button = new Button("", "/rsc/img/symbols/forward_blue_16x16.png");

    button.addEventListener(Events.ON_CLICK, new EventListener<Event>()
    {
      public void onEvent(Event t) throws Exception
      {
        logger.debug("onClicK: " + item.getName());
        onHPDetailsClicked(item);
      }
    });

    lc.appendChild(button);
    cells[index++] = new GenericListCellType(lc, false, "");

    row.setData(item);
    row.setCells(cells);

    return row;
  }

  public void applyForHPSearch()
  {
    String gln = ((Textbox) getFellow("tbGLN")).getText();
    Object o = SessionHelper.getValue("updateListenerListview");
    if (o != null && o instanceof IUpdate)
    {
      ((IUpdate) o).update(gln);

      Component parentWindow = this.getParent();
      while (!(parentWindow instanceof Window))
      {
        parentWindow = parentWindow.getParent();
      }

      this.detach();
      parentWindow.detach();
    }
  }

  public void onHPDetailsClicked(HealthProfessional hp)
  {
    String zul = "/Portal/hpiDetails.zul";
    Map map = new HashMap();
    map.put("data", hp);
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
      Messagebox.show(ex.getLocalizedMessage());
    }
  }

  public void onSubOrgDetailsClicked(HealthOrganization ho)
  {
    // load new data
    String zul = "/Portal/hoiDetails.zul";
    Map map = new HashMap();
    map.put("data", ho);
    map.put("hpiSearch", false);
    map.put("allowEditing", false);
    map.put("allowDetails", false);

    map.put("winID", this.getId() + "_parent");

    try
    {
      Window win = (Window) Executions.createComponents(zul, this, map);
      win.doModal();
    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, this);
      Messagebox.show(ex.getLocalizedMessage());
    }
  }

  private void initOrganisations()
  {

    final org.zkoss.zk.ui.Desktop desktop = Executions.getCurrent().getDesktop();
    if (desktop.isServerPushEnabled() == false)
      desktop.enableServerPush(true);

    final IGenericTreeActions treeActions = this;

    try
    {
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
          List<GenericTreeRowType> dataList = createOrgTree(healthOrganization, null);
          
          // update gui
          try
          {
            Executions.activate(desktop);

            Include inc = (Include) getFellow("incTree");
            Window winGenericTree = (Window) inc.getFellow("winGenericTree");
            genericTree = (GenericTree) winGenericTree;

            genericTree.setMultiple(false);
            //genericTree.setTreeActions(this);
            //genericList.setUpdateDataListener(this);
            genericTree.setButton_new(false);
            genericTree.setButton_edit(false);
            genericTree.setButton_delete(false);
            genericTree.setShowRefresh(false);
            genericTree.setShowFilter(false);
            genericTree.setShowExpandCollapse(false);
            genericTree.setAutoExpandAll(true);
            genericTree.setListHeader(header);
            genericTree.setDataList(dataList);
            genericTree.setTreeActions(treeActions);

            getFellow("divProgressTree").setVisible(false);
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
   * Creates the tree view to display all organizations to root.
   *
   * @param healthOrganization starting from an organization
   * @param healthProfessional starting from a health professional
   * @param selectedGLN selects the GLN
   * @return
   */
  public static List<GenericTreeRowType> createOrgTree(HealthOrganization healthOrganization, HealthProfessional healthProfessional)
  {
    // create map with all (upper) organizations
    logger.debug("createOrgTree, initialize map...");
    Map<String, HealthOrganization> organizationMap = new HashMap<String, HealthOrganization>();

    List<String> selectedGLN = new LinkedList<String>();

    if (healthOrganization != null)
    {
      logger.debug("createOrgTree with ho: " + healthOrganization.getGln());
      organizationMap.put(healthOrganization.getGln(), healthOrganization);  // add own to map
      createUpperOrgMap(healthOrganization.getMemberOf(), organizationMap);
      
      // select organization in tree
      selectedGLN.add(healthOrganization.getGln());
    }
    if (healthProfessional != null)
    {
      logger.debug("createOrgTree with hp: " + healthProfessional.getGln());
      createUpperOrgMap(healthProfessional.getMemberOf(), organizationMap);

      // select organizations belongs to HP in tree
      for (String memberOfStr : healthProfessional.getMemberOf())
      {
        DN dn = new DN(memberOfStr);
        String gln = dn.getCn();
        selectedGLN.add(gln);
      }
    }
    logger.debug("map initialized, count: " + organizationMap.size());

    // create tree from map
    logger.debug("createOrgTree, initialize tree...");
    List<GenericTreeRowType> rows = new LinkedList<GenericTreeRowType>();

    for (HealthOrganization organization : organizationMap.values())
    {
      if (organization.getMemberOf() == null | organization.getMemberOf().size() == 0)
      {
        // root organization, add to rows
        GenericTreeRowType row = createRow(organization, selectedGLN);

        // add all child rows
        row.getChildRows().addAll(createChildRows(organization, selectedGLN, organizationMap));

        logger.debug("add root entry with gln: " + organization.getGln());
        rows.add(row);
      }
    }
    logger.debug("tree initialized, count root entries: " + rows.size());

    return rows;
  }

  private static List<GenericTreeRowType> createChildRows(HealthOrganization upperOrganization, List<String> selectedGLN, Map<String, HealthOrganization> organizationMap)
  {
    List<GenericTreeRowType> rows = new LinkedList<GenericTreeRowType>();

    for (HealthOrganization organization : organizationMap.values())
    {
      for (String memberOfStr : organization.getMemberOf())
      {
        DN dn = new DN(memberOfStr);
        String gln = dn.getCn();

        if (gln.equals(upperOrganization.getGln()))
        {
          // children found, add to list...
          GenericTreeRowType row = createRow(organization, selectedGLN);
          // add all child rows
          row.getChildRows().addAll(createChildRows(organization, selectedGLN, organizationMap));

          rows.add(row);
        }
      }
    }

    return rows;
  }

  private static void createUpperOrgMap(List<String> memberOfList, Map<String, HealthOrganization> organizationMap)
  {
    logger.debug("createUpperOrgMap...");
    if (memberOfList == null || memberOfList.size() == 0)
    {
      logger.debug("no elements found");
      return;  // root element
    }
    logger.debug("elements: " + memberOfList.size());

    for (String memberOfStr : memberOfList)
    {
      DN dn = new DN(memberOfStr);
      String gln = dn.getCn();

      // check if upper org exists
      logger.debug("check element with gln: " + gln);
      if (organizationMap.containsKey(gln))
      {
        // do nothing
        logger.debug("element found in map, do nothing");
      }
      else
      {
        // read details from web service and add to map
        logger.debug("element not found, read details from web service");
        try
        {
          HpdSearch search = new HpdSearch();
          search.setGln(dn.getCn());

          List<Object> list = search.performHOISearch();

          if (list != null && list.size() > 0)
          {
            // handle results
            for (Object obj : list)
            {
              if (obj instanceof HealthOrganization)
              {
                HealthOrganization healthOrganization = (HealthOrganization) obj;
                organizationMap.put(healthOrganization.getGln(), healthOrganization);

                logger.debug("add to map: " + healthOrganization.getGln());

                // recursive call
                createUpperOrgMap(healthOrganization.getMemberOf(), organizationMap);
              }
            }
          }
        }
        catch (Exception ex)
        {
          LoggingOutput.outputException(ex, HoiDetails.class);
        }

      }

    }
  }

  /**
   * Creates row for the tree view.
   * 
   * @param org The organization to display
   * @param selectedGLN A list of selected GLNs. The entry will be selected if it's in the list.
   * @return  the tree row
   */
  public static GenericTreeRowType createRow(HealthOrganization org, List<String> selectedGLN)
  {
    GenericTreeRowType row = new GenericTreeRowType(null);

    String style = "";
    if (StringHelper.isNullOrEmpty(org.getGln()) == false && selectedGLN.contains(org.getGln()))
    {
      logger.debug("bold: " + selectedGLN);
      style = "font-weight:bold;";
    }

    GenericTreeCellType[] cells = new GenericTreeCellType[2];
    cells[0] = new GenericTreeCellType(org.getName(), false, "", style);
    cells[1] = new GenericTreeCellType(org.getGln(), false, "", style);

    row.setData(org);
    row.setCells(cells);

    return row;
  }

  /**
   * @return the healthOrganization
   */
  public HealthOrganization getHealthOrganization()
  {
    return healthOrganization;
  }

  /**
   * @param healthOrganization the healthOrganization to set
   */
  public void setHealthOrganization(HealthOrganization healthOrganization)
  {
    this.healthOrganization = healthOrganization;
  }

  public void onTreeNewClicked(String string, Object o)
  {
  }

  public void onTreeEditClicked(String string, Object o)
  {
  }

  public boolean onTreeDeleted(String string, Object o)
  {
    return true;
  }

  public void onTreeSelected(String string, Object o)
  {
    logger.debug("tree item selected");
    if (o != null)
    {
      if (o instanceof HealthOrganization)
      {
        // disable gui elements
        getFellow("incListHP").setVisible(false);
        getFellow("incListOrg").setVisible(false);
        getFellow("gbHP").setVisible(true);
        getFellow("gbOrg").setVisible(true);

        // load dialog with selected org
        healthOrganization = (HealthOrganization) o;
        loadData(false);
      }
      else
      {
        logger.debug("unknown type: " + o.getClass().getCanonicalName());
      }
    }
  }

  public void onTreeRefresh(String string)
  {
  }

}
