package com.redocode.backend.RequstHandling.Handlers;

import com.redocode.backend.Excpetions.RequestHadndlingException;
import com.redocode.backend.Messages.UtilContainers.ChainNodeInfo;
import com.redocode.backend.Messages.UtilContainers.Range;
import com.redocode.backend.RequstHandling.Requests.ExerciseCreationRequest;
import com.redocode.backend.RequstHandling.Requests.RequestBase;
import com.redocode.backend.VmAcces.CodeRunners.Variables.Variables;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;
import java.util.regex.Pattern;

@Slf4j
public class ExerciseInfoValidation extends MessageRequestHandler{

    static String stringTitlePatttrnRegexMatch= "^[\\S ]{5,30}$";
    static Pattern TitlePattern=Pattern.compile(stringTitlePatttrnRegexMatch);


    static String stringDescPatttrnRegexMatch= "^[\\S ]{10,3000}$";
    static Pattern DescPattern=Pattern.compile(stringDescPatttrnRegexMatch);



    boolean validlateArrayRange(Range range,float minRange,float maxRange)
    {
        if(
                range.getMax()<range.getMin()
                || range.getMax()<minRange
                || range.getMin()<minRange
                || range.getMax()>maxRange
                || range.getMin()>maxRange
        ){
            return false;
        }
        else {
            return true;
        }
    }


    @Override
    String getChainNodeName() {
        return "Validating exercise information";
    }

    @Override
    RequestBase handle(RequestBase request) throws RequestHadndlingException {
        this.nodeUpdate(request,"Checking exercise information", ChainNodeInfo.CHAIN_NODE_STATUS.RUNNING);
        ExerciseCreationRequest exerciseCretionRequest= (ExerciseCreationRequest) request;

        if( !TitlePattern.matcher(exerciseCretionRequest.getTitle()).matches())
        {
            throw  new RequestHadndlingException("Exercise title: "+exerciseCretionRequest.getTitle()+" is inocrrect");
        }
        if( !DescPattern.matcher(exerciseCretionRequest.getDescription()).matches())
        {
            throw  new RequestHadndlingException("Exercise description is inocrrect");
        }
        if(exerciseCretionRequest.getAmountOfAutoTests()<=0 || exerciseCretionRequest.getAmountOfAutoTests()>10)
        {
            throw  new RequestHadndlingException("inccorrect Amount of auto test");
        }


        if(exerciseCretionRequest.getInputType().getId()>2 && exerciseCretionRequest.getXArrayRange()!=null) {
            if (!validlateArrayRange(exerciseCretionRequest.getXArrayRange(),1,20)) {
                throw new RequestHadndlingException("inccorrect X array range");
            }
        }
        if(exerciseCretionRequest.getInputType().getId()>5 && exerciseCretionRequest.getYArrayRange()!=null) {
            if (!validlateArrayRange(exerciseCretionRequest.getYArrayRange(),1,20)) {
                throw new RequestHadndlingException("inccorrect Y array range");
            }
        }


        switch (exerciseCretionRequest.getInputType().getId()%3){
            case 2:
                if(!validlateArrayRange(exerciseCretionRequest.getLengthRange(),Integer.MIN_VALUE,Integer.MAX_VALUE)){
                    throw new RequestHadndlingException("inccorrect int values range: "+exerciseCretionRequest.getLengthRange());
                }
                break;
            case 0:
                if(!validlateArrayRange(exerciseCretionRequest.getLengthRange(),Integer.MIN_VALUE,Integer.MAX_VALUE)){
                    throw new RequestHadndlingException("inccorrect float values range: "+exerciseCretionRequest.getLengthRange() );
                }
                break;
            case 1:
                if(!validlateArrayRange(exerciseCretionRequest.getLengthRange(),0,30)){
                    throw new RequestHadndlingException("inccorrect string values range: "+exerciseCretionRequest.getLengthRange());
                }
                break;
        }


        if(exerciseCretionRequest.getTimeForExecution()<10 || exerciseCretionRequest.getTimeForExecution()>5000)
        {
            throw new RequestHadndlingException("inoccret max execution limit");
        }

        if(exerciseCretionRequest.getTimeForTaskMin()<15 || exerciseCretionRequest.getTimeForTaskMin()> 60*10)
        {
            throw new RequestHadndlingException("inoccret time for task limit: "+ exerciseCretionRequest.getTimeForTaskMin());
        }
        if(exerciseCretionRequest.getSolutionCodes().size()==0)
        {
            throw new RequestHadndlingException("there should be at least one solution to chekc");
        }
        this.nodeUpdate(request,"Correct exercise setup", ChainNodeInfo.CHAIN_NODE_STATUS.SUCCESS);


        return request;
    }

    @Override
    void exceptionHandling(Exception exception) {

    }
}
