package com.redocode.backend.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.Messages.ExerciseListRequestMessage;
import com.redocode.backend.VmAcces.CodeRunners.Variables.SingleString;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration
@Log
// @Disabled("Islotating specific test for debugging")
class ExerciseRepositoryTest {
  @Autowired private ExerciseRepository exerciseRepository;

  @Test
  void getSimpleExcersizeList() {
    List<ExcersizeListEntry> list = exerciseRepository.getSimpleExcersizeList();
    log.info("list size: " + Arrays.toString(list.toArray()));
    // todo: add asserts
  }

  @Test
  // @Disabled("Islotating specific test for debugging")
  void getExeciseListFromQuery() {

    ExerciseListRequestMessage req =
        ExerciseListRequestMessage.builder()
            .page(1)
            .rowsPerPage(10)
            .sortBy("name")
            .sortDirection(false)
            .build();

    org.springframework.data.domain.Page<com.redocode.backend.database.Excersize> list =
        exerciseRepository.findAll(
            PageRequest.of(
                req.getPage(),
                req.getRowsPerPage(),
                req.isSortDirection() ? Sort.Direction.ASC : Sort.Direction.DESC,
                "id"));
    log.info("list  " + list.toString());
  }

  @Test
  void saveNewExerciseToDb() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    ExerciseTests test1 =
        ExerciseTests.builder()
            .expectedOutput(objectMapper.writeValueAsString(new SingleString("Hello world!")))
            .build();
    HashSet<ExerciseTests> exerciseTests = new HashSet<>();
    exerciseTests.add(test1);
    Excersize excersize =
        Excersize.builder()
            .excersizeName("Test")
            .inputType(null)
            .outputType(Variables.VARIABLES_TYPES.SINGLE_STRING)
                .ram_mb(512)
            .build();

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
