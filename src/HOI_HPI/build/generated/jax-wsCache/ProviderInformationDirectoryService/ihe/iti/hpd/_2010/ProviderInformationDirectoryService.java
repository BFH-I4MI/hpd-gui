
package ihe.iti.hpd._2010;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ProviderInformationDirectory_Service", targetNamespace = "urn:ihe:iti:hpd:2010", wsdlLocation = "http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService.wsdl")
public class ProviderInformationDirectoryService
    extends Service
{

    private final static URL PROVIDERINFORMATIONDIRECTORYSERVICE_WSDL_LOCATION;
    private final static WebServiceException PROVIDERINFORMATIONDIRECTORYSERVICE_EXCEPTION;
    private final static QName PROVIDERINFORMATIONDIRECTORYSERVICE_QNAME = new QName("urn:ihe:iti:hpd:2010", "ProviderInformationDirectory_Service");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://147.87.117.77:8080/hpd-ws/ProviderInformationDirectoryService.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PROVIDERINFORMATIONDIRECTORYSERVICE_WSDL_LOCATION = url;
        PROVIDERINFORMATIONDIRECTORYSERVICE_EXCEPTION = e;
    }

    public ProviderInformationDirectoryService() {
        super(__getWsdlLocation(), PROVIDERINFORMATIONDIRECTORYSERVICE_QNAME);
    }

    public ProviderInformationDirectoryService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PROVIDERINFORMATIONDIRECTORYSERVICE_QNAME, features);
    }

    public ProviderInformationDirectoryService(URL wsdlLocation) {
        super(wsdlLocation, PROVIDERINFORMATIONDIRECTORYSERVICE_QNAME);
    }

    public ProviderInformationDirectoryService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PROVIDERINFORMATIONDIRECTORYSERVICE_QNAME, features);
    }

    public ProviderInformationDirectoryService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ProviderInformationDirectoryService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ProviderInformationDirectoryPortType
     */
    @WebEndpoint(name = "ProviderInformationDirectory_Port_Soap")
    public ProviderInformationDirectoryPortType getProviderInformationDirectoryPortSoap() {
        return super.getPort(new QName("urn:ihe:iti:hpd:2010", "ProviderInformationDirectory_Port_Soap"), ProviderInformationDirectoryPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ProviderInformationDirectoryPortType
     */
    @WebEndpoint(name = "ProviderInformationDirectory_Port_Soap")
    public ProviderInformationDirectoryPortType getProviderInformationDirectoryPortSoap(WebServiceFeature... features) {
        return super.getPort(new QName("urn:ihe:iti:hpd:2010", "ProviderInformationDirectory_Port_Soap"), ProviderInformationDirectoryPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PROVIDERINFORMATIONDIRECTORYSERVICE_EXCEPTION!= null) {
            throw PROVIDERINFORMATIONDIRECTORYSERVICE_EXCEPTION;
        }
        return PROVIDERINFORMATIONDIRECTORYSERVICE_WSDL_LOCATION;
    }

}
