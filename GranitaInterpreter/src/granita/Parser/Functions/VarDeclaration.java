/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.DataLayout.Context;
import granita.DataLayout.SimpleVariable;
import granita.IR.Statements.D_Statement;
import granita.Parser.Statements.Statement;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.SemanticUtils;
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
                context.add(key, new SimpleVariable(type, null));
            }
        }
        return null;
    }
}
