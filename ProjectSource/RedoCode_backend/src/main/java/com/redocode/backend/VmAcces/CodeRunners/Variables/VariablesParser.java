package com.redocode.backend.VmAcces.CodeRunners.Variables;

import com.redocode.backend.Tools.StringFormatter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

// TODO: 20/02/2024 cleanre moethod would be nice
@Slf4j
public class VariablesParser {
    static public Variables parseVaraiables(Variables.VARIABLES_TYPES type, String toParse)
    {
        log.info("toParse: "+toParse);
        log.info("PARsed: "+Arrays.deepToString(Arrays.stream(toParse.split("\t")).map(StringFormatter::removeWhiteCharacterss).toArray()));
        Variables var= switch (type)
        {

            case SINGLE_INTEGER -> new SingleInteger(Integer.parseInt(toParse));
            case SINGLE_STRING -> new SingleString(StringFormatter.recoverWhiteCharacter(toParse));
            case SINGLE_FLOAT -> new SingleFloat(Float.parseFloat(toParse));
            case ARRAY_OF_INTEGERS ->  {
                String[] splits =toParse.split("\t");
                Integer[] res =new Integer[splits.length];
                for (int i = 0; i < splits.length; i++) {
                    res[i]=Integer.parseInt(splits[i]);
                }
                yield new ArrayOfIntegers(res);
            }
            case ARRAY_STRINGS -> {
                    String[] splits =toParse.split("\t",-1);
                    String[] res =new String[splits.length];
                    for (int i = 0; i < splits.length; i++) {
                        res[i]=StringFormatter.recoverWhiteCharacter(splits[i]);
                    }
                    yield new ArrayOfStrings(res);
            }
            case ARRAY_OF_FLOATS -> {
                String[] splits =toParse.split("\t");
                Float[] res =new Float[splits.length];
                for (int i = 0; i < splits.length; i++) {
                    res[i]=Float.parseFloat(splits[i]);
                }
                yield new ArrayOfFloats(res);
            }
            case DOUBLE_ARRAY_OF_INTEGERS -> {
                String[] firstPass=toParse.split("\n");
                String[][] secPass= new String[firstPass.length][];
                for (int i = 0; i < firstPass.length ; i++) {
                    secPass[i]=firstPass[i].split("\t");
                }
                log.info("firstPass: "+ Arrays.deepToString(firstPass));
                log.info("secPass: "+ Arrays.deepToString(secPass));
                Integer res[][]=new Integer[firstPass.length][secPass[0].length];
                for (int i = 0; i <firstPass.length ; i++) {
                    for (int j = 0; j <secPass[0].length ; j++) {
                        res[i][j]=Integer.parseInt(secPass[i][j]);
                    }
                }
                log.info("ARRRAY INT: "+ Arrays.deepToString(res));
                yield new DoubleArrayOfIntegers(res);
            }
            case DOUBLE_ARRAY_OF_FLOATS ->{
                String[] firstPass=toParse.split("\n");
                String[][] secPass= new String[firstPass.length][];
                for (int i = 0; i < firstPass.length ; i++) {
                    secPass[i]=firstPass[i].split("\t");
                }
                Float res[][]=new Float[firstPass.length][secPass[0].length];
                for (int i = 0; i <firstPass.length ; i++) {
                    for (int j = 0; j <secPass[0].length ; j++) {
                        res[i][j]=Float.parseFloat(secPass[i][j]);
                    }
                }
                yield new DoubleArrayOfFloats(res);
            }
            case DOUBLE_ARRAY_OF_STRINGS -> {
                String[] firstPass=toParse.split("\n",-1);
                String[][] secPass= new String[firstPass.length][];
                for (int i = 0; i < firstPass.length ; i++) {
                    secPass[i]=firstPass[i].split("\t",-1);
                }
                String res[][]=new String[firstPass.length][secPass[0].length];
                for (int i = 0; i <firstPass.length ; i++) {
                    for (int j = 0; j <secPass[0].length ; j++) {
                        res[i][j]=StringFormatter.recoverWhiteCharacter(secPass[i][j]);
                    }
                }
                yield new DoubleArrayOfStrings(res);
            }
        };
        log.info("varaaa: "+var);

        return var;
    }
    
    
    
}
