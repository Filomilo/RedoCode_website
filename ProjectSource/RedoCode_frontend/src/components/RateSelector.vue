<template>
  <div v-if="props.rateOptions !== undefined && props.rateOptions.length > 0">
    <div class="RateSelector">
      <div class="ColumnsContainer">
        <Suspense>
          <div
            v-for="(item, index) in props.rateOptions"
            v-bind:key="item.value"
            :id="props.id + '-' + index"
            class="ColumnContainer"
            :style="
              'height: ' +
              (100 / (props.rateOptions.length + 1)) * (index + 2) +
              '%'
            "
          >
            <div
              class="Column"
              @click="selectColumn(index)"
              :style="
                'background-color: ' + getColorForcolumnOfIndex(index) + ' ;'
              "
              @mouseover="columnEnter(index)"
              @mouseleave="columnleave"
            />
          </div>

          <template #fallback>
            <span>Loading...</span>
          </template>
        </Suspense>
      </div>
      <p
        class="labelContaiener"
        :style="'color: ' + activeLabelColor + ' ;'"
        :id="props.id + '-selection'"
      >
        {{ activeLabel }}
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { required } from '@vuelidate/validators'
  import { computed, ComputedRef, ModelRef, Ref, ref, onMounted } from 'vue'
  const chroma = await import('chroma-js')

  const props = defineProps<{
    rateOptions: RateOption[]
    heightChange?: number
    id: string
  }>()

  const model: ModelRef<number | string | undefined> = defineModel()

  const defaultColor = 'grey'
  const selectedIndex = ref(-1)
  const hoverIndex = ref(-1)

  const activeLabel: ComputedRef<string> = computed(() => {
    if (selectedIndex.value >= 0) {
      return props.rateOptions[selectedIndex.value].label === undefined
        ? ''
        : props.rateOptions[selectedIndex.value].label
    }
    return ''
  }) as ComputedRef<string>

  const activeLabelColor: ComputedRef<string> = computed(() => {
    return getColorSelection(selectedIndex.value)
  }) as ComputedRef<string>

  const activeIndex: ComputedRef<number> = computed(() => {
    if (hoverIndex.value >= 0) {
      return hoverIndex.value
    }
    return selectedIndex.value
  })

  const getColorForcolumnOfIndex = (index: number): string => {
    if (activeIndex.value < index) {
      return defaultColor
    } else {
      return getColorSelection(
        hoverIndex.value < 0 ? selectedIndex.value : hoverIndex.value
      )
    }
  }

  const selectColumn = (index: number) => {
    selectedIndex.value = index
    model.value = props.rateOptions[index].value
  }

  const gradient = chroma.scale(['#00ff00', '#ff0000']).mode('lab').colors(5) // Generate 10 colors between the two
  console.log(`Rate Selector gradient: ${JSON.stringify(gradient)}`)
  const getColorSelection = (val: number): string => {
    return gradient[val]
  }

  const columnEnter = (index: number) => {
    hoverIndex.value = index
  }
  const columnleave = () => {
    hoverIndex.value = -1
  }
  const heightChangeColumn =
    props.heightChange === undefined ? 10 : props.heightChange === undefined
  document.documentElement.style.setProperty(
    '--heightChangeColumn',
    `${heightChangeColumn}px`
  )

  onMounted(() => {
    console.log('Rate selctor props: ' + JSON.stringify(props))
  })
</script>
<script lang="ts">
  export interface RateOption {
    value: any
    label?: string | undefined
    color?: string | undefined
  }
</script>

<style>
  .RateSelector {
    height: 100%;
    width: 100%;
    display: flex;
    align-items: center;
    flex-direction: column;
    font-size: 1.5rem;
    min-height: 5rem;
  }
  .ColumnsContainer {
    display: flex;
    flex-direction: row;
    flex: 1;
    width: 100%;
    height: 100%;
    padding: 2%;
    align-items: flex-end;
    align-content: flex-start;
  }
  .ColumnContainer {
    flex-direction: column;
    flex: 1;
    flex-direction: row;
    align-items: flex-start;
    align-content: flex-start;
    margin: 1%;

    display: flex;
    flex-direction: row;
    flex: 1;
    width: 100%;
    height: 100%;
    padding: 2%;
    align-items: flex-end;
    align-content: flex-start;
  }
  .Column {
    flex: auto;
    margin: 0.01vh;
    width: 100%;
    height: calc(100% - var(--heightChangeColumn));
    border-radius: 1.5vh;
    transition: height 0.1s ease;
    transition: background-color 0.3s ease-out;
    cursor: pointer;
  }
  .Column:hover {
    height: calc(100%);
  }

  .labelContaiener {
    height: 20%;
  }
</style>
