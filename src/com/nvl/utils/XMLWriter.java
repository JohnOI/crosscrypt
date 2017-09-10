/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nvl.utils;

/**
 *  This class writes XML formatted files with the public methods it defines.
 *
 * @author johnoai
 */
public class XMLWriter {

  private int beginTagCount = 0;
  private int endTagCount = 0;

  private String xml="";

  public XMLWriter() {
      String XMLHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
      xml += XMLHeader;
  }

  public void addBeginTag(String tag) {
    String start = "<";
    if (beginTagCount > 0) {
      start = "\n\t<";
    }
    xml = xml + start + tag + ">";
    beginTagCount++;
  }

  public void addEndTag(String tag) {
    String start = "</";
    if ( (beginTagCount - endTagCount) == 1) {
      start = "\n</";
    }
    xml = xml + start + tag + ">";
    endTagCount++;
  }

  public void addTagValue(String value) {
    xml = xml + value ;
  }

  public void addTagsAndValue(String tag, String value) {
    addBeginTag(tag);
    addTagValue(value);
    addEndTag(tag);
  }

  public String getXML() {
    return xml;
  }

  public boolean isValid() {
    return beginTagCount == endTagCount ? true: false;
  }

    

}
