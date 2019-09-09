package com.olkhovyi.mykhailo.parser;

import com.olkhovyi.mykhailo.ast.BinaryExpression;
import com.olkhovyi.mykhailo.ast.Expression;
import com.olkhovyi.mykhailo.ast.NumberExpression;
import com.olkhovyi.mykhailo.ast.UnaryExpression;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final Token EOF = new Token(TokenType.EOF, null);

    private List<Token> tokens;
    private int pos;
    private final int size;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        size = tokens.size();
    }

    public List<Expression> parse () {
        final List<Expression> result = new ArrayList<>();
        while(!match(TokenType.EOF)) {
            result.add(expression());
        }
        return result;
    }

    private Expression expression() {
        return additive();
    }
    private Expression additive() {
        Expression result = multiplicative();

        while(true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression('+', result, additive());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression('-', result, additive());
                continue;
            }
            break;
        }
        return result;
    }

    private Expression multiplicative() {
        Expression result = unary();

        while(true) {
            if (match(TokenType.STAR)) {
                result = new BinaryExpression('*', result, unary());
                continue;
            }
            if (match(TokenType.SLASH)) {
                result = new BinaryExpression('/', result, unary());
                continue;
            }
            break;
        }
        return result;
    }

    private Expression unary() {
        if(match(TokenType.MINUS)) {
            return new UnaryExpression('-', primary());
        }
        return primary();

    }

    private Expression primary() {
        final Token current = get(0);

        if (match(TokenType.NUMBER)) {
            return new NumberExpression(Double.parseDouble(current.getText()));
        }
        if (match(TokenType.HEX)) {
            return new NumberExpression(Long.parseLong(current.getText(), 16));
        }
        if (match(TokenType.LPAREN)) {
            Expression result =  expression();
            match(TokenType.RPAREN);
            return result;
        }
        throw new RuntimeException("Невідомий вираз");
    }

    private boolean match(TokenType type) {
        final Token current = get(0);

        if (type != current.getType()) {
            return false;
        }
        pos++;
        return true;
    }

    private Token get(int relativePosition) {
        final int position = pos + relativePosition;
        if (position >= size) return EOF;
        return tokens.get(position);
    }
}
