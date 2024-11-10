package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.RequstHandling.Requests.Interfaces.ITestResultsRequest;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import com.redocode.backend.VmAcces.CodeRunners.Program.TestResults;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PorgramReusltsSendRequest extends RequestBase implements ITestResultsRequest {
  Map<CODE_RUNNER_TYPE, List<TestResults>> programResults;
}
