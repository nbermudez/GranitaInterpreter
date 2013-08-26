/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Functions.ParameterDeclaration;
import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.Type;
import granita.Semantic.Types.VoidType;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodDeclarationStatement extends Statement {
    private String identifier;
    private ArrayList<ParameterDeclaration> parameters;
    private Statement block;
    private Type type;
    private SymbolTableNode paramsEntry;
    
    public MethodDeclarationStatement(Type type, String identifier, int line){
        super(line);
        this.type = type;
        this.identifier = identifier;
        this.parameters = new ArrayList<ParameterDeclaration>();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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
    
    public boolean isMain(){
        return this.identifier.equals("main");
    }

    public SymbolTableNode getParamsEntry() {
        return paramsEntry;
    }

    public void setParamsEntry(SymbolTableNode paramsEntry) {
        this.paramsEntry = paramsEntry;
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
        SymbolTableNode root = SymbolTableTree.getInstance().getRoot();
        root.addEntry(identifier, new Function(type));
        
        SymbolTableNode parent = SymbolTableTree.getInstance().getParentNode();
        SymbolTableTree.getInstance().setCurrentNode(new SymbolTableNode(parent));
        
        for (ParameterDeclaration st : parameters){
            st.validateSemantics();
        }
        this.paramsEntry = SymbolTableTree.getInstance().getCurrentNode();
        
        
        
        if (!this.type.equivalent(new VoidType()) &&
            this.block.hasReturn(this.type) == null) {
            ErrorHandler.handle("missing return statement in method '" + identifier
                    + "': line " + this.line);
        }
        SymbolTableTree.getInstance().setCurrentNode(parent);
    }
    
}
