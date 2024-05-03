<template>
  <main style="margin-top: 15rem">
    <div class="VerticalLine">
      <h1>Time for task: 5:33</h1>
    </div>
    <div class="VerticalLine" style="margin-top: 5rem">
      <h1>Execution Time: 0:03:22</h1>
    </div>
    <div class="VerticalLine" style="margin-top: 5rem">
      <h2>Rate diffuciulty</h2>
    </div>
    <div class="VerticalLine algainBottom" style="margin-top: 5rem">
      <Button class="rateButton level1" />
      <Button class="rateButton level2" />
      <Button class="rateButton level3" />
      <Button class="rateButton level4" />
      <Button class="rateButton level5" />
    </div>
    <div class="VerticalLine" style="margin-top: 5rem">
      <Button class="saveButton"> save Rate </Button>
    </div>

    <div class="VerticalLine" style="margin-top: 5rem">
      <Textarea class="CommentArea" />
    </div>
    <div class="VerticalLine" style="margin-top: 0.5rem; display: flex">
      <Button class="commentButton"> comment </Button>
    </div>
    <div v-for="(data, index) in comments" v-bind:index="index">
      <div class="VerticalLine" style="margin-top: 0.5rem">
        <div class="ProfilePicContainer">
          <img :src="data.profilePic" class="profilePic" />
        </div>
        <div class="CommentContainer">
          <h3 style="margin-left: 2rem; margin-top: 1rem">{{ data.nick }}</h3>
          <div style="margin: 1rem">
            {{ data.content }}
          </div>
        </div>
      </div>
    </div>
  </main>

  <!-- <Image :src=data.profilePic class="profilePic" /> -->
</template>

<script setup lang="ts">
import { ref } from 'vue'
const comments = ref([
  {
    nick: 'nick',
    content: 'Great exercise',
    profilePic: 'https://thispersondoesnotexist.com/'
  },
  {
    nick: 'nick',
    content: 'Great exercise',
    profilePic: 'https://thispersondoesnotexist.com/'
  },
  {
    nick: 'nick',
    content: 'Great exercise',
    profilePic: 'https://thispersondoesnotexist.com/'
  },
  {
    nick: 'nick',
    content: 'Great exercise',
    profilePic: 'https://thispersondoesnotexist.com/'
  }
])
</script>

<style>
.VerticalLine {
  width: 100%;
}

.rateButton {
  border-color: transparent;
  margin: 0.1rem;
  border-radius: 0.2rem;
  align-self: flex-end;
}

.level1 {
  height: 0.1rem;
}
.level2 {
  height: 2rem;
}
.level3 {
  height: 3rem;
}
.level4 {
  height: 4rem;
}
.level5 {
  height: 5rem;
}
.algainBottom {
  display: flex;
  flex-direction: row;
}
.saveButton {
  border-radius: 0.2rem;
  border-color: transparent;
}
.commentButton {
  border-radius: 0.2rem;
  border-color: transparent;
  align-self: flex-end;
}

.CommentArea {
  width: 95%;
  background-color: rgb(49, 49, 49);
  height: 10rem;
  width: 95%;
  color: white;
}

.ProfilePicContainer {
  width: 5rem;
  max-width: 5rem;
  margin-left: 2rem;
}
.profilePic {
  width: 100%;
  border-radius: 50rem;
  max-width: 100%;
  height: 5rem;
}

.CommentContainer {
  background-color: rgb(22, 22, 22);
  border-radius: 2rem;
  width: 95%;
  height: 20rem;
  margin: 2rem;
  color: white;
}
</style>
