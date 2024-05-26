<template>
  info: 
  {{ JSON.stringify(codeRunnerStore.exerciseCreatorController) }}
  <!-- {{ JSON.stringify(codeRunnerStore.exerciseCreatorController.languages.map((x)=> x.value)) }} -->
  <Splitter layout="vertical" style="height: 100%" class="exerciseSetupPanel">
    <SplitterPanel>
      <div class="setupPanel">
        <div class="panelSection">
          <MultiSelect
            v-model="codeRunnerStore.exerciseCreatorController.languages"
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
            <vue-slider v-model="codeRunnerStore.exerciseCreatorController.ram" :min="128" :max="2048" class="slider" />
            <InputNumber
              v-model="codeRunnerStore.exerciseCreatorController.ram"
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
              v-model="codeRunnerStore.exerciseCreatorController.timeForTaskH"
                inputId="integeronly"
                suffix=" H"
                :min="0"
                :max="12"
                class="numberInput"
              />
              <InputNumber
              v-model="codeRunnerStore.exerciseCreatorController.timeForTaskM"
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
              v-model="codeRunnerStore.exerciseCreatorController.executionTimeMs"
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
              v-model="codeRunnerStore.exerciseCreatorController.inputType" 
              inputId="inputType1" 
              name="int" 
              value="int" 
              />
              <label>int</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton
              v-model="codeRunnerStore.exerciseCreatorController.inputType" 
              inputId="inputType2" 
              name="float" 
              value="float" 
              />
              <label>float</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseCreatorController.inputType" 
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
              v-model="codeRunnerStore.exerciseCreatorController.inputSize"
                inputId="inputSize1"
                name="single_value"
                value="single_value"
              />
              <label>single value</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseCreatorController.inputSize" 
              inputId="inputSize2" 
              name="array" 
              value="array" 
              />
              <label>array</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton
                v-model="codeRunnerStore.exerciseCreatorController.inputSize"
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
              v-model="codeRunnerStore.exerciseCreatorController.outputType" 
              inputId="outputype1" 
              name="int" 
              value="int" 
              />
              <label>int</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseCreatorController.outputType" 
              inputId="outputype2" 
              name="float" 
              value="float" 
              />
              <label>float</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseCreatorController.outputType" 
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
                v-model="codeRunnerStore.exerciseCreatorController.outputSize"
                inputId="outputSize1"
                name="single_value"
                value="single_value"
              />
              <label>single value</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton 
              v-model="codeRunnerStore.exerciseCreatorController.outputSize" 
              inputId="outputSize2" 
              name="array" 
              value="array" 
              />
              <label>array</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton
                v-model="codeRunnerStore.exerciseCreatorController.outputSize"
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
          :inputType="codeRunnerStore.exerciseCreatorController.inputType"
          :outputype="codeRunnerStore.exerciseCreatorController.outputType"
          :inputSize="codeRunnerStore.exerciseCreatorController.inputSize"
          :outputSize="codeRunnerStore.exerciseCreatorController.outputSize"
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
              v-model="codeRunnerStore.exerciseCreatorController.amountOfAutoTests"
              :min="1"
              :max="10"
              class="smallNumberInput"
              style="width: 0.5rem"
            />
            <div class="sliderContaienr">
              <Slider v-model="codeRunnerStore.exerciseCreatorController.amountOfAutoTests" :min="1" :max="10" />
            </div>
          </div>
          <div
            class="horizontalSection"
            style="flex-direction: column"
            v-if="codeRunnerStore.exerciseCreatorController.inputSize === ('2d_array' as VarSize) || codeRunnerStore.exerciseCreatorController.inputSize === ('array' as VarSize)"
          >
            <h5>Array x length range</h5>
            <div>
              <InputNumber
                v-model="codeRunnerStore.exerciseCreatorController.xArrayRange.min"
                :min="1"
                :max="10"
                class="smallNumberInput"
              />
              <InputNumber
                v-model="codeRunnerStore.exerciseCreatorController.xArrayRange.max"
                :min="1"
                :max="10"
                class="smallNumberInput"
              />
            </div>
            <div class="sliderContaienr">
              <Slider
                :model-value="[codeRunnerStore.exerciseCreatorController.xArrayRange.min,codeRunnerStore.exerciseCreatorController.xArrayRange.max]"
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
            v-if="codeRunnerStore.exerciseCreatorController.inputSize === ('2d_array' as VarSize)"
          >
            <h5>Array y length range</h5>
            <div>
              <InputNumber
              v-model="codeRunnerStore.exerciseCreatorController.yArrayRange.min"
                :min="1"
                :max="10"
                class="smallNumberInput"
              />
              <InputNumber
              v-model="codeRunnerStore.exerciseCreatorController.yArrayRange.max"
                :min="1"
                :max="10"
                class="smallNumberInput"
              />
            </div>
            <div class="sliderContaienr">
              <Slider
              :model-value="[codeRunnerStore.exerciseCreatorController.yArrayRange.min,codeRunnerStore.exerciseCreatorController.yArrayRange.max]"
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
          <div v-if="codeRunnerStore.exerciseCreatorController.inputType === ('int'as VarType) " class="SubPanelSection">
            <div class="horizontalSection" style="flex-direction: column">
              <h5>int range</h5>
              <div>
                <InputNumber

                  v-model="codeRunnerStore.exerciseCreatorController.lengthRange.min"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([event, codeRunnerStore.exerciseCreatorController.lengthRange.max])"
                />
                <InputNumber
                  v-model="codeRunnerStore.exerciseCreatorController.lengthRange.max"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([codeRunnerStore.exerciseCreatorController.lengthRange.min, event])"
                />
              </div>
              <div class="sliderContaienr" style="width: 100%; height: 3rem; margin-top: 1rem">
                <Slider
                :model-value="[codeRunnerStore.exerciseCreatorController.lengthRange.min,codeRunnerStore.exerciseCreatorController.lengthRange.max]"
                  range
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :unstyled="false"
                  @change="(value:any)=>{codeRunnerStore.exerciseCreatorController.lengthRange.min=value[0];codeRunnerStore.exerciseCreatorController.lengthRange.max=value[1]}"
                  />
              </div>
            </div>
          </div>

          <div v-if="codeRunnerStore.exerciseCreatorController.inputType === ('float' as VarType)" class="SubPanelSection">
            <div class="horizontalSection" style="flex-direction: column">
              <h5>float range</h5>
              <div>
                <InputNumber
                  v-model="codeRunnerStore.exerciseCreatorController.lengthRange.min"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  @update:modelValue="(event) => onLengthChange([event, codeRunnerStore.exerciseCreatorController.lengthRange.max])"
                />
                <InputNumber
                  v-model="codeRunnerStore.exerciseCreatorController.lengthRange.max"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  @update:modelValue="(event) => onLengthChange([codeRunnerStore.exerciseCreatorController.lengthRange.min, event])"
                />
              </div>
              <div class="sliderContaienr" style="width: 100%; height: 3rem; margin-top: 1rem">
                <Slider
                  :model-value="[codeRunnerStore.exerciseCreatorController.lengthRange.min,codeRunnerStore.exerciseCreatorController.lengthRange.max]"
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

          <div v-if="codeRunnerStore.exerciseCreatorController.inputType === 'string'" class="SubPanelSection">
            <div class="horizontalSection" style="flex-direction: column">
              <h5>string range</h5>
              <div style="display: flex; justify-content: center">
                <InputNumber
                  v-model="codeRunnerStore.exerciseCreatorController.lengthRange.min"
                  :min="0"
                  :max="30"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([event, codeRunnerStore.exerciseCreatorController.lengthRange.max])"
                />
                <InputNumber
                  v-model="codeRunnerStore.exerciseCreatorController.lengthRange.max"
                  :min="0"
                  :max="30"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([codeRunnerStore.exerciseCreatorController.lengthRange.min, event])"
                />
              </div>
              <div class="sliderContaienr" style="width: 100%; height: 3rem; margin-top: 1rem">
                <Slider
                  :model-value="[codeRunnerStore.exerciseCreatorController.lengthRange.min,codeRunnerStore.exerciseCreatorController.lengthRange.max]"
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
                  <Checkbox v-model="codeRunnerStore.exerciseCreatorController.upperCaseInput" invalid binary />
                  <label> A, B, C, D, ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseCreatorController.lowerCaseInput" invalid binary />
                  <label> a, b, c, d, ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseCreatorController.numberInput" invalid binary />
                  <label> 1, 2, 3, 4 ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseCreatorController.specialCharacterInput" invalid binary />
                  <label> +, (, ), %, # ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseCreatorController.breakCharacterInupt" invalid binary />
                  <label> \n \t </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="codeRunnerStore.exerciseCreatorController.spaceInupt" invalid binary />
                  <label> [_] </label>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </SplitterPanel>
  </Splitter>
  test
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
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




const onXChange = (val: any) => {
  if (val[0] > val[1]) {
    val[0] = val[1]
  }
  codeRunnerStore.exerciseCreatorController.xArrayRange.min=val[0];
  codeRunnerStore.exerciseCreatorController.xArrayRange.max=val[1];
}

const onYChange = (val: any) => {
  if (val[0] > val[1]) {
    val[0] = val[1]
  }
  codeRunnerStore.exerciseCreatorController.yArrayRange.min=val[0];
  codeRunnerStore.exerciseCreatorController.yArrayRange.max=val[1];
}

const onLengthChange = (val: any) => {
  if (val[0] > val[1]) {
    val[0] = val[1]
  }
  codeRunnerStore.exerciseCreatorController.lengthRange.min=val[0];
  codeRunnerStore.exerciseCreatorController.lengthRange.max=val[1];
}

onMounted(() => {
  codeRunnerStore.exerciseCreatorController.resetParams()
})
</script>

<style></style>
../types/VarType
