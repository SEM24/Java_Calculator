package calculator;

import java.util.ArrayList;
import java.util.HashMap;

import static calculator.CalcTools.getErrorMessage;
import static calculator.CalcTools.unaryOp;
import static calculator.ReversePolishNotation.calculate;
import static calculator.ReversePolishNotation.reversePolishNotation;

/**
 * Task: At the entrance of the program in program arguments(String[] args) the first parameter is a mathematical expression
 * The other parameters look like the name of the variable = value, for example a = 2
 * We have to parse the expression one time and then to calculate the result of mathematical task
 * (if we parse one time, it will be faster, than every time)
 * Example of expression: 62*5+a-9/b+47/2^2-5 a=4.5 b=2
 */
public class Calculator {

    public static void main(String[] args) {
        HashMap<String, Double> variables = new HashMap<>();
        if (args.length > 0) {
            //first i element is formula, so i skip it and check another gaps
            //use cycle to find variables and write them inside the map
            for (int i = 1; i < args.length; i++) {
                findVariables(args[i], variables);
            }
            printResult(args[0], variables);
        }
        if (args.length == 0) {
            throw new IllegalArgumentException(getErrorMessage(3));
        }
    }


    /**
     * @param formula   our expression that we have to calculate
     * @param variables our numbers that we will replace in expression
     */
    public static void printResult(String formula, HashMap<String, Double> variables) {
        System.out.println("Hello, it's a java calculator\n" +
                "Operators: + - * /\n" +
                "Expression example: 1+2+3/2");

        System.out.println("\nSolve the expression: " + formula + " " +
                String.valueOf(variables).replace("{", "").replace("}", ""));

        ArrayList<String> sort = reversePolishNotation(findFormula(formula.replaceAll("\\s+", ""), variables));
        //Show sorted list by reversePolishNotation
        System.out.println(sort);
        System.out.println("Result: " + calculate(sort, variables));
    }


    /**
     * Find the variables in main arg, put the input variables inside the variable map like key = value
     *
     * @param expression our expression that we have to calculate
     * @param variables  our numbers that we will replace in expression
     */
    private static void findVariables(String expression, HashMap<String, Double> variables) {
        //Char to save the key
        char key = '0';
        StringBuilder result = new StringBuilder();
        //Loop through the expression
        for (int i = 0; i < expression.length(); i++) {
            //element like 2,3,4 etc and replace "," into "." to have to problem in future calculating
            char element = expression.replace(',', '.').charAt(i);
            //Check if the element from expression is letter
            if (Character.isAlphabetic(element)) {
                key = element;
            }
            //Check if the element from expression is number
            if (Character.isDigit(element) || unaryOp.contains(String.valueOf(element))) {
                result.append(element);
            }
            //If the number is like 2.5, write this dot to our number
            else if (element == '.') {
                result.append(".");
            }
        }
        //Save the result to our map
        if (result.length() > 0) {
            variables.put("" + key, Double.parseDouble(result.toString()));
        }
    }

    /**
     * In this class we decompose expression into elements and write each element into the list
     *
     * @param expression our "mathematical equation"
     * @return result list with each elements from our expression in the list
     */
    private static ArrayList<String> findFormula(String expression, HashMap<String, Double> variables) {

        boolean digit = false;
        boolean unaryOperator = false;
        //List with unary operators to check if the expression contains it

        ArrayList<String> result = new ArrayList<>();

        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            //Before we start, replace element "," into "." to have to problems in future
            char element = expression.replace(',', '.').charAt(i);

            //Find the number in line or dot from number
            if (Character.isDigit(element) || element == '.') {
                //Use flag to show that it's not operator, but it's a number
                unaryOperator = false;
                digit = true;
                //update our StringBuilder with new digit element
                buffer.append(element);

                //If it's last number, put the number inside the list
                if (expression.length() - 1 == i) {
                    result.add(buffer.toString());
                }
                // if (element == '.')

            }
            //If we find the letter, then use this case
            else if (Character.isAlphabetic(element)) {
                if (variables.containsKey(String.valueOf(element))) {
                    unaryOperator = false;
                    result.add(String.valueOf(element));
                } else throw new IllegalArgumentException(getErrorMessage(2));
            }

            //If it's not number, then it's operator and we have to work with another case
            else {
                //If the flag was true, add all buffer into list, clean it and make flag false
                if (digit) {
                    result.add(buffer.toString());
                    buffer.setLength(0);
                    digit = false;
                }
                //Check if the flag is TRUE
                //skip this check if I haven't found an operator yet
                if (unaryOperator || i == 0) {
                    //Check if the list of our unary operators contains element from expression
                    if (unaryOp.contains(String.valueOf(element))) {
                        buffer.append(element);
                    } else {
                        throw new IllegalArgumentException(getErrorMessage(1));
                    }
                } else {
                    //If the last operator, throw an error, for example 1+
                    if (i == expression.length() - 1) {
                        throw new IllegalArgumentException(getErrorMessage(1));
                    }
                    result.add(String.valueOf(element));
                    //If i find the operator
                    unaryOperator = true;
                }
            }
        }
        return result;
    }
}
