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
import org.junit.jupiter.api.BeforeAll;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticatedExcecisesEndpointsTest {

    @LocalServerPort
    private int port;


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    JwtService jwtService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SolutionProgramsRepository solutionProgramsRepository;
    @Autowired
    ProgrammingLanguageRepository programmingLanguageRepository;
    @Autowired
    CommentsRepository commentsRepository;
    @Autowired
    ExcersizeDiffucultyRatingRepository excersizeDiffucultyRatingRepository;


    private User _authenticaredUser;
    private String _Token;
    private final String _getSolutionsDataEndPont = "/secure/exercises/solutions";
    private final String _getResultDataEndPont = "/secure/exercises/results";
    private final String _getSolutionsCodesDataEndPont = "/secure/exercises/solutionsCodes";
    private final String _postCommentEndPont = "/secure/exercises/comment";
    private final String _postRateEndPont = "/secure/exercises/rate";
    private final String _getExerciseSolvingStateEndPont = "/secure/exercises/ExerciseSolvingState";

    private final static Random RANDOM = new Random();

    private   long exerciseID=-1;

    private  final String exerciseTiitle="Tile_"+UUID.randomUUID();
    private  final String exerciseDesc="Desc_"+UUID.randomUUID();
    private final Long maxExecutionTIme= RANDOM.nextLong(100,1000);

    private List<Comment> commentsList=new ArrayList<>();
    private List<SolutionPrograms> solutionProgramsList=new ArrayList<>();

    @BeforeAll
           public void saveExerciseTempleate(){
        Excersize excersize= Excersize.builder()
                .excersizeName(exerciseTiitle)
                .description(exerciseDesc)
                .maxExecutionTimeMS(maxExecutionTIme)
                .ram_mb(512)
                .valueLengthRangeMax(500f)
                .valueLengthRangeMin(500f)
                .timeForTaskMin(500l)
                .build();
        Excersize saved   =exerciseRepository.save(excersize);
        log.info("saved: "+ saved);
        this.exerciseID=saved.getId();
        HashSet<User> usersSOlved=new HashSet<>();
        for (long i = 1; i <8; i++) {
            User user= usersRepository.findById(i).get();
            usersSOlved.add(user);
        }


        for (int i = 0; i < RANDOM.nextLong(4,20) ; i++) {
            long useId=RANDOM.nextInt(1,usersSOlved.size());
            User user= this.usersRepository.getReferenceById(useId);
            Comment com=Comment.builder().comment("COMMENT_"+UUID.randomUUID().toString()).date(
                    java.sql.Date.valueOf( LocalDate.now().plusDays(RANDOM.nextInt(1000)))).author(user).excersize(saved).build();
            this.commentsList.add(commentsRepository.save(com));
        }

        for(User user:usersSOlved){
            SolutionPrograms solution=
                    SolutionPrograms.builder()
                            .AvgExecutionTime(RANDOM.nextLong(400,500))
                            .code("Code_"+UUID.randomUUID().toString())
                            .excersize(saved)
                            .SolutionAuthor(user)
                            .language(programmingLanguageRepository.findByName(RedoCodeObjectMapper.CodeRunnerToDataBaseLanguageName(RANDOM.nextBoolean()? CODE_RUNNER_TYPE.JS_RUNNER: CODE_RUNNER_TYPE.CPP_RUNNER)) )
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

    @BeforeEach  void registerUser()
    {
        User user =
                User.builder()
                        .email(UUID.randomUUID().toString()+"@mail.com")
                        .nickname(UUID.randomUUID().toString())
                        .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                        .type(User.USER_TYPE.AUTHENTICATED)
                        .ProfilePicture("")
                        .build();
        usersRepository.save(user);
        String token = jwtService.generateToken(user);
    this._authenticaredUser = user;
    this._Token = token;
    }

    private HttpHeaders getAuthHeaders()
    {

            HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+this._Token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }



    @Test
    void getSolutionsDataCorrect() {
        assertNotNull(restTemplate);

        IdRequest idRequest = IdRequest.builder()
                .id(this.exerciseID)
                .build();

    log.info("idRequest: "+idRequest);


        ResponseEntity<SolutionsData> response = restTemplate.exchange(
                getFullEndpoint(_getSolutionsDataEndPont), HttpMethod.GET,new HttpEntity<IdRequest>(idRequest, getAuthHeaders()), SolutionsData.class);

        SolutionsData responseData =response.getBody();
//                this.restTemplate.getForObject(
//                        getFullEndpoint(_getSolutionsDataEndPont), SolutionsData.class,(Object)idRequest);
        log.info(responseData.toString());
        assertNotNull(responseData);
//        assertEquals(this.exerciseTiitle,responseData.getTitle());
//        assertEquals(this.exerciseDesc,responseData.getDesc());
//        assertEquals(this.maxExecutionTIme,responseData.getMaxExecutionTimeMs());
        int i=0;
        for (Object comment : commentsList.stream().sorted((a,b)-> a.getDate().compareTo(b.getDate())).toArray())
        {
            Comment com = (Comment) comment;
            assertEquals(
                    com.getAuthor().getUsername()
                    ,responseData.getComments().get(i).getUsername()
            );
            assertEquals(
                    com.getComment()
                    ,responseData.getComments().get(i).getComment()
            );
            assertEquals(
                    com.getAuthor().getProfilePicture()
                    ,responseData.getComments().get(i).getProfilePicture()
            );
            log.info(comment.toString());
            i++;
        }

i=0;
        for (Object solution : solutionProgramsList.stream().sorted(Comparator.comparing(SolutionPrograms::getAvgExecutionTime)).toArray())
        {
            SolutionPrograms sol = (SolutionPrograms) solution;
            assertEquals(sol.getAvgExecutionTime(), responseData.getSolutionList().get(i).getExecutionTimeMs());
            assertEquals(RedoCodeObjectMapper.LanguageNameToCodeRunner(sol.getLanguage().getName()),   responseData.getSolutionList().get(i).getCodeRunner());
            assertEquals(sol.getId(),   responseData.getSolutionList().get(i).getSolutionId());
            assertEquals(sol.getSolutionAuthor().getUsername(),   responseData.getSolutionList().get(i).getUsername());
            assertEquals(sol.getSolutionAuthor().getProfilePicture(),   responseData.getSolutionList().get(i).getProfilePic());


            i++;
        }


    }

    @Test
    void getResultData() {
        assertNotNull(restTemplate);


        SolutionPrograms solutionPrograms= SolutionPrograms.builder()
                .SolutionAuthor(this._authenticaredUser)
                .language(programmingLanguageRepository.getReferenceById(1l))
                .AvgExecutionTime(999999999l)
                .excersize(exerciseRepository.getReferenceById(this.exerciseID))
                .code("Code_"+UUID.randomUUID().toString())

                .build();
        solutionProgramsRepository.save(solutionPrograms);
        IdRequest idRequest = IdRequest.builder()
                .id(this.exerciseID)
                .build();

        log.info("idRequest: "+idRequest);


        ResponseEntity<ResultData> response = restTemplate.exchange(
                getFullEndpoint(_getResultDataEndPont), HttpMethod.GET, new HttpEntity<IdRequest>(idRequest, getAuthHeaders()), ResultData.class);
        log.info("response: "+response);
        ResultData responseData =response.getBody();
        assertEquals(solutionPrograms.getAvgExecutionTime(), responseData.getExecutionTimeMs());
        assertEquals(this.solutionProgramsList.size()+1,responseData.getSolutionRanking());
        assertEquals(0f,responseData.getBetterThanProcent());
        assertEquals(exerciseRepository.getReferenceById(this.exerciseID).getMaxExecutionTimeMS(),responseData.getMaxExecutionTimeMs());
    }

    @Test
    void getSolutionsCodesData() {
        assertNotNull(restTemplate);
for (SolutionPrograms programs: this.solutionProgramsList){
    IdRequest idRequest = IdRequest.builder()
            .id(programs.getId())
            .build();

    log.info("idRequest: "+idRequest);


    ResponseEntity<String> response = restTemplate.exchange(
            getFullEndpoint(_getSolutionsCodesDataEndPont), HttpMethod.GET, new HttpEntity<IdRequest>(idRequest, getAuthHeaders()), String.class);
    log.info("response: "+response);
    String responseData =response.getBody();
    log.info("response body: "+responseData);
    assertEquals(programs.getCode(), responseData);
}
    }

    @Test
    void postCommentCorrect() {
        CommentPostRequest commentPostRequest = CommentPostRequest.builder()
                .comment("Comment_"+UUID.randomUUID())
                .id(this.exerciseID)
                .build();

        log.info("commentPostRequest: "+commentPostRequest);


        ResponseEntity<Void> response = restTemplate.exchange(
                getFullEndpoint(_postCommentEndPont), HttpMethod.POST, new HttpEntity<CommentPostRequest>(commentPostRequest, getAuthHeaders()), Void.class );
        log.info("response: "+response);
        log.info("getStatusCode: "+response.getStatusCode());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Comment newestInDataBase=commentsRepository.findAllByOrderByDateDesc().get(0);
        assertEquals(commentPostRequest.getComment(),newestInDataBase.getComment() );
        assertEquals(this._authenticaredUser,newestInDataBase.getAuthor() );
        assertEquals(commentPostRequest.getId(),newestInDataBase.getExcersize().getId() );
    }


    @Test
    void postRate() {
        RateRequest rateRequest = RateRequest.builder()
                .rate(RANDOM.nextInt(1,5))
                .id(this.exerciseID)
                .build();

        log.info("rateRequest: "+rateRequest);


        ResponseEntity<Void> response = restTemplate.exchange(
                getFullEndpoint(_postRateEndPont), HttpMethod.POST, new HttpEntity<RateRequest>(rateRequest, getAuthHeaders()), Void.class);
        log.info("response: "+response);
        log.info("getStatusCode: "+response.getStatusCode());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        List<ExcersizeDiffucultyRating> listOfRating=excersizeDiffucultyRatingRepository.findAll();
        ExcersizeDiffucultyRating newestInDataBase=listOfRating.get(listOfRating.size()-1);
        assertEquals(rateRequest.getRate(),newestInDataBase.getRating() );
        assertEquals(this._authenticaredUser,newestInDataBase.getUser() );
        assertEquals(rateRequest.getId(),newestInDataBase.getExcersize().getId() );
    }

    @Test
    void getExerciseSolvingState() {
        ResponseEntity<ExerciseSolvingState> response = restTemplate.exchange(
                getFullEndpoint(_getExerciseSolvingStateEndPont), HttpMethod.GET, new HttpEntity<Void>(null, getAuthHeaders()), ExerciseSolvingState.class);
        log.info("response: "+response);
        log.info("getStatusCode: "+response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ExerciseSolvingState.UNATTEMPTED,response.getBody() );
    }
}