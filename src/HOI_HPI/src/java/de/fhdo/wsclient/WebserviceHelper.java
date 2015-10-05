/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.wsclient;

import de.fhdo.helper.PropertiesHelper;
import de.fhdo.logging.LoggingOutput;
import de.fhdo.terminologie.ws.authorization.AuthenticateResponse;
import de.fhdo.terminologie.ws.search.ListCodeSystemConceptsRequestType;
import de.fhdo.terminologie.ws.search.ListCodeSystemConceptsResponse;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import javax.xml.ws.BindingProvider;
import oasis.names.tc.dsml._2._0.core.BatchResponse;
import types.termserver.fhdo.de.CodeSystem;
import types.termserver.fhdo.de.CodeSystemVersion;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class WebserviceHelper
{
  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  
  public static BatchResponse providerInformationQueryRequest(oasis.names.tc.dsml._2._0.core.BatchRequest body)
  {
    //String s = "http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService.wsdl";
    logger.debug("Webservice call: providerInformationQueryRequest");

    URL url = null;
    try
    {
      String s = PropertiesHelper.getInstance().getHpdUrl();
      if (s.endsWith(".wsdl") == false)
        s = s + ".wsdl";

      url = new URL(s);
      
      logger.debug("url: " + s);
    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, WebserviceHelper.class);
    }

    // invoke Webservice method
    ihe.iti._2010.ProviderInformationDirectoryService service = new ihe.iti._2010.ProviderInformationDirectoryService(url);
    HPDHandlerResolver handlerResolver = new HPDHandlerResolver();

    service.setHandlerResolver(handlerResolver);
    ihe.iti._2010.ProviderInformationDirectoryPortType port = service.getProviderInformationDirectoryPortSoap();
    
    logger.debug("adding security, username: " + PropertiesHelper.getInstance().getHpdUsername());
    ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, PropertiesHelper.getInstance().getHpdUsername()); 
    ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PropertiesHelper.getInstance().getHpdPassword()); 


    //((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService");
    ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, PropertiesHelper.getInstance().getHpdUrl());

    return port.providerInformationQueryRequest(body);
  }

  
  public static AuthenticateResponse.Return authenticate(String token)
  {
    List<String> parameter = new LinkedList<String>();
    parameter.add(token);
    
    return authenticate(parameter);
  }
  
  public static AuthenticateResponse.Return authenticate(List<String> parameter)
  {
    URL url = null;
    try
    {
      url = new URL(PropertiesHelper.getInstance().getTermserverUrl() + "Authorization?wsdl");
      logger.debug("url: " + url);
    }
    catch(Exception ex)
    {
      LoggingOutput.outputException(ex, WebserviceHelper.class);
    }
    
    de.fhdo.terminologie.ws.authorization.Authorization_Service service = 
            new de.fhdo.terminologie.ws.authorization.Authorization_Service(url);
    de.fhdo.terminologie.ws.authorization.Authorization port = service.getAuthorizationPort();
    return port.authenticate(parameter);
  }
  
  
  public static ListCodeSystemConceptsResponse.Return listCodeSystemConcepts(String oid)
  {
    ListCodeSystemConceptsRequestType parameter = new ListCodeSystemConceptsRequestType();
    parameter.setCodeSystem(new CodeSystem());
    
    CodeSystemVersion csv = new CodeSystemVersion();
    csv.setOid(oid);
    
    parameter.getCodeSystem().getCodeSystemVersions().add(csv);
    
    return listCodeSystemConcepts(parameter);
  }
  
  public static ListCodeSystemConceptsResponse.Return listCodeSystemConcepts(de.fhdo.terminologie.ws.search.ListCodeSystemConceptsRequestType parameter)
  {
    URL url = null;
    try
    {
      url = new URL(PropertiesHelper.getInstance().getTermserverUrl() + "Search?wsdl");
      logger.debug("Termserver URL: " + url);
    }
    catch(Exception ex)
    {
      LoggingOutput.outputException(ex, WebserviceHelper.class);
    }
    
    de.fhdo.terminologie.ws.search.Search_Service service = 
            new de.fhdo.terminologie.ws.search.Search_Service(url);
    de.fhdo.terminologie.ws.search.Search port = service.getSearchPort();
    return port.listCodeSystemConcepts(parameter);
  }
  
  
  

}
