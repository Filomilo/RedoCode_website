<template>
  <div class="mainContainer">
    <div class="errorContainer">
      {{ codeRunnerStore.getExerciseSetupError() }}
    </div>
    <div class="setupPanel">
      <div class="panelSection">
        <MultiSelect
          v-model="codeRunnerStore.exerciseCreatorController.languages"
          display="chip"
          :options="languageChoices"
          optionLabel="label"
          optionValue="value"
          placeholder="programming languages"
          class="languageDropDown"
          id="language-selection"
        />
      </div>
      <div class="panelSection SubPanelSection">
        <div class="panelVerticalSection">
          Ram
          <vue-slider
            v-model="codeRunnerStore.exerciseCreatorController.ram"
            :min="128"
            :max="2048"
            class="slider"
          />
          <InputNumber
            v-model="codeRunnerStore.exerciseCreatorController.ram"
            inputId="integeronly"
            suffix=" Mb"
            :min="128"
            :max="2048"
            class="numberInputRam"
            id="ram-number-input"
          />
        </div>
        <div class="panelVerticalSection">
          Time for task
          <div style="display: flex; flex-direction: row">
            <InputNumber
              @update:model-value="
                (newNum: number) => {
                  codeRunnerStore.exerciseCreatorController.timeForTaskMin =
                    newNum * 60 +
                    (codeRunnerStore.exerciseCreatorController.timeForTaskMin -
                      Math.floor(
                        codeRunnerStore.exerciseCreatorController
                          .timeForTaskMin / 60
                      ) *
                        60)
                }
              "
              :model-value="
                Math.floor(
                  codeRunnerStore.exerciseCreatorController.timeForTaskMin / 60
                )
              "
              inputId="integeronly"
              suffix=" H"
              :min="0"
              :max="12"
              class="numberInput"
              id="hour-number-input"
            />
            <InputNumber
              @update:model-value="
                (newNum: number) => {
                  codeRunnerStore.exerciseCreatorController.timeForTaskMin =
                    Math.floor(
                      codeRunnerStore.exerciseCreatorController.timeForTaskMin /
                        60
                    ) *
                      60 +
                    +newNum
                }
              "
              :model-value="
                codeRunnerStore.exerciseCreatorController.timeForTaskMin -
                Math.floor(
                  codeRunnerStore.exerciseCreatorController.timeForTaskMin / 60
                ) *
                  60
              "
              inputId="integeronly"
              suffix=" M"
              :min="0"
              :max="59"
              class="numberInput"
              id="minute-number-input"
            />
          </div>
        </div>
        <div class="panelVerticalSection">
          Maximum execution time
          <div style="display: flex; flex-direction: row">
            <InputNumber
              v-model="
                codeRunnerStore.exerciseCreatorController.timeForExecutionMs
              "
              inputId="integeronly"
              suffix=" ms"
              :min="5"
              :max="5000"
              class="numberInput"
              id="ms-number-input"
            />
          </div>
        </div>
      </div>

      <div class="panelSection SubPanelSection">
        <div class="horizontalSection">Input type</div>
        <div class="horizontalSection">
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.inputType =
                    setTypeToInt(
                      codeRunnerStore.exerciseCreatorController.inputType
                    )
                }
              "
              inputId="inputType1"
              :value="true"
              :model-value="
                isTypeInt(codeRunnerStore.exerciseCreatorController.inputType)
              "
              id="radio-input-int"
            />
            <label>int</label>
          </div>
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.inputType =
                    setTypeToFloat(
                      codeRunnerStore.exerciseCreatorController.inputType
                    )
                }
              "
              inputId="inputType2"
              :value="true"
              :model-value="
                isTypeFloat(codeRunnerStore.exerciseCreatorController.inputType)
              "
              id="radio-input-float"
            />
            <label>float</label>
          </div>
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.inputType =
                    setTypeToString(
                      codeRunnerStore.exerciseCreatorController.inputType
                    )
                }
              "
              inputId="inputType3"
              :value="true"
              :model-value="
                isTypeString(
                  codeRunnerStore.exerciseCreatorController.inputType
                )
              "
              id="radio-input-string"
            />
            <label>string</label>
          </div>
        </div>
        <div class="horizontalSection">
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.inputType =
                    setTypeToSingle(
                      codeRunnerStore.exerciseCreatorController.inputType
                    )
                }
              "
              inputId="inputSize1"
              :value="true"
              :model-value="
                isTypeSingle(
                  codeRunnerStore.exerciseCreatorController.inputType
                )
              "
              id="radio-input-single"
            />
            <label>single value</label>
          </div>
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.inputType =
                    setTypeToArray(
                      codeRunnerStore.exerciseCreatorController.inputType
                    )
                }
              "
              inputId="inputSize2"
              :value="true"
              :model-value="
                isTypeArray(codeRunnerStore.exerciseCreatorController.inputType)
              "
              id="radio-input-array"
            />
            <label>array</label>
          </div>
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.inputType =
                    setTypeToDoubleArray(
                      codeRunnerStore.exerciseCreatorController.inputType
                    )
                }
              "
              inputId="inputSize3"
              :value="true"
              :model-value="
                isTypeDoubleArray(
                  codeRunnerStore.exerciseCreatorController.inputType
                )
              "
              id="radio-input-2d-array"
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
              @update:model-value="
                (value: any) => {
                  codeRunnerStore.exerciseCreatorController.outputType =
                    setTypeToInt(
                      codeRunnerStore.exerciseCreatorController.outputType
                    )
                }
              "
              inputId="outputype1"
              :value="true"
              :model-value="
                isTypeInt(codeRunnerStore.exerciseCreatorController.outputType)
              "
              id="radio-output-int"
            />
            <label>int</label>
          </div>
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.outputType =
                    setTypeToFloat(
                      codeRunnerStore.exerciseCreatorController.outputType
                    )
                }
              "
              inputId="outputype2"
              :value="true"
              :model-value="
                isTypeFloat(
                  codeRunnerStore.exerciseCreatorController.outputType
                )
              "
              id="radio-output-float"
            />
            <label>float</label>
          </div>
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.outputType =
                    setTypeToString(
                      codeRunnerStore.exerciseCreatorController.outputType
                    )
                }
              "
              inputId="outputype3"
              :value="true"
              :model-value="
                isTypeString(
                  codeRunnerStore.exerciseCreatorController.outputType
                )
              "
              id="radio-output-string"
            />
            <label>string</label>
          </div>
        </div>
        <div class="horizontalSection">
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.outputType =
                    setTypeToSingle(
                      codeRunnerStore.exerciseCreatorController.outputType
                    )
                }
              "
              inputId="outputSize1"
              :value="true"
              :model-value="
                isTypeSingle(
                  codeRunnerStore.exerciseCreatorController.outputType
                )
              "
              id="radio-output-single"
            />
            <label>single value</label>
          </div>
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  codeRunnerStore.exerciseCreatorController.outputType =
                    setTypeToArray(
                      codeRunnerStore.exerciseCreatorController.outputType
                    )
                }
              "
              inputId="outputSize2"
              :value="true"
              :model-value="
                isTypeArray(
                  codeRunnerStore.exerciseCreatorController.outputType
                )
              "
              id="radio-output-array"
            />
            <label>array</label>
          </div>
          <div class="RadioButtonSection">
            <RadioButton
              @update:model-value="
                (value: any) => {
                  resetManualtTetstBuffer()
                  console.log('RadioButtonSection: outDoubleeArray')
                  codeRunnerStore.exerciseCreatorController.outputType =
                    setTypeToDoubleArray(
                      codeRunnerStore.exerciseCreatorController.outputType
                    )
                }
              "
              inputId="outputSize3"
              :value="true"
              :model-value="
                isTypeDoubleArray(
                  codeRunnerStore.exerciseCreatorController.outputType
                )
              "
              id="radio-output-2d-array"
            />
            <label>2d array</label>
          </div>
        </div>
      </div>
    </div>
    <hr />
    <div class="manualTestPanel">
      <h1>Manual tests</h1>
      <ManualTestPnael
        :inputType="codeRunnerStore.exerciseCreatorController.inputType"
        :outputype="codeRunnerStore.exerciseCreatorController.outputType"
      />
    </div>
    <hr />
    <div class="autoTestPanel">
      <h1>Automatic tests</h1>
      <div class="panelSection SubPanelSection">
        <div class="horizontalSection" style="flex-direction: column">
          <h5>Amount of automatic tests</h5>
          <InputNumber
            v-model="
              codeRunnerStore.exerciseCreatorController.amountOfAutoTests
            "
            :min="1"
            :max="10"
            class="smallNumberInput"
            style="width: 0.5rem"
            id="amount-of-auto-test-input"
          />
          <div class="sliderContaienr">
            <Slider
              v-model="
                codeRunnerStore.exerciseCreatorController.amountOfAutoTests
              "
              :min="1"
              :max="10"
            />
          </div>
        </div>
        <div
          class="horizontalSection"
          style="flex-direction: column"
          v-if="
            isTypeArray(codeRunnerStore.exerciseCreatorController.inputType) ||
            isTypeDoubleArray(
              codeRunnerStore.exerciseCreatorController.inputType
            )
          "
        >
          <h5>Array x length range</h5>
          <div>
            <InputNumber
              v-model="
                codeRunnerStore.exerciseCreatorController.xArrayRange.min
              "
              :min="1"
              :max="10"
              class="smallNumberInput"
            />
            <InputNumber
              v-model="
                codeRunnerStore.exerciseCreatorController.xArrayRange.max
              "
              :min="1"
              :max="10"
              class="smallNumberInput"
            />
          </div>
          <div class="sliderContaienr">
            <Slider
              :model-value="[
                codeRunnerStore.exerciseCreatorController.xArrayRange.min,
                codeRunnerStore.exerciseCreatorController.xArrayRange.max,
              ]"
              range
              :min="1"
              :max="10"
              :unstyled="false"
              @update:modelValue="event => onXChange(event)"
            />
          </div>
        </div>
        <div
          class="horizontalSection"
          style="flex-direction: column"
          v-if="
            isTypeDoubleArray(
              codeRunnerStore.exerciseCreatorController.inputType
            )
          "
        >
          <h5>Array y length range</h5>
          <div>
            <InputNumber
              v-model="
                codeRunnerStore.exerciseCreatorController.yArrayRange.min
              "
              :min="1"
              :max="10"
              class="smallNumberInput"
            />
            <InputNumber
              v-model="
                codeRunnerStore.exerciseCreatorController.yArrayRange.max
              "
              :min="1"
              :max="10"
              class="smallNumberInput"
            />
          </div>
          <div class="sliderContaienr">
            <Slider
              :model-value="[
                codeRunnerStore.exerciseCreatorController.yArrayRange.min,
                codeRunnerStore.exerciseCreatorController.yArrayRange.max,
              ]"
              range
              :min="1"
              :max="10"
              :unstyled="false"
              @update:modelValue="event => onYChange(event)"
            />
          </div>
        </div>
      </div>
      <div class="panelSection SubPanelSection">
        <div
          v-if="
            codeRunnerStore.exerciseCreatorController.inputType ===
            ('int' as VarType)
          "
          class="SubPanelSection"
        >
          <div class="horizontalSection" style="flex-direction: column">
            <h5>int range</h5>
            <div>
              <InputNumber
                v-model="
                  codeRunnerStore.exerciseCreatorController.lengthRange.min
                "
                :min="-Math.pow(10, 10)"
                :max="Math.pow(10, 10)"
                :maxFractionDigits="0"
                @update:modelValue="
                  event =>
                    onLengthChange([
                      event,
                      codeRunnerStore.exerciseCreatorController.lengthRange.max,
                    ])
                "
              />
              <InputNumber
                v-model="
                  codeRunnerStore.exerciseCreatorController.lengthRange.max
                "
                :min="-Math.pow(10, 10)"
                :max="Math.pow(10, 10)"
                :maxFractionDigits="0"
                @update:modelValue="
                  event =>
                    onLengthChange([
                      codeRunnerStore.exerciseCreatorController.lengthRange.min,
                      event,
                    ])
                "
              />
            </div>
            <div
              class="sliderContaienr"
              style="width: 100%; height: 3rem; margin-top: 1rem"
            >
              <Slider
                :model-value="[
                  codeRunnerStore.exerciseCreatorController.lengthRange.min,
                  codeRunnerStore.exerciseCreatorController.lengthRange.max,
                ]"
                range
                :min="-Math.pow(10, 10)"
                :max="Math.pow(10, 10)"
                :unstyled="false"
                @change="
                  (value: any) => {
                    codeRunnerStore.exerciseCreatorController.lengthRange.min =
                      value[0]
                    codeRunnerStore.exerciseCreatorController.lengthRange.max =
                      value[1]
                  }
                "
              />
            </div>
          </div>
        </div>

        <div
          v-if="
            codeRunnerStore.exerciseCreatorController.inputType ===
            ('float' as VarType)
          "
          class="SubPanelSection"
        >
          <div class="horizontalSection" style="flex-direction: column">
            <h5>float range</h5>
            <div>
              <InputNumber
                v-model="
                  codeRunnerStore.exerciseCreatorController.lengthRange.min
                "
                :min="-Math.pow(10, 10)"
                :max="Math.pow(10, 10)"
                @update:modelValue="
                  event =>
                    onLengthChange([
                      event,
                      codeRunnerStore.exerciseCreatorController.lengthRange.max,
                    ])
                "
              />
              <InputNumber
                v-model="
                  codeRunnerStore.exerciseCreatorController.lengthRange.max
                "
                :min="-Math.pow(10, 10)"
                :max="Math.pow(10, 10)"
                @update:modelValue="
                  event =>
                    onLengthChange([
                      codeRunnerStore.exerciseCreatorController.lengthRange.min,
                      event,
                    ])
                "
              />
            </div>
            <div
              class="sliderContaienr"
              style="width: 100%; height: 3rem; margin-top: 1rem"
            >
              <Slider
                :model-value="[
                  codeRunnerStore.exerciseCreatorController.lengthRange.min,
                  codeRunnerStore.exerciseCreatorController.lengthRange.max,
                ]"
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

        <div class="SubPanelSection">
          <div class="horizontalSection" style="flex-direction: column">
            <h5>
              {{
                isTypeString(
                  codeRunnerStore.exerciseCreatorController.inputType
                )
                  ? 'string'
                  : isTypeFloat(
                        codeRunnerStore.exerciseCreatorController.inputType
                      )
                    ? 'float'
                    : 'int'
              }}

              range
            </h5>
            <div style="display: flex; justify-content: center">
              <InputNumber
                v-model="
                  codeRunnerStore.exerciseCreatorController.lengthRange.min
                "
                :min="
                  isTypeString(
                    codeRunnerStore.exerciseCreatorController.inputType
                  )
                    ? 0
                    : Number.MIN_SAFE_INTEGER
                "
                :max="
                  isTypeString(
                    codeRunnerStore.exerciseCreatorController.inputType
                  )
                    ? 30
                    : Number.MAX_SAFE_INTEGER
                "
                :maxFractionDigits="
                  isTypeFloat(
                    codeRunnerStore.exerciseCreatorController.inputType
                  )
                    ? 10
                    : 0
                "
                @update:modelValue="
                  event =>
                    onLengthChange([
                      event,
                      codeRunnerStore.exerciseCreatorController.lengthRange.max,
                    ])
                "
                id="string-range-low-input"
              />
              <InputNumber
                v-model="
                  codeRunnerStore.exerciseCreatorController.lengthRange.max
                "
                :min="
                  isTypeString(
                    codeRunnerStore.exerciseCreatorController.inputType
                  )
                    ? 0
                    : Number.MIN_SAFE_INTEGER
                "
                :max="
                  isTypeString(
                    codeRunnerStore.exerciseCreatorController.inputType
                  )
                    ? 30
                    : Number.MAX_SAFE_INTEGER
                "
                :maxFractionDigits="
                  isTypeFloat(
                    codeRunnerStore.exerciseCreatorController.inputType
                  )
                    ? 10
                    : 0
                "
                @update:modelValue="
                  event =>
                    onLengthChange([
                      codeRunnerStore.exerciseCreatorController.lengthRange.min,
                      event,
                    ])
                "
                id="string-range-up-input"
              />
            </div>
            <div
              class="sliderContaienr"
              style="width: 100%; height: 3rem; margin-top: 1rem"
            >
              <Slider
                :model-value="[
                  codeRunnerStore.exerciseCreatorController.lengthRange.min,
                  codeRunnerStore.exerciseCreatorController.lengthRange.max,
                ]"
                range
                :min="
                  isTypeString(
                    codeRunnerStore.exerciseCreatorController.inputType
                  )
                    ? 0
                    : Number.MIN_SAFE_INTEGER
                "
                :max="
                  isTypeString(
                    codeRunnerStore.exerciseCreatorController.inputType
                  )
                    ? 30
                    : Number.MAX_SAFE_INTEGER
                "
                :step="
                  isTypeFloat(
                    codeRunnerStore.exerciseCreatorController.inputType
                  )
                    ? 0.01
                    : 1
                "
                :unstyled="false"
                @update:modelValue="change => onLengthChange(change)"
              />
            </div>
            <div
              class="SubPanelSection"
              v-if="
                isTypeString(
                  codeRunnerStore.exerciseCreatorController.inputType
                )
              "
            >
              <div class="checkBoxContainer">
                <Checkbox
                  v-model="
                    codeRunnerStore.exerciseCreatorController.upperCaseInput
                  "
                  invalid
                  binary
                  id="upper-case-checkbox"
                />
                <label> A, B, C, D, ... </label>
              </div>
              <div class="checkBoxContainer">
                <Checkbox
                  v-model="
                    codeRunnerStore.exerciseCreatorController.lowerCaseInput
                  "
                  invalid
                  binary
                  id="lower-case-checkbox"
                />
                <label> a, b, c, d, ... </label>
              </div>
              <div class="checkBoxContainer">
                <Checkbox
                  v-model="
                    codeRunnerStore.exerciseCreatorController.numberInput
                  "
                  invalid
                  binary
                  id="number-checkbox"
                />
                <label> 1, 2, 3, 4 ... </label>
              </div>
              <div class="checkBoxContainer">
                <Checkbox
                  v-model="
                    codeRunnerStore.exerciseCreatorController
                      .specialCharacterInput
                  "
                  invalid
                  binary
                  id="special-char-checkbox"
                />
                <label> +, (, ), %, # ... </label>
              </div>
              <div class="checkBoxContainer">
                <Checkbox
                  v-model="
                    codeRunnerStore.exerciseCreatorController
                      .breakCharacterInupt
                  "
                  invalid
                  binary
                  id="character-breaks-checkbox"
                />
                <label> \n \t </label>
              </div>
              <div class="checkBoxContainer">
                <Checkbox
                  v-model="codeRunnerStore.exerciseCreatorController.spaceInupt"
                  invalid
                  binary
                  id="space-char-checkbox"
                />
                <label> [_] </label>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  test
