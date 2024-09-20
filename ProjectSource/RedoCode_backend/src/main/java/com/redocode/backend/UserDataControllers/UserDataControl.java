package com.redocode.backend.UserDataControllers;

import com.redocode.backend.Messages.AmountOfLatlyDonePart;
import com.redocode.backend.Messages.StatisticMessage;
import com.redocode.backend.database.ExerciseRepository;
import com.redocode.backend.database.SolutionPrograms;
import com.redocode.backend.database.SolutionProgramsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserDataControl {

    @Autowired
    ExerciseRepository exerciseRepository;


    @Autowired
    private SolutionProgramsRepository solutionProgramsRepository;

    public StatisticMessage getUserStats(long userId)
    {

        List<AmountOfLatlyDonePart> AmountOfLatlyDoneParts=new ArrayList<AmountOfLatlyDonePart>();
        List<SolutionPrograms> solutionProgramsList=solutionProgramsRepository.findAllBySolutionAuthorId(userId);

        for (int i = 6; i >=0 ; i--) {
            LocalDate localDateCurr = LocalDate.now();
            LocalDate localDateLowBound = localDateCurr.minusDays(i);
            LocalDate localDateUpperBound = localDateCurr.minusDays(i-1);
            LocalDateTime dateTimeAtZeroLowBOund = LocalDateTime.of(localDateLowBound, LocalTime.MIDNIGHT);
            LocalDateTime dateTimeAtZeroUpBOund = LocalDateTime.of(localDateUpperBound, LocalTime.MIDNIGHT);

            Date dateLowBound = Date.from(dateTimeAtZeroLowBOund.atZone(ZoneId.systemDefault()).toInstant());
            Date dateUppBound = Date.from(dateTimeAtZeroUpBOund.atZone(ZoneId.systemDefault()).toInstant());
            long count=solutionProgramsList.stream()
                    .filter(x-> x.getDate().before(dateUppBound) && !x.getDate().before(dateLowBound)&& x.getSolutionAuthor().getId()!=userId)
                    .count();
            AmountOfLatlyDoneParts.add(
                    AmountOfLatlyDonePart.builder()
                            .amount(count)
                            .date(dateLowBound)
                            .build()
            );
        }
        return StatisticMessage.builder()
                .amountOfLatelyDone(AmountOfLatlyDoneParts)
                .LanguageUse(
                        solutionProgramsRepository.findLanguageAmountForExercsieNotMadeThisUser(userId)
                )
                .build();
    }


}
