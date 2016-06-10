/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameElements;

import javafx.scene.image.Image;

/**
 *
 * @author jorge
 */
public abstract class Gem extends Sprite {
    
    protected byte destructionFrame, selectedFrame;    
    protected boolean destroy;
    
    public Gem(String SVGData, double xLocation, double yLocation, Image... spriteImages) {
        super(SVGData, xLocation, yLocation, spriteImages);
        destructionFrame = 0;
        selectedFrame = 0;
    }
    
    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    @Override
    protected void update(){
        if(destroy){
            destroy();
        }
    }
    
    abstract void destroy();
    
}
