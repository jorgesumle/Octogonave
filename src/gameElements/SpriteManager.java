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

package gameElements;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase formada por un conjunto de objetos ArrayList que son utilizados para
 * la administración de todos los <i>sprites</i> del juego. Básicamente, permite
 * añadir y eliminar <i>sprites</i> del bucle del juego evitando problemas de
 * concurrencia.
 * @author Jorge Maldonado Ventura 
 */
class SpriteManager {
    
    private final ArrayList<ArrayList<Sprite>> currentSprites;
    private final ArrayList<Sprite> currentNormal, normalToAdd, currentBullets,
            bulletsToAdd, removedNormal, normalToRemove, removedBullets,
            bulletsToRemove;
    private final ArrayList<ArrayList<Sprite>> removedSprites;
    
    /**
     * Construye un SpriteManage vacío.
     */
    SpriteManager(){
        currentSprites = new ArrayList<>();
        currentNormal = new ArrayList<>();
        normalToAdd = new ArrayList<>();
        currentBullets = new ArrayList<>();
        bulletsToAdd = new ArrayList<>();
        currentSprites.add(currentNormal);
        currentSprites.add(currentBullets);
        
        removedSprites = new ArrayList<>();
        removedNormal = new ArrayList<>();
        normalToRemove = new ArrayList<>();
        removedBullets = new ArrayList<>();
        bulletsToRemove = new ArrayList<>();
        removedSprites.add(removedNormal);
        removedSprites.add(removedBullets);
    }
    
    /**
     * @return las balas que se actualizan en el bucle del videojuego.
     */
    ArrayList<Sprite> getCurrentBullets() {
        return currentBullets;
    }
    
    /**
     * @return la balas que se actualizarán de la próxima ejecución del bucle del juego en adelante.
     */
    ArrayList<Sprite> getBulletsToAdd() {
        return bulletsToAdd;
    }
    
     /**
     * @return Las balas que se borrarán antes de la próxima ejecución del bucle del juego.
     */
    ArrayList<Sprite> getBulletsToRemove() {
        return bulletsToRemove;
    }
    
    /**
     * @return los <i>sprites</i> que se actualizan en el bucle del videojuego.
     */
    ArrayList<Sprite> getCurrentNormal(){
        return currentNormal;
    }
    
    /**
     * @return los <i>sprites</i> normales (todos menos las balas) que se
     * actualizarán de la próxima ejecución del bucle del juego en adelante.
     */
    ArrayList<Sprite> getNormalToAdd() {
        return normalToAdd;
    }
    
    /**
     * @return los <i>sprites</i> normales (todos menos las balas) que se
     * borrarán antes de la próxima ejecución del bucle del juego.
     */
    ArrayList<Sprite> getNormalToRemove() {
        return normalToRemove;
    }
    
    /**
     * Añade un número variable de balas al ArrayList de balas que
     * se añadirám antes de la próxima ejecución del bucle del juego.
     * @param sprites las balas.
     */
    void addToBulletsToAdd(Sprite... sprites){
        bulletsToAdd.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Elimina un número variable de balas del ArrayList de balas que
     * se eliminarán antes de la próxima ejecución del bucle del juego.
     * @param sprites las balas.
     */
    void addToBulletsToRemove(Sprite... sprites){
        bulletsToRemove.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Añade un número variable de balas al ArrayList de balas que se están actualizando.
     * @param sprites las balas a añadir.
     */
    void addToCurrentBullets(Sprite... sprites){
        currentBullets.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Añade un número variable de <i>sprites</i> normales (todos menos las balas)
     * al ArrayList de <i>sprites</i> que se están actualizando.
     * @param sprites los <i>sprites</i> a añadir.
     */
    void addToCurrentNormal(Sprite... sprites){
        currentNormal.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Añade un número variable de <i>sprites</i> normales (todos menos las balas) al ArrayList de <i>sprites</i>
     * normales que se quire actualizar de la próxima ejecución del bucle del juego en adelante.
     * @param sprites los <i>sprites</i> a añadir.
     */
    void addToNormalToAdd(Sprite... sprites){
        normalToAdd.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Añade un número variable de <i>sprites</i> normales (todos menos las balas) 
     * que se quire eliminar antes de la próxima ejecución del bucle del juego.
     * @param sprites los <i>sprites</i> a eliminar.
     */
    void addToNormalToRemove(Sprite... sprites){
        normalToRemove.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Deja el ArrayList de balas que se actualizarán de la próxima ejecución en
     * adelante vacío. Esto se debería hacer cuando esos <i>sprites</i> ya están
     * en el ArrayList de los que se están actualizando.
     */
    void clearBulletsToAdd(){
        bulletsToAdd.clear();
    }
    
    /**
     * Deja el ArrayList de balas que se eliminarán antes de la próxima ejecución
     * vacío. Esto se debería hacer cuando esas balas ya se han añadido al ArrayList de las eliminadas.
     */
    void clearBulletsToRemove(){
        bulletsToRemove.clear();
    }
    
    /**
     * Deja el ArrayList de <i>sprites</i> normales (todos menos las balas) que
     * se actualizarán de la próxima ejecución en adelante vacío. Esto se debería hacer cuando
     * esos <i>sprites</i> ya están en el ArrayList de los que se están actualizando.
     */
    void clearNormalToAdd(){
        normalToAdd.clear();
    }
    
    /**
     * Deja el ArrayList de <i>sprites</i> normales (todos menos las balas) que
     * se eliminarán antes de la próxima ejecución vacío. Esto se debería hacer cuando
     * esos <i>sprites</i> ya se han añadido al ArrayList de los eliminados.
     */
    void clearNormalToRemove(){
        normalToRemove.clear();
    }
    
    /**
     * Elimina un un número variable de balas
     * del ArrayList de balas que se están actualizando en el bucle del videojuego.
     * @param sprites los <i>sprites</i> a eliminar.
     */
    void removeFromCurrentBullets(Sprite... sprites){
        currentBullets.removeAll(Arrays.asList(sprites));
        removedBullets.addAll(Arrays.asList(sprites));
    }    
    
    /**
     * Elimina un un número variable de <i>sprites</i> normales (todos menos las balas)
     * del ArrayList de los <i>sprites</i> normales que se están actualizando en el bucle del videojuego.
     * @param sprites los <i>sprites</i> a eliminar.
     */
    void removeFromCurrentNormal(Sprite... sprites){
        currentNormal.removeAll(Arrays.asList(sprites));
        removedNormal.addAll(Arrays.asList(sprites));
    }
    
    void pauseCurrentSpritesTimeline(){
        getCurrentNormal().stream().forEach((sprite) -> {
            if(sprite instanceof MovingEnemy){
                ((MovingEnemy)sprite).getTimeline().pause();
            }
        });
    }
    
    void resumeCurrentSpritesTimeline(){
        getCurrentNormal().stream().forEach((sprite) -> {
            if(sprite instanceof MovingEnemy){
                ((MovingEnemy)sprite).getTimeline().play();
            }
        });
    }
    
}
