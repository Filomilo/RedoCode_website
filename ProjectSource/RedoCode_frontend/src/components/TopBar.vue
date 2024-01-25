<template>


    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <router-link to="/Home" class="nav-link">
        <a class="navbar-brand" href="#">
         
            <img src="../assets/logo/logo_noBackground.png" height="30" alt="" >
      
        </a>
    </router-link>

          <div class="collapse navbar-collapse " id="navbarNav">
            <ul class="navbar-nav topbar-navigation">
              <li class="nav-item ">
                <router-link to="/Exercises" class="nav-link">
                    <b-nav-text>Excersices</b-nav-text>
                    </router-link>              </li>
              <li class="nav-item">
                <router-link to="/Exercises" class="nav-link">
                    <b-nav-text>Excersices</b-nav-text>
                    </router-link> 
              </li>
  
            </ul>
          </div>

    </nav>




          

</template>


<script setup lang="ts">
</script>