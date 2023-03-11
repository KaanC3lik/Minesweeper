import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        Deque<Character> deque= new ArrayDeque<>();

        StringBuilder input = new StringBuilder(scanner.nextLine());
        String bracket1 = "{}";
        String bracket2 = "[]";
        String bracket3 = "()";
        for (int i = 0; i < input.length(); i ++) {
            if (i < input.length() - 1) {
                if (    (input.charAt(i) == bracket1.charAt(0) && input.charAt(i + 1) == bracket1.charAt(1)) ||
                        (input.charAt(i) == bracket2.charAt(0) && input.charAt(i + 1) == bracket2.charAt(1)) ||
                        (input.charAt(i) == bracket3.charAt(0) && input.charAt(i + 1) == bracket3.charAt(1))){
                    input.deleteCharAt(i + 1);
                    input.deleteCharAt(i);
                    i = -1;
                }
            }
        }
        if (input.length() > 0) {
            System.out.println(false);
        } else {
            System.out.println(true);
        }
    }
}