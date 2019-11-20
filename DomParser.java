package com.wyncore.as400.rest.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyncore.as400.rest.api.model.Transaction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * This is a DOM Parser which will read the XML as input and convert it into JSON string
 */
public class DomParser {


    public String parse(){
        String jsonString = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document
            Document document = builder.parse(new File("customer.xml"));

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //Here comes the root node
            Element root = document.getDocumentElement();

            NodeList nList = document.getElementsByTagName("transaction");
            System.out.println("============================");

            Node node = nList.item(0);
            Element eElement = (Element) node;
            System.out.println(eElement.getAttribute("id"));
            Transaction transaction = new Transaction();
            transaction.setId(eElement.getAttribute("id"));
            transaction.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
            transaction.setState(eElement.getElementsByTagName("state").item(0).getTextContent());
            transaction.setStreet(eElement.getElementsByTagName("street").item(0).getTextContent());
            transaction.setCity(eElement.getElementsByTagName("city").item(0).getTextContent());
            transaction.setPostal(eElement.getElementsByTagName("postal").item(0).getTextContent());

            ObjectMapper mapper = new ObjectMapper();
            jsonString = mapper.writeValueAsString(transaction);
            System.out.println(jsonString);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return  jsonString;

    }
}
