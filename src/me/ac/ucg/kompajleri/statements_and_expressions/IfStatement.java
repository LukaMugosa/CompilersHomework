package me.ac.ucg.kompajleri.statements_and_expressions;

public class IfStatement extends Statement{

    private Expr2 expr2;
    private CommandSequence commandSequence;
    private IfStatementEnd ifStmtEnd;

    public IfStatement(Expr2 expr2, CommandSequence commandSequence,IfStatementEnd ifStmtEnd ){
        this.expr2 = expr2;
        this.commandSequence = commandSequence;
        this.ifStmtEnd = ifStmtEnd;
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

    public IfStatementEnd getIfStmtEnd() {
        return ifStmtEnd;
    }

    public void setIfStmtEnd(IfStatementEnd ifStmtEnd) {
        this.ifStmtEnd = ifStmtEnd;
    }
}
