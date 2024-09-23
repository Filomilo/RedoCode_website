<template>
  <main>
    {{ pieChartData }}
    <div class="titleContainer">
      <label class="title"> Amount of language used </label>
    </div>
    <div class="statContainer">
      <Doughnut
        :data="pieChartData"
        :options="pieCharOption"
        v-if="pieChartData !== undefined"
      />
      <div v-else class="noDataContainer" id="NoLanguageData">
        No data, you need to first solve some exercises
      </div>
    </div>
    <div class="titleContainer">
      <label class="title"> Lately done exercises </label>
    </div>
    <div class="statContainer barContainer" v-if="latelyDoneData !== undefined">
      <Bar :data="latelyDoneData" :options="barOptions" />
    </div>
  </main>
</template>

<script lang="ts" setup>
  import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js'
  import { Doughnut, Bar } from 'vue-chartjs'
  import StatisticMessage from '@/types/ApiMessages/StatisticMessage'
  import { computed, ref, Ref, onMounted } from 'vue'
  import EndpointAccess from '@/controllers/EndpointsAccess'
  import { BarElement, CategoryScale, LinearScale } from 'chart.js'
  const StatData: Ref<StatisticMessage | undefined> = ref()

  const pieChartData = computed(() => {
    if (StatData.value === undefined || StatData.value.languageUse.length === 0)
      return undefined

    return {
      labels: StatData.value?.languageUse.map(x => x.name),
      datasets: [
        {
          label: 'Amount of exercises done',

          backgroundColor: ['#41B883', '#E46651', '#00D8FF', '#DD1B16'],
          data: StatData.value?.languageUse.map(x => x.amount),
        },
      ],
    }
  })

  const latelyDoneData = computed(() => {
    if (StatData.value === undefined) return undefined

    return {
      labels: StatData.value.amountOfLatelyDone.map(x =>
        new Date(x.date).toDateString()
      ),
      datasets: [
        {
          label: 'Amount of exercises done',

          backgroundColor: '#a550b3',
          data: StatData.value.amountOfLatelyDone.map(x => x.amount),
        },
      ],
    }
  })

  ChartJS.register(
    ArcElement,
    Tooltip,
    Legend,
    BarElement,
    CategoryScale,
    LinearScale
  )
  // ChartJS.register( BarElement, Title, Tooltip, Legend)

  const barOptions = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        ticks: {
          color: 'white',
        },
      },
      y: {
        ticks: {
          color: 'white',
        },
      },
    },
    plugins: {
      legend: {
        labels: {
          color: 'white',
          font: {
            size: 14,
          },
        },
      },
    },
  }

  const pieCharOption = {
    ...barOptions,
    scales: {
      x: {
        display: false, // Hides the X axis
      },
      y: {
        display: false, // Hides the Y axis
      },
    },
  }

  onMounted(() => {
    EndpointAccess.authorized.getUserStatisticData().then(result => {
      StatData.value = result
    })
  })
</script>

<style lang="css">
  .statContainer {
    width: 100%;
    height: fit-content;
    color: white;
    margin-bottom: 5rem;
    height: 20rem;
  }
  .statContainer * {
    color: white;
  }

  .noDataContainer {
    display: flex;
    justify-content: center;
    align-items: center;
    justify-items: center;
    align-content: center;
    height: 100%;
  }

  .titleContainer {
    width: 100%;
    align-items: center;
    justify-content: center;
    align-content: center;
    justify-items: center;
    margin: 1rem;
    height: fit-content;
    display: flex;
  }

  .title {
    font-size: 2rem;
    text-align: center;
    align-self: center;
  }
</style>
