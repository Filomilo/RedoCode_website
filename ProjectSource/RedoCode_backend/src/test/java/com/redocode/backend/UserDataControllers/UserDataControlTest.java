package com.redocode.backend.UserDataControllers;

import com.redocode.backend.Messages.LanguageUsePart;
import com.redocode.backend.Messages.StatisticMessage;
import com.redocode.backend.Messages.UserDetailsMessage;
import com.redocode.backend.RedoCodeController;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.database.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDataControlTest {

  @Autowired UserDataControl userDataControl;
  @Autowired ProgrammingLanguageRepository programmingLanguageRepository;
  @Autowired SolutionProgramsRepository SolutionProgramsRepository;

  @Autowired ExerciseRepository exerciseRepository;
  @Autowired private UsersRepository usersRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  private static User user;
  private static List<SolutionPrograms> solutionPrograms = new ArrayList<SolutionPrograms>();
  private static List<Integer> amounntOFExercsiesDoneTodayAndPreviousDay = new ArrayList<>();
  @Autowired private SolutionProgramsRepository solutionProgramsRepository;

  private static String password= "Password_";
  private static String img="iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAQUlEQVR42mOYsFbtPzk4daIMGDNQzQCYALHYKJcPjCk3oDPO+j8IJ1tpkoUpN2Bxlc9/EIYZRCweNWB4GTDgKREAV3gQO7H1SHUAAAAASUVORK5CYII=";
  private static String ext="png";
  private static String newPass=UUID.randomUUID().toString();

  @BeforeAll
  void setupData() {
    user =
        User.builder()
            .email(UUID.randomUUID() + "@mail.com")
            .nickname(UUID.randomUUID() + "_nick")
            .password(passwordEncoder.encode(password))
            .ProfilePicture(null)
            .type(User.USER_TYPE.AUTHENTICATED)
                .description("Description_"+UUID.randomUUID())
            .build();

    usersRepository.save(user);
    Random rand = new Random();
    Excersize excersize = exerciseRepository.getReferenceById(1l);
    List<ProgrammingLanguage> langs = programmingLanguageRepository.findAll();
    for (int i = 0; i < 8; i++) {
      int amountOfSOutionThisDate = 1; // rand.nextInt(10);
      LocalDate localDate = LocalDate.now();
      localDate = localDate.minusDays(i);
      Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
      amounntOFExercsiesDoneTodayAndPreviousDay.add(amountOfSOutionThisDate);
      for (int j = 0; j < amountOfSOutionThisDate; j++) {
        SolutionPrograms program =
            SolutionPrograms.builder()
                .solutionAuthor(user)
                .excersize(excersize)
                .avgExecutionTime(23l)
                .language(langs.get(rand.nextInt(0, langs.size())))
                .code(UUID.randomUUID().toString())
                .date(date)
                .build();
        solutionPrograms.add(program);
        SolutionProgramsRepository.save(program);
      }
    }
  }

  @Test
  void testGetUserStats() {
    StatisticMessage statisticMessage = userDataControl.getUserStats(user.getId());
    log.info(
        "statisticMessage: getLanguageUse  "
            + Arrays.toString(statisticMessage.getLanguageUse().toArray()));
    log.info(
        "statisticMessage getAmountOfLatelyDone:  "
            + Arrays.toString(statisticMessage.getAmountOfLatelyDone().toArray()));
    log.info("solutionPrograms :  " + Arrays.toString(solutionPrograms.toArray()));
    final int amountOFElementsInLatelyDone = 7;
    final Set<ProgrammingLanguage> languageThatShouldBeInStats =
        solutionPrograms.stream().map(x -> x.getLanguage()).collect(Collectors.toSet());
    final int amountOfElemtnsInLangaugeStatsic = (int) languageThatShouldBeInStats.size();

    assertEquals(amountOfElemtnsInLangaugeStatsic, statisticMessage.getLanguageUse().size());
    assertEquals(amountOFElementsInLatelyDone, statisticMessage.getAmountOfLatelyDone().size());

    for (LanguageUsePart languageUsePart : statisticMessage.getLanguageUse()) {
      String lanugaeName = languageUsePart.getName();
      ProgrammingLanguage programmingLanguage =
          programmingLanguageRepository.findByName(lanugaeName);
      int amountOFDoneWithThisLangauege =
          (int)
              solutionPrograms.stream()
                  .filter(x -> x.getLanguage().getId() == programmingLanguage.getId())
                  .count();
      log.info("chekcing: " + languageUsePart + " with " + amountOFDoneWithThisLangauege);
      assertEquals(amountOFDoneWithThisLangauege, languageUsePart.getAmount());
    }
    int index = 0;
    for (int i = 6; i >= 0; i--) {
      int amountThatShouldBe = amounntOFExercsiesDoneTodayAndPreviousDay.get(i);
      LocalDate localDate = LocalDate.now();
      localDate = localDate.minusDays(i);
      LocalDateTime dateTimeAtZero = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
      Date dateShouldBe = Date.from(dateTimeAtZero.atZone(ZoneId.systemDefault()).toInstant());
      log.info(
          "echecking: "
              + statisticMessage.getAmountOfLatelyDone().get(index)
              + " with "
              + amountThatShouldBe);
      assertEquals(
          amountThatShouldBe, statisticMessage.getAmountOfLatelyDone().get(index).getAmount());
      assertEquals(
          0,
          dateShouldBe
              .toInstant()
              .compareTo(
                  statisticMessage.getAmountOfLatelyDone().get(index).getDate().toInstant()));
      index++;
    }

    log.info(statisticMessage.toString());
    assertNotNull(statisticMessage);
  }

    @Test
    void changeAccountImage() {
      byte[] bytes= RedoCodeObjectMapper.Base64ToBytes(img);
      userDataControl.changeAccountImage(user.getId(),bytes,ext);
      User userRetrived=usersRepository.getReferenceById(user.getId());
      assertArrayEquals(bytes,userRetrived.getProfilePicture().getData());
    }

    @Test
    void changePassword() {
      assertDoesNotThrow(()->{
        userDataControl.changePassword(user.getId(),password,newPass);
        User userRetrived=usersRepository.getReferenceById(user.getId());
        assertTrue(passwordEncoder.matches(newPass,userRetrived.getPassword()));

      });
    }

  @Test
  void changePasswordFail() {
    String prevoiudPass=user.getPassword();
    assertThrows(Exception.class,()->{
      userDataControl.changePassword(user.getId(),"",newPass);
      });
    User userRetrived=usersRepository.getReferenceById(user.getId());
  assertEquals(prevoiudPass,userRetrived.getPassword());
  }

    @Test
    void removeAccount() {

      User user =
              User.builder()
                      .email(UUID.randomUUID() + "@mail.com")
                      .nickname(UUID.randomUUID() + "_nick")
                      .password(passwordEncoder.encode(password))
                      .ProfilePicture(null)
                      .type(User.USER_TYPE.AUTHENTICATED)
                      .description("Description_"+UUID.randomUUID())
                      .build();

    User userToDelete=  usersRepository.save(user);

    assertDoesNotThrow(()->{
      userDataControl.removeAccount(userToDelete.getId(),password);

    });
      User userRetrived=usersRepository.getReferenceById(userToDelete.getId());
      assertEquals("REMOVED",userRetrived.getPassword());
      assertNull(userRetrived.getProfilePicture());
      assertTrue(userRetrived.getEmail().endsWith("@rm.rm"));
      assertEquals("REMOVED",userRetrived.getNickname());
      assertEquals(User.USER_TYPE.UNAUTHENTICATED,userRetrived.getUserType());
    }
  @Test
  void removeAccountFail() {
    assertThrows(Exception.class,()->{
      userDataControl.removeAccount(user.getId(),user.getPassword());

    });
    User userRetrived=usersRepository.getReferenceById(user.getId());
    assertEquals(userRetrived.getEmail(),user.getEmail());
    }

    @Test
    void getUserDetails() {
    User userOnDb=usersRepository.getReferenceById(user.getId());
    assertDoesNotThrow(()->{
      UserDetailsMessage userDetailsMessage= userDataControl.getUserDetails(userOnDb.getId());
      assertEquals(userOnDb.getEmail().substring(0,1)+"***"+"@mail.com",userDetailsMessage.getEmailSignature());
      assertEquals(userOnDb.getDescription(),userDetailsMessage.getDescription());
    });
    }

    @Test
    void setDescription() {
      String desc="desc_"+UUID.randomUUID();
      assertDoesNotThrow(()->{
        userDataControl.setDescription(user.getId(),desc);
      });
      User userRetrived=usersRepository.getReferenceById(user.getId());
      assertEquals(desc,userRetrived.getDescription());
    }
}
