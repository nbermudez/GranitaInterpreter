/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.SymbolTable;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SymbolTableTree {
    private static SymbolTableTree instance = null;
    private SymbolTableNode root = null;
    
    private SymbolTableTree(){
        this.root = new SymbolTableNode(null);
    }
    
    public static SymbolTableTree getInstance(){
        if (instance == null){
            instance = new SymbolTableTree();
        }
        return instance;
    }

    public SymbolTableNode getGlobal() {
        return root;
    }

    public void setGlobal(SymbolTableNode root) {
        this.root = root;
    }
    
    public SymbolTableEntry lookupFunction(String functionName) {
        return this.root.getFunction(functionName);
    }    
}
