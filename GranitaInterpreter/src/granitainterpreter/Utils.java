/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Utils {
    private boolean firstBlockInMethod = false;
    private Utils() {
    }
    
    public static Utils getInstance() {
        return UtilsHolder.INSTANCE;
    }
    
    private static class UtilsHolder {

        private static final Utils INSTANCE = new Utils();
    }

    public boolean isFirstBlockInMethod() {
        return firstBlockInMethod;
    }

    public void setFirstBlockInMethod(boolean firstBlockInMethod) {
        this.firstBlockInMethod = firstBlockInMethod;
    }
}
