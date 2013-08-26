/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.VoidType;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Utils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ClassStatement extends Statement {

    private ArrayList<Statement> fieldDecls;
    private ArrayList<Statement> methodDecls;
    private String id;

    public ClassStatement(String id, int line) {
        super(line);
        fieldDecls = new ArrayList<Statement>();
        methodDecls = new ArrayList<Statement>();
        this.id = id;
    }

    public void addFieldDeclaration(Statement fieldDec) {
        this.fieldDecls.add(fieldDec);
    }

    public void addMethodDeclaration(Statement method) {
        this.methodDecls.add(method);
    }

    public ArrayList<Statement> getFieldDecls() {
        return fieldDecls;
    }

    public void setFieldDecls(ArrayList<Statement> fieldDecls) {
        this.fieldDecls = fieldDecls;
    }

    public ArrayList<Statement> getMethodDecls() {
        return methodDecls;
    }

    public void setMethodDecls(ArrayList<Statement> methodDecls) {
        this.methodDecls = methodDecls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String cl = "class " + id + "{\n";
        for (Statement s : fieldDecls) {
            if (s != null) {
                cl += "\t" + s.toString() + ";\n";
            }
        }
        for (Statement s : methodDecls) {
            cl += "\t" + s.toString() + "\n";
        }
        cl += "}";
        return cl;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        SymbolTableNode root = SymbolTableTree.getInstance().getRoot();

        for (Statement st : fieldDecls) {
            st.validateSemantics();
        }

        SymbolTableTree.getInstance().setParentNode(root);

        MethodDeclarationStatement main;
        for (Statement st : methodDecls) {
            if (((MethodDeclarationStatement) st).isMain()) {
                main = (MethodDeclarationStatement) st;
                if (main == null) {
                    ErrorHandler.handle("class must contain a method 'main': line "
                            + st.getLine());
                } else {
                    if (!main.getType().equivalent(new VoidType())) {
                        ErrorHandler.handle("'main' method must be void: line "
                                + st.getLine());
                    }
                    if (!main.getParameters().isEmpty()) {
                        ErrorHandler.handle("'main' method cannot have parameters "
                                + ": line " + st.getLine());
                    }
                }
            }
            st.validateSemantics();
        }
        for (Statement statement : methodDecls) {            
            main = (MethodDeclarationStatement) statement;
            
            SymbolTableNode parent = SymbolTableTree.getInstance().getParentNode();
            SymbolTableTree.getInstance().setCurrentNode(main.getParamsEntry());
            
            Utils.getInstance().setFirstBlockInMethod(true);
            main.getBlock().validateSemantics();
            
            SymbolTableTree.getInstance().setCurrentNode(parent);
        }
    }
}
