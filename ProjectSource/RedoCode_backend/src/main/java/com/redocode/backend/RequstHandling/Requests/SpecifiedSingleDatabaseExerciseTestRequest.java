package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeResultsRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeRunSpecificationParametersRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeRunnerRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ITestsToRunRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.ExerciseTests;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@SuperBuilder
@Data
public class SpecifiedSingleDatabaseExerciseTestRequest extends SingleDatabaseExerciseTestRequest
    implements ITestsToRunRequest,
        ICodeRunSpecificationParametersRequest,
        ICodeRunnerRequest,
        ICodeResultsRequest {
  @NonNull protected Long timeForExecution;
  @NonNull protected Variables.VARIABLES_TYPES inputType;
  @NonNull protected Variables.VARIABLES_TYPES outputType;
  protected List<ExerciseTests> AutotestsToRun;
  protected List<ExerciseTests> TestsToRun;
  protected int ram;

  int amountOfAutoTests;
  Range lengthRange;
  Range xArrayRange;
  Range yArrayRange;
  boolean upperCaseInput;
  boolean lowerCaseInput;
  boolean numberInput;
  boolean specialCharacterInput;
  boolean breakCharacterInput;
  boolean spaceInput;

  protected Map<CODE_RUNNER_TYPE, List<ProgramResult>> programResults;

  /**
   * {@inheritDoc} . <br>
   * In this case, the should be only one solution provided so code runner requested should be equal
   * to a solution to that code runner
   */
  @Override
  public CODE_RUNNER_TYPE getCodeRunnerType() {
    return this.solutionCodes.keySet().stream().findFirst().orElse(CODE_RUNNER_TYPE.UNIDENTIFIED);
  }
}
