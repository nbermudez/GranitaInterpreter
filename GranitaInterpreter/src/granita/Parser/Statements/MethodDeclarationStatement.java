/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.DataLayout.Context;
import granita.DataLayout.Function;
import granita.Parser.Functions.ParameterDeclaration;
import granita.SymbolTable.SymbolTableEntry;
import granita.SymbolTable.SymbolTableNode;
import granita.SymbolTable.SymbolTableTree;
import granita.Types.ErrorType;
import granita.Types.Type;
import granita.Types.VoidType;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodDeclarationStatement extends DeclarationStatement {

    //<editor-fold defaultstate="collapsed" desc="Instance attributes">
    private String identifier;
    private ArrayList<ParameterDeclaration> parameters;
    private BlockStatement block;
    private Type type;
    private SymbolTableNode paramsEntry;
    private Context tmp;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public MethodDeclarationStatement(Type type, String identifier, int line) {
        super(line);
        this.type = type;
        this.identifier = identifier;
        this.parameters = new ArrayList<ParameterDeclaration>();
    }
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
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

    public BlockStatement getBlock() {
        return block;
    }

    public void setBlock(BlockStatement block) {
        this.block = block;
    }
    
    public SymbolTableNode getParamsEntry() {
        return paramsEntry;
    }

    public void setParamsEntry(SymbolTableNode paramsEntry) {
        this.paramsEntry = paramsEntry;
    }
    //</editor-fold>    

    public void addParameter(ParameterDeclaration param) {
        this.parameters.add(param);
    }

    public boolean isMain() {
        return this.identifier.equals("main");
    }    

    @Override
    public String toString() {
        String method = type + " " + identifier + "(";
        for (int i = 0; i < parameters.size() - 1; i++) {
            method += parameters.get(i).toString() + ",";
        }
        if (parameters.size() > 0) {
            method += parameters.get(parameters.size() - 1).toString();
        }
        method += ")";
        method += block.toString();
        return method;
    }

    // TODO: eliminar esto cuando el getIR sea equivalente
    @Override
    public void validateSemantics() throws GranitaException {
        //<editor-fold defaultstate="collapsed" desc="Validate block">
        SemanticUtils.getInstance().setExpectedReturnType(this.type);

        if (this.getType().equivalent(new VoidType())) {
            SemanticUtils.getInstance().setMustReturnExpression(false);
        } else {
            SemanticUtils.getInstance().setMustReturnExpression(true);
        }
        SemanticUtils.getInstance().setCurrentBlock(block);
        this.block.validateSemantics();
        SemanticUtils.getInstance().setCurrentBlock(block.parentBlock);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Return type checks">
        if (!this.getType().equivalent(new VoidType())) {
            SemanticUtils.getInstance().setExpectedReturnType(this.type);

            Type hasReturn = this.getBlock().hasReturn(this.getType());
            if (hasReturn == null) {
                ErrorHandler.handle("missing return statement in method '"
                        + this.getIdentifier()
                        + "': line " + this.line);
            }

            SemanticUtils.getInstance().setExpectedReturnType(null);
        }
        //</editor-fold>

    }

    @Override
    public void register() throws GranitaException {
        SymbolTableNode root = SymbolTableTree.getInstance().getGlobal();
        SymbolTableEntry val = root.getFunction(identifier);
        if (val == null) {
            root.addFunction(identifier, new Function(type));
        } else {
            ErrorHandler.handle("function '" + identifier + "' is already defined:"
                    + " line " + this.getLine());
        }

        SemanticUtils.getInstance().setCurrentBlock(block);
        tmp = new Context();
        SemanticUtils.getInstance().setTmpContext(tmp);
        for (ParameterDeclaration st : parameters) {
            st.register();
        }
        SemanticUtils.getInstance().setCurrentBlock(block);
    }
    
    public void checkBody() {
        SymbolTableNode root = SymbolTableTree.getInstance().getGlobal();
        SymbolTableEntry val = root.getFunction(identifier);
        
        if (val == null) {
            ErrorHandler.handle("function '" + identifier + "' is not defined:"
                    + " line " + this.getLine());
        } else {
            Function f = (Function) val;
            SemanticUtils.getInstance().mustMergeWithTempContext(true);
            SemanticUtils.getInstance().setTmpContext(tmp);
            
            if (type instanceof VoidType || type instanceof ErrorType) {
                SemanticUtils.getInstance().setMustReturnExpression(false);
            } else {
                SemanticUtils.getInstance().setMustReturnExpression(true);
            }
            SemanticUtils.getInstance().setExpectedReturnType(type);
            f.setBody(block.getIR());
        }
        
        if (!this.getType().equivalent(new VoidType())) {
            SemanticUtils.getInstance().setExpectedReturnType(type);
            //checkear aki por returns en todos los path :S
            SemanticUtils.getInstance().setExpectedReturnType(null);
        }
    }
}
