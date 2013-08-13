/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class GranitaInterpreter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Lexer lexer = new Lexer("C:/Users/Alejandro/Documents/GitHub/GranitaInterpreter/test_programs/program5.txt");
            //System.out.println(lexer.toString());
            
            NewParser parser = new NewParser(lexer);
            parser.parse();
            System.out.println("Analisis Sintactico exitoso");
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println(ex.getMessage());
        }
    }
}
