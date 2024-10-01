<template>
  <div class="image_upload_container">
    <FileUpload
      id="choose-image-button"
      customUpload
      name="demo[]"
      @select="onFileSelect"
      :multiple="false"
      accept="image/*"
      :maxFileSize="1024 * 1024 * 5"
      class="image_upload_container"
      mode="basic"
    >
    </FileUpload>
    <div class="image_container" v-if="img">
      <div class="image_mask">
        <img :src="img" alt="Image" class="image_uploaded" />
      </div>
      <Button
        id="upload-image-button"
        class="buttonUpload"
        @click="onUploud"
        :disabled="!allowUpload"
      >
        upload
      </Button>
    </div>
  </div>
</template>

<script lang="ts" setup>
  import EndpointAccess from '@/controllers/EndpointsAccess'
  import { useToastStore } from '@/stores/ToastStore'
  import { json } from 'agent-base'
  import FileUpload from 'primevue/fileupload'
  import type {
    FileUploadSelectEvent,
    FileUploadErrorEvent,
    FileUploadUploadEvent,
  } from 'primevue/fileupload'
  import { Ref, ref } from 'vue'

  const toastStore = useToastStore()
  const emit = defineEmits(['ChangedProfile'])

  const img: Ref<null | string> = ref(null)
  const allowUpload = ref(true)

  function onFileSelect(event: FileUploadSelectEvent) {
    const file = event.files[0]
    const reader = new FileReader()

    reader.onload = async e => {
      if (e.target != null && typeof e.target.result === 'string') {
        img.value = e.target.result
      }
    }

    reader.readAsDataURL(file)
  }

  const onUploud = () => {
    if (img.value) {
      allowUpload.value = false
      toastStore.showProcessingMessage('Sending image')
      EndpointAccess.authorized
        .postAccountPic(img.value)
        .then(x => {
          toastStore.showSuccessMessage('Successfully changed profile image')
          emit('ChangedProfile')
        })
        .catch(ex => {
          toastStore.showErrorMessage('Error sending image: ' + ex)
        })
        .finally(() => {
          allowUpload.value = true
        })
    }
  }
</script>

<style lang="css">
  .image_upload_container {
    width: 100%;
    height: 100%;
    justify-content: center;
    align-items: center;
  }
  .image_container {
    width: 100%;
    height: 100%;
    justify-self: center;
    align-items: center;
    max-width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 1rem;
    flex-direction: column;
  }
  .image_mask {
    aspect-ratio: 1/1;
    border-radius: 100%;
    border: 0.21rem solid rgb(158, 52, 114);
    overflow: hidden;
    display: flex;
    justify-content: center;
    width: 50%;
  }
  .p-fileupload * {
    background-color: var(--primary);
    border: none;
  }
  .image_uploaded {
    width: 100%;
    height: 100%;
  }

  .buttonUpload {
    margin-top: 1rem;
    background-color: var(--primary);
    border: none;
    border-radius: 0.5rem;
  }
</style>
