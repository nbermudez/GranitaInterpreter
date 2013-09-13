/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter.DataLayout;

import granita.Interpreter.Results.Result;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class RE_Variable {
    public static enum Type { INT_VARIABLE, BOOL_VARIABLE }
    
    protected String name; //for debugging purposes
    private Result value;

    public RE_Variable(String name) {
        this.name = name;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Result getValue() {
        return value;
    }

    public void setValue(Result value) {
        this.value = value;
    }  
    //</editor-fold>
    
}
