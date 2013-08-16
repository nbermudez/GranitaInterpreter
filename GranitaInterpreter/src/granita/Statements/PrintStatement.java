/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.Expressions.Expression;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class PrintStatement extends Statement {
    ArrayList<Expression> arguments;
    
    public PrintStatement(int line) {
        super(line);
        arguments = new ArrayList<Expression>();
    }

    public PrintStatement(ArrayList<Expression> arguments, int line) {
        super(line);
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        String print = "print ";
        for (int i = 0; i< arguments.size() - 1 ; i++){
            print += arguments.get(i).toString() + ",";
        }
        print += arguments.get(arguments.size() - 1).toString() + ";";
        return print;
    }
    
}
