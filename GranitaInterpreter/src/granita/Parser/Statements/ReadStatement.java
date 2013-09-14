/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.IR.LeftValues.D_LeftValue;
import granita.IR.Statements.D_Read;
import granita.IR.Statements.D_Statement;
import granita.Parser.LeftValues.LeftValue;
import granita.Misc.GranitaException;
import granita.Semantics.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ReadStatement extends Statement {
    
    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    ArrayList<LeftValue> leftValues;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public ReadStatement(int line) {
        super(line);
        leftValues = new ArrayList<LeftValue>();
    }

    public ReadStatement(ArrayList<LeftValue> leftValues, int line) {
        super(line);
        this.leftValues = leftValues;
    }
    //</editor-fold>    

    @Override
    public String toString() {
        String read = "read ";
        for (int i = 0; i< leftValues.size() - 1 ; i++){
            read += leftValues.get(i).toString() + ",";
        }
        read += leftValues.get(leftValues.size() - 1).toString();
        return read;
    }

    public ArrayList<LeftValue> getLeftValues() {
        return leftValues;
    }

    public void setLeftValues(ArrayList<LeftValue> leftValues) {
        this.leftValues = leftValues;
    }

    @Override
    public void execute() throws GranitaException {
        /*Scanner scanner = new Scanner(System.in);
        for (LeftValue leftValue : leftValues) {
            Type t = leftValue.getLocation();
            try {
                if (t instanceof IntType) {
                    Integer readValue = scanner.nextInt();
                    t.setValue(readValue);
                } else if (t instanceof BoolType) {
                    Boolean boolValue = scanner.nextBoolean();
                    t.setValue(boolValue);
                }
            } catch (Exception ex) {
                ErrorHandler.handleFatalError("incompatible types, expected "+ t
                        + ": line " + line);
            }
        }*/
    }

    @Override
    public D_Statement getIR() {
        checkForUnreachableStatement();
        
        ArrayList<D_LeftValue> variables = new ArrayList<D_LeftValue>();
        SemanticUtils.getInstance().setInsideRead(true);
        for (LeftValue lValue : this.leftValues) {
            D_LeftValue dLvalue = lValue.getIR();
            if (dLvalue != null) {
                variables.add(dLvalue);
            }
        }
        SemanticUtils.getInstance().setInsideRead(false);
        
        return new D_Read(variables);
    }
    
}
