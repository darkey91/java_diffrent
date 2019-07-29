import java.io.*;

public class A {
    public int lengthOfLongestSubstring(String s) {

        int answer = 1;
        for (int i = 0; i < s.length(); ++i) {
            boolean[] letter = new boolean[26];
            letter[s.charAt(i) - 'a'] = true;
            int cur = 1;
            for (int j = i + 1; j < s.length(); ++j) {
                if (letter[s.charAt(j) - 'a']) {
                    break;
                }
                ++cur;
                answer = Math.max(cur, answer);
            }
        }

        return answer;
    }

    public static void main(String[] args) throws IOException {
        String s = "-91283472332";
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            ++i;
        }
        if (s.length() == i) return ;
        boolean positive = s.charAt(i) != '-';

        if (!positive)
            ++i;

        int temp = i;

        while (temp < s.length() && Character.isDigit(s.charAt(temp))) ++temp;

        int radix = temp - i;
        int m = 1;
        if (radix > 11) return Integer.MAX_VALUE ;
        for (int j = 1; j < radix; ++j) {
            m *= 10;
        }
        long answer = 0;

        while (i != temp) {
            answer += m * (s.charAt(i) - '0');
            m /= 10;
            if (answer * (positive ? 1 : -1) > Integer.MAX_VALUE || answer * (positive ? 1 : -1) < Integer.MIN_VALUE) return ;
            ++i;
        }


        System.out.println((int) (answer * (positive ? 1 : -1)));
    }
}