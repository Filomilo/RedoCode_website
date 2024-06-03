package com.redocode.backend.RequstHandling.Requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExerciseCretionRequest extends CodeTestRequest{

    String Title;
    String Description;



}
