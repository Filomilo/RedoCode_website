package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.Authentication.Authentication;
import com.redocode.backend.Messages.Authentication.AuthenticationRequest;
import com.redocode.backend.Messages.ExercisesInfo.ExerciseSolvingState;
import com.redocode.backend.Messages.ExercisesInfo.ResultData;
import com.redocode.backend.Messages.ExercisesInfo.SolutionItemList;
import com.redocode.backend.Messages.ExercisesInfo.SolutionsData;
import com.redocode.backend.Messages.Requests.CommentPostRequest;
import com.redocode.backend.Messages.Requests.IdRequest;
import com.redocode.backend.Messages.Requests.RateRequest;
import com.redocode.backend.UserDataControllers.ExerciseDataControl;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/secure/exercises")

public class AuthenticatedExcecisesEndpoints {
    @Autowired
    ExerciseDataControl exerciseDataControl;


    @GetMapping("/solutions")
    public SolutionsData getSolutionsData(Long id,@AuthenticationPrincipal User userDetails) {
        log.info("getSolutionsData request: " + id+" from: "+userDetails);
        SolutionsData resp = exerciseDataControl.getSolutionsDataForExerciseOfId(id);
        log.info("sending getSolutionsData: "+resp);
        return resp;
    }
    @GetMapping("/results")
    public ResultData getResultData(IdRequest mes,@AuthenticationPrincipal User userDetails) {
        log.info("getResultData request: " + mes+" from: "+userDetails);
        ResultData resp = exerciseDataControl.getResultDataForExerciseOfUser(mes.getId(),userDetails.getId() );
        log.info("sending getResultData: "+resp);
        return resp;
    }

    @GetMapping("/solutionsCodes")
    public String getSolutionsCodesData(IdRequest mes) {
        log.info("getSolutionsCodesData request: " + mes);
        log.info("sending getSolutionsCodesData: " );
        return "TEST";
    }

    @PostMapping("/comment")
    public ResponseEntity postComment(@RequestBody CommentPostRequest request) {
        log.info("postComment request: " + request);

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
    @PostMapping("/rate")
    public ResponseEntity postRate(@RequestBody RateRequest request) {
        log.info("postRate request: " + request);

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

    @GetMapping("/ExerciseSolvingState")
    public ExerciseSolvingState getExerciseSolvingState(@RequestBody IdRequest request) {
        log.info("getExerciseSolvingState request: " + request);

        return ExerciseSolvingState.SOLVED;
    }
}
