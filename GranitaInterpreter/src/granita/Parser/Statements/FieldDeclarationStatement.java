/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.FieldItems.ArrayField;
import granita.Parser.FieldItems.Field;
import granita.Semantic.SymbolTable.ArrayVariable;
import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.SymbolTableValue;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class FieldDeclarationStatement extends Statement {

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
    public void validateSemantics() throws GranitaException {
        SymbolTableNode node = SymbolTableTree.getInstance().getRoot();

        for (Field f : this.declarations) {
            SymbolTableValue v = SymbolTableTree.getInstance().lookupFromCurrent(f.getFieldName());
            if (v != null) {
                ErrorHandler.handle("already defined variable " + f.getFieldName()
                        + ": line " + f.getLine());
            } else {
                if (f instanceof ArrayField) {
                    ArrayField af = (ArrayField) f;
                    node.addEntry(f.getFieldName(), new ArrayVariable(type, af.getSize()));
                } else {
                    node.addEntry(f.getFieldName(), new Variable(type, null));
                }
            }
        }
    }
}