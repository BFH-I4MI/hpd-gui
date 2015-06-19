package de.fhdo.gui;

import de.fhdo.helper.ArgumentHelper;
import de.fhdo.helper.IUpdate;
import de.fhdo.helper.PropertiesHelper;
import de.fhdo.helper.SessionHelper;
import de.fhdo.helper.StringHelper;
import de.fhdo.list.GenericList;
import de.fhdo.list.GenericListCellType;
import de.fhdo.list.GenericListHeaderType;
import de.fhdo.list.GenericListRowType;
import de.fhdo.list.IGenericListActions;
import de.fhdo.logging.LoggingOutput;
import de.fhdo.models.Address;
import de.fhdo.models.DN;
import de.fhdo.models.HealthOrganization;
import de.fhdo.models.HealthProfessional;
import de.fhdo.models.Language;
import de.fhdo.models.Languages;
import de.fhdo.models.TermserverCatalog;
import de.fhdo.models.TermserverCatalogValues;
import de.fhdo.terminologie.ws.search.Status;
import de.fhdo.wsclient.HpdSearch;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.ws.soap.SOAPFaultException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import types.termserver.fhdo.de.CodeSystemConcept;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class Listview extends Window implements AfterCompose, IGenericListActions, IUpdate
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  private boolean hpiVisible;

  private enum MESSAGE_CATEGORY
  {

    INFO, WARN, ERROR
  }

  private enum MODE
  {

    HOI, HPI
  }

  private GenericList genericList;
  private MODE mode;
  boolean allowEditing = false;
  boolean hpiSearch = false;

  private String filterName = "";

  public Listview()
  {
    logger.debug("Listview - constructor");

    // TODO in SecurityFilter auslagern
    String sessionIdParameter = ArgumentHelper.getWindowParameterString("sessionId");
    SessionHelper.setValue("sessionIdParameter", sessionIdParameter);
    
    if(sessionIdParameter != null && sessionIdParameter.length() > 0)
    {
      // check login
      logger.debug("Try login with sessionId...");
//      AuthenticateResponse.Return response = WebserviceHelper.authenticate(sessionIdParameter);
//      
//      if(response.getReturnInfos().getStatus() == de.fhdo.terminologie.ws.authorization.Status.OK)
//      {
//        if(response.getParameterList() != null && response.getParameterList().size() > 0)
//          SessionHelper.setValue("sessionId", response.getParameterList().get(0));
//        else SessionHelper.setValue("sessionId", sessionIdParameter);
//        
//        logger.debug("sessionId accepted: " + SessionHelper.getString("sessionId"));
//      }
      
      //Authorization.authenticate(sessionIdParameter);
    }
    
//    String username = ArgumentHelper.getWindowParameterString("usr");
//    String password = ArgumentHelper.getWindowParameterString("pw");
//
//    logger.debug("username: " + username);
//    logger.debug("password: " + password);

    Map args = Executions.getCurrent().getArg();
    if (args.get("winID") == null)
    {
      Map arg = new HashMap();
      arg.put("winID", "winHOI_HPI");
      Executions.getCurrent().pushArg(arg);
    }

    hpiSearch = ArgumentHelper.getWindowParameterBool("hpisearch");
    String s_mode = ArgumentHelper.getWindowParameterString("mode");
    if (s_mode == null || s_mode.length() == 0)
    {
      s_mode = ArgumentHelper.getWindowArgumentString("mode");
      hpiSearch = ArgumentHelper.getWindowParameterBool("hpisearch");
    }
    if (s_mode != null && s_mode.equalsIgnoreCase("hoi"))
    {
      mode = MODE.HOI;
      hpiVisible = false;
    }
    else if (s_mode != null && s_mode.equalsIgnoreCase("hpi"))
    {
      mode = MODE.HPI;
      hpiVisible = true;
    }

    logger.debug("mode: " + mode);

    String s_locale = ArgumentHelper.getWindowParameterString("locale");
    if (s_locale != null && s_locale.length() > 0)
    {
      logger.debug("locale: " + s_locale);
      boolean switchLang = true;

      Object o = SessionHelper.getValue(org.zkoss.web.Attributes.PREFERRED_LOCALE);
      if (o != null)
      {
        Locale current_locale = (Locale) o;
        if (current_locale.getLanguage().equalsIgnoreCase(s_locale))
        {
          switchLang = false;
        }
      }

      if (switchLang)
      {
        logger.debug("switch language to: " + s_locale);
        Locale prefer_locale = new Locale(s_locale);
        SessionHelper.setValue(org.zkoss.web.Attributes.PREFERRED_LOCALE, prefer_locale);

        Executions.sendRedirect("");
      }
    }

    // filter
    filterName = ArgumentHelper.getWindowArgumentString("filter_name");
    logger.debug("filterName: " + filterName);

    //BatchResponse response = new BatchResponse();
    //response.getBatchResponses()
  }

  /**
   * Initialize the gui here, full gui access
   */
  public void afterCompose()
  {

    // look if search is available or error message is shown
    boolean searchVisible = true;

    if (mode == null || StringHelper.isNullOrEmpty(PropertiesHelper.getInstance().getHpdUrl()))
    {
      // show error Message and hide everything else
      getFellow("mainBorderlayout").setVisible(false);

      if (mode == null)
        getFellow("divError").setVisible(true);

      if (StringHelper.isNullOrEmpty(PropertiesHelper.getInstance().getHpdUrl()))
      {
        getFellow("divErrorConfiguration").setVisible(true);

        getFellow("buttonReloadConfiguration").addEventListener(Events.ON_CLICK, new EventListener<Event>()
        {
          public void onEvent(Event t) throws Exception
          {
            PropertiesHelper.getInstance().reload();
            Executions.sendRedirect(null); // reload page
          }
        });
      }

      searchVisible = false;
    }
    else
    {
//      getFellow("gridHOI").setVisible(false);
//       getFellow("gridHPI").setVisible(false);
//
//       if (mode == MODE.HOI)
//       {
//       getFellow("gridHOI").setVisible(true);
//       }
//       if (mode == MODE.HPI)
//       {
//       getFellow("gridHPI").setVisible(true);
//       }
    }

    if (searchVisible)
    {
      // add events to components
      this.addEventListener(Events.ON_OK, new EventListener<Event>()
      {
        public void onEvent(Event t) throws Exception
        {
          onSearchClicked();
        }
      });

      getFellow("bandboxOrg").addEventListener(Events.ON_OPEN, new EventListener<org.zkoss.zk.ui.event.OpenEvent>()
      {
        public void onEvent(org.zkoss.zk.ui.event.OpenEvent event) throws Exception
        {
          String value = "";
          if (event.getValue() != null)
            value = event.getValue().toString();
          onOpenBandbox(value);
        }
      });
      getFellow("bandboxOrg").addEventListener(Events.ON_CHANGING, new EventListener<org.zkoss.zk.ui.event.InputEvent>()
      {
        public void onEvent(org.zkoss.zk.ui.event.InputEvent ie) throws Exception
        {
          logger.debug("ON_CHANGING: " + ie.getValue());
        }
      });

      getFellow("buttonSearch").addEventListener(Events.ON_CLICK, new EventListener<Event>()
      {
        public void onEvent(Event t) throws Exception
        {
          onSearchClicked();
        }
      });
      
//      getFellow("hoDetailsDemoButton").addEventListener(Events.ON_CLICK, new EventListener<Event>()
//      {
//        public void onEvent(Event t) throws Exception
//        {
//          HealthOrganization ho = new HealthOrganization();
//          ho.setName("FH Dortmund");
//          ho.setCity("Dortmund");
//          ho.setAddress("Emil-Figge-Str. 42");
//          ho.setZip("44227");
//          ho.setGln("123456789");
//          ho.setLanguage("de");
//          
//          showDetails(ho, false);
//        }
//      });

      // show/hide components
      getFellow("labelGender").setVisible(hpiVisible);
      getFellow("divGender").setVisible(hpiVisible);
      getFellow("labelOrg").setVisible(hpiVisible);
      getFellow("bandboxOrg").setVisible(hpiVisible);
      
      getFellow("cbStatusRetired").setVisible(hpiVisible);
      getFellow("cbStatusDeceased").setVisible(hpiVisible);

      // fill language list
      Languages.getInstance().fillCombobox((Combobox) getFellow("cbLanguage"), "", true);

      // reload properties file, if failure
      if (SessionHelper.getBoolean("reload_property_file"))
      {
        PropertiesHelper.getInstance().reload();
      }

      SessionHelper.setValue("reload_property_file", false);

      if (mode == MODE.HOI)
      {
        TermserverCatalogValues catalogType = TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidOrganizationType());
        catalogType.fillCombobox((Combobox) getFellow("cbType"), Labels.getLabel("all"), true);
        if (catalogType.getWsResponse().getReturnInfos().getStatus() == Status.FAILURE)
        {
          getFellow("cbType").setVisible(false);
          getFellow("divTypeFailure").setVisible(true);
          ((Label) getFellow("labelTypeFailure")).setValue(catalogType.getWsResponse().getReturnInfos().getMessage());

          SessionHelper.setValue("reload_property_file", true);
        }

        TermserverCatalogValues catalogSpec = TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidOrganizationSpeciality());
        catalogSpec.fillCombobox((Combobox) getFellow("cbSpeciality"), Labels.getLabel("all"), true);
        if (catalogSpec.getWsResponse().getReturnInfos().getStatus() == Status.FAILURE)
        {
          getFellow("cbSpeciality").setVisible(false);
          getFellow("divSpecialityFailure").setVisible(true);
          ((Label) getFellow("labelSpecialityFailure")).setValue(catalogSpec.getWsResponse().getReturnInfos().getMessage());

          SessionHelper.setValue("reload_property_file", true);
        }
      }
      else if (mode == MODE.HPI)
      {
        TermserverCatalogValues catalogType = TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidHealthProfessionalType());
        catalogType.fillCombobox((Combobox) getFellow("cbType"), Labels.getLabel("all"), true);
        if (catalogType.getWsResponse().getReturnInfos().getStatus() == Status.FAILURE)
        {
          getFellow("cbType").setVisible(false);
          getFellow("divTypeFailure").setVisible(true);
          ((Label) getFellow("labelTypeFailure")).setValue(catalogType.getWsResponse().getReturnInfos().getMessage());

          SessionHelper.setValue("reload_property_file", true);
        }

        TermserverCatalogValues catalogSpec = TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidHealthProfessionalSpeciality());
        catalogSpec.fillCombobox((Combobox) getFellow("cbSpeciality"), Labels.getLabel("all"), true);
        if (catalogSpec.getWsResponse().getReturnInfos().getStatus() == Status.FAILURE)
        {
          getFellow("cbSpeciality").setVisible(false);
          getFellow("divSpecialityFailure").setVisible(true);
          ((Label) getFellow("labelSpecialityFailure")).setValue(catalogSpec.getWsResponse().getReturnInfos().getMessage());

          SessionHelper.setValue("reload_property_file", true);
        }

      }

      // set filter from parameter
      boolean performSearch = false;

      if (StringHelper.isNullOrEmpty(filterName) == false)
      {
        if (filterName.length() == 13)
          ((Textbox) getFellow("tbGLN")).setText(filterName);
        else
          ((Textbox) getFellow("tbName")).setText(filterName);
        performSearch = true;
      }

      if (performSearch)
      {
        onSearchClicked();
      }
    }

    // Statusbar
    getFellow("south").setVisible(PropertiesHelper.getInstance().isInfosVisible());

  }

  private String getFilterTypeStr()
  {
    TermserverCatalogValues catalogType = null;

    if (mode == MODE.HOI)
    {
      catalogType = TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidOrganizationType());
    }
    else if (mode == MODE.HPI)
    {
      catalogType = TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidHealthProfessionalType());
    }

    if (catalogType != null)
    {
      CodeSystemConcept csc = catalogType.getCodeSystemConceptFromCombobox((Combobox) getFellow("cbType"));
      if (csc != null)
        return csc.getCode();
    }

    return "";
  }
  
  private String getFilterFachrichtungStr()
  {
    TermserverCatalogValues catalogType = null;

    if (mode == MODE.HOI)
    {
      catalogType = TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidOrganizationSpeciality());
    }
    else if (mode == MODE.HPI)
    {
      catalogType = TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidHealthProfessionalSpeciality());
    }

    if (catalogType != null)
    {
      CodeSystemConcept csc = catalogType.getCodeSystemConceptFromCombobox((Combobox) getFellow("cbSpeciality"));
      if (csc != null)
        return csc.getCode();
    }

    return "";
  }

  public void onSearchClicked()
  {
    logger.debug("onSearchClicked()");

    try
    {
      // perform webservice search
      HpdSearch search = new HpdSearch();
      // TODO apply all parameter
      search.setGln(((Textbox) getFellow("tbGLN")).getText());
      search.setName(((Textbox) getFellow("tbName")).getText());
      search.setAddress(((Textbox) getFellow("tbAddress")).getText());
      search.setZip(((Textbox) getFellow("tbZIP")).getText());
      search.setCity(((Textbox) getFellow("tbCity")).getText());

      search.setFachrichtung(getFilterFachrichtungStr());
      search.setTyp(getFilterTypeStr());

      search.setActive(((Checkbox) getFellow("cbStatusActive")).isChecked());
      search.setInactive(((Checkbox) getFellow("cbStatusInactive")).isChecked());
      
      if(hpiVisible)
      {
        search.setRetired(((Checkbox) getFellow("cbStatusRetired")).isChecked());
        search.setDeceased(((Checkbox) getFellow("cbStatusDeceased")).isChecked());
      }

//        if (((Checkbox) getFellow("cbStatusActive")).isChecked())
//          search.setStatus("active");
//        else if (((Checkbox) getFellow("cbStatusInactive")).isChecked())
//          search.setStatus("inactive");
      Comboitem selectedItem = ((Combobox) getFellow("cbLanguage")).getSelectedItem();
      if (selectedItem != null)
      {
        Language lang = selectedItem.getValue();
        search.setLanguageCd(lang.get2LetterCode());
      }
      double penetration;
      if (mode == MODE.HPI)
      {
        // only helath professional has gender selection
        search.setMale(((Checkbox) getFellow("cbGenderMale")).isChecked());
        search.setFemale(((Checkbox) getFellow("cbGenderFemale")).isChecked());

        // only helath professional has Org-GLN
        String orgText = ((Bandbox) getFellow("bandboxOrg")).getText();
        if (StringHelper.isNullOrEmpty(orgText) == false)
        {
          DN dn = new DN(orgText, "Relationship", "HPD", "ehealth-suisse", "ch");
          logger.debug("[Listview.java] search with memberof dn: " + dn.toString());
          search.setMemberOf(dn.toString());
        }
      }

      List<Object> list = null;
      if (mode == MODE.HPI)
        list = search.performHPISearch();
      else if (mode == MODE.HOI)
        list = search.performHOISearch();

      int countResults = 0;
      if (list != null)
        countResults = list.size();

      if (countResults == 0)
      {
        showMessage(Labels.getLabel("search.noresults"), MESSAGE_CATEGORY.INFO);
        //showMessage("Keine Treffer mit den angegebenen Filtern. Bitte ändern Sie die Suchkriterien und suchen Sie erneut.", MESSAGE_CATEGORY.INFO);
        getFellow("incList").setVisible(false);
        //incList.setVisible(false);
      }
      else
      {
        showMessage(null, MESSAGE_CATEGORY.INFO);

        initList(list);
        getFellow("incList").setVisible(true);
        //incList.setVisible(true);
      }

    }
    catch (SOAPFaultException ex)
    {
      LoggingOutput.outputException(ex, this);

      showMessage("SOAP-" + Labels.getLabel("error") + ": " + ex.getFault().getFaultString(), MESSAGE_CATEGORY.ERROR);
      getFellow("incList").setVisible(false);
    }
    catch (IOException ex)
    {
      LoggingOutput.outputException(ex, this);
      showMessage(Labels.getLabel("errormsg.timeout"), MESSAGE_CATEGORY.ERROR);
      getFellow("incList").setVisible(false);
    }
    catch (Exception ex)
    {
      // error Message
      LoggingOutput.outputException(ex, this);
      showMessage(Labels.getLabel("error") + ": " + ex.getLocalizedMessage(), MESSAGE_CATEGORY.ERROR);
      getFellow("incList").setVisible(false);
    }

  }

  private void initList(List<Object> list)
  {
    logger.debug("initList()");
    boolean showFilter = true;

    List<GenericListHeaderType> header = new LinkedList<GenericListHeaderType>();

    header.add(new GenericListHeaderType(Labels.getLabel("gln"), 125, "", showFilter, "String", true, true, false, false));
    header.add(new GenericListHeaderType(Labels.getLabel("listheader.name"), 300, "", showFilter, "String", true, true, false, false));
    header.add(new GenericListHeaderType(Labels.getLabel("type"), 230, "", showFilter, "String", true, true, false, false));
    header.add(new GenericListHeaderType(Labels.getLabel("speciality"), 150, "", showFilter, "String", true, true, false, false));
    header.add(new GenericListHeaderType(Labels.getLabel("language.language"), 80, "", showFilter, "String", true, true, false, false));
    
    if (mode == MODE.HPI)
      header.add(new GenericListHeaderType(Labels.getLabel("gender").substring(0, 1) + ".", 30, "", false, "String", true, true, false, false));

    //header.add(new GenericListHeaderType(Labels.getLabel("status"), 50, "", showFilter, "Bool", true, true, false, true));
    header.add(new GenericListHeaderType(Labels.getLabel("status"), 65, "", showFilter, "String", true, true, false, false));
    header.add(new GenericListHeaderType(Labels.getLabel("address"), 250, "", showFilter, "String", true, true, false, false));
    header.add(new GenericListHeaderType(Labels.getLabel("zip"), 70, "", showFilter, "String", true, true, false, false));
    header.add(new GenericListHeaderType(Labels.getLabel("city"), 200, "", showFilter, "String", true, true, false, false));

    List<GenericListRowType> dataList = new LinkedList<GenericListRowType>();

    for (Object element : list)
    {
      dataList.add(createRow(element));
    }
    //dataList.add(createRow("Robert Mützner"));
    //dataList.add(createRow("Peter Haas"));

    // Liste initialisieren
    Include inc = (Include) getFellow("incList");
    Window winGenericList = (Window) inc.getFellow("winGenericList");
    genericList = (GenericList) winGenericList;

    if (allowEditing)
    {
      genericList.setButton_new(true);
      genericList.setButton_edit(true);
    }
    else
    {
      genericList.setButton_new(false);
      genericList.setButton_edit(false);

      genericList.removeCustomButtons();

      Button button = new Button(Labels.getLabel("showDetails"));
      button.addEventListener(Events.ON_CLICK, new EventListener<Event>()
      {
        public void onEvent(Event t) throws Exception
        {
          de.fhdo.list.GenericListRowType row = genericList.getSelection();
          showDetails(row.getData(), false);
        }
      });

      genericList.addCustomButton(button);
    }
    /*
     genericList.setUpdateDataListener(this);
     genericList.setButton_new(true);
     genericList.setButton_edit(true);
     genericList.setButton_delete(true);*/
    //genericList.setCheckable(true);

    genericList.setListActions(this);
    genericList.setListHeader(header);
    genericList.setDataList(dataList);

    genericList.setShowCount(true);
  }

  private GenericListRowType createRow(Object element)
  {
    GenericListRowType row = new GenericListRowType();

    int index = 0;
    GenericListCellType[] cells = new GenericListCellType[mode == MODE.HPI ? 10 : 9];

    if (element instanceof HealthProfessional)
    {
      HealthProfessional hp = (HealthProfessional) element;

      cells[index++] = new GenericListCellType(hp.getGln(), false, "");
      cells[index++] = new GenericListCellType(hp.getName(), false, "");
      cells[index++] = new GenericListCellType(hp.getTypesString(), false, "");
      cells[index++] = new GenericListCellType(hp.getSpecialitiesString(), false, "");
      cells[index++] = new GenericListCellType(hp.getLanguageCodeString(), false, "");
      cells[index++] = new GenericListCellType(hp.getGenderCell(), false, ""); // gender
      //cells[index++] = new GenericListCellType(hp.getStatus() != null && hp.getStatus().equalsIgnoreCase("active"), false, "");  // Status -> Boolean
      cells[index++] = new GenericListCellType(hp.getLocalizedStatus(), false, "");
      
      Address addr = hp.getPrimaryAddress();
      
//      cells[index++] = new GenericListCellType(hp.getAddress(), false, "");
//      cells[index++] = new GenericListCellType(hp.getZip(), false, "");
//      cells[index++] = new GenericListCellType(hp.getCity(), false, "");
      cells[index++] = new GenericListCellType(addr == null ? "" : addr.getStreetLine(), false, "");
      cells[index++] = new GenericListCellType(addr == null ? "" : addr.getPostalCode(), false, "");
      cells[index++] = new GenericListCellType(addr == null ? "" : addr.getCity(), false, "");

    }
    else if (element instanceof HealthOrganization)
    {
      HealthOrganization ho = (HealthOrganization) element;

      cells[index++] = new GenericListCellType(ho.getGln(), false, "");
      cells[index++] = new GenericListCellType(ho.getName(), false, "");
//      cells[index++] = new GenericListCellType(ho.getType().getTerm(), false, "");
//      cells[index++] = new GenericListCellType(ho.getSpeciality().getTerm(), false, "");
      
      cells[index++] = new GenericListCellType(ho.getTypesString(), false, "");
      cells[index++] = new GenericListCellType(ho.getSpecialitiesString(), false, "");
      
      cells[index++] = new GenericListCellType(ho.getLanguageCodeString(), false, "");
      //cells[index++] = new GenericListCellType(ho.getStatus() != null && ho.getStatus().equalsIgnoreCase("active"), false, "");  // Status -> Boolean
      cells[index++] = new GenericListCellType(ho.getLocalizedStatus(), false, "");
      
      Address addr = ho.getPrimaryAddress();
      logger.debug("create row - address: " + addr);
      
      cells[index++] = new GenericListCellType(addr == null ? "" : addr.getStreetLine(), false, "");
      cells[index++] = new GenericListCellType(addr == null ? "" : addr.getPostalCode(), false, "");
      cells[index++] = new GenericListCellType(addr == null ? "" : addr.getCity(), false, "");
    }

//    cells[1] = new GenericListCellType(person.getName(), false, "");
//    cells[2] = new GenericListCellType(person.getGeburtsdatum(), false, "");
//    cells[3] = new GenericListCellType(person.isMaennlich(), false, "");
//    cells[4] = new GenericListCellType(person.getFamilienstand(), false, "");

    /*header.add(new GenericListHeaderType(Labels.getLabel("gln"), 100, "", showFilter, "String", true, true, false, false));
     header.add(new GenericListHeaderType(Labels.getLabel("listheader.name"), 0, "", showFilter, "String", true, true, false, false));
     header.add(new GenericListHeaderType(Labels.getLabel("type"), 100, "", showFilter, "String", true, true, false, false));
     header.add(new GenericListHeaderType(Labels.getLabel("speciality"), 100, "", showFilter, "String", true, true, false, false));
     header.add(new GenericListHeaderType(Labels.getLabel("language"), 80, "", showFilter, "String", true, true, false, false));
     header.add(new GenericListHeaderType(Labels.getLabel("gender"), 60, "", showFilter, "String", true, true, false, false));
     header.add(new GenericListHeaderType(Labels.getLabel("status"), 60, "", showFilter, "String", true, true, false, false));
     header.add(new GenericListHeaderType(Labels.getLabel("address"), 250, "", showFilter, "String", true, true, false, false));
     header.add(new GenericListHeaderType(Labels.getLabel("zip"), 60, "", showFilter, "String", true, true, false, false));
     header.add(new GenericListHeaderType(Labels.getLabel("city"), 200, "", showFilter, "String", true, true, false, false));*/
    // Beispielhaft ein Icon einfügen in die Liste
    /*Component compNote = null;
     Image image = new Image("/rsc/img/filetypes/note.png");
     image.setTooltiptext("Das ist eine Notiz");
     compNote = image;
     cells[5] = new GenericListCellType(compNote, false, "");*/
    row.setData(element);
    row.setCells(cells);

    return row;
  }

  /**
   * Shows a message in the result list.
   *
   * @param MessageText Message text, pass null or an empty string to hide the
   * message.
   */
  private void showMessage(String MessageText, MESSAGE_CATEGORY Category)
  {
    boolean showMessage = false;

    if (MessageText != null && MessageText.length() > 0)
    {
      // set message
      showMessage = true;

      ((Label) getFellow("labelMessage")).setValue(MessageText);

      // set image
      String src = "/rsc/img/symbols/info_blue_32x32.png";//"/rsc/img/symbols/Info.png";

      if (Category == MESSAGE_CATEGORY.ERROR)
        src = "/rsc/img/symbols/Error.png";
      else if (Category == MESSAGE_CATEGORY.WARN)
        src = "/rsc/img/symbols/Warning.png";

      logger.debug("image: " + src);

      ((Image) getFellow("imageMessage")).setSrc(src);
    }

    ((Div) getFellow("divMessage")).setVisible(showMessage);
  }

  public void onNewClicked(String string)
  {
  }

  public void onEditClicked(String string, Object o)
  {
    logger.debug("onEditClicked");

    showDetails(o, allowEditing);
  }

  public void onDeleted(String string, Object o)
  {
  }

  public void onSelected(String string, Object o)
  {
  }

  private void showDetails(Object obj, boolean editable)
  {
    logger.debug("showDetails, editable: " + editable);

    if (obj == null)
      return;

    String zul = "";
    Map map = new HashMap();
    map.put("data", obj);
    map.put("hpiSearch", hpiSearch);
    map.put("allowDetails", true);

    logger.debug("Obj-Type: " + obj.getClass().getCanonicalName());

    if (mode == MODE.HOI)
    {
      if (obj instanceof String)
      {
        logger.debug("obj is string: " + obj.toString());
        //map.put("phenomenonID", PhenomenonID);
      }

      zul = "/Portal/hoiDetails.zul";
      //zul = "/Portal/listview.zul";
    }
    else if (mode == MODE.HPI)
    {
      zul = "/Portal/hpiDetails.zul";
    }

    if (zul.length() > 0)
    {
      map.put("allowEditing", allowEditing);

      try
      {
        logger.debug("create window...");

        Window win = (Window) Executions.createComponents(zul, this, map);

        //((CourtExhibitsDetails) win).setUpdateInterface(refThis);
        logger.debug("open window...");
        win.doModal();
      }
      catch (Exception ex)
      {
        LoggingOutput.outputException(ex, this);
      }
    }

  }

  /**
   * search for an organisation in a modal dialog
   */
  public void onOpenBandbox(String value)
  {
    logger.error("ON OPEN");

    SessionHelper.setValue("updateListenerListview", this);

    //Listview win = (Listview) Executions.createComponents("/Portal/listview.zul?mode=hoi&hpisearch=true", null, null);
    Map map = new HashMap();
    map.put("winID", "winHOISearch");
    map.put("mode", "hoi");
    map.put("hpisearch", true);

    this.focus(); // save text in bandbox
    ((Bandbox) getFellow("bandboxOrg")).focus();
    ((Bandbox) getFellow("bandboxOrg")).setFocus(false);

    //logger.debug("add filter 'filter_name': " + ((Bandbox)getFellow("bandboxOrg")).getText());
    map.put("filter_name", value);

    Window win2 = (Window) Executions.getCurrent().createComponents("/Portal/listview.zul", null, map);

    Listview win = (Listview) win2;

    // make Window modal
    //win.setId("winHOISearch");
    win.setBorder("normal");
    win.setTitle(Labels.getLabel("healhOrganisation") + " - " + Labels.getLabel("Search"));
    win.setWidth("95%");
    win.setHeight("95%");
    win.setClosable(true);
    win.setSizable(false);

    /*<window border="false" width="100%" height="100%"
     id="winHOI" use="de.fhdo.gui.Listview"
     onOK="winHOI.onSearchClicked()">*/
    /*<window id="winHOIDetails" title="${labels.healhOrganisation} - ${labels.details}" width="950px"  height="630px" border="normal"
     use="de.fhdo.gui.HoiDetails" closable="true"
     sizable="false">*/
    //((CourtExhibitsDetails) win).setUpdateInterface(refThis);
    logger.debug("open window...");
    win.doModal();
  }

  /**
   * @return the hpiVisible
   */
  public boolean isHpiVisible()
  {
    return hpiVisible;
  }

  /**
   * @param hpiVisible the hpiVisible to set
   */
  public void setHpiVisible(boolean hpiVisible)
  {
    this.hpiVisible = hpiVisible;
  }

  public void update(Object o)
  {
    logger.debug("Update Listview.java");

    if (o != null && o instanceof String)
    {
      String gln = o.toString();
      logger.debug("GLN: " + gln);
      ((Bandbox) getFellow("bandboxOrg")).setText(gln);

      SessionHelper.setValue("updateListenerListview", null);
    }
  }

}
