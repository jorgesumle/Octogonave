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

package octogonave;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

/**
 * Un <i>sprite</i> que forma parte del juego.
 * @author Jorge Maldonado Ventura 
 */
public abstract class Sprite {
    /**
     * Las imágenes que utiliza el <i>sprite</i>.
     */
    protected List<Image> spriteImages = new ArrayList<>();
    /**
     * La imagen que actualmente tiene asignada el <i>sprite</i>, esto es, la
     * representación actual del <i>sprite</i> en pantalla.
     */
    protected ImageView spriteFrame;
    /**
     * La ruta SVG del <i>sprite</i>.
     */
    protected SVGPath spriteBound;
    /**
     * La ventana en la que se ubican los <i>sprites</i>.
     */
    protected Scene scene;
    
    /**
     * Crea un <i>sprite</i> con ruta SVG, coordenadas X y Y y con las imágenes que
     * utiliza.
     * @param SVGData la ruta SVG.
     * @param xLocation la coordenada X.
     * @param yLocation la coordenada Y.
     * @param spriteImages las imágenes que utiliza el <i>sprite</i>.
     */
    public Sprite(String SVGData, double xLocation, double yLocation, Image... spriteImages){
        spriteBound = new SVGPath();
        spriteBound.setContent(SVGData);
        spriteBound.setTranslateX(xLocation);
        spriteBound.setTranslateY(yLocation);
        spriteFrame = new ImageView(spriteImages[0]);
        spriteFrame.setTranslateX(xLocation);
        spriteFrame.setTranslateY(yLocation);
        
        this.spriteImages.addAll(Arrays.asList(spriteImages));
        scene = Main.getScene();
    }
    /**
     * Este código se actualiza en cada fotograma.
     */
    public abstract void update();
    /**
     * 
     * @return las imágenes que utiliza el <i>sprite</i>.
     */
    public List<Image> getSpriteImages() {
        return spriteImages;
    }
    /**
     * Asigna nuevas imagenes al <i>sprite</i>.
     * @param spriteImages las imagenes que utiliza el <i>sprite</i>.
     */
    public void setSpriteImages(List<Image> spriteImages) {
        this.spriteImages = spriteImages;
    }
    /**
     * 
     * @return la imagen que actualmente tiene asignada el <i>sprite</i>.
     */
    public ImageView getSpriteFrame() {
        return spriteFrame;
    }
    /**
     * Asigna la imagen que se verá en pantalla del <i>sprite</i>.
     * @param spriteFrame la imagen del <i>sprite</i> que se quiere mostrar en pantalla.
     */
    public void setSpriteFrame(ImageView spriteFrame) {
        this.spriteFrame = spriteFrame;
    }
    /**
     * 
     * @return la ruta SVG del <i>sprite</i>.
     */
    public SVGPath getSpriteBound() {
        return spriteBound;
    }   
    /**
     * Asigna una nueva ruta SVG al <i>sprite</i>.
     * @param spriteBound la ruta SVG que se quiere asignar al <i>sprite</i>.
     */
    public void setSpriteBound(SVGPath spriteBound) {
        this.spriteBound = spriteBound;
    }
    
}
