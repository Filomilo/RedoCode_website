package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariablesParserTest {

    @Test
    void parseVaraiablesDoubleArrayString() {

        String toParseBadCase="KVxQv\tYk\tzO2\tY1EOSn 6rfB8r\tjxhgw3\t\n" +
                "9ksS\tPvtfYJUB\tLdfM1k\tK5eI DFUwOfv0Kd\tSmKz3wLzs0lLlPNNIBT\t\n" +
                "mJXPCnsK8Q8PEp\t2zw0fWWfT3Cvg\t80Ig\tuyCV\\nsKHzlYBR0Cq\t\t\n" +
                "W\tyeAKV\tOY7G\\n\t8pq6 2iUFVS\tzE02Q\\ndULoZZJvf\t\n" +
                "TrK\\tdQF9tg\tGVIY5G3zj\tseAj\\np3\tFORuR5v2Koo6 W6VEn\trUF\\n0L45H5O3RLhjy\t\n" +
                "H\tRi hqj8\t\\t\\tEglj15K76u\t\tCOu6\\tGLJETviYrpdk\t\n" +
                "l\\nw7fX1m5uD\tLktHCs\tRqw\tKWdv5n41Ow08QX\t\\t6SrNMH\t\n" +
                "\tKHeWydWQjk oY3mhr\tsjTukW2l\tJ3GGSO\t5OnQCFCzWdGrlA1pL\t";

        assertDoesNotThrow(()->{
            DoubleArrayOfStrings doubleArrayOfStrings= (DoubleArrayOfStrings) VariablesParser.parseVaraiables(Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS,toParseBadCase);
        });


    }
}