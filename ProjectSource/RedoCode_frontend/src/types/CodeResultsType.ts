export default interface CodeResultType{
    Console_output: string,
    Error_output: string,
    Solution_type: string,
    correct_solution: number| number[]| number[][]| string | string[]| string [][] | null,
    achived_solution: number| number[]| number[][]| string | string[]| string [][] | null,
    error: string|null
}