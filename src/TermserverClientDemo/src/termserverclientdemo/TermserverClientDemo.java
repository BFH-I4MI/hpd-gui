/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package termserverclientdemo;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class TermserverClientDemo
{

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    // TODO code application logic here
    
    //ListCodeSystems lcs = new ListCodeSystems();
    //lcs.listCodeSystems();
    
    //ListCodeSystemConcepts lcsc = new ListCodeSystemConcepts();
    //lcsc.listCodeSystemConcepts();
    
    SynchronizeConcepts sc = new SynchronizeConcepts();
    sc.synchronizeConcepts();
    
  }
  
}
