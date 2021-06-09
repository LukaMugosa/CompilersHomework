package me.ac.ucg.kompajleri.statements_and_expressions;

public class IfStatementEnd {

    private CommandSequence commandSequence;

    public IfStatementEnd(CommandSequence commandSequence) {
        this.commandSequence = commandSequence;
    }

    public CommandSequence getCommandSequence() {
        return commandSequence;
    }

    public void setCommandSequence(CommandSequence commandSequence) {
        this.commandSequence = commandSequence;
    }
}
