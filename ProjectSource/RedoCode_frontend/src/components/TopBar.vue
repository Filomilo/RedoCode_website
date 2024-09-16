<template>
  <nav class="">
    <div class="TopBarContainer">
      <div class="TopBarList">
        <router-link to="/Home" class="TopBarItemContainer" id="switch-home">
          <IconHome class="TopBarItemIcon" />
          <label class="TopBarItemTitle">Home</label>
        </router-link>

        <router-link
          to="/PlayGround"
          class="TopBarItemContainer"
          id="switch-playground"
        >
          <IconPlayground class="TopBarItemIcon" />
          <label class="TopBarItemTitle">PlayGorund</label>
        </router-link>

        <router-link
          to="/Exercises"
          class="TopBarItemContainer"
          id="switch-exercises"
        >
          <IconExcerisce class="TopBarItemIcon" />
          <label class="TopBarItemTitle">Exercises</label>
        </router-link>

        <router-link
          to="/Account"
          class="TopBarItemContainer"
          id="switch-account"
          v-if="activeUserStore.authController.isLogged"
        >
          <IconAccount class="TopBarItemIcon" />
          <label class="TopBarItemTitle">My account</label>
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
  import '../assets/icons/IconHome.vue'
  import IconPlayground from '@/assets/icons/IconPlayground.vue'
  import IconHome from '@/assets/icons/IconHome.vue'
  import IconExcerisce from '@/assets/icons/IconExcerisce.vue'
  import IconAccount from '@/assets/icons/IconAccount.vue'
  import { useActiveUserStore } from '../stores/ActiveUserStore'

  const activeUserStore = useActiveUserStore()
</script>
