package com.redocode.backend.DataProviders;

import com.redocode.backend.Messages.UtilContainers.Range;
import org.junit.jupiter.params.provider.Arguments;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class IncorrectExerciseParamatersProviders {
    public static Stream<Arguments> incorrectTitleProvider() {
        List<String> titles = new ArrayList<>();
        titles.add("");
        titles.add("1");
        titles.add("22");
        titles.add("333");
        titles.add("         ");
        titles.add("                                    ");

        Stream<Arguments> args=titles.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }
    public static Stream<Arguments> incorrectAmountAutoTest() {
        List<Integer> titles = new ArrayList<>();
        titles.add(-1);
        titles.add(0);
        titles.add(-10000);
        titles.add(-10);
        titles.add(Integer.MAX_VALUE);;
        Stream<Arguments> args=titles.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }

    public static Stream<Arguments> incorrectLengthRange() {
        List<Range> titles = new ArrayList<>();
        titles.add(new Range(-4,-5));
        titles.add(new Range(5,2));
        Stream<Arguments> args=titles.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }

    public static Stream<Arguments> incorrectArrayRange() {
        List<Range> titles = new ArrayList<>();
        titles.add(new Range(1,25));
        titles.add(new Range(0,0));
        titles.add(new Range(5,2));
        titles.add(new Range(-2,4));
        titles.add(new Range(4,-5));
        Stream<Arguments> args=titles.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }

    public static Stream<Arguments>incorrectStringRange()
    {
        List<Range> titles = new ArrayList<>();
        titles.add(new Range(1,55));
        titles.add(new Range(5,2));
        titles.add(new Range(-2,4));
        titles.add(new Range(4,-5));
        Stream<Arguments> args=titles.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }

    public static Stream<Arguments>incorrectTime()
    {
        List<Long> titles = new ArrayList<>();
        titles.add(0L);
        titles.add(3L);
        titles.add(60*20L);
        titles.add(-20L);
        Stream<Arguments> args=titles.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }

    public static Stream<Arguments>executionTime()
    {
        List<Long> msTime = new ArrayList<>();
        msTime.add(1L);
        msTime.add(9L);
        msTime.add(-9001L);
        msTime.add(5001L);
        msTime.add(9001L);
        msTime.add(Long.MAX_VALUE);
        msTime.add(Long.MIN_VALUE);
        Stream<Arguments> args=msTime.stream().map((arg)->{return  Arguments.of((Object) arg);});
        return args;
    }
}
