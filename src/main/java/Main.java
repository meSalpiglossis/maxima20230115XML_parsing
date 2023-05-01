/**
 SOURCE ARTICLE: https://howtodoinjava.com/java/xml/read-xml-dom-parser-example/
*/


import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        final String inputFileName = "src\\main\\resources\\InputXML.xml";
        final String tagName = "employee";

        Document document = XMLReader.readFromFile(inputFileName);

        //Extract the Root Element
        Element root = document.getDocumentElement();
        System.out.println("Root node: " + root.getNodeName());

        //Check element attributes using the below methods.
        System.out.println("root.getAttribute(id) = " + root.getAttribute("id"));    //returns specific attribute
//            System.out.println("root.getAttributes = " + root.getAttributes());                //returns a Map (table) of names/values

        //Get NodeList with specified tag
        NodeList nodeList = document.getElementsByTagName(tagName);
        System.out.printf("Amount of elements with tag '%s': %d\n", tagName, nodeList.getLength());
        System.out.println("=========================================");

//        createEmployeesPojo(nodeList);

        visitChildNodes(nodeList);

    }

    private static void createEmployeesPojo(NodeList nodeList) {
        //
        //XML to POJO
        //
        //Initialize a list of employees
        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) // if element node, such as <element>
            {
                Element eElement = (Element) node;

                //Create new Employee Object
                Employee employee = new Employee();
                employee.id = (Integer.parseInt(eElement.getAttribute("id")));
                employee.firstName = (eElement.getElementsByTagName("firstName").item(0).getTextContent());
                employee.lastName = (eElement.getElementsByTagName("lastName").item(0).getTextContent());
                employee.location = (eElement.getElementsByTagName("location").item(0).getTextContent());

                //Populate Employees list
                employees.add(employee);
            }
        }

        //just a check
        for(Employee employee: employees) {
            System.out.println(employee.toString());
        }
    }

    //This function is called recursively
    private static void visitChildNodes(NodeList nodeList)
    {
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) // if element node, such as <element>
            {
                System.out.println("Node Name = " + node.getNodeName() + "; Value = " + node.getTextContent());

                //ONLY if <element> have attribute (like id)
                if (node.hasAttributes()) {
                    // get attributes names and values
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int j = 0; j < nodeMap.getLength(); j++)
                    {
                        Node tempNode = nodeMap.item(j);
                        System.out.println("Attr name : " + tempNode.getNodeName()+ "; Value = " + tempNode.getNodeValue());
                    }

                    if (node.hasChildNodes()) {
                        //We got children, let's visit
                        visitChildNodes(node.getChildNodes());
                    }
                }
            }
        }
    }
}
