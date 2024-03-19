<template>

<!-- <div class="promotionScrollPanel"> -->
    <ScrollPanel style="width: 100%; height: fit-content" orientation="horizontal" >
    <div class="promotionScrollPanel">
        <div class="promotionExcerciseCard" v-for="(item,index) in data" v-bind:key="index">
    <ExerciseCard  :excerciseData="item"></ExerciseCard>
</div>
</div>
</ScrollPanel>
<!-- </div> -->

</template>

<script setup lang="ts">
import ExerciseCard from './ExerciseCard.vue';
import type CodeExerciseTab from '../types/CodeExerciseTab';


const data: CodeExerciseTab[]=[
    {
        title: "11111",
        description: "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Iure possimus ea distinctio asperiores repellat qui saepe corrupti reprehenderit fugiat quod ipsam delectus dicta corporis voluptatem aspernatur cupiditate, placeat sunt consequuntur? Possimus voluptatem consequuntur earum repudiandae, ex laudantium rem nesciunt dolor, placeat, fugit officiis? Libero, magni id excepturi voluptate quae est sint maxime fuga quibusdam neque doloribus optio, natus, eius minima quod quidem consequatur obcaecati nisi aperiam unde alias! Dignissimos nostrum, amet accusamus numquam fuga aut sequi repellendus quae facilis id eaque sapiente rem voluptate deserunt molestias impedit expedita commodi blanditiis, qui vero? Reprehenderit rerum quia in minima expedita, eos voluptates repellat perspiciatis quaerat sit laborum ad, odio itaque culpa unde omnis, fuga doloribus ipsa tempora debitis aperiam placeat eligendi? Labore odio voluptate nisi delectus velit fuga quas, quis similique autem cum accusamus, quibusdam dolores repudiandae officia fugiat! Harum saepe eaque dolorum reiciendis velit sint dolores vitae, aspernatur dicta excepturi delectus fugit fugiat aliquid vero doloremque fuga odio, quam cum nesciunt eius quis culpa dolor? Sint eum sequi vel suscipit distinctio impedit odit esse officia perferendis provident odio velit, numquam hic accusamus! Doloremque quo iste ullam quia laborum, ipsum pariatur labore eum repellat quam modi voluptates ipsa quasi dignissimos mollitia dolor perferendis corporis consectetur accusantium ducimus. Repellendus sed, nisi assumenda quae cumque, voluptate aliquam mollitia voluptatibus quas eius id recusandae rem, dolores libero?",
        id: 2
    },
    {
        title: "22222",
        description: "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Iure possimus ea distinctio asperiores repellat qui saepe corrupti reprehenderit fugiat quod ipsam delectus dicta corporis voluptatem aspernatur cupiditate, placeat sunt consequuntur? Possimus voluptatem consequuntur earum repudiandae, ex laudantium rem nesciunt dolor, placeat, fugit officiis? Libero, magni id excepturi voluptate quae est sint maxime fuga quibusdam neque doloribus optio, natus, eius minima quod quidem consequatur obcaecati nisi aperiam unde alias! Dignissimos nostrum, amet accusamus numquam fuga aut sequi repellendus quae facilis id eaque sapiente rem voluptate deserunt molestias impedit expedita commodi blanditiis, qui vero? Reprehenderit rerum quia in minima expedita, eos voluptates repellat perspiciatis quaerat sit laborum ad, odio itaque culpa unde omnis, fuga doloribus ipsa tempora debitis aperiam placeat eligendi? Labore odio voluptate nisi delectus velit fuga quas, quis similique autem cum accusamus, quibusdam dolores repudiandae officia fugiat! Harum saepe eaque dolorum reiciendis velit sint dolores vitae, aspernatur dicta excepturi delectus fugit fugiat aliquid vero doloremque fuga odio, quam cum nesciunt eius quis culpa dolor? Sint eum sequi vel suscipit distinctio impedit odit esse officia perferendis provident odio velit, numquam hic accusamus! Doloremque quo iste ullam quia laborum, ipsum pariatur labore eum repellat quam modi voluptates ipsa quasi dignissimos mollitia dolor perferendis corporis consectetur accusantium ducimus. Repellendus sed, nisi assumenda quae cumque, voluptate aliquam mollitia voluptatibus quas eius id recusandae rem, dolores libero?",
        id: 2  
    },
    {
        title: "22222",
        description: "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Iure possimus ea distinctio asperiores repellat qui saepe corrupti reprehenderit fugiat quod ipsam delectus dicta corporis voluptatem aspernatur cupiditate, placeat sunt consequuntur? Possimus voluptatem consequuntur earum repudiandae, ex laudantium rem nesciunt dolor, placeat, fugit officiis? Libero, magni id excepturi voluptate quae est sint maxime fuga quibusdam neque doloribus optio, natus, eius minima quod quidem consequatur obcaecati nisi aperiam unde alias! Dignissimos nostrum, amet accusamus numquam fuga aut sequi repellendus quae facilis id eaque sapiente rem voluptate deserunt molestias impedit expedita commodi blanditiis, qui vero? Reprehenderit rerum quia in minima expedita, eos voluptates repellat perspiciatis quaerat sit laborum ad, odio itaque culpa unde omnis, fuga doloribus ipsa tempora debitis aperiam placeat eligendi? Labore odio voluptate nisi delectus velit fuga quas, quis similique autem cum accusamus, quibusdam dolores repudiandae officia fugiat! Harum saepe eaque dolorum reiciendis velit sint dolores vitae, aspernatur dicta excepturi delectus fugit fugiat aliquid vero doloremque fuga odio, quam cum nesciunt eius quis culpa dolor? Sint eum sequi vel suscipit distinctio impedit odit esse officia perferendis provident odio velit, numquam hic accusamus! Doloremque quo iste ullam quia laborum, ipsum pariatur labore eum repellat quam modi voluptates ipsa quasi dignissimos mollitia dolor perferendis corporis consectetur accusantium ducimus. Repellendus sed, nisi assumenda quae cumque, voluptate aliquam mollitia voluptatibus quas eius id recusandae rem, dolores libero?",
        id: 2  
    },
    {
        title: "22222",
        description: "Lorem ipsum dolor, sit amet consectetur adipisicing elit. Iure possimus ea distinctio asperiores repellat qui saepe corrupti reprehenderit fugiat quod ipsam delectus dicta corporis voluptatem aspernatur cupiditate, placeat sunt consequuntur? Possimus voluptatem consequuntur earum repudiandae, ex laudantium rem nesciunt dolor, placeat, fugit officiis? Libero, magni id excepturi voluptate quae est sint maxime fuga quibusdam neque doloribus optio, natus, eius minima quod quidem consequatur obcaecati nisi aperiam unde alias! Dignissimos nostrum, amet accusamus numquam fuga aut sequi repellendus quae facilis id eaque sapiente rem voluptate deserunt molestias impedit expedita commodi blanditiis, qui vero? Reprehenderit rerum quia in minima expedita, eos voluptates repellat perspiciatis quaerat sit laborum ad, odio itaque culpa unde omnis, fuga doloribus ipsa tempora debitis aperiam placeat eligendi? Labore odio voluptate nisi delectus velit fuga quas, quis similique autem cum accusamus, quibusdam dolores repudiandae officia fugiat! Harum saepe eaque dolorum reiciendis velit sint dolores vitae, aspernatur dicta excepturi delectus fugit fugiat aliquid vero doloremque fuga odio, quam cum nesciunt eius quis culpa dolor? Sint eum sequi vel suscipit distinctio impedit odit esse officia perferendis provident odio velit, numquam hic accusamus! Doloremque quo iste ullam quia laborum, ipsum pariatur labore eum repellat quam modi voluptates ipsa quasi dignissimos mollitia dolor perferendis corporis consectetur accusantium ducimus. Repellendus sed, nisi assumenda quae cumque, voluptate aliquam mollitia voluptatibus quas eius id recusandae rem, dolores libero?",
        id: 2  
    }
] 

</script>
