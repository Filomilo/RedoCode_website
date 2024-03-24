<template>

    <Splitter layout="vertical" style="height: 100%;" class="exerciseSetupPanel" >
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
            <vue-slider v-model="sliderVal" :min="128"  :max="2048"  />
            <InputNumber v-model="sliderVal" inputId="integeronly" suffix=" Mb" :min="128"  :max="2048" class="numberInputRam" />
        </div>
        <div class="panelVerticalSection">
            Time for task
            <div style="display: flex; flex-direction:row">
            <InputNumber v-model="hourVal" inputId="integeronly" suffix=" H" :min="0"  :max="12" class="numberInput" />
            <InputNumber v-model="minuteVal" inputId="integeronly" suffix=" M" :min="0"  :max="60"   class="numberInput" />
            </div>

        </div>

       
    </div>

            </div>
           
        </SplitterPanel>
        <SplitterPanel>
            <div class="manualTestPanel">
                panel 2
           </div>
        </SplitterPanel>
        <SplitterPanel>
            <div class="autoTestPanel">
                panel 3
           </div>
        </SplitterPanel>
    </Splitter>

</template>

<script setup lang="ts">
import { ref } from 'vue';
import VueSlider from 'vue-slider-component'
import 'vue-slider-component/theme/antd.css'


const langOptions=[
      { label: "Cpp", value: "cpp" }, { label: "Js", value: "js" }
    ];
      const selectedLagn=ref("");
      const sliderVal=ref(55);
      const minuteVal=ref(55);
      const hourVal=ref(55);
</script>