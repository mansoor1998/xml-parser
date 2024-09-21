import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class Main {

    public static Document readParseFile(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            Document document = builder.parse(filePath);
            
            document.getDocumentElement().normalize();

            return document;
            
         } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeFile(Document document, String outputPath) {
        try {
            File outputFile = new File(outputPath);

            // prepare the transformer.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputFile);

            transformer.transform(source, result);
        } catch (Exception ex) {
            System.out.println("failed to save the file");
        }
        
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("require 2 arguments, input file path and output file path");
            return;
        }

        System.out.println("fetching give arguments.");

        String inputPath = args[0];
        String outputPath = args[1];

        System.out.println("Parsing and writing File..");

        Document document = readParseFile(inputPath);
        writeFile(document, outputPath);

        System.out.println("done");
    }
}