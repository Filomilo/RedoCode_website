<template>
  <main>
    <Dialog 
          @after-hide="onDialogHide"
      @show="onDialogShow"
    class="image_dialog_container" v-model:visible="changeAccountImageDialogVisible" header="Change account image" id="Change_image_dialog">
      <ChangeAccountImageDialog 
      @ChangedProfile="onChangedProfile"

      />

    </Dialog>

    <Dialog 
          @after-hide="onDialogHide"
      @show="onDialogShow"
    class="password_change_dialog_container" v-model:visible="changePasswordDialogVisible" header="Change password" id="Change_password_dialog">
      <ChangePasswordDialog 
      @ChangedPassword="onChangedPassword"

      />

    </Dialog>


    <Dialog @after-hide="onDialogHide" @show="onDialogShow" class="removeAccountDialogVisible" v-model:visible="removeAccountDialogVisible" header="Remove account" id="Remove_account_dialog">
<RemoveAccountDialog
/>

</Dialog>

    
    <div class="AccountInfoPanel">
      <div class="ImageContainer">
        <img :src="imgURL" class="ProfileImgStyle" />
      </div>
      <div class="editButtonContainer">
        <Button class="IconEditImg" @click="onChangeProfilePic">
          <IconEdit />
        </Button>
      </div>
      <div class="NickContainer">
        {{ activeUserStore.accountInfo.nickname }}
      </div>
      <div class="DescriptionContainer">Account description</div>
      <div class="LogoutButtonContainer">
        <Button @click="onLogOutButton" id="logout"> Logout </Button>
      </div>
    </div>
    <div class="BottomPanel">
      <div class="SettingSidePanel">
        <Button
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
          <div class="SettingContentRow">E-mail: e****@gmail.com</div>
          <div class="SettingContentRow">
            Password: *******
            <Button class="changeButton" @click="onChangePassword">
              change
            </Button>
          </div>
          <div class="SettingContentRow center">
            <Button class="removeAccountButton" @click="onRemoveAccount">
              Remove account
            </Button>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
  import { ref, type Ref, computed } from 'vue'
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
  const activeUserStore = useActiveUserStore()
  const globalStateStore=useGlobalStateStore();
  const ToastStore = useToastStore()

  const changeAccountImageDialogVisible=ref(false);
  const changePasswordDialogVisible=ref(false);
  const removeAccountDialogVisible=ref(true);

  const imgURL = computed(() => {
    return profilePicImageResolve(activeUserStore.accountInfo.profilePicture)
  })



  enum Panels {
    STATISTIC,
    SETTINGS,
  }

  const screenSelected: Ref<Panels> = ref(Panels.SETTINGS)
  const setPanel = (type: Panels) => {
    console.log('select')
    screenSelected.value = type
  }

  const onLogOutButton = () => {
    activeUserStore.logout()
    location.reload();
  }

  const onChangePassword = () => {
    changePasswordDialogVisible.value=true;
  }

  const onRemoveAccount = () => {
    ToastStore.featureNotImplemented()
  }

  const onChangeProfilePic = () => {
    changeAccountImageDialogVisible.value=true;
  }

  const onChangedProfile=()=>{
    changeAccountImageDialogVisible.value=false;
    activeUserStore.updateAccountData();
  }

  const onChangedPassword=()=>{
    changePasswordDialogVisible.value=false;

  }


  const onDialogShow=()=>{
    console.log("onDialogShow")
    globalStateStore.isLocked=true;
  }
  const onDialogHide=()=>{
    console.log("onDialogHide")

    globalStateStore.isLocked=false;
  }




</script>

<style>
.image_dialog_container{
  width: 60vw;
  height: fit-content;
}
.password_change_dialog_container{
  width: 60vw;
  height: fit-content;
}
.remove_account_dialog_container{
  width: 60vw;
  height: fit-content;
}

</style>
