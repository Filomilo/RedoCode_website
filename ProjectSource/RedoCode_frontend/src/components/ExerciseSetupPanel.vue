<template>
  <Splitter layout="vertical" style="height: 100%" class="exerciseSetupPanel">
    <SplitterPanel>
      <div class="setupPanel">
        <div class="panelSection">
          <MultiSelect
            v-model="codeRunnerStore.exerciseSetupParams.languages"
            display="chip"
            :options="langOptions"
            optionLabel="label"
            placeholder="programming languages"
            class="languageDropDown"
          />
        </div>
        <div class="panelSection SubPanelSection">
          <div class="panelVerticalSection">
            Ram
            <vue-slider v-model="codeRunnerStore.exerciseSetupParams.ram" :min="128" :max="2048" class="slider" />
            <InputNumber
              v-model="codeRunnerStore.exerciseSetupParams.ram"
              inputId="integeronly"
              suffix=" Mb"
              :min="128"
              :max="2048"
              class="numberInputRam"
            />
          </div>
          <div class="panelVerticalSection">
            Time for task
            <div style="display: flex; flex-direction: row">
              <InputNumber
              v-model="codeRunnerStore.exerciseSetupParams.timeForTaskH"
                inputId="integeronly"
                suffix=" H"
                :min="0"
                :max="12"
                class="numberInput"
              />
              <InputNumber
              v-model="codeRunnerStore.exerciseSetupParams.timeForTaskM"
                inputId="integeronly"
                suffix=" M"
                :min="0"
                :max="59"
                class="numberInput"
              />
            </div>
          </div>
          <div class="panelVerticalSection">
            Maximum execution time
            <div style="display: flex; flex-direction: row">
              <InputNumber
              v-model="codeRunnerStore.exerciseSetupParams.executionTimeMs"
                inputId="integeronly"
                suffix=" ms"
                :min="5"
                :max="5000"
                class="numberInput"
              />
            </div>
          </div>
        </div>

        <div class="panelSection SubPanelSection">
          <div class="horizontalSection">Input type  </div>
          <div class="horizontalSection">
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseSetupParams.inputType" 
              inputId="inputType1" 
              name="int" 
              value="int" 
              />
              <label>int</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton
              v-model="codeRunnerStore.exerciseSetupParams.inputType" 
              inputId="inputType2" 
              name="float" 
              value="float" 
              />
              <label>float</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseSetupParams.inputType" 
              inputId="inputType3" 
              name="string" 
              value="string" 
              />
              <label>string</label>
            </div>
          </div>
          <div class="horizontalSection">
            <div class="RadioButtonSection">
              <RadioButton
              v-model="codeRunnerStore.exerciseSetupParams.inputSize"
                inputId="inputSize1"
                name="single_value"
                value="single_value"
              />
              <label>single value</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseSetupParams.inputSize" 
              inputId="inputSize2" 
              name="array" 
              value="array" 
              />
              <label>array</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton
                v-model="codeRunnerStore.exerciseSetupParams.inputSize"
                inputId="inputSize3"
                name="2d_array"
                value="2d_array"
              />
              <label>2d array</label>
            </div>
          </div>
        </div>
        <div class="panelSection SubPanelSection">
          <div class="horizontalSection">Output type</div>
          <div class="horizontalSection">
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseSetupParams.outputType" 
              inputId="outputype1" 
              name="int" 
              value="int" 
              />
              <label>int</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseSetupParams.outputType" 
              inputId="outputype2" 
              name="float" 
              value="float" 
              />
              <label>float</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseSetupParams.outputType" 
              inputId="outputype3" 
              name="string" 
              value="string" 
              />
              <label>string</label>
            </div>
          </div>
          <div class="horizontalSection">
            <div class="RadioButtonSection">
              <RadioButton
                v-model="codeRunnerStore.exerciseSetupParams.outputSize"
                inputId="outputSize1"
                name="single_value"
                value="single_value"
              />
              <label>single value</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseSetupParams.outputSize" 
              inputId="outputSize2" 
              name="array" 
              value="array" 
              />
              <label>array</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton
                v-model="codeRunnerStore.exerciseSetupParams.outputSize"
                inputId="outputSize3"
                name="2d_array"
                value="2d_array"
              />
              <label>2d array</label>
            </div>
          </div>
        </div>
      </div>
    </SplitterPanel>
    <SplitterPanel>
      <div class="manualTestPanel">
        <h1>Manual tests</h1>
        <ManualTestPnael
          :inputType="codeRunnerStore.exerciseSetupParams.inputType"
          :outputype="codeRunnerStore.exerciseSetupParams.outputType"
          :inputSize="codeRunnerStore.exerciseSetupParams.inputSize"
          :outputSize="codeRunnerStore.exerciseSetupParams.outputSize"
        />
      </div>
    </SplitterPanel>
    <SplitterPanel>
      <div class="autoTestPanel">
        <h1>Automatic tests</h1>
        <div class="panelSection SubPanelSection">
          <div class="horizontalSection" style="flex-direction: column">
            <h5>Amount of automatic tests</h5>
            <InputNumber
              v-model="codeRunnerStore.exerciseSetupParams.amountOfAutoTests"
              :min="1"
              :max="10"
              class="smallNumberInput"
              style="width: 0.5rem"
            />
            <div class="sliderContaienr">
              <Slider v-model="codeRunnerStore.exerciseSetupParams.amountOfAutoTests" :min="1" :max="10" />
            </div>
          </div>
          <div
            class="horizontalSection"
            style="flex-direction: column"
            v-if="codeRunnerStore.exerciseSetupParams.inputSize === ('2d_array' as VarSize) || codeRunnerStore.exerciseSetupParams.inputSize === ('array' as VarSize)"
          >
            <h5>Array x length range</h5>
            <div>
              <InputNumber
                v-model="codeRunnerStore.exerciseSetupParams.xArrayRange.min"
                :min="1"
                :max="10"
                class="smallNumberInput"
              />
              <InputNumber
                v-model="codeRunnerStore.exerciseSetupParams.xArrayRange.max"
                :min="1"
                :max="10"
                class="smallNumberInput"
              />
            </div>
            <div class="sliderContaienr">
              <Slider
                :model-value="[codeRunnerStore.exerciseSetupParams.xArrayRange.min,codeRunnerStore.exerciseSetupParams.xArrayRange.max]"
                range
                :min="1"
                :max="10"
                :unstyled="false"
                @update:modelValue="(event) => onXChange(event)"
              />
            </div>
          </div>
          <div
            class="horizontalSection"
            style="flex-direction: column"
            v-if="codeRunnerStore.exerciseSetupParams.inputSize === ('2d_array' as VarSize)"
          >
            <h5>Array y length range</h5>
            <div>
              <InputNumber
              v-model="codeRunnerStore.exerciseSetupParams.yArrayRange.min"
                :min="1"
                :max="10"
                class="smallNumberInput"
              />
              <InputNumber
              v-model="codeRunnerStore.exerciseSetupParams.yArrayRange.max"
                :min="1"
                :max="10"
                class="smallNumberInput"
              />
            </div>
            <div class="sliderContaienr">
              <Slider
              :model-value="[codeRunnerStore.exerciseSetupParams.yArrayRange.min,codeRunnerStore.exerciseSetupParams.yArrayRange.max]"
              range
              :min="1"
              :max="10"
              :unstyled="false"
              @update:modelValue="(event) => onYChange(event)"
              />
            </div>
          </div>
        </div>
        <div class="panelSection SubPanelSection">
          <div v-if="codeRunnerStore.exerciseSetupParams.inputType === ('int'as VarType) " class="SubPanelSection">
            <div class="horizontalSection" style="flex-direction: column">
              <h5>int range</h5>
              <div>
                <InputNumber

                  v-model="codeRunnerStore.exerciseSetupParams.lengthRange.min"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([event, codeRunnerStore.exerciseSetupParams.lengthRange.max])"
                />
                <InputNumber
                  v-model="codeRunnerStore.exerciseSetupParams.lengthRange.max"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([codeRunnerStore.exerciseSetupParams.lengthRange.min, event])"
                />
              </div>
              <div class="sliderContaienr" style="width: 100%; height: 3rem; margin-top: 1rem">
                <Slider
                :model-value="[codeRunnerStore.exerciseSetupParams.lengthRange.min,codeRunnerStore.exerciseSetupParams.lengthRange.max]"
                  range
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :unstyled="false"
                  @change="(value:any)=>{codeRunnerStore.exerciseSetupParams.lengthRange.min=value[0];codeRunnerStore.exerciseSetupParams.lengthRange.max=value[1]}"
                  />
              </div>
            </div>
          </div>

          <div v-if="codeRunnerStore.exerciseSetupParams.inputType === ('float' as VarType)" class="SubPanelSection">
            <div class="horizontalSection" style="flex-direction: column">
              <h5>float range</h5>
              <div>
                <InputNumber
                  v-model="codeRunnerStore.exerciseSetupParams.lengthRange.min"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  @update:modelValue="(event) => onLengthChange([event, codeRunnerStore.exerciseSetupParams.lengthRange.max])"
                />
                <InputNumber
                  v-model="codeRunnerStore.exerciseSetupParams.lengthRange.max"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  @update:modelValue="(event) => onLengthChange([codeRunnerStore.exerciseSetupParams.lengthRange.min, event])"
                />
              </div>
              <div class="sliderContaienr" style="width: 100%; height: 3rem; margin-top: 1rem">
                <Slider
                  :model-value="[codeRunnerStore.exerciseSetupParams.lengthRange.min,codeRunnerStore.exerciseSetupParams.lengthRange.max]"
                  range
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :step="0.001"
                  :unstyled="false"
                  @change="onLengthChange"
                />
              </div>
            </div>
          </div>

          <div v-if="codeRunnerStore.exerciseSetupParams.inputType === 'string'" class="SubPanelSection">
            <div class="horizontalSection" style="flex-direction: column">
              <h5>string range</h5>
              <div style="display: flex; justify-content: center">
                <InputNumber
                  v-model="codeRunnerStore.exerciseSetupParams.lengthRange.min"
                  :min="0"
                  :max="30"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([event, codeRunnerStore.exerciseSetupParams.lengthRange.max])"
                />
                <InputNumber
                  v-model="codeRunnerStore.exerciseSetupParams.lengthRange.max"
                  :min="0"
                  :max="30"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([codeRunnerStore.exerciseSetupParams.lengthRange.min, event])"
                />
              </div>
              <div class="sliderContaienr" style="width: 100%; height: 3rem; margin-top: 1rem">
                <Slider
                  :model-value="[codeRunnerStore.exerciseSetupParams.lengthRange.min,codeRunnerStore.exerciseSetupParams.lengthRange.max]"
                  range
                  :min="0"
                  :max="30"
                  :step="1"
                  :unstyled="false"
                  @update:modelValue="(change) => onLengthChange(change)"
                />
              </div>
              <div class="SubPanelSection">
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseSetupParams.upperCaseInput" invalid binary />
                  <label> A, B, C, D, ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseSetupParams.lowerCaseInput" invalid binary />
                  <label> a, b, c, d, ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseSetupParams.numberInput" invalid binary />
                  <label> 1, 2, 3, 4 ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseSetupParams.specialCharacterInput" invalid binary />
                  <label> +, (, ), %, # ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseSetupParams.breakCharacterInupt" invalid binary />
                  <label> \n \t </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseSetupParams.spaceInupt" invalid binary />
                  <label> [_] </label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </SplitterPanel>
  </Splitter>
