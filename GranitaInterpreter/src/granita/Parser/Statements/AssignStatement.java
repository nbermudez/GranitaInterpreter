/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Expressions.D_Expression;
import granita.IR.LeftValues.D_LeftValue;
import granita.IR.Statements.D_Assign;
import granita.IR.Statements.D_Statement;
import granita.Parser.Expressions.Expression;
import granita.Parser.LeftValues.LeftValue;
import granita.Types.ErrorType;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class AssignStatement extends Statement {

    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    LeftValue left;
    Expression value;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public AssignStatement(int line) {
        super(line);
    }

    public AssignStatement(LeftValue left, Expression value, int line) {
        super(line);
        this.left = left;
        this.value = value;
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public LeftValue getLeft() {
        return left;
    }

    public void setLeft(LeftValue left) {
        this.left = left;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
    //</editor-fold>    

    @Override
    public String toString() {
        return left.toString() + " = " + value.toString();
    }

    @Override
    public D_Statement getIR() {
        checkForUnreachableStatement();
        
        SemanticUtils.getInstance().setLeftValueAsLocation(true);
        D_LeftValue lValue = left.getIR();
        SemanticUtils.getInstance().setLeftValueAsLocation(false);
        D_Expression dValue = value.getIR();
        left.initializeVariable();
        
        if (lValue != null && dValue != null) {
            Type LHS = lValue.getExpressionType(), RHS = dValue.getExpressionType();
            if (LHS != null && RHS != null && !(LHS instanceof ErrorType)
                    && !(RHS instanceof ErrorType) 
                    && !LHS.equivalent(RHS)) {
                ErrorHandler.handle("cannot assign " + RHS.toString() + " to "
                        + LHS.toString() + " variable: line " + value.getLine());
            }
        }
        return new D_Assign(lValue, dValue);
    }
    
}
