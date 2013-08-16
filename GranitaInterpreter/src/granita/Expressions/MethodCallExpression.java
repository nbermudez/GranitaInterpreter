/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Expressions;

import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodCallExpression extends Expression {
    
    ArrayList<Expression> arguments;
    String id;
    
    public MethodCallExpression(int line) {
        super(line);
    }

    public MethodCallExpression(String id, ArrayList<Expression> arguments, int line) {
        super(line);
        this.arguments = arguments;
        this.id = id;
    }
    
    @Override
    public String toString() {
        String t = id + "(";
        
        for (int i = 0; i< arguments.size() - 1; i++ ){
            t = t + arguments.get(i).toString() + ",";
        }
        if (arguments.size()>0){
            t = t + arguments.get(arguments.size() - 1).toString();
        }
        
        t = t + ")";
        return t;
    }
}
