<template>
  <main>
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
  const activeUserStore = useActiveUserStore()
  const ToastStore = useToastStore()
  const imgURL = computed(() => {
    return profilePicImageResolve(activeUserStore.accountInfo.profilePicture)
  })

  enum Panels {
    STATISTIC,
    SETTINGS,
  }

  const screenSelected: Ref<Panels> = ref(Panels.STATISTIC)
  const setPanel = (type: Panels) => {
    console.log('select')
    screenSelected.value = type
  }

  const onLogOutButton = () => {
    activeUserStore.logout()
    router.push('/home')
  }

  const onChangePassword = () => {
    ToastStore.featureNotImplemented()
  }

  const onRemoveAccount = () => {
    ToastStore.featureNotImplemented()
  }

  const onChangeProfilePic = () => {
    ToastStore.featureNotImplemented()
  }
</script>

<style></style>
