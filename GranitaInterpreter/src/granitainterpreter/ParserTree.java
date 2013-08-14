/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import granita.Expressions.Add;
import granita.Expressions.And;
import granita.Expressions.Div;
import granita.Expressions.Eq;
import granita.Expressions.Expression;
import granita.Expressions.GreaterThan;
import granita.Expressions.GreaterThanEq;
import granita.Expressions.LessThan;
import granita.Expressions.LessThanEq;
import granita.Expressions.LitBool;
import granita.Expressions.LitInt;
import granita.Expressions.Mod;
import granita.Expressions.Mult;
import granita.Expressions.NotEq;
import granita.Expressions.Or;
import granita.Expressions.Rot;
import granita.Expressions.ShiftLeft;
import granita.Expressions.ShiftRight;
import granita.Expressions.Sub;
import granita.Functions.Parameter;
import granita.Statements.BlockStatement;
import granita.Statements.BreakStatement;
import granita.Statements.ClassStatement;
import granita.Statements.ContinueStatement;
import granita.Statements.IfStatement;
import granita.Statements.MethodDeclarationStatement;
import granita.Statements.ReturnStatement;
import granita.Statements.WhileStatement;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ParserTree {

    //<editor-fold defaultstate="collapsed" desc="Instance attributes">
    private Token currentToken;
    private Lexer lexer;
    private ClassStatement currentClass;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public ParserTree(String path) throws IOException {
        lexer = new Lexer(path);
    }

    public ParserTree(Lexer lexer) {
        this.lexer = lexer;
    }
    //</editor-fold>    

    public void parse() throws GranitaException {
        currentToken = lexer.nextToken();
        statements();
    }

    private ArrayList<AstNode> statements() throws GranitaException { 
        ArrayList<AstNode> programs = new ArrayList<AstNode>();
        if (currentToken != Token.TK_KW_CLASS) {
            throw new GranitaException("Expected class but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        }
        while (currentToken.type == Token.TokenType.KW_CLASS) {
            programs.add(P());
        }
        match(Token.TK_EOF, "end of file");
        return programs;
    }

    private void match(Token comp, String expected) throws GranitaException {
        if (currentToken.equals(comp)) {
            currentToken = lexer.nextToken();
        } else {
            throw new GranitaException("Expected " + expected + " but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Non-terminals">
    /**
     * P stands for Program. P -> class id { DECL_L }
     */
    private AstNode P() throws GranitaException {
        currentClass = new ClassStatement(lexer.lineNumber());
        
        match(Token.TK_KW_CLASS, "class");
        match(Token.TK_IDENTIFIER, "identifier");
        match(Token.TK_LEFT_CURLY_BRACKET, "{");
        DECL_L();
        match(Token.TK_RIGHT_CURLY_BRACKET, "}");
        
        return currentClass;
    }

    /**
     * DECL_L stands for Declaration List. DECL_L -> type id ((FD DECL_L) | (MD
     * DECL_Lp)) |void id MD DECL_Lp
     */
    private void DECL_L() throws GranitaException {
        String id;
        if (currentToken == Token.TK_KW_VOID) {
            currentToken = lexer.nextToken();
            id = currentToken.lexeme;
            match(Token.TK_IDENTIFIER, "identifier");
            currentClass.addMethodDeclaration(MD("void", id));
            DECL_Lp();
        } else if (is_type(currentToken)) {
            String type;
            if (currentToken == Token.TK_KW_BOOL) {
                type = "bool";
            } else {
                type = "int";
            }
            currentToken = lexer.nextToken();
            id = currentToken.lexeme;
            match(Token.TK_IDENTIFIER, "identifier");
            if (currentToken == Token.TK_LEFT_PARENTHESIS) {
                currentClass.addMethodDeclaration(MD(type, id));
                DECL_Lp();
            } else if (currentToken == Token.TK_SEMICOLON
                    || currentToken == Token.TK_COLON
                    || currentToken == Token.TK_LEFT_BRACKET
                    || currentToken == Token.TK_OP_EQ) {
                currentClass.addFieldDeclaration(FD(type, id));
                DECL_L();
            }
        } else { /* Nada por epsilon */ }
    }

    /**
     * DECL_Lp stands for declaration list prime. DECL_Lp -> (type | void) id MD
     * DECL_Lp | e
     */
    private void DECL_Lp() throws GranitaException {
        String type;
        if (is_type(currentToken) || currentToken == Token.TK_KW_VOID) {
            type = get_type(currentToken);
        } else {
            return;
        }
        currentToken = lexer.nextToken();
        String id = currentToken.lexeme;
        match(Token.TK_IDENTIFIER, "identifier");
        currentClass.addMethodDeclaration(MD(type, id));
        DECL_Lp();
    }

    /**
     * MD stands for Method Declaration. MD -> '(' (type id (, type id)* | e )
     * ')' BLOCK
     */
    private AstNode MD(String type, String id) throws GranitaException {
        MethodDeclarationStatement mds = new MethodDeclarationStatement(type, id, lexer.lineNumber());
        
        match(Token.TK_LEFT_PARENTHESIS, "(");
        String paramType, paramId;
        if (is_type(currentToken)) {
            paramType = get_type(currentToken);

            currentToken = lexer.nextToken();
            paramId = currentToken.lexeme;
            
            Parameter param = new Parameter(paramType, paramId, lexer.lineNumber());
            mds.addParameter(param);

            match(Token.TK_IDENTIFIER, "identifier");
            
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                if (is_type(currentToken)) {
                    paramType = get_type(currentToken);

                    currentToken = lexer.nextToken();
                    paramId = currentToken.lexeme;
                    
                    param = new Parameter(paramType, paramId, lexer.lineNumber());
                    mds.addParameter(param);

                    match(Token.TK_IDENTIFIER, "identifier");
                } else {
                    throw new GranitaException("Expected argument after , but found "
                            + currentToken.lexeme + " in line " + this.lexer.lineNumber());
                }
            }
        } else { /*Nada por el epsilon*/ }
        match(Token.TK_RIGHT_PARENTHESIS, ")");
        mds.setBlock(BLOCK());
        return mds;
    }

    /**
     * FD stands for Field Declaration.
     */
    private AstNode FD(String type, String id) throws GranitaException {
        if (currentToken == Token.TK_SEMICOLON) {
            currentToken = lexer.nextToken();
        } else if (currentToken == Token.TK_OP_EQ) {
            currentToken = lexer.nextToken();
            AstNode constant = CONSTANT();
            match(Token.TK_SEMICOLON, ";");
        } else if (currentToken == Token.TK_LEFT_BRACKET) {
            currentToken = lexer.nextToken();
            String arraySize = currentToken.lexeme;

            match(Token.TK_INT_CONSTANT, "integer");
            match(Token.TK_RIGHT_BRACKET, "]");
            FD_L(type);
        } else if (currentToken == Token.TK_COLON) {
            FD_L(type);
        }
        return null;
    }

    private void FD_L(String fieldType) throws GranitaException {
        String fieldId;
        while (currentToken == Token.TK_COLON) {
            currentToken = lexer.nextToken();

            fieldId = currentToken.lexeme;
            match(Token.TK_IDENTIFIER, "identifier");
            if (currentToken == Token.TK_LEFT_BRACKET) {
                currentToken = lexer.nextToken();
                String arraySize = currentToken.lexeme;

                match(Token.TK_INT_CONSTANT, "integer");
                match(Token.TK_RIGHT_BRACKET, "]");
            }
        }
        match(Token.TK_SEMICOLON, ";");
    }

    private Expression CONSTANT() throws GranitaException {
        if (currentToken.equals(Token.TK_CHAR_CONSTANT) 
                || currentToken.equals(Token.TK_INT_CONSTANT)){
            LitInt integer = new LitInt(currentToken.lexeme, lexer.lineNumber());
            currentToken = lexer.nextToken();
            return integer;
        }else if (currentToken.equals(Token.TK_KW_FALSE) 
                || currentToken.equals(Token.TK_KW_TRUE)){
            LitBool bool = new LitBool(currentToken.lexeme, lexer.lineNumber());
            currentToken = lexer.nextToken();
            return bool;
        }
        return null;
    }

    /**
     * BLOCK represents a list of statements enclosed in curly brackets. BLOCK
     * -> '{' VD* STNT* '}'
     */
    private AstNode BLOCK() throws GranitaException {
        BlockStatement block = new BlockStatement(lexer.lineNumber());
        match(Token.TK_LEFT_CURLY_BRACKET, "{");
        while (is_type(currentToken)) {
            block.addStatement(VD());
        }
        while (is_start_of_statement(currentToken)) {
            block.addStatement(STNT());
        }
        match(Token.TK_RIGHT_CURLY_BRACKET, "}");
        
        return block;
    }

    /**
     * VD stands for Variable declaration. VD -> type id (, id)* ;
     */
    private AstNode VD() throws GranitaException {
        String type, id;
        if (is_type(currentToken)) {
            type = get_type(currentToken);

            currentToken = lexer.nextToken();
            id = currentToken.lexeme;

            match(Token.TK_IDENTIFIER, "identifier");
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                id = currentToken.lexeme;

                match(Token.TK_IDENTIFIER, "identifier");
            }
            match(Token.TK_SEMICOLON, ";");
        }
        return null;
    }

    private AstNode STNT() throws GranitaException {
        if (currentToken == Token.TK_LEFT_CURLY_BRACKET) {
            return BLOCK();
        } else if (currentToken == Token.TK_KW_CONTINUE) {
            AstNode c = new ContinueStatement(false, lexer.lineNumber());
            currentToken = lexer.nextToken();            
            match(Token.TK_SEMICOLON, ";");
            
            return c;
        } else if (currentToken == Token.TK_KW_BREAK){
            AstNode b = new BreakStatement(false, lexer.lineNumber());
            currentToken = lexer.nextToken();            
            match(Token.TK_SEMICOLON, ";");
            
            return b;
        }else if (currentToken == Token.TK_KW_RETURN) {
            currentToken = lexer.nextToken();
            AstNode exp = null;
            if (is_start_of_expr(currentToken)) {
                exp = EXPR();
            } else { /*Nada por el epsilon*/ }            
            match(Token.TK_SEMICOLON, ";");
            
            AstNode re = new ReturnStatement(false, exp, lexer.lineNumber());
            return re;
        } else if (currentToken == Token.TK_KW_FOR) {
            currentToken = lexer.nextToken();
            match(Token.TK_LEFT_PARENTHESIS, "(");
            EXPR();
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                EXPR();
            }
            match(Token.TK_SEMICOLON, ";");
            EXPR();
            match(Token.TK_SEMICOLON, ";");

            String id = currentToken.lexeme;
            match(Token.TK_IDENTIFIER, "identifier");

            ASSIGN(id);
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                id = currentToken.lexeme;

                match(Token.TK_IDENTIFIER, "identifier");
                ASSIGN(id);
            }
            match(Token.TK_RIGHT_PARENTHESIS, ")");
            BLOCK();
        } else if (currentToken == Token.TK_KW_WHILE) {
            currentToken = lexer.nextToken();
            match(Token.TK_LEFT_PARENTHESIS, "(");
            AstNode condition = EXPR();
            match(Token.TK_RIGHT_PARENTHESIS, ")");
            AstNode block = BLOCK();
            return new WhileStatement(condition, block, lexer.lineNumber());
        } else if (currentToken == Token.TK_KW_IF) {
            currentToken = lexer.nextToken();
            match(Token.TK_LEFT_PARENTHESIS, "(");
            AstNode conditional = EXPR();
            match(Token.TK_RIGHT_PARENTHESIS, ")");
            AstNode trueBlock = BLOCK(), falseBlock = null;
            if (currentToken == Token.TK_KW_ELSE) {
                currentToken = lexer.nextToken();
                falseBlock = BLOCK();
            } else { /*Nada por el epsilon*/ }
            return new IfStatement(conditional, trueBlock, falseBlock, lexer.lineNumber());
        } else if (currentToken.equals(Token.TK_IDENTIFIER)) {
            String id = currentToken.lexeme;
            currentToken = lexer.nextToken();
            if (currentToken == Token.TK_OP_EQ || currentToken == Token.TK_LEFT_BRACKET) {
                ASSIGN(id);
            } else if (currentToken == Token.TK_LEFT_PARENTHESIS) {
                MC(id);
            }
            match(Token.TK_SEMICOLON, ";");
        } else if (currentToken == Token.TK_KW_PRINT || currentToken == Token.TK_KW_READ) {
            AstNode smc = SMC();
            match(Token.TK_SEMICOLON, ";");
            return smc;
        } else {
            throw new GranitaException("Expected a statement but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        }
        return null;
    }

    private void ASSIGN(String id) throws GranitaException {
        if (currentToken == Token.TK_OP_EQ) {
            currentToken = lexer.nextToken();
            EXPR();
        } else if (currentToken == Token.TK_LEFT_BRACKET) {
            currentToken = lexer.nextToken();
            EXPR();
            match(Token.TK_RIGHT_BRACKET, "]");
            match(Token.TK_OP_EQ, "=");
            EXPR();
        } else {
            throw new GranitaException("Expected assignment but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        }
    }

    private AstNode EXPR() throws GranitaException {
        return E();
    }

    /**
     * SMC stands for Special Method Call. SMC -> print ARG (, ARG)* |read id
     * ([EXPR] | e)
     */
    private AstNode SMC() throws GranitaException {
        if (currentToken == Token.TK_KW_PRINT) {
            currentToken = lexer.nextToken();
            ARG();
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                ARG();
            }
        } else if (currentToken == Token.TK_KW_READ) {
            currentToken = lexer.nextToken();
            String id = currentToken.lexeme;

            match(Token.TK_IDENTIFIER, "identifier");
            if (currentToken == Token.TK_LEFT_BRACKET) {
                currentToken = lexer.nextToken();
                EXPR();
                match(Token.TK_RIGHT_BRACKET, "]");
            } else { /* Nada por el epsilon */ }
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();                
                id = currentToken.lexeme;
                match(Token.TK_IDENTIFIER, "identifier");
                if (currentToken == Token.TK_LEFT_BRACKET) {
                    currentToken = lexer.nextToken();
                    E();
                    match(Token.TK_RIGHT_BRACKET, "]");
                }
            }
        } else {
            throw new GranitaException("Expected print or read function but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        }return null;
    }

    /**
     * MC stands for Method Call.
     */
    private void MC(String id) throws GranitaException {
        match(Token.TK_LEFT_PARENTHESIS, "(");
        if (is_start_of_expr(currentToken)) {
            EXPR();
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                EXPR();
            }
        }
        match(Token.TK_RIGHT_PARENTHESIS, ")");
    }

    /**
     * ARG stands for Argument. ARG -> stringConstant |EXPR
     */
    private void ARG() throws GranitaException {
        if (is_start_of_expr(currentToken)) {
            EXPR();
        } else {
            match(Token.TK_STRING_CONSTANT, "string constant");
        }
    }

    //</editor-fold>  
    
    //<editor-fold defaultstate="collapsed" desc="Expressions with precendence">
    private Expression E() throws GranitaException {
        Expression left = F(), right;
        while (currentToken == Token.TK_OP_OR){
            currentToken = lexer.nextToken();
            right = F();
            left = new Or(left, right, lexer.lineNumber());
        }
        return left;
    }

    private Expression F() throws GranitaException {
        Expression left = G(), right;
        while (currentToken == Token.TK_OP_AND) {
            currentToken = lexer.nextToken();
            right = G();
            left = new And(left, right, lexer.lineNumber());
        }
        return left;
    }

    private Expression G() throws GranitaException {
        Expression left = H(), right;
        while (currentToken == Token.TK_REL_NEQ || currentToken == Token.TK_REL_EQ) {
            if (currentToken == Token.TK_REL_NEQ){
                currentToken = lexer.nextToken();
                right = H();
                left = new NotEq(left, right, lexer.lineNumber());
            }else{
                currentToken = lexer.nextToken();
                right = H();
                left = new Eq(left, right, lexer.lineNumber());
            }            
        }
        return left;
    }

    private Expression H() throws GranitaException {
        Expression left = I(), right;
        while (currentToken == Token.TK_REL_GT || currentToken == Token.TK_REL_GTE
                || currentToken == Token.TK_REL_LT || currentToken == Token.TK_REL_LTE){
            
            Token tmp = currentToken;
            currentToken = lexer.nextToken();
            
            right = I();
            if (tmp == Token.TK_REL_GT){
                left = new GreaterThan(left, right, lexer.lineNumber());
            }else if (tmp == Token.TK_REL_GTE){
                left = new GreaterThanEq(left, right, lexer.lineNumber());
            }else if (tmp == Token.TK_REL_LT){
                left = new LessThan(left, right, lexer.lineNumber());
            }else if (tmp == Token.TK_REL_LTE){
                left = new LessThanEq(left, right, lexer.lineNumber());
            }else{
                break;
            }
        }
        return left;
    }

    private Expression I() throws GranitaException {
        Expression left = J(), right;
        while (currentToken == Token.TK_OP_LEFT_SHIFT || currentToken == Token.TK_OP_RIGHT_SHIFT 
                || currentToken == Token.TK_KW_ROT){
            Token tmp = currentToken;
            currentToken = lexer.nextToken();
            
            right = J();
            if (tmp == Token.TK_OP_LEFT_SHIFT){
                left = new ShiftLeft(left, right, lexer.lineNumber());
            }else if (tmp == Token.TK_OP_RIGHT_SHIFT){
                left = new ShiftRight(left, right, lexer.lineNumber());
            }else if (tmp == Token.TK_KW_ROT){
                left = new Rot(left, right, lexer.lineNumber());
            }
        }
        return left;
    }
    
    private Expression J() throws GranitaException{
        Expression left = K(), right;
        while (currentToken == Token.TK_OP_MOD){
            currentToken = lexer.nextToken();
            
            right = K();
            left = new Mod(left, right, lexer.lineNumber());
        }
        return left;
    }
    
    private Expression K() throws GranitaException{
        Expression left = L(), right;
        while (currentToken == Token.TK_OP_ADD || currentToken == Token.TK_OP_SUB){
            if (currentToken == Token.TK_OP_ADD){
                currentToken = lexer.nextToken();
                
                right = L();
                left = new Add(left, right, lexer.lineNumber());
            }else {
                currentToken = lexer.nextToken();
                
                right = L();
                left = new Sub(left, right, lexer.lineNumber());
            }
        }
        return left;
    }
    
    private Expression L() throws GranitaException {
        Expression left = M(), right;
        while (currentToken == Token.TK_OP_DIV || currentToken == Token.TK_OP_MULT){
            if (currentToken == Token.TK_OP_DIV){
                currentToken = lexer.nextToken();
                right = M();
                
                left = new Div(left, right, lexer.lineNumber());
            }else {
                currentToken = lexer.nextToken();
                right = M();
                
                left = new Mult(left, right, lexer.lineNumber());
            }
            
        }
        return left;
    }
    
    private Expression M() throws GranitaException{
        Expression result = null;
        if (currentToken == Token.TK_LEFT_PARENTHESIS){
            currentToken = lexer.nextToken();
            result = E();
            match(Token.TK_RIGHT_PARENTHESIS, ")");
        }else if (currentToken == Token.TK_OP_NOT || currentToken == Token.TK_OP_SUB){
            currentToken = lexer.nextToken();
            result = E();
        }else if (currentToken == Token.TK_KW_READ || currentToken == Token.TK_KW_PRINT){
            SMC();
        }else if (currentToken.equals(Token.TK_IDENTIFIER)){
            String id = currentToken.lexeme;
            currentToken = lexer.nextToken();
            if (currentToken == Token.TK_LEFT_PARENTHESIS){
                MC(id);
            }else if (currentToken == Token.TK_LEFT_BRACKET) {
                currentToken = lexer.nextToken();
                E();
                match(Token.TK_RIGHT_BRACKET, "]");
            }
        }else if (is_constant(currentToken)){
            result = CONSTANT();
        }else {
            throw new GranitaException("Expected expression but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        }
        return result;
    }   

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Helper functions">
    private String get_type(Token current) {
        if (current == Token.TK_KW_BOOL) {
            return "bool";
        } else if (current == Token.TK_KW_INT) {
            return "int";
        } else if (current == Token.TK_KW_VOID){
            return "void";
        }else {
            return null;
        }
    }

    private boolean is_constant(Token current) {
        if (current.type == Token.TokenType.CHAR_CONSTANT
                || current.type == Token.TokenType.INT_CONSTANT
                || is_bool_constant(current)) {
            return true;
        }
        return false;
    }

    private boolean is_type(Token current) {
        if (current.type == Token.TokenType.KW_BOOL
                || current.type == Token.TokenType.KW_INT) {
            return true;
        }
        return false;
    }

    private boolean is_bin_op(Token current) {
        return is_arith_op(current) || is_rel_op(current) || is_eq_op(current) || is_cond_op(current);
    }

    private boolean is_arith_op(Token current) {
        if (current.type == Token.TokenType.OP_ADD
                || current.type == Token.TokenType.OP_SUB
                || current.type == Token.TokenType.OP_MULT
                || current.type == Token.TokenType.OP_DIV
                || current.type == Token.TokenType.OP_LEFT_SHIFT
                || current.type == Token.TokenType.OP_RIGHT_SHIFT
                || current.type == Token.TokenType.OP_MOD
                || current.type == Token.TokenType.KW_ROT) {
            return true;
        } else {
            return false;
        }
    }

    private boolean is_rel_op(Token current) {
        if (current.type == Token.TokenType.REL_LESS
                || current.type == Token.TokenType.REL_LESS_EQ
                || current.type == Token.TokenType.REL_GREATER
                || current.type == Token.TokenType.REL_GREATER_EQ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean is_eq_op(Token current) {
        if (current.type == Token.TokenType.REL_EQ
                || current.type == Token.TokenType.REL_NOT_EQ) {
            return true;
        } else {
            return false;
        }
    }

    private boolean is_cond_op(Token current) {
        if (current.type == Token.TokenType.OP_AND
                || current.type == Token.TokenType.OP_OR) {
            return true;
        } else {
            return false;
        }
    }

    private boolean is_bool_constant(Token current) {
        if (current.type == Token.TokenType.KW_TRUE
                || current.type == Token.TokenType.KW_FALSE) {
            return true;
        } else {
            return false;
        }
    }

    private boolean is_start_of_expr(Token current) {
        if (current.equals(Token.TK_OP_SUB)
                || current.equals(Token.TK_OP_NOT)
                || current.equals(Token.TK_KW_PRINT)
                || current.equals(Token.TK_KW_READ)
                || is_constant(currentToken)
                || current.equals(Token.TK_IDENTIFIER)
                || current.equals(Token.TK_LEFT_PARENTHESIS)) {
            return true;
        }
        return false;
    }

    private boolean is_start_of_statement(Token current) {
        if (current.equals(Token.TK_IDENTIFIER)
                || current == Token.TK_KW_IF
                || current == Token.TK_KW_WHILE
                || current == Token.TK_KW_FOR
                || current == Token.TK_KW_RETURN
                || current == Token.TK_KW_BREAK
                || current == Token.TK_KW_CONTINUE
                || current == Token.TK_LEFT_CURLY_BRACKET
                || current == Token.TK_KW_PRINT
                || current == Token.TK_KW_READ) {
            return true;
        }
        return false;
    }
    //</editor-fold>    
}
