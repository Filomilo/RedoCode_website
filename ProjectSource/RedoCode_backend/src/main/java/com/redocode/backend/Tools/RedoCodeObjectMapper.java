package com.redocode.backend.Tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseCreatorValidationMessage;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseIdToRunMessage;
import com.redocode.backend.Messages.CodeRunningMessages.ExerciseTestToRunMesseage;
import com.redocode.backend.Messages.CodeRunningMessages.RawCodeToRunMessage;
import com.redocode.backend.RequstHandling.Requests.CodeTestRequest;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RawCodeRunRequest;
import com.redocode.backend.RequstHandling.Requests.SingleDatabaseExerciseTestRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Variables.*;
import com.redocode.backend.database.User;
import lombok.SneakyThrows;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE.JS_RUNNER;
import static com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE.UNIDENTIFIED;

public class RedoCodeObjectMapper {
  public static ObjectMapper mapper = new ObjectMapper();

  public static ExerciseCreationRequest toExerciseCreationRequest(
      ExerciseCreatorValidationMessage exerciseCreatorValidationMessage, User user) {

    return ExerciseCreationRequest.builder()
        .user(user)
        .Title(exerciseCreatorValidationMessage.getTitle())
        .Description(exerciseCreatorValidationMessage.getDescription())
        .amountOfAutoTests((int) exerciseCreatorValidationMessage.getAmountOfAutoTests())
        .lengthRange(exerciseCreatorValidationMessage.getLengthRange())
        .xArrayRange(exerciseCreatorValidationMessage.getXArrayRange())
        .yArrayRange(exerciseCreatorValidationMessage.getYArrayRange())
        .upperCaseInput(exerciseCreatorValidationMessage.isUpperCaseInput())
        .lowerCaseInput(exerciseCreatorValidationMessage.isLowerCaseInput())
        .numberInput(exerciseCreatorValidationMessage.isNumberInput())
        .specialCharacterInput(exerciseCreatorValidationMessage.isSpecialCharacterInput())
        .breakCharacterInput(exerciseCreatorValidationMessage.isBreakCharacterInupt())
        .spaceInput(exerciseCreatorValidationMessage.isSpaceInupt())
        .timeForExecution(exerciseCreatorValidationMessage.getTimeForExecutionMs())
        .solutionCodes(exerciseCreatorValidationMessage.getSolutionCodes())
        .testsToRun(exerciseCreatorValidationMessage.getManualTests())
        .inputType(exerciseCreatorValidationMessage.getInputType())
        .outputType(exerciseCreatorValidationMessage.getOutputType())
        .ram(exerciseCreatorValidationMessage.getRam())
        .programResults(new HashMap<>())
        .build();
  }

  public static RawCodeRunRequest toRunRawCodeRequest(
      RawCodeToRunMessage rawCodeToRunMessage, User user, CODE_RUNNER_TYPE codeRunnerType) {
    return RawCodeRunRequest.builder()
        .user(user)
        .Code(rawCodeToRunMessage.getCode())
        .codeRunnerType(codeRunnerType)
        .timeForExecution(10000L)
        .build();
  }

  public static CodeTestRequest toExerciseTestsRunRequest(
      ExerciseTestToRunMesseage request, User user, CODE_RUNNER_TYPE codeRunnerType) {
    HashMap<CODE_RUNNER_TYPE, String> solutions = new HashMap<CODE_RUNNER_TYPE, String>();
    solutions.put(codeRunnerType, request.getCode());
    return CodeTestRequest.builder()
        .user(user)
        .codeRunnerType(codeRunnerType)
        .timeForExecution(request.getExecutionTime())
        .solutionCodes(solutions)
        .inputType(Variables.VARIABLES_TYPES.valueOf(request.getInputType()))
        .testsToRun(request.getManualTests())
        .outputType(Variables.VARIABLES_TYPES.valueOf(request.getOutputType()))
        .amountOfAutoTests(request.getAmountOfAutoTests())
        .xArrayRange(request.getXArrayRange())
        .yArrayRange(request.getYArrayRange())
        .upperCaseInput(request.isUpperCaseInput())
        .lowerCaseInput(request.isLowerCaseInput())
        .numberInput(request.isNumberInput())
        .specialCharacterInput(request.isSpecialCharacterInput())
        .breakCharacterInput(request.isBreakCharacterInupt())
        .spaceInput(request.isSpaceInupt())
        .lengthRange(request.getLengthRange())
        .programResults(new HashMap<>())
        .build();
  }

