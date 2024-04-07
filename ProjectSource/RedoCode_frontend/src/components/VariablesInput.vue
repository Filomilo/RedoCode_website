<template>
  <div style="width: 100%; height: 100%; max-height: 100%; max-width: 100%">
    <div class="dimensionsPanel">
      <div :v-if="dimension >= 1">
        <label>X size: </label>
        <InputNumber
          v-model="xSize"
          inputId="integeronly"
          class="InputSizeContainer"
          :min="0"
          :max="20"
          @update:modelValue="(value) => onXDimChange(value)"
        />
      </div>
      <div :v-if="dimension >= 2">
        <label>Y size: </label>
        <InputNumber
          v-model="ySize"
          inputId="integeronly"
          class="InputSizeContainer"
          :min="1"
          :max="20"
          @update:modelValue="(value) => onYDimChange(value)"
        />
      </div>
    </div>

    <div class="inputCotainer">
      <div v-for="(row, index) in data" class="rowContainer" v-bind:key="index">
        <div v-for="(val, index) in row" class="signleValContainer" v-bind:key="index">
          <InputSetupInput />
        </div>
      </div>
    </div>

    <!-- <InputNumber  inputId="integeronly" /> -->
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import InputSetupInput from './InputSetupInput.vue'
const props = defineProps(['type'])
const dimension = ref(2)
const xSize = ref(1)
const ySize = ref(1)
const data = ref([[0]])

const onXDimChange = (value: number) => {
  console.log('change x: ' + value)
  if (data.value[0].length < value) {
    let toIncrease = value - data.value[0].length
    for (let i = 0; i < data.value.length; i++) {
      console.log('index: ' + i)

      console.log('toIncrease: ' + toIncrease)

      for (let j = 0; j < toIncrease; j++) {
        data.value[i].push(0)
      }
    }
  } else {
    if (data.value[0].length > value) {
      let todecrease = data.value[0].length - value
      for (let i = 0; i < data.value.length; i++) {
        console.log('index: ' + i)

        // console.log("toIncrease: "+ toIncrease)
        for (let j = 0; j < todecrease; j++) {
          data.value[i].pop()
        }
      }
    }
  }
}
const onYDimChange = (value: number) => {
  console.log('change y: ' + value)
  if (data.value[0].length < value) {
    let toIncrease = value - data.value.length
    console.log('toIncrease: ' + toIncrease)
    for (let i = 0; i < toIncrease; i++) {
      console.log('data.value[0].length: ' + data.value[0].length)
      data.value.push([])
      for (let j = 0; j < xSize.value; j++) {
        console.log('j: ' + j)
        data.value[data.value.length - 1].push(0)
      }
    }
  } else {
    if (data.value[0].length > value) {
      let toDecrease = data.value.length - value
      // console.log("toIncrease: "+ toIncrease)
      for (let i = 0; i < toDecrease; i++) {
        console.log('data.value[0].length: ' + data.value[0].length)
        data.value.pop()
      }
    }
  }
}
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
  background-color: rgb(43, 29, 13);
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
