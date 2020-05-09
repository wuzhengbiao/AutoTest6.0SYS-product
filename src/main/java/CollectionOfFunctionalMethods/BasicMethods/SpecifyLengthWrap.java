package CollectionOfFunctionalMethods.BasicMethods;

public class SpecifyLengthWrap {
    public static String getStringByEnter(int length, String string) throws Exception
    {
        for (int i = 1; i <= string.length(); i++)
        {
            if (string.substring(0, i).getBytes("GBK").length > length)
            {
                return string.substring(0, i - 1) + "\n" +
                        getStringByEnter(length, string.substring(i - 1));
            }
        }
        return string;
    }
}
