<template>
  <div style="width: 100%; height: 100%; max-height: 100%; max-width: 100%; overflow: hidden">
    <!-- <div class="dimensionsPanel">
      <div v-if="props.Size === '2d_array' || props.Size === 'array'">
        <label>X size: </label>
        <InputNumber
          :model-value="xSize"
          @update:model-value="onXDimChange"
          inputId="integeronly"
          class="InputSizeContainer"
          :min="0"
          :max="20"
        />
      </div>
      <div v-if="props.Size === '2d_array'">
        <label>Y size: </label>
        <InputNumber
          v-model="ySize"
          inputId="integeronly"
          class="InputSizeContainer"
          :min="1"
          :max="20"
        />
      </div>
    </div>

    <div class="inputCotainer">
      <div v-for="(row, indexRow) in props.isInput?CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input:CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput" class="rowContainer" v-bind:key="indexRow">
        <div v-for="(val, indexColumn) in row" class="signleValContainer" v-bind:key="indexColumn">
          <InputSetupInput
            :type="props.Type"
            :manualTestInputIndex="props.manualTestInputIndex"
            :xIndex="indexColumn"
            :yIndex="indexRow"
            :isInput="props.isInput"
          />
        </div>
      </div>
    </div> -->

    <!-- <InputNumber  inputId="integeronly" /> -->

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
    case 'single_value':
      return 'x'
    case 'array':
      return '[x,x,x,x,...]'
    case '2d_array':
      return '[[x,x,x,x,...],[x,x,x,x,...],...]'
  }
}

const storedValue = computed(() => {
  return props.isInput
    ? CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input
    : CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput
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
  if (size === 'single_value' && isAnArray) {
    throw new Error('Data should  be a signle value')
  }
  if (size === 'array' && (!isAnArray || isAn2DArray)) {
    throw new Error('Data should  be a array')
  }
  if (size === '2d_array' && !isAn2DArray) {
    throw new Error('Data should  be a 2darray')
  }

  if ((isAnArray && value.length == 0) || (isAn2DArray && value[0].length == 0))
    throw new Error('Array should have at least on value')

  if (isAn2DArray && !value.every((elem: any[]) => elem.length === value[0].length)) {
    throw new Error('Every row should have the same dimeniosns')
  }

  switch (size) {
    case 'single_value':
      validateType(value, type)
      break
    case 'array':
      value.forEach((element: any[]) => {
        validateType(element, type)
      })
      break
    case '2d_array':
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
watch(validationofData, (newVal, oldVal) => {
  try {
    if (newVal === '') {
      if (props.isInput) {
        CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input = value.value
      } else {
        CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput = value.value
      }
    }
  } catch (error) {
    console.error(error)
  }
})
const CodeRunnerStore = useCodeRunnerStore()
// const dimension = ref(2)
// const xSize = computed(() => {
//   return CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input.length
// })
// const ySize = ref(1)

const props = defineProps({
  Type: { type: String as PropType<VarType>, required: true, default: 'int' },
  Size: { type: String as PropType<VarSize>, required: true, default: 'single_value' },
  manualTestInputIndex: { type: Number, required: true },
  isInput: { type: Boolean, required: true }
})

// watch(xSize, (newValue: number, oldValue: number) => {
//   onXDimChange(newValue)
// })
// watch(ySize, (newValue: number, oldValue: number) => {
//   onYDimChange(newValue)
// })
// const onXDimChange = (value: number) => {
//   let data = props.isInput
//     ? CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input
//     : CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput

//   if (data.length >= value) {
//     data = data.slice(0, value)
//   } else {
//     while (data.length < value) {
//       data.push([0])
//     }
//   }

//   if (props.isInput) {
//     CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input = data
//   } else {
//     CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput = data
//   }
// }
// const onYDimChange = (value: number) => {
//   let data = props.isInput
//     ? CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input
//     : CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput
//   console.log('change y: ' + value)
//   if (data[0].length < value) {
//     let toIncrease = value - data.length
//     console.log('toIncrease: ' + toIncrease)
//     for (let i = 0; i < toIncrease; i++) {
//       console.log('data.value[0].length: ' + data[0].length)
//       data.push([])
//       for (let j = 0; j < xSize.value; j++) {
//         console.log('j: ' + j)
//         data[data.length - 1].push(0)
//       }
//     }
//   } else {
//     if (data[0].length > value) {
//       let toDecrease = data.length - value
//       // console.log("toIncrease: "+ toIncrease)
//       for (let i = 0; i < toDecrease; i++) {
//         console.log('data.value[0].length: ' + data[0].length)
//         data.pop()
//       }
//     }
//   }
//   if (props.isInput) {
//     CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input = data
//   } else {
//     CodeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput = data
//   }
// }
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
