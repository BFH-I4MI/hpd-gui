package termserverclientdemo;

import de.fhdo.terminologie.ws.search.ListCodeSystemsRequestType;
import de.fhdo.terminologie.ws.search.ListCodeSystemsResponse;
import de.fhdo.terminologie.ws.search.Search;
import de.fhdo.terminologie.ws.search.Search_Service;
import java.net.URL;
import javax.xml.namespace.QName;
import types.termserver.fhdo.de.CodeSystem;
import types.termserver.fhdo.de.CodeSystemVersion;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class ListCodeSystems
{

  public ListCodeSystems()
  {
  }
  
  public void listCodeSystems()
  {
    try
    {
      // create webservice reference and port
      Search_Service service = new Search_Service(
              new URL("http://193.25.22.69:8080/TermServer/Search?wsdl"),
              new QName("http://search.ws.terminologie.fhdo.de/", "Search"));
      Search port = service.getSearchPort();

      // define parameter
      ListCodeSystemsRequestType request = new ListCodeSystemsRequestType();
      
      // invoke method
      ListCodeSystemsResponse.Return response = port.listCodeSystems(request);
      
      // handle response
      System.out.println("Response: " + response.getReturnInfos().getMessage());
      
      if(response.getReturnInfos().getStatus() == de.fhdo.terminologie.ws.search.Status.OK)
      {
        System.out.println("Count: " + response.getReturnInfos().getCount());
        
        for(CodeSystem cs : response.getCodeSystem())
        {
          for(CodeSystemVersion csv : cs.getCodeSystemVersions())
          {
            System.out.println("VersionId: " + csv.getVersionId() + ", " + cs.getName() + " - " + csv.getName());
          }
        }
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
  }
}
