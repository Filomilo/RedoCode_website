import CodeRunnerRequestMessage from "@/types/CodeRunnerRequestMessage";
import StompApiSender from "../Stomp/StompApiSender";
import CodeRunnerType from "@/types/CodeRunnerTypes";
import { useCodeRunnerStore } from "@/stores/CodeRunnerStore";
import CodeRunnerConnection from "./CodeRunnerConnection";
import RawCodeToRunMessage from "@/types/ApiMesseages/RawCodeToRunMessage";

class CodeRunnerSender{
  
    private _stompApiSender: StompApiSender;
    private _CodeRnnerCOnnection: CodeRunnerConnection;
    constructor (stompApiSender: StompApiSender, codeRunnerConnection: CodeRunnerConnection)
    {
        this._stompApiSender=stompApiSender;
        this._CodeRnnerCOnnection=codeRunnerConnection;
    }

    

  public readonly requestCodeRunner = (codeRunnerName: CodeRunnerType) => {
    this._CodeRnnerCOnnection.setAwaiting();
    console.log('codeRunnerName: ' + JSON.stringify(codeRunnerName))
    const request: CodeRunnerRequestMessage = {
      CodeRunnerType: codeRunnerName,
    }
    console.log('requestCodeRunner: ' + JSON.stringify(request))
    this._stompApiSender.codeRunnerRequest(request);

  }
  runRawCode = (code: string) => {
    const message: RawCodeToRunMessage = {
      code: code
    }
    console.log('runRawCode: ' + JSON.stringify(message))
    this._stompApiSender.runRawCode(message)
  }


}

export default CodeRunnerSender;