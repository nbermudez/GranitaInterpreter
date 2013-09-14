/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Interpreter.Results.Result;
import granita.Semantic.Types.ErrorType;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class D_BinaryExpression extends D_Expression {
    D_Expression left, right;

    public D_BinaryExpression(D_Expression left, D_Expression right) {
        this.left = left;
        this.right = right;
        if (!this.left.expressionType.equivalent(this.right.expressionType)) {
            this.expressionType = new ErrorType();
        } else {
            this.expressionType = this.left.expressionType;
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public D_Expression getLeft() {
        return left;
    }

    public void setLeft(D_Expression left) {
        this.left = left;
    }

    public D_Expression getRight() {
        return right;
    }

    public void setRight(D_Expression right) {
        this.right = right;
    }
    //</editor-fold>    
    
    @Override
    public abstract Result eval();
}
