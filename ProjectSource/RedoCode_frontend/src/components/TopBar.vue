<template>
  <nav class="">
    <div class="TopBarContainer">
      <!-- {{ route }} -->
      <div class="TopBarList">
        <router-link to="/Home" :class="`TopBarItemContainer ${isHomeSelected ? 'selected' : ''}`" id="switch-home"
        >
          <IconHome class="TopBarItemIcon" />
          <label class="TopBarItemTitle">Home</label>
        </router-link>

        <router-link
          to="/PlayGround"
         :class="`TopBarItemContainer ${isPlaygroundSelected ? 'selected' : ''}`"
          id="switch-playground"
        >
          <IconPlayground class="TopBarItemIcon" />
          <label class="TopBarItemTitle">PlayGorund</label>
        </router-link>

        <router-link
          to="/Exercises"
         :class="`TopBarItemContainer ${isExercisesSelected ? 'selected' : ''}`"
          id="switch-exercises"
        >
          <IconExcerisce class="TopBarItemIcon" />
          <label class="TopBarItemTitle">Exercises</label>
        </router-link>

        <router-link
          to="/Account"
         :class="`TopBarItemContainer ${isAccountSelected ? 'selected' : ''}`"
          id="switch-account"
          v-if="activeUserStore.isLogged"
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
import { computed } from 'vue'
import { useRoute } from 'vue-router'

  const activeUserStore = useActiveUserStore()
  const route=useRoute();
  const isHomeSelected=computed(()=>{
    return route.fullPath.includes("/Home");
  })

  const isPlaygroundSelected=computed(()=>{
    return route.fullPath.includes("/PlayGround");
  })

  const isExercisesSelected=computed(()=>{
    return route.fullPath.includes("/Exercises");
  })
  
  const isAccountSelected=computed(()=>{
    return route.fullPath.includes("/Account");
  })
</script>

<style lang="css">

</style>