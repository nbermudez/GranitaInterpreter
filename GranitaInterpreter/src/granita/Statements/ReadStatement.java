/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.LeftValues.LeftValue;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ReadStatement extends Statement {
    
    ArrayList<LeftValue> leftValues;

    public ReadStatement(int line) {
        super(line);
        leftValues = new ArrayList<LeftValue>();
    }

    public ReadStatement(ArrayList<LeftValue> leftValues, int line) {
        super(line);
        this.leftValues = leftValues;
    }

    @Override
    public String toString() {
        String read = "read ";
        for (int i = 0; i< leftValues.size() - 1 ; i++){
            read += leftValues.get(i).toString() + ",";
        }
        read += leftValues.get(leftValues.size() - 1).toString() + ";";
        return read;
    }

    public ArrayList<LeftValue> getLeftValues() {
        return leftValues;
    }

    public void setLeftValues(ArrayList<LeftValue> leftValues) {
        this.leftValues = leftValues;
    }
    
}
