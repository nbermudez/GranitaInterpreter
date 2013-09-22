/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Semantic.DataLayout.Function;
import granita.IR.Statements.D_Block;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.VoidType;
import granita.Misc.ErrorHandler;
import granita.Misc.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
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
    public D_Block getIR() {
        //<editor-fold defaultstate="collapsed" desc="Register fields">
        for (Statement statement : fieldDecls) {
            try {
                ((DeclarationStatement) statement).register();
            } catch (GranitaException ex) {
                return null;
            }
        }
        //</editor-fold>        

        //<editor-fold defaultstate="collapsed" desc="Register methods">
        boolean mainFound = false;
        for (Statement statement : methodDecls) {
            try {
                MethodDeclarationStatement method = (MethodDeclarationStatement) statement;
                if (method.isMain()) {
                    mainFound = true;
                    if (!method.getType().equivalent(new VoidType())) {
                        ErrorHandler.handle("'main' method must be void: line " + method.getLine());
                    }
                    if (!method.getParameters().isEmpty()) {
                        ErrorHandler.handle("'main' method cannot have parameters: line " + method.getLine());
                    }
                }
                method.register();
            } catch (GranitaException ex) {
                return null;
            }
        }
        //</editor-fold>

        if (!mainFound) {
            ErrorHandler.handle("class must contain a method 'main'");
            return null;
        } else {
            for (Statement statement : methodDecls) {
                MethodDeclarationStatement method = (MethodDeclarationStatement) statement;
                method.checkBody();
            }

            Function f = (Function) SymbolTableTree.getInstance().lookupFunction("main");
            return f.getBody();
        }
    }
}
