package me.ac.ucg.kompajleri.services;

import me.ac.ucg.kompajleri.Token;
import me.ac.ucg.kompajleri.constants.DataTypesConstants;
import me.ac.ucg.kompajleri.constants.OperatorsConstants;
import me.ac.ucg.kompajleri.constants.TokenCodes;
import me.ac.ucg.kompajleri.constants.TokenValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class CreateHashmapsService {

    public static HashMap<String, Integer> createKeywordsMap() {
        HashMap<String, Integer> keywords = new HashMap<>();
        keywords.put("LET", TokenCodes.let_);
        keywords.put("IN", TokenCodes.in_);
        keywords.put("END", TokenCodes.end_);
        keywords.put("IF", TokenCodes.if_);
        keywords.put("THEN", TokenCodes.then_);
        keywords.put("FI", TokenCodes.fi_);
        keywords.put("ELSE", TokenCodes.else_);
        keywords.put("WHILE", TokenCodes.while_);
        keywords.put("FOR", TokenCodes.for_);
        keywords.put("BREAK", TokenCodes.break_);
        keywords.put("PRINT", TokenCodes.print_);
        keywords.put("READINT", TokenCodes.readInteger_);
        keywords.put("READDOUBLE", TokenCodes.readDouble_);
        keywords.put("READBOOL", TokenCodes.readBoolean_);
        keywords.put("READSTRING", TokenCodes.readString_);
        keywords.put("DO", TokenCodes.doWhile_);
        return keywords;
    }

    public static HashMap<String, Integer> createDataTypesMap() {
        HashMap<String, Integer> dataTypes = new HashMap<>();
        dataTypes.put(DataTypesConstants.INTEGER, TokenCodes.integer_);
        dataTypes.put(DataTypesConstants.DOUBLE, TokenCodes.double_);
        dataTypes.put(DataTypesConstants.BOOLEAN, TokenCodes.bool_);
        dataTypes.put(DataTypesConstants.STRING, TokenCodes.string_);
        return dataTypes;
    }

    public static HashMap<String, Integer> createTokensMap() {
        HashMap<String, Integer> tokensMap = new HashMap<>();
        tokensMap.put("identifier", TokenCodes.identifier_);
        tokensMap.put(TokenValues.plus_, TokenCodes.plus_);
        tokensMap.put(TokenValues.minus_, TokenCodes.minus_);
        tokensMap.put(TokenValues.mul_, TokenCodes.mul_);
        tokensMap.put(TokenValues.div_, TokenCodes.div_);
        tokensMap.put(TokenValues.mod_, TokenCodes.mod_);
        tokensMap.put(TokenValues.less_, TokenCodes.less_);
        tokensMap.put(TokenValues.lessOrEqual_, TokenCodes.lessOrEqual_);
        tokensMap.put(TokenValues.greater_, TokenCodes.greater_);
        tokensMap.put(TokenValues.greaterOrEqual_, TokenCodes.greaterOrEqual_);
        tokensMap.put(TokenValues.assign_, TokenCodes.assign_);
        tokensMap.put(TokenValues.equal_, TokenCodes.equal_);
        tokensMap.put(TokenValues.notEqual_, TokenCodes.notEqual_);
        tokensMap.put(TokenValues.and_, TokenCodes.and_);
        tokensMap.put(TokenValues.or_, TokenCodes.or_);
        tokensMap.put(TokenValues.not_, TokenCodes.not_);
        tokensMap.put(TokenValues.semicolon_, TokenCodes.semicolon_);
        tokensMap.put(TokenValues.comma_, TokenCodes.comma_);
        tokensMap.put(TokenValues.period_, TokenCodes.period_);
        tokensMap.put(TokenValues.leftParentheses_, TokenCodes.leftParentheses_);
        tokensMap.put(TokenValues.rightParentheses_, TokenCodes.rightParentheses_);
        tokensMap.put(TokenValues.leftCurlyBracket_, TokenCodes.leftCurlyBracket_);
        tokensMap.put(TokenValues.rightCurlyBracket_, TokenCodes.rightCurlyBracket_);
        return tokensMap;
    }

    public static HashMap<String, Integer> createOperatorsMap() {
        HashMap<String, Integer> operatorsMap = new HashMap<>();
        operatorsMap.put(OperatorsConstants.plus_, TokenCodes.plus_);
        operatorsMap.put(OperatorsConstants.minus_, TokenCodes.minus_);
        operatorsMap.put(OperatorsConstants.mul_, TokenCodes.mul_);
        operatorsMap.put(OperatorsConstants.div_, TokenCodes.div_);
        operatorsMap.put(OperatorsConstants.mod_, TokenCodes.mod_);
        operatorsMap.put(OperatorsConstants.less_, TokenCodes.less_);
        operatorsMap.put(OperatorsConstants.lessOrEqual_, TokenCodes.lessOrEqual_);
        operatorsMap.put(OperatorsConstants.greater_, TokenCodes.greater_);
        operatorsMap.put(OperatorsConstants.greaterOrEqual_, TokenCodes.greaterOrEqual_);
        operatorsMap.put(OperatorsConstants.equal_, TokenCodes.equal_);
        operatorsMap.put(OperatorsConstants.notEqual_, TokenCodes.notEqual_);
        operatorsMap.put(OperatorsConstants.and_, TokenCodes.and_);
        operatorsMap.put(OperatorsConstants.or_, TokenCodes.or_);
        operatorsMap.put(OperatorsConstants.assign_, TokenCodes.assign_);
        return operatorsMap;
    }

    public static HashMap<String, ArrayList<Integer>> createMapOfFirstSets() {
        HashMap<String, ArrayList<Integer>> mapOfFirstSets = new HashMap<>();
        mapOfFirstSets.put("Declarations",
                new ArrayList<>(Arrays.asList(
                        TokenCodes.integer_,
                        TokenCodes.bool_,
                        TokenCodes.string_,
                        TokenCodes.double_
                )
                )
        );
        mapOfFirstSets.put("Type",
                new ArrayList<>(Arrays.asList(
                        TokenCodes.integer_,
                        TokenCodes.bool_,
                        TokenCodes.string_,
                        TokenCodes.double_
                )
                )
        );
        mapOfFirstSets.put("CommandSequence",
                new ArrayList<>(Arrays.asList(
                        TokenCodes.if_,
                        TokenCodes.doWhile_,
                        TokenCodes.while_,
                        TokenCodes.for_,
                        TokenCodes.break_,
                        TokenCodes.print_,
                        TokenCodes.identifier_,
                        TokenCodes.integerConstant_,
                        TokenCodes.boolConstant_,
                        TokenCodes.stringConstant_,
                        TokenCodes.doubleConstant_,
                        TokenCodes.leftParentheses_,
                        TokenCodes.readInteger_,
                        TokenCodes.readString_,
                        TokenCodes.readDouble_,
                        TokenCodes.readBoolean_
                )
                )
        );
        mapOfFirstSets.put("ExprNum_2",
                new ArrayList<>(Arrays.asList(
                        TokenCodes.identifier_,
                        TokenCodes.integerConstant_,
                        TokenCodes.boolConstant_,
                        TokenCodes.stringConstant_,
                        TokenCodes.doubleConstant_,
                        TokenCodes.leftParentheses_,
                        TokenCodes.readInteger_,
                        TokenCodes.readString_,
                        TokenCodes.readDouble_,
                        TokenCodes.readBoolean_
                )
                )
        );
        mapOfFirstSets.put("LogOp", new ArrayList<>(Arrays.asList(TokenCodes.and_, TokenCodes.or_)));

        mapOfFirstSets.put("EqualityOp", new ArrayList<>(Arrays.asList(TokenCodes.equal_, TokenCodes.notEqual_)));

        mapOfFirstSets.put("ComparisonOp",
                new ArrayList<>(Arrays.asList(
                        TokenCodes.less_,
                        TokenCodes.lessOrEqual_,
                        TokenCodes.greater_,
                        TokenCodes.greaterOrEqual_
                )
                )
        );
        mapOfFirstSets.put("ADD", new ArrayList<>(Arrays.asList(TokenCodes.plus_, TokenCodes.minus_)));

        mapOfFirstSets.put("MUL", new ArrayList<>(Arrays.asList(TokenCodes.mul_, TokenCodes.div_, TokenCodes.mod_)));

        mapOfFirstSets.put("NegationOp", new ArrayList<>(Arrays.asList(TokenCodes.not_, TokenCodes.minus_)));

        mapOfFirstSets.put("Const",
                new ArrayList<>(Arrays.asList(
                        TokenCodes.integerConstant_,
                        TokenCodes.boolConstant_,
                        TokenCodes.stringConstant_,
                        TokenCodes.doubleConstant_
                )
                )
        );

        mapOfFirstSets.put("ReadOperations",
                new ArrayList<>(Arrays.asList(
                        TokenCodes.readInteger_,
                        TokenCodes.readString_,
                        TokenCodes.readBoolean_,
                        TokenCodes.readDouble_
                )
                )
        );
        mapOfFirstSets.put("DO", new ArrayList<>(Collections.singletonList(TokenCodes.doWhile_)));

        return mapOfFirstSets;
    }
}
