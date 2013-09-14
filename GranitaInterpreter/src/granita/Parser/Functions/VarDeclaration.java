/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.Semantic.DataLayout.Context;
import granita.Semantic.DataLayout.SimpleVariable;
import granita.IR.Statements.D_Statement;
import granita.Interpreter.DataLayout.BoolVariable;
import granita.Interpreter.DataLayout.IntVariable;
import granita.Parser.Statements.Statement;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granita.Semantics.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class VarDeclaration extends Statement {

    Type type;
    ArrayList<String> varNames;

    public VarDeclaration(Type type, ArrayList<String> varNames, int line) {
        super(line);
        this.type = type;
        this.varNames = varNames;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<String> getVarNames() {
        return varNames;
    }

    public void setVarNames(ArrayList<String> varNames) {
        this.varNames = varNames;
    }

    @Override
    public String toString() {
        String var = type + " ";
        for (int i = 0; i < varNames.size() - 1; i++) {
            var += varNames.get(i) + ",";
        }
        var += varNames.get(varNames.size() - 1);
        return var;
    }

    @Override
    public D_Statement getIR() {
        checkForUnreachableStatement();
        
        Context context = SemanticUtils.getInstance().currentContext();
        
        for (String key : varNames) {
            if (context.findLocally(key) != null) {
                ErrorHandler.handle("already defined variable '" + key
                        + "': line " + this.getLine());
            } else {
                context.add(key, new SimpleVariable(type, false));
                if (this.type instanceof BoolType) {
                    context.add(context.getVariableIndex(), new BoolVariable(key, false));
                } else {
                    context.add(context.getVariableIndex(), new IntVariable(key, 0));
                }
            }
        }
        return null;
    }
}
