<template>
  test
  {{
    JSON.stringify(
      codeRunnerStore.exerciseCreatorController.getSingleRowOfManualTests
    )
  }}
  <div>
    <div class="VerticalLine">
      <label class="VerticalLineElement">Input</label>
      <label class="VerticalLineElement">expected Output</label>
    </div>
    <div>
      <div
        v-for="(item, index) in codeRunnerStore.exerciseCreatorController
          .getSingleRowOfManualTests"
        v-bind:key="item.uuid"
        class="VerticalLine"
      >
        <div class="TestContainer">
          <VariablesInput
            :Type="codeRunnerStore.exerciseCreatorController.inputType"
            :isInput="true"
            :manualTestInputIndex="index"
          />
        </div>
        <div class="TestContainer">
          <VariablesInput
            :Type="codeRunnerStore.exerciseCreatorController.outputType"
            :isInput="false"
            :manualTestInputIndex="index"
          />
        </div>
        <div class="TrashButtonContainer">
          <Button
            class="trashButton"
            @click="
              () => {
                onRemoveButton(index)
              }
            "
          >
            <IconTrash />
          </Button>
        </div>
      </div>
    </div>
    <div class="VerticalLine">
      <Button class="addButton" @click="onAddButton" id="add-exercise-button">
        add
      </Button>
    </div>
  </div>
</template>

<script setup lang="ts">
  import IconTrash from '../assets/icons/IconTrash.vue'
  import VariablesInput from './VariablesInput.vue'
  import { useCodeRunnerStore } from '@/stores/CodeRunnerStore'
  import VarType from '@/types/VarType'
  import { onMounted, PropType } from 'vue'
  import ExerciseTest from '@/types/ExcericseTest'
  import { useToastStore } from '@/stores/ToastStore'
  const codeRunnerStore = useCodeRunnerStore()
  const toastStore = useToastStore()

  const onAddButton = () => {
    try {
      codeRunnerStore.exerciseCreatorController.addEmptyTest()
    } catch (error: any) {
      toastStore.showErrorMessage(error)
    }
  }
  const onRemoveButton = (index: number) => {
    codeRunnerStore.exerciseCreatorController.removeTest(index)
    // codeRunnerStore.removeTestFromBuffer(index)
  }
  onMounted(() => {
    codeRunnerStore.exerciseCreatorController.clearTests()
  })
</script>

<style>
  .VerticalLine {
    width: 100%;

    display: flex;
    justify-content: center;
  }
  .VerticalLineElement {
    margin-left: 20%;
    margin-right: 20%;
  }
  .TestContainer {
    border: 0.1rem solid;
    width: 40%;
    margin-right: 1rem;
    min-height: fit-content;
    max-height: 10rem;
    overflow-y: scroll;
  }
  .trashButton {
    background-color: transparent;
    border-color: transparent;
  }
  .trashButton * {
    background-color: transparent;
    border-color: transparent;
  }
  .TrashButtonContainer {
    width: 5rem;

    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 2rem;
    padding-right: 1rem;
  }

  .addButton {
    border-radius: 0.5rem;
    border-color: transparent;
    margin-top: 5rem;
  }
  .testScroll {
    height: 100%;
    max-height: 100%;
    overflow-y: scroll;
  }
</style>
