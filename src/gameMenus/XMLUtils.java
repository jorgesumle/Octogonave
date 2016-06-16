/*
 * Copyright (C) 2016 Jorge Maldonado Ventura
 *
 * Este programa es software libre: usted puede redistruirlo y/o modificarlo
 * bajo los términos de la Licencia Pública General GNU, tal y como está publicada por
 * la Free Software Foundation; ya sea la versión 3 de la Licencia, o
 * (a su elección) cualquier versión posterior.
 *
 * Este programa se distribuye con la intención de ser útil,
 * pero SIN NINGUNA GARANTÍA; incluso sin la garantía implícita de
 * USABILIDAD O UTILIDAD PARA UN FIN PARTICULAR. Vea la
 * Licencia Pública General GNU para más detalles.
 *
 * Usted debería haber recibido una copia de la Licencia Pública General GNU
 * junto a este programa.  Si no es así, vea <http://www.gnu.org/licenses/>.
 */
package gameMenus;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Contiene métodos que sirven de ayuda para la realización de operaciones
 * relacionadas con documentos XML.
 * @author Jorge Maldonado Ventura
 */
class XMLUtils {
    
    /**
     * Crea un objeto <tt>Document</tt> a partir de la ruta del archivo
     * que se quiere parsear.
     * @param xmlPath la ruta del archivo que se quiere parsear.
     * @return el objeto <tt>Document</tt> resultante.
     */
    static Document createDocument(String xmlPath){
        Document document = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlPath);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XMLUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return document;
    }
    
    /**
     * Realiza una transformación ejecutando el método <tt>transform()</tt>.
     * Para realizar la transformación se construye un <tt>transformer</tt>,
     * un <tt>DOMSource</tt> y un <tt>StreamResult</tt> con los parámetros.
     * @param document el documento XML que se desea transformar.
     * @param outputXml la ruta donde se creará el nuevo documento XML. Sobreescribirá
     * el anterior si existe.
     */
    static void transform(Document document, String outputXml){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(outputXml));
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(ScoreXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
