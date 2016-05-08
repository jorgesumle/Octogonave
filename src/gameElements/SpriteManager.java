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
 * la administración de todos los <i>sprites</i> del juego. Básicamente permite
 * añadir y eliminar <i>sprites</i> del bucle del juego evitando problemas de
 * concurrencia.
 * @author Jorge Maldonado Ventura 
 */
public class SpriteManager {
    private final ArrayList<ArrayList<Sprite>> CURRENT_SPRITES;
    private final ArrayList<Sprite> CURRENT_NORMAL, NORMAL_TO_ADD, CURRENT_BULLETS,
            BULLETS_TO_ADD, REMOVED_NORMAL, NORMAL_TO_REMOVE, REMOVED_BULLETS,
            BULLETS_TO_REMOVE;
    private final ArrayList<ArrayList<Sprite>> REMOVED_SPRITES;
    
    /**
     * Construye un SpriteManage vacío.
     */
    protected SpriteManager(){
        CURRENT_SPRITES = new ArrayList<>();
        CURRENT_NORMAL = new ArrayList<>();
        NORMAL_TO_ADD = new ArrayList<>();
        CURRENT_BULLETS = new ArrayList<>();
        BULLETS_TO_ADD = new ArrayList<>();
        CURRENT_SPRITES.add(CURRENT_NORMAL);
        CURRENT_SPRITES.add(CURRENT_BULLETS);
        
        REMOVED_SPRITES = new ArrayList<>();
        REMOVED_NORMAL = new ArrayList<>();
        NORMAL_TO_REMOVE = new ArrayList<>();
        REMOVED_BULLETS = new ArrayList<>();
        BULLETS_TO_REMOVE = new ArrayList<>();
        REMOVED_SPRITES.add(REMOVED_NORMAL);
        REMOVED_SPRITES.add(REMOVED_BULLETS);
    }
    
    /**
     * @return las balas que se actualizan en el bucle del videojuego.
     */
    public ArrayList<Sprite> getCURRENT_BULLETS() {
        return CURRENT_BULLETS;
    }
    
    /**
     * @return la balas que se actualizarán de la próxima ejecución del bucle del juego en adelante.
     */
    public ArrayList<Sprite> getBULLETS_TO_ADD() {
        return BULLETS_TO_ADD;
    }
    
     /**
     * @return Las balas que se borrarán antes de la próxima ejecución del bucle del juego.
     */
    public ArrayList<Sprite> getBULLETS_TO_REMOVE() {
        return BULLETS_TO_REMOVE;
    }
    
    /**
     * @return los <i>sprites</i> que se actualizan en el bucle del videojuego.
     */
    public ArrayList<Sprite> getCURRENT_NORMAL(){
        return CURRENT_NORMAL;
    }
    
    /**
     * @return los <i>sprites</i> normales (todos menos las balas) que se
     * actualizarán de la próxima ejecución del bucle del juego en adelante.
     */
    public ArrayList<Sprite> getNORMAL_TO_ADD() {
        return NORMAL_TO_ADD;
    }
    
    /**
     * @return los <i>sprites</i> normales (todos menos las balas) que se
     * borrarán antes de la próxima ejecución del bucle del juego.
     */
    public ArrayList<Sprite> getNORMAL_TO_REMOVE() {
        return NORMAL_TO_REMOVE;
    }
    
    /**
     * Añade un número variable de balas al ArrayList de balas que
     * se añadirám antes de la próxima ejecución del bucle del juego.
     * @param sprites las balas.
     */
    protected void addToBULLETS_TO_ADD(Sprite... sprites){
        BULLETS_TO_ADD.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Elimina un número variable de balas del ArrayList de balas que
     * se eliminarán antes de la próxima ejecución del bucle del juego.
     * @param sprites las balas.
     */
    protected void addToBULLETS_TO_REMOVE(Sprite... sprites){
        BULLETS_TO_REMOVE.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Añade un número variable de balas al ArrayList de balas que se están actualizando.
     * @param sprites las balas a añadir.
     */
    protected void addToCURRENT_BULLETS(Sprite... sprites){
        CURRENT_BULLETS.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Añade un número variable de <i>sprites</i> normales (todos menos las balas)
     * al ArrayList de <i>sprites</i> que se están actualizando.
     * @param sprites los <i>sprites</i> a añadir.
     */
    protected void addToCURRENT_NORMAL(Sprite... sprites){
        CURRENT_NORMAL.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Añade un número variable de <i>sprites</i> normales (todos menos las balas) al ArrayList de <i>sprites</i>
     * normales que se quire actualizar de la próxima ejecución del bucle del juego en adelante.
     * @param sprites los <i>sprites</i> a añadir.
     */
    protected void addToNORMAL_TO_ADD(Sprite... sprites){
        NORMAL_TO_ADD.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Añade un número variable de <i>sprites</i> normales (todos menos las balas) 
     * que se quire eliminar antes de la próxima ejecución del bucle del juego.
     * @param sprites los <i>sprites</i> a eliminar.
     */
    protected void addToNORMAL_TO_REMOVE(Sprite... sprites){
        NORMAL_TO_REMOVE.addAll(Arrays.asList(sprites));
    }
    
    /**
     * Deja el ArrayList de balas que se actualizarán de la próxima ejecución en
     * adelante vacío. Esto se debería hacer cuando esos <i>sprites</i> ya están
     * en el ArrayList de los que se están actualizando.
     */
    protected void clearBULLETS_TO_ADD(){
        BULLETS_TO_ADD.clear();
    }
    
    /**
     * Deja el ArrayList de balas que se eliminarán antes de la próxima ejecución
     * vacío. Esto se debería hacer cuando esas balas ya se han añadido al ArrayList de las eliminadas.
     */
    protected void clearBULLETS_TO_REMOVE(){
        BULLETS_TO_REMOVE.clear();
    }
    
    /**
     * Deja el ArrayList de <i>sprites</i> normales (todos menos las balas) que
     * se actualizarán de la próxima ejecución en adelante vacío. Esto se debería hacer cuando
     * esos <i>sprites</i> ya están en el ArrayList de los que se están actualizando.
     */
    protected void clearNORMAL_TO_ADD(){
        NORMAL_TO_ADD.clear();
    }
    
    /**
     * Deja el ArrayList de <i>sprites</i> normales (todos menos las balas) que
     * se eliminarán antes de la próxima ejecución vacío. Esto se debería hacer cuando
     * esos <i>sprites</i> ya se han añadido al ArrayList de los eliminados.
     */
    protected void clearNORMAL_TO_REMOVE(){
        NORMAL_TO_REMOVE.clear();
    }
    
    /**
     * Elimina un un número variable de balas
     * del ArrayList de balas que se están actualizando en el bucle del videojuego.
     * @param sprites los <i>sprites</i> a eliminar.
     */
    protected void removeFromCURRENT_BULLETS(Sprite... sprites){
        CURRENT_BULLETS.removeAll(Arrays.asList(sprites));
        REMOVED_BULLETS.addAll(Arrays.asList(sprites));
    }    
    
    /**
     * Elimina un un número variable de <i>sprites</i> normales (todos menos las balas)
     * del ArrayList de los <i>sprites</i> normales que se están actualizando en el bucle del videojuego.
     * @param sprites los <i>sprites</i> a eliminar.
     */
    protected void removeFromCURRENT_NORMAL(Sprite... sprites){
        CURRENT_NORMAL.removeAll(Arrays.asList(sprites));
        REMOVED_NORMAL.addAll(Arrays.asList(sprites));
    }    
    
}