  public static Variables parseVaraibles(String toParse, Variables.VARIABLES_TYPES type)
      throws JsonProcessingException {
    com.fasterxml.jackson.databind.ObjectMapper mapper =
        new com.fasterxml.jackson.databind.ObjectMapper();
    Variables input = null;
    switch (type) {
      case SINGLE_INTEGER -> {
        input = new SingleInteger(mapper.readValue(toParse, Integer.class));
      }
      case SINGLE_STRING -> {
        input = new SingleString(mapper.readValue(toParse, String.class));
      }
      case SINGLE_FLOAT -> {
        input = new SingleFloat(mapper.readValue(toParse, Float.class));
      }
      case ARRAY_OF_INTEGERS -> {
        input = new ArrayOfIntegers(mapper.readValue(toParse, Integer[].class));
      }
      case ARRAY_STRINGS -> {
        input = new ArrayOfStrings(mapper.readValue(toParse, String[].class));
      }
      case ARRAY_OF_FLOATS -> {
        input = new ArrayOfFloats(mapper.readValue(toParse, Float[].class));
      }
      case DOUBLE_ARRAY_OF_INTEGERS -> {
        input = new DoubleArrayOfIntegers(mapper.readValue(toParse, Integer[][].class));
      }
      case DOUBLE_ARRAY_OF_FLOATS -> {
        input = new DoubleArrayOfFloats(mapper.readValue(toParse, Float[][].class));
      }
      case DOUBLE_ARRAY_OF_STRINGS -> {
        input = new DoubleArrayOfStrings(mapper.readValue(toParse, String[][].class));
      }
    }
    return input;
  }

  public static String VarAsString(Variables variables) throws JsonProcessingException {
    return mapper.writeValueAsString(variables.getValue());
  }

  @SneakyThrows
  public static Variables parseVaraibles(byte[] bytes, Variables.VARIABLES_TYPES variablesTypes) {
    return parseVaraibles(new String(bytes), variablesTypes);
  }

  public static String CodeRunnerToDataBaseLanguageName(CODE_RUNNER_TYPE codeRunnerType) {
    return switch (codeRunnerType) {
      case CPP_RUNNER -> "cpp";
      case JS_RUNNER -> "js";
      case UNIDENTIFIED -> "";
    };
  }

  public static CODE_RUNNER_TYPE LanguageNameToCodeRunner(String languageName) {
    return switch (languageName) {
      case "cpp" -> CODE_RUNNER_TYPE.CPP_RUNNER;
      case "js" -> JS_RUNNER;
      default -> UNIDENTIFIED;
    };
  }

  public static SingleDatabaseExerciseTestRequest toSingleDatabaseExerciseTestRequest(
      ExerciseIdToRunMessage exerciseIdToRunMessage, User user, CODE_RUNNER_TYPE codeRunnerType) {
    Map<CODE_RUNNER_TYPE, String> solutions = new HashMap<>();
    solutions.put(codeRunnerType, exerciseIdToRunMessage.getCode());

    return SingleDatabaseExerciseTestRequest.builder()
        .user(user)
        .solutionCodes(solutions)
        .idOfExercise(exerciseIdToRunMessage.getExercise_id())
        .build();
  }

  public static byte[] jsonMessageToBase64(String image) {
    String imageData = image.split(",")[1];
    return Base64ToBytes(imageData);
  }

  public static String jsonMessageToExtension(String image) {
    return image.split("/")[1].split(";")[0];
  }

  public static byte[] Base64ToBytes(String base64Data) {
    return Base64.getDecoder().decode(base64Data);
  }
}
