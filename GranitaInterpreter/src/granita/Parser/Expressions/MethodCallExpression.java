/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.SymbolTableValue;
import granita.Semantic.Types.Type;
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
        SymbolTableValue val = SymbolTableTree.getInstance().lookupFunction(id);
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
    public Object evaluate() throws GranitaException {
        Function f = (Function) SymbolTableTree.getInstance().lookupFunction(this.id);
        for (Expression arg : arguments) {
            Type t = f.getParameters().get(0).getType();
            t.setValue(arg.evaluate());
        }
        Interpreter.getInstance().register(f);
        f.getBlock().execute();
        return f.getType().getValue();
    }
}
