package com.redocode.backend.Tools;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringFormatter {

   public static String removeWhiteCharacterss(String input)
    {
        input= input.replace("\n","\\n");
        input=input.replace("\"","\\\"");
        input=input.replace("\'","\\\'");
        input=input.replace("\\","\\\\");
            return input;
    }

    public static String prepreForFileSaving(String input)
    {
//        input =input.replace("\\\\","\\\\\\");
//        input =input.replace("\\\\\\","\\\\\\\\");
//        log.info("AFTER SLAHES: \n\n"+ input);
//        input =input.replace("\\n","\\\\n");
//        input =input.replace("\\\\\\n","\\\\\\\\n");
//        input=input.replace("\n","\\n");
////        input =input.replace("\\\\\\\\n","\\\\\\\\\\\\\\\\\n");
////        input=input.replace("\\\\\\\\\\n","AAAAAA");
//        log.info("AFTER N: \n\n"+ input);
//        input =input.replace("\\\\t","\\t");




//        input =input.replace("\\\\n","\\\\\\n");
//        input =input.replace("\\n","\\\\n");
//
//
//        input =input.replace("\\\\t","\\\\\\t");
//        input =input.replace("\\t","\\\\t");
//input.re


//    log.info("For SAVING: \n\n"+input);
//        input=input.replace("\t","\\t");
//        input=input.replace("\"","\\\"");
//        input=input.replace("\\","\\\\\\");
//    input=input.replace("'","'");
//        input=input.replace("\"","\\\"");
//        input=input.replace("\\\\","\\\\\\");

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
