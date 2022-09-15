package com.marcosavard.commons.text.encoding;

public class AccentPostponer {
    private static Decoder decoder = new Decoder();
    private static Encoder encoder = new Encoder();

    public static Decoder getDecoder() {
        return decoder;
    }

    public static Encoder getEncoder() {
        return encoder;
    }

    private static final String[][] CODES = new String[][] { //
            //00c0 to 00cf
            new String[] {"A`", "\u00c0",  "&Agrave;"},
            new String[] {"A'", "\u00c1",  "&Aacute;"},
            new String[] {"A\\^", "\u00c2", "&Acirc;"},
            new String[] {"A~", "\u00c3",  "&Atilde;"},
            new String[] {"A:", "\u00c4",  "&Auml;"},
            new String[] {"A\\.", "\u00c5", "&Aring;"},
            new String[] {"AE", "\u00c6", "&AElig;"},
            new String[] {"C,", "\u00ec", "&Ccedil;"},
            new String[] {"E`", "\u00c8", "&Eacute;"},
            new String[] {"E'", "\u00c9", "&Egrave"},
            new String[] {"E\\^", "\u00ca", "&Ecirc;"},
            new String[] {"E:", "\u00cb", "&Euml;"},
            new String[] {"I`", "\u00cc", "&Igrave;"},
            new String[] {"I'", "\u00cd", "&Iacute;"},
            new String[] {"I\\^", "\u00ce", "&Icirc;"},
            new String[] {"I:", "\u00cf", "&Iuml;"},

            //00d0 to 00df
            new String[] {"D\\\\", "\u00d0", "&ETH;"},
            new String[] {"N~", "\u00d1", "&Ntilde;"},
            new String[] {"O`", "\u00d2", "&Ograve;"},
            new String[] {"O'", "\u00d3", "&Oacute;"},
            new String[] {"O\\^", "\u00d4", "&Ocirc;"},
            new String[] {"O~", "\u00d5", "&Otilde;"},
            new String[] {"O:", "\u00d6", "&Ouml;"},
            new String[] {"x/", "\u00d7", "&times;"},
            new String[] {"O/", "\u00d8", "&Oslash;"},
            new String[] {"U`", "\u00d9", "&Ugrave;"},
            new String[] {"U'", "\u00da", "&Uacute;"},
            new String[] {"U\\^", "\u00db", "&Ucirc;"},
            new String[] {"U:", "\u00dc", "&Uuml;"},
            new String[] {"Y'", "\u00dd", "&Yacute;"},
            new String[] {"\\|p", "\u00de", "&THORN;"},
            new String[] {"sz", "\u00df", "&szlig;"},

            //00e0 to 00ef
            new String[] {"a`", "\u00e0",  "&agrave;"},
            new String[] {"a'", "\u00e1",  "&aacute;"},
            new String[] {"a\\^", "\u00e2", "&acirc;"},
            new String[] {"a~", "\u00e3",  "&atilde;"},
            new String[] {"a:", "\u00e4",  "&auml;"},
            new String[] {"a\\.", "\u00e5", "&aring;"},
            new String[] {"ae", "\u00e6", "&aelig;"},
            new String[] {"c,", "\u00e7", "&ccedil;"},
            new String[] {"e`", "\u00e8", "&eacute;"},
            new String[] {"e'", "\u00e9", "&egrave"},
            new String[] {"e\\^", "\u00ea", "&ecirc;"},
            new String[] {"e:", "\u00eb", "&euml;"},
            new String[] {"i`", "\u00ec", "&igrave;"},
            new String[] {"i'", "\u00ed", "&iacute;"},
            new String[] {"i\\^", "\u00ee", "&icirc;"},
            new String[] {"i:", "\u00ef", "&iuml;"},

            //00f0 to 00ff
            new String[] {"d\\\\", "\u00f0", "&eth;"},
            new String[] {"n~", "\u00f1", "&ntilde;"},
            new String[] {"o`", "\u00f2", "&ograve;"},
            new String[] {"o'", "\u00f3", "&oacute;"},
            new String[] {"o\\^", "\u00f4", "&ocirc;"},
            new String[] {"o~", "\u00f5", "&otilde;"},
            new String[] {"o:", "\u00f6", "&ouml;"},
            new String[] {":/", "\u00f7", "&div;"},
            new String[] {"o/", "\u00f8", "&oslash;"},
            new String[] {"u`", "\u00f9", "&ugrave;"},
            new String[] {"u'", "\u00fa", "&uacute;"},
            new String[] {"u\\^", "\u00fb", "&ucirc;"},
            new String[] {"u:", "\u00fc", "&uuml;"},
            new String[] {"y'", "\u00fd", "&yacute;"},
            new String[] {"\\|p", "\u00fe", "&thorn;"},
            new String[] {"y:", "\u00ff", "&yuml;"},
    };


    public static class Decoder {
        public String decode(String encoded) {
            String decoded = encoded;

            for (String[] entry : CODES) {
                decoded = decoded.replaceAll(entry[0], entry[1]);
            }

            return decoded;
        }
    }

    public static class Encoder {

        public String encode(String decoded) {
            String encoded = decoded;

            for (String[] entry : CODES) {
                encoded = encoded.replaceAll(entry[1], entry[0]);
            }

            return encoded;
        }
    }
}
