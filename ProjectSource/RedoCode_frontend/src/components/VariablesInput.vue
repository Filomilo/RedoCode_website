<template>
  <div
    style="
      width: 100%;
      height: 100%;
      max-height: 100%;
      max-width: 100%;
      overflow: hidden;
    "
  >
    <InputText
      v-model="jsonInput"
      style="width: 100%"
      :id="
        'test-input-' +
        props.manualTestInputIndex +
        '-' +
        (props.isInput ? 'input' : 'output')
      "
    />

    <br />
    value: <br />
    {{ JSON.stringify(storedValue) }}
    <br />
    <div style="color: red; font-size: 0.9rem">
      {{ validationError }}
    </div>
  </div>
</template>

<script lang="ts" setup>
//#region imports
  import { computed, PropType, Ref, ref, watch } from 'vue'
  import InputSetupInput from './InputSetupInput.vue'
  import VarType, {
    isTypeArray,
    isTypeDoubleArray,
    isTypeInt,
    isTypeSingle,
    isTypeString,
  } from '@/types/VarType'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
  import test from 'node:test'
  import { isArray, isNumber } from 'chart.js/helpers'
  import { json } from 'agent-base'
  import VariablesParser from '@/tools/VariablesParser'
//#endregion 

  const jsonInput: Ref<string> = ref('')
  const value = computed(() => {
    try {
      console.log('jsonInput.value: ' + jsonInput.value)

      if (props.Type == 'SINGLE_STRING') {
        return jsonInput.value
      }
      return JSON.parse(jsonInput.value)
    } catch {
      return null
    }
  })



  const storedValue = computed(() => {
    return props.isInput
      ? CodeRunnerStore.exerciseCreatorController.getSingleRowOfManualTests[props.manualTestInputIndex].input
      : CodeRunnerStore.exerciseCreatorController.getSingleRowOfManualTests[props.manualTestInputIndex]
          .expectedOutput
  })



  const validationError = computed(() => {
    try {
      VariablesParser.validateData(jsonInput.value, props.Type)
    } catch (erorr: any) {
      return erorr.message
    }
    return ''
  })
  watch(value, (newVal, oldVal) => {
    try {
      console.log('validationofData.value: ' + validationError.value)
      if (validationError.value === '') {
        CodeRunnerStore.exerciseCreatorController.setTestValue(props.isInput,props.manualTestInputIndex,newVal)


      }
    } catch (error) {
      console.error(error)
    }
  })
  const CodeRunnerStore = useCodeRunnerStore()

  const props = defineProps({
    Type: {
      type: String as PropType<VarType>,
      required: true,
      default: 'SINGLE_INTEGER',
    },
    manualTestInputIndex: { type: Number, required: true },
    isInput: { type: Boolean, required: true },
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
@/tools/VariablesParser