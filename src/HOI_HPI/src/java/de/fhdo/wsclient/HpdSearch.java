/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.wsclient;

import de.fhdo.helper.PropertiesHelper;
import de.fhdo.helper.StringHelper;
import de.fhdo.logging.LoggingOutput;
import de.fhdo.models.Address;
import de.fhdo.models.HealthOrganization;
import de.fhdo.models.HealthProfessional;
import de.fhdo.models.Language;
import de.fhdo.models.Languages;
import de.fhdo.models.TermserverCatalog;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import oasis.names.tc.dsml._2._0.core.AttributeDescription;
import oasis.names.tc.dsml._2._0.core.AttributeDescriptions;
import oasis.names.tc.dsml._2._0.core.AttributeValueAssertion;
import oasis.names.tc.dsml._2._0.core.BatchRequest;
import oasis.names.tc.dsml._2._0.core.BatchResponse;
import oasis.names.tc.dsml._2._0.core.DsmlAttr;
import oasis.names.tc.dsml._2._0.core.Filter;
import oasis.names.tc.dsml._2._0.core.FilterSet;
import oasis.names.tc.dsml._2._0.core.SearchRequest;
import oasis.names.tc.dsml._2._0.core.SearchResponse;
import oasis.names.tc.dsml._2._0.core.SearchResultEntry;
import oasis.names.tc.dsml._2._0.core.SubstringFilter;
import org.zkoss.util.resource.Labels;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class HpdSearch
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  private String gln, name, address, zip, city, languageCd, memberOf, fachrichtung, typ;
  private boolean male, female;
  private boolean active, inactive, retired, deceased;

  private static int currentRequestId = 1;

  private boolean checkFilter()
  {
    /*if (StringHelper.isNullOrEmpty(gln)
     && StringHelper.isNullOrEmpty(name)
     && StringHelper.isNullOrEmpty(address)
     && StringHelper.isNullOrEmpty(zip)
     && StringHelper.isNullOrEmpty(city)
     )
     {
     Messagebox.show(Labels.getLabel("search.nofilter"), Labels.getLabel("Search"), Messagebox.OK, Messagebox.INFORMATION);
     return false;
     }*/

    /*if (StringHelper.isNullOrEmpty(gln)
     && StringHelper.isNullOrEmpty(name)
     && StringHelper.isNullOrEmpty(address)
     && StringHelper.isNullOrEmpty(zip)
     && StringHelper.isNullOrEmpty(city))
     {
     if (StringHelper.isNullOrEmpty(status))
     {
     Messagebox.show(Labels.getLabel("search.noStatus"), Labels.getLabel("Search"), Messagebox.OK, Messagebox.INFORMATION);
     return false;
     }

     if (male == false && female == false)
     {
     Messagebox.show(Labels.getLabel("search.noGender"), Labels.getLabel("Search"), Messagebox.OK, Messagebox.INFORMATION);
     return false;
     }
     }*/
    return true;
  }

  public List<Object> performHOISearch() throws Exception
  {
    logger.debug("performHOISearch()");

    List<Object> list = new LinkedList<Object>();

    if (checkFilter() == false)
      return list;

    oasis.names.tc.dsml._2._0.core.BatchRequest request = new BatchRequest();

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.setRequestID("" + currentRequestId++);
    searchRequest.setDn("dc=HPD,o=ehealth-suisse,c=ch");
    searchRequest.setScope("wholeSubtree");
    searchRequest.setDerefAliases("neverDerefAliases");
    searchRequest.setSizeLimit(100l);

    // create filter
    searchRequest.setFilter(createFilter(false, true));

    // create attributes
    searchRequest.setAttributes(createAttributes(true, false));

    // HOI
    request.getBatchRequests().add(searchRequest);

    logger.debug("start webservice call");
    BatchResponse response = WebserviceHelper.providerInformationQueryRequest(request);
    if (response != null)
    {
      logger.debug("Result-Request-ID: " + response.getRequestID());
      for (javax.xml.bind.JAXBElement o : response.getBatchResponses())
      {
        logger.debug("Result object type: " + o.getValue().getClass().getCanonicalName());

        if (o.getValue() instanceof SearchResponse)
        {

          SearchResponse searchResponse = (SearchResponse) o.getValue();
          for (SearchResultEntry entry : searchResponse.getSearchResultEntry())
          {
            Object obj = createHealthOrganizationFromSearchResult(entry);
            if (obj != null)
              list.add(obj);
          }
        }
      }
    }

    return list;
  }

  private AttributeDescriptions createAttributes(boolean hoi, boolean hpi)
  {
    AttributeDescriptions attributes = new AttributeDescriptions();
    attributes.getAttribute().add(createAttribute("objectClass"));
    attributes.getAttribute().add(createAttribute("givenName"));
    attributes.getAttribute().add(createAttribute("sn"));
    attributes.getAttribute().add(createAttribute("cn"));
    attributes.getAttribute().add(createAttribute("memberOf"));
    attributes.getAttribute().add(createAttribute("HcPracticeLocation"));
    attributes.getAttribute().add(createAttribute("HcPrincipalPracticeLocation"));
    attributes.getAttribute().add(createAttribute("HcRegisteredAddr"));
    attributes.getAttribute().add(createAttribute("HcServiceLocations"));
    attributes.getAttribute().add(createAttribute("hpdProviderPracticeAddress"));
    attributes.getAttribute().add(createAttribute("HcIdentifier"));
    attributes.getAttribute().add(createAttribute("HcRegisteredName"));
    attributes.getAttribute().add(createAttribute("HcRole"));
    attributes.getAttribute().add(createAttribute("HcSpecialisation"));
    attributes.getAttribute().add(createAttribute("HcOperatingHours"));
    attributes.getAttribute().add(createAttribute("hpdProviderLanguageSupported"));
    if (hpi)
      attributes.getAttribute().add(createAttribute("Gender"));
    attributes.getAttribute().add(createAttribute("description"));
    attributes.getAttribute().add(createAttribute("hcProfession"));
    attributes.getAttribute().add(createAttribute("displayName"));
    attributes.getAttribute().add(createAttribute("hpdProviderStatus"));

    // HOI
    attributes.getAttribute().add(createAttribute("businessCategory"));

    return attributes;
  }

  public List<Object> performHOI_HPISearch() throws Exception
  {
    logger.debug("performHOI_HPISearch()");

    List<Object> list = new LinkedList<Object>();

    if (checkFilter() == false)
      return list;

    oasis.names.tc.dsml._2._0.core.BatchRequest request = new BatchRequest();

    //request.setAuthRequest(new AuthRequest());
    //request.getAuthRequest().
    SearchRequest searchRequest = new SearchRequest();
    searchRequest.setRequestID("" + currentRequestId++);
    searchRequest.setDn("dc=HPD,o=ehealth-suisse,c=ch");
    searchRequest.setScope("wholeSubtree");
    searchRequest.setDerefAliases("neverDerefAliases");
    searchRequest.setSizeLimit(100l);

    // create filter
    searchRequest.setFilter(createFilter(true, true));

    // create attributes
    searchRequest.setAttributes(createAttributes(true, true));

    request.getBatchRequests().add(searchRequest);

    logger.debug("start webservice call");
    BatchResponse response = WebserviceHelper.providerInformationQueryRequest(request);
    if (response != null)
    {
      logger.debug("Result-Request-ID: " + response.getRequestID());
      for (javax.xml.bind.JAXBElement o : response.getBatchResponses())
      {
        logger.debug("Result object type: " + o.getValue().getClass().getCanonicalName());

        if (o.getValue() instanceof SearchResponse)
        {
          SearchResponse searchResponse = (SearchResponse) o.getValue();
          for (SearchResultEntry entry : searchResponse.getSearchResultEntry())
          {
            Object obj = createHealthOrganizationFromSearchResult(entry);
            if (obj != null)
              list.add(obj);

            obj = createHealthProfessionalFromSearchResult(entry);
            if (obj != null)
              list.add(obj);
          }
        }
      }
    }

    return list;
  }

  private JAXBElement<?> createFilterEntry(String filterType, String key, String value)
  {
    AttributeValueAssertion attr = new AttributeValueAssertion();
    attr.setName(key);
    attr.setValue(value);
    
    //return new JAXBElement<AttributeValueAssertion>(new QName(filterType), AttributeValueAssertion.class, attr);
    return new JAXBElement<AttributeValueAssertion>(new QName("urn:oasis:names:tc:DSML:2:0:core", filterType, "urn"), AttributeValueAssertion.class, attr);
    
    
  }
  
  private JAXBElement<?> createSubstringEntry(String key, String value)
  {
    SubstringFilter filter = new SubstringFilter();
    filter.setName(key);
    filter.getAny().add(value);
    
    
    //return new JAXBElement<AttributeValueAssertion>(new QName(filterType), AttributeValueAssertion.class, attr);
    return new JAXBElement<SubstringFilter>(new QName("urn:oasis:names:tc:DSML:2:0:core", "substrings", "urn"), SubstringFilter.class, filter);
  }

  private Filter createFilter(boolean hpi, boolean hoi)
  {
    Filter filter = new Filter();
    //filter.setPresent(createAttribute("objectClass"));

    filter.setAnd(new FilterSet());

    if (hoi && hpi == false)
    {
      filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "objectClass", "organization"));
    }
    else if(hoi && hpi == false)
    {
      filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "objectClass", "organization"));
    }
    else if(hpi && hoi == false)
    {
      filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "objectClass", "organizationalPerson"));
    }

    if (StringHelper.isNullOrEmpty(name) == false)
    {
//      if (hpi)
//        filter.getAnd().getFilterGroup().add(createFilterEntry("approxMatch", "sn", name));
//      else if (hoi)
//        filter.getAnd().getFilterGroup().add(createFilterEntry("approxMatch", "HcRegisteredName", name));
      if (hpi)
      {
        //filter.getAnd().getFilterGroup().add(createSubstringEntry("sn", name));
        JAXBElement<FilterSet> filterOr = new JAXBElement<FilterSet>(new QName("urn:oasis:names:tc:DSML:2:0:core", "or", "urn"), FilterSet.class, new FilterSet());
        
        filterOr.setValue(new FilterSet());
        filterOr.getValue().getFilterGroup().add(createSubstringEntry("sn", name));
        filterOr.getValue().getFilterGroup().add(createSubstringEntry("displayName", name));
        
        filter.getAnd().getFilterGroup().add(filterOr);
      }
      else if (hoi)
      {
        JAXBElement<FilterSet> filterOr = new JAXBElement<FilterSet>(new QName("urn:oasis:names:tc:DSML:2:0:core", "or", "urn"), FilterSet.class, new FilterSet());
        
        filterOr.setValue(new FilterSet());
        filterOr.getValue().getFilterGroup().add(createSubstringEntry("HcRegisteredName", name));
        filterOr.getValue().getFilterGroup().add(createSubstringEntry("o", name));
        
        filter.getAnd().getFilterGroup().add(filterOr);
      }
      
       
    }
    if (StringHelper.isNullOrEmpty(gln) == false)
    {
      filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "uid", gln));
    }

