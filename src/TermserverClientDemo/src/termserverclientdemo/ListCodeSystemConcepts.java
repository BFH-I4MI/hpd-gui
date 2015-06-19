/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termserverclientdemo;

import de.fhdo.terminologie.ws.search.ListCodeSystemConceptsRequestType;
import de.fhdo.terminologie.ws.search.ListCodeSystemConceptsResponse;
import de.fhdo.terminologie.ws.search.Search;
import de.fhdo.terminologie.ws.search.Search_Service;
import java.net.URL;
import javax.xml.namespace.QName;
import types.termserver.fhdo.de.CodeSystemConcept;
import types.termserver.fhdo.de.CodeSystemEntity;
import types.termserver.fhdo.de.CodeSystemEntityVersion;
import types.termserver.fhdo.de.CodeSystemVersion;
import types.termserver.fhdo.de.CodeSystemVersionEntityMembership;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class ListCodeSystemConcepts
{
  public void listCodeSystemConcepts()
  {
    try
    {
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

      CodeSystemVersionEntityMembership csvem = new CodeSystemVersionEntityMembership();
      csvem.setIsMainClass(true);  // retrieve only main classes (root concepts)
      request.setCodeSystemEntity(new CodeSystemEntity());
      request.getCodeSystemEntity().getCodeSystemVersionEntityMemberships().add(csvem);
      
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
            System.out.println("VersionId: " + csv.getVersionId() + ", " + csc.getCode()+ " - " + csc.getTerm());
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
