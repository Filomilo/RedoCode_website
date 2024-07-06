<template>
  <div style="width: 100%; height: 100%; max-height: 100%; max-width: 100%; overflow: hidden">
    <InputText v-model="jsonInput" style="width: 100%" />

    <br />
    value: <br />
    {{ JSON.stringify(storedValue) }}
    <br />
    <div style="color: red">
      {{ validationofData }}
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, PropType, Ref, ref, watch } from 'vue'
import InputSetupInput from './InputSetupInput.vue'
import VarType from '@/types/VarType'
import VarSize from '@/types/VarSize'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
import test from 'node:test'
import { isArray, isNumber } from 'chart.js/helpers'
import { json } from 'agent-base'

const jsonInput: Ref<string> = ref('')
const value = computed(() => {
  try {
    return JSON.parse(jsonInput.value)
  } catch {
    return null
  }
})

const getFormatexample = (size: VarSize) => {
  switch (size) {
    case 'SINGLE_VALUE':
      return 'x'
    case 'ARRAY':
      return '[x,x,x,x,...]'
    case 'DOUBLE_ARRAY':
      return '[[x,x,x,x,...],[x,x,x,x,...],...]'
  }
}

const storedValue = computed(() => {
  return props.isInput
    ? CodeRunnerStore.manualTestBuffer[props.manualTestInputIndex].input
    : CodeRunnerStore.manualTestBuffer[props.manualTestInputIndex].expectedOutput
})

const validateType = (variable: any, type: VarType) => {
  if (type != 'string' && (!isNumber(variable) || typeof variable == 'string')) {
    throw new Error('All varaibles should be ' + type)
  }

  if (type === 'int' && Math.floor(variable) !== Number(variable)) {
    throw new Error('All varaibles should be int not float')
  }

  console.log('type: ' + typeof variable)
}

const validateData = (jsonString: string, type: VarType, size: VarSize): boolean => {
  const value = JSON.parse(jsonString)
  console.log('valie: ' + value)
  if (value === null) {
    throw new Error(
      'couldnt parse value to ' +
        type +
        ' plase format your data like this: ' +
        getFormatexample(size)
    )
  }
  const isAnArray = isArray(value)
  const isAn2DArray = isArray ? isArray(value[0]) : false
  if (size === 'SINGLE_VALUE' && isAnArray) {
    throw new Error('Data should  be a signle value')
  }
  if (size === 'ARRAY' && (!isAnArray || isAn2DArray)) {
    throw new Error('Data should  be a array')
  }
  if (size === 'DOUBLE_ARRAY' && !isAn2DArray) {
    throw new Error('Data should  be a 2darray')
  }

  if ((isAnArray && value.length == 0) || (isAn2DArray && value[0].length == 0))
    throw new Error('Array should have at least on value')

  if (isAn2DArray && !value.every((elem: any[]) => elem.length === value[0].length)) {
    throw new Error('Every row should have the same dimeniosns')
  }

  switch (size) {
    case 'SINGLE_VALUE':
      validateType(value, type)
      break
    case 'ARRAY':
      value.forEach((element: any[]) => {
        validateType(element, type)
      })
      break
    case 'DOUBLE_ARRAY':
      value.forEach((element: any[]) => {
        element.forEach((el: any) => {
          validateType(el, type)
        })
      })
  }

  console.log('validatea')

  return true
}

const validationofData = computed(() => {
  try {
    validateData(jsonInput.value, props.Type, props.Size)
  } catch (erorr: any) {
    return erorr.message
  }
  return ''
})
watch(value, (newVal, oldVal) => {
  try {
    if (validationofData.value === '') {
      if (props.isInput) {
        CodeRunnerStore.manualTestBuffer[props.manualTestInputIndex].input = newVal
      } else {
        CodeRunnerStore.manualTestBuffer[props.manualTestInputIndex].expectedOutput = newVal
      }
    }
  } catch (error) {
    console.error(error)
  }
})
const CodeRunnerStore = useCodeRunnerStore()

const props = defineProps({
  Type: { type: String as PropType<VarType>, required: true, default: 'int' },
  Size: { type: String as PropType<VarSize>, required: true, default: 'SINGLE_VALUE' },
  manualTestInputIndex: { type: Number, required: true },
  isInput: { type: Boolean, required: true }
})
</script>

<style>
.dimensionsPanel {
  display: flex;
  width: 100%;
}

.rowContainer {
  margin: 0.5rem;
  display: flex;
  flex-direction: row;
  width: fit-content;
}
.signleValContainer {
  margin: 0.1rem;
  width: fit-content;
}

.InputSizeContainer {
}

.InputSizeContainer * {
  margin-right: 5rem;
  margin-left: 1rem;
  width: 5rem;
}

.inputCotainer {
  width: 100%;
  height: 85%;
  overflow-y: scroll;
}
</style>
