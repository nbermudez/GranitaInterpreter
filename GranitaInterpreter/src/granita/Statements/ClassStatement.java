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
}
