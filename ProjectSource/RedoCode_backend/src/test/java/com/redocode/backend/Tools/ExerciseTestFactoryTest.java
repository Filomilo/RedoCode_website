package com.redocode.backend.Tools;

import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import com.redocode.backend.database.ExerciseTests;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ExerciseTestFactoryTest {

    @ParameterizedTest
    @ValueSource(ints = {1,4,5,6,10})
    void amountOfGeneratedtests(int amt) {
        ExerciseTests[] tests =  new ExerciseTestFactory()
                .setAmount(amt)
                .setLengthRange(new Range(0f,10f))
                .setInputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .build();
            assertEquals(amt,tests.length);
    }
    @Test
    @SneakyThrows
    void generateInts()
    {
        int min =-3;
        int max=4;

        int aamount=10;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .setLengthRange(
                        new Range(min,max)
                )
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            SingleInteger singleInteger= (SingleInteger) test[i].getParsedInput(Variables.VARIABLES_TYPES.SINGLE_INTEGER);
            log.info(singleInteger.toString());
            assertTrue(singleInteger.getValue()<=max && singleInteger.getValue()>=min);
        }
    }

    @Test
    @SneakyThrows
    void generatefloats()
    {
        float min =-3.1f;
        float max=4.4f;

        int aamount=10;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.SINGLE_FLOAT)
                .setLengthRange(
                        new Range(min,max)
                )
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            SingleFloat singleInteger= (SingleFloat) test[i].getParsedInput(Variables.VARIABLES_TYPES.SINGLE_FLOAT);
            log.info(singleInteger.toString());
            assertTrue(singleInteger.getValue()<=max && singleInteger.getValue()>=min);
        }
    }

    @Test
    @SneakyThrows
    void generateStringLengthTest()
    {
        float min =10;
        float max=14;

        int aamount=10;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.SINGLE_STRING)
                .setLengthRange(
                        new Range(min,max)
                )
                .setCapitalLetters(true)
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            SingleString singleString= (SingleString) test[i].getParsedInput(Variables.VARIABLES_TYPES.SINGLE_STRING);
            log.info(singleString.toString());
            assertTrue(singleString.getValue().length()<=max && singleString.getValue().length()>=min);
        }
    }

    @Test
    @SneakyThrows
    void generateStringCapitalLetters()
    {
        float min =10;
        float max=14;

        int aamount=10;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.SINGLE_STRING)
                .setLengthRange(
                        new Range(min,max)
                )
                .setCapitalLetters(true)
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            SingleString singleString= (SingleString) test[i].getParsedInput(Variables.VARIABLES_TYPES.SINGLE_STRING);
            for (int j = 0; j <singleString.getValue().length() ; j++) {
                Character character= (Character) singleString.getValue().charAt(j);
                assertTrue(
                        (int)character<='Z' && character>='A'
                );
            }
        }
    }

    @Test
    @SneakyThrows
    void generateStringUnderLetters()
    {
        float min =10;
        float max=14;

        int aamount=10;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.SINGLE_STRING)
                .setLengthRange(
                        new Range(min,max)
                )
                .setUnderscoreLetters(true)
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            SingleString singleString= (SingleString) test[i].getParsedInput(Variables.VARIABLES_TYPES.SINGLE_STRING);
            for (int j = 0; j <singleString.getValue().length() ; j++) {
                Character character= (Character) singleString.getValue().charAt(j);
                assertTrue(
                        (int)character<='z' && character>='a'
                );
            }
        }
    }

    @Test
    @SneakyThrows
    void generateStringNumbers()
    {
        float min =10;
        float max=14;

        int aamount=10;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.SINGLE_STRING)
                .setLengthRange(
                        new Range(min,max)
                )
                .setNumbers(true)
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            SingleString singleString= (SingleString) test[i].getParsedInput(Variables.VARIABLES_TYPES.SINGLE_STRING);
            for (int j = 0; j <singleString.getValue().length() ; j++) {
                Character character= (Character) singleString.getValue().charAt(j);
                assertTrue(
                        (int)character<='9' && character>='0'
                );
            }
        }
    }
    @Test
    @SneakyThrows
    void generateStringspace()
    {
        float min =10;
        float max=14;

        int aamount=10;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.SINGLE_STRING)
                .setLengthRange(
                        new Range(min,max)
                )
                .setSpaceCharacters(true)
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            SingleString singleString= (SingleString) test[i].getParsedInput(Variables.VARIABLES_TYPES.SINGLE_STRING);
            log.info("- "+singleString.getValue()+" -");
            for (int j = 0; j <singleString.getValue().length() ; j++) {
                Character character= (Character) singleString.getValue().charAt(j);
                assertTrue(
                        (int)character==' '
                );
            }
        }
    }
    @Test
    @SneakyThrows
    void generateStringBreakChacaretes()
    {
        float min =10;
        float max=14;

        int aamount=10;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.SINGLE_STRING)
                .setLengthRange(
                        new Range(min,max)
                )
                .setBreakCharacters(true)
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            SingleString singleString= (SingleString) test[i].getParsedInput(Variables.VARIABLES_TYPES.SINGLE_STRING);
            log.info("- "+singleString.getValue()+" -");
            for (int j = 0; j <singleString.getValue().length() ; j++) {
                Character character= (Character) singleString.getValue().charAt(j);
                log.info("Checking character: "+ character+": "+ (int)character);
                assertTrue(
                        (int)character=='\n' || (int)character=='\t'
                );
            }
        }
    }

