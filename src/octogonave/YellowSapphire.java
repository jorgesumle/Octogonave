/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octogonave;

import javafx.scene.image.Image;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class YellowSapphire extends StaticSprite{
    private static final Image YELLOW_SAPPHIRE_IMG = new Image("/yellowSapphire.png", 22, 21, true, false, true);
    private static final String SVG_PATH = "M 0,4 L 0,4 4,0 18,0 22,4 22,17 18,21 4,21 0,17 Z";
    public YellowSapphire(double xLocation, double yLocation){
        super(SVG_PATH, xLocation, yLocation, YELLOW_SAPPHIRE_IMG);
    }
    
}
