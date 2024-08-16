package com.redocode.backend.RequstHandling.Requests.Interfaces;

public interface IExerciseIdRequest {
    /**
     * gives id of exercise in database that relates to this request
     * @return id of exercises
     */
    public Long getIdOfExercise();
}
