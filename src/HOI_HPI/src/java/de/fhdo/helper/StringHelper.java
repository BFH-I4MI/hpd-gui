/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.helper;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class StringHelper
{
  
  public static boolean isNullOrEmpty(String s)
  {
    return (s == null || s.length() == 0);
  }
}
