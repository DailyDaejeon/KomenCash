<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">그룹 신용등급 리스트</h1>
      <div class="row">
        <div class="col-12">
          <div class="card flex-fill w-100">
            <div class="card-body">
              <div class="chart">
                <canvas ref="barChart" id="barChart"></canvas>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>

export default {
  props:['propsdata'],
  data() {
    return {
      mainColor :"#e7ab3c",
      chartData:[],
      barChart:''
    }
  },
  created() {
    this.fetchData()

  },
  mounted() {
    if (this.propsdata.length) {
    this.fetchChart()
    }
  },
  methods: {
    fetchData() {
      this.chartData = this.propsdata
      console.log(this.chartData)
    },
    fetchChart() {
      const ctx = this.$refs.barChart.getContext('2d');
      const myBarChart =  new this.$_Chart(ctx, {
      type: 'bar',
      data: {
        labels: ["1등급", "2등급", "3등급", "4등급", "5등급", "6등급", "7등급", "8등급", "9등급", "10등급"],
        datasets: [{
          label: "해당등급 명수",
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(255, 99, 132, 0.2)',
            'rgba(255, 159, 64, 0.2)',
            'rgba(255, 159, 64, 0.2)',
            'rgba(255, 205, 86, 0.2)',
            'rgba(255, 205, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(201, 203, 207, 0.2)',
            'rgba(201, 203, 207, 0.2)',
          ],
          borderColor: this.mainColor,
          hoverBackgroundColor: this.mainColor,
          hoverBorderColor: this.mainColor,
          data: this.propsdata,
          barPercentage: .75,
          categoryPercentage: .5
        }]
      },
      options: {
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        scales: {
          yAxes: [{
            gridLines: {
              display: false
            },
            stacked: false,
            ticks: {
              stepSize: 1,
              min:0
            }
          }],
          xAxes: [{
            stacked: false,
            gridLines: {
              color: "transparent"
            }
          }]
        }
      }
    });
    this.barChart = myBarChart
    }
  }
  
}
</script>

<style>

</style>