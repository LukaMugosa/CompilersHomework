package me.ac.ucg.kompajleri.statements_and_expressions;

public class CommandSequence {

    private Statement[] statements;

    public CommandSequence(Statement[] statements) {
        this.statements = statements;
    }

    public Statement[] getStatements() {
        return statements;
    }

    public void setStatement(Statement[] statements) {
        this.statements = statements;
    }
}
