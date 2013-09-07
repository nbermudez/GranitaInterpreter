/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.IR.Expressions.D_Expression;
import granita.IR.Expressions.D_MethodCallExpression;
import granita.Parser.Statements.BlockStatement;
import granita.DataLayout.Function;
import granita.SymbolTable.SymbolTableEntry;
import granita.SymbolTable.SymbolTableTree;
import granita.DataLayout.Variable;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Interpreter;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodCallExpression extends Expression {

    ArrayList<Expression> arguments;
    String id;

    public MethodCallExpression(int line) {
        super(line);
    }

    public MethodCallExpression(String id, ArrayList<Expression> arguments, int line) {
        super(line);
        this.arguments = arguments;
        this.id = id;
    }

    @Override
    public String toString() {
        String t = id + "(";

        for (int i = 0; i < arguments.size() - 1; i++) {
            t = t + arguments.get(i).toString() + ",";
        }
        if (arguments.size() > 0) {
            t = t + arguments.get(arguments.size() - 1).toString();
        }

        t = t + ")";
        return t;
    }

    @Override
    public Type validateSemantics() throws GranitaException {
        SymbolTableEntry val = SymbolTableTree.getInstance().lookupFunction(id);
        Function f;
        if (val == null) {
            return ErrorHandler.handle("no such method '" + id + "': line " 
                    + this.getLine());
        } else {
            f = (Function) val;
        }
        Type t = f.getType();
        if (t == null) {
            return ErrorHandler.handle("undefined method " + id + ": line " + line);
        } else {
            if (arguments.size() != f.getParameters().size()) {
                ErrorHandler.handle("actual and formal argument list differ in length "
                        + ": line " + this.getLine());
            }
            int min = arguments.size()<f.getParameters().size()?
                    arguments.size():f.getParameters().size();
            for (int i = 0; i < min; i++) {
                Expression ex = arguments.get(i);
                Type ret = ex.validateSemantics();
                Type o = f.getParameters().get(i).getType();
                if (!o.equivalent(ret)) {
                    ErrorHandler.handle("incompatible types in method call's arg " + i
                            + ": line " + ex.getLine());
                }
            }
            return t;
        }
    }

    @Override
    public D_Expression getIR() {
        SymbolTableEntry val = SymbolTableTree.getInstance().lookupFunction(id);
        Function f;
        if (val == null) {
            ErrorHandler.handle("no such method '" + id + "': line " 
                    + this.getLine());
            return null;
        } else {
            f = (Function) val;
        }
        Type t = f.getType();
        if (t == null) {
            ErrorHandler.handle("undefined method " + id + ": line " + line);
            return null;
        } else {
            if (arguments.size() != f.getParameters().size()) {
                ErrorHandler.handle("actual and formal argument list differ in length "
                        + ": line " + this.getLine());
            }
            int min = arguments.size()<f.getParameters().size()?
                    arguments.size():f.getParameters().size();
            ArrayList<D_Expression> d_args = new ArrayList<D_Expression>();
            for (int i = 0; i < min; i++) {
                D_Expression ex = arguments.get(i).getIR();
                Type ret = ex.getExpressionType();
                Type o = f.getParameters().get(i).getType();
                if (!o.equivalent(ret)) {
                    ErrorHandler.handle("incompatible types in method call's arg " + i
                            + ": line " + arguments.get(i).getLine());
                }
                d_args.add(ex);
            }
            return new D_MethodCallExpression(id, d_args);
        }
    }
}
