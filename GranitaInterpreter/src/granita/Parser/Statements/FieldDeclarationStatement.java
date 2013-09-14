/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Semantic.DataLayout.ArrayVariable;
import granita.Semantic.DataLayout.SimpleVariable;
import granita.Interpreter.DataLayout.BoolVariable;
import granita.Interpreter.DataLayout.IntVariable;
import granita.Interpreter.DataLayout.RE_Variable;
import granita.Parser.FieldItems.ArrayField;
import granita.Parser.FieldItems.Field;
import granita.Semantic.SymbolTable.SymbolTableEntry;
import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granita.Semantics.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class FieldDeclarationStatement extends DeclarationStatement {

    private ArrayList<Field> declarations;
    Type type;

    public FieldDeclarationStatement(Type type, int line) {
        super(line);
        this.type = type;
        this.declarations = new ArrayList<Field>();
    }

    public void addDeclaration(Field st) {
        this.declarations.add(st);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public ArrayList<Field> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(ArrayList<Field> declarations) {
        this.declarations = declarations;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    //</editor-fold>    

    @Override
    public String toString() {
        String fd = type + " ";
        for (int i = 0; i < declarations.size() - 1; i++) {
            fd += declarations.get(i).toString() + ",";
        }
        fd += declarations.get(declarations.size() - 1).toString();
        return fd;
    }

    @Override
    public void register() throws GranitaException {
        SymbolTableNode node = SymbolTableTree.getInstance().getGlobal();

        for (Field f : this.declarations) {
            SymbolTableEntry v = node.findInThisTable(f.getFieldName());
            if (v != null) {
                ErrorHandler.handle("already defined variable '" + f.getFieldName()
                        + "': line " + f.getLine());
            } else {
                if (f instanceof ArrayField) {
                    ArrayField af = (ArrayField) f;
                    node.addEntry(f.getFieldName(), new ArrayVariable(type, af.getSize()));
                    if (this.type instanceof BoolType) {
                        SemanticUtils.getInstance().addVariableRE(
                                new granita.Interpreter.DataLayout.ArrayVariable(
                                RE_Variable.Type.BOOL_VARIABLE, af.getFieldName(),
                                af.getSize().getValue()));
                    } else {
                        SemanticUtils.getInstance().addVariableRE(
                                new granita.Interpreter.DataLayout.ArrayVariable(
                                RE_Variable.Type.INT_VARIABLE, af.getFieldName(),
                                af.getSize().getValue()));
                    }
                } else {
                    node.addEntry(f.getFieldName(), new SimpleVariable(type, false));
                    if (this.type instanceof BoolType) {
                        SemanticUtils.getInstance().addVariableRE(new BoolVariable(f.getFieldName(), false));
                    } else {
                        SemanticUtils.getInstance().addVariableRE(new IntVariable(f.getFieldName(), 0));
                    }
                }
            }
        }
    }
}
