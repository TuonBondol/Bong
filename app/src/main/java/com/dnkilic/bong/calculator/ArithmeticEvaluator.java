package com.dnkilic.bong.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ArithmeticEvaluator {

    private static final int LEFT_ASSOC = 0;
    private static final int RIGHT_ASSOC = 1;

    private static final Map<String, int[]> OPERATORS = new HashMap<>();

    static {
        OPERATORS.put("+", new int[]{0, LEFT_ASSOC});
        OPERATORS.put("-", new int[]{0, LEFT_ASSOC});
        OPERATORS.put("x", new int[]{5, LEFT_ASSOC});
        OPERATORS.put("/", new int[]{5, LEFT_ASSOC});
    }

    private static String[] infixToRPN(String[] inputTokens) {
        ArrayList<String> out = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : inputTokens) {

            if (isOperator(token)) {
                while (!stack.empty() && isOperator(stack.peek())) {
                    if ((isAssociative(token, LEFT_ASSOC) &&
                            cmpPrecedence(token, stack.peek()) <= 0) ||
                            (isAssociative(token, RIGHT_ASSOC) &&
                                    cmpPrecedence(token, stack.peek()) < 0)) {
                        out.add(stack.pop());
                        continue;
                    }
                    break;
                }

                stack.push(token);
            }
            else if (token.equals("(")) {
                stack.push(token);  //
            }
            else if (token.equals(")")) {
                while (!stack.empty() && !stack.peek().equals("(")) {
                    out.add(stack.pop());
                }
                stack.pop();
            }
            else {
                out.add(token);
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop());
        }
        String[] output = new String[out.size()];
        return out.toArray(output);
    }

    private static double RPNtoDouble(String[] tokens) {
        Stack<String> stack = new Stack<>();

        for (String token : tokens) {
            if (!isOperator(token)) {
                stack.push(token);
            } else {
                Double d2 = Double.valueOf(stack.pop());
                Double d1 = Double.valueOf(stack.pop());

                Double result = token.compareTo("+") == 0 ? d1 + d2 :
                        token.compareTo("-") == 0 ? d1 - d2 :
                                token.compareTo("x") == 0 ? d1 * d2 :
                                        d1 / d2;

                stack.push(String.valueOf(result));
            }
        }

        return Double.valueOf(stack.pop());
    }

    private static final int cmpPrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            throw new IllegalArgumentException("Invalid tokens: " + token1
                    + " " + token2);
        }
        return OPERATORS.get(token1)[0] - OPERATORS.get(token2)[0];
    }

    private static boolean isAssociative(String token, int type) {
        if (!isOperator(token)) {
            throw new IllegalArgumentException("Invalid token: " + token);
        }

        return OPERATORS.get(token)[1] == type;
    }

    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    public Double CalculateArithmeticExpression(ArrayList<String> expression) {
        String input = expression.get(0);
        String[] inputVector = input.split(" ");
        String[] output = infixToRPN(inputVector);

        Double result = RPNtoDouble(output);
        return result;
    }

}
