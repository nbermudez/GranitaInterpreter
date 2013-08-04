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
public class Parser {

    //<editor-fold defaultstate="collapsed" desc="Instance attributes">
    private Token currentToken;
    private Lexer lexer;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Parser(String path) throws IOException {
        lexer = new Lexer(path);
    }

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }
    //</editor-fold>    

    public void parse() throws GranitaException {
        currentToken = lexer.nextToken();
        statements();
    }

    private void statements() throws GranitaException {
        if (currentToken != Token.TK_KW_CLASS){
            throw new GranitaException("Expected class but found "+ currentToken.lexeme);
        }
        while (currentToken.type == Token.TokenType.KW_CLASS) {
            P();
        }
        Match(Token.TK_EOF, "end of file");
    }

    private void Match(Token comp, String expected) throws GranitaException {
        if (currentToken.equals(comp)) {
            currentToken = lexer.nextToken();
        } else {
            throw new GranitaException("Expected " + expected + " but found " + currentToken.lexeme);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Non-terminals">
    /**
     * P stands for Program. P -> class id { DECL_L }
     */
    private void P() throws GranitaException {
        Match(Token.TK_KW_CLASS, "class");
        Match(Token.TK_IDENTIFIER, "identifier");
        Match(Token.TK_LEFT_CURLY_BRACKET, "{");
        DECL_L();
        Match(Token.TK_RIGHT_CURLY_BRACKET, "}");
    }

    /**
     * DECL_L stands for Declaration List. DECL_L -> type id ((FD DECL_L) | (MD
     * DECL_Lp)) |void id MD DECL_Lp
     */
    private void DECL_L() throws GranitaException {
        if (currentToken == Token.TK_KW_VOID) {
            currentToken = lexer.nextToken();
            Match(Token.TK_IDENTIFIER, "identifier");
            MD();
            DECL_Lp();
        } else if (is_type(currentToken)) {
            currentToken = lexer.nextToken();
            Match(Token.TK_IDENTIFIER, "identifier");
            if (currentToken == Token.TK_LEFT_PARENTHESIS) {
                MD();
                DECL_Lp();
            } else if (currentToken == Token.TK_SEMICOLON
                    || currentToken == Token.TK_COLON
                    || currentToken == Token.TK_LEFT_BRACKET
                    || currentToken == Token.TK_OP_EQ) {
                FD();
                DECL_L();
            }
        } else { /* Nada por epsilon */ }
    }

    /**
     * DECL_Lp stands for declaration list prime. DECL_Lp -> (type | void) id MD
     * DECL_Lp | e
     */
    private void DECL_Lp() throws GranitaException {
        if (is_type(currentToken) || currentToken == Token.TK_KW_VOID) {
            currentToken = lexer.nextToken();
            Match(Token.TK_IDENTIFIER, "identifier");
            MD();
            DECL_Lp();
        } else { /* Nada por epsilon */ }
    }

    /**
     * MD stands for Method Declaration. MD -> '(' (type id (, type id)* | e )
     * ')' BLOCK
     */
    private void MD() throws GranitaException {
        Match(Token.TK_LEFT_PARENTHESIS, "(");
        if (is_type(currentToken)) {
            currentToken = lexer.nextToken();
            Match(Token.TK_IDENTIFIER, "identifier");
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                if (is_type(currentToken)) {
                    currentToken = lexer.nextToken();
                    Match(Token.TK_IDENTIFIER, "identifier");
                } else {
                    throw new GranitaException("Expected argument after , but found " + currentToken.lexeme);
                }
            }
        } else { /*Nada por el epsilon*/ }
        Match(Token.TK_RIGHT_PARENTHESIS, ")");
        BLOCK();
    }

    /**
     * FD stands for Field Declaration.
     */
    private void FD() throws GranitaException {
        if (currentToken == Token.TK_SEMICOLON) {
            currentToken = lexer.nextToken();
        } else if (currentToken == Token.TK_OP_EQ) {
            currentToken = lexer.nextToken();
            CONSTANT();
            Match(Token.TK_SEMICOLON, ";");
        } else if (currentToken == Token.TK_LEFT_BRACKET) {
            currentToken = lexer.nextToken();
            Match(Token.TK_INT_CONSTANT, "integer");
            Match(Token.TK_RIGHT_BRACKET, "]");
            FD_L();            
        } else if (currentToken == Token.TK_COLON) {
            FD_L();
        }
    }

    private void FD_L() throws GranitaException {
        while (currentToken == Token.TK_COLON) {
            currentToken = lexer.nextToken();
            System.out.println(currentToken.lexeme);
            Match(Token.TK_IDENTIFIER, "identifier");
            if (currentToken == Token.TK_LEFT_BRACKET) {
                currentToken = lexer.nextToken();
                Match(Token.TK_INT_CONSTANT, "integer");
                Match(Token.TK_RIGHT_BRACKET, "]");
            }
        }
        Match(Token.TK_SEMICOLON, ";");
    }

    private void CONSTANT() throws GranitaException {
        if (is_constant(currentToken)) {
            currentToken = lexer.nextToken();
        }
    }

    /**
     * BLOCK represents a list of statements enclosed in curly brackets. BLOCK
     * -> '{' VD* STNT* '}'
     */
    private void BLOCK() throws GranitaException {
        Match(Token.TK_LEFT_CURLY_BRACKET, "{");
        while (is_type(currentToken)) {
            VD();
        }
        while (is_start_of_statement(currentToken)){
            System.out.println(currentToken.lexeme);
            STNT();
        }
        Match(Token.TK_RIGHT_CURLY_BRACKET, "}");
    }

    /**
     * VD stands for Variable declaration. VD -> type id (, id)* ;
     */
    private void VD() throws GranitaException {
        if (is_type(currentToken)) {
            currentToken = lexer.nextToken();
            Match(Token.TK_IDENTIFIER, "identifier");
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                Match(Token.TK_IDENTIFIER, "identifier");
            }
            Match(Token.TK_SEMICOLON, ";");
        }
    }

    private void STNT() throws GranitaException {
        if (currentToken == Token.TK_LEFT_CURLY_BRACKET) {
            BLOCK();
        } else if (currentToken == Token.TK_KW_CONTINUE || currentToken == Token.TK_KW_BREAK) {
            currentToken = lexer.nextToken();
            Match(Token.TK_SEMICOLON, ";");
        } else if (currentToken == Token.TK_KW_RETURN) {
            currentToken = lexer.nextToken();
            if (is_start_of_expr(currentToken)) {
                EXPR();
            } else { /*Nada por el epsilon*/ }
            Match(Token.TK_SEMICOLON, ";");
        } else if (currentToken == Token.TK_KW_FOR) {
            currentToken = lexer.nextToken();
            Match(Token.TK_LEFT_PARENTHESIS, "(");
            EXPR();
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                EXPR();
            }
            Match(Token.TK_SEMICOLON, ";");
            EXPR();
            Match(Token.TK_SEMICOLON, ";");
            Match(Token.TK_IDENTIFIER, "identifier");
            ASSIGN();
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                Match(Token.TK_IDENTIFIER, "identifier");
                ASSIGN();
            }
            Match(Token.TK_RIGHT_PARENTHESIS, ")");
            BLOCK();
        } else if (currentToken == Token.TK_KW_WHILE) {
            currentToken = lexer.nextToken();
            Match(Token.TK_LEFT_PARENTHESIS, "(");
            EXPR();
            Match(Token.TK_RIGHT_PARENTHESIS, ")");
            BLOCK();
        } else if (currentToken == Token.TK_KW_IF) {
            currentToken = lexer.nextToken();
            Match(Token.TK_LEFT_PARENTHESIS, "(");
            EXPR();
            Match(Token.TK_RIGHT_PARENTHESIS, ")");
            BLOCK();
            if (currentToken == Token.TK_KW_ELSE) {
                currentToken = lexer.nextToken();
                BLOCK();
            } else { /*Nada por el epsilon*/ }
        } else if (currentToken.equals(Token.TK_IDENTIFIER)) {
            currentToken = lexer.nextToken();
            if (currentToken == Token.TK_OP_EQ || currentToken == Token.TK_LEFT_BRACKET) {
                ASSIGN();
            } else if (currentToken == Token.TK_LEFT_PARENTHESIS) {
                MC();
            }
            Match(Token.TK_SEMICOLON, ";");
        } else if (currentToken == Token.TK_KW_PRINT || currentToken == Token.TK_KW_READ) {
            SMC();
            Match(Token.TK_SEMICOLON, ";");
        } else {
            throw new GranitaException("Expected a statement but found " + currentToken.lexeme);
        }
    }

    private void ASSIGN() throws GranitaException {
        if (currentToken == Token.TK_OP_EQ) {
            currentToken = lexer.nextToken();
            EXPR();
        } else if (currentToken == Token.TK_LEFT_BRACKET) {
            currentToken = lexer.nextToken();
            EXPR();
            Match(Token.TK_RIGHT_BRACKET, "]");
            Match(Token.TK_OP_EQ, "=");
            EXPR();
        } else {
            throw new GranitaException("Expected assignment but found " + currentToken.lexeme);
        }
    }

    private void EXPR() throws GranitaException {
        if (currentToken == Token.TK_OP_SUB || currentToken == Token.TK_OP_NOT) {
            currentToken = lexer.nextToken();
            EXPR();
        } else if (currentToken == Token.TK_KW_PRINT || currentToken == Token.TK_KW_READ) {
            SMC();
        } else if (is_constant(currentToken)) {
            CONSTANT();
        } else if (currentToken.equals(Token.TK_IDENTIFIER)) {
            currentToken = lexer.nextToken();
            if (currentToken == Token.TK_LEFT_BRACKET) {
                currentToken = lexer.nextToken();
                EXPR();
                Match(Token.TK_RIGHT_BRACKET, "]");
            } else if (currentToken == Token.TK_LEFT_PARENTHESIS) {
                MC();
            } else { /* Nada por el epsilon */ }
        } else if (currentToken == Token.TK_LEFT_PARENTHESIS) {
            currentToken = lexer.nextToken();
            EXPR();
            Match(Token.TK_RIGHT_PARENTHESIS, ")");
        } else {
            throw new GranitaException("Expected expression but found " + currentToken.lexeme);
        }
        EXPRp();
    }

    private void EXPRp() throws GranitaException {
        if (is_bin_op(currentToken)) {
            currentToken = lexer.nextToken();
            EXPR();
            EXPRp();
        } else { /* Nada por el epsilon */ }
    }

    /**
     * SMC stands for Special Method Call. SMC -> print ARG (, ARG)* |read id
     * ([EXPR] | e)
     */
    private void SMC() throws GranitaException {
        if (currentToken == Token.TK_KW_PRINT) {
            currentToken = lexer.nextToken();
            ARG();
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                ARG();
            }
        } else if (currentToken == Token.TK_KW_READ) {
            currentToken = lexer.nextToken();
            Match(Token.TK_IDENTIFIER, "identifier");
            if (currentToken == Token.TK_LEFT_BRACKET) {
                currentToken = lexer.nextToken();
                EXPR();
                Match(Token.TK_RIGHT_BRACKET, "]");
            } else { /* Nada por el epsilon */ }
        } else {
            throw new GranitaException("Expected print or read function but found " + currentToken.lexeme);
        }
    }

    /**
     * MC stands for Method Call.
     */
    private void MC() throws GranitaException {
        Match(Token.TK_LEFT_PARENTHESIS, "(");
        if (is_start_of_expr(currentToken)){
            EXPR();
            while (currentToken == Token.TK_COLON){
                currentToken = lexer.nextToken();
                EXPR();
            }
        }
        Match(Token.TK_RIGHT_PARENTHESIS, ")");
    }

    /**
     * ARG stands for Argument. ARG -> stringConstant |EXPR
     */
    private void ARG() throws GranitaException {
        if (is_start_of_expr(currentToken)) {
            EXPR();
        } else {
            Match(Token.TK_STRING_CONSTANT, "string constant");
        }
    }

    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Helper functions">
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
    
    private boolean is_start_of_statement(Token current){
        if (current.equals(Token.TK_IDENTIFIER) 
                || current == Token.TK_KW_IF 
                || current == Token.TK_KW_WHILE
                || current == Token.TK_KW_FOR
                || current == Token.TK_KW_RETURN
                || current == Token.TK_KW_BREAK
                || current == Token.TK_KW_CONTINUE
                || current == Token.TK_LEFT_CURLY_BRACKET
                || current == Token.TK_KW_PRINT 
                || current == Token.TK_KW_READ){
            return true;
        }
        return false;
    }
    //</editor-fold>    
}
