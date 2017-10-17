package ServerClient.basic;

public class StripNonValidXMLCharacters {

    /**

     * This method ensures that the output String has only valid XML unicode characters as specified by the XML 1.0

     * standard.

     * 

     * @param in

     *            The String whose non-valid characters we want to remove.

     * @return The in String, stripped of non-valid characters.

     */

    public static String strip(String in) {

        StringBuffer out = new StringBuffer();

        char current;


        if (in == null || ("".equals(in)))

            return "";

        for (int i = 0; i < in.length(); i++) {

            current = in.charAt(i);

            if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))

                    || ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF)))

                out.append(current);

        }

        return out.toString();

    }


}