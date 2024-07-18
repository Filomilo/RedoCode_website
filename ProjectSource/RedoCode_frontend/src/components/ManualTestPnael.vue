<template>
  <div>
    <div class="VerticalLine">
      <label class="VerticalLineElement">Input</label>
      <label class="VerticalLineElement">expected Output</label>
    </div>
    <div>
      <div
        v-for="(item, index) in codeRunnerStore.manualTestBuffer"
        v-bind:key="index"
        class="VerticalLine"
      >
        <div class="TestContainer">
          <VariablesInput :Type="inputType" :isInput="true" :manualTestInputIndex="index" />
        </div>
        <div class="TestContainer">
          <VariablesInput :Type="outputype" :isInput="false" :manualTestInputIndex="index" />
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
      <Button class="addButton" @click="onAddButton" id="add-exercise-button"> add </Button>
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
const codeRunnerStore = useCodeRunnerStore()
const props = defineProps({
  inputType: { type: String as PropType<VarType>, required: true, default: 'SINGLE_INT' },
  outputype: { type: String as PropType<VarType>, required: true, default: 'SINGLE_INT' }
})
const onAddButton = () => {
  codeRunnerStore.addblankTestToBuffer(props.inputType, props.outputype)
}
const onRemoveButton = (index: number) => {
  codeRunnerStore.removeTestFromBuffer(index)
}
onMounted(() => {
  codeRunnerStore.clearTestsFromBuffer()
  codeRunnerStore.addblankTestToBuffer(props.inputType, props.outputype)
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
  background-color: rgb(0, 255, 98);
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
