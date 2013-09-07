/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Expressions.D_Expression;
import granita.IR.Statements.D_Return;
import granita.IR.Statements.D_Statement;
import granita.Parser.Expressions.Expression;
import granita.Types.BoolType;
import granita.Types.ErrorType;
import granita.Types.IntType;
import granita.Types.Type;
import granita.Types.VoidType;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Interpreter;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ReturnStatement extends Statement {

    private boolean isInsideFunction;
    private Expression returnExpression;
    private Type returnType = null;

    public ReturnStatement(boolean isInsideFunction, Expression returnExpression, int line) {
        super(line);
        this.isInsideFunction = isInsideFunction;
        this.returnExpression = returnExpression;
    }

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
    public void validateSemantics() throws GranitaException {
        /*super.validateSemantics();
        if (!SemanticUtils.getInstance().mustReturnExpression()
                && returnExpression != null) {
            ErrorHandler.handle("cannot return a value from method whose result"
                    + " type is void: line " + returnExpression.getLine());
        }
        if (returnExpression != null) {
            if (returnType == null) {
                returnType = returnExpression.validateSemantics();
            }
            if (returnType instanceof VoidType) {
                ErrorHandler.handle("return value cannot be void: line " + line);
            }

        }
        SemanticUtils.getInstance().setUnreachableStatement();*/
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        if (returnExpression == null) {
            return null;
        }
        if (returnType == null) {
            returnType = returnExpression.validateSemantics();
        }
        Type expectedType = SemanticUtils.getInstance().getExpectedReturnType();
        if (expectedType != null 
                && !returnType.equivalent(new ErrorType()) 
                && !expectedType.equivalent(returnType)) {
            ErrorHandler.handle("return expression type must be "
                    + expectedType + " but found " + returnType + ": line "
                    + this.getLine());
        }
        return returnType;
    }

    @Override
    public D_Statement getIR() {
        checkForUnreachableStatement();
        
        if (!SemanticUtils.getInstance().mustReturnExpression()
                && returnExpression != null) {
            ErrorHandler.handle("cannot return a value from method whose result"
                    + " type is void: line " + returnExpression.getLine());
        }
        
        D_Expression retExp = null;
        if (returnExpression != null) {
            retExp = returnExpression.getIR();
            
            returnType = retExp.getExpressionType();
            if (returnType instanceof VoidType) {
                ErrorHandler.handle("return value cannot be void: line " + line);
            }
        }
        SemanticUtils.getInstance().setUnreachableStatement();
        return new D_Return(retExp);
    }
    
}