//    if (StringHelper.isNullOrEmpty(status) == false)
//    {
//      filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderStatus", status));
//    }
    // status
    if (active || inactive || retired || deceased)
    {
      int count = 0;
      if(active)
        count++;
      if(inactive)
        count++;
      if(retired)
        count++;
      if(deceased)
        count++;
      
      
      //if (active && inactive)
      if(count > 0)
      {
        JAXBElement<FilterSet> filterOr = new JAXBElement<FilterSet>(new QName("urn:oasis:names:tc:DSML:2:0:core", "or", "urn"), FilterSet.class, new FilterSet());
        //JAXBElement<FilterSet> filterOr = new JAXBElement<FilterSet>(new QName("or"), FilterSet.class, new FilterSet());
        filterOr.setValue(new FilterSet());
        if (active)
          filterOr.getValue().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderStatus", "active"));
        if (inactive)
          filterOr.getValue().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderStatus", "inactive"));
        if (retired)
          filterOr.getValue().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderStatus", "retired"));
        if (deceased)
          filterOr.getValue().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderStatus", "deceased"));
        filter.getAnd().getFilterGroup().add(filterOr);
      }
      else if(active)
      {
        filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderStatus", "active"));
      }
      else if(inactive)
      {
        filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderStatus", "inactive"));
      }
      else if(retired)
      {
        filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderStatus", "retired"));
      }
      else if(deceased)
      {
        filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderStatus", "deceased"));
      }
    }

    if (StringHelper.isNullOrEmpty(memberOf) == false)
    {
      filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "memberOf", memberOf));
    }
  
    // address
    if (StringHelper.isNullOrEmpty(address) == false)
    {
      filter.getAnd().getFilterGroup().add(createSubstringEntry("hpdProviderPracticeAddress", address));
    }
    if (StringHelper.isNullOrEmpty(zip) == false)
    {
      filter.getAnd().getFilterGroup().add(createSubstringEntry("hpdProviderPracticeAddress", zip));
    }
    if (StringHelper.isNullOrEmpty(city) == false)
    {
      filter.getAnd().getFilterGroup().add(createSubstringEntry("hpdProviderPracticeAddress", city));
    }

    // gender
    if (male || female)
    {
      JAXBElement<FilterSet> filterOr = new JAXBElement<FilterSet>(new QName("urn:oasis:names:tc:DSML:2:0:core", "or", "urn"), FilterSet.class, new FilterSet());
      //JAXBElement<FilterSet> filterOr = new JAXBElement<FilterSet>(new QName("or"), FilterSet.class, new FilterSet());
      filterOr.setValue(new FilterSet());
      if (male)
        filterOr.getValue().getFilterGroup().add(createFilterEntry("equalityMatch", "gender", "M"));
      if (female)
        filterOr.getValue().getFilterGroup().add(createFilterEntry("equalityMatch", "gender", "F"));
      filter.getAnd().getFilterGroup().add(filterOr);
    }

    // language
    if (StringHelper.isNullOrEmpty(languageCd) == false)
    {
      filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "hpdProviderLanguageSupported", languageCd));
    }

    if (StringHelper.isNullOrEmpty(fachrichtung) == false && fachrichtung.equals(Labels.getLabel("all")) == false)
    {
      filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "HcSpecialisation", fachrichtung));
    }

    if (StringHelper.isNullOrEmpty(typ) == false && typ.equals(Labels.getLabel("all")) == false)
    {
      if (hpi)
        filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "hcProfession", typ));
      else if (hoi)
        filter.getAnd().getFilterGroup().add(createFilterEntry("equalityMatch", "businessCategory", typ));
    }

    return filter;
  }

  //filter.setApproxMatch(new AttributeValueAssertion());
  //filter.getApproxMatch().setName("sn");
  //filter.getApproxMatch().setValue(name);
  public List<Object> performHPISearch() throws Exception
  {
    List<Object> list = new LinkedList<Object>();

    if (checkFilter() == false)
      return list;

    oasis.names.tc.dsml._2._0.core.BatchRequest request = new BatchRequest();

    //request.setAuthRequest(new AuthRequest());
    //request.getAuthRequest().
    SearchRequest searchRequest = new SearchRequest();
    searchRequest.setRequestID("" + currentRequestId++);
    searchRequest.setDn("dc=HPD,o=ehealth-suisse,c=ch");
    searchRequest.setScope("wholeSubtree");
    searchRequest.setDerefAliases("neverDerefAliases");
    searchRequest.setSizeLimit(100l);

    // create filter
    //Control control = new Control();
    searchRequest.setFilter(createFilter(true, false));

    // create attributes
    searchRequest.setAttributes(createAttributes(false, true));

    request.getBatchRequests().add(searchRequest);

    logger.debug("start webservice call");

    BatchResponse batchResponse = WebserviceHelper.providerInformationQueryRequest(request);
    if (batchResponse != null)
    {
      logger.debug("Result-Request-ID: " + batchResponse.getRequestID());
      logger.debug("Count sub elements: " + batchResponse.getBatchResponses().size());

      for (javax.xml.bind.JAXBElement o : batchResponse.getBatchResponses())
      {
        logger.debug("Result object type: " + o.getValue().getClass().getCanonicalName());

        if (o.getValue() instanceof SearchResponse)
        {

          SearchResponse searchResponse = (SearchResponse) o.getValue();
          for (SearchResultEntry entry : searchResponse.getSearchResultEntry())
          {
            Object obj = createHealthProfessionalFromSearchResult(entry);
            if (obj != null)
              list.add(obj);
          }
        }
      }
    }

    return list;
  }

  private HealthProfessional createHealthProfessionalFromSearchResult(SearchResultEntry entry)
  {
    HealthProfessional hp = new HealthProfessional();

    try
    {
      for (DsmlAttr attr : entry.getAttr())
      {
        if (attr.getName().equalsIgnoreCase("objectClass"))
        {
          if (isHealthProfessional(attr.getValue()) == false)
            return null;
        }

        if (attr.getName().equalsIgnoreCase("displayName"))
        {
          hp.setName(getFirstValue(attr.getValue()));
        }
        if (attr.getName().equalsIgnoreCase("givenName"))
        {
          //hp.set
        }
        else if (attr.getName().equalsIgnoreCase("sn"))
        {
          //hp.set
        }
        else if (attr.getName().equalsIgnoreCase("cn"))
        {
          if (StringHelper.isNullOrEmpty(hp.getName()))
            hp.setName(getFirstValue(attr.getValue()));
        }
        else if (attr.getName().equalsIgnoreCase("HcIdentifier"))
        {
          // 2.16.840.1.113883.4.6:GLN:000000000012:Active
          parseHcIdentifier(attr.getValue(), hp, null);
        }
        else if (attr.getName().equalsIgnoreCase("hpdProviderStatus"))
        {
          hp.setStatus(getFirstValue(attr.getValue()));
        }
        else if (attr.getName().equalsIgnoreCase("HcSpecialisation"))
        {
          // Fachärztin/Facharzt für Allgemeine Innere Medizin
          logger.debug("HcSpecialisation:");
          logger.debug(getFirstValue(attr.getValue()));
          //hp.setSpeciality(getFirstValue(attr.getValue()));

          for(String s : attr.getValue())
          {
            hp.getSpeciality().add(TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidHealthProfessionalSpeciality())
              .getConceptFromString(s));
          }
          
//          hp.setSpeciality(TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidHealthProfessionalSpeciality())
//              .getConceptFromString(getFirstValue(attr.getValue())));
        }
        else if (attr.getName().equalsIgnoreCase("HcPrincipalPracticeLocation"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("HcRegisteredAddr"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("HcServiceLocations"))
        {
          //parseAddress(attr.getValue(), hp);
        }
        else if (attr.getName().equalsIgnoreCase("hpdProviderPracticeAddress"))
        {
          // status=Primary $ addr=10 Kreuzweg Biel 2502
          //parseAddress(attr.getValue(), hp, null);
          for(String s : attr.getValue())
          {
            hp.getAddress().add(new Address(s));
          }
        }
        else if (attr.getName().equalsIgnoreCase("HcRegisteredName"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("HcRole"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("HcSpecialisation"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("HcOperatingHours"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("memberOf"))
        {
          // cn=000000000008,ou=Reliationship,dc=HPD,o=ehealth-suisse,c=ch
          hp.getMemberOf().addAll(attr.getValue());
        }
        else if (attr.getName().equalsIgnoreCase("hpdProviderLanguageSupported"))
        {
          parseLanguages(attr.getValue(), hp, null);
        }
        else if (attr.getName().equalsIgnoreCase("Gender"))
        {
          hp.setGender(getFirstValue(attr.getValue()));
        }
        else if (attr.getName().equalsIgnoreCase("description"))
        {
          hp.setDescription(getFirstValue(attr.getValue()));
        }
        else if (attr.getName().equalsIgnoreCase("hcProfession"))
        {
          //hp.setType(TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidHealthProfessionalType())
          //    .getConceptFromString(getFirstValue(attr.getValue())));
          
          for(String s : attr.getValue())
          {
            //hp.getType().add(TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidOrganizationType())
            hp.getType().add(TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidHealthProfessionalType())
              .getConceptFromString(s));
          }

          //hp.setType(getFirstValueWithoutB64(attr.getValue()));
          logger.debug("hcProfession: " + hp.getType());
        }

      }
    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, this);
    }

    return hp;
  }

  private boolean isHealthProfessional(List<String> values)
  {
    for (String s : values)
    {
      if (s.equalsIgnoreCase("organizationalPerson"))
        return true;
    }

    return false;
  }

  private boolean isHealthOrganization(List<String> values)
  {
    for (String s : values)
    {
      if (s.equalsIgnoreCase("organization"))
        return true;
    }

    return false;
  }

  private HealthOrganization createHealthOrganizationFromSearchResult(SearchResultEntry entry)
  {
    HealthOrganization hoi = new HealthOrganization();

    try
    {
      for (DsmlAttr attr : entry.getAttr())
      {

        /*if (attr.getName().equalsIgnoreCase("displayName"))
         {
         hoi.setName(getFirstValue(attr.getValue()));
         }*/
        /*if (attr.getName().equalsIgnoreCase("givenName"))
         {
         //hp.set
         }*/
        if (attr.getName().equalsIgnoreCase("objectClass"))
        {
          if (isHealthOrganization(attr.getValue()) == false)
            return null;
        }

        if (attr.getName().equalsIgnoreCase("sn"))
        {
          //hp.set
        }
        else if (attr.getName().equalsIgnoreCase("cn"))
        {
          if (StringHelper.isNullOrEmpty(hoi.getName()))
            hoi.setName(getFirstValue(attr.getValue()));
        }
        else if (attr.getName().equalsIgnoreCase("HcIdentifier"))
        {
          // 2.16.840.1.113883.4.6:GLN:000000000012:Active
          parseHcIdentifier(attr.getValue(), null, hoi);
        }
        else if (attr.getName().equalsIgnoreCase("HcSpecialisation"))
        {
          // Fachärztin/Facharzt für Allgemeine Innere Medizin
          logger.debug("HcSpecialisation: " + getFirstValue(attr.getValue()));

          for(String s : attr.getValue())
          {
            hoi.getSpeciality().add(TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidOrganizationSpeciality())
              .getConceptFromString(s));
          }
          //hoi.setSpeciality(TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidOrganizationSpeciality())
          //    .getConceptFromString(getFirstValue(attr.getValue())));

          //hoi.setSpeciality(TermserverCatalog.getInstance().getAuthorSpecialty().getConceptFromString(getFirstValue(attr.getValue())));  // TODO
        }
        else if (attr.getName().equalsIgnoreCase("HcPrincipalPracticeLocation"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("HcRegisteredAddr"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("HcServiceLocations"))
        {
          //parseAddress(attr.getValue(), hp);
        }
        else if (attr.getName().equalsIgnoreCase("hpdProviderPracticeAddress"))
        {
          // status=Primary $ addr=10 Kreuzweg Biel 2502
          for(String s : attr.getValue())
          {
            Address addr = new Address(s);
            hoi.getAddress().add(addr);
            
            logger.debug("address added: " + addr.toString());
          }
          //parseAddress(attr.getValue(), null, hoi);
        }
        else if (attr.getName().equalsIgnoreCase("HcRegisteredName"))
        {
          hoi.setName(getFirstValue(attr.getValue()));
        }
        else if (attr.getName().equalsIgnoreCase("HcRole"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("HcSpecialisation"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("HcOperatingHours"))
        {

        }
        else if (attr.getName().equalsIgnoreCase("memberOf"))
        {
          // cn=000000000008,ou=Reliationship,dc=HPD,o=ehealth-suisse,c=ch
          hoi.getMemberOf().addAll(attr.getValue());
        }
        /*else if (attr.getName().equalsIgnoreCase("hpdProviderLanguageSupported"))
         {
         parseLanguages(attr.getValue(), null, hoi);
         }*/
        /*else if (attr.getName().equalsIgnoreCase("Gender"))
         {
         hoi.setGender(getFirstValue(attr.getValue()));
         }*/
        else if (attr.getName().equalsIgnoreCase("description"))
        {
          hoi.setDescription(getFirstValue(attr.getValue()));
        }
        else if (attr.getName().equalsIgnoreCase("businessCategory"))
        {
          // hoi.setType(TermserverCatalog.getInstance().getHealthcareFacilityTypeCode().getConceptFromString(getFirstValue(attr.getValue())));

          //hoi.setType(TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidOrganizationType())
          //    .getConceptFromString(getFirstValue(attr.getValue())));
          
          for(String s : attr.getValue())
          {
            hoi.getType().add(TermserverCatalog.getInstance().getCatalogFromOID(PropertiesHelper.getInstance().getOidOrganizationType())
              .getConceptFromString(s));
          }
        }
        else if (attr.getName().equalsIgnoreCase("hpdProviderLanguageSupported"))
        {
          parseLanguages(attr.getValue(), null, hoi);
        }
        else if (attr.getName().equalsIgnoreCase("hpdProviderStatus"))
        {
          hoi.setStatus(getFirstValue(attr.getValue()));
        }

      }
    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, this);
    }

    return hoi;
  }

  private String decodeBase64(String s) throws UnsupportedEncodingException
  {
    /*if (s == null || s.length() == 0)
     return "";

     //    if(Base64.isBase64(s))
     //     {
     //     return new String(Base64.decodeBase64(s));
     //     }
     //     else return s;
     byte[] bytes = Base64.decode(s);
     if (bytes == null || bytes.length == 0)
     return s;

     String str = new String(bytes, "UTF-8");
     if (str.length() == 0)
     return s;
     return str;*/
    return s;
  }

  private String getFirstValue(List<String> list) throws UnsupportedEncodingException
  {
    if (list != null && list.size() > 0)
      return decodeBase64(list.get(0));
    return "";
  }

  private String getFirstValueWithoutB64(List<String> list) throws UnsupportedEncodingException
  {
    if (list != null && list.size() > 0)
      return list.get(0);
    return "";
  }


  private void parseLanguages(List<String> list, HealthProfessional hp, HealthOrganization hoi)
  {
    /*      <value>de</value>
     <value>fr</value>
     <value>it</value>
     <value>en</value>*/

    for (String s : list)
    {
      if (s == null)
        continue;

      Language lang = Languages.getInstance().getLanguageByCode2(s);
      if (hp != null)
        hp.getLanguages().add(lang);
      if (hoi != null)
        hoi.getLanguages().add(lang);
      //if(hoi != null)
      //  hoi.getLanguages().add(lang);
    }
  }

  private void parseHcIdentifier(List<String> list, HealthProfessional hp, HealthOrganization hoi)
  {
    // e.g. hcIdentifier="id_type:id_number" or hcIdentifer="source:id_type:id_number:In/Active")

    for (String s : list)
    {
      if (s == null)
        continue;

      String[] split = s.split(":");
      if (split != null && split.length == 2)
      {
        // "id_type:id_number"
        if (split[0].equalsIgnoreCase("gln"))
        {
          if (hp != null)
            hp.setGln(split[1]);
          if (hoi != null)
            hoi.setGln(split[1]);
        }
      }
      else if (split != null && split.length == 4)
      {
        // "source:id_type:id_number:In/Active"
        if (split[1].equalsIgnoreCase("gln"))
        {
          if (hp != null)
          {
            hp.setGln(split[2]);
            //hp.setStatus(split[3]);
          }
          if (hoi != null)
          {
            hoi.setGln(split[2]);
            //hoi.setStatus(split[3]);
          }
        }
      }
    }

  }

  private AttributeDescription createAttribute(String name)
  {
    AttributeDescription attribute = new AttributeDescription();
    attribute.setName(name);
    return attribute;
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
   * @return the address
   */
  public String getAddress()
  {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address)
  {
    this.address = address;
  }

  /**
   * @return the zip
   */
  public String getZip()
  {
    return zip;
  }

  /**
   * @param zip the zip to set
   */
  public void setZip(String zip)
  {
    this.zip = zip;
  }

  /**
   * @return the city
   */
  public String getCity()
  {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city)
  {
    this.city = city;
  }

  /**
   * @param male the male to set
   */
  public void setMale(boolean male)
  {
    this.male = male;
  }

  /**
   * @param female the female to set
   */
  public void setFemale(boolean female)
  {
    this.female = female;
  }

  /**
   * @param languageCd the languageCd to set
   */
  public void setLanguageCd(String languageCd)
  {
    this.languageCd = languageCd;
  }

  /**
   * @return the memberOf
   */
  public String getMemberOf()
  {
    return memberOf;
  }

  /**
   * @param memberOf the memberOf to set
   */
  public void setMemberOf(String memberOf)
  {
    this.memberOf = memberOf;
  }

  /**
   * @param fachrichtung the fachrichtung to set
   */
  public void setFachrichtung(String fachrichtung)
  {
    this.fachrichtung = fachrichtung;
  }

  /**
   * @param active the active to set
   */
  public void setActive(boolean active)
  {
    this.active = active;
  }

  /**
   * @param inactive the inactive to set
   */
  public void setInactive(boolean inactive)
  {
    this.inactive = inactive;
  }

  /**
   * @param typ the typ to set
   */
  public void setTyp(String typ)
  {
    this.typ = typ;
  }

  /**
   * @return the retired
   */
  public boolean isRetired()
  {
    return retired;
  }

  /**
   * @param retired the retired to set
   */
  public void setRetired(boolean retired)
  {
    this.retired = retired;
  }

  /**
   * @return the deceased
   */
  public boolean isDeceased()
  {
    return deceased;
  }

  /**
   * @param deceased the deceased to set
   */
  public void setDeceased(boolean deceased)
  {
    this.deceased = deceased;
  }

}
