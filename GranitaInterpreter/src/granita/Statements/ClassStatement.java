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
    
    public ArrayList<Statement> fieldDecls;
    public ArrayList<Statement> methodDecls;
    
    public ClassStatement(int line){
        super(line);
        fieldDecls = new ArrayList<Statement>();
        methodDecls = new ArrayList<Statement>();
    }
    
    public void addFieldDeclaration(Statement fieldDec){
        this.fieldDecls.add(fieldDec);
    }
    
    public void addMethodDeclaration(Statement method){
        this.methodDecls.add(method);
    }
    
}
