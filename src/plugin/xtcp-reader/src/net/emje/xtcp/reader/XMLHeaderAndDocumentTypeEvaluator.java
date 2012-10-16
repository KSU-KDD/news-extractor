/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.emje.xtcp.reader;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * This Class is used as XML Header And DocumentType evaluator for SourceString. It,s extract XML Header and Document Type, resulting :<br>
 * <ul>
 *  <li>The lines of XML Header and Document Type in {@code ArrayList} object by executing {@code generateLinesOfIt()} method</li>
 *  <li>XML Header String as a single line of String</li>
 *  <li>Document Type String as a single line of String</li>
 * </ul>
 * @author shadiq
 */
public class XMLHeaderAndDocumentTypeEvaluator {

    private String sourceString;
    private ArrayList<String> linesOfIt;
    private String documentType = "";
    private String XMLHeader = "";

    /**
      * This Class is used as XML Header And DocumentType evaluator for SourceString. It,s extract XML Header and Document Type, resulting :<br>
    * <ul>
    *  <li>The lines of XML Header and Document Type in {@code ArrayList} object by executing {@code generateLinesOfIt()} method</li>
    *  <li>XML Header String as a single line of String</li>
    *  <li>Document Type String as a single line of String</li>
    * </ul>
     * @param sourceString
     */
    public XMLHeaderAndDocumentTypeEvaluator(String sourceString) {
        this.sourceString = sourceString;
    }

    /**
     * Generating:<br>
     * <ul>
     *  <li>The lines of XML Header and Document Type in {@code ArrayList} object,<br>
     *  could be acquired by {@code getLinesOfIt()} method</li>
     *  <li>XML Header String as a single line of String,<br>
     *  could be acquired by {@code getXMLHeader()} method</li>
     *  <li>Document Type String as a single line of String,<br>
     *  could be acquired by {@code getDocumentType()} method</li>
     * </ul>
     */
    public void generateLinesOfIt() {
        try {
            BufferedReader br = new BufferedReader(new StringReader(sourceString));
            String line = null;
            linesOfIt = new ArrayList<String>();
            boolean isXMLHeaderSet = false;
            boolean isDocumentTypeSet = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("<?xml") && line.contains(">")) {
                    XMLHeader = line.trim();
                    linesOfIt.add(line);
                    isXMLHeaderSet = true;
                } else if (line.startsWith("<?xml") && !line.contains(">")) {
                    XMLHeader = line.trim();
                    linesOfIt.add(line);
                    while ((line = br.readLine()) != null) {
                        if (line.contains(">")) {
                            linesOfIt.add(line);
                            line = line.split(">")[0].trim();
                            XMLHeader = XMLHeader + " " + line + ">";
                            isXMLHeaderSet = true;
                            break;
                        } else {
                            XMLHeader = XMLHeader + " " + line.trim();
                            linesOfIt.add(line);
                        }
                    }
                }

                if (line.startsWith("<!DOCTYPE") && line.contains(">")) {
                    documentType = line.trim();
                    linesOfIt.add(line);
                    isDocumentTypeSet = true;
                } else if (line.startsWith("<!DOCTYPE") && !line.contains(">")) {
                    documentType = line.trim();
                    linesOfIt.add(line);
                    while ((line = br.readLine()) != null) {
                        if (line.contains(">")) {
                            linesOfIt.add(line);
                            line = line.split(">")[0].trim();
                            documentType = documentType + " " + line + ">";
                            isDocumentTypeSet = true;
                            break;
                        } else {
                            documentType = documentType + " " + line.trim();
                            linesOfIt.add(line);
                        }
                    }
                }
                if (isXMLHeaderSet && isDocumentTypeSet) {
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex);
            ex.printStackTrace(System.err);
        }
    }

    /**
     *
     * @return XML Header String as a single line of String
     */
    public String getXMLHeader() {
        return XMLHeader;
    }

    /**
     *
     * @return Document Type String as a single line of String
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     *
     * @return The lines of XML Header and Document Type in {@code ArrayList} object by executing {@code generateLinesOfIt()} method
     */
    public ArrayList<String> getLinesOfIt() {
        return linesOfIt;
    }
}
