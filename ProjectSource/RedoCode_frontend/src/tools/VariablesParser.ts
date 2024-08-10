import VarType, { isTypeArray, isTypeDoubleArray, isTypeSingle, isTypeString, isTypeInt } from "@/types/VarType"

 namespace VariablesParser{



    export function isNumber(value?: string | number): boolean
    {
       return ((value != null) &&
               (value !== '') &&
               !isNaN(Number(value.toString())));
    }
    
    

    export function getParsingFormatExmaple(type: VarType){
        if (isTypeArray(type)) return '[x,x,x,x,...]'
        if (isTypeDoubleArray(type)) return '[[x,x,x,x,...],[x,x,x,x,...],...]'
        if (isTypeSingle(type)) return 'x'
      }

      export function validateType(variable: any, type: VarType){
        console.log('isTypeString(type) ' +  isTypeString(type))
        console.log('!isNumber(variable) ' + !isNumber(variable))
        // if (isTypeString(type) && (!isNumber(variable) )) {
        //   throw new Error('All varaibles should be ' + type)
        // }
    
        if (isTypeInt(type) && Math.floor(variable) !== Number(variable)) {
          throw new Error('All varaibles should be int not float')
        }
    
        console.log('type: ' + typeof variable)
      }
    


     export function validateData(jsonString: string, type: VarType): boolean{
        let value: any = null
        try {
          value =
          type == 'SINGLE_STRING' ? jsonString : JSON.parse(jsonString)
    
          console.log('valie: ' + value)
        } catch (erorr) {
          value = null
        }
        if (value === null) {
          throw new Error(
            'couldnt parse value to ' +
              type +
              ' plase format your data like this: ' +
              getParsingFormatExmaple(type)
          )
        }
        const isAnArray =  Array.isArray(value)
        const isAn2DArray = isAnArray ? Array.isArray(value[0]) : false
        if (isTypeSingle(type) && isAnArray) {
          throw new Error('Data should  be a signle value')
        }
        if (isTypeArray(type) && (!isAnArray || isAn2DArray)) {
          throw new Error('Data should  be a array')
        }
        if (isTypeDoubleArray(type) && !isAn2DArray) {
          throw new Error('Data should  be a 2darray')
        }
    
        if (
          (isAnArray && value.length == 0) ||
          (isAn2DArray && value[0].length == 0)
        )
          throw new Error('Array should have at least on value')
    
        if (
          isAn2DArray &&
          !value.every((elem: any[]) => elem.length === value[0].length)
        ) {
          throw new Error('Every row should have the same dimeniosns')
        }
    
        switch (type) {
          case 'SINGLE_INTEGER':
          case 'SINGLE_FLOAT':
          case 'SINGLE_STRING':
            validateType(value, type)
            break
          case 'ARRAY_OF_FLOATS':
          case 'ARRAY_OF_INTEGERS':
          case 'ARRAY_STRINGS':
            value.forEach((element: any[]) => {
              validateType(element, type)
            })
            break
          case 'DOUBLE_ARRAY_OF_FLOATS':
          case 'DOUBLE_ARRAY_OF_INTEGERS':
          case 'DOUBLE_ARRAY_OF_STRINGS':
            value.forEach((element: any[]) => {
              element.forEach((el: any) => {
                validateType(el, type)
              })
            })
        }
    
        console.log('validatea')
    
        return true
      }
    
}


export default VariablesParser;