package com.redocode.backend.UserDataControllers;

import com.redocode.backend.Messages.AmountOfLatlyDonePart;
import com.redocode.backend.Messages.StatisticMessage;
import com.redocode.backend.Messages.UserDetailsMessage;
import com.redocode.backend.database.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserDataControl {

  @Autowired ExerciseRepository exerciseRepository;

  @Autowired private SolutionProgramsRepository solutionProgramsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MediaRepository mediaRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public StatisticMessage getUserStats(long userId) {

    List<AmountOfLatlyDonePart> AmountOfLatlyDoneParts = new ArrayList<AmountOfLatlyDonePart>();
    List<SolutionPrograms> solutionProgramsList =
        solutionProgramsRepository.findAllBySolutionAuthorId(userId);

    for (int i = 6; i >= 0; i--) {
      LocalDate localDateCurr = LocalDate.now();
      LocalDate localDateLowBound = localDateCurr.minusDays(i);
      LocalDate localDateUpperBound = localDateCurr.minusDays(i - 1);
      LocalDateTime dateTimeAtZeroLowBOund =
          LocalDateTime.of(localDateLowBound, LocalTime.MIDNIGHT);
      LocalDateTime dateTimeAtZeroUpBOund =
          LocalDateTime.of(localDateUpperBound, LocalTime.MIDNIGHT);

      Date dateLowBound =
          Date.from(dateTimeAtZeroLowBOund.atZone(ZoneId.systemDefault()).toInstant());
      Date dateUppBound =
          Date.from(dateTimeAtZeroUpBOund.atZone(ZoneId.systemDefault()).toInstant());
      long count =
          solutionProgramsList.stream()
              .filter(
                  x ->
                      x.getDate().before(dateUppBound)
                          && !x.getDate().before(dateLowBound)
                          && x.getExcersize().getAuthor().getId() != userId)
              .count();
      AmountOfLatlyDoneParts.add(
          AmountOfLatlyDonePart.builder().amount(count).date(dateLowBound).build());
    }
    return StatisticMessage.builder()
        .amountOfLatelyDone(AmountOfLatlyDoneParts)
        .LanguageUse(
            solutionProgramsRepository.findLanguageAmountForExercsieNotMadeThisUser(userId))
        .build();
  }

    public void changeAccountImage(Long id, byte[] bytes,String ext) {

      Media media = Media.builder()
              .data(bytes)
              .extension(ext)

              .build();
Media saved=mediaRepository.save(media);

      User user= usersRepository.getReferenceById(id);
    user.setProfilePicture(saved);
    usersRepository.save(user);
    }

  public void changePassword(Long id, String password, String newPassword) throws Exception {
    User user=usersRepository.getReferenceById(id);

    if(!passwordEncoder.matches(password, user.getPassword())) {
      throw new Exception("Wrong password");
    }
    user.setPassword(passwordEncoder.encode(newPassword) );
    usersRepository.save(user);
  }

  public void removeAccount(Long id, String password) throws Exception {
    User user=usersRepository.getReferenceById(id);
    if(!passwordEncoder.matches(password, user.getPassword())) {
      throw new Exception("Wrong password");
    }
    Media media=user.getProfilePicture();
    user.setPassword("REMOVED");
    user.setDescription("");
    user.setType(User.USER_TYPE.UNAUTHENTICATED);
    user.setEmail(UUID.randomUUID().toString()+"@rm.rm");
    user.setProfilePicture(null);
    user.setNickname("REMOVED");

    usersRepository.save(user);
    if(media!=null)
      mediaRepository.delete(media);
  }

  public UserDetailsMessage getUserDetails(Long id) throws Exception {
    User user=usersRepository.getReferenceById(id);
    String mail = user.getEmail();
   UserDetailsMessage userDetailsMessage=UserDetailsMessage.builder()
           .description(user.getDescription())
           .emailSignature(mail.substring(0,1)+"***@"+mail.split("@")[1])
           .build();
   return userDetailsMessage;
  }

  public void setDescription(Long id, String description) throws Exception {
    if(description.length()>3000)
      throw new Exception("Description too long, max 3000 characters");
    User user=usersRepository.getReferenceById(id);
    user.setDescription(description);
    usersRepository.save(user);

  }
}
