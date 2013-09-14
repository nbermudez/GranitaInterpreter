/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.Functions.D_Argument;
import granita.IR.Statements.D_Print;
import granita.IR.Statements.D_Statement;
import granita.Parser.Functions.Argument;
import granita.Semantics.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class PrintStatement extends Statement {
    
    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    ArrayList<Argument> arguments;
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public PrintStatement(int line) {
        super(line);
        arguments = new ArrayList<Argument>();
    }

    public PrintStatement(ArrayList<Argument> arguments, int line) {
        super(line);
        this.arguments = arguments;
    }
    //</editor-fold>    

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
    public D_Statement getIR() {
        checkForUnreachableStatement();
        
        ArrayList<D_Argument> args = new ArrayList<D_Argument>();
        
        SemanticUtils.getInstance().setInsidePrint(true);
        for (Argument arg : arguments) {
            D_Argument dArg = arg.getIR();
            if (dArg != null) {
                args.add(dArg);
            }
        }
        SemanticUtils.getInstance().setInsidePrint(false);
        
        return new D_Print(args);
    }
    
}
