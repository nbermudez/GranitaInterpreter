/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Tests;

import granita.Parser.Statements.BlockStatement;
import granita.Parser.Statements.IfStatement;
import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SimpleVariable;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.IntType;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Basic {
    public static void main(String args[]) {
        Function f = new Function(new BoolType(true));
        BlockStatement block = new BlockStatement(1);
        block.addStatement(new IfStatement(1));
        block.registerVariable("test", new SimpleVariable(new IntType(), null));
        f.setBlock(block);
        if (f == null) {
            System.out.println("error");
        } else {
            Function f2 = f.getCopy();
            System.out.println(f2.getVariable("test"));
            System.out.println(f.getVariable("test"));
        }
    }
}
