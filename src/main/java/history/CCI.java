package history;

import java.util.ArrayList;
import java.util.List;

/**
 * http://future.aae.wisc.edu/data/monthly_values/by_area/998?area=US
 */
public class CCI {

    private static final int YEAR = 2015;

    public static String getString() {
        return "Jan\t103.80\n" +
                "Feb\t98.80\n" +
                "Mar\t101.30\n" +
                "Apr\t94.30\n" +
                "May\t94.60\n" +
                "Jun\t99.80\n" +
                "Jul\t91.00\n" +
                "Aug\t101.30\n" +
                "Sep\t102.60\n" +
                "Oct\t99.10\n" +
                "Nov\t90.40\n" +
                "Dec\t96.30";
    }

    public static void main(String[] args) {
        List<Double> digits = extract(getString());
        if (digits.size() != 12) {
            throw new IllegalStateException("Size is " + digits.size());
        }

        List<String> scripts = createScripts(YEAR, digits);
        for (String script : scripts) {
            System.out.println(script);
        }
    }


    private static List<String> createScripts(int year, List<Double> digits) {
        List<String> scripts = new ArrayList<>();

        for (int i = 0; i < digits.size(); i++) {
            scripts.add("insert into indicator(date,type,value) values ('" + year + "-" + prettyMonth(i + 1) + "-01', 'CCI', " + digits.get(i) + ");");
        }

        return scripts;
    }

    private static String prettyMonth(int i) {
        String str = "" + i;

        if (str.length() == 1) {
            return "0" + str;
        }

        return str;
    }

    private static List<Double> extract(String string) {
        List<Double> doubles = new ArrayList<>();

        String nextDigit = "";
        for (int i = 0; i < string.length(); i++) {
            if (isDigitOrDot(string.charAt(i))) {
                nextDigit += string.charAt(i);
            } else if (!nextDigit.isEmpty()) {
                doubles.add(Double.valueOf(nextDigit));
                nextDigit = "";
            }
        }

        if (!nextDigit.isEmpty()) {
            doubles.add(Double.valueOf(nextDigit));
        }

        return doubles;
    }

    private static boolean isDigitOrDot(char c) {
        return c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5'
                || c == '6' || c == '7' || c == '8' || c == '9' || c == '.';
    }


}
