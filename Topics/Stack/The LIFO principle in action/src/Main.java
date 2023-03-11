import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Deque<Integer> stack = new ArrayDeque<>();
        Scanner scanner = new Scanner(System.in);
        int numberOfElements = scanner.nextInt();
        for (int i = 0; i < numberOfElements; i++){
            stack.add(scanner.nextInt());
        }
        for (int i = 0; i < numberOfElements; i++){
            System.out.println(stack.pollLast());
        }
    }
}