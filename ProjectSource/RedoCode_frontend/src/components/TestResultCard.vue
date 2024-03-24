<template>
<div class="resultPanelContainer" >
    <div class="testInputContiner">
        11
    </div>
    <div class="testOuputSection">
        <TabView>
            <TabPanel header="Console">
                <div class="consoleOutputCard" v-html="console">
                 </div>
            </TabPanel>
            <TabPanel header="Result">
                <div class="TypeResultContainer">
                    <div class="TypeResultContainerPanel" style="text-wrap: wrap;">
                        {{ JSON.stringify(data.correct_solution) }}
                    </div>
                    <div class="TypeResultContainerPanel">
                        {{ JSON.stringify(data.achived_solution) }}
                    </div>
                </div>
            </TabPanel>

        </TabView>
    </div>
    <div class="testValidationSection">
        Correct
    </div>
</div>
</template>

<script setup lang="ts">
import type CodeResultsType from "@/types/CodeResultsType";
const props = defineProps<{
    data: CodeResultsType;

}>();

const console:string=props.data.Console_output;
</script>