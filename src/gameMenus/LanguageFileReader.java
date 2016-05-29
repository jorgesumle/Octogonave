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

import gameElements.Main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Se encarga de la lectura de los archivos de idiomas.
 * @author Jorge Maldonado Ventura 
 */
class LanguageFileReader {
    
    /**
     * Lee el archivo de idiomas especificado.
     * @param path la ruta donde se encuentra el archivo de idiomas a leer.
     * @return un ArrayList con todos los textos que aparecen en el videojuego.
     */
    static ArrayList<String> readLanguageFile(String path){
        BufferedReader br = null;
        ArrayList<String> text = null;
            try {
                br = new BufferedReader(new FileReader(path));
                text = LanguageFileReader.storeFileContentIntoList(br);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        return text;
    }
    
    /**
     * Guarda el contenido de un fichero en un ArrayList. El salto de línea
     * actúa como delimitador, es decir, la primera línea se guarda en la posición
     * 0, la segunda en la 1...
     * @param br un BufferedReader con un FileReader con la ruta especificada.
     * @return los textos en un ArrayList de cadenas.
     */
    private static ArrayList<String> storeFileContentIntoList(BufferedReader br){
        String line;
        ArrayList<String> lines = new ArrayList<>();
        try {
            while((line = br.readLine()) != null){
                lines.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(LanguageFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lines;
    }
    
}
