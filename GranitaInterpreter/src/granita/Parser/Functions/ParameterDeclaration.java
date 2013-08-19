/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.Parser.Statements.Statement;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;



/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ParameterDeclaration extends Statement {
    Type type;
    String name;
    int scopeId;
    
    public ParameterDeclaration(int scopeId, int line) {
        super(line);
        this.scopeId = scopeId;
    }
    
    public ParameterDeclaration(Type type, String name, int scopeId, int line) {
        super(line);
        this.type = type;
        this.scopeId = scopeId;
        this.name = name;
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

    @Override
    public String toString() {
        return type + " " + name;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        System.out.println("Not supported yet.");
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
