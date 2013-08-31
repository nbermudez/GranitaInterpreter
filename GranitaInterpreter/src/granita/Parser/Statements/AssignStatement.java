/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Parser.LeftValues.LeftValue;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.SymbolTableValue;
import granita.Semantic.SymbolTable.SimpleVariable;
import granita.Semantic.Types.ErrorType;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Interpreter;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class AssignStatement extends Statement {

    LeftValue left;
    Expression value;

    public AssignStatement(int line) {
        super(line);
    }

    public AssignStatement(LeftValue left, Expression value, int line) {
        super(line);
        this.left = left;
        this.value = value;
    }

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

    @Override
    public String toString() {
        return left.toString() + " = " + value.toString();
    }

    @Override
    public void validateSemantics() throws GranitaException {
        super.validateSemantics();
        
        SemanticUtils.getInstance().setLeftValueAsLocation(true);
        Type LHS = left.validateSemantics();
        SemanticUtils.getInstance().setLeftValueAsLocation(false);
        left.initializeVariable();
        
        Type RHS = value.validateSemantics();

        if (!(LHS instanceof ErrorType)
                && !(RHS instanceof ErrorType) 
                && !LHS.equivalent(RHS)) {
            ErrorHandler.handle("cannot assign " + RHS.toString() + " to "
                    + LHS.toString() + " variable: line " + value.getLine());
        }
    }

    @Override
    public void execute() throws GranitaException {
        String id = left.toString();
        SimpleVariable v = (SimpleVariable) Interpreter.getInstance().getVariable(id);
        v.getType().setValue(value.evaluate());
    }
}
