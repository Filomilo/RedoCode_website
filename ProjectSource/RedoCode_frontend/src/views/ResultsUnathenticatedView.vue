<template>
  <main >
    <div class="MainContainer">
    <div class="MessageContainer">
      Unfortunately in order to view detailed results you need to be logged in
      <Button  id="GotToLogin"
      @click="onGoToLogin"
      >
        Go to login page
      </Button >
    </div>
  </div>
  </main>

</template>

<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
const onGoToLogin=()=>{
router.replace("Home")
}


</script>

<style>

.MainContainer{
  width: 100%;
    display: flex;
  align-items: center;
  align-content: center;
  justify-content: center;
  height: 100%;
}

.MessageContainer{

width: 70%;
background-color: rgb(20, 20, 20);
border-radius: 3rem;
border: 0.11rem solid var(--primary);
display: flex;
align-items: center;
flex-direction: column;
font-size: large;
height: fit-content;
  padding: 3rem;

}

#GotToLogin{
  background-color: var(--primary);
  border: 0;
  color: white;
  border-radius: 0.5rem;
  widows: 10%;
  margin: 3rem;
}

</style>