</template>

<script setup lang="ts">
import { onMounted,  ref } from 'vue'
import VueSlider from 'vue-slider-component'
import ManualTestPnael from './ManualTestPnael.vue'
import VarType from '@/types/VarType'
import VarSize from '@/types/VarSize'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'

const codeRunnerStore = useCodeRunnerStore()

const langOptions = [
  { label: 'Cpp', value: 'cpp' }, //TODO: replace with html call
  { label: 'Js', value: 'js' }
]



// const stringGenParam = ref([true, true, true, true, true, true])

const onXChange = (val: any) => {
  if (val[0] > val[1]) {
    val[0] = val[1]
  }
  codeRunnerStore.exerciseSetupParams.xArrayRange.min=val[0];
  codeRunnerStore.exerciseSetupParams.xArrayRange.max=val[1];
}

const onYChange = (val: any) => {
  if (val[0] > val[1]) {
    val[0] = val[1]
  }
  codeRunnerStore.exerciseSetupParams.yArrayRange.min=val[0];
  codeRunnerStore.exerciseSetupParams.yArrayRange.max=val[1];
}

const onLengthChange = (val: any) => {
  if (val[0] > val[1]) {
    val[0] = val[1]
  }
  codeRunnerStore.exerciseSetupParams.lengthRange.min=val[0];
  codeRunnerStore.exerciseSetupParams.lengthRange.max=val[1];
}

onMounted(() => {
  codeRunnerStore.setupCreatingExercise()
})
</script>

<style></style>
../types/VarType
