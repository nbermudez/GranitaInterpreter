/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.DataLayout.SimpleVariable;
import granita.Parser.Expressions.Expression;
import granita.SymbolTable.SymbolTableEntry;
import granita.SymbolTable.SymbolTableNode;
import granita.SymbolTable.SymbolTableTree;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class InitializedFieldDeclarationStatement extends DeclarationStatement {

    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    Expression initValue;
    String fieldName;
    Type type;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public InitializedFieldDeclarationStatement(Type type,
            String fieldName, Expression initValue, int line) {
        super(line);
        this.initValue = initValue;
        this.fieldName = fieldName;
        this.type = type;
    }
    //</editor-fold>    

    @Override
    public String toString() {
        return type + " " + fieldName + " = " + initValue.toString();
    }

    @Override
    public void validateSemantics() throws GranitaException {
        /*SymbolTableNode node = SymbolTableTree.getInstance().getGlobal();
        Type result = this.initValue.validateSemantics();

        SymbolTableEntry v = node.findInThisTable(fieldName);
        if (v != null) {
            ErrorHandler.handle("already defined variable '" + fieldName
                    + "': line " + this.getLine());
        }
        if (!result.equivalent(type)) {
            ErrorHandler.handle("incompatible types, required " + type.toString()
                    + " and found " + result.toString() + ": line " + initValue.getLine());
        } else {
            type.setValue(result.getValue());
            if (v == null){
                //node.addEntry(fieldName, new SimpleVariable(type, initValue.getIR()));
            }
        }*/
    }

    public Expression getInitValue() {
        return initValue;
    }

    public void setInitValue(Expression initValue) {
        this.initValue = initValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void register() throws GranitaException {
        SymbolTableNode node = SymbolTableTree.getInstance().getGlobal();
        SymbolTableEntry v = node.findInThisTable(fieldName);
        if (v != null) {
            ErrorHandler.handle("already defined variable '" + fieldName
                        + "': line " + this.getLine());
        } else {
            node.addEntry(fieldName, new SimpleVariable(type, initValue.getIR()));
        }        
    }
}
