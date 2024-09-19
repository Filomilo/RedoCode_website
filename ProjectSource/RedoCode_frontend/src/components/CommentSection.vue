<template>
  <main>

    <div class="commentSectionContainer">
      <div class="VerticalLine" style="margin-top: 5rem">
        <Textarea
          class="CommentArea"
          :disabled="!ActiveUserStore.isLogged"
          v-model="commentInput"
          :invalid="!validatedComment"
          id="comment-input"
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
          id="comment-post"
        >
          comment
        </Button>
  
      </div>
      <div v-for="(data, index) in commentsRef" v-bind:key="index">
        <div
          class="VerticalLine"
          :id="'comment-' + index"
          style="margin-top: 0.5rem"
        >
          <div class="ProfilePicContainer">
            <img :src="profilePicImageResolve( data.profilePicture)" class="profilePic" />
          </div>
          <div class="CommentContainer">
            <h3
              style="margin-left: 2rem; margin-top: 1rem"
              :id="'comment-nick-' + index"
            >
              {{ data.nickname }}
            </h3>
            <div class="commentContent" :id="'comment-content-' + index">
              {{ data.comment }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
  import { computed, Ref, ref } from 'vue'
  import { useActiveUserStore } from '@/stores/ActiveUserStore'
  import CommentType from '@/types/ApiMesseages/CommentType'
  import EndpointAcces from '@/controllers/EndpointsAcces'
  import { useToastStore } from '@/stores/ToastStore'
  import profilePicImageResolve from '@/tools/ImageResolve'
  const props = defineProps<{
    comments: CommentType[]
    id: number
  }>()

  const commentsRef: Ref<CommentType[]> = ref(props.comments)

  const ActiveUserStore = useActiveUserStore()
  const ToastStore = useToastStore()
  const commentInput: Ref<string> = ref('')

  const validatedComment = computed(() => {
    return commentInput.value.length > 0 && commentInput.value.length < 3000
  })

  const onCommentButton = () => {
    EndpointAcces.authorized
      .postComment(commentInput.value, props.id)
      .then(x => {
        console.log('token: ' + JSON.stringify(x))
        if (x < 200 || x > 201) {
          ToastStore.showErrorMessage("Couldn't' post comment")
        } else {
          if (validatedComment.value) {
            console.log("comment post: "+JSON.stringify(ActiveUserStore.accountInfo)  )
            commentsRef.value.unshift({
              nickname: ActiveUserStore.accountInfo.nickname,
              profilePicture: ActiveUserStore.accountInfo.profilePicture,
              comment: commentInput.value,
            })
            commentInput.value = ''
          }
          ToastStore.showSuccessMessage('Succsefully commented')
        }
      })
  }
</script>

<style>
  .commentSectionContainer {
    width: 100%;
    height: 100%;

    display: flex;
    flex-direction: column;
    justify-content: center;
  }
</style>
