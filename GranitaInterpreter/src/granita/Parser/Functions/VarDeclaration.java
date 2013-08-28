/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.Parser.Statements.Statement;
import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Utils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class VarDeclaration extends Statement {

    Type type;
    int scopeId;
    ArrayList<String> varNames;

    public VarDeclaration(Type type, ArrayList<String> varNames, int scopeId, int line) {
        super(line);
        this.type = type;
        this.scopeId = scopeId;
        this.varNames = varNames;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getScopeId() {
        return scopeId;
    }

    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
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
        SymbolTableNode current = SymbolTableTree.getInstance().getCurrentNode();
        for (String name : varNames) {
            if (current.findInThisTable(name) != null) {
                ErrorHandler.handle("already defined variable '" + name
                        + "': line " + this.getLine());
            } else {
                current.addEntry(name, new Variable(type, null));
            }
        }
    }
}
