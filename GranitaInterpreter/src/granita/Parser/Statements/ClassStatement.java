/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ClassStatement extends Statement{
    
    private ArrayList<Statement> fieldDecls;
    private ArrayList<Statement> methodDecls;
    private String id;
    
    public ClassStatement(String id, int line){
        super(line);
        fieldDecls = new ArrayList<Statement>();
        methodDecls = new ArrayList<Statement>();
        this.id = id;
    }
    
    public void addFieldDeclaration(Statement fieldDec){
        this.fieldDecls.add(fieldDec);
    }
    
    public void addMethodDeclaration(Statement method){
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
        for (Statement s: fieldDecls){
            if (s != null){
                cl += "\t" + s.toString() + ";\n";
            }
        }
        for (Statement s: methodDecls){
            cl += "\t" +s.toString() + "\n";
        }
        cl += "}";
        return cl;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        SymbolTableNode root = SymbolTableTree.getInstance().getRoot();
        
        for (Statement st : fieldDecls){
            st.validateSemantics();
        }
        
        SymbolTableTree.getInstance().setParentNode(root);
        
        for (Statement st : methodDecls){
            st.validateSemantics();
        }
    }
}
