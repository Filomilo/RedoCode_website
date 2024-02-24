<template>
  <EasyDataTable
    show-index
    v-model:items-selected="itemsSelected"
    buttons-pagination
    :headers="headers"
    :items="items"
    :loading="loading"
  >
    <template #loading>
      <img
        src="https://thumbs.gfycat.com/AngelicYellowIberianmole.webp"
        style="width: 60px; height: 100px"
      />
    </template>
    <template #item-player="{ player, avator, page }">
      <div class="player-wrapper">
        <img class="avator" :src="avator" alt="" />
        <a target="_blank" :href="page">{{ player }}</a>
      </div>
    </template>
    <template #expand="item">
      test
    </template>
    <template #item-team="{ teamName, teamUrl }">
      <a target="_blank" :href="teamUrl">{{ teamName }}</a>
    </template>
    <template #item-operation="item">
      <div class="operation-wrapper">
        <img
          src="./images/delete.png"
          class="operation-icon"
          @click="deleteItem(item)"
        />
        <img
          src="./images/edit.png"
          class="operation-icon"
          @click="editItem(item)"
        />
      </div>
    </template>
  </EasyDataTable>

  <div class="edit-item" v-if="isEditing">
    height:<input type="text" v-model="editingItem.height" />
    <br />
    weight:<input type="text" v-model="editingItem.weight" />
    <br />
    <button @click="submitEdit">ok</button>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, reactive } from "vue";
import { Header, Item } from "vue3-easy-data-table";

export default defineComponent({
  components: {},
  setup() {
    const headers: Header[] = [
      { text: "PLAYER", value: "player" },
      { text: "TEAM", value: "team" },
      { text: "NUMBER", value: "number" },
      { text: "POSITION", value: "position" },
      { text: "HEIGHT", value: "info.height" },
      { text: "WEIGHT (lbs)", value: "info.weight", sortable: true },
      { text: "LAST ATTENDED", value: "lastAttended" },
      { text: "COUNTRY", value: "country" },
      { text: "Operation", value: "operation" },
    ];
    const itemsSelected: Item[] = ref([]);
    const items: Item[] = ref([]);
    const loading = ref(true);
    setTimeout(() => {
      loading.value = false;
      items.value = [
        {
          id: 1,
          player: "Stephen Curry",
          page: "https://www.nba.com/player/201939/stephen-curry",
          avator:
            "https://github.com/HC200ok/vue3-easy-data-table/blob/main/images/nba/Stephen.png?raw=true",
          teamName: "GSW",
          teamUrl: "https://www.nba.com/team/1610612744/warriors",
          number: 30,
          position: "G",
          info: {
            height: "6-2",
            weight: 185,
          },
          lastAttended: "Davidson",
          country: "USA",
        },
        {
          id: 2,
          player: "Lebron James",
          page: "https://www.nba.com/player/2544/lebron-james",
          avator:
            "https://github.com/HC200ok/vue3-easy-data-table/blob/main/images/nba/lebron.png?raw=true",
          teamName: "LAL",
          teamUrl: "https://www.nba.com/team/1610612747/lakers",
          number: 6,
          position: "F",
          info: {
            height: "6-9",
            weight: 250,
          },
          lastAttended: "St. Vincent-St. Mary HS (OH)",
          country: "USA",
        },
        {
          id: 3,
          player: "Kevin Durant",
          page: "https://www.nba.com/player/201142/kevin-durant",
          avator:
            "https://github.com/HC200ok/vue3-easy-data-table/blob/main/images/nba/Kevin.png?raw=true",
          teamName: "BKN",
          teamUrl: "https://www.nba.com/team/1610612751/nets",
          number: 7,
          position: "F",
          info: {
            height: "6-10",
            weight: 240,
          },
          lastAttended: "Texas-Austin",
          country: "USA",
        },
        {
          id: 4,
          player: "Giannis Antetokounmpo",
          page: "https://www.nba.com/player/203507/giannis-antetokounmpo",
          avator:
            "https://github.com/HC200ok/vue3-easy-data-table/blob/main/images/nba/Giannis.png?raw=true",
          teamName: "MIL",
          teamUrl: "https://www.nba.com/team/1610612749/bucks",
          number: 34,
          position: "F",
          info: {
            height: "6-11",
            weight: 242,
          },
          lastAttended: "Filathlitikos",
          country: "Greece",
        },
      ];
    }, 2000);

    const isEditing = ref(false);
    const editingItem = reactive({
      height: "",
      weight: "",
      id: 0,
    });

    const deleteItem = (val: Item) => {
      items.value = items.value.filter((item) => item.id !== val.id);
    };

    const editItem = (val: Item) => {
      isEditing.value = true;
      const { height, weight, id } = val;
      editingItem.height = height;
      editingItem.weight = weight;
      editingItem.id = id;
    };

    const submitEdit = () => {
      isEditing.value = false;
      const item = items.value.find((item) => item.id === editingItem.id);
      item.height = editingItem.height;
      item.weight = editingItem.weight;
    };

    return {
      loading,
      submitEdit,
      headers,
      items,
      deleteItem,
      editItem,
      editingItem,
      isEditing,
    };
  },
});
</script>

<style>
.operation-wrapper .operation-icon {
  width: 20px;
  cursor: pointer;
}
.player-wrapper {
  padding: 5px;
  display: flex;
  align-items: center;
  justify-items: center;
}
.avator {
  margin-right: 10px;
  display: inline-block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
  box-shadow: inset 0 2px 4px 0 rgb(0 0 0 / 10%);
}
</style>
