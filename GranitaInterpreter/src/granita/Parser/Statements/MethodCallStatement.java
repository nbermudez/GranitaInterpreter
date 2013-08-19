/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodCallStatement extends Statement {

    ArrayList<Expression> params;
    String id;

    public MethodCallStatement(int line) {
        super(line);
    }

    public MethodCallStatement(String id, ArrayList<Expression> params, int line) {
        super(line);
        this.params = params;
        this.id = id;
    }

    @Override
    public String toString() {
        String t = id + "(";

        for (int i = 0; i < params.size() - 1; i++) {
            t = t + params.get(i).toString() + ",";
        }
        if (params.size() > 0) {
            t = t + params.get(params.size() - 1).toString();
        }

        t = t + ")";
        return t;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        for (Expression ex : params) {
            ex.validateSemantics();
        }
    }
}
