package com.redocode.backend.RequstHandling.Requests.Interfaces;

/**
 * interface used in chain of resposbility to handle request realted to a specifc exercise id in database, it declares method to get id of that exercise
 * @see com.redocode.backend.RequstHandling.Requests.SingleDatabaseExerciseTestRequest SingleDatabaseExerciseTestRequest
 */
public interface IExerciseIdRequest {
    /**
     * gives id of exercise in database that relates to this request
     * @return id of exercises
     */
    public Long getIdOfExercise();
}
