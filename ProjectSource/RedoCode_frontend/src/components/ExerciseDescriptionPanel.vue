<template>
    <div class="DesciptionPanelContainer">
        <div class="DesciptionTitleContainer">
            <div class="DesciptionTitle"> Fibonachi</div>
        </div>
        <div class="DescriptioNContainer">
            <!-- <div v-html="markdown.render(mdSrc)" style="width: 100%;" /> -->
            descitpiron of taks, create fibonachi sewaunce
        </div>
    </div>
    
</template>

<script setup lang="ts">
import MarkdownIt from "markdown-it";
const markdown = new MarkdownIt();

const mdSrc = `# tile
        descitpiron of taks, create fibonachi sewaunce`;
</script>