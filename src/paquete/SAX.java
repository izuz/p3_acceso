package paquete;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Gonzalo Izuzquiza
 */
public class SAX {

    SAXParser parser;
    ManejadorSAX sh;
    File fXML;

    public int abrir_XML_SAX(File fichero) {

        try {

            // se crea un objeto para interpretar el documento XML
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();

            //se crea una instancia que recorrera el xml secuencialmente
            sh = new ManejadorSAX();

            fXML = fichero;

            return 0;

        } catch (Exception e) {

            e.printStackTrace();
            return -1;
        }

    }

    class ManejadorSAX extends DefaultHandler {

        int ultimoelement;
        String cadena_resultado = "";

        public ManejadorSAX() {
            ultimoelement = 0;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

            if (qName.equals("Libro")) {
                cadena_resultado = cadena_resultado + "\nPublicado en: " + atts.getValue(atts.getQName(0)) + "\n";
                ultimoelement = 1;
            } else if (qName.equals("Titulo")) {
                ultimoelement = 2;
                cadena_resultado = cadena_resultado + "\nEl título es: ";
            } else if (qName.equals("Autor")) {
                ultimoelement = 3;
                cadena_resultado = cadena_resultado + "\nEl autor es: ";
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {

            if (qName.equals("Libro")) {
                System.out.println("He encontrado el final de un elemento");
                cadena_resultado = cadena_resultado + "\n -------------";
            }
        }

        @Override
        public void characters(char[] ch, int start, int lenght) throws SAXException {

            if (ultimoelement == 2) {
                for (int i = start; i < lenght + start; i++) {
                    cadena_resultado = cadena_resultado + ch[i];
                }

            } else if (ultimoelement == 3) {
                for (int i = start; i < lenght + start; i++) {
                    cadena_resultado = cadena_resultado + ch[i];
                }
            }
        }
    }

    public String recorrerSAX() {

        try {
            parser.parse(fXML, sh);
            return sh.cadena_resultado;
        } catch (SAXException e) {
            e.printStackTrace();
            return "Error al parsear con SAX";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al parsear con SAX";
        }
    }

}
