/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Functions.ParameterDeclaration;
import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.SymbolTableValue;
import granita.Semantic.Types.Type;
import granita.Semantic.Types.VoidType;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodDeclarationStatement extends Statement {

    private String identifier;
    private ArrayList<ParameterDeclaration> parameters;
    private BlockStatement block;
    private Type type;
    private SymbolTableNode paramsEntry;

    public MethodDeclarationStatement(Type type, String identifier, int line) {
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

    public BlockStatement getBlock() {
        return block;
    }

    public void setBlock(BlockStatement block) {
        this.block = block;
    }

    public void addParameter(ParameterDeclaration param) {
        this.parameters.add(param);
    }

    public boolean isMain() {
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

    public void initialize() throws GranitaException {
        SymbolTableNode root = SymbolTableTree.getInstance().getRoot();
        SymbolTableValue val = root.getFunction(identifier);
        if (val == null) {
            root.addFunction(identifier, new Function(type, this.block));
        } else {
            ErrorHandler.handle("function '" + identifier + "' is already defined:"
                    + " line " + this.getLine());
        }

        SymbolTableNode parent = SymbolTableTree.getInstance().getParentNode();
        SymbolTableTree.getInstance().setCurrentNode(new SymbolTableNode(parent));
        SemanticUtils.getInstance().setCurrentBlock(block);

        for (ParameterDeclaration st : parameters) {
            st.validateSemantics();
        }
        this.paramsEntry = SymbolTableTree.getInstance().getCurrentNode();

        SymbolTableTree.getInstance().setCurrentNode(parent);
        SemanticUtils.getInstance().setCurrentBlock(block);
    }

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
    public void execute() throws GranitaException {
        for (Statement st : this.block.getStatements()) {
            st.execute();
        }
    }
}
