/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.demos.scratch;

/**
 *
 * @author randy
 */

// ScratchMessage.java
// Andrew Davison, March 2009, ad@fivedots.coe.psu.ac.th

/* The Scratch message format is described in
        http://scratch.mit.edu/forums/viewtopic.php?id=9458

   The two types:
      broadcast "<name-string>"
      sensor-update <name-string> <value-string>

   We only support one name-value pair in a sensor-update message,
   and we assume the value is a string.

   The quotes are removed from name-string, and the "Scratch-" prefix
   is deleted (if present).
*/


public class ScratchMessage {
  // possible message types
  public static final int BROADCAST_MSG = 0;
  public static final int SENSOR_UPDATE_MSG = 1;
  public static final int UNKNOWN_MSG = 2;

  private int msgType = UNKNOWN_MSG;   // default values
  private String msgTypeStr = "unknown";
  private String varName = null;
  private String valStr = null;


  // convert message string into ScratchMessage field values
  public ScratchMessage(String msg) {
    String[] args = StringToks.parseTokens(msg);
    // for (String t: args)
    //  System.out.println(t);

    if (args.length < 2)    // 0 or 1 argument
      System.err.println("Incorrectly formatted message");
    else {
      // assign message type
      msgTypeStr = args[0].toLowerCase();
      if (msgTypeStr.equals("broadcast"))
        msgType = BROADCAST_MSG;
      else if (msgTypeStr.equals("sensor-update"))
        msgType = SENSOR_UPDATE_MSG;
      else
        System.err.println("Unknown message type");

      varName = extractName(args[1]);  // assign message name

      // check no. of arguments and assign sensor-update value
      if ((msgType == BROADCAST_MSG) && (args.length > 2))
        System.err.println("Ignoring extra arguments in broadcast message");
      else if (msgType == SENSOR_UPDATE_MSG) {
        if (args.length < 3)
          System.err.println("sensor-update message has no value");
        else  {
          valStr = args[2];
          if (args.length > 3)
            System.err.println("Ignoring extra arguments in sensor-update message");
        }
      }
    }
  }  // end of ScratchMessage()


  private String extractName(String nm)
  // extract the name which may be in quotes, and start with "Scratch-"
  {
    if (nm.charAt(0) == '\"')
      nm = nm.substring(1, nm.length()-1);   // remove quotes

    if (nm.startsWith("Scratch-"))
      nm = nm.substring(8);    // remove "Scratch-"

    // System.out.println("Extracted name: " + nm);
    return nm;
  }  // end of extractName()


  // accessor methods

  public int getMessageType()
  {  return msgType;  }

  public String getMessageTypeStr()
  {  return msgTypeStr;  }

  public String getName()
  {  return varName;  }

  public String getValue()
  {  return valStr;  }


  public String toString()
  {
    if (msgType ==  BROADCAST_MSG)
      return msgTypeStr + " " + varName;
    else if (msgType ==  SENSOR_UPDATE_MSG)
      return msgTypeStr + " " + varName + " " + valStr;
    else // unknown message
      return msgTypeStr;
  }  // end of toString()

}  // end of ScratchMessage class