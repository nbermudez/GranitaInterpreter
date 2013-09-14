/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Semantic.DataLayout.Context;
import granita.Semantic.DataLayout.Function;
import granita.IR.Statements.D_Block;
import granita.Interpreter.DataLayout.BoolVariable;
import granita.Interpreter.DataLayout.IntVariable;
import granita.Interpreter.DataLayout.Procedure;
import granita.Parser.Functions.ParameterDeclaration;
import granita.Semantic.SymbolTable.SymbolTableEntry;
import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.ErrorType;
import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granita.Semantic.Types.VoidType;
import granita.Misc.ErrorHandler;
import granita.Misc.GranitaException;
import granita.Semantics.SemanticUtils;
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

        tmp = new Context();        
        
        if (!(type instanceof VoidType)) {
            tmp.initialize(parameters.size() + 1);
            if (type instanceof IntType) {
                tmp.add(tmp.getVariableIndex(), new IntVariable("retValue", 0));
            } else {
                tmp.add(tmp.getVariableIndex(), new BoolVariable("retValue", false));
            }
            
        } else {
            tmp.initialize(parameters.size());
        }
        
        SemanticUtils.getInstance().setTmpContext(tmp);
        for (ParameterDeclaration st : parameters) {
            st.register();
        }
        Procedure thisProc = new Procedure(identifier);
        SemanticUtils.getInstance().addProcedure(thisProc);
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
            D_Block dBlock = block.getIR();
            f.setBody(dBlock);
            Procedure proc = SemanticUtils.getInstance().findProcedure(identifier);
            proc.setBody(dBlock);
        }
        
        if (!this.getType().equivalent(new VoidType())) {
            SemanticUtils.getInstance().setExpectedReturnType(type);
            //checkear aki por returns en todos los path :S
            SemanticUtils.getInstance().setExpectedReturnType(null);
        }
    }
}
