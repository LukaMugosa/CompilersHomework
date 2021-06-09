package me.ac.ucg.kompajleri.statements_and_expressions;

public class Program {

    private Declaration[] declarations;
    private CommandSequence commandSequence;

    public Program(Declaration[] declarations, CommandSequence commandSequence) {
        this.declarations = declarations;
        this.commandSequence = commandSequence;
    }

    public Declaration[] getDeclarations() {
        return declarations;
    }

    public void setDeclarations(Declaration[] declarations) {
        this.declarations = declarations;
    }

    public CommandSequence getCommandSequence() {
        return commandSequence;
    }

    public void setCommandSequences(CommandSequence commandSequence) {
        this.commandSequence = commandSequence;
    }
}
