/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.Parser.Statements.BlockStatement;
import granita.Parser.Statements.Statement;
import granita.DataLayout.SimpleVariable;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
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
    public void validateSemantics() throws GranitaException {
        super.validateSemantics();
        BlockStatement currentBlock = SemanticUtils.getInstance().getCurrentBlock();
        for (String name : varNames) {
            if (currentBlock.alreadyRegistered(name)) {
                ErrorHandler.handle("already defined variable '" + name
                        + "': line " + this.getLine());
            } else {
                currentBlock.registerVariable(name, new SimpleVariable(type, null));
            }
        }

    }
}
