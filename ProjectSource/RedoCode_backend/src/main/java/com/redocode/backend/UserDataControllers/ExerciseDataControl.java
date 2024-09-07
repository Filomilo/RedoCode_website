package com.redocode.backend.UserDataControllers;

import com.redocode.backend.Messages.ExercisesInfo.CommentType;
import com.redocode.backend.Messages.ExercisesInfo.ResultData;
import com.redocode.backend.Messages.ExercisesInfo.SolutionItemList;
import com.redocode.backend.Messages.ExercisesInfo.SolutionsData;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ExerciseDataControl {

    public ResultData getResultDataForExerciseOfUser(long idOfExercise, long idOfUser)
    {
        return  ResultData.builder()
                .betterThanProcent(47.2f)
                .executionTimeMs(441)
                .maxExecutionTimeMs(726)
                .SolutionRanking(4)
                .build();
    }


    public SolutionsData getSolutionsDataForExerciseOfId(Long id) {
        List<SolutionItemList>  solutionItemLists= new ArrayList<>();
        solutionItemLists.add(
                SolutionItemList.builder()
                        .date(new Date())
                        .solutionId(4)
                        .codeRunner(CODE_RUNNER_TYPE.CPP_RUNNER)
                        .username("User name")
                        .profilePic("")
                        .executionTimeMs(100)
                        .build()
        );

        solutionItemLists.add(
                SolutionItemList.builder()
                        .date(new Date())
                        .solutionId(5)
                        .codeRunner(CODE_RUNNER_TYPE.JS_RUNNER)
                        .username("User name2")
                        .profilePic("")
                        .executionTimeMs(25)
                        .build()
        );

        List<CommentType> commentTypes = new ArrayList<>();
        commentTypes.add(
                CommentType.builder()
                        .comment("Comment ")
                        .profilePicture("")
                        .username("Username1")
                        .build()
        );
        commentTypes.add(
                CommentType.builder()
                        .comment("Comment2 ")
                        .profilePicture("")
                        .username("Username2")
                        .build()
        );

        return SolutionsData.builder()
                .title("Title")
                .desc("descirtipn")
                .solutionList(solutionItemLists)
                .maxExecutionTimeMs(500)
                .comments(commentTypes)
                .build();
    }
}
