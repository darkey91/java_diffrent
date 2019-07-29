import java.io.*;

public class E {
    public static void main(String[] args) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        String first = rd.readLine();
        String second = rd.readLine();
        if (first.length() != second.length()) {
            System.out.println(0);
            return;
        }

        int[] let = new int[26];
        for (int i = 0; i < first.length(); ++i) {
            ++let[first.charAt(i) - 'a'];
        }
        for (int i = 0; i < second.length(); ++i) {
            --let[second.charAt(i) - 'a'];
        }

        for (int i = 0; i < 26; ++i)
            if (let[i] != 0) {
                System.out.println(0);
                return;
            }
        System.out.println(1);
    }
}
