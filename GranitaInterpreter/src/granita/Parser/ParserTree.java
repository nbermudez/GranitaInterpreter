/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser;

import granita.Parser.Expressions.Add;
import granita.Parser.Expressions.And;
import granita.Parser.Expressions.Div;
import granita.Parser.Expressions.Eq;
import granita.Parser.Expressions.Expression;
import granita.Parser.Expressions.GreaterThan;
import granita.Parser.Expressions.GreaterThanEq;
import granita.Parser.Expressions.LessThan;
import granita.Parser.Expressions.LessThanEq;
import granita.Parser.Expressions.LitBool;
import granita.Parser.Expressions.LitInt;
import granita.Parser.Expressions.LitString;
import granita.Parser.Expressions.MethodCallExpression;
import granita.Parser.Expressions.Mod;
import granita.Parser.Expressions.Mult;
import granita.Parser.Expressions.NotEq;
import granita.Parser.Expressions.Or;
import granita.Parser.Expressions.Rot;
import granita.Parser.Expressions.ShiftLeft;
import granita.Parser.Expressions.ShiftRight;
import granita.Parser.Expressions.Sub;
import granita.Parser.Expressions.UnaryMinus;
import granita.Parser.Expressions.UnaryNot;
import granita.Parser.FieldItems.ArrayField;
import granita.Parser.FieldItems.SimpleField;
import granita.Parser.Functions.Argument;
import granita.Parser.Functions.ParameterDeclaration;
import granita.Parser.Functions.VarDeclaration;
import granita.Parser.LeftValues.ArrayIndexLeftValue;
import granita.Parser.LeftValues.LeftValue;
import granita.Parser.LeftValues.SimpleValue;
import granita.Lexer.Lexer;
import granita.Lexer.Token;
import granita.Parser.Statements.AssignStatement;
import granita.Parser.Statements.BlockStatement;
import granita.Parser.Statements.BreakStatement;
import granita.Parser.Statements.ClassStatement;
import granita.Parser.Statements.ContinueStatement;
import granita.Parser.Statements.FieldDeclarationStatement;
import granita.Parser.Statements.ForStatement;
import granita.Parser.Statements.IfStatement;
import granita.Parser.Statements.InitializedFieldDeclarationStatement;
import granita.Parser.Statements.MethodCallStatement;
import granita.Parser.Statements.MethodDeclarationStatement;
import granita.Parser.Statements.PrintStatement;
import granita.Parser.Statements.ReadStatement;
import granita.Parser.Statements.ReturnStatement;
import granita.Parser.Statements.Statement;
import granita.Parser.Statements.WhileStatement;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granita.Semantic.Types.VoidType;
import granitainterpreter.GranitaException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ParserTree {

    //<editor-fold defaultstate="collapsed" desc="Instance attributes">
    private Token currentToken;
    private Lexer lexer;
    private ClassStatement currentClass;
    private boolean insideLoop = false;
    private int counter_d = 0;
    private Stack<Integer> scopes_d;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public ParserTree(String path) throws IOException {
        lexer = new Lexer(path);
        this.scopes_d = new Stack<Integer>();
    }

    public ParserTree(Lexer lexer) {
        this.lexer = lexer;
        this.scopes_d = new Stack<Integer>();
        pushLocalScope(newLocalScopeId());
    }
    //</editor-fold>    

    public ArrayList<Statement> parse() throws GranitaException {
        currentToken = lexer.nextToken();
        return statements();
    }

    private ArrayList<Statement> statements() throws GranitaException {
        ArrayList<Statement> programs = new ArrayList<Statement>();
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

    private int getLocalScopeId() {
        return this.scopes_d.peek();
    }

    private void pushLocalScope(int top) {
        this.scopes_d.push(top);
    }

    private void popLocalScope() {
        this.scopes_d.pop();
    }

    private int newLocalScopeId() {
        int ret = this.counter_d;
        counter_d += 1;
        return ret;
    }

    //<editor-fold defaultstate="collapsed" desc="Non-terminals">
    /**
     * P stands for Program. P -> class id { DECL_L }
     */
    private Statement P() throws GranitaException {

        match(Token.TK_KW_CLASS, "class");
        currentClass = new ClassStatement(currentToken.lexeme, lexer.lineNumber());
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
    private Statement MD(String typeText, String id) throws GranitaException {
        Type type = getTypeFromText(typeText);
        MethodDeclarationStatement mds = new MethodDeclarationStatement(type, id,
                lexer.lineNumber());

        match(Token.TK_LEFT_PARENTHESIS, "(");
        int scopeId = newLocalScopeId();
        pushLocalScope(scopeId);

        String paramType, paramId;
        if (is_type(currentToken)) {
            paramType = get_type(currentToken);

            currentToken = lexer.nextToken();
            paramId = currentToken.lexeme;

            ParameterDeclaration param = new ParameterDeclaration(getTypeFromText(paramType), 
                    paramId, getLocalScopeId(), lexer.lineNumber());

            mds.addParameter(param);

            match(Token.TK_IDENTIFIER, "identifier");

            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                if (is_type(currentToken)) {
                    paramType = get_type(currentToken);

                    currentToken = lexer.nextToken();
                    paramId = currentToken.lexeme;

                    param = new ParameterDeclaration(getTypeFromText(paramType), 
                            paramId, getLocalScopeId(), lexer.lineNumber());

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

        popLocalScope();
        return mds;
    }

    /**
     * FD stands for Field Declaration.
     */
    private Statement FD(String typeText, String id) throws GranitaException {
        Type type = getTypeFromText(typeText);
        if (currentToken == Token.TK_SEMICOLON) {
            SimpleField f = new SimpleField(id, lexer.lineNumber());
            FieldDeclarationStatement flist = new FieldDeclarationStatement(type, lexer.lineNumber());
            flist.addDeclaration(f);
            currentToken = lexer.nextToken();
            return flist;
        } else if (currentToken == Token.TK_OP_EQ) {
            currentToken = lexer.nextToken();
            Expression constant = CONSTANT();
            match(Token.TK_SEMICOLON, ";");
            InitializedFieldDeclarationStatement i = new InitializedFieldDeclarationStatement(type, id, constant, lexer.lineNumber());
            return i;
        } else if (currentToken == Token.TK_LEFT_BRACKET) {
            currentToken = lexer.nextToken();

            LitInt arraySize = new LitInt(currentToken.lexeme, lexer.lineNumber());
            ArrayField f = new ArrayField(id, arraySize, lexer.lineNumber());
            FieldDeclarationStatement flist = new FieldDeclarationStatement(type, lexer.lineNumber());
            flist.addDeclaration(f);

            match(Token.TK_INT_CONSTANT, "integer");
            match(Token.TK_RIGHT_BRACKET, "]");
            String fieldId;
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();

                fieldId = currentToken.lexeme;
                match(Token.TK_IDENTIFIER, "identifier");
                if (currentToken == Token.TK_LEFT_BRACKET) {
                    currentToken = lexer.nextToken();
                    arraySize = new LitInt(currentToken.lexeme, lexer.lineNumber());
                    f = new ArrayField(fieldId, arraySize, lexer.lineNumber());
                    flist.addDeclaration(f);
                    match(Token.TK_INT_CONSTANT, "integer");
                    match(Token.TK_RIGHT_BRACKET, "]");
                } else {
                    SimpleField ff = new SimpleField(fieldId, lexer.lineNumber());
                    flist.addDeclaration(ff);
                }
            }
            match(Token.TK_SEMICOLON, ";");
            return flist;
        } else if (currentToken == Token.TK_COLON) {
            SimpleField f = new SimpleField(id, lexer.lineNumber());
            FieldDeclarationStatement flist = new FieldDeclarationStatement(type, lexer.lineNumber());
            flist.addDeclaration(f);
            String fieldId;
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();

                fieldId = currentToken.lexeme;
                match(Token.TK_IDENTIFIER, "identifier");
                if (currentToken == Token.TK_LEFT_BRACKET) {
                    currentToken = lexer.nextToken();
                    LitInt arraySize = new LitInt(currentToken.lexeme, lexer.lineNumber());
                    ArrayField ff = new ArrayField(fieldId, arraySize, lexer.lineNumber());
                    flist.addDeclaration(ff);
                    match(Token.TK_INT_CONSTANT, "integer");
                    match(Token.TK_RIGHT_BRACKET, "]");
                } else {
                    SimpleField ff = new SimpleField(fieldId, lexer.lineNumber());
                    flist.addDeclaration(ff);
                }
            }
            match(Token.TK_SEMICOLON, ";");
            return flist;
        }
        return null;
    }

    private Expression CONSTANT() throws GranitaException {
        if (currentToken.equals(Token.TK_CHAR_CONSTANT)
                || currentToken.equals(Token.TK_INT_CONSTANT)) {
            LitInt integer = new LitInt(currentToken.lexeme, lexer.lineNumber());
            currentToken = lexer.nextToken();
            return integer;
        } else if (currentToken.equals(Token.TK_KW_FALSE)
                || currentToken.equals(Token.TK_KW_TRUE)) {
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
    private Statement BLOCK() throws GranitaException {
        match(Token.TK_LEFT_CURLY_BRACKET, "{");
        BlockStatement block = new BlockStatement(getLocalScopeId(), lexer.lineNumber());
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
    private VarDeclaration VD() throws GranitaException {
        String type, id;
        ArrayList<String> vars = new ArrayList<String>();
        if (is_type(currentToken)) {
            type = get_type(currentToken);

            currentToken = lexer.nextToken();
            id = currentToken.lexeme;
            vars.add(id);

            match(Token.TK_IDENTIFIER, "identifier");
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                id = currentToken.lexeme;
                vars.add(id);

                match(Token.TK_IDENTIFIER, "identifier");
            }
            match(Token.TK_SEMICOLON, ";");
            return new VarDeclaration(getTypeFromText(type), vars, 
                    getLocalScopeId(), lexer.lineNumber());
        }
        throw new GranitaException("Expected a variable declaration but found "
                + currentToken.lexeme + " in line " + lexer.lineNumber());
    }

    private Statement STNT() throws GranitaException {
        if (currentToken == Token.TK_LEFT_CURLY_BRACKET) {
            int scopeId = newLocalScopeId();
            pushLocalScope(scopeId);

            Statement block = BLOCK();

            popLocalScope();
            return block;
        } else if (currentToken == Token.TK_KW_CONTINUE) {
            Statement c = new ContinueStatement(false, lexer.lineNumber());
            currentToken = lexer.nextToken();
            match(Token.TK_SEMICOLON, ";");

            return c;
        } else if (currentToken == Token.TK_KW_BREAK) {
            Statement b = new BreakStatement(insideLoop, lexer.lineNumber());
            currentToken = lexer.nextToken();
            match(Token.TK_SEMICOLON, ";");

            return b;
        } else if (currentToken == Token.TK_KW_RETURN) {
            currentToken = lexer.nextToken();
            Expression exp = null;
            if (is_start_of_expr(currentToken)) {
                exp = EXPR();
            } else { /*Nada por el epsilon*/ }
            match(Token.TK_SEMICOLON, ";");

            Statement re = new ReturnStatement(insideLoop, exp, lexer.lineNumber());
            return re;
        } else if (currentToken == Token.TK_KW_FOR) {
            int scopeId = newLocalScopeId();
            pushLocalScope(scopeId);

            ArrayList<Expression> inits = new ArrayList<Expression>();
            ArrayList<Statement> incrs = new ArrayList<Statement>();
            Expression termination;
            insideLoop = true;
            currentToken = lexer.nextToken();
            match(Token.TK_LEFT_PARENTHESIS, "(");
            inits.add(EXPR());

            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                inits.add(EXPR());
            }
            match(Token.TK_SEMICOLON, ";");
            termination = EXPR();
            match(Token.TK_SEMICOLON, ";");

            String id = currentToken.lexeme;
            match(Token.TK_IDENTIFIER, "identifier");

            incrs.add(ASSIGN(id));
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                id = currentToken.lexeme;

                match(Token.TK_IDENTIFIER, "identifier");
                incrs.add(ASSIGN(id));
            }
            match(Token.TK_RIGHT_PARENTHESIS, ")");
            Statement block = BLOCK();
            insideLoop = false;
            Statement fo = new ForStatement(block, inits, termination, incrs, lexer.lineNumber());

            popLocalScope();
            return fo;
        } else if (currentToken == Token.TK_KW_WHILE) {
            int scopeId = newLocalScopeId();
            pushLocalScope(scopeId);

            insideLoop = true;
            currentToken = lexer.nextToken();
            match(Token.TK_LEFT_PARENTHESIS, "(");
            Expression condition = EXPR();
            match(Token.TK_RIGHT_PARENTHESIS, ")");
            Statement block = BLOCK();
            insideLoop = false;

            popLocalScope();
            return new WhileStatement(condition, block, lexer.lineNumber());
        } else if (currentToken == Token.TK_KW_IF) {
            int scopeId = newLocalScopeId();
            pushLocalScope(scopeId);

            currentToken = lexer.nextToken();
            match(Token.TK_LEFT_PARENTHESIS, "(");
            Expression conditional = EXPR();
            match(Token.TK_RIGHT_PARENTHESIS, ")");
            Statement trueBlock = BLOCK(), falseBlock = null;
            if (currentToken == Token.TK_KW_ELSE) {
                currentToken = lexer.nextToken();
                falseBlock = BLOCK();
            } else { /*Nada por el epsilon*/ }

            popLocalScope();
            return new IfStatement(conditional, trueBlock, falseBlock, lexer.lineNumber());
        } else if (currentToken.equals(Token.TK_IDENTIFIER)) {
            String id = currentToken.lexeme;
            currentToken = lexer.nextToken();
            Statement node = null;
            if (currentToken == Token.TK_OP_EQ || currentToken == Token.TK_LEFT_BRACKET) {
                node = ASSIGN(id);
            } else if (currentToken == Token.TK_LEFT_PARENTHESIS) {
                node = MC_Statement(id);
            }
            match(Token.TK_SEMICOLON, ";");
            return node;
        } else if (currentToken == Token.TK_KW_PRINT || currentToken == Token.TK_KW_READ) {
            Statement smc = SMC();
            match(Token.TK_SEMICOLON, ";");

            return smc;
        } else {
            throw new GranitaException("Expected a statement but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        }
    }

    private Statement ASSIGN(String id) throws GranitaException {
        if (currentToken == Token.TK_OP_EQ) {
            currentToken = lexer.nextToken();

            LeftValue left = new SimpleValue(id, getLocalScopeId(), lexer.lineNumber());
            Expression value = EXPR();

            Statement assign = new AssignStatement(left, value, lexer.lineNumber());
            return assign;
        } else if (currentToken == Token.TK_LEFT_BRACKET) {
            currentToken = lexer.nextToken();

            Expression index = EXPR();

            match(Token.TK_RIGHT_BRACKET, "]");
            match(Token.TK_OP_EQ, "=");

            Expression value = EXPR();

            ArrayIndexLeftValue left = new ArrayIndexLeftValue(id, index, getLocalScopeId(), lexer.lineNumber());
            Statement assign = new AssignStatement(left, value, lexer.lineNumber());
            return assign;
        } else {
            throw new GranitaException("Expected assignment but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        }
    }

    private Expression EXPR() throws GranitaException {
        return E();
    }

    /**
     * SMC stands for Special Method Call. SMC -> print ARG (, ARG)* |read id
     * ([EXPR] | e)
     */
    private Statement SMC() throws GranitaException {
        if (currentToken == Token.TK_KW_PRINT) {
            currentToken = lexer.nextToken();
            ArrayList<Argument> args = new ArrayList<Argument>();
            args.add(ARG());
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                args.add(ARG());
            }
            return new PrintStatement(args, lexer.lineNumber());
        } else if (currentToken == Token.TK_KW_READ) {
            currentToken = lexer.nextToken();

            String id = currentToken.lexeme;
            ReadStatement read = new ReadStatement(lexer.lineNumber());
            ArrayList<LeftValue> leftValues = new ArrayList<LeftValue>();
            Expression index;

            match(Token.TK_IDENTIFIER, "identifier");
            if (currentToken == Token.TK_LEFT_BRACKET) {
                currentToken = lexer.nextToken();
                index = EXPR();
                match(Token.TK_RIGHT_BRACKET, "]");
                leftValues.add(new ArrayIndexLeftValue(id, index, getLocalScopeId(), lexer.lineNumber()));
            } else {
                leftValues.add(new SimpleValue(id, getLocalScopeId(), lexer.lineNumber()));
            }
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                id = currentToken.lexeme;
                match(Token.TK_IDENTIFIER, "identifier");
                if (currentToken == Token.TK_LEFT_BRACKET) {
                    currentToken = lexer.nextToken();
                    index = EXPR();
                    match(Token.TK_RIGHT_BRACKET, "]");
                    leftValues.add(new ArrayIndexLeftValue(id, index, getLocalScopeId(), lexer.lineNumber()));
                } else {
                    leftValues.add(new SimpleValue(id, getLocalScopeId(), lexer.lineNumber()));
                }
            }
            read.setLeftValues(leftValues);
            return read;
        } else {
            throw new GranitaException("Expected print or read function but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        }
    }

    /**
     * MC_Statement stands for Method Call.
     */
    private Statement MC_Statement(String id) throws GranitaException {
        match(Token.TK_LEFT_PARENTHESIS, "(");
        ArrayList<Expression> params = new ArrayList<Expression>();
        if (is_start_of_expr(currentToken)) {
            params.add(EXPR());
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                params.add(EXPR());
            }
        }
        match(Token.TK_RIGHT_PARENTHESIS, ")");
        Statement st = new MethodCallStatement(id, params, lexer.lineNumber());
        return st;
    }

    /**
     * MC_Expression stands for Method Call.
     */
    private Expression MC_Expression(String id) throws GranitaException {
        match(Token.TK_LEFT_PARENTHESIS, "(");
        ArrayList<Expression> params = new ArrayList<Expression>();
        if (is_start_of_expr(currentToken)) {
            params.add(EXPR());
            while (currentToken == Token.TK_COLON) {
                currentToken = lexer.nextToken();
                params.add(EXPR());
            }
        }
        match(Token.TK_RIGHT_PARENTHESIS, ")");
        Expression mc = new MethodCallExpression(id, params, lexer.lineNumber());
        return mc;
    }

    /**
     * ARG stands for Argument. ARG -> stringConstant |EXPR
     */
    private Argument ARG() throws GranitaException {
        Expression value;
        if (is_start_of_expr(currentToken)) {
            value = EXPR();
        } else {
            value = new LitString(currentToken.lexeme, lexer.lineNumber());
            match(Token.TK_STRING_CONSTANT, "string constant");
        }
        return new Argument(value, getLocalScopeId(), lexer.lineNumber());
    }

    //</editor-fold>  
    //<editor-fold defaultstate="collapsed" desc="Expressions with precendence">
    private Expression E() throws GranitaException {
        Expression left = F(), right;
        while (currentToken == Token.TK_OP_OR) {
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
            if (currentToken == Token.TK_REL_NEQ) {
                currentToken = lexer.nextToken();
                right = H();
                left = new NotEq(left, right, lexer.lineNumber());
            } else {
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
                || currentToken == Token.TK_REL_LT || currentToken == Token.TK_REL_LTE) {

            Token tmp = currentToken;
            currentToken = lexer.nextToken();

            right = I();
            if (tmp == Token.TK_REL_GT) {
                left = new GreaterThan(left, right, lexer.lineNumber());
            } else if (tmp == Token.TK_REL_GTE) {
                left = new GreaterThanEq(left, right, lexer.lineNumber());
            } else if (tmp == Token.TK_REL_LT) {
                left = new LessThan(left, right, lexer.lineNumber());
            } else if (tmp == Token.TK_REL_LTE) {
                left = new LessThanEq(left, right, lexer.lineNumber());
            } else {
                break;
            }
        }
        return left;
    }

    private Expression I() throws GranitaException {
        Expression left = J(), right;
        while (currentToken == Token.TK_OP_LEFT_SHIFT || currentToken == Token.TK_OP_RIGHT_SHIFT
                || currentToken == Token.TK_KW_ROT) {
            Token tmp = currentToken;
            currentToken = lexer.nextToken();

            right = J();
            if (tmp == Token.TK_OP_LEFT_SHIFT) {
                left = new ShiftLeft(left, right, lexer.lineNumber());
            } else if (tmp == Token.TK_OP_RIGHT_SHIFT) {
                left = new ShiftRight(left, right, lexer.lineNumber());
            } else if (tmp == Token.TK_KW_ROT) {
                left = new Rot(left, right, lexer.lineNumber());
            }
        }
        return left;
    }

    private Expression J() throws GranitaException {
        Expression left = K(), right;
        while (currentToken == Token.TK_OP_MOD) {
            currentToken = lexer.nextToken();

            right = K();
            left = new Mod(left, right, lexer.lineNumber());
        }
        return left;
    }

    private Expression K() throws GranitaException {
        Expression left = L(), right;
        while (currentToken == Token.TK_OP_ADD || currentToken == Token.TK_OP_SUB) {
            if (currentToken == Token.TK_OP_ADD) {
                currentToken = lexer.nextToken();

                right = L();
                left = new Add(left, right, lexer.lineNumber());
            } else {
                currentToken = lexer.nextToken();

                right = L();
                left = new Sub(left, right, lexer.lineNumber());
            }
        }
        return left;
    }

    private Expression L() throws GranitaException {
        Expression left = M(), right;
        while (currentToken == Token.TK_OP_DIV || currentToken == Token.TK_OP_MULT) {
            if (currentToken == Token.TK_OP_DIV) {
                currentToken = lexer.nextToken();
                right = M();

                left = new Div(left, right, lexer.lineNumber());
            } else {
                currentToken = lexer.nextToken();
                right = M();

                left = new Mult(left, right, lexer.lineNumber());
            }

        }
        return left;
    }

    private Expression M() throws GranitaException {
        Expression result = null;
        if (currentToken == Token.TK_LEFT_PARENTHESIS) {
            currentToken = lexer.nextToken();
            result = E();
            match(Token.TK_RIGHT_PARENTHESIS, ")");
        } else if (currentToken == Token.TK_OP_NOT) {
            currentToken = lexer.nextToken();
            result = new UnaryNot(E(), lexer.lineNumber());
        } else if (currentToken == Token.TK_OP_SUB) {
            currentToken = lexer.nextToken();
            result = new UnaryMinus(E(), lexer.lineNumber());
        } else if (currentToken == Token.TK_KW_READ || currentToken == Token.TK_KW_PRINT) {
            //SMC();
            throw new GranitaException("Expected expression but found " + currentToken.lexeme
                    + " in line " + this.lexer.lineNumber());
        } else if (currentToken.equals(Token.TK_IDENTIFIER)) {
            String id = currentToken.lexeme;
            currentToken = lexer.nextToken();
            if (currentToken == Token.TK_LEFT_PARENTHESIS) {
                result = MC_Expression(id);
            } else if (currentToken == Token.TK_LEFT_BRACKET) {
                currentToken = lexer.nextToken();
                Expression index = EXPR();
                match(Token.TK_RIGHT_BRACKET, "]");

                result = new ArrayIndexLeftValue(id, index, getLocalScopeId(), lexer.lineNumber());
            } else {
                result = new SimpleValue(id, getLocalScopeId(), lexer.lineNumber());
            }
        } else if (is_constant(currentToken)) {
            result = CONSTANT();
        } else {
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
        } else if (current == Token.TK_KW_VOID) {
            return "void";
        } else {
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

    private Type getTypeFromText(String typeText) {
        if (typeText.equals(IntType.text())) {
            return new IntType();
        } else if (typeText.equals(BoolType.text())) {
            return new BoolType();
        } else if (typeText.equals(VoidType.text())) {
            return new VoidType();
        }
        return null;
    }
    //</editor-fold>    
}
