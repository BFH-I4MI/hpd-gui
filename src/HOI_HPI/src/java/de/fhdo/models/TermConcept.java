/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.models;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class TermConcept
{
  private long conceptId;
  private String code, term, termAbbrevation;

  /**
   * @return the conceptId
   */
  public long getConceptId()
  {
    return conceptId;
  }

  /**
   * @param conceptId the conceptId to set
   */
  public void setConceptId(long conceptId)
  {
    this.conceptId = conceptId;
  }

  /**
   * @return the code
   */
  public String getCode()
  {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code)
  {
    this.code = code;
  }

  /**
   * @return the term
   */
  public String getTerm()
  {
    return term;
  }

  /**
   * @param term the term to set
   */
  public void setTerm(String term)
  {
    this.term = term;
  }

  /**
   * @return the termAbbrevation
   */
  public String getTermAbbrevation()
  {
    return termAbbrevation;
  }

  /**
   * @param termAbbrevation the termAbbrevation to set
   */
  public void setTermAbbrevation(String termAbbrevation)
  {
    this.termAbbrevation = termAbbrevation;
  }
  
  
}
