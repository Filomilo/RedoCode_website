package com.redocode.backend.RequstHandling.Requests.Interfaces;

import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.VmAcces.CodeRunners.CodeRunner;

import java.util.Map;

/**
 * Interface used in request to be used in chain of reposbilisty handleerers, This interface defines method used for magnaing solution cdoes with in a request
 */
public interface ISolutionCodesRequest {
    /**
     * A mehtod fo retiriving oslution codes
     * @param solutionCodes Map of types {@link CODE_RUNNER_TYPE CODE_RUNNER_TYPE} and values of String with solution code
     */
    public void setSolutionCodes(Map<CODE_RUNNER_TYPE,String> solutionCodes);

    /**
     * returns map containg soultion codes  with in given request
     * @return Map of types {@link CODE_RUNNER_TYPE CODE_RUNNER_TYPE} and values of String with solution code
     */
    public Map<CODE_RUNNER_TYPE, String> getSolutionCodes();

    public Long getTimeForExecution();
}
