package com.example.talisman.genconfirm;

import java.util.Random;

/**
 * Created by gipotalamus on 17.08.16.
 */
public class ConfirmCodeGenerator {

    public String generateCode(String name) {
        Random r = new Random();
        int a = r.nextInt(100);
        char[] chars = name.toCharArray();
        String code = "";
        for (char c :
                chars) {
            int i = Character.getNumericValue(c) + a;
            code = code.concat(String.valueOf(i));

        }
        return code;
    }


}
