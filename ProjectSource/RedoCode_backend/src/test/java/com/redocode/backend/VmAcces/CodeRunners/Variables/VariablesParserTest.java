package com.redocode.backend.VmAcces.CodeRunners.Variables;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

// @Disabled("Islotating specific test for debugging")
class VariablesParserTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "VSYKCpBg55hx9gSRgh\ti4gN\tF\tOUHtY7uvzEGJ\tk6BdhnPnBCeitojF9\tsJYE6MfwPsYwj\tD\tS\\n"
            + "e\t\tL\\tDdtHRA\t3IW2\\tijt\tWOS0qPreAShd8w\n"
            + "sqd0YwxOrEK6\tE\\t Ji0QSrX3yKPXEv\t\teQ8MFLj0v\t78d5Le\tIdt 0h\tSOQwUlRnDFSDXd4SA"
            + "\tvuP43ZyCQVjdlvPIH\tEhp\t\\t5IumQHpFo2Tj\t7Tjv\t\n"
            + "0VHBe7sLx\tq\tY7SOBPBkQi\tOm0mV7FwmX\\teuAEJGQ3\t99CwvPhV dC\tx8r9v\\n"
            + "A4XRU4L\t2G\t1vK03XSmNDSQoos\t\tQOyzge\\tFhRznG6S\t\tUI8ROH3VALTtxo\n"
            + "JGIRSuK0nvK2h8HI9\t\\n"
            + "\t dq\\t9om617KmP2G\tC\t5 EGCx42r\trlAnn0 CneUT\\n"
            + "KNvS\tQe1x9U\\n"
            + "Jgo\\n"
            + "4m\t7\t44f7zomBU\\n"
            + "jQD\thzqn0WLBGQxOzh\t\\n"
            + "yQiPGMukN0\tL6oOhrzj"
      })
  void parseVaraiablesDoubleArrayString(String str) {

    assertDoesNotThrow(
        () -> {
          DoubleArrayOfStrings doubleArrayOfStrings =
              (DoubleArrayOfStrings)
                  VariablesParser.parseVaraiables(
                      Variables.VARIABLES_TYPES.DOUBLE_ARRAY_OF_STRINGS, str);
        });
  }
}
