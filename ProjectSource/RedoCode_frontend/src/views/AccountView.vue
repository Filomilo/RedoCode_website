<template>
  <main>
    <Dialog
      @after-hide="onDialogHide"
      @show="onDialogShow"
      class="image_dialog_container"
      v-model:visible="changeAccountImageDialogVisible"
      header="Change account image"
      id="Change_image_dialog"
    >
      <ChangeAccountImageDialog
        id="change-profile-image-dialog"
        @ChangedProfile="onChangedProfile"
      />
    </Dialog>

    <Dialog
      @after-hide="onDialogHide"
      @show="onDialogShow"
      class="password_change_dialog_container"
      v-model:visible="changePasswordDialogVisible"
      header="Change password"
      id="Change_password_dialog"
    >
      <ChangePasswordDialog
        id="change_password_dialog"
        @ChangedPassword="onChangedPassword"
      />
    </Dialog>

    <Dialog
      @after-hide="onDialogHide"
      @show="onDialogShow"
      class="removeAccountDialogVisible"
      v-model:visible="removeAccountDialogVisible"
      header="Remove account"
      id="Remove_account_dialog"
    >
      <RemoveAccountDialog id="remove-account-dialog" />
    </Dialog>

    <div class="AccountInfoPanel" id="Account_Info_Panel">
      <div class="ImageContainer">
        <img :src="imgURL" class="ProfileImgStyle" id="profile-image" />
      </div>
      <div class="editButtonContainer">
        <Button
          id="change-profile-picture"
          class="IconEditImg"
          @click="onChangeProfilePic"
        >
          <IconEdit />
        </Button>
      </div>
      <div id="user-nickname" class="NickContainer">
        {{ activeUserStore.accountInfo.nickname }}
      </div>
      <div class="DescriptionContainer" id="user_description">
        {{ description }}
      </div>
      <div class="LogoutButtonContainer">
        <Button @click="onLogOutButton" id="logout"> Logout </Button>
      </div>
    </div>
    <div class="BottomPanel">
      <div class="SettingSidePanel">
        <Button
          id="Statistics-button"
          :class="
            screenSelected === Panels.STATISTIC
              ? 'SettingButtonNavigation selected'
              : 'SettingButtonNavigation'
          "
          @click="setPanel(Panels.STATISTIC)"
        >
          Statistics
          <div
            :class="
              screenSelected === Panels.STATISTIC
                ? 'bottomLine selected'
                : 'bottomLine'
            "
          />
        </Button>
        <Button
          id="Settings-button"
          :class="
            screenSelected === Panels.SETTINGS
              ? 'SettingButtonNavigation selected'
              : 'SettingButtonNavigation'
          "
          @click="setPanel(Panels.SETTINGS)"
        >
          Settings
          <div
            :class="
              screenSelected === Panels.SETTINGS
                ? 'bottomLine selected'
                : 'bottomLine'
            "
          />
        </Button>
      </div>
      <div class="SettingContentPanel">
        <div v-if="screenSelected === Panels.STATISTIC">
          <StatisticPanel />
        </div>
        <div v-if="screenSelected === Panels.SETTINGS">
          <div id="email-signature" class="SettingContentRow">
            E-mail: {{ emailSignature }}
          </div>
          <div class="SettingContentRow">
            Description:
            <div class="descriptionContainer">
              <Textarea
                v-model="descriptionEditor"
                rows="5"
                cols="30"
                id="Account_description_input"
              />
              <Button
                id="change_description_button"
                class="changeButton"
                style="height: fit-content"
                @click="onChangeDescription"
              >
                change
              </Button>
            </div>
          </div>
          <div class="SettingContentRow">
            Password: *******
            <Button
              id="Change_Password_button"
              class="changeButton"
              @click="onChangePassword"
            >
              change
            </Button>
          </div>

          <div class="SettingContentRow center">
            <Button
              id="remove-button"
              class="removeAccountButton"
              @click="onRemoveAccount"
            >
              Remove account
            </Button>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
  import { ref, type Ref, computed, onMounted } from 'vue'
  import IconEdit from '../assets/icons/IconEdit.vue'
  import { useActiveUserStore } from '../stores/ActiveUserStore'
  import router from '@/router'
  import StatisticPanel from '@/components/StatisticPanel.vue'
  import { useToastStore } from '@/stores/ToastStore'
  import profilePicImageResolve from '@/tools/ImageResolve'
  import ChangeAccountImageDialog from '@/components/ChangeAccountImageDialog.vue'
  import ChangePasswordDialog from '@/components/ChangePasswordDialog.vue'
  import RemoveAccountDialog from '@/components/RemoveAccountDialog.vue'

  import { useGlobalStateStore } from '@/stores/GlobalStateStore'
  import EndpointAccess from '@/controllers/EndpointsAccess'
  const activeUserStore = useActiveUserStore()
  const globalStateStore = useGlobalStateStore()
  const ToastStore = useToastStore()

  const changeAccountImageDialogVisible = ref(false)
  const changePasswordDialogVisible = ref(false)
  const removeAccountDialogVisible = ref(false)

  const description = ref('')
  const descriptionEditor = ref('')
  const emailSignature = ref('')

  const imgURL = computed(() => {
    return profilePicImageResolve(activeUserStore.accountInfo.profilePicture)
  })

  onMounted(() => {
    fetchUserDetails()
  })
  const fetchUserDetails = () => {
    EndpointAccess.authorized
      .getUserDetails()
      .then(x => {
        description.value = x.description
        emailSignature.value = x.emailSignature
      })
      .catch(ex => {
        ToastStore.showErrorMessage("Couldn't fetch user data: " + ex)
      })
  }

  enum Panels {
    STATISTIC,
    SETTINGS,
  }

  const screenSelected: Ref<Panels> = ref(Panels.STATISTIC)
  const setPanel = (type: Panels) => {
    console.log('select')
    screenSelected.value = type
  }

  const onLogOutButton = async () => {
    await activeUserStore.logout()
    location.reload()
  }

  const onChangePassword = () => {
    changePasswordDialogVisible.value = true
  }

  const onRemoveAccount = () => {
    removeAccountDialogVisible.value = true
  }

  const onChangeProfilePic = () => {
    changeAccountImageDialogVisible.value = true
  }

  const onChangedProfile = () => {
    changeAccountImageDialogVisible.value = false
    activeUserStore.updateAccountData()
  }

  const onChangedPassword = () => {
    changePasswordDialogVisible.value = false
  }

  const onChangeDescription = () => {
    EndpointAccess.authorized
      .postChangeDescription(descriptionEditor.value)
      .then(() => {
        descriptionEditor.value = ''
        fetchUserDetails()
        ToastStore.showSuccessMessage('Description changed')
      })
      .catch(ex => {
        ToastStore.showErrorMessage("Couldn't change description: " + ex)
      })
  }

  const onDialogShow = () => {
    console.log('onDialogShow')
    globalStateStore.isLocked = true
  }
  const onDialogHide = () => {
    console.log('onDialogHide')

    globalStateStore.isLocked = false
  }
</script>

<style>
  .image_dialog_container {
    width: 60vw;
    height: fit-content;
  }
  .password_change_dialog_container {
    width: 60vw;
    height: fit-content;
  }
  .remove_account_dialog_container {
    width: 60vw;
    height: fit-content;
  }

  .descriptionContainer {
    width: 100%;
    display: flex;
    justify-items: center;
    align-items: center;
  }
</style>