</template>

<script setup lang="ts">
  import { onMounted, watch } from 'vue'
  import VueSlider from 'vue-slider-component'
  import ManualTestPnael from './ManualTestPnael.vue'
  import VarType, {
    setTypeToArray,
    isTypeDoubleArray,
    isTypeString,
    isTypeArray,
    isTypeInt,
    isTypeFloat,
    setTypeToDoubleArray,
    setTypeToSingle,
    setTypeToString,
    setTypeToInt,
    setTypeToFloat,
    isTypeSingle,
  } from '@/types/VarType'

  import { languageChoices } from '@/config/Data'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
import CodeRunnerStatus from '@/types/CodeRunnerStatus'
import CodeRunnerType from '@/types/CodeRunnerTypes'

  const codeRunnerStore = useCodeRunnerStore()

  const onXChange = (val: any) => {
    if (val[0] > val[1]) {
      val[0] = val[1]
    }
    codeRunnerStore.exerciseCreatorController.xArrayRange.min = val[0]
    codeRunnerStore.exerciseCreatorController.xArrayRange.max = val[1]
  }

  const onYChange = (val: any) => {
    if (val[0] > val[1]) {
      val[0] = val[1]
    }
    codeRunnerStore.exerciseCreatorController.yArrayRange.min = val[0]
    codeRunnerStore.exerciseCreatorController.yArrayRange.max = val[1]
  }

  const onLengthChange = (val: any) => {
    if (val[0] > val[1]) {
      val[0] = val[1]
    }
    codeRunnerStore.exerciseCreatorController.lengthRange.min = val[0]
    codeRunnerStore.exerciseCreatorController.lengthRange.max = val[1]
  }

  onMounted(() => {
    codeRunnerStore.exerciseCreatorController.resetParams()
  })

  const resetManualtTetstBuffer = () => {
    console.log('input output type cahnged')
    codeRunnerStore.clearTestsFromBuffer()
  }

  const getStartFuntionForLnagauge=(type: CodeRunnerType)=>{
    return "unimplmented";
  }

  watch(() => codeRunnerStore.exerciseCreatorController.languages, (newVal: CodeRunnerType[], oldVa: CodeRunnerType[]) => {
    codeRunnerStore.exerciseCreatorController.solutionCodes= {};
    for (let index = 0; index < newVal.length; index++) {
        
      codeRunnerStore.exerciseCreatorController.solutionCodes[newVal[index]]=getStartFuntionForLnagauge(newVal[index]);
      }
    
    });
</script>

<style>
  .mainContainer {
    overflow: scroll;
    height: 100%;
    max-height: 100vh;
    background-color: rgb(22, 22, 22);
    color: white;
  }

  .errorContainer {
    color: red;
    justify-self: center;
    align-items: center;
    align-content: center;
    justify-content: center;
    text-align: center;
  }
</style>
