/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Expressions.D_Expression;
import granita.IR.Statements.D_Return;
import granita.IR.Statements.D_Statement;
import granita.Misc.ErrorHandler;
import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.Type;
import granita.Semantic.Types.VoidType;
import granita.Semantics.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class ReturnStatement extends Statement {

    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    private boolean isInsideFunction;
    private Expression returnExpression;
    private Type returnType = null;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public ReturnStatement(boolean isInsideFunction, Expression returnExpression, int line) {
        super(line);
        this.isInsideFunction = isInsideFunction;
        this.returnExpression = returnExpression;
    }
    //</editor-fold>    

    public boolean isIsInsideFunction() {
        return isInsideFunction;
    }

    public void setIsInsideFunction(boolean isInsideFunction) {
        this.isInsideFunction = isInsideFunction;
    }

    @Override
    public String toString() {
        String ret = "return";
        if (returnExpression != null) {
            ret += " " + returnExpression.toString();
        }
        return ret;
    }

    @Override
    public D_Statement getIR() {
        checkForUnreachableStatement();

        if (!SemanticUtils.getInstance().mustReturnExpression()
                && returnExpression != null) {
            ErrorHandler.handle("cannot return a value from method whose result"
                    + " type is void: line " + returnExpression.getLine());
        }
        
        if (SemanticUtils.getInstance().mustReturnExpression()
                && returnExpression == null) {
            ErrorHandler.handle("return value cannot be void: line " + this.getLine());
        }

        D_Expression retExp = null;
        if (returnExpression != null) {
            retExp = returnExpression.getIR();

            if (retExp != null) {
                returnType = retExp.getExpressionType();
                if (returnType instanceof VoidType) {
                    ErrorHandler.handle("return value cannot be void: line " + line);
                } else if (!returnType.equivalent(SemanticUtils.getInstance().getExpectedReturnType())) {
                    ErrorHandler.handle("expected return type " + 
                            SemanticUtils.getInstance().getExpectedReturnType()
                            + ": line " + line);
                }
                
            }
        }
        SemanticUtils.getInstance().setUnreachableStatement();        
        SemanticUtils.getInstance().setUnreachable(true);
        return new D_Return(retExp);
    }
}
