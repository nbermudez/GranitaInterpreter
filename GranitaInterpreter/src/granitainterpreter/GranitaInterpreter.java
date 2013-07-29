/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import java.io.IOException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class GranitaInterpreter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Parser parser = new Parser("C:/Users/Alejandro/Documents/GitHub/GranitaInterpreter/test_programs/program4.txt");
            parser.parse();
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
    }
}
