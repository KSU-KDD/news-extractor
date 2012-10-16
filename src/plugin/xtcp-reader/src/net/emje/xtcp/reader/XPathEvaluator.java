/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.emje.xtcp.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.w3c.dom.Document;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author shadiq
 */
public class XPathEvaluator {

    private Document sourceDocument;
    private XPath xPath = XPathFactory.newInstance().newXPath();


    public Object evaluateXPathExpression(String expression, QName returnType) throws XPathExpressionException {
        XPathExpression xPathExpression = xPath.compile(expression);
        return xPathExpression.evaluate(sourceDocument, returnType);
    }

    public void BuildDocument(String cleanedSource) throws ParserConfigurationException, SAXException, IOException{
        //build the xpath
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // set Name Space Aware to false, to prefent user have to type namespace to each xpath expression segment
        factory.setNamespaceAware(false);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        builder.setErrorHandler(null);

        this.sourceDocument = builder.parse(new ByteArrayInputStream(cleanedSource.getBytes()));
    }

    public void setDocument(Document doc){
        this.sourceDocument = doc;
    }
}
