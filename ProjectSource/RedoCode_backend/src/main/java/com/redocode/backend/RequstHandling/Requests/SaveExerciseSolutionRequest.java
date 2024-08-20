package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeResultsRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.IExerciseIdRequest;
import com.redocode.backend.RequstHandling.Requests.Interfaces.ISolutionCodesRequest;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class SaveExerciseSolutionRequest extends SpecifiedSingleDatabaseExerciseTestRequest
    implements ISolutionCodesRequest, ICodeResultsRequest, IExerciseIdRequest {
  List<ProgramResult> programResults;
}
