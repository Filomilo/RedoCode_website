package com.redocode.backend;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.provider.Arguments;
import java.util.*;
import java.util.stream.Stream;
@Slf4j
public class ValuesProvider {


    static final Random rand=new Random();
    private static Integer[] generateRadnomIntArray(int length)
    {
        Integer[] arr= new Integer[length];
        for (int i = 0; i < length; i++) {
            arr[i]=rand.nextInt();
        }
        return arr;
    }
    private static Integer[][] generateRadnomIntDoubleArray(int height ,int  width)
    {
        Integer[][] arr= new Integer[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                arr[i][j]=rand.nextInt();
        }
        return arr;
    }


    private static Float[] generateRadnomFloatArray(int length)
    {
        Float[] arr= new Float[length];
        for (int i = 0; i < length; i++) {
            arr[i]=rand.nextFloat();
        }
        return arr;
    }
    private static Float[][] generateRadnomFloatDoubleArray(int height ,int  width)
    {
        Float[][] arr= new Float[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                arr[i][j]=rand.nextFloat();
        }
        return arr;
    }




    public static Stream<Integer> singleIntProvider() {
        List<Integer> argeumnets= (List<Integer>) new ArrayList<Integer>();
        argeumnets.add(5);
        return argeumnets.stream();
    }


    public static Stream<Arguments> arrayIntProvider() {
        List<Integer[]> argeumnets= (List<Integer[]>) new ArrayList<Integer[]>();
        int width=rand.nextInt(1,20);
        argeumnets.add(generateRadnomIntArray(width));
        argeumnets.add(generateRadnomIntArray(width));
        log.info(Arrays.deepToString(argeumnets.toArray()));
        Stream<Arguments> args=argeumnets.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }
    public static Stream<Arguments> doubleArrayIntProvider() {
        List<Integer[][]> argeumnets= (List<Integer[][]>) new ArrayList<Integer[][]>();
        int width=rand.nextInt(1,20);
        int height=rand.nextInt(1,20);
        argeumnets.add(generateRadnomIntDoubleArray(height,width));
        argeumnets.add(generateRadnomIntDoubleArray(height,width));
        Stream<Arguments> args=argeumnets.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }





    public static Stream<Float> singleFloatProvider() {
        List<Float> argeumnets= (List<Float>) new ArrayList<Float>();
        argeumnets.add(5.0f);
        return argeumnets.stream();
    }


    public static Stream<Arguments> arrayFloatProvider() {
        List<Float[]> argeumnets= (List<Float[]>) new ArrayList<Float[]>();
        int width=rand.nextInt(1,20);
        argeumnets.add(generateRadnomFloatArray(width));
        argeumnets.add(generateRadnomFloatArray(width));
        log.info(Arrays.deepToString(argeumnets.toArray()));
        Stream<Arguments> args=argeumnets.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }
    public static Stream<Arguments> doubleArrayFloatProvider() {
        List<Float[][]> argeumnets= (List<Float[][]>) new ArrayList<Float[][]>();
        int width=rand.nextInt(1,20);
        int height=rand.nextInt(1,20);
        argeumnets.add(generateRadnomFloatDoubleArray(height,width));
        argeumnets.add(generateRadnomFloatDoubleArray(height,width));
        Stream<Arguments> args=argeumnets.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }



}
