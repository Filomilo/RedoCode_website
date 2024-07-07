type VarType =
  | 'SINGLE_INTEGER'
  | 'SINGLE_STRING'
  | 'SINGLE_FLOAT'
  | 'ARRAY_OF_INTEGERS'
  | 'ARRAY_STRINGS'
  | 'ARRAY_OF_FLOATS'
  | 'DOUBLE_ARRAY_OF_INTEGERS'
  | 'DOUBLE_ARRAY_OF_FLOATS'
  | 'DOUBLE_ARRAY_OF_STRINGS'
export default VarType

export const isTypeString = (type: VarType): boolean => {
  return type === 'SINGLE_STRING' || type === 'ARRAY_STRINGS' || type === 'DOUBLE_ARRAY_OF_STRINGS'
}

export const isTypeSFloat = (type: VarType): boolean => {
  return type === 'SINGLE_FLOAT' || type === 'ARRAY_OF_FLOATS' || type === 'DOUBLE_ARRAY_OF_FLOATS'
}

export const isTypeInt = (type: VarType): boolean => {
  return (
    type === 'SINGLE_INTEGER' || type === 'ARRAY_OF_INTEGERS' || type === 'DOUBLE_ARRAY_OF_INTEGERS'
  )
}

export const isTypeSingle = (type: VarType): boolean => {
  return type === 'SINGLE_INTEGER' || type === 'SINGLE_STRING' || type === 'SINGLE_FLOAT'
}
export const isTypeArray = (type: VarType): boolean => {
  return type === 'ARRAY_OF_INTEGERS' || type === 'ARRAY_STRINGS' || type === 'ARRAY_OF_FLOATS'
}

export const isTypeDoubleArray = (type: VarType): boolean => {
  return (
    type === 'DOUBLE_ARRAY_OF_INTEGERS' ||
    type === 'DOUBLE_ARRAY_OF_FLOATS' ||
    type === 'DOUBLE_ARRAY_OF_STRINGS'
  )
}

export const setTypeToSingle = (type: VarType): VarType => {
  switch (type) {
    case 'SINGLE_INTEGER':
    case 'ARRAY_OF_INTEGERS':
    case 'DOUBLE_ARRAY_OF_INTEGERS':
      return 'SINGLE_INTEGER'

    case 'SINGLE_STRING':
    case 'ARRAY_STRINGS':
    case 'DOUBLE_ARRAY_OF_STRINGS':
      return 'SINGLE_STRING'

    case 'SINGLE_FLOAT':
    case 'ARRAY_OF_FLOATS':
    case 'DOUBLE_ARRAY_OF_FLOATS':
      return 'SINGLE_FLOAT'
  }
}

export const setTypeToArray = (type: VarType): VarType => {
  switch (type) {
    case 'SINGLE_INTEGER':
    case 'ARRAY_OF_INTEGERS':
    case 'DOUBLE_ARRAY_OF_INTEGERS':
      return 'ARRAY_OF_INTEGERS'

    case 'SINGLE_STRING':
    case 'ARRAY_STRINGS':
    case 'DOUBLE_ARRAY_OF_STRINGS':
      return 'ARRAY_STRINGS'

    case 'SINGLE_FLOAT':
    case 'ARRAY_OF_FLOATS':
    case 'DOUBLE_ARRAY_OF_FLOATS':
      return 'ARRAY_OF_FLOATS'
  }
}

export const setTypeToDoubleArray = (type: VarType): VarType => {
  switch (type) {
    case 'SINGLE_INTEGER':
    case 'ARRAY_OF_INTEGERS':
    case 'DOUBLE_ARRAY_OF_INTEGERS':
      return 'DOUBLE_ARRAY_OF_INTEGERS'

    case 'SINGLE_STRING':
    case 'ARRAY_STRINGS':
    case 'DOUBLE_ARRAY_OF_STRINGS':
      return 'DOUBLE_ARRAY_OF_STRINGS'

    case 'SINGLE_FLOAT':
    case 'ARRAY_OF_FLOATS':
    case 'DOUBLE_ARRAY_OF_FLOATS':
      return 'DOUBLE_ARRAY_OF_FLOATS'
  }
}

export const setTypeToInt = (type: VarType): VarType => {
  switch (type) {
    case 'SINGLE_INTEGER':
    case 'SINGLE_FLOAT':
    case 'SINGLE_STRING':
      return 'SINGLE_INTEGER'

    case 'ARRAY_OF_INTEGERS':
    case 'ARRAY_STRINGS':
    case 'ARRAY_OF_FLOATS':
      return 'ARRAY_OF_INTEGERS'

    case 'DOUBLE_ARRAY_OF_INTEGERS':
    case 'DOUBLE_ARRAY_OF_STRINGS':
    case 'DOUBLE_ARRAY_OF_FLOATS':
      return 'DOUBLE_ARRAY_OF_INTEGERS'
  }
}

export const setTypeToFloat = (type: VarType): VarType => {
  switch (type) {
    case 'SINGLE_INTEGER':
    case 'SINGLE_FLOAT':
    case 'SINGLE_STRING':
      return 'SINGLE_FLOAT'

    case 'ARRAY_OF_INTEGERS':
    case 'ARRAY_STRINGS':
    case 'ARRAY_OF_FLOATS':
      return 'ARRAY_OF_FLOATS'

    case 'DOUBLE_ARRAY_OF_INTEGERS':
    case 'DOUBLE_ARRAY_OF_STRINGS':
    case 'DOUBLE_ARRAY_OF_FLOATS':
      return 'DOUBLE_ARRAY_OF_FLOATS'
  }
}

export const setTypeToString = (type: VarType): VarType => {
  switch (type) {
    case 'SINGLE_INTEGER':
    case 'SINGLE_FLOAT':
    case 'SINGLE_STRING':
      return 'SINGLE_STRING'

    case 'ARRAY_OF_INTEGERS':
    case 'ARRAY_STRINGS':
    case 'ARRAY_OF_FLOATS':
      return 'ARRAY_STRINGS'

    case 'DOUBLE_ARRAY_OF_INTEGERS':
    case 'DOUBLE_ARRAY_OF_STRINGS':
    case 'DOUBLE_ARRAY_OF_FLOATS':
      return 'DOUBLE_ARRAY_OF_STRINGS'
  }
}
