import CoderunnerState from '@/types/CodeRunnerState';
import CodeRunnerStatus from '@/types/CodeRunnerStatus';
import CodeRunnerType from '@/types/CodeRunnerTypes';
import axios from 'axios';
namespace EnpointAcces{

    export async function getCodeRunnerState(token:string): Promise< CoderunnerState> {
        try {
            const response = await axios.post('/public/coderunner/state', { token: token });
            console.log('updateCodeRunner Response:', response.data);
            return response.data

        } catch (error) {
            console.error('updateCodeRunner Error:', error);
            return {
                codeRunnerType: CodeRunnerType.UNIDENTIFIED,
                state: CodeRunnerStatus.NONE
            }
        }
    }
    
}



export default EnpointAcces;