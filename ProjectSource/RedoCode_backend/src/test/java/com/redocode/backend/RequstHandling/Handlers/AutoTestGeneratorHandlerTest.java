package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.User;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

// @Disabled("Islotating specific test for debugging")
class AutoTestGeneratorHandlerTest {
  @SneakyThrows
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 5, 7, 3, 0, 23})
  void handle(int amtOfAutoTests) {
    AutoTestGeneratorHandler handler = new AutoTestGeneratorHandler();

    CodeTestRequest codeTestRequest =
        CodeTestRequest.builder()
            .testsToRun(new ArrayList<>())
            .amountOfAutoTests(amtOfAutoTests)
            .user(new User("2"))
            .timeForExecution(22L)
            .solutionCodes(new HashMap<>())
            .inputType(Variables.VARIABLES_TYPES.ARRAY_OF_INTEGERS)
            .lengthRange(new Range(4, 22))
            .xArrayRange(new Range(3, 8))
            .programResults(new HashMap<>())
            .build();

    int currAmtOFTests =
        codeTestRequest.getAutotestsToRun() == null
            ? 0
            : codeTestRequest.getAutotestsToRun().size();
    codeTestRequest = (CodeTestRequest) handler.handle(codeTestRequest);
    int amtOfTestsAfter = codeTestRequest.getAutotestsToRun().size();
    assertEquals(currAmtOFTests + amtOfAutoTests, amtOfTestsAfter);
  }
}
