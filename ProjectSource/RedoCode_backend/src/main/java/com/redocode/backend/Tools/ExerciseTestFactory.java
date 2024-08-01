package com.redocode.backend.Tools;

import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import com.redocode.backend.database.ExerciseTests;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ExerciseTestFactory {

    private final static com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
    private final Random rnd = new Random();

  private Variables.VARIABLES_TYPES inputType;

    private  int amount;

    private Range xArrayRange;
    private Range yArrayRange;
    private Range lengthRange;


    private boolean capitalLetters=false;
    private boolean underscoreLetters=false;
    private boolean numbers=false;
    private boolean specialChars=false;
    private boolean  breakCharacters=false;
    private boolean spaceCharacters=false;


    public ExerciseTestFactory setInputType(Variables.VARIABLES_TYPES inputType) {
        this.inputType = inputType;
        return this;
    }

    public ExerciseTestFactory setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ExerciseTestFactory setXArrayRange(Range xArrayRange) {
        this.xArrayRange = xArrayRange;
        return this;
    }

    public ExerciseTestFactory setYArrayRange(Range yArrayRange) {
        this.yArrayRange = yArrayRange;
        return this;
    }

    public ExerciseTestFactory setLengthRange(Range lengthRange) {
        this.lengthRange = lengthRange;
        return this;
    }

    public ExerciseTestFactory setCapitalLetters(boolean capitalLetters) {
        this.capitalLetters = capitalLetters;
        return this;
    }
    public ExerciseTestFactory setUnderscoreLetters(boolean underscoreLetters) {
        this.underscoreLetters = underscoreLetters;
        return this;
    }
    public ExerciseTestFactory setNumbers(boolean numbers) {
        this.numbers = numbers;
        return this;
    }
    public ExerciseTestFactory setSpecialChars(boolean specialChars) {
        this.specialChars = specialChars;
        return this;
    }
    public ExerciseTestFactory setBreakCharacters(boolean breakCharacters) {
        this.breakCharacters = breakCharacters;
        return this;
    }
    public ExerciseTestFactory setSpaceCharacters(boolean spaceCharacters) {
        this.spaceCharacters = spaceCharacters;
        return this;
    }



    private List<Character> getSpecialCharacters()
    {
        List<Character> list = new ArrayList<>();
        for (int i = 33; i <= 46; i++) {
            list.add((char)i);
        }
        for (int i = 58; i <= 64; i++) {
            list.add((char)i);
        }
        for (int i = 91; i <= 96; i++) {
            list.add((char)i);
        }
        for (int i = 161; i <= 172; i++) {
            list.add((char)i);
        }
        return list;
    }
    private  List<Character> getCapitalLetters()
    {
        List<Character> list = new ArrayList<>();
        for (int i = 65; i <= 90; i++) {
            list.add((char)i);
        }
        return list;
    }
    private List<Character> getUnderscoreLetters()
    {
        List<Character> list = new ArrayList<>();
        for (int i = 100; i <= 122; i++) {
            list.add((char)i);
        }
        return list;
    }
    private List<Character> getNumbers()
    {
        List<Character> list = new ArrayList<>();
        for (int i = 48; i <= 57; i++) {
            list.add((char)i);
        }
        return list;
    }
    private List<Character> getSpaceCharacters()
    {
        List<Character> list = new ArrayList<>();
            list.add((char)32);
        return list;
    }
    private List<Character> getBreakCharacters()
    {
        List<Character> list = new ArrayList<>();
        list.add((char)9);
        list.add((char)10);
        return list;
    }

    private void generatePossilbeCharacters()
    {
        List<Character> list = new ArrayList<>();
        if(this.capitalLetters){
            list.addAll(this.getCapitalLetters());
        }
        if(this.underscoreLetters){
            list.addAll(this.getUnderscoreLetters());
        }
        if(this.numbers){
            list.addAll(this.getNumbers());
        }
        if(this.breakCharacters){
            list.addAll(this.getBreakCharacters());
        }
        if(this.spaceCharacters){
            list.addAll(this.getSpaceCharacters());
        }
        if(this.specialChars){
            list.addAll(this.getSpecialCharacters());
        }
        this.possibleChapters=list;
    }



    private List<Character> possibleChapters =null;


   private Integer generateInt()
   {
       return rnd.nextInt(lengthRange.getMin().intValue(),(int)this.lengthRange.getMax().intValue());
   }

   private Float generateFloat(){
       return rnd.nextFloat (lengthRange.getMin().floatValue(),this.lengthRange.getMax().floatValue());
   }
   private String generateString(){
       int length=generateInt();
       StringBuilder sb=new StringBuilder(length);
       for(int i=0;i<length;i++){
           sb.append(this.possibleChapters.get(rnd.nextInt(this.possibleChapters.size())));
       }
       return sb.toString();
   }

   private Object getSingleValue()
   {
        if(inputType.isInteger())
            return generateInt();
        if(inputType.isFloat())
            return generateFloat();
        if(inputType.isString())
            return generateString();
        return null;
   }


    private Object[] getArrayValues()
    {
        int size= rnd.nextInt(this.xArrayRange.getMin().intValue(),this.xArrayRange.getMax().intValue());
        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = this.getSingleValue();
        }
        return array;
    }

    private Object[][] getDoubleArrayValues()
    {
        int sizeX= rnd.nextInt(this.xArrayRange.getMin().intValue(),this.xArrayRange.getMax().intValue());
        int sizeY= rnd.nextInt(this.yArrayRange.getMin().intValue(),this.yArrayRange.getMax().intValue());
        Object[][] array = new Object[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                array[i][j] = this.getSingleValue();
            }
            }
        return array;
    }

   private Variables generateFinalVar()
   {
        switch(inputType){
            case SINGLE_INTEGER -> {
                return new SingleInteger((Integer) getSingleValue());
            }
            case SINGLE_STRING -> {
                return new SingleString((String) getSingleValue());
            }
            case SINGLE_FLOAT -> {
                return new SingleFloat((Float) getSingleValue());
            }
            case ARRAY_OF_INTEGERS -> {

                Integer[] integerArray = Arrays.stream(getArrayValues())
                        .map(x -> (Integer) x)
                        .toArray(Integer[]::new);

                return new ArrayOfIntegers(integerArray);

            }
            case ARRAY_STRINGS -> {

                String[] integerArray = Arrays.stream(getArrayValues())
                        .map(x -> (String) x)
                        .toArray(String[]::new);

                return new ArrayOfStrings(integerArray);

            }
            case ARRAY_OF_FLOATS -> {
                Float[] floatArray = Arrays.stream(getArrayValues())
                        .map(x -> (Float) x)
                        .toArray(Float[]::new);

                return new ArrayOfFloats(floatArray);
            }
            case DOUBLE_ARRAY_OF_INTEGERS -> {

                Object[][] arr=getDoubleArrayValues();
                Integer[][] arrout=new Integer[arr.length][];
                for (int i = 0; i < arr.length ; i++) {
                    Integer[] subarr=new Integer[arr[i].length];
                   for (int j = 0; j < arr[i].length; j++) {
                       subarr[j]=(Integer) arr[i][j];
                   }
                   arrout[i]=subarr;
                }
                return new DoubleArrayOfIntegers(arrout );
            }
            case DOUBLE_ARRAY_OF_FLOATS -> {
                Object[][] arr=getDoubleArrayValues();
                Float[][] arrout=new Float[arr.length][];
                for (int i = 0; i < arr.length ; i++) {
                    Float[] subarr=new Float[arr[i].length];
                    for (int j = 0; j < arr[i].length; j++) {
                        subarr[j]=(Float) arr[i][j];
                    }
                    arrout[i]=subarr;
                }
                return new DoubleArrayOfFloats(arrout );
            }
            case DOUBLE_ARRAY_OF_STRINGS -> {
                Object[][] arr=getDoubleArrayValues();
                String[][] arrout=new String[arr.length][];
                for (int i = 0; i < arr.length ; i++) {
                    String[] subarr=new String[arr[i].length];
                    for (int j = 0; j < arr[i].length; j++) {
                        subarr[j]=(String) arr[i][j];
                    }
                    arrout[i]=subarr;
                }
                return new DoubleArrayOfStrings(arrout );
            }
        }
        return null;
   }
    @SneakyThrows
    public ExerciseTests[] build()
    {
        if(inputType.isString())
        {
            generatePossilbeCharacters();
        }

        List<ExerciseTests> tests = new ArrayList<ExerciseTests>();
        for (int i = 0; i < amount ; i++) {
            tests.add(
                    ExerciseTests
                            .builder()
                            .expectedOutput("")
                            .input(RedoCodeObjectMapper.VarAsString(generateFinalVar()))
                            .build()
            );
        }
        return tests.toArray(new ExerciseTests[tests.size()]);
    }

}
