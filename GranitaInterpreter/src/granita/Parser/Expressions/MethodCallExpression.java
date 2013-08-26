/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Expressions;

import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
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
        Type t = findInSymbolTable(this.id);
        if (t == null) {
            return ErrorHandler.handle("undefined method " + id + ": line " + line);
        } else {
            Function f = (Function) SymbolTableTree.getInstance().lookupFromCurrent(id);
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

    private Type findInSymbolTable(String name) {
        Function f = (Function) SymbolTableTree.getInstance().lookupFromCurrent(name);
        if (f != null) {
            return f.getType();
        }
        return null;
    }
}
