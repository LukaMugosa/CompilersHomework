package me.ac.ucg.kompajleri.statements_and_expressions;

public class RepeatStatement extends Statement{

    private Expr2 expr2;
    private CommandSequence commandSequence;

    public RepeatStatement(Expr2 expr2, CommandSequence commandSequence) {
        this.expr2 = expr2;
        this.commandSequence = commandSequence;
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
