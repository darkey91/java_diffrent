import java.io.*;

public class B {
    public static void main(String[] args) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(rd.readLine());
        int ans = 0, cur = 0;
        for (int i = 0; i < n; ++i) {
            int bit = Integer.parseInt(rd.readLine());
            if (bit == 0) cur = 0;
            else ++cur;
            ans = Math.max(cur, ans);
        }
        System.out.println(ans);

    }
}