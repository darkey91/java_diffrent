import java.io.*;

public class D {
    int n;
    StringBuilder str;

    private void fill(StringBuilder string, int amount, char sym) {
        for (int i = 0; i < amount; ++i) {
            string.append(sym);
        }
    }

    private boolean getAndPrintNext() {
        int balance = 0;
        for (int i = n * 2 - 1; i >= 0; --i ) {
            if (str.charAt(i) == ')')
                ++balance;
            else
                -- balance;
            if (str.charAt(i) == '(' && balance > 0) {
                --balance;
                str = new StringBuilder(str.substring(0, i).concat(")"));
                int open = (2 * n - i - 1 - balance) / 2;
                fill(str, open,'(');
                fill(str, 2 * n - i - 1 - open, ')');
                return true;
            }
        }

        return false;
    }

    public void solve() throws IOException {
        n = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
        str = new StringBuilder();
        for (int k = 0; k < n; ++k) {
            str.append('(');
        }
        for (int k = 0; k < n; ++k) {
            str.append(')');
        }
        System.out.println(str.toString());

        while(getAndPrintNext()) {
            System.out.println(str.toString());
        }
    }
    public static void main(String[] args) throws IOException {
        new D().solve();
    }
}