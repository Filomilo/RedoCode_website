<template>
  <InputText v-if="props.type === 'string'" @update:modelValue="onValueUpdate" :value="modelval" />
  <InputNumber
    v-else
    :inputId="inputId"
    class="InputSizeContainer"
    :maxFractionDigits="props.type === 'float' ? 5 : 0"
    @update:modelValue="onValueUpdate"
    :modelValue="modelval"
  />
</template>

<script lang="ts" setup>
import VarSize from '@/types/VarSize'
import VarType from '@/types/VarType'
import { computed, PropType, ref } from 'vue'
import { getSize } from 'vue-slider-component/lib/utils'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
const codeRunnerStore = useCodeRunnerStore()

const props = defineProps({
  type: { type: String as PropType<VarType>, required: true, default: 'int' },
  size: { type: String as PropType<VarSize>, required: true, default: 'single_value' },
  manualTestInputIndex: { type: Number, required: true },
  xIndex: { type: Number, required: true },
  yIndex: { type: Number, required: true },
  isInput: { type: Boolean, required: true }
})
const inputId = computed(() => {
  return 'input_' + props.manualTestInputIndex + '_' + props.xIndex + '_' + props.yIndex
})
const modelval = computed(() => {
  // return props.manualTestInputIndex;
  const test = codeRunnerStore.exerciseData.tests[props.manualTestInputIndex]
  const values = props.isInput ? test.input : test.expectedOutput
  console.log('manualTestInputIndex, values : ' + props.isInput + ' _ ' + values)
  if (test === undefined) return 0
  console.log('values at ' + props.manualTestInputIndex + ' ::' + JSON.stringify(test))
  switch (props.size) {
    case 'single_value':
      return values
    case 'array':
      return values[xIndex]
    case '2d_array':
      return values[xIndex][yIndex]
  }
  return 0
})

const onValueUpdate = (val: any) => {
  console.log(
    'udate value: ' + props.manualTestInputIndex + ' is input: ' + props.isInput + ' to ' + val
  )
  if (props.isInput) {
    switch (props.size) {
      case 'single_value':
        codeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input = val
        break
      case 'array':
        codeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input[xIndex] = val
        break
      case '2d_array':
        codeRunnerStore.exerciseData.tests[props.manualTestInputIndex].input[xIndex][yIndex] = val
        break
    }
  } else {
    switch (props.size) {
      case 'single_value':
        codeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput = val
        break
      case 'array':
        codeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput[xIndex] = val
        break
      case '2d_array':
        codeRunnerStore.exerciseData.tests[props.manualTestInputIndex].expectedOutput[xIndex][
          yIndex
        ] = val
        break
    }
  }
}
</script>

<style>
.dimensionsPanel {
  display: flex;
}

.rowContainer {
  margin: 0.5rem;
  display: flex;
  flex-direction: row;
}
.signleValContainer {
  margin: 0.5rem;
}

.InputSizeContainer {
  margin-right: -5rem;
}

.InputSizeContainer * {
  width: 2rem;
  margin-right: -1rem;
}

.inputCotainer {
  overflow: scroll;
}
</style>
