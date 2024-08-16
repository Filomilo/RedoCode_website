package com.redocode.backend.RequstHandling.Requests.Interfaces;

import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import com.redocode.backend.database.ExerciseTests;

import java.util.List;

/**
 * A chain of responsibility request base interface, for managing exercise tests <br/>
 * contains definitions for getting and setting of exercise tests
 */
public interface ITestsToRunRequest {
    /**
     * returns list of Exercise Tests to be handled by requests handler
     * @return List of type ExerciseTests
     */
    public List<ExerciseTests> getTestsToRun();

    /**
     * setter for exercise tests
     * @param tests List of type ExerciseTests
     */
    public void setTestsToRun(List<ExerciseTests>  tests);

    public List<ExerciseTests> getAutotestsToRun();

    public Variables.VARIABLES_TYPES getInputType();
    public Variables.VARIABLES_TYPES getOutputType();

}
