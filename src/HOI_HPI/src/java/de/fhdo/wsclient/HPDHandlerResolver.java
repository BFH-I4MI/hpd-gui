/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhdo.wsclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 *
 * @author Robert Mützner <robert.muetzner@fh-dortmund.de>
 */
public class HPDHandlerResolver implements HandlerResolver
{
  public List<Handler> getHandlerChain(PortInfo portInfo)
  {
    List<Handler> handlerChain = new ArrayList<Handler>();
    HPDHandler handler = new HPDHandler();
    handlerChain.add(handler);
    return handlerChain;
  }
  
}
