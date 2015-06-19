package termserverclientdemo;

import de.fhdo.terminologie.ws.search.ListCodeSystemConceptsRequestType;
import de.fhdo.terminologie.ws.search.ListCodeSystemConceptsResponse;
import de.fhdo.terminologie.ws.search.Search;
import de.fhdo.terminologie.ws.search.Search_Service;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import types.termserver.fhdo.de.CodeSystemConcept;
import types.termserver.fhdo.de.CodeSystemEntity;
import types.termserver.fhdo.de.CodeSystemEntityVersion;
import types.termserver.fhdo.de.CodeSystemVersion;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class SynchronizeConcepts
{
  private java.util.Date lastSynch = new Date();
  
  public void synchronizeConcepts()
  {
    boolean success = false;
    
    try
    {
      GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
      gc.set(GregorianCalendar.YEAR, 2014);
      gc.set(GregorianCalendar.MONTH, 10);
      gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
      lastSynch = gc.getTime();
      
      // create webservice reference and port
      Search_Service service = new Search_Service(
              new URL("http://193.25.22.69:8080/TermServer/Search?wsdl"),
              new QName("http://search.ws.terminologie.fhdo.de/", "Search"));
      Search port = service.getSearchPort();

      // define parameter
      ListCodeSystemConceptsRequestType request = new ListCodeSystemConceptsRequestType();
      CodeSystemVersion csvRequest = new CodeSystemVersion();
      csvRequest.setVersionId(1l);  // get code system with version id '1'
      
      request.setCodeSystem(new types.termserver.fhdo.de.CodeSystem());
      request.getCodeSystem().getCodeSystemVersions().add(csvRequest);

      CodeSystemEntityVersion csev = new CodeSystemEntityVersion();
      csev.setStatusVisibilityDate(dateToXMLGregorianCalendar(lastSynch));  // set last synch date
      request.setCodeSystemEntity(new CodeSystemEntity());
      request.getCodeSystemEntity().getCodeSystemEntityVersions().add(csev);
      System.out.println("Date: " + dateToXMLGregorianCalendar(lastSynch));
      
      // invoke method
      ListCodeSystemConceptsResponse.Return response = port.listCodeSystemConcepts(request);
      
      // handle response
      System.out.println("Response: " + response.getReturnInfos().getMessage());
      
      if(response.getReturnInfos().getStatus() == de.fhdo.terminologie.ws.search.Status.OK)
      {
        System.out.println("Count: " + response.getReturnInfos().getCount());
        
        for(CodeSystemEntity cse : response.getCodeSystemEntity())
        {
          for(CodeSystemEntityVersion csv : cse.getCodeSystemEntityVersions())
          {
            CodeSystemConcept csc = csv.getCodeSystemConcepts().get(0);
            System.out.println("VersionId: " + csv.getVersionId() + " " + csv.getStatusVisibilityDate().toString() + ", " + csc.getCode()+ " - " + csc.getTerm());
            
            // update changed data in your database
            
          }
        }
        
        success = true;
      }
      else
      {
        // handle error message
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    
    if(success)
    {
      lastSynch = new java.util.Date();
      // TODO save current date for synchronization
    }
  }
  
  public static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date)
  {
    GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
    gc.setTime(date);
    DatatypeFactory dataTypeFactory = null;
    try
    {
      dataTypeFactory = DatatypeFactory.newInstance();
    }
    catch (DatatypeConfigurationException ex)
    {
      ex.printStackTrace();
    }
    XMLGregorianCalendar value = dataTypeFactory.newXMLGregorianCalendar(gc);
    return value;
  }
}
