import ChainNodeStatus from "@/types/ApiMesseages/ExecutionResponses/ChainNodeStatus";
import ExecutionChainScheme from "@/types/ApiMesseages/ExecutionResponses/ExecutionChainScheme";
import StompApiSubsciptionContorller from "./Stomp/StompApiSubsriptionsController";
import ExecutionResponseStatusUpdate from "@/types/ApiMesseages/ExecutionResponses/ExecutionResponseStatusUpdate";

export interface ExecutionChainControls{
  _executionChain: ChainNodeStatus[]
  _shouldBeVisible: boolean
  _closeReady: boolean
}

class ExecutionChainController implements ExecutionChainControls{


    public _executionChain: ChainNodeStatus[]=[]

    public _shouldBeVisible: boolean=false;
    public _closeReady: boolean=false;

    public onVisibiltyUpdate:(state: ExecutionChainController)=>void=(state: ExecutionChainController)=>{}

    get executionChain () :ChainNodeStatus[]{
        return this._executionChain;
    }

    get shouldBeVisible () :boolean{
        return this._shouldBeVisible;
    }
    get closeReady () :boolean{
        return this._closeReady;
    }
    constructor(stompApiSubsciptionContorller: StompApiSubsciptionContorller)
    {
        stompApiSubsciptionContorller.addExecutionChainSchemeSubscription(
            this.loadChainScheme.bind(this)
          )
          stompApiSubsciptionContorller.addEExecutionResponseStatusUpdateSubscription(
            this.updateStatus.bind(this)
          )
        
    }


    private reset()
    {
        this._executionChain=[];
        this._shouldBeVisible = false
        this._closeReady = false
        this.onVisibiltyUpdate(this);
    }

    public loadChainScheme = (scheme: ExecutionChainScheme) => {
        console.log('ExecutionResponses  loadChainScheme:' + JSON.stringify(scheme))
        this._executionChain = scheme.levels
        this._shouldBeVisible = true
        this.onVisibiltyUpdate(this);
    }

    private  waitForScheme = (expectedLvl: number, timeout: number) => {
        console.log('CHAIN waiting for' + expectedLvl)
        return new Promise<void>((resolve, reject) => {
          resolve();
          // const interval = 50
          // const checkArraySize = () => {
          //   if (this.executionChain.length > expectedLvl) {
          //     resolve()
          //   } else if (timeout <= 0) {
          //     reject(new Error('Error receiving messages'))
          //   } else {
          //     timeout -= interval
          //     setTimeout(checkArraySize, interval)
          //   }
          // }
          // checkArraySize()
        })
      }


      public updateStatus = async (update: ExecutionResponseStatusUpdate) => {
        console.log('CHAIN  udpate:' + JSON.stringify(update))
        this.waitForScheme(update.stepUpdate, 10000)
          .then(() => {
            console.log("_______________UPDATE: "+ JSON.stringify( update))
            this. executionChain[update.stepUpdate].processingMessage =
              update.message
            this.executionChain[update.stepUpdate].status = update.lvlStatus
            console.log("UPdate check if can close: "+JSON.stringify(update))
            if (
              update.lvlStatus === 'FAILED' ||
              (update.stepUpdate === this.executionChain.length - 1 &&
                update.lvlStatus === 'SUCCESS')
            ) {
                this._closeReady = true
                
            }
            this.onVisibiltyUpdate(this);
          })
          .catch(() => {
            this._closeReady = true
            this.onVisibiltyUpdate(this);
          })
      }


      public close() {
       this.reset();
      }
    

}

export default ExecutionChainController;