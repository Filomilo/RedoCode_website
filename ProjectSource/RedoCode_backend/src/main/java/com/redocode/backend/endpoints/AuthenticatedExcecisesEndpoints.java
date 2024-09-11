package com.redocode.backend.endpoints;

import com.github.dockerjava.api.exception.NotFoundException;
import com.redocode.backend.Messages.ExercisesInfo.ExerciseSolvingState;
import com.redocode.backend.Messages.ExercisesInfo.ResultData;
import com.redocode.backend.Messages.ExercisesInfo.SolutionsData;
import com.redocode.backend.Messages.Requests.CommentPostRequest;
import com.redocode.backend.Messages.Requests.RateRequest;
import com.redocode.backend.UserDataControllers.ExerciseDataControl;
import com.redocode.backend.database.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/secure/exercises")
public class AuthenticatedExcecisesEndpoints {
  @Autowired ExerciseDataControl exerciseDataControl;

  @GetMapping("/solutions")
  public ResponseEntity<SolutionsData> getSolutionsData(
      Long id, @AuthenticationPrincipal User userDetails) {
    log.info("getSolutionsData request: " + id + " from: " + userDetails);
    SolutionsData resp = exerciseDataControl.getSolutionsDataForExerciseOfId(id);
    if (resp == null) {
      log.info("SolutionsData not found for id: " + id);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("sending getSolutionsData: " + resp);
    return new ResponseEntity<>(resp, HttpStatus.OK);
  }

  @GetMapping("/results")
  public ResponseEntity<ResultData> getResultData(
      Long id, @AuthenticationPrincipal User userDetails) {
    log.info("getResultData request: " + id + " from: " + userDetails);
    ResultData resp = exerciseDataControl.getResultDataForExerciseOfUser(id, userDetails.getId());
    if (resp == null) {
      log.info("getResultData: ResultData not found for id: " + id);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("sending getResultData: " + resp);
    return new ResponseEntity<>(resp, HttpStatus.OK);
  }

  @GetMapping("/solutionsCodes")
  public ResponseEntity<String> getSolutionsCodesData(
      Long id, @AuthenticationPrincipal User userDetails) {
    log.info("getSolutionsCodesData request: " + id);
    String code = exerciseDataControl.getSolutionCode(id);
    if (code == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    log.info("sending getSolutionsCodesData: " + code);
    return new ResponseEntity<>(code, HttpStatus.OK);
  }

  @PostMapping("/comment")
  public ResponseEntity postComment(
      @RequestBody CommentPostRequest request, @AuthenticationPrincipal User userDetails) {
    log.info("postComment request: " + request);
    try {
      exerciseDataControl.saveNewComment(
          request.getId(), userDetails.getId(), request.getComment());
    } catch (NotFoundException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.warn("postComment forbidien: " + ex.getMessage());
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    return new ResponseEntity<>("Comment posted", HttpStatus.CREATED);
  }

  @PostMapping("/rate")
  public ResponseEntity postRate(
      @RequestBody RateRequest request, @AuthenticationPrincipal User userDetails) {
    log.info("postRate request: " + request);
    try {
      exerciseDataControl.saveNewRating(request.getId(), userDetails.getId(), request.getRate());
    } catch (NotFoundException ex) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception ex) {
      log.warn("postRate forbidien: " + ex.getMessage());
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity<>("Rating posted", HttpStatus.CREATED);
  }

  @GetMapping("/ExerciseSolvingState")
  public ExerciseSolvingState getExerciseSolvingState(
      Long id, @AuthenticationPrincipal User userDetails) {
    log.info("getExerciseSolvingState request: " + id);
    ExerciseSolvingState exerciseSolvingState =
        exerciseDataControl.getUserSovingState(id, userDetails.getId());
    log.info("getExerciseSolvingState response: " + exerciseSolvingState);
    return exerciseSolvingState;
  }
}
