/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Expressions.D_Expression;
import granita.IR.Functions.D_Argument;
import granita.IR.Statements.D_Print;
import granita.IR.Statements.D_Statement;
import granita.Parser.Functions.Argument;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class PrintStatement extends Statement {
    ArrayList<Argument> arguments;
    
    public PrintStatement(int line) {
        super(line);
        arguments = new ArrayList<Argument>();
    }

    public PrintStatement(ArrayList<Argument> arguments, int line) {
        super(line);
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        String print = "print ";
        for (int i = 0; i< arguments.size() - 1 ; i++){
            print += arguments.get(i).toString() + ",";
        }
        print += arguments.get(arguments.size() - 1).toString();
        return print;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        /*super.validateSemantics();
        
        SemanticUtils.getInstance().setInsidePrint(true);
        for (Argument st : arguments){
            st.validateSemantics();
        }
        SemanticUtils.getInstance().setInsidePrint(false);*/
    }

    @Override
    public D_Statement getIR() {
        checkForUnreachableStatement();
        
        ArrayList<D_Argument> args = new ArrayList<D_Argument>();
        
        SemanticUtils.getInstance().setInsidePrint(true);
        for (Argument arg : arguments) {
            args.add(arg.getIR());
        }
        SemanticUtils.getInstance().setInsidePrint(false);
        
        return new D_Print(args);
    }
    
}
