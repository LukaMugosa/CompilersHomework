package me.ac.ucg.kompajleri.statements_and_expressions;

public class ForStatement extends Statement{

    private AssignmentExpression assignExprLeft;
    private AssignmentExpression assignExprRight;
    private Expr2 expr2;
    private CommandSequence commandSequence;

    public ForStatement(AssignmentExpression assignExprLeft, AssignmentExpression assignExprRight,  Expr2 expr2, CommandSequence commandSequence){
        this.assignExprLeft = assignExprLeft;
        this.assignExprRight = assignExprRight;
        this.expr2 = expr2;
        this.commandSequence = commandSequence;
    }

    public AssignmentExpression getAssignExprLeft() {
        return assignExprLeft;
    }

    public void setAssignExprLeft(AssignmentExpression assignExprLeft) {
        this.assignExprLeft = assignExprLeft;
    }

    public AssignmentExpression getAssignExprRight() {
        return assignExprRight;
    }

    public void setAssignExprRight(AssignmentExpression assignExprRight) {
        this.assignExprRight = assignExprRight;
    }

    public Expr2 getExpr2() {
        return expr2;
    }

    public void setExpr2(Expr2 expr2) {
        this.expr2 = expr2;
    }

    public CommandSequence getCommandSequence() {
        return commandSequence;
    }

    public void setCommandSequence(CommandSequence commandSequence) {
        this.commandSequence = commandSequence;
    }
}
