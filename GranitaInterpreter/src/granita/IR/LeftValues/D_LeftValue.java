/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.LeftValues;

import granita.IR.Expressions.D_Expression;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public abstract class D_LeftValue extends D_Expression {
    String identifier;
    int contextPosition;
    int contextId;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public D_LeftValue(String identifier) {
        this.identifier = identifier;
    }

    public D_LeftValue(String identifier, int contextPosition) {
        this.identifier = identifier;
        this.contextPosition = contextPosition;
    }

    public D_LeftValue(String identifier, int contextPosition, int contextId) {
        this.identifier = identifier;
        this.contextPosition = contextPosition;
        this.contextId = contextId;
    }
    //</editor-fold>        
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    public void setContextPosition(int contextPosition) {
        this.contextPosition = contextPosition;
    }

    public int getContextPosition() {
        return contextPosition;
    }

    public int getContextId() {
        return contextId;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }
    //</editor-fold>
    
}
