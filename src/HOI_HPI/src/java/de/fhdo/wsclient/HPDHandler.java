package de.fhdo.wsclient;

import de.fhdo.helper.PropertiesHelper;
import de.fhdo.logging.LoggingOutput;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.w3c.dom.Node;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class HPDHandler implements SOAPHandler<SOAPMessageContext>
{

  private static org.apache.log4j.Logger logger = de.fhdo.logging.Logger4j.getInstance().getLogger();

  public Set<QName> getHeaders()
  {
//    logger.debug("HANDLER - getHeaders()");
//    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    return Collections.EMPTY_SET;

    Set<QName> set = new HashSet<QName>();
    // Make sure the '[{http://www.w3.org/2005/08/addressing}]Action' header 
    // is handled in case the device set the 'MustUnderstand' attribute to '1'
    set.add(new QName("http://www.w3.org/2005/08/addressing", "To"));
    return set;

  }

  public boolean handleMessage(SOAPMessageContext messageContext)
  {
    logger.debug("HANDLER - handleMessage()");

    boolean outgoing = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    logger.debug("outgoing: " + outgoing);
    //messageContext.getMessage().
    //messageContext.
    try
    {
      SOAPMessage msg = messageContext.getMessage();
      SOAPPart sp = msg.getSOAPPart();
      SOAPEnvelope env = sp.getEnvelope();
      
      if (outgoing)
      {
        
        
        //SOAPHeader soapHeader = env.addHeader();
        /*SOAPHeader soapHeader = env.getHeader();
        if (soapHeader == null)
        {
          soapHeader = env.addHeader();
        }*/
        
        SOAPHeader soapHeader = msg.getSOAPHeader();
        if (soapHeader == null)
        {
          logger.debug("add soap header");
          soapHeader = env.addHeader();
        }
        
        logger.debug("soapHeader: " + soapHeader);
      //soapHeader = env.addHeader();

        //soapHeader.addAttribute(new QName("mustUnderstand"), "false");
        soapHeader.addNamespaceDeclaration("wsa", "http://www.w3.org/2005/08/addressing");
      //logger.debug("SOAP Header: " + soapHeader.getTagName());
        
        // Add WS security
        SOAPElement securityElem = soapHeader.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        securityElem.addNamespaceDeclaration("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        SOAPElement usernameTokenElem = soapHeader.addChildElement("UsernameToken", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        usernameTokenElem.addAttribute(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id", "wsu"), "UsernameToken-A85D182E16C2B28AF914310065840142");
        
        SOAPElement usernameElem = usernameTokenElem.addChildElement("Username", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        usernameElem.setTextContent(PropertiesHelper.getInstance().getHpdUsername());
        usernameTokenElem.addChildElement(usernameElem);
        
        SOAPElement passwordElem = usernameTokenElem.addChildElement("Password", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        passwordElem.addAttribute(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Type", "wsse"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
        passwordElem.setTextContent(PropertiesHelper.getInstance().getHpdPassword());
        usernameTokenElem.addChildElement(passwordElem);
        
        SOAPElement createdElem = usernameTokenElem.addChildElement("Created", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        createdElem.setTextContent(sdf.format(new Date()));
        usernameTokenElem.addChildElement(createdElem);
        
        securityElem.addChildElement(usernameTokenElem);
        soapHeader.addChildElement(securityElem);
        
        //messageContext.put("com.sun.xml.wss.XWSSConstants.USERNAME_PROPERTY", PropertiesHelper.getInstance().getHpdUsername());
        //messageContext.put("com.sun.xml.wss.XWSSConstants.PASSWORD_PROPERTY", PropertiesHelper.getInstance().getHpdPassword());
        //soapHeader.
        
        //((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, PropertiesHelper.getInstance().getHpdUsername()); 
        //((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PropertiesHelper.getInstance().getHpdPassword()); 

        //SOAPElement headerElem = soapHeader.addChildElement("Action", "wsa", "http://www.w3.org/2005/08/addressing");
        SOAPElement headerElem = soapHeader.addChildElement("Action", "wsa");
        headerElem.setTextContent("urn:ihe:iti:2010:ProviderInformationQuery");
        //headerElem.addAttribute(new QName(env.getNamespaceURI(), "mustUnderstand"), "0");
        //headerElem.
        soapHeader.addChildElement(headerElem);

        headerElem = soapHeader.addChildElement("To", "wsa");
        //headerElem.setTextContent("http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService");
        headerElem.setTextContent(PropertiesHelper.getInstance().getHpdUrl());
        soapHeader.addChildElement(headerElem);

        headerElem = soapHeader.addChildElement("ReplyTo", "wsa");
        SOAPElement se = headerElem.addChildElement("Address", "wsa");
        se.setTextContent("http://www.w3.org/2005/08/addressing/anonymous");
        headerElem.addChildElement(se);
        soapHeader.addChildElement(headerElem);
        
        logger.debug("'ReplyTo' added");

        headerElem = soapHeader.addChildElement("MessageID", "wsa");
        headerElem.setTextContent("uuid:" + UUID.randomUUID().toString());
        soapHeader.addChildElement(headerElem);
        
        logger.debug("'MessageID' added");

        // add namespaces
        env.removeNamespaceDeclaration("S");
        env.addNamespaceDeclaration("soap", "http://www.w3.org/2003/05/soap-envelope");
        env.addNamespaceDeclaration("urn", "urn:oasis:names:tc:DSML:2:0:core");

        msg.saveChanges();
        
        logger.debug(soapHeader.toString());
        
        //dumpSOAPMessage(soapHeader);
        
      }
      else
      {
        // incoming message
        SOAPBody body = env.getBody();

        // TODO gefährlich hier?
        for (int i = 0; i < body.getChildNodes().getLength(); ++i)
        {
          Node node = body.getChildNodes().item(i);
          decodeAllBase64Stuff(node);
        }

        //logger.debug("TODO parse base64");
      }

      dumpSOAPMessage(msg);
      

    }
    catch (Exception ex)
    {
      LoggingOutput.outputException(ex, this);
    }

    return true;
  }

  private void decodeAllBase64Stuff(Node node) throws UnsupportedEncodingException
  {
    //logger.debug("check node with name: " + node.getNodeName());

    if (node.hasAttributes())
    {
      if (node.getNodeName().equalsIgnoreCase("value"))
      {
        //logger.debug("found value: " + node.getNodeName() + ", " + node.getTextContent());
        //logger.debug("Attrib-Length: " + node.getAttributes().getLength());

        /*for (int i = 0; i < node.getAttributes().getLength(); ++i)
        {
          Node n = node.getAttributes().item(i);
          logger.debug("attr: " + n.getNodeName());
        }*/

        Node nodeType = node.getAttributes().getNamedItem("xsi:type");
        if (nodeType != null)
        {
          logger.debug("decode base 64: " + node.getTextContent());
          node.setTextContent(new String(Base64.decode(node.getTextContent()), "UTF-8"));
        }
      }
    }

    for (int i = 0; i < node.getChildNodes().getLength(); ++i)
    {
      Node child = node.getChildNodes().item(i);
      decodeAllBase64Stuff(child);
    }
  }

  /**
   * Dump SOAP Message to console
   *
   * @param msg
   */
  private void dumpSOAPMessage(SOAPMessage msg)
  {
    if (msg == null)
    {
      System.out.println("SOAP Message is null");
      return;
    }
    System.out.println("");
    System.out.println("--------------------");
    System.out.println("DUMP OF SOAP MESSAGE");
    System.out.println("--------------------");
    try
    {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      msg.writeTo(baos);
      System.out.println(baos.toString(getMessageEncoding(msg)));

      System.out.println("--------------------");
      System.out.println("--------------------");

      // show included values
      //String values = msg.getSOAPBody().getTextContent();
      //System.out.println("Included values:" + values);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  

  /**
   * Returns the message encoding (e.g. utf-8)
   *
   * @param msg
   * @return
   * @throws javax.xml.soap.SOAPException
   */
  private String getMessageEncoding(SOAPMessage msg) throws SOAPException
  {
    String encoding = "utf-8";
    if (msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING) != null)
    {
      encoding = msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING).toString();
    }
    return encoding;
  }

  public boolean handleFault(SOAPMessageContext context)
  {
    logger.debug("HANDLER - handleFault()");
    return true;
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  public void close(MessageContext context)
  {
    logger.debug("HANDLER - close()");
    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
