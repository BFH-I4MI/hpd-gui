/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.models;

import java.util.Comparator;
import types.termserver.fhdo.de.CodeSystemEntity;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class CodeSystemEntityComparator implements Comparator<CodeSystemEntity>
{



  public int compare(CodeSystemEntity cse1, CodeSystemEntity cse2)
  {
    String term1 = cse1.getCodeSystemEntityVersions().get(0).getCodeSystemConcepts().get(0).getTerm();
    String term2 = cse2.getCodeSystemEntityVersions().get(0).getCodeSystemConcepts().get(0).getTerm();
    
    return term1.compareToIgnoreCase(term2);
  }
  
}
