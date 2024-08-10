import CodeRunnerRequestMessage from "@/types/CodeRunnerRequestMessage";
import StompApiSender from "../Stomp/StompApiSender";
import CodeRunnerType from "@/types/CodeRunnerTypes";

class CodeRunnerSender{
    private _stompApiSender: StompApiSender;

    constructor (stompApiSender: StompApiSender)
    {
        this._stompApiSender=stompApiSender;
    }

    

  public readonly requestCodeRunner = (codeRunnerName: CodeRunnerType) => {
    // this.codeRunnerState.value.state = CodeRunnerStatus.AWAITING
    console.log('codeRunnerName: ' + JSON.stringify(codeRunnerName))
    const request: CodeRunnerRequestMessage = {
      CodeRunnerType: codeRunnerName,
    }
    console.log('requestCodeRunner: ' + JSON.stringify(request))
    this._stompApiSender.codeRunnerRequest(request);
  }



}

export default CodeRunnerSender;