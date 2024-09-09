package com.redocode.backend.UserDataControllers;

import com.github.dockerjava.api.exception.NotFoundException;
import com.redocode.backend.Messages.ExercisesInfo.*;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.database.*;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
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
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ExcersizeDiffucultyRatingRepository excersizeDiffucultyRatingRepository;

    public ResultData getResultDataForExerciseOfUser(long idOfExercise, long idOfUser)
    {
        if(!exerciseRepository.findById(idOfExercise).isPresent() || !usersRepository.findById(idOfUser).isPresent())
        {
            return null;
        }
        SolutionPrograms thisUserProgram = solutionProgramsRepository.findFirstByExcersizeIdAndSolutionAuthorId(idOfExercise,idOfUser);
        int AllOFSolutions= solutionProgramsRepository.countAllByExcersizeId(idOfExercise);
        int solutionWorseThanUSer=solutionProgramsRepository.countAllByExcersizeIdAndAvgExecutionTimeGreaterThan(idOfExercise,thisUserProgram.getAvgExecutionTime());

        return  ResultData.builder()
                .betterThanProcent(solutionWorseThanUSer/AllOFSolutions*100)
                .executionTimeMs(thisUserProgram.getAvgExecutionTime())
                .maxExecutionTimeMs(thisUserProgram.getExcersize().getMaxExecutionTimeMS())
                .SolutionRanking(AllOFSolutions-solutionWorseThanUSer)
                .build();
    }


    public SolutionsData getSolutionsDataForExerciseOfId(@NotNull Long ExerciseId) {
        if(!exerciseRepository.findById(ExerciseId).isPresent())
            return null;
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

    public String getSolutionCode(long solutionID)
    {
        if(!solutionProgramsRepository.findById(solutionID).isPresent())
            return null;
        return solutionProgramsRepository.getReferenceById(solutionID).getCode();
    }


    public void saveNewComment(Long exerciseID, Long userID, String comment) {
        Comment newComment = Comment.builder()
                .date(new Date())
                .author(usersRepository.getReferenceById(userID))
                .excersize(exerciseRepository.getReferenceById(exerciseID))
                .comment(comment).build();
        commentsRepository.save(newComment);
    }

    public void saveNewRating(Long exercsieID, Long userId, int rate) {
        ExcersizeDiffucultyRating excersizeDiffucultyRating=
                new ExcersizeDiffucultyRating(
                        usersRepository.getReferenceById(userId)
                        ,exerciseRepository.getReferenceById(exercsieID)
                        ,rate
                );
        excersizeDiffucultyRatingRepository.save(excersizeDiffucultyRating);
    }

    public ExerciseSolvingState getUserSovingState(Long exerciseId, Long userId) {
        SolutionPrograms solutionPrograms=solutionProgramsRepository.findFirstByExcersizeIdAndSolutionAuthorId(exerciseId,userId);
        if(solutionPrograms==null)
            return ExerciseSolvingState.UNATTEMPTED;
        ExcersizeDiffucultyRating rating= excersizeDiffucultyRatingRepository.findFirstByUserIdAndExcersizeId(userId,exerciseId);
        if(rating==null)
            return ExerciseSolvingState.SOLVED;
        return  ExerciseSolvingState.RATED;

    }
}
