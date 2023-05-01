import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlDomParserOriginal {

    public static void main(String[] args) {

        final String inputFileName = "src\\main\\resources\\InputXML.xml";

        try {
            /*
            //// EXAMPLE: The Simplest way of DOM parse with validation
            // Singleton DP
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Abstract Factory DP
            DocumentBuilder builder = factory.newDocumentBuilder(); // Builder Design Pattern

            final String inputFileName = "C:\\Users\\HP\\Downloads\\reader-development\\TestValueType\\target\\classes\\InputXML.xml";
            Document document = builder.parse(new File( inputFileName ));

            Schema schema = null;
            try {
                String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
                // Singleton PD
                SchemaFactory schemaFactory = SchemaFactory.newInstance(language); // Abstract Factory DP
                //schema = schemaFactory.newSchema(new File(inputFileName));
                schema = schemaFactory.newSchema();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
            Validator validator = schema.newValidator();   // Factory Method DP
            //validator.validate(new DOMSource(document)); // NOTE: Don't work due to prefix leaks

            Element root = document.getDocumentElement();
           //System.out.println(root.getAttributes()); // returns a Map (table) of names/values

            //root.getElementsByTagName("firstName"); // returns a list of sub-elements of specified name
            root.getChildNodes(); // returns a list of all child nodes
            */

            //// EXAMPLE: getChildNodes example
            /*
            NodeList nodes = root.getChildNodes();
            for (int count = 0; count < nodes.getLength(); count++) {
                Node node = nodes.item(count);
                System.out.println(node.getNodeName() + ", " + node.getNodeValue());
            }
            */

            /*
            //// EXAMPLE: The Simplest example of DOM parse with not recursive nodes print
            // Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            // Here comes the root node
            Element root = document.getDocumentElement();
            System.out.println(root.getNodeName());

            // Get all employees
            NodeList nList = document.getElementsByTagName("employee");
            System.out.println("============================");

            for (int i = 0; i < nList.getLength(); i++)
            {
                Node node = nList.item(i);

                System.out.println(""); // Just a separator

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    // Print each employee's detail
                    Element eElement = (Element) node;
                    System.out.println("Employee id : "    + eElement.getAttribute("id"));
                    System.out.println("First Name : "     + eElement.getElementsByTagName("firstName").item(0).getTextContent());
                    System.out.println("Last Name : "      + eElement.getElementsByTagName("lastName").item(0).getTextContent());
                    System.out.println("Location : "       + eElement.getElementsByTagName("location").item(0).getTextContent());
                }
            }
             */

            /*
            //// EXAMPLE: not recursive print DTO \ POJO based
            System.out.println("============================");
            for (Employee currentEmployee: parseEmployeesXML()){
                System.out.println(currentEmployee.toString());
            }
            System.out.println("============================");
             */

            //// EXAMPLE: recursive print
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Build Document

            Document document = builder.parse(new File(inputFileName));

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();
            //Here comes the root node
            Element root = document.getDocumentElement();
            System.out.println(root.getNodeName());

            //Get all employees
            NodeList nList = document.getElementsByTagName("employee");
            System.out.println("nList.getLength() = " + nList.getLength());
            System.out.println("============================");

            visitChildNodes(nList);
        }
        catch(ParserConfigurationException ex){
            System.out.println(ex.toString());
        }
        catch(SAXException ex){
            System.out.println(ex.toString());
        }
        catch(IOException ex){
            System.out.println(ex.toString());
        }
        finally{
            System.out.println("============================");
            System.out.println("Program completed!");
        }
    }

    private static List<Employee> parseEmployeesXML() throws ParserConfigurationException, SAXException, IOException
    {
        //Initialize a list of employees
        List<Employee> employees = new ArrayList<Employee>();
        Employee employee = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        final String inputFileName = "src\\main\\resources\\InputXML.xml";
        Document document = builder.parse(new File(inputFileName));
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("employee");
        for (int i = 0; i < nList.getLength(); i++)
        {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;

                //Create new Employee Object
                employee = new Employee();
                employee.id = (Integer.parseInt(eElement.getAttribute("id")));
                employee.firstName = (eElement.getElementsByTagName("firstName").item(0).getTextContent());
                employee.lastName = (eElement.getElementsByTagName("lastName").item(0).getTextContent());
                employee.location = (eElement.getElementsByTagName("location").item(0).getTextContent());

                //Add Employee to list
                employees.add(employee);
            }
        }
        return employees;
    }

    //This function is called recursively
    private static void visitChildNodes(NodeList nList)
    {
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                System.out.println("Node Name = " + node.getNodeName() + "; Value = " + node.getTextContent());
                //Check all attributes
                if (node.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++)
                    {
                        Node tempNode = nodeMap.item(i);
                        System.out.println("Attr name : " + tempNode.getNodeName()+ "; Value = " + tempNode.getNodeValue());
                    }
                    if (node.hasChildNodes()) {
                        //We got more childs; Let's visit them as well
                        visitChildNodes(node.getChildNodes());
                    }
                }
            }
        }
    }
}
