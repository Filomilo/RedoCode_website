<template>
  <main class="PlayGroundBase" >
    <TabView >
      <div class="childHeight">
      <TabPanel header="Information" class="childHeight"  >
        <ExerciseInfoSetup class="childHeight" />
      </TabPanel>
    </div>
      <TabPanel header="Setup">
        <ExerciseSetupPanel />
      </TabPanel>

      <TabPanel header="Solution">
        <CodeRunnerPanel :showLeftPanel="true" :show-creator-panel="true" :connectAtStart="false" />
      </TabPanel>
  </TabView>
  
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'
import CodeRunnerPanel from '@/components/CodeRunnerPanel.vue'
import { useToastStore } from '@/stores/ToastStore'
import { onBeforeRouteUpdate } from 'vue-router'
import ExerciseSetupPanel from '@/components/ExerciseSetupPanel.vue'
import ExerciseDescriptionPanel from '@/components/ExerciseDescriptionPanel.vue'
import ExerciseInfoSetup from '@/components/ExerciseInfoSetup.vue'
const text = ref('')
const ToastStore = useToastStore()

onMounted(() => {
  ToastStore.featureNotImplemented()
})
</script>

<style>
.p-tabview-nav-container *{
  background-color: black;
  
}
.p-tabview-nav{
  width: 100%;
  justify-content: center;
  background-color: black;
  
}
.p-tabview-nav *{
  width: 100%;
  background-color: rgb(31, 31, 31);
}
.p-tabview-nav {
  background-color: red;
  padding-left: 0;
  
}
.p-tabview-header{
  text-align: center;
}
.p-tabview-panels{
  width: 100%;
  height: 100%;
  padding: 0;
  min-width: 100%;
  flex-grow: 100%;

}
.p-tabview *{

  
}

.p-tabview-panels  *{

}

.p-tabview-nav *{

}


.p-tabview-panels{
  background-color: rgb(31, 31, 31);
  min-height: 100%;
  overflow: hidden;
  max-height: 100%;
}
.p-tabview .p-component{

}
.p-tabview{
  background-color: cadetblue;
  height: 93%;
}
.info{
  
}
.childHeight  *{
  height: 100%;
}
.p-tabview-nav-container{
  height: 4rem;
}
.p-tabview-panel{
  height: 100%;
}
</style>