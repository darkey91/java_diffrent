import java.io.*;
import java.util.Scanner;

public class C {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int last = 0;
        int n = scanner.nextInt();
        int number = 0;
        for (int i = 0; i < n; ++i) {
            number = scanner.nextInt();
            if (i == 0 ||  last != number)
                System.out.println(number);
            last = number;
        }
    }

}