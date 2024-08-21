import ExcerciseDataMessage from "@/types/ApiMesseages/ExcerciseDataMessage"
import CodeRunnerControllerBase from "./CodeRunnerControllerBase"
import ExerciseTest from "@/types/ExcericseTest"
import VarType from "@/types/VarType"
import generateStartingFunction from "@/tools/StartingFunctionGenerator"
import CodeRunnerType from "@/types/CodeRunnerTypes"
import ProgramResult from "@/types/ProgramResults"
import IExerciseDescriptionI from "@/types/IExerciseDescriptionI"

export default class  ExerciseSolverController extends CodeRunnerControllerBase implements IExerciseDescriptionI {
    public id: number=0
    public title!: string
    public description!: string
    public solution!: string
    public manualTests!: ExerciseTest[]
    public autoTests!: ExerciseTest[]
    

    public inputType!: VarType;
    public outputType!: VarType;

    constructor()
    {
        super();

    }

    public loadInitialData(id:number,data: ExcerciseDataMessage)
    {
        console.log("Loadingi inital ExerciseSolverController "+JSON.stringify(data));
        this.title=data.title;
        this.description=data.description;
        this.manualTests=data.tests;
        this._languages=data.availbleCodeRunners;
        this.inputType=data.inputType as VarType;
        this.outputType=data.outputType as VarType;
        this.id=id;2
    }

    public startFunction(type: CodeRunnerType): string {
        if(this.solution=="")
        this.solution=generateStartingFunction(type,this.inputType,this.outputType);
        return  generateStartingFunction(type,this.inputType,this.outputType);
    }

    
    reset(): void {
        (this.title = ''),
        (this.description = ''),
        (this.solution = ''),
        (this.manualTests = []),
        (this.autoTests = [])
    }

    updateTests(results: ProgramResult[])
    {
        console.log("ExerciseSolverController update ts: "+JSON.stringify(results))
        const procesedResults=this.processCodeResultLoad(results,
            {
                tests: this.manualTests,
                autoTests:this.autoTests
            })
            this.manualTests=procesedResults.tests;
            this.autoTests=procesedResults.autoTests;

        
    }

}