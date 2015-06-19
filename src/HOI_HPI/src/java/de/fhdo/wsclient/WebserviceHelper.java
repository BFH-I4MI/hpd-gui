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
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import oasis.names.tc.dsml._2._0.core.BatchResponse;
import sun.net.www.http.HttpClient;
import types.termserver.fhdo.de.CodeSystem;
import types.termserver.fhdo.de.CodeSystemVersion;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class WebserviceHelper
{
  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();
  
  /*public static BatchResponse providerInformationQueryRequestPure(oasis.names.tc.dsml._2._0.core.BatchRequest body)
          
  {
    //String s = "http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService.wsdl";
    String s = "http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService";

    //String s = "http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService";
    //s = PropertiesHelper.getInstance().getHpdUrl();
    URL url = null;
    try
    {
      //<S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope" xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:urn="urn:oasis:names:tc:DSML:2:0:core"><soap:Header xmlns:wsa="http://www.w3.org/2005/08/addressing"><wsa:Action>urn:ihe:iti:2010:ProviderInformationQuery</wsa:Action><wsa:ReplyTo><wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address></wsa:ReplyTo><wsa:MessageID>uuid:c74d2d9d-b948-4ea1-a985-340637be1fd7</wsa:MessageID><wsa:To>http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService</wsa:To></soap:Header>

      //String request = "<S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:urn=\"urn:oasis:names:tc:DSML:2:0:core\"><S:Body><batchRequest xmlns=\"urn:oasis:names:tc:DSML:2:0:core\" xmlns:ns2=\"urn:ehealth-suisse:names:tc:CS:1\"><searchRequest derefAliases=\"neverDerefAliases\" dn=\"dc=HPD,o=ehealth-suisse,c=ch\" requestID=\"1\" scope=\"wholeSubtree\" sizeLimit=\"100\"><filter><approxMatch name=\"sn\"><value>Wenger</value></approxMatch></filter><attributes><attribute name=\"givenName\"/><attribute name=\"cn\"/><attribute name=\"memberOf\"/></attributes></searchRequest></batchRequest></S:Body></S:Envelope>";
      //String request = "<S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:urn=\"urn:oasis:names:tc:DSML:2:0:core\">\n" +
      String request = "<S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:urn=\"urn:oasis:names:tc:DSML:2:0:core\"><soap:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>urn:ihe:iti:2010:ProviderInformationQuery</wsa:Action><wsa:ReplyTo><wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address></wsa:ReplyTo><wsa:MessageID>uuid:c74d2d9d-b948-4ea1-a985-340637be1fd7</wsa:MessageID><wsa:To>http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService</wsa:To></soap:Header>\n"
              + "<S:Body>\n"
              + "<batchRequest xmlns=\"urn:oasis:names:tc:DSML:2:0:core\" xmlns:ns2=\"urn:ehealth-suisse:names:tc:CS:1\">\n"
              + "<searchRequest derefAliases=\"neverDerefAliases\" dn=\"dc=HPD,o=ehealth-suisse,c=ch\" requestID=\"1\" scope=\"wholeSubtree\" sizeLimit=\"100\">\n"
              + "<filter>\n"
              + "<approxMatch name=\"sn\">\n"
              + "<value>Wenger</value>\n"
              + "</approxMatch>\n"
              + "</filter>\n"
              + "\n"
              + "<attributes>\n"
              + "<attribute name=\"givenName\"/>\n"
              + "<attribute name=\"cn\"/>\n"
              + "<attribute name=\"memberOf\"/>\n"
              + "</attributes>\n"
              + "</searchRequest>\n"
              + "</batchRequest>\n"
              + "</S:Body>\n"
              + "</S:Envelope>";
      //String request = "<S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:urn=\"urn:oasis:names:tc:DSML:2:0:core\"></S:Envelope>";
      url = new URL(s);

      //                                                           <soap:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsa:Action>urn:ihe:iti:2010:ProviderInformationQuery</wsa:Action><wsa:ReplyTo><wsa:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa:Address></wsa:ReplyTo><wsa:MessageID>uuid:c74d2d9d-b948-4ea1-a985-340637be1fd7</wsa:MessageID><wsa:To>http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService</wsa:To></soap:Header>\n"
      
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      //urlConnection.setDoOutput(true);
      urlConnection.setRequestMethod("POST");
      //urlConnection.setUseCaches(false);
      urlConnection.setAllowUserInteraction(false);

      urlConnection.setConnectTimeout(10000);
      urlConnection.setReadTimeout(10000);
      //urlConnection.setRequestProperty("Content-Type", "application/json");

      urlConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");

      urlConnection.setRequestProperty("Content-Type", "application/soap+xml;charset=UTF-8;action=\"urn:ihe:iti:2010:ProviderInformationQuery\"");
      //urlConnection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8;action=\\\"urn:ihe:iti:2010:ProviderInformationQuery\\\"");
      urlConnection.setRequestProperty("Content-Length", "" + request.length());
      System.out.println("Content-Length: " + request.length());
      urlConnection.setRequestProperty("Host", "147.87.117.77:8080");
      urlConnection.setRequestProperty("Connection", "Keep-Alive");
//      //urlConnection.setRequestProperty("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");
      urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
      urlConnection.setRequestProperty("Accept", "* /*");
      //urlConnection.setRequestProperty("Host", "");

      urlConnection.setUseCaches(false);

      urlConnection.setDoInput(true);
      urlConnection.setDoOutput(true);

      urlConnection.connect();

      OutputStream output = urlConnection.getOutputStream();
      output.write(request.getBytes("UTF-8"));
      output.flush();
      output.close();

      urlConnection.disconnect();

      StringBuilder sb = new StringBuilder();

      int HttpResult = urlConnection.getResponseCode();

      System.out.println("http result: " + HttpResult);
      System.out.println("message: " + urlConnection.getResponseMessage());

      if (HttpResult == HttpURLConnection.HTTP_ACCEPTED)
      {
        System.out.println("ACCEPTED, show headers:");
        //foreach 
        for (String key : urlConnection.getHeaderFields().keySet())
        {
          System.out.println(key + ": " + urlConnection.getHeaderField(key));
        }
      }
      else if (HttpResult == HttpURLConnection.HTTP_OK)
      {
        System.out.println("Content-Länge: " + urlConnection.getContentLength());

        BufferedReader br = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream(), "utf-8"));
        String line = null;
        while ((line = br.readLine()) != null)
        {
          sb.append(line + "\n");
        }
        br.close();

        System.out.println("ERGEBNIS:");
        System.out.println("" + sb.toString());
      }
      else
      {
        System.out.println("Fehler:");
        System.out.println("message: " + urlConnection.getResponseMessage());
        //System.out.println(urlConnection.getContent().toString());

      }

    }
    catch (Exception ex)
    {
      System.out.println("ERROR:");
      Logger.getLogger(WebserviceHelper.class.getName()).log(Level.SEVERE, null, ex);

      ex.printStackTrace();
    }

    
    return null;
  }*/

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
    ihe.iti.hpd._2010.ProviderInformationDirectoryService service = new ihe.iti.hpd._2010.ProviderInformationDirectoryService(url);
    HPDHandlerResolver handlerResolver = new HPDHandlerResolver();

    service.setHandlerResolver(handlerResolver);
    ihe.iti.hpd._2010.ProviderInformationDirectoryPortType port = service.getProviderInformationDirectoryPortSoap();
    
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
