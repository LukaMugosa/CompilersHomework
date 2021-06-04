package me.ac.ucg.kompajleri;

import me.ac.ucg.kompajleri.constants.TokenCodes;
import me.ac.ucg.kompajleri.constants.TokenNames;
import me.ac.ucg.kompajleri.services.CreateHashmapsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Parser {

    private static Token curr;			// current token (recently recognized)
    private static Token la;		// lookahead token
    private static int sym;			// always contains la.kind
    public  static int errors;  	// error counter
    private static int errDist;		// no. of correctly recognized tokens since last error

    private static HashMap<String, ArrayList<Integer>> mapOfFirstSets;

    //------------------- auxiliary methods ----------------------
    private static void scan() {
        curr = la;
        la = Scanner.next();
        while (la == null) {
            la = Scanner.next();
        }
        System.out.println(la);
        sym = la.kind;
        errDist++;
    }

    private static void check(int expected) {
//        System.out.println(sym);
        if (sym == expected){
//            System.out.println(expected);
//            System.out.println(expected);
            if(sym == TokenCodes.identifier_ && la.string.length() > 31){
                error("Identifier name length must be less or equal to 31.");
            }
            scan();
        }
        else error(TokenNames.tokenNames[expected] + " expected");
    }

    public static void error(String msg) { // syntactic error at token la
        if (errDist >= 3) {
            System.out.println("-- line " + la.line + " col " + la.col + ": " + msg);
            errors++;
        }
        errDist = 0;
    }

    //-------------- parsing methods (in alphabetical order) -----------------

    private static void Program() {
        check(TokenCodes.let_);
        Declarations();
        check(TokenCodes.in_);
        CommandSequence();
        check(TokenCodes.end_);
    }

    private static void Declarations() {
        ArrayList<Integer> firstDeclarations = mapOfFirstSets.get("Declarations");
        while (firstDeclarations.contains(sym)) {
            Decl();
        }
    }

    private static void Decl() {
        Type();
        check(TokenCodes.identifier_);
        check(TokenCodes.semicolon_);
    }

    private static void Type() {
        switch (sym) {
            case TokenCodes.integer_, TokenCodes.bool_, TokenCodes.string_, TokenCodes.double_ -> scan();
            default -> error("variable type (integer, bool, string, double) expected");
        }
    }

    private static void CommandSequence() {
        ArrayList<Integer> firstCommandSequence = mapOfFirstSets.get("CommandSequence");
        check(TokenCodes.leftCurlyBracket_);
        while (firstCommandSequence.contains(sym))
            Stmt();
        check(TokenCodes.rightCurlyBracket_);

    }

    private static void Stmt() {
        if (sym == TokenCodes.if_)
            IfStmt();
        else if (sym == TokenCodes.while_)
            WhileStmt();
        else if (sym == TokenCodes.for_)
            ForStmt();
        else if (sym == TokenCodes.break_)
            BreakStmt();
        else if (sym == TokenCodes.print_)
            PrintStmt();
        else if (sym == TokenCodes.identifier_) {
            AssignmentExpr();
            check(TokenCodes.semicolon_);
        } else if (mapOfFirstSets.get("ExprNum_2").contains(sym)) {
            ExprNum_2();
            check(TokenCodes.semicolon_);
        } else {
            error("Statement error");
        }
    }

    private static void IfStmt() {
        check(TokenCodes.if_);
        check(TokenCodes.leftParentheses_);
        ExprNum_2();
        check(TokenCodes.rightParentheses_);
        CommandSequence();
        IfStmtEnd();
    }

    private static void IfStmtEnd() {
        if (sym == TokenCodes.else_) {
            scan();
            CommandSequence();
            check(TokenCodes.fi_);
        } else if (sym == TokenCodes.fi_) {
            scan();
        } else {
            error("Invalid end for IF statement");
        }
    }

    private static void WhileStmt() {
        check(TokenCodes.while_);
        check(TokenCodes.leftParentheses_);
        ExprNum_2();
        check(TokenCodes.rightParentheses_);
        CommandSequence();
    }

    private static void ForStmt() {
        check(TokenCodes.for_);
        check(TokenCodes.leftParentheses_);
        AssignmentExpr();
        check(TokenCodes.semicolon_);
        ExprNum_2();
        check(TokenCodes.semicolon_);
        AssignmentExpr();
        check(TokenCodes.rightParentheses_);
        CommandSequence();
    }

    private static void BreakStmt() {
        check(TokenCodes.break_);
        check(TokenCodes.semicolon_);
    }

    private static void PrintStmt() {
        check(TokenCodes.print_);
        check(TokenCodes.leftParentheses_);
        ExprNum_2();
        check(TokenCodes.rightParentheses_);
        check(TokenCodes.semicolon_);
    }

    private static void AssignmentExpr() {
        check(TokenCodes.identifier_);
        check(TokenCodes.assign_);
        ExprNum_2();
    }

    private static void ExprNum_2() {
        ExprNum_3();
        ExprNum_2Prim();
    }

    private static void ExprNum_2Prim() {
        if (mapOfFirstSets.get("LogOp").contains(sym)) {
            scan();
            ExprNum_3();
            ExprNum_2Prim();
        }
    }

    private static void ExprNum_3() {
        ExprNum_4();
        EndExprNum_3();
    }

    private static void EndExprNum_3() {
        if (mapOfFirstSets.get("EqualityOp").contains(sym)) {
            scan();
            ExprNum_4();
        }
    }

    private static void ExprNum_4() {
        ExprNum_5();
        EndExprNum_4();
    }

    private static void EndExprNum_4() {
        if (mapOfFirstSets.get("ComparisonOp").contains(sym)) {
            scan();
            ExprNum_5();
        }
    }

    private static void ExprNum_5() {
        ExprNum_6();
        ExprNum_5Prim();
    }

    private static void ExprNum_5Prim() {
        if (mapOfFirstSets.get("ADD").contains(sym)) {
            scan();
            ExprNum_6();
            ExprNum_5Prim();
        }
    }

    private static void ExprNum_6() {
        ExprNum_7();
        ExprNum_6Prim();
    }

    private static void ExprNum_6Prim() {
        if (mapOfFirstSets.get("MUL").contains(sym)) {
            scan();
            ExprNum_7();
            ExprNum_6Prim();
        }
    }

    private static void ExprNum_7() {
        if(sym == TokenCodes.not_ || sym == TokenCodes.minus_){
            scan();
        }
        ExprNum_8();
    }

    private static void ExprNum_8() {
        if (mapOfFirstSets.get("Const").contains(sym)) {
            scan();
        } else if (sym == TokenCodes.identifier_)
            scan();
        else if (sym == TokenCodes.leftParentheses_) {
            scan();
            ExprNum_2();
            check(TokenCodes.rightParentheses_);
        } else if (mapOfFirstSets.get("ReadOperations").contains(sym)) {
            scan();
        }
        else {
            error("Expression expected");
            scan();
        }
    }

    public static void parse() {
        mapOfFirstSets = CreateHashmapsService.createMapOfFirstSets();
        errors = 0;
        errDist = 3;
        scan();
        Program();
        if (sym != TokenCodes.eof) error("end of file found before end of program");
    }
}
