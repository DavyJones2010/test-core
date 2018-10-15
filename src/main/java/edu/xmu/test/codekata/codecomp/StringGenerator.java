package edu.xmu.test.codekata.codecomp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringGenerator implements ColumnGenerator {
    private static final String CHARS = " abcdef ghijklmn opqrst uvwxyz ABCDEFG HIJKLMN OPQRST UVWXYZ 01234 56789 ";
    public static char[] chars = CHARS.toCharArray();
    int len;
    int total;

    static Random rand = new Random();
    List<String> list = new ArrayList<String>();

    public StringGenerator(int len, int total) {
        super();
        this.len = len;
        this.total = total;
        for (int i = 0; i < total; i++) {
            list.add(randString(len));
        }
    }

    public static String randString(int len) {
        len = len / 2 + rand.nextInt(len / 2);
        int clen = chars.length;
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = chars[rand.nextInt(clen)];
        }
        return new String(str);
    }

    public String gen() {
        return list.get(rand.nextInt(list.size()));
    }

}
