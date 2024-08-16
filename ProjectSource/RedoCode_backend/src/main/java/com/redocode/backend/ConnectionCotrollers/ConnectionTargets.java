package com.redocode.backend.ConnectionCotrollers;

public class ConnectionTargets {
  public static final String INrunExerciseById = "/CodeRun/ExerciseIdRun";
  public static final String INrunRawCode = "/CodeRun/RawCodeRun";
  public static final String INrunExercsieIdValidationCode = "/CodeRun/ExerciseIdValidation";
  public static final String INrunExercsieTestsCode = "/CodeRun/ExerciseCodeTests";
  public static final String INrunExerciseCreatorValidationCode =
      "/CodeRun/ExerciseCreationValidation";
  public static final String INstompAuthenticaiton = "/tokenAuth";
  public static final String OUTNodeStatusUpdate = "/NodeStatusUpdate";

  public static final String INStompHealthCheck = "/public/Health";
}
