import CoderunnerState from '@/types/CodeRunnerState';
import CodeRunnerStatus from '@/types/CodeRunnerStatus';
import CodeRunnerType from '@/types/CodeRunnerTypes';
import axios from 'axios';
namespace EnpointAcces{

    export async function getCodeRunnerState(token:string): Promise< CoderunnerState> {
        try {
            if(token==="")
            throw "token empty"
        console.log("token: "+ token)
            const response = await axios.post('/public/coderunner/state', token);
            console.log('updateCodeRunner Response:',  response);
            if(response===undefined|| response.data==="" ||  response.headers['Content-Length']==0)
                throw "no status codeRunenr"
            return response.data

        } catch (error) {
            console.log('updateCodeRunner Error:', error);
            return {
                codeRunnerType: CodeRunnerType.UNIDENTIFIED,
                state: CodeRunnerStatus.NONE
            }
        }
    }
    
}



export default EnpointAcces;