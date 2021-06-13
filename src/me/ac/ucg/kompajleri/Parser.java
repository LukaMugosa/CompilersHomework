package me.ac.ucg.kompajleri;

import me.ac.ucg.kompajleri.constants.TokenCodes;
import me.ac.ucg.kompajleri.constants.TokenNames;
import me.ac.ucg.kompajleri.services.CreateHashmapsService;
import me.ac.ucg.kompajleri.statements_and_expressions.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {

    private static Token curr;            // current token (recently recognized)
    private static Token la;        // lookahead token
    private static int sym;            // always contains la.kind
    public static int errors;    // error counter
    private static int errDist;        // no. of correctly recognized tokens since last error

    private static HashMap<String, ArrayList<Integer>> mapOfFirstSets;
    private static HashMap<String, Couple> mapOfSymbols; // Table of symbols

    private static final Integer ARRAY_SIZE = 200;

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
        if (sym == expected) {
//            System.out.println(expected);
//            System.out.println(expected);
            if (sym == TokenCodes.identifier_ && la.string.length() > 31) {
                error("Identifier name length must be less or equal to 31.");
            }
            scan();
        } else error(TokenNames.tokenNames[expected] + " expected");
    }

    public static void error(String msg) { // syntactic error at token la
        if (errDist >= 3) {
            System.out.println("-- line " + la.line + " col " + la.col + ": " + msg);
            errors++;
        }
        errDist = 0;
    }

    private static String generatingTabs(Integer numTabs) {
        String tabs = "";
        for (int i = 0; i < numTabs; i++)
            tabs = tabs.concat("\t");
        return tabs;
    }

    private static void printingProgram(Program program) {
        System.out.println("Printing all declarations..\n");
        Declaration[] declarations = program.getDeclarations();
        for (Declaration declaration : declarations) {
            System.out.println("\tType: " + declaration.getType() + " , Name : " + declaration.getIdentifierName());
        }
        printCommandSequences(program.getCommandSequence(), 1);
    }

    private static void printCommandSequences(CommandSequence commandSequence, Integer numberOfTabs) {
        System.out.println(generatingTabs(numberOfTabs - 1) + "\nCommand Sequences\n");
        Statement[] statements = commandSequence.getStatements();
        for (Statement st : statements) {
            printStatement(st, numberOfTabs);
        }
    }

    private static void printStatement(Statement statement, Integer numberOfTabs) {
        System.out.println(statement);
        if (statement == null) {
            System.out.println(generatingTabs(numberOfTabs) + " ----- Found null statement");
            return;
        }
        switch (statement.getKind()) {
            case "IF" -> printIfStatement((IfStatement) statement, numberOfTabs);
            case "WHILE" -> printWhileStatement((WhileStatement) statement, numberOfTabs);
            case "FOR" -> printForStatement((ForStatement) statement, numberOfTabs);
            case "BREAK" -> System.out.println(generatingTabs(numberOfTabs) + statement.getKind());
            case "PRINT" -> printPrintingStatement((PrintStatement) statement, numberOfTabs);
            case "ASSIGN" -> printAssignmentExpression((AssignmentExpression) statement, numberOfTabs);
            case "BINARY", "UNARY" -> printExpr((Expr2) statement, numberOfTabs);
            case "REPEAT" -> printRepeatStmt((RepeatStatement) statement, numberOfTabs);
        }

    }

    private static void printIfStatement(IfStatement statement, Integer numberOfTabs) {
        String tabs = generatingTabs(numberOfTabs);
        System.out.println(tabs + statement.getKind());
        System.out.println(tabs + "\t" + "Condition");
        printExpr(statement.getExpr2(), numberOfTabs + 2);
        printCommandSequences(statement.getCommandSequence(), numberOfTabs + 2);
        printEndIfStatement(statement.getIfStmtEnd(), numberOfTabs);
    }

    private static void printEndIfStatement(IfStatementEnd ifStatementEnd, Integer numberOfTabs) {
        System.out.println(ifStatementEnd);
        if (ifStatementEnd == null) {
            System.out.println(generatingTabs(numberOfTabs) + " Found null end if statement");
            return;
        }
        String tabs = generatingTabs(numberOfTabs);
        if (ifStatementEnd.getCommandSequence() != null) {
            System.out.println(tabs + "ELSE statement");
            printCommandSequences(ifStatementEnd.getCommandSequence(), numberOfTabs + 2);
        } else {
            System.out.println(tabs + "END IF statement");
        }
    }

    private static void printWhileStatement(WhileStatement whileStatement, Integer numberOfTabs) {
        String tabs = generatingTabs(numberOfTabs);
        System.out.println(tabs + whileStatement.getKind());
        printExpr(whileStatement.getExpr2(), numberOfTabs + 2);
        printCommandSequences(whileStatement.getCommandSequence(), numberOfTabs + 2);
    }

    private static void printForStatement(ForStatement forStatement, Integer numberOfTabs) {
        String tabs = generatingTabs(numberOfTabs);
        System.out.println(tabs + forStatement.getKind());
        printAssignmentExpression(forStatement.getAssignExprLeft(), numberOfTabs + 1);
        System.out.println(tabs + " Expression: ");
        printExpr(forStatement.getExpr2(), numberOfTabs + 2);
        printAssignmentExpression(forStatement.getAssignExprRight(), numberOfTabs + 1);
        printCommandSequences(forStatement.getCommandSequence(), numberOfTabs + 2);
    }

    private static void printRepeatStmt(RepeatStatement repeatStatement, Integer numberOfTabs) {
        String tabs = generatingTabs(numberOfTabs);
        System.out.println("Repeat statement: ");
        printCommandSequences(repeatStatement.getCommandSequence(), numberOfTabs);
        System.out.println(tabs + "Expression: ");
        printExpr(repeatStatement.getExpr2(), numberOfTabs);
    }

    private static void printPrintingStatement(PrintStatement printStatement, Integer numberOfTabs) {
        String tabs = generatingTabs(numberOfTabs);
        System.out.println(tabs + printStatement.getKind());
        System.out.println(tabs + " Expression: ");
        printExpr(printStatement.getExpr2(), numberOfTabs + 2);
    }

    private static void printExpr(Expr2 expression, Integer numberOfTabs) {
        if (expression == null) {
            System.out.println(generatingTabs(numberOfTabs) + " NULL EXPRESSION");
            return;
        }
        switch (expression.getKind()) {
            case "BINARY" -> printBinary(expression, numberOfTabs);
            case "UNARY" -> printUnary(expression, numberOfTabs);
            case "IntegerConstant", "DoubleConstant", "StringConstant",
                    "boolConstant", "Identifier", "READSTRING", "READDOUBLE",
                    "READINT", "READBOOL" -> System.out.println(generatingTabs(numberOfTabs) + expression.getKind() + ": " + expression.getValue());
        }
    }

    private static void printAssignmentExpression(AssignmentExpression assignmentExpression, Integer numberOfTabs) {
        String tabs = generatingTabs(numberOfTabs);
        System.out.println(tabs + assignmentExpression.getKind());
        System.out.println(tabs + "\t" + " Identifier: " + assignmentExpression.getIdentifier());
        System.out.println(tabs + "\t" + " Expression: ");
        printExpr(assignmentExpression.getExpr2(), numberOfTabs + 2);
    }

    private static void printBinary(Expr2 expr2, Integer numberOfTabs) {
        String tabs = generatingTabs(numberOfTabs);
        System.out.println(tabs + expr2.getKind());
        System.out.println(tabs + "\t" + " Left Operand: ");
        printExpr(expr2.getLeft(), numberOfTabs + 2);
        System.out.println(tabs + "\t" + " Operator " + expr2.getOperator());
        System.out.println(tabs + "\t" + " Right Operand: ");
        printExpr(expr2.getRight(), numberOfTabs + 2);
    }

    private static void printUnary(Expr2 expr, int numTabs) {
        String tabs = generatingTabs(numTabs);
        System.out.println(tabs + expr.getKind());
        System.out.println(tabs + "\t" + " Operator: " + expr.getOperator());
        System.out.println(tabs + "\t" + " Expression: ");
        printExpr(expr.getLeft(), numTabs + 2);
    }


    //-------------- parsing methods (in alphabetical order) -----------------

    private static Program Program() {
        check(TokenCodes.let_);
        Declaration[] declarations = Declarations();
        check(TokenCodes.in_);
        CommandSequence commandSequence = CommandSequence();
        check(TokenCodes.end_);
        Program p = new Program(declarations, commandSequence);
        printingProgram(p);
        return p;
    }

    private static Declaration[] Declarations() {
        ArrayList<Integer> firstDeclarations = mapOfFirstSets.get("Declarations");
        Declaration[] declarations = new Declaration[ARRAY_SIZE];
        int cnt = 0;
        while (firstDeclarations.contains(sym)) {
            declarations[cnt++] = Decl();
        }
        Declaration[] finalDecl = new Declaration[cnt];
        for (int i = 0; i < cnt; i++) {
            finalDecl[i] = declarations[i];
        }
//        System.arraycopy(declarations, 0, finalDecl, 0, cnt);
        return finalDecl;
    }

    private static Declaration Decl() {
        String type = Type();
        String identifier = la.string;
        check(TokenCodes.identifier_);
        check(TokenCodes.semicolon_);
        mapOfSymbols.put(identifier, new Couple(type, null));
        return new Declaration(type, identifier);
    }

    private static String Type() {
        switch (sym) {
            case TokenCodes.integer_ -> {
                scan();
                return "Integer";
            }
            case TokenCodes.bool_ -> {
                scan();
                return "Boolean";
            }
            case TokenCodes.string_ -> {
                scan();
                return "String";
            }
            case TokenCodes.double_ -> {
                scan();
                return "Double";
            }
            default -> {
                error("variable type (integer, bool, string, double) expected");
                return "Invalid data type.";
            }
        }
    }

    private static CommandSequence CommandSequence() {
        ArrayList<Integer> firstCommandSequence = mapOfFirstSets.get("CommandSequence");
        Statement[] statements = new Statement[ARRAY_SIZE];
        int cnt = 0;
        check(TokenCodes.leftCurlyBracket_);
        while (firstCommandSequence.contains(sym))
            statements[cnt++] = Stmt();
        Statement[] finalStmt = new Statement[cnt];
        System.arraycopy(statements, 0, finalStmt, 0, cnt);
        check(TokenCodes.rightCurlyBracket_);
        return new CommandSequence(finalStmt);
    }

    private static Statement Stmt() {
        if (sym == TokenCodes.if_)
            return IfStmt();
        else if (sym == TokenCodes.while_)
            return WhileStmt();
        else if (sym == TokenCodes.for_)
            return ForStmt();
        else if (sym == TokenCodes.break_)
            return BreakStmt();
        else if (sym == TokenCodes.print_)
            return PrintStmt();
        else if (sym == TokenCodes.identifier_) {
            AssignmentExpression assignmentExpression = AssignmentExpr();
            check(TokenCodes.semicolon_);
            return assignmentExpression;
        } else if (mapOfFirstSets.get("ExprNum_2").contains(sym)) {
            Expr2 expr2 = ExprNum_2();
            check(TokenCodes.semicolon_);
            return expr2;
        }
        else if(sym == TokenCodes.repeat_) {
            RepeatStatement repeatStatement = RepeatStmt();
            check(TokenCodes.semicolon_);
            return repeatStatement;
        }
        else {
            error("Statement error");
            return null;
        }
    }

    private static IfStatement IfStmt() {
        check(TokenCodes.if_);
        check(TokenCodes.leftParentheses_);
        Expr2 expr2 = ExprNum_2();
        check(TokenCodes.rightParentheses_);
        CommandSequence commandSequence = CommandSequence();
        IfStatementEnd ifStatementEnd = IfStmtEnd();
        IfStatement ifStatement = new IfStatement(expr2, commandSequence, ifStatementEnd);
        ifStatement.setKind("IF");
        return  ifStatement;
    }

    private static IfStatementEnd IfStmtEnd() {
        if (sym == TokenCodes.else_) {
            scan();
            CommandSequence commandSequence = CommandSequence();
            check(TokenCodes.fi_);
            return new IfStatementEnd(commandSequence);
        } else if (sym == TokenCodes.fi_) {
            scan();
            return new IfStatementEnd(null);
        } else {
            error("Invalid end for IF statement");
            return null;
        }
    }

    private static WhileStatement WhileStmt() {
        check(TokenCodes.while_);
        check(TokenCodes.leftParentheses_);
        Expr2 expr2 = ExprNum_2();
        check(TokenCodes.rightParentheses_);
        CommandSequence commandSequence = CommandSequence();
        WhileStatement whileStatement = new WhileStatement(expr2, commandSequence);
        whileStatement.setKind("WHILE");
        return whileStatement;
    }

    private static ForStatement ForStmt() {
        check(TokenCodes.for_);
        check(TokenCodes.leftParentheses_);
        AssignmentExpression assignmentExpressionLeft = AssignmentExpr();
        check(TokenCodes.semicolon_);
        Expr2 expr2  = ExprNum_2();
        check(TokenCodes.semicolon_);
        AssignmentExpression assignmentExpressionRight = AssignmentExpr();
        check(TokenCodes.rightParentheses_);
        CommandSequence commandSequence = CommandSequence();
        ForStatement forStatement = new ForStatement(assignmentExpressionLeft, assignmentExpressionRight, expr2, commandSequence);
        forStatement.setKind("FOR");
        return forStatement;
    }

    private static Statement BreakStmt() {
        check(TokenCodes.break_);
        check(TokenCodes.semicolon_);
        Statement statement = new Statement();
        statement.setKind("BREAK");
        return statement;
    }

    private static RepeatStatement RepeatStmt() {
        check(TokenCodes.repeat_);
        CommandSequence commandSequence = CommandSequence();
        check(TokenCodes.until_);
        check(TokenCodes.leftParentheses_);
        Expr2 expr2 = ExprNum_2();
        check(TokenCodes.rightParentheses_);
        RepeatStatement repeatStatement = new RepeatStatement(expr2, commandSequence);
        repeatStatement.setKind("REPEAT");
        return repeatStatement;
    }

    private static PrintStatement PrintStmt() {
        check(TokenCodes.print_);
        check(TokenCodes.leftParentheses_);
        Expr2 expr2 = ExprNum_2();
        check(TokenCodes.rightParentheses_);
        check(TokenCodes.semicolon_);
        PrintStatement printStatement = new PrintStatement(expr2);
        printStatement.setKind("PRINT");
        return printStatement;
    }

    private static AssignmentExpression AssignmentExpr() {
        String id = la.string;
        check(TokenCodes.identifier_);
        check(TokenCodes.assign_);
        Expr2 expr2 = ExprNum_2();
        AssignmentExpression assignmentExpression = new AssignmentExpression(id, expr2);
        assignmentExpression.setKind("Assign");
        if(mapOfSymbols.containsKey(id)){
            mapOfSymbols.get(id).setExpr2(expr2);
        }else{
            error("Missing declaration for identifier " + id);
        }
        return assignmentExpression;
    }

    private static Expr2 ExprNum_2() {
        Expr2 expr3 = ExprNum_3();
        return ExprNum_2Prim(expr3);
    }

    private static Expr2 ExprNum_2Prim(Expr2 expr3) {
        if (mapOfFirstSets.get("LogOp").contains(sym)) {
            String op = sym == TokenCodes.or_ ? "||" : "&&";
            scan();
            Expr2 tmp = ExprNum_3();
            return ExprNum_2Prim(new Expr2(op, expr3, tmp, "BINARY"));
        }else{
            return expr3;
        }
    }

    private static Expr2 ExprNum_3() {
        Expr2 expr4 = ExprNum_4();
        return EndExprNum_3(expr4);
    }

    private static Expr2 EndExprNum_3(Expr2 expr4) {
        if (mapOfFirstSets.get("EqualityOp").contains(sym)) {
            String op = sym == TokenCodes.equal_ ? "==" : "!=";
            scan();
            Expr2 tmp = ExprNum_4();
            return new Expr2(op, expr4, tmp, "BINARY");
        }
        return expr4;
    }

    private static Expr2 ExprNum_4() {
        Expr2 expr5 = ExprNum_5();
        return EndExprNum_4(expr5);
    }

    private static Expr2 EndExprNum_4(Expr2 expr5) {
        if (mapOfFirstSets.get("ComparisonOp").contains(sym)) {
            String op = "";
            switch (sym){
                case TokenCodes.less_ -> op = "<";
                case TokenCodes.lessOrEqual_ -> op = "<=";
                case TokenCodes.greater_ -> op = ">";
                case TokenCodes.greaterOrEqual_ -> op = ">=";
            }
            scan();
            Expr2 tmp = ExprNum_5();
            return new Expr2(op, expr5, tmp, "BINARY");
        }
        return expr5;
    }

    private static Expr2 ExprNum_5() {
        Expr2 expr6 = ExprNum_6();
        return ExprNum_5Prim(expr6);
    }

    private static Expr2 ExprNum_5Prim(Expr2 expr6) {
        if (mapOfFirstSets.get("ADD").contains(sym)) {
            String op = sym == TokenCodes.plus_ ? "+" : "-";
            scan();
            Expr2 temp = ExprNum_6();
            return ExprNum_5Prim(new Expr2(op, expr6, temp, "BINARY"));
        }
        return expr6;
    }

    private static Expr2 ExprNum_6() {
        Expr2 expr7 = ExprNum_7();
        return ExprNum_6Prim(expr7);
    }

    private static Expr2 ExprNum_6Prim(Expr2 expr7) {
        if (mapOfFirstSets.get("MUL").contains(sym)) {
            String op = "";
            switch (sym){
                case TokenCodes.mul_ -> op = "*";
                case TokenCodes.div_ -> op = "/";
                case TokenCodes.mod_ -> op = "%";
            }
            scan();
            Expr2 tmp = ExprNum_7();
            return ExprNum_6Prim(new Expr2(op, expr7, tmp, "BINARY"));
        }
        return expr7;
    }

    private static Expr2 ExprNum_7() {
        String op = "";
        if (sym == TokenCodes.not_ || sym == TokenCodes.minus_) {
            if (sym == TokenCodes.not_)
                op = "!";
            else op = "-";
            scan();
        }
        Expr2 tmp = ExprNum_8();
        return op.equals("") ? tmp : new Expr2(op, tmp, null, "UNARY");
    }

    private static Expr2 ExprNum_8() {
        if (mapOfFirstSets.get("Const").contains(sym)) {
            String type = "";
            Expr2 expr = new Expr2("", null, null, null);
            if (sym == TokenCodes.integerConstant_) {
                type = "IntegerConstant";
                expr.setValue(la.valForInt);
            }
            else if (sym == TokenCodes.boolConstant_){
                type = "BooleanConstant";
                expr.setValue(la.string);
            }
            else if (sym == TokenCodes.doubleConstant_) {
                type = "DoubleConstant";
                expr.setValue(la.valForDouble);
            }
            else if (sym == TokenCodes.stringConstant_){
                type = "StringConstant";
                expr.setValue(la.string);
            }

            expr.setKind(type);
            scan();
            return expr ;
        } else if (sym == TokenCodes.identifier_) {
            String identifier = la.string;
            scan();
            if(!mapOfSymbols.containsKey(identifier)){
                error("Missing identifier declaration " + identifier);
            }
            Expr2 expr = new Expr2("", null, null, "Identifier");
            expr.setValue(identifier);
            return expr;
        } else if (sym == TokenCodes.leftParentheses_) {
            scan();
            Expr2 exprNum_2 = ExprNum_2();
            check(TokenCodes.rightParentheses_);
            return exprNum_2;
        } else if (mapOfFirstSets.get("ReadOperations").contains(sym)) {
            String type = "";
            Expr2 expr = new Expr2("", null, null, null);
            if (sym == TokenCodes.readString_)
                type = "READSTRING";
            else if (sym == TokenCodes.readBoolean_)
                type = "READBOOL";
            else if (sym == TokenCodes.readDouble_)
                type = "READDOUBLE";
            else if (sym == TokenCodes.readInteger_)
                type = "READINT";
            expr.setValue(la.string);
            scan();
            expr.setKind(type);
            return expr;
        } else {
            error("Expression was expected");
            scan();
            return null;
        }

    }

    public static void parse() {
        mapOfFirstSets = CreateHashmapsService.createMapOfFirstSets();
        mapOfSymbols = new HashMap<>();
        errors = 0;
        errDist = 3;
        scan();
        Program program = Program();
        if (sym != TokenCodes.eof) error("end of file found before end of program");
        System.out.println();
        System.out.println("***************************************");
        for(String ident : mapOfSymbols.keySet()){
            Couple cp = mapOfSymbols.get(ident);
            System.out.println(" IDENTIFIER " + ident);
            System.out.println(" EXPRESSION " + cp.getType());
            printExpr(cp.getExpr2(), 1);
            System.out.println();
        }
        System.out.println("***************************************");
    }
}
