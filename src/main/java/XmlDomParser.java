import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class XmlDomParser {

    private static final String filePath = "src\\main\\resources\\InputXML.xml";

    public static void main(String[] args) {
        XmlDomParser xml = new XmlDomParser();
        xml.parseAndPrint();
    }

    public void parseAndPrint() {

        File file = new File(filePath);

        try {
            //Create the DocumentBuilder object.
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            //Read the XML file to Document object.
            Document document = docBuilder.parse(file);

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            //
            //XML validation is optional but good to have it before starting parsing.
            //
//            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
//            SchemaFactory schFactory = SchemaFactory.newInstance(language);
//            Schema schema = schFactory.newSchema(file);
//
//            Validator validator = schema.newValidator();
//            validator.validate(new DOMSource(document));
            //
            //If you don’t have a schema file, you can’t use the SchemaFactory and Validator classes to validate your XML file against a schema.
            //


            //Extract the Root Element
            Element root = document.getDocumentElement();
            System.out.println("root.getNodeName() = " + root.getNodeName());


            //We can examine the XML element attributes using the below methods.
            System.out.println("root.getAttribute(id) = " + root.getAttribute("id"));    //returns specific attribute
//            System.out.println("root.getAttributes = " + root.getAttributes());                //returns a Map (table) of names/values


            //Get all employees
            System.out.println("============================");
            NodeList nList = document.getElementsByTagName("employee");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //Print each employee's detail
                    Element eElement = (Element) node;
                    System.out.println("\nEmployee id : " + eElement.getAttribute("id"));
                    System.out.println("First Name : " + eElement.getElementsByTagName("firstName").item(0).getTextContent());
                    System.out.println("Last Name : " + eElement.getElementsByTagName("lastName").item(0).getTextContent());
                    System.out.println("Location : " + eElement.getElementsByTagName("location").item(0).getTextContent());
                }
            }




        } catch (ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            System.out.println("==============Parsing complete==============");
        }
    }
}
