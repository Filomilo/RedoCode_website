package com.redocode.backend.endpoints;

import com.redocode.backend.Messages.ExercisesInfo.ExerciseSolvingState;
import com.redocode.backend.Messages.ExercisesInfo.ResultData;
import com.redocode.backend.Messages.ExercisesInfo.SolutionsData;
import com.redocode.backend.Messages.Requests.CommentPostRequest;
import com.redocode.backend.Messages.Requests.IdRequest;
import com.redocode.backend.Messages.Requests.RateRequest;
import com.redocode.backend.Secuirity.JwtService;
import com.redocode.backend.Tools.RedoCodeObjectMapper;
import com.redocode.backend.VmAcces.CodeRunners.CODE_RUNNER_TYPE;
import com.redocode.backend.database.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AuthenticatedExcecisesEndpointsTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Autowired JwtService jwtService;

  @Autowired UsersRepository usersRepository;

  @Autowired ExerciseRepository exerciseRepository;
  @Autowired PasswordEncoder passwordEncoder;
  @Autowired SolutionProgramsRepository solutionProgramsRepository;
  @Autowired ProgrammingLanguageRepository programmingLanguageRepository;
  @Autowired CommentsRepository commentsRepository;
  @Autowired ExcersizeDiffucultyRatingRepository excersizeDiffucultyRatingRepository;

  private User _authenticaredUser;
  private String _Token;
  private final String _getSolutionsDataEndPont = "/secure/exercises/solutions?id={id}";
  private final String _getResultDataEndPont = "/secure/exercises/results?id={id}";
  private final String _getSolutionsCodesDataEndPont = "/secure/exercises/solutionsCodes?id={id}";
  private final String _postCommentEndPont = "/secure/exercises/comment";
  private final String _postRateEndPont = "/secure/exercises/rate";
  private final String _getExerciseSolvingStateEndPont =
      "/secure/exercises/ExerciseSolvingState?id={id}";

  private static final Random RANDOM = new Random();

  private long exerciseID = -1;

  private final String exerciseTiitle = "Tile_" + UUID.randomUUID();
  private final String exerciseDesc = "Desc_" + UUID.randomUUID();
  private final Long maxExecutionTIme = RANDOM.nextLong(100, 1000);

  private List<Comment> commentsList = new ArrayList<>();
  private List<SolutionPrograms> solutionProgramsList = new ArrayList<>();

  @BeforeEach
  public void saveExerciseTempleate() {
    Excersize excersize =
        Excersize.builder()
            .excersizeName(exerciseTiitle)
            .description(exerciseDesc)
            .maxExecutionTimeMS(maxExecutionTIme)
            .ram_mb(512)
            .valueLengthRangeMax(500f)
            .valueLengthRangeMin(500f)
            .timeForTaskMin(500l)
            .author(_authenticaredUser)
            .build();
    Excersize saved = exerciseRepository.save(excersize);
    log.info("saved: " + saved);
    this.exerciseID = saved.getId();
    HashSet<User> usersSOlved = new HashSet<>();
    for (long i = 1; i < 8; i++) {
      User user = usersRepository.findById(i).get();
      usersSOlved.add(user);
    }

    for (int i = 0; i < RANDOM.nextLong(4, 20); i++) {
      long useId = RANDOM.nextInt(1, usersSOlved.size());
      User user = this.usersRepository.getReferenceById(useId);
      Comment com =
          Comment.builder()
              .comment("COMMENT_" + UUID.randomUUID().toString())
              .date(java.sql.Date.valueOf(LocalDate.now().minusDays(RANDOM.nextInt(1000))))
              .author(user)
              .excersize(saved)
              .build();
      this.commentsList.add(commentsRepository.save(com));
    }

    for (User user : usersSOlved) {
      SolutionPrograms solution =
          SolutionPrograms.builder()
              .avgExecutionTime(RANDOM.nextLong(400, 500))
              .code("Code_" + UUID.randomUUID().toString())
              .excersize(saved)
              .solutionAuthor(user)
              .language(
                  programmingLanguageRepository.findByName(
                      RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(
                          RANDOM.nextBoolean()
                              ? CODE_RUNNER_TYPE.JS_RUNNER
                              : CODE_RUNNER_TYPE.CPP_RUNNER)))
              .build();
      solutionProgramsList.add(solutionProgramsRepository.save(solution));
    }
  }

  // localhost:8080/public/auth/register
  String getFullEndpoint(String endpoint) {
    String endpointResutl = "http://localhost:" + port + endpoint;
    log.info("endpoint get for: " + endpointResutl);
    return endpointResutl;
  }

  @BeforeEach
  void registerUser() {
    User user =
        User.builder()
            .email(UUID.randomUUID().toString() + "@mail.com")
            .nickname(UUID.randomUUID().toString())
            .password(passwordEncoder.encode(UUID.randomUUID().toString()))
            .type(User.USER_TYPE.AUTHENTICATED)
            .ProfilePicture(null)
            .build();
    usersRepository.save(user);
    String token = jwtService.generateToken(user);
    this._authenticaredUser = user;
    this._Token = token;
  }

  private HttpHeaders getAuthHeaders() {

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + this._Token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    return headers;
  }

  @Test
  void getSolutionsDataCorrect() {
    assertNotNull(restTemplate);

    Map<String, Long> params = new HashMap<>();
    params.put("id", exerciseID);

    ResponseEntity<SolutionsData> response =
        restTemplate.exchange(
            getFullEndpoint(_getSolutionsDataEndPont),
            HttpMethod.GET,
            new HttpEntity<>(getAuthHeaders()),
            SolutionsData.class,
            params);

    SolutionsData responseData = response.getBody();

    log.info(responseData.toString());
    assertNotNull(responseData);
    assertEquals(this.exerciseTiitle, responseData.getTitle());
    assertEquals(this.exerciseDesc, responseData.getDesc());
    assertEquals(this.maxExecutionTIme, responseData.getMaxExecutionTimeMs());
    int i = 0;
    log.info(
        Arrays.toString(
            commentsList.stream()
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .map(x -> x.getDate())
                .toArray()));
    for (Object comment :
        commentsList.stream().sorted((a, b) -> a.getDate().compareTo(b.getDate())).toArray()) {
      Comment com = (Comment) comment;
      assertEquals(com.getAuthor().getNickname(), responseData.getComments().get(i).getNickname());
      assertEquals(com.getComment(), responseData.getComments().get(i).getComment());
      assertEquals(
          com.getAuthor().getProfilePicture()==null?"":com.getAuthor().getProfilePicture().getUrl(),
          responseData.getComments().get(i).getProfilePicture());
      log.info(comment.toString());
      i++;
    }

    i = 0;
    for (Object solution :
        solutionProgramsList.stream()
            .sorted(Comparator.comparing(SolutionPrograms::getAvgExecutionTime).reversed())
            .toArray()) {
      SolutionPrograms sol = (SolutionPrograms) solution;
      assertEquals(
          sol.getAvgExecutionTime(), responseData.getSolutionList().get(i).getExecutionTimeMs());
      assertEquals(
          RedoCodeObjectMapper.LanguageNameToCodeRunner(sol.getLanguage().getName()),
          responseData.getSolutionList().get(i).getCodeRunner());
      assertEquals(sol.getId(), responseData.getSolutionList().get(i).getSolutionId());
      assertEquals(
          sol.getSolutionAuthor().getNickname(),
          responseData.getSolutionList().get(i).getUsername());
      assertEquals(
          sol.getSolutionAuthor().getProfilePicture()==null?"":sol.getSolutionAuthor().getProfilePicture().getUrl(),
          responseData.getSolutionList().get(i).getProfilePic());

      i++;
    }
  }

  @Test
  void getSolutionsDataNotExisting() {
    assertNotNull(restTemplate);
    Map<String, Long> params = new HashMap<>();
    params.put("id", -1l);
    ResponseEntity<SolutionsData> response =
        restTemplate.exchange(
            getFullEndpoint(_getSolutionsDataEndPont),
            HttpMethod.GET,
            new HttpEntity<>(getAuthHeaders()),
            SolutionsData.class,
            params);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void getSolutionsDataUnauthenticated() {
    assertNotNull(restTemplate);
    Map<String, Long> params = new HashMap<>();
    params.put("id", exerciseID);

    ResponseEntity<SolutionsData> response =
        restTemplate.exchange(
            getFullEndpoint(_getSolutionsDataEndPont),
            HttpMethod.GET,
            new HttpEntity<>(null),
            SolutionsData.class,
            params);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void getResultData() {
    assertNotNull(restTemplate);

    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .solutionAuthor(this._authenticaredUser)
            .language(programmingLanguageRepository.getReferenceById(1l))
            .avgExecutionTime(999999999l)
            .excersize(exerciseRepository.getReferenceById(this.exerciseID))
            .code("Code_" + UUID.randomUUID().toString())
            .build();
    solutionProgramsRepository.save(solutionPrograms);
    Map<String, Long> params = new HashMap<>();
    params.put("id", exerciseID);

    ResponseEntity<ResultData> response =
        restTemplate.exchange(
            getFullEndpoint(_getResultDataEndPont),
            HttpMethod.GET,
            new HttpEntity<IdRequest>(getAuthHeaders()),
            ResultData.class,
            params);
    log.info("response: " + response);
    ResultData responseData = response.getBody();
    assertEquals(solutionPrograms.getAvgExecutionTime(), responseData.getExecutionTimeMs());
    assertEquals(this.solutionProgramsList.size() + 1, responseData.getSolutionRanking());
    assertEquals(0f, responseData.getBetterThanProcent());
    assertEquals(
        exerciseRepository.getReferenceById(this.exerciseID).getMaxExecutionTimeMS(),
        responseData.getMaxExecutionTimeMs());
  }

  @Test
  void getResultDataNoExercise() {
    assertNotNull(restTemplate);

    Map<String, Long> params = new HashMap<>();
    params.put("id", -1l);

    ResponseEntity<ResultData> response =
        restTemplate.exchange(
            getFullEndpoint(_getResultDataEndPont),
            HttpMethod.GET,
            new HttpEntity<IdRequest>(getAuthHeaders()),
            ResultData.class,
            params);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void getResultUnathenticated() {
    assertNotNull(restTemplate);

    Map<String, Long> params = new HashMap<>();
    params.put("id", -1l);

    ResponseEntity<ResultData> response =
        restTemplate.exchange(
            getFullEndpoint(_getResultDataEndPont),
            HttpMethod.GET,
            new HttpEntity<>(null),
            ResultData.class,
            params);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void getSolutionsCodesData() {
    assertNotNull(restTemplate);
    for (SolutionPrograms programs : this.solutionProgramsList) {
      Map<String, Long> params = new HashMap<>();
      params.put("id", programs.getId());

      log.info("idRequest: " + programs.getId());
      ResponseEntity<String> response =
          restTemplate.exchange(
              getFullEndpoint(_getSolutionsCodesDataEndPont),
              HttpMethod.GET,
              new HttpEntity<IdRequest>(getAuthHeaders()),
              String.class,
              params);
      log.info("response: " + response);
      String responseData = response.getBody();
      log.info("response body: " + responseData);
      assertEquals(programs.getCode(), responseData);
    }
  }

  @Test
  void getSolutionsCodesNoDatea() {
    assertNotNull(restTemplate);
    Map<String, Long> params = new HashMap<>();
    params.put("id", -1l);
    ResponseEntity<String> response =
        restTemplate.exchange(
            getFullEndpoint(_getSolutionsCodesDataEndPont),
            HttpMethod.GET,
            new HttpEntity<IdRequest>(getAuthHeaders()),
            String.class,
            params);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void getSolutionsCodesNoAunthenitcaion() {
    assertNotNull(restTemplate);
    Map<String, Long> params = new HashMap<>();
    params.put("id", -1l);
    ResponseEntity<String> response =
        restTemplate.exchange(
            getFullEndpoint(_getSolutionsCodesDataEndPont),
            HttpMethod.GET,
            new HttpEntity<>(null),
            String.class,
            params);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void postCommentCorrect() {

    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .solutionAuthor(this._authenticaredUser)
            .language(programmingLanguageRepository.getReferenceById(1l))
            .excersize(exerciseRepository.getReferenceById(this.exerciseID))
            .avgExecutionTime(100l)
            .build();
    solutionProgramsRepository.save(solutionPrograms);

    ExcersizeDiffucultyRating excersizeDiffucultyRating =
        new ExcersizeDiffucultyRating(
            this._authenticaredUser, exerciseRepository.getReferenceById(this.exerciseID), 5);
    excersizeDiffucultyRatingRepository.save(excersizeDiffucultyRating);

    CommentPostRequest commentPostRequest =
        CommentPostRequest.builder()
            .comment("Comment_" + UUID.randomUUID())
            .id(this.exerciseID)
            .build();
    log.info("commentPostRequest: " + commentPostRequest);
    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postCommentEndPont),
            HttpMethod.POST,
            new HttpEntity<CommentPostRequest>(commentPostRequest, getAuthHeaders()),
            Void.class);
    log.info("response: " + response);
    log.info("getStatusCode: " + response.getStatusCode());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    Comment newestInDataBase = commentsRepository.findAllByOrderByDateDesc().get(0);
    assertEquals(commentPostRequest.getComment(), newestInDataBase.getComment());
    assertEquals(this._authenticaredUser, newestInDataBase.getAuthor());
    assertEquals(commentPostRequest.getId(), newestInDataBase.getExcersize().getId());
  }

  @Test
  void postCommentUnathenticated() {
    CommentPostRequest commentPostRequest =
        CommentPostRequest.builder()
            .comment("Comment_" + UUID.randomUUID())
            .id(this.exerciseID)
            .build();
    log.info("commentPostRequest: " + commentPostRequest);
    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postCommentEndPont),
            HttpMethod.POST,
            new HttpEntity<>(commentPostRequest, null),
            Void.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void postCommentUnattempted() {
    CommentPostRequest commentPostRequest =
        CommentPostRequest.builder().comment("Comment_" + UUID.randomUUID()).id(1).build();
    log.info("commentPostRequest: " + commentPostRequest);
    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postCommentEndPont),
            HttpMethod.POST,
            new HttpEntity<>(commentPostRequest, getAuthHeaders()),
            Void.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void postCommentUnRated() {
    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .solutionAuthor(this._authenticaredUser)
            .language(programmingLanguageRepository.getReferenceById(1l))
            .excersize(exerciseRepository.getReferenceById(1l))
            .avgExecutionTime(100l)
            .build();
    solutionProgramsRepository.save(solutionPrograms);

    CommentPostRequest commentPostRequest =
        CommentPostRequest.builder().comment("Comment_" + UUID.randomUUID()).id(1l).build();
    log.info("commentPostRequest: " + commentPostRequest);
    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postCommentEndPont),
            HttpMethod.POST,
            new HttpEntity<>(commentPostRequest, getAuthHeaders()),
            Void.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void postCommentIncoorectID() {

    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .solutionAuthor(this._authenticaredUser)
            .language(programmingLanguageRepository.getReferenceById(1l))
            .excersize(exerciseRepository.getReferenceById(this.exerciseID))
            .avgExecutionTime(100l)
            .build();
    solutionProgramsRepository.save(solutionPrograms);

    ExcersizeDiffucultyRating excersizeDiffucultyRating =
        new ExcersizeDiffucultyRating(
            this._authenticaredUser, exerciseRepository.getReferenceById(this.exerciseID), 5);

    excersizeDiffucultyRatingRepository.save(excersizeDiffucultyRating);

    CommentPostRequest commentPostRequest =
        CommentPostRequest.builder().comment("Comment_" + UUID.randomUUID()).id(-1).build();
    log.info("commentPostRequest: " + commentPostRequest);
    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postCommentEndPont),
            HttpMethod.POST,
            new HttpEntity<CommentPostRequest>(commentPostRequest, getAuthHeaders()),
            Void.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void postCommentEmptyComment() {

    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .solutionAuthor(this._authenticaredUser)
            .language(programmingLanguageRepository.getReferenceById(1l))
            .excersize(exerciseRepository.getReferenceById(this.exerciseID))
            .avgExecutionTime(100l)
            .build();
    solutionProgramsRepository.save(solutionPrograms);

    ExcersizeDiffucultyRating excersizeDiffucultyRating =
        new ExcersizeDiffucultyRating(
            this._authenticaredUser, exerciseRepository.getReferenceById(this.exerciseID), 5);

    excersizeDiffucultyRatingRepository.save(excersizeDiffucultyRating);

    CommentPostRequest commentPostRequest =
        CommentPostRequest.builder().comment("").id(this.exerciseID).build();
    log.info("commentPostRequest: " + commentPostRequest);
    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postCommentEndPont),
            HttpMethod.POST,
            new HttpEntity<CommentPostRequest>(commentPostRequest, getAuthHeaders()),
            Void.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void postCommentTooLongComment() {

    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .solutionAuthor(this._authenticaredUser)
            .language(programmingLanguageRepository.getReferenceById(1l))
            .excersize(exerciseRepository.getReferenceById(this.exerciseID))
            .avgExecutionTime(100l)
            .build();
    solutionProgramsRepository.save(solutionPrograms);

    ExcersizeDiffucultyRating excersizeDiffucultyRating =
        new ExcersizeDiffucultyRating(
            this._authenticaredUser, exerciseRepository.getReferenceById(this.exerciseID), 5);

    excersizeDiffucultyRatingRepository.save(excersizeDiffucultyRating);
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < 3001; i++) {
      stringBuilder.append("A");
    }

    CommentPostRequest commentPostRequest =
        CommentPostRequest.builder().comment(stringBuilder.toString()).id(this.exerciseID).build();
    log.info("commentPostRequest: " + commentPostRequest);
    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postCommentEndPont),
            HttpMethod.POST,
            new HttpEntity<CommentPostRequest>(commentPostRequest, getAuthHeaders()),
            Void.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void postRate() {

    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .solutionAuthor(this._authenticaredUser)
            .language(programmingLanguageRepository.getReferenceById(1l))
            .excersize(exerciseRepository.getReferenceById(1l))
            .avgExecutionTime(100l)
            .build();
    solutionProgramsRepository.save(solutionPrograms);

    RateRequest rateRequest = RateRequest.builder().rate(RANDOM.nextInt(1, 5)).id(1l).build();

    log.info("rateRequest: " + rateRequest);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postRateEndPont),
            HttpMethod.POST,
            new HttpEntity<RateRequest>(rateRequest, getAuthHeaders()),
            Void.class);
    log.info("response: " + response);
    log.info("getStatusCode: " + response.getStatusCode());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    List<ExcersizeDiffucultyRating> listOfRating = excersizeDiffucultyRatingRepository.findAll();
    ExcersizeDiffucultyRating newestInDataBase = listOfRating.get(listOfRating.size() - 1);
    assertEquals(rateRequest.getRate(), newestInDataBase.getRating());
    assertEquals(this._authenticaredUser.getNickname(), newestInDataBase.getUser().getNickname());
    assertEquals(rateRequest.getId(), newestInDataBase.getExcersize().getId());
  }

  @Test
  void postRateAlreadyRated() {

    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .solutionAuthor(this._authenticaredUser)
            .language(programmingLanguageRepository.getReferenceById(1l))
            .excersize(exerciseRepository.getReferenceById(this.exerciseID))
            .avgExecutionTime(100l)
            .build();
    solutionProgramsRepository.save(solutionPrograms);

    ExcersizeDiffucultyRating excersizeDiffucultyRating =
        new ExcersizeDiffucultyRating(
            this._authenticaredUser, exerciseRepository.getReferenceById(this.exerciseID), 5);

    excersizeDiffucultyRatingRepository.save(excersizeDiffucultyRating);

    RateRequest rateRequest =
        RateRequest.builder().rate(RANDOM.nextInt(1, 5)).id(this.exerciseID).build();

    log.info("rateRequest: " + rateRequest);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postRateEndPont),
            HttpMethod.POST,
            new HttpEntity<RateRequest>(rateRequest, getAuthHeaders()),
            Void.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void postRateNotSOlved() {

    RateRequest rateRequest =
        RateRequest.builder().rate(RANDOM.nextInt(1, 5)).id(this.exerciseID).build();

    log.info("rateRequest: " + rateRequest);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postRateEndPont),
            HttpMethod.POST,
            new HttpEntity<RateRequest>(rateRequest, getAuthHeaders()),
            Void.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void postRateNotuthenticated() {

    RateRequest rateRequest =
        RateRequest.builder().rate(RANDOM.nextInt(1, 5)).id(this.exerciseID).build();

    log.info("rateRequest: " + rateRequest);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postRateEndPont),
            HttpMethod.POST,
            new HttpEntity<RateRequest>(rateRequest, null),
            Void.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void postRateWorngid() {

    RateRequest rateRequest = RateRequest.builder().rate(RANDOM.nextInt(1, 5)).id(-1).build();

    log.info("rateRequest: " + rateRequest);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postRateEndPont),
            HttpMethod.POST,
            new HttpEntity<RateRequest>(rateRequest, getAuthHeaders()),
            Void.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void postWrongRate() {

    RateRequest rateRequest = RateRequest.builder().rate(44).id(this.exerciseID).build();
    log.info("rateRequest: " + rateRequest);
    ResponseEntity<Void> response =
        restTemplate.exchange(
            getFullEndpoint(_postRateEndPont),
            HttpMethod.POST,
            new HttpEntity<RateRequest>(rateRequest, getAuthHeaders()),
            Void.class);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void getExerciseSolvingState() {
    Map<String, Long> params = new HashMap<>();
    params.put("id", 1l);

    ResponseEntity<ExerciseSolvingState> response =
        restTemplate.exchange(
            getFullEndpoint(_getExerciseSolvingStateEndPont),
            HttpMethod.GET,
            new HttpEntity<Void>(getAuthHeaders()),
            ExerciseSolvingState.class,
            params);

    log.info("response: " + response);
    log.info("getStatusCode: " + response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(ExerciseSolvingState.UNATTEMPTED, response.getBody());

    SolutionPrograms solutionPrograms =
        SolutionPrograms.builder()
            .solutionAuthor(this._authenticaredUser)
            .language(programmingLanguageRepository.getReferenceById(1l))
            .excersize(exerciseRepository.getReferenceById(1l))
            .avgExecutionTime(100l)
            .build();
    solutionProgramsRepository.save(solutionPrograms);

    response =
        restTemplate.exchange(
            getFullEndpoint(_getExerciseSolvingStateEndPont),
            HttpMethod.GET,
            new HttpEntity<Void>(getAuthHeaders()),
            ExerciseSolvingState.class,
            params);
    assertNotNull(response.getBody());
    assertEquals(ExerciseSolvingState.SOLVED, response.getBody());

    ExcersizeDiffucultyRating excersizeDiffucultyRating =
        new ExcersizeDiffucultyRating(
            this._authenticaredUser, exerciseRepository.getReferenceById(1l), 5);

    excersizeDiffucultyRatingRepository.save(excersizeDiffucultyRating);

    response =
        restTemplate.exchange(
            getFullEndpoint(_getExerciseSolvingStateEndPont),
            HttpMethod.GET,
            new HttpEntity<Void>(getAuthHeaders()),
            ExerciseSolvingState.class,
            params);
    assertNotNull(response.getBody());
    assertEquals(ExerciseSolvingState.RATED, response.getBody());
  }

  @Test
  void getExerciseSolvingStateUnathenticated() {
    Map<String, Long> params = new HashMap<>();
    params.put("id", this.exerciseID);

    ResponseEntity<ExerciseSolvingState> response =
        restTemplate.exchange(
            getFullEndpoint(_getExerciseSolvingStateEndPont),
            HttpMethod.GET,
            new HttpEntity<>(null),
            ExerciseSolvingState.class,
            params);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }
}
