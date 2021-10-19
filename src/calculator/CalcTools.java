package calculator;

import java.util.Arrays;
import java.util.List;

/**
 * Class with additional tools for calculator
 */
public class CalcTools {

    //List with all our operators
    public static List<String> operators = Arrays.asList(IOperators.PLUS, IOperators.MINUS,
            IOperators.MULT, IOperators.DIVIDE, IOperators.DEGREE);

    public static List<String> unaryOp = Arrays.asList(IOperators.PLUS, IOperators.MINUS);

    /**
     * This method returns the priority of our type depends on case
     *
     * @param type in our expression
     * @return the priority(number) of our type
     */
    public static String getErrorMessage(int type) {
        return switch (type) {
            case 1 -> "Incorrect formula entry!";
            case 2 -> "Incorrect variable entry!";
            case 3 -> "The field is empty! Please check your input.";
            case 4 -> "Unknown operator";
            case 5 -> "Multiple point error";
            default -> "Unknown error...";
        };
    }

    /**
     * This method returns the priority of our operator depends on case
     *
     * @param operator in our expression
     * @return the priority(number) of our operator
     */
    public static int getPriority(String operator) {
        return switch (operator) {
            case IOperators.PLUS, IOperators.MINUS -> 1;
            case IOperators.MULT, IOperators.DIVIDE -> 2;
            case IOperators.DEGREE -> 3;
            default -> 0;
        };
    }

    /**
     * This method check if the input string is operand
     *
     * @param operand our "number"
     * @return true/false result depends on is it number or not
     */
    public static boolean isOperand(String operand) {
        boolean checker = false;
        for (int i = 0; i < operand.length(); i++) {
            if (Character.isDigit(operand.charAt(i))) {
                checker = true;
            }
        }
        return checker;
    }
}
