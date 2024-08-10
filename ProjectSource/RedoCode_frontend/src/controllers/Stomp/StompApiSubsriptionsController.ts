import CoderunnerState from "@/types/CodeRunnerState";
import StompApiConnection from "./StompApiConnection";
import ProgramResultsMessage from "@/types/ApiMesseages/ProgramResultsMessage";
import ExecutionResponseBase from "@/types/ApiMesseages/ExecutionResponses/ExecutionResponseBase";
import ExecutionChainScheme from "@/types/ApiMesseages/ExecutionResponses/ExecutionChainScheme";
import ExecutionResponseStatusUpdate from "@/types/ApiMesseages/ExecutionResponses/ExecutionResponseStatusUpdate";
import StompApiSubscription from "./StompApiSubscription";

class StompApiSubsciptionContorller{

    private _stompApiConnection:StompApiConnection


    constructor(stompApiConneciton: StompApiConnection)
    {
        this._stompApiConnection=stompApiConneciton;
        this.initSubscriptions();
    }


    private initSubscriptions()
    {
        console.log("this._vmStatusSubscription intlize")
        this._vmStatusSubscription = this._stompApiConnection.subscribe(
            '/user/public/topic/codeRunnerState',this._VmStatusCallBack.bind(this)
          );
          console.log("this._CodeResultsSubscription intlize")

    this._CodeResultsSubscription=this._stompApiConnection.subscribe(
      '/user/public/topic/codeRunnerResults',
      this._CodeResultsCallBack.bind(this)
        );

        this._ExecutionChainSubscription=this._stompApiConnection.subscribe(
            '/user/public/topic/ExecutionResponses',
            this._ExecutionChainCallBack.bind(this)
              );

    }





    //#region Subscription objects 
    private _vmStatusSubscription!:StompApiSubscription;
    private _CodeResultsSubscription!:StompApiSubscription;
    private _ExecutionChainSubscription!: StompApiSubscription;
    //#endregion

    //#region Subscription CallBacks

    private _VmStatusCallBack(message: object)
    {
        const state: CoderunnerState=message as CoderunnerState;
       
        const eventHandlers: any[]=this._VmStatusSubscriptions as (any[]);
        console.log('codeRunnerState recived  ' + JSON.stringify(state))
        console.log('typeof this.eventHandlers  ' + (typeof eventHandlers))
        console.log('typeof this.eventHandlers  ' + eventHandlers)
        console.log('length  ' + (JSON.stringify(eventHandlers.length)))

        eventHandlers.forEach((element: { (arg: CoderunnerState): void; }) => {
            console.log("|Test")
            element(state);
        });
    }
    private _CodeResultsCallBack(message: object)
    {
        const results: ProgramResultsMessage = message as ProgramResultsMessage;       
        console.log('_CodeResultsCallBack recived  ' + JSON.stringify(results))
        this._CodeResultsSubscriptions.forEach((element: ((results: ProgramResultsMessage)=> void)) => {
            element(results);
        });
    }
    private _ExecutionChainCallBack(message: object)
    {
        const responseBase: ExecutionResponseBase = message as ExecutionResponseBase;    
        
        if (responseBase.messageType === 'CHAIN_SCHEME')
            this._ExecutionChainSchemeSubscriptions.forEach((element:((_update: ExecutionChainScheme)=> void) ) => {
                element(responseBase as ExecutionChainScheme)}
            )
          else 
          this._ExecutionResponseStatusUpdateSubscriptions.forEach((element:((_update: ExecutionResponseStatusUpdate)=> void)) => {
            element(responseBase as ExecutionResponseStatusUpdate)
        });
    }
    //#endregion

    //#region Subcription Events


    //#region vmStatus
    private _VmStatusSubscriptions: { (arg: CoderunnerState): void; }[]=[]

    public addVmStatusSubscription(method: { (arg: CoderunnerState): void; }):void{
        console.log('_VmStatusSubscriptions  ' + this._VmStatusSubscriptions)
        console.log('method  ' + method)
        console.log('method  ' + (typeof method))

       this._VmStatusSubscriptions.push(method)
       console.log('_VmStatusSubscriptions  ' + this._VmStatusSubscriptions)

    }
    public removeVmStatusSubscription(method:{ (arg: CoderunnerState): void; }):void{
        console.log("Test")
        const indexToRemove = this._VmStatusSubscriptions.findIndex(item => item === method);
        if (indexToRemove !== -1) {
            this._VmStatusSubscriptions.splice(indexToRemove, 1);
        }
     }

     public clearVmStatusSubscriptions(){
        console.log("test")
        this._VmStatusSubscriptions=[];
     }
     //#endregion

    //#region codeResults
    private _CodeResultsSubscriptions: { (arg: ProgramResultsMessage): void; } []=[]

    public addCodeResultsSubscription(method: { (arg: ProgramResultsMessage): void; }):void{
       this._CodeResultsSubscriptions.push(method)
    }
    public removeCodeResultsSubscription(method: { (arg: ProgramResultsMessage): void; }):void{
        const indexToRemove = this._CodeResultsSubscriptions.findIndex(item => item === method);
        if (indexToRemove !== -1) {
            this._CodeResultsSubscriptions.splice(indexToRemove, 1);
        }
     }

     public clearCodeResultsSubscriptions(){
        this._CodeResultsSubscriptions=[];
     }
     //#endregion


 //#region execution chain scheme
 private _ExecutionChainSchemeSubscriptions: ((chain: ExecutionChainScheme)=> void)[]=[]

 public addExecutionChainSchemeSubscription(method: ((chain: ExecutionChainScheme)=> void)):void{
    this._ExecutionChainSchemeSubscriptions.push(method)
 }
 public removeExecutionChainSchemeSubscription(method: ((chain: ExecutionChainScheme)=> void)):void{
     const indexToRemove = this._ExecutionChainSchemeSubscriptions.findIndex(item => item === method);
     if (indexToRemove !== -1) {
         this._ExecutionChainSchemeSubscriptions.splice(indexToRemove, 1);
     }
  }

  public clearExecutionChainSchemeSubscriptions(){
     this._CodeResultsSubscriptions=[];
  }
  //#endregion


 //#region execution status update
 private _ExecutionResponseStatusUpdateSubscriptions: ((_update: ExecutionResponseStatusUpdate)=> void)[]=[]

 public addEExecutionResponseStatusUpdateSubscription(method: ((_update: ExecutionResponseStatusUpdate)=> void)):void{
    this._ExecutionResponseStatusUpdateSubscriptions.push(method)
 }
 public removeExecutionResponseStatusUpdateSubscription(method: ((_update: ExecutionResponseStatusUpdate)=> void)):void{
     const indexToRemove = this._ExecutionResponseStatusUpdateSubscriptions.findIndex(item => item === method);
     if (indexToRemove !== -1) {
         this._ExecutionResponseStatusUpdateSubscriptions.splice(indexToRemove, 1);
     }
  }

  public clearExecutionResponseStatusUpdateSubscriptions(){
     this._CodeResultsSubscriptions=[];
  }
  //#endregion






    //#endregion
    



}

export default StompApiSubsciptionContorller;