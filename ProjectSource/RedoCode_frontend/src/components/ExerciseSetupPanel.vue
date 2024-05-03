<template>
  <Splitter layout="vertical" style="height: 100%" class="exerciseSetupPanel">
    <SplitterPanel>
      <div class="setupPanel">
        <div class="panelSection">
          <MultiSelect
            v-model="selectedLagn"
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
            <vue-slider v-model="sliderVal" :min="128" :max="2048" />
            <InputNumber
              v-model="sliderVal"
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
                v-model="hourVal"
                inputId="integeronly"
                suffix=" H"
                :min="0"
                :max="12"
                class="numberInput"
              />
              <InputNumber
                v-model="minuteVal"
                inputId="integeronly"
                suffix=" M"
                :min="0"
                :max="60"
                class="numberInput"
              />
            </div>
          </div>
        </div>

        <div class="panelSection SubPanelSection">
          <div class="horizontalSection">Input type</div>
          <div class="horizontalSection">
            <div class="RadioButtonSection">
              <RadioButton v-model="inputType" inputId="inputType1" name="int" value="int" />
              <label>int</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton v-model="inputType" inputId="inputType2" name="float" value="float" />
              <label>float</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton v-model="inputType" inputId="inputType3" name="string" value="string" />
              <label>string</label>
            </div>
          </div>
          <div class="horizontalSection">
            <div class="RadioButtonSection">
              <RadioButton
                v-model="inputSize"
                inputId="inputSize1"
                name="single_value"
                value="single_value"
              />
              <label>single value</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton v-model="inputSize" inputId="inputSize2" name="array" value="array" />
              <label>array</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton
                v-model="inputSize"
                inputId="inputSize3"
                name="2d_array"
                value="2d_array"
              />
              <label>2d array</label>
            </div>
          </div>
        </div>
        <div class="panelSection SubPanelSection">
          <div class="horizontalSection">Input type</div>
          <div class="horizontalSection">
            <div class="RadioButtonSection">
              <RadioButton v-model="outputype" inputId="outputype1" name="int" value="int" />
              <label>int</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton v-model="outputype" inputId="outputype2" name="float" value="float" />
              <label>float</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton v-model="outputype" inputId="outputype3" name="string" value="string" />
              <label>string</label>
            </div>
          </div>
          <div class="horizontalSection">
            <div class="RadioButtonSection">
              <RadioButton
                v-model="outputSize"
                inputId="outputSize1"
                name="single_value"
                value="single_value"
              />
              <label>single value</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton v-model="outputSize" inputId="outputSize2" name="array" value="array" />
              <label>array</label>
            </div>
            <div class="RadioButtonSection">
              <RadioButton
                v-model="outputSize"
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
          :inputType="inputType"
          :outputype="outputype"
          :inputSize="inputSize"
          :outputSize="outputSize"
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
              v-model="amountOfAutoTests"
              :min="1"
              :max="10"
              class="smallNumberInput"
              style="width: 0.5rem"
            />
            <div class="sliderContaienr">
              <Slider v-model="amountOfAutoTests" :min="1" :max="10" />
            </div>
          </div>
          <div
            class="horizontalSection"
            style="flex-direction: column"
            v-if="inputSize === '2d_array' || inputSize === 'array'"
          >
            <h5>Array x length range</h5>
            <div>
              <InputNumber
                v-model="xArrayInputRange[0]"
                :min="1"
                :max="10"
                class="smallNumberInput"
                @update:modelValue="(event) => onXChange([event, xArrayInputRange[1]])"
              />
              <InputNumber
                v-model="xArrayInputRange[1]"
                :min="1"
                :max="10"
                class="smallNumberInput"
                @update:modelValue="(event) => onXChange([xArrayInputRange[0], event])"
              />
            </div>
            <div class="sliderContaienr">
              <Slider
                v-model="xArrayInputRange"
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
            v-if="inputSize === '2d_array'"
          >
            <h5>Array y length range</h5>
            <div>
              <InputNumber
                v-model="yArrayInputRange[0]"
                :min="1"
                :max="10"
                class="smallNumberInput"
                @update:modelValue="(event) => onYChange([event, yArrayInputRange[1]])"
              />
              <InputNumber
                v-model="yArrayInputRange[1]"
                :min="1"
                :max="10"
                class="smallNumberInput"
                @update:modelValue="(event) => onYChange([yArrayInputRange[0], event])"
              />
            </div>
            <div class="sliderContaienr">
              <Slider
                v-model="yArrayInputRange"
                range
                :min="1"
                :max="10"
                :unstyled="false"
                @change="onYChange"
              />
            </div>
          </div>
        </div>
        <div class="panelSection SubPanelSection">
          <div v-if="inputType === 'int'" class="SubPanelSection">
            <div class="horizontalSection" style="flex-direction: column">
              <h5>int range</h5>
              <div>
                <InputNumber
                  v-model="lengthRange[0]"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([event, lengthRange[1]])"
                />
                <InputNumber
                  v-model="lengthRange[1]"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([lengthRange[0], event])"
                />
              </div>
              <div class="sliderContaienr" style="width: 100%; height: 3rem; margin-top: 1rem">
                <Slider
                  v-model="lengthRange"
                  range
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  :unstyled="false"
                  @change="onLengthChange"
                />
              </div>
            </div>
          </div>

          <div v-if="inputType === 'float'" class="SubPanelSection">
            <div class="horizontalSection" style="flex-direction: column">
              <h5>float range</h5>
              <div>
                <InputNumber
                  v-model="lengthRange[0]"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  @update:modelValue="(event) => onLengthChange([event, lengthRange[1]])"
                />
                <InputNumber
                  v-model="lengthRange[1]"
                  :min="-Math.pow(10, 10)"
                  :max="Math.pow(10, 10)"
                  @update:modelValue="(event) => onLengthChange([lengthRange[0], event])"
                />
              </div>
              <div class="sliderContaienr" style="width: 100%; height: 3rem; margin-top: 1rem">
                <Slider
                  v-model="lengthRange"
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

          <div v-if="inputType === 'string'" class="SubPanelSection">
            <div class="horizontalSection" style="flex-direction: column">
              <h5>string range</h5>
              <div style="display: flex; justify-content: center">
                <InputNumber
                  v-model="lengthRange[0]"
                  :min="0"
                  :max="30"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([event, lengthRange[1]])"
                />
                <InputNumber
                  v-model="lengthRange[1]"
                  :min="0"
                  :max="30"
                  :maxFractionDigits="0"
                  @update:modelValue="(event) => onLengthChange([lengthRange[0], event])"
                />
              </div>
              <div class="sliderContaienr" style="width: 100%; height: 3rem; margin-top: 1rem">
                <Slider
                  v-model="lengthRange"
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
                  <Checkbox v-model="stringGenParam[0]" invalid binary />
                  <label> A, B, C, D, ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="stringGenParam[1]" invalid binary />
                  <label> a, b, c, d, ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="stringGenParam[2]" invalid binary />
                  <label> 1, 2, 3, 4 ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="stringGenParam[3]" invalid binary />
                  <label> +, (, ), %, # ... </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="stringGenParam[4]" invalid binary />
                  <label> \n \t </label>
                </div>
                <div class="checkBoxContainer">
                  <Checkbox v-model="stringGenParam[5]" invalid binary />
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
import { onMounted, Ref, ref } from 'vue'
import VueSlider from 'vue-slider-component'
import 'vue-slider-component/theme/antd.css'
import InputSetupInput from './InputSetupInput.vue'
import ManualTestPnael from './ManualTestPnael.vue'
import VarType from '@/types/VarType'
import VarSize from '@/types/VarSize'
import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'

