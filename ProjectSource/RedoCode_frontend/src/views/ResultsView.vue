<template>
  <main style="margin-top: 15rem">
    <div class="VerticalLine">
      <h1>Time for task: x:xx</h1>
    </div>
    <div class="VerticalLine" style="margin-top: 5rem">
      <h1>Execution Time: x:xx:xx</h1>
    </div>
    <div class="VerticalLine" style="margin-top: 5rem">
      <h2>Rate diffuciulty</h2>
    </div>
    <div class="VerticalLine algainBottom" style="margin-top: 5rem">
      <Button
        class="rateButton level1"
        :disabled="alreadyRated"
        :class="
          (alreadyRated ? selectedRating : ratingLevelShow) >= 1
            ? 'filledBar'
            : 'unfilledBar'
        "
        @mouseover="ratingLevelShow = 1"
        @mouseleave="ratingLevelShow = selectedRating"
        @click="selectedRating = 1"
      />
      <Button
        class="rateButton level2"
        :disabled="alreadyRated"
        :class="
          (alreadyRated ? selectedRating : ratingLevelShow) >= 2
            ? 'filledBar'
            : 'unfilledBar'
        "
        @mouseover="ratingLevelShow = 2"
        @mouseleave="ratingLevelShow = selectedRating"
        @click="selectedRating = 2"
      />
      <Button
        class="rateButton level3"
        :disabled="alreadyRated"
        :class="
          (alreadyRated ? selectedRating : ratingLevelShow) >= 3
            ? 'filledBar'
            : 'unfilledBar'
        "
        @mouseover="ratingLevelShow = 3"
        @mouseleave="ratingLevelShow = selectedRating"
        @click="selectedRating = 3"
      />
      <Button
        class="rateButton level4"
        :disabled="alreadyRated"
        :class="
          (alreadyRated ? selectedRating : ratingLevelShow) >= 4
            ? 'filledBar'
            : 'unfilledBar'
        "
        @mouseover="ratingLevelShow = 4"
        @mouseleave="ratingLevelShow = selectedRating"
        @click="selectedRating = 4"
      />
      <Button
        class="rateButton level5"
        :disabled="alreadyRated"
        :class="
          (alreadyRated ? selectedRating : ratingLevelShow) >= 5
            ? 'filledBar'
            : 'unfilledBar'
        "
        @mouseover="ratingLevelShow = 5"
        @mouseleave="ratingLevelShow = selectedRating"
        @click="selectedRating = 5"
      />
    </div>
    <div class="VerticalLine" style="margin-top: 5rem" v-if="!alreadyRated">
      <Button class="saveButton" @click="onSaveRate"> save Rate </Button>
    </div>

    <div class="VerticalLine" style="margin-top: 5rem">
      <Textarea
        class="CommentArea"
        :disabled="!ActiveUserStore.isLogged"
        v-model="commentInput"
        :invalid="!validatedComment"
      />
    </div>
    <div style="margin-left: 3rem">
      {{ commentInput.length + '/3000' }}
    </div>
    <div class="VerticalLine" style="margin-top: 0.5rem; display: flex">
      <Button
        class="commentButton"
        :disabled="!ActiveUserStore.isLogged"
        @click="onCommentButton"
      >
        comment
      </Button>
    </div>
    <div v-for="(data, index) in comments" v-bind:key="index">
      <div class="VerticalLine" style="margin-top: 0.5rem">
        <div class="ProfilePicContainer">
          <img :src="data.profilePic" class="profilePic" />
        </div>
        <div class="CommentContainer">
          <h3 style="margin-left: 2rem; margin-top: 1rem">{{ data.nick }}</h3>
          <div class="commentContent">
            {{ data.content }}
          </div>
        </div>
      </div>
    </div>
  </main>

  <!-- <Image :src=data.profilePic class="profilePic" /> -->
</template>

<script setup lang="ts">
  import { computed, Ref, ref } from 'vue'
  import { useActiveUserStore } from '@/stores/ActiveUserStore'

  const ActiveUserStore = useActiveUserStore()
  const commentInput: Ref<string> = ref('')
  const comments = ref([
    {
      nick: 'nick',
      content: 'Great exercise',
      profilePic: 'https://i.imgur.com/Z6fpYPD.png',
    },
    {
      nick: 'nick',
      content: 'Great exercise',
      profilePic: 'https://i.imgur.com/Z6fpYPD.png',
    },
    {
      nick: 'nick',
      content: 'Great exercise',
      profilePic: 'https://i.imgur.com/Z6fpYPD.png',
    },
    {
      nick: 'nick',
      content: 'Great exercise',
      profilePic: 'https://i.imgur.com/Z6fpYPD.png',
    },
  ])

  const ratingLevelShow: Ref<number> = ref(0)

  const selectedRating: Ref<number> = ref(0)
  const alreadyRated: Ref<boolean> = ref(!ActiveUserStore.isLogged)
  const validatedComment = computed(() => {
    return commentInput.value.length > 0 && commentInput.value.length < 3000
  })

  const onCommentButton = () => {
    if (validatedComment.value) {
      comments.value.unshift({
        nick: ActiveUserStore.acoountInfo.nick.value,
        profilePic: 'https://i.imgur.com/Z6fpYPD.png',
        content: commentInput.value,
      })
      commentInput.value = ''
    }
  }
  const onSaveRate = () => {
    alreadyRated.value = true
  }
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
