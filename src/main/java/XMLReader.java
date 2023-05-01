import org.w3c.dom.Document;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

public class XMLReader {

    public static Document readFromFile(String filePath) {
        File file = new File(filePath);

        try {
            //Create the DocumentBuilder object.
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            //Read the XML file to Document object.
            Document document = docBuilder.parse(file);

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

            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            return document;

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
        } finally {
//            System.out.println("==============Parsing complete==============");
        }
        return null;
    }
}
