/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.piketec.jenkins.plugins.tpt;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.piketec.jenkins.plugins.tpt.TptLog.LogLevel;

import hudson.FilePath;

public class TestcaseParser extends DefaultHandler {

  private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

  private Testcase ti = null;

  private LogLevel type = null;

  private StringBuffer logString = null;

  private AssessmentVariable assessmentVariable = null;

  /**
   * Parse an XML file to retrieve a testcase info instance (non null)
   * 
   * @param xmlFile
   *          testcase_information.xml
   * @return A testcase, filled with the content of the xml-file.
   * 
   * @throws IOException
   *           if the xml file cannot be read or has a wrong format
   */
  public static Testcase parseXml(FilePath xmlFile) throws IOException {
    try {
      TestcaseParser parser = new TestcaseParser();
      SAXParserFactory.newInstance().newSAXParser().parse(xmlFile.read(), parser);
      if (parser.ti == null) {
        throw new IOException(
            "XML file " + xmlFile + " does not contain tag <testcaseinformation>");
      }
      return parser.ti;
    } catch (ParserConfigurationException e) {
      throw new IOException("XML parser config error: " + e.getMessage());
    } catch (SAXException e) {
      throw new IOException("SAX error: " + e.getMessage());
    } catch (IOException e) {
      throw new IOException("I/O error: " + e.getMessage());
    } catch (InterruptedException e) {
      throw new IOException("Interrupted reading XML File: " + e.getMessage(), e);
    }
  }

  // -------------------------------------

  private TestcaseParser() {
  }

  @Override
  public void startElement(String s, String s1, String elementName, Attributes attributes)
      throws SAXException {

    if (elementName.equalsIgnoreCase("TestcaseInformation")) {
      ti = new Testcase();
      ti.setName(attributes.getValue("ScenarioName"));
      ti.setID(Integer.parseInt(attributes.getValue("ScenarioId")));
      ti.setExecDuration(attributes.getValue("ExecutionDuration"));
      ti.setResult(attributes.getValue("Result"));
      ti.setExecDate(parseDate(attributes.getValue("ExecDate")));

    } else if (elementName.equalsIgnoreCase("Log")) {
      logString = new StringBuffer();
      String type = attributes.getValue("Type");
      if (type == null) {
        this.type = LogLevel.ALL;
      } else if (type.equalsIgnoreCase("info") || type.equalsIgnoreCase("customoutput")
          || type.equalsIgnoreCase("section")) {
        this.type = LogLevel.INFO;
      } else if (type.equalsIgnoreCase("warning")) {
        this.type = LogLevel.WARNING;
      } else if (type.equalsIgnoreCase("error")) {
        this.type = LogLevel.ERROR;
      } else if (!type.equalsIgnoreCase("invisible")) {
        this.type = LogLevel.ALL;
      }
    } else if (elementName.equalsIgnoreCase("AssessmentVariable")) {
      String name = attributes.getValue("Name");
      String result = attributes.getValue("Result");
      String type = attributes.getValue("Type");
      String value = attributes.getValue("Value");
      assessmentVariable = new AssessmentVariable(name, result, type, value);
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equalsIgnoreCase("log")) {
      ti.addLogEntry(logString.toString(), type);
      logString = null;
    } else if (qName.equalsIgnoreCase("AssessmentVariable")) {
      ti.addAssessmentVariable(assessmentVariable);
      assessmentVariable = null;
    }
  }

  @Override
  public void characters(char[] ac, int i, int j) throws SAXException {
    if (logString != null) {
      logString.append(ac, i, j);
    }
    if (assessmentVariable != null) {
      assessmentVariable.appendMessage(new String(ac, i, j));
    }
  }

  private Date parseDate(String value) throws SAXException {
    if (value != null) {
      synchronized (sdf) {
        try {
          return sdf.parse(value);
        } catch (ParseException e) {
          throw new SAXException("Can't parse date format \"" + value + "\"");
        }
      }
    } else {
      return null;
    }
  }

}
