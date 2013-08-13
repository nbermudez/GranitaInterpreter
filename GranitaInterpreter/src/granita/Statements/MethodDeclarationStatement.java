/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granitainterpreter.AstNode;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodDeclarationStatement extends Statement {
    String type, identifier;
    ArrayList<AstNode> parameters;
    AstNode block;
    
    public MethodDeclarationStatement(String type, String identifier){
        this.type = type;
        this.identifier = identifier;
        this.parameters = new ArrayList<AstNode>();
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

    public ArrayList<AstNode> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<AstNode> parameters) {
        this.parameters = parameters;
    }

    public AstNode getBlock() {
        return block;
    }

    public void setBlock(AstNode block) {
        this.block = block;
    }
    
    
}
