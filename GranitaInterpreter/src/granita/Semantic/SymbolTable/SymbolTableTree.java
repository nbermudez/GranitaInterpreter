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
    private SymbolTableNode parentNode = null;
    private SymbolTableNode currentNode = null;
    
    private SymbolTableTree(){
        this.root = new SymbolTableNode(null);
        this.parentNode = null;
        this.currentNode = this.root;
    }
    
    public static SymbolTableTree getInstance(){
        if (instance == null){
            instance = new SymbolTableTree();
        }
        return instance;
    }

    public SymbolTableNode getRoot() {
        return root;
    }

    public void setRoot(SymbolTableNode root) {
        this.root = root;
    }

    public SymbolTableNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(SymbolTableNode parentNode) {
        this.parentNode = parentNode;
    }

    public SymbolTableNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(SymbolTableNode currentNode) {
        this.currentNode = currentNode;
        this.parentNode = currentNode;
    }
    
    public SymbolTableValue lookupFromCurrent(String name) {
        return this.currentNode.getEntry(name);
    }
    
    public SymbolTableValue lookupFunction(String functionName) {
        return this.currentNode.getFunction(functionName);
    }
}
