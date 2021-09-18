package co.ledger.util;

public class StringUtil {

    public static String DELIMITER = " ";

    public static String[] getStringArray(String string) {
        return string.split(DELIMITER);
    }
}
