package com.redocode.backend.RequstHandling.Requests;

import com.redocode.backend.RequstHandling.Requests.Interfaces.ICodeResultsRequest;
import com.redocode.backend.VmAcces.CodeRunners.Program.ProgramResult;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PorgramReusltsSendRequest extends RequestBase implements ICodeResultsRequest {
  List<ProgramResult> programResults;
}
