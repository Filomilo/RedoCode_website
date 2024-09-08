package com.redocode.backend.UserDataControllers;

import com.redocode.backend.Messages.ExercisesInfo.CommentType;
import com.redocode.backend.Messages.ExercisesInfo.ResultData;
import com.redocode.backend.Messages.ExercisesInfo.SolutionItemList;
import com.redocode.backend.Messages.ExercisesInfo.SolutionsData;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.database.*;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExerciseDataControl {

    @Autowired
    SolutionProgramsRepository solutionProgramsRepository;
    @Autowired
    CommentsRepository commentsRepository;
    @Autowired
    ExerciseRepository exerciseRepository;

    public ResultData getResultDataForExerciseOfUser(long idOfExercise, long idOfUser)
    {
        return  ResultData.builder()
                .betterThanProcent(47.2f)
                .executionTimeMs(441)
                .maxExecutionTimeMs(726)
                .SolutionRanking(4)
                .build();
    }


    public SolutionsData getSolutionsDataForExerciseOfId(@NotNull Long ExerciseId) {
        List<SolutionPrograms> solutionPrograms= solutionProgramsRepository.findAllByExcersizeIdOrderByAvgExecutionTimeDesc(ExerciseId);

        List<SolutionItemList> solutionItemLists = solutionPrograms.stream()
                .map(x -> SolutionItemList.builder()
                        .solutionId(x.getId())
                        .codeRunner(RedoCodeObjectMapper.LanguageNameToCodeRunner(x.getLanguage().getName()))
                        .username(x.getSolutionAuthor().getUsername())
                        .profilePic(x.getSolutionAuthor().getProfilePicture())
                        .executionTimeMs(x.getAvgExecutionTime())
                        .build()
                )
                .collect(Collectors.toList());
        List<Comment> comments= commentsRepository.findAllByExcersizeIdOrderByDateAsc(ExerciseId);
        log.info(Arrays.toString(comments.stream().map(x -> x.getDate()).toArray()));
        List<CommentType> commentTypes = comments.stream().map(
                x-> CommentType.builder()
                        .comment(x.getComment())
                        .profilePicture(x.getAuthor().getProfilePicture())
                        .nickname(x.getAuthor().getNickname())
                        .build()
        ).collect(Collectors.toList());

        Excersize excersize= exerciseRepository.findById(ExerciseId).get();

        return SolutionsData.builder()
                .title(excersize.getExcersizeName())
                .desc(excersize.getDescription())
                .solutionList(solutionItemLists)
                .maxExecutionTimeMs(excersize.getMaxExecutionTimeMS())
                .comments(commentTypes)
                .build();
    }
}
