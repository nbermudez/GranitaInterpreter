/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodDeclarationStatement extends Statement {
    String type, identifier;
    ArrayList<Statement> parameters;
    Statement block;
    
    public MethodDeclarationStatement(String type, String identifier, int line){
        super(line);
        this.type = type;
        this.identifier = identifier;
        this.parameters = new ArrayList<Statement>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ArrayList<Statement> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Statement> parameters) {
        this.parameters = parameters;
    }

    public Statement getBlock() {
        return block;
    }

    public void setBlock(Statement block) {
        this.block = block;
    }
    
    public void addParameter(Statement param){
        this.parameters.add(param);
    }
    
    
}