@Test
@SneakyThrows
    void generateIntsArray()
    {
        int min =-3;
        int max=4;

        int minX =10;
        int maxX=12;

        int aamount=100;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
                .setLengthRange(
                        new Range(min,max)
                )
                .setXArrayRange(new Range(minX,maxX))
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            ArrayOfIntegers arrayOfIntegers= (ArrayOfIntegers) test[i].getParsedInput(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS);
            log.info(arrayOfIntegers.toString());
            assertTrue(arrayOfIntegers.getValue().length<=maxX && arrayOfIntegers.getValue().length>=min);
        }
    }
    @Test
    @SneakyThrows
    void generateFloatArray()
    {
        int min =-3;
        int max=4;

        int minX =10;
        int maxX=12;

        int aamount=100;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.ARRAY_OF_FLOATS)
                .setLengthRange(
                        new Range(min,max)
                )
                .setXArrayRange(new Range(minX,maxX))
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            ArrayOfFloats arrayOfFloats= (ArrayOfFloats) test[i].getParsedInput(Variables.VARIABLES_TYPES.ARRAY_OF_FLOATS);
            log.info(arrayOfFloats.toString());
            assertTrue(arrayOfFloats.getValue().length<=maxX && arrayOfFloats.getValue().length>=min);
        }
    }

    @Test
    @SneakyThrows
    void generateStringArray()
    {
        int min =8;
        int max=31;

        int minX =10;
        int maxX=12;

        int aamount=100;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.ARRAY_STRINGS)
                .setLengthRange(
                        new Range(min,max)
                )
                .setXArrayRange(new Range(minX,maxX))
                .setSpaceCharacters(true)
                .setCapitalLetters(true)

                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            ArrayOfStrings arrayOfStrings= (ArrayOfStrings) test[i].getParsedInput(Variables.VARIABLES_TYPES.ARRAY_STRINGS);
            log.info( arrayOfStrings.toString());
            assertTrue(arrayOfStrings.getValue().length<=maxX && arrayOfStrings.getValue().length>=min);
        }
    }

    @Test
    @SneakyThrows
    void generateIntsDoubleArray()
    {
        int min =-3;
        int max=4;

        int minX =10;
        int maxX=12;
        int minY =3;
        int maxY=5;

        int aamount=100;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_INTEGERS)
                .setLengthRange(
                        new Range(min,max)
                )

                .setXArrayRange(new Range(minX,maxX))
                .setYArrayRange(new Range(minY,maxY))
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            DoubleArrayOfIntegers doubleArrayOfIntegers= (DoubleArrayOfIntegers) test[i].getParsedInput(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_INTEGERS);
            log.info(doubleArrayOfIntegers.toString());
            assertTrue(doubleArrayOfIntegers.getValue().length<=maxX
                    && doubleArrayOfIntegers.getValue().length>=minX
                    && doubleArrayOfIntegers.getValue()[0].length>=minY
                    && doubleArrayOfIntegers.getValue()[0].length<=maxY
            );
        }
    }

    @Test
    @SneakyThrows
    void generateFloatDoubleArray()
    {
        int min =-3;
        int max=4;

        int minX =10;
        int maxX=12;
        int minY =3;
        int maxY=5;

        int aamount=100;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS)
                .setLengthRange(
                        new Range(min,max)
                )

                .setXArrayRange(new Range(minX,maxX))
                .setYArrayRange(new Range(minY,maxY))
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            DoubleArrayOfFloats doubleArrayOfFloats= (DoubleArrayOfFloats) test[i].getParsedInput(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_FLOATS);
            log.info(doubleArrayOfFloats.toString());
            assertTrue(doubleArrayOfFloats.getValue().length<=maxX
                    && doubleArrayOfFloats.getValue().length>=minX
                    && doubleArrayOfFloats.getValue()[0].length>=minY
                    && doubleArrayOfFloats.getValue()[0].length<=maxY
            );
        }
    }
    @Test
    @SneakyThrows
    void generateStringDoubleArray()
    {
        int min =3;
        int max=6;

        int minX =10;
        int maxX=12;
        int minY =3;
        int maxY=5;

        int aamount=100;

        ExerciseTests[] test=new ExerciseTestFactory()
                .setAmount(aamount)
                .setInputType(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS)
                .setLengthRange(
                        new Range(min,max)
                )

                .setXArrayRange(new Range(minX,maxX))
                .setYArrayRange(new Range(minY,maxY))

                .setUnderscoreLetters(true)
                .build();
        assertEquals(aamount,test.length);
        for (int i = 0; i < aamount; i++) {
            DoubleArrayOfStrings doubleArrayOfStrings= (DoubleArrayOfStrings) test[i].getParsedInput(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS);
            log.info(doubleArrayOfStrings.toString());
            assertTrue(doubleArrayOfStrings.getValue().length<=maxX
                    && doubleArrayOfStrings.getValue().length>=minX
                    && doubleArrayOfStrings.getValue()[0].length>=minY
                    && doubleArrayOfStrings.getValue()[0].length<=maxY
            );
        }
    }

}