<template>
    <b-navbar toggleable="sm" type="dark" variant="dark">
        <b-navbar-nav>
                <b-navbar-brand>RedoCode</b-navbar-brand>
         
        <b-nav-item >
     
        </b-nav-item>
        <b-collapse id="nav-text-collapse" is-nav>
            <b-navbar-nav>
                <router-link to="/home" class="nav-link">
                    <b-nav-text>Home</b-nav-text>
                    </router-link>
            </b-navbar-nav>
          </b-collapse>
    

          <b-collapse id="nav-text-collapse" is-nav>
            <b-navbar-nav>
                <router-link to="/Exercises" class="nav-link">
                    <b-nav-text>Excersices</b-nav-text>
                    </router-link>
            </b-navbar-nav>
          </b-collapse>
        </b-navbar-nav>
    </b-navbar>
 
</template>


<script setup lang="ts">
</script>