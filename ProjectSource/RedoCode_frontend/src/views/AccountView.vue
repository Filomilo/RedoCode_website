<template>
  <main>
    <div class="AccountInfoPanel">
      <div class="ImageContainer">
        <img :src="imgURL" class="ProfileImgStyle" />
      </div>
      <div class="editButtonContinaer">
        <Button class="IconEditImg" @click="onChangeProfilePic">
          <IconEdit />
        </Button>
      </div>
      <div class="NickContainer">{{ activeUserStore.acoountInfo.nick.value }}</div>
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
            :class="screenSelected === Panels.STATISTIC ? 'bottomLine selected' : 'bottomLine'"
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
          <div :class="screenSelected === Panels.SETTINGS ? 'bottomLine selected' : 'bottomLine'" />
        </Button>
      </div>
      <div class="SettingContentPanel">
        <div v-if="screenSelected === Panels.STATISTIC">
          <div>
            <div class="chartTitle">Usage of langauges</div>

            <Chart type="doughnut" :data="data" class="chartStyle" />
          </div>
          <div>
            <div class="chartTitle">Execises done last week</div>
            <Chart type="bar" :data="barData" class="chartStyle" />
          </div>
        </div>

        <div v-if="screenSelected === Panels.SETTINGS">
          <div class="SettingContentRow">E-mail: e****@gmail.com</div>
          <div class="SettingContentRow">
            Password: *******
            <Button class="changeButton" @click="onChangePassword"> change </Button>
          </div>
          <div class="SettingContentRow center">
            <Button class="removeAccountButton" @click="onRemoveAccount"> Remove account </Button>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref, type Ref } from 'vue'
import IconEdit from '../assets/icons/IconEdit.vue'
import { useActiveUserStore } from '../stores/ActiveUserStore'
import router from '@/router'
import { useToastStore } from '@/stores/ToastStore'
const activeUserStore = useActiveUserStore()
const ToastStore = useToastStore()
const imgURL = ref('https://i.imgur.com/Z6fpYPD.png')

const data = {
  labels: ['c++', 'JavaScrip', 'Python', 'Java'],
  datasets: [
    {
      label: 'excercises',
      data: [540, 325, 702, 620],
      // backgroundColor: ['rgba(249, 115, 22, 0.2)', 'rgba(6, 182, 212, 0.2)', 'rgb(107, 114, 128, 0.2)', 'rgba(139, 92, 246 0.2)'],
      // borderColor: ['rgb(249, 115, 22)', 'rgb(6, 182, 212)', 'rgb(107, 114, 128)', 'rgb(139, 92, 246)'],
      borderWidth: 1
    }
  ]
}
const curDate = new Date()
let dateOffset = 24 * 60 * 60 * 1000 * 5
const barData = {
  labels: ['01-01', '01-02', '01-03', '01-04', '01-05', '01-06'],
  datasets: [
    {
      label: 'exercises done',
      data: [1, 5, 8, 3, 7, 4],
      //   borderColor: ['rgb(249, 115, 22)', 'rgb(6, 182, 212)', 'rgb(107, 114, 128)', 'rgb(139, 92, 246)'],
      borderWidth: 1
    }
  ]
}

enum Panels {
  STATISTIC,
  SETTINGS
}

const screenSelected: Ref<Panels> = ref(Panels.SETTINGS)
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
