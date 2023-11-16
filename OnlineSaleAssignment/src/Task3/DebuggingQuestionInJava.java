package Task3;

public class DebuggingQuestionInJava {
    public static int calculate(int n) {
        if (n < 10) {
            return n * n; // Calculate square
        } else if (n >= 10 && n <= 20) {
            return factorial(n - 10); // Calculate factorial
        } else {
            return sumIntegers(n - 20); // Calculate sum of integers
        }
    }

    // Method to calculate factorial
    private static int factorial(int num) {
        if (num <= 1) {
            return 1;
        }
        return num * factorial(num - 1);
    }

    // Method to calculate sum of integers
    private static int sumIntegers(int num) {
        return (num * (num + 1)) / 2;
    }

    public static void main(String[] args) {
        int num1 = 4;
        int num2 = 15;
        int num3 = 25;

        System.out.println("Result for " + num1 + ": " + calculate(num1));
        System.out.println("Result for " + num2 + ": " + calculate(num2));
        System.out.println("Result for " + num3 + ": " + calculate(num3));
    }
}
