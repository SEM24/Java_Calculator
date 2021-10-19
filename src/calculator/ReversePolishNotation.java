package calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

import static calculator.CalcTools.*;


public class ReversePolishNotation {

    /**
     * @param expression arrayList with our divided "expression"
     * @return output arrayList sorted by reverse polish notation
     */
    public static ArrayList<String> reversePolishNotation(ArrayList<String> expression) {
        ArrayList<String> output = new ArrayList<>();
        Stack<String> stackOp = new Stack<>();

        for (String value : expression) {
            //If we have a number, add it to our outPut list
            if (isOperand(value)) {
                output.add(value);
            }
            if (!isOperand(value) && !operators.contains(value)) {
                output.add(value);
            }
            //If we have in our expression operator, do this case
            //Если больше 2-х знаков, это ошибка, поэтому проверяю value.length() <= 2
            else if (operators.contains(value) && value.length() <= 2) {
                //peek() - check who was last in stack

                //Если оператор из стека меньше оператора значения, пушим
                if (stackOp.isEmpty() || getPriority(stackOp.peek()) < getPriority(value)) {
                    stackOp.push(value);
                } else if (getPriority(stackOp.peek()) == getPriority(value)) {
                    output.add(stackOp.peek());
                    stackOp.pop();
                    stackOp.push(value);
                } else if (getPriority(stackOp.peek()) >= getPriority(value)) {

                    //Revers the stack
                    Collections.reverse(stackOp);
                    //Add all elements from reversed stack into our output list
                    output.addAll(stackOp);
                    //Clean the stack
                    stackOp.clear();
                    //Add new element into stack
                    stackOp.push(value);
                }
            }

        }
        //If in the end we have operators in stack, push them into output list
        if (!stackOp.isEmpty()) {
            //Revers the stack
            Collections.reverse(stackOp);
            //Add all from stack to our list
            output.addAll((stackOp));
            //Clean the stack from old elements
            stackOp.clear();
        }
        return output;
    }


    public static Double calculate(ArrayList<String> expression, HashMap<String, Double> variables) {
        //last in - first out "(LIFO)
        Stack<String> stack = new Stack<>();

        //Loop though the array with our elements
        for (String value : expression) {
            if (isOperand(value)) {
                //Put inside our stack if we find the operand
                stack.push(value);
            }
            //If it's not number or operator
            if (!isOperand(value) && !operators.contains(value)) {
                //Replace the letter to number
                if (variables.containsKey(value)) {
                    stack.push(String.valueOf(variables.get(value)));
                } else {
                    throw new IllegalArgumentException(getErrorMessage(1));
                }
            }

            //If we find the operator from list, then do this case
            //after the needed case was found, we push the result to our stack and continue calculate
            else if (operators.contains(value)) {
                //Put inside the variable the last input number and .pop() - delete from our stack
                double first = Double.parseDouble(stack.pop());
                //Put inside the variable the next last input number and .pop() - delete from our stack
                double second = Double.parseDouble(stack.pop());
                //Search for needed operator though the switch
                switch (value) {
                    case IOperators.PLUS -> stack.push("" + (first + second));
                    case IOperators.MINUS -> stack.push("" + (second - first));
                    case IOperators.MULT -> stack.push("" + (first * second));
                    case IOperators.DIVIDE -> stack.push("" + (second / first));
                    case IOperators.DEGREE -> stack.push("" + Math.pow(first, second));
                    default -> throw new IllegalArgumentException(getErrorMessage(4));
                }
            }

        }
        //Return the result from our stack and clean it
        return Double.parseDouble(stack.pop());
    }

}
