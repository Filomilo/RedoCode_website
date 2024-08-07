package com.redocode.backend.Tools;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringFormatter {

    public static String removeWhiteCharacterss(String input)
    {
//    log.info("String before format: "+ input);
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '\\':
                    input = input.substring(0, i) + "\\\\" + input.substring(i + 1);
                    i += 1;
                    break;
                case '\n':
                    input = input.substring(0, i) + "\\n" + input.substring(i + 1);
                    i += 1;
                    break;
                case '\"':
                    input = input.substring(0, i) + "\\\"" + input.substring(i + 1);
                    i += 1;
                    break;
                case '\t':
                    input = input.substring(0, i) + "\\t" + input.substring(i + 1);
                    i += 1;
                    break;

            }
        }
//        log.info("String before format: "+ input);
            return input;
        }

    public static String recoverWhiteCharacter(String input)
    {
//        log.info("String before reformat: "+ input);
        for (int i = 0; i < input.length()-1; i++) {
            String tmp = input.substring(i, i + 2);

            switch (tmp) {
                case "\\\\":
                    log.info("oart: "+ tmp);
                    input = input.substring(0, i) + "\\" + input.substring(i + 2);
//                    i -= 1;
                    log.info("after oart: "+ input);
                    break;
                case "\\n":
                    input = input.substring(0, i) + "\n" + input.substring(i + 2);
//                    i -= 1;
                    break;
                case "\\\"":
                    input = input.substring(0, i) + "\"" + input.substring(i + 2);
                    break;
//                    i -= 1;
                case "\\t":
                    input = input.substring(0, i) + "\t" + input.substring(i + 2);
                    break;

            }
        }
//        log.info("String after  reformat: "+ input);
            return input;
    }

    public static String prepreForFileSaving(String input)
    {
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i))
            {
                case '\\':
                    switch (input.charAt(i+1))
                    {
                        case 'n':
                            input=input.substring(0,i)+"\\\\n"+input.substring(i+2);
                            i+=2;
                            break;
                        default:
                            input=input.substring(0,i)+"\\\\"+input.substring(i+1);
                            i+=1;
                    }


                    break;
                case '\n':
//                    input=input.substring(0,i)+"\\\n"+input.substring(i+1);
//                    i+=1;
                    break;
                case '\"':
                    input=input.substring(0,i)+"\\\""+input.substring(i+1);
                    i+=1;
                    break;

            }

        }

        return input;
    }

}
