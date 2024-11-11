package com.redocode.backend.UserDataControllers;

import com.redocode.backend.Messages.ExercisesInfo.ResultData;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExerciseDataControlTest {

  @Autowired ExerciseDataControl exerciseDataControl;
  @Autowired ExcersizeDiffucultyRatingRepository excersizeDiffucultyRatingRepository;
  @Autowired ExerciseRepository exerciseRepository;
  @Autowired UsersRepository usersRepository;
  @Autowired SolutionProgramsRepository solutionProgramsRepository;

  private User userToCheck;
  private Excersize exerciseToCheck;

  private Long avgExecTime = 500L;

  @BeforeAll
  public void setUp() {
    //        this.excersizeDiffucultyRatingRepository.deleteAll();
    //        this.exerciseRepository.deleteAll();
    //        this.usersRepository.deleteAll();
    //        this.solutionProgramsRepository.deleteAll();

    this.userToCheck =
        usersRepository.save(
            User.builder()
                .email(UUID.randomUUID() + "mail@mail.com")
                .description("desc")
                .type(User.USER_TYPE.AUTHENTICATED)
                .nickname("nickname")
                .sessionID("sesssion")
                .password("PASSWORD+123")
                .build());
    User userAuthor =
        usersRepository.save(
            User.builder()
                .email(UUID.randomUUID() + "mail@mail.com")
                .description("desc")
                .type(User.USER_TYPE.AUTHENTICATED)
                .nickname("nickname")
                .sessionID("sesssion")
                .password("PASSWORD+123")
                .build());
    this.exerciseToCheck =
        exerciseRepository.save(
            Excersize.builder()
                .excersizeName("Test")
                .inputType(Variables.VARIABLES_TYPES.SINGLE_INTEGER)
                .valueLengthRangeMax(100f)
                .maxExecutionTimeMS(100L)
                .author(userAuthor)
                .description("descriiptn")
                .outputType(Variables.VARIABLES_TYPES.SINGLE_STRING)
                .ram_mb(512)
                .build());
    this.solutionProgramsRepository.save(
        SolutionPrograms.builder()
            .date(new Date())
            .code("CODE")
            .excersize(this.exerciseToCheck)
            .avgExecutionTime(avgExecTime)
            .solutionAuthor(this.userToCheck)
            .build());
  }

  @Test
  void getResultDataForExerciseOfUser() {
    ResultData resultData =
        exerciseDataControl.getResultDataForExerciseOfUser(
            this.exerciseToCheck.getId(), this.userToCheck.getId());
    assertEquals(resultData.getExecutionTimeMs(), avgExecTime);
    assertEquals(100, resultData.getBetterThanPercent());
    log.info(resultData.toString());

    // add User better
    User userBetter =
        usersRepository.save(
            User.builder()
                .email(UUID.randomUUID() + "mail@mail.com")
                .description("desc")
                .type(User.USER_TYPE.AUTHENTICATED)
                .nickname("nickname")
                .sessionID("sesssion")
                .password("PASSWORD+123")
                .build());
    this.solutionProgramsRepository.save(
        SolutionPrograms.builder()
            .date(new Date())
            .code("CODE")
            .excersize(this.exerciseToCheck)
            .avgExecutionTime(avgExecTime - 100)
            .solutionAuthor(userBetter)
            .build());
    ResultData resultDataWorse =
        exerciseDataControl.getResultDataForExerciseOfUser(
            this.exerciseToCheck.getId(), this.userToCheck.getId());
    assertEquals(0, resultDataWorse.getBetterThanPercent());

    // add User Worse
    User userWorse =
        usersRepository.save(
            User.builder()
                .email(UUID.randomUUID() + "mail@mail.com")
                .description("desc")
                .type(User.USER_TYPE.AUTHENTICATED)
                .nickname("nickname")
                .sessionID("sesssion")
                .password("PASSWORD+123")
                .build());
    this.solutionProgramsRepository.save(
        SolutionPrograms.builder()
            .date(new Date())
            .code("CODE")
            .excersize(this.exerciseToCheck)
            .avgExecutionTime(avgExecTime + 100)
            .solutionAuthor(userWorse)
            .build());
    ResultData resultDataBetter =
        exerciseDataControl.getResultDataForExerciseOfUser(
            this.exerciseToCheck.getId(), this.userToCheck.getId());
    assertEquals(50, resultDataBetter.getBetterThanPercent());
  }

  @Test
  void getSolutionsDataForExerciseOfId() {}

  @Test
  void getSolutionCode() {}

  @Test
  void saveNewComment() {}

  @Test
  void saveNewRating() {}

  @Test
  void getUserSovingState() {}
}
