/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic;

import granita.Parser.Expressions.Expression;
import granita.Parser.FieldItems.Field;
import granita.Parser.Functions.ParameterDeclaration;
import granita.Parser.Functions.VarDeclaration;
import granita.Parser.Statements.BlockStatement;
import granita.Parser.Statements.ClassStatement;
import granita.Parser.Statements.FieldDeclarationStatement;
import granita.Parser.Statements.InitializedFieldDeclarationStatement;
import granita.Parser.Statements.MethodDeclarationStatement;
import granita.Parser.Statements.ReturnStatement;
import granita.Parser.Statements.Statement;
import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SymbolTable;
import granita.Semantic.SymbolTable.SymbolTableValue;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.GranitaException;
import java.util.HashMap;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SemanticAnalysis {
    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">

    private ClassStatement program;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public SemanticAnalysis(ClassStatement program) {
        this.program = program;
    }
    //</editor-fold>

    public ClassStatement analyze() throws GranitaException {
        validateMain();
        validateFieldDeclarations();
        validateMethodDeclarations();
        SymbolTable st = SymbolTable.getInstance();
        return this.program;
    }

    private void validateMain() throws GranitaException {
        if (this.program.getMethodDecls().isEmpty()) {
            throw new GranitaException("Class must contain a void main method.");
        } else {
            for (Statement st : this.program.getMethodDecls()) {
                MethodDeclarationStatement s = (MethodDeclarationStatement) st;
                if (s.getIdentifier().equals("main")) {
                    if (s.getParameters().size() > 0) {
                        throw new GranitaException("main method cannot have parameters.");
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private void validateFieldDeclarations() throws GranitaException {
        for (Statement st : this.program.getFieldDecls()) {
            if (st instanceof FieldDeclarationStatement) {
                FieldDeclarationStatement fst = (FieldDeclarationStatement) st;
                
                for (Field f : fst.getDeclarations()){
                    addFieldToGlobalSymbolTable(f.getFieldName(), fst.getType(), null);
                }
            } else if (st instanceof InitializedFieldDeclarationStatement) {
                InitializedFieldDeclarationStatement ist = (InitializedFieldDeclarationStatement) st;
                
                addFieldToGlobalSymbolTable(ist.getFieldName(), ist.getType(), ist.getInitValue());
            }
        }
    }

    private void validateMethodDeclarations() throws GranitaException {
        for (Statement st : this.program.getMethodDecls()){
            if (st instanceof MethodDeclarationStatement){
                MethodDeclarationStatement method = (MethodDeclarationStatement) st;
                
                HashMap<String, Variable> params = new HashMap<String, Variable>();
                HashMap<String, Variable> localSymbolTable = new HashMap<String, Variable>();
                
                for (ParameterDeclaration param : method.getParameters()){
                    Variable var = new Variable(param.getType(), null);
                    params.put(param.getName(), var);
                }
                
                Function func = new Function(method.getType(), localSymbolTable, params);
                addFunctionToGlobalSymbolTable(method.getIdentifier(), func);
                
                validateMethod(method.getIdentifier(), localSymbolTable, params);
            }
        }
    }
    
    private void validateMethod(String methodName, HashMap<String, Variable> local, 
            HashMap<String, Variable> params) throws GranitaException{
        //semantica dentro de la funcion, en extremo complicado por los scopes
        BlockStatement block = getBlock(methodName);
        for (Statement st : block.getStatements()){
            if (st instanceof BlockStatement){
                //aqui es lo complicado
            } else if (st instanceof VarDeclaration) {
                VarDeclaration vd = (VarDeclaration) st;
                for (String varName : vd.getVarNames()){
                    Variable var = new Variable(vd.getType(), null);
                    local.put(varName, var);
                }
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Helper methods">
    private void addFieldToGlobalSymbolTable(String fieldName,
            Type type, Expression value) throws GranitaException {

        SymbolTableValue val = SymbolTable.getInstance().getEntry(fieldName);
        if (val != null) {
            throw new GranitaException("field " + fieldName + " is already defined");
        } else {
            SymbolTable.getInstance().addEntry(fieldName, new Variable(type, value));
        }
    }

    private void addFunctionToGlobalSymbolTable(String function, Function f)
            throws GranitaException {

        SymbolTableValue val = SymbolTable.getInstance().getEntry(function);
        if (val != null) {
            throw new GranitaException("method " + function + " is already defined");
        } else {
            SymbolTable.getInstance().addEntry(function, f);
        }
    }

    private void addToLocalTable(String method, String varName,
            Type type, Expression value) throws GranitaException {

        SymbolTableValue val = SymbolTable.getInstance().getEntry(method);
        if (val != null) {
            Function func = (Function) val;
            if (func.getSymbolTableEntry(varName) != null) {
                throw new GranitaException("variable " + varName
                        + " is already defined in method " + method);
            } else {
                func.addSymbolTableEntry(varName, new Variable(type, value));
            }
        } else {
            throw new GranitaException("method " + method + " is not defined!");
        }
    }

    private boolean hasReturn(BlockStatement block) {
        for (Statement st : block.getStatements()) {
            if (st instanceof ReturnStatement) {
                return true;
            }
        }
        return false;
    }

    private BlockStatement getBlock(String methodName) {
        for (Statement st : this.program.getMethodDecls()) {
            MethodDeclarationStatement mds = (MethodDeclarationStatement) st;
            if (mds.getIdentifier().equals(methodName)) {
                return (BlockStatement) mds.getBlock();
            }
        }
        return null;
    }
    //</editor-fold>
}