const codeRunnerStore = useCodeRunnerStore()

const langOptions = [
  { label: 'Cpp', value: 'cpp' },
  { label: 'Js', value: 'js' }
]
const selectedLagn = ref('')
const sliderVal = ref(128)
const minuteVal = ref(10)
const hourVal = ref(0)

const inputType: Ref<VarType> = ref('int')
const outputype = ref<VarType>('int')
const inputSize = ref<VarSize>('single_value')
const outputSize = ref<VarSize>('single_value')

const amountOfAutoTests = ref(1)
const xArrayInputRange = ref([1, 10])
const yArrayInputRange = ref([1, 10])

const lengthRange = ref([1, 10])
const stringGenParam = ref([true, true, true, true, true, true])

const onXChange = (val: any) => {
  // console.log("X vlaue chnaged: "+JSON.stringify(val));
  if (val[0] > val[1]) {
    val[0] = val[1]
  }
  xArrayInputRange.value = val
}

const onYChange = (val: any) => {
  // console.log("X vlaue chnaged: "+JSON.stringify(val));
  if (val[0] > val[1]) {
    val[0] = val[1]
  }
  yArrayInputRange.value = val
}

const onLengthChange = (val: any) => {
  // console.log("X vlaue chnaged: "+JSON.stringify(val));
  if (val[0] > val[1]) {
    val[0] = val[1]
  }
  lengthRange.value = val
}

onMounted(() => {
  codeRunnerStore.setupCreatingExercise()
})
</script>

<style></style>
../types/VarType
