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
public class ClassStatement extends Statement{
    
    public ArrayList<AstNode> fieldDecls;
    public ArrayList<AstNode> methodDecls;
    
    public ClassStatement(){
        fieldDecls = new ArrayList<AstNode>();
        methodDecls = new ArrayList<AstNode>();
    }
    
    public void addFieldDeclaration(AstNode fieldDec){
        this.fieldDecls.add(fieldDec);
    }
    
    public void addMethodDeclaration(AstNode method){
        this.methodDecls.add(method);
    }
    
}
