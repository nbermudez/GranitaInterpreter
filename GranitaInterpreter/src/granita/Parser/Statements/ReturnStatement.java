/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.ErrorType;
import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granita.Semantic.Types.VoidType;
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
        super.validateSemantics();
        if (!SemanticUtils.getInstance().mustReturnExpression()
                && returnExpression != null) {
            ErrorHandler.handle("cannot return a value from method whose result"
                    + " type is void: line " + returnExpression.getLine());
            SemanticUtils.getInstance().setErrored();
        }
        if (returnExpression != null) {
            if (returnType == null) {
                returnType = returnExpression.validateSemantics();
            }
            if (returnType instanceof VoidType) {
                ErrorHandler.handle("return value cannot be void: line " + line);
                SemanticUtils.getInstance().setErrored();
            }

        }
        SemanticUtils.getInstance().setUnreachableStatement();
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
    public void execute() throws GranitaException {
        if (returnType instanceof BoolType || returnType instanceof IntType) {
            Interpreter.getInstance().setReturnValue(returnExpression.evaluate());
        } else  {
            Interpreter.getInstance().setReturnReached(true);
        }
    }
    
}
