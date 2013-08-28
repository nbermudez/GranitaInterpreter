/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.Parser.Statements.Statement;
import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ParameterDeclaration extends Statement {

    Type type;
    String name, methodName;
    int scopeId;

    public ParameterDeclaration(int scopeId, int line) {
        super(line);
        this.scopeId = scopeId;
    }

    public ParameterDeclaration(Type type, String name,
            String methodName, int scopeId, int line) {
        super(line);
        this.type = type;
        this.scopeId = scopeId;
        this.name = name;
        this.methodName = methodName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScopeId() {
        return scopeId;
    }

    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        SymbolTableNode current = SymbolTableTree.getInstance().getCurrentNode();
        
        Function f = (Function)SymbolTableTree.getInstance().lookupFromCurrent(methodName);
        f.getParameters().add(new Variable(type, null));
        
        if (current.findInThisTable(name) != null) {
            ErrorHandler.handle("duplicated parameter '" + name + "': line " + this.getLine());
        } else {            
            current.addEntry(name, new Variable(type, null));
        }
    }
}
