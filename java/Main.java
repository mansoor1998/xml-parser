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

    public static void queryData(Document doc, String q) throws Exception {
        
        if(q == null){
            return;
        }

        NodeList nodeList = (NodeList) javax.xml.xpath.XPathFactory.newInstance().newXPath().evaluate(q, doc, javax.xml.xpath.XPathConstants.NODESET);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(node);
            StreamResult result = new StreamResult(System.out);
            System.out.println();
            
            transformer.transform(source, result);
        }
    }

    public static void main(String[] args) throws Exception {
        
        String inputPath = null;
        String outputPath = null;
        String query = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--input":
                    inputPath = args[++i];
                    break;
                case "--output":
                    outputPath = args[++i];
                    break;
                case "-q":
                    query = args[++i];
                    break;
            }
        }

        if(inputPath == null || outputPath == null) {
            throw new Exception(" input or output paths are not provided ");
        }

        System.out.println("Parsing and writing File..");

        Document document = readParseFile(inputPath);

        queryData(document, query);

        writeFile(document, outputPath);

        System.out.println();
        System.out.println("done");
    }
}