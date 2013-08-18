/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.Functions.ParameterDeclaration;
import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodDeclarationStatement extends Statement {
    String type, identifier;
    ArrayList<ParameterDeclaration> parameters;
    Statement block;
    
    public MethodDeclarationStatement(String type, String identifier, int line){
        super(line);
        this.type = type;
        this.identifier = identifier;
        this.parameters = new ArrayList<ParameterDeclaration>();
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

    public ArrayList<ParameterDeclaration> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<ParameterDeclaration> parameters) {
        this.parameters = parameters;
    }

    public Statement getBlock() {
        return block;
    }

    public void setBlock(Statement block) {
        this.block = block;
    }
    
    public void addParameter(ParameterDeclaration param){
        this.parameters.add(param);
    }

    @Override
    public String toString() {
        String method = type + " " + identifier + "(";
        for (int i = 0; i< parameters.size() - 1 ; i++){
            method += parameters.get(i).toString() + ",";
        }
        if (parameters.size()>0){
            method += parameters.get(parameters.size() - 1).toString();
        }
        method += ")";
        method += block.toString();
        return method;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
