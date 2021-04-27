<template>
  <main class="content">
    <div class="mb-3">
      <button class="mr-3 btn btn-main"  @click="addData">데이터추가</button>
      <button class="btn btn-main" @click="addHintData">힌트추가</button>
    </div>
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">주식이름</h1>
      <div class="row">
        <div class="col-12">
          <div class="card flex-fill w-100">
            <div class="card-body">
              <div class="chart">
                <canvas ref="lineChart" id="lineChart"></canvas>
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
  data () {
    return {
      myChart : ''
    }
  },
  mounted() {
    const ctx = this.$refs.lineChart.getContext('2d');
    const myLineChart = new this.$_Chart(ctx, {
      type: 'line',
      data: {
        // labels: [],
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        datasets: [{
          label: "주식종목 ($)",
          fill: true,
          backgroundColor: "transparent",
          borderColor: "#e7ab3c",
          data: [2115, 1562, 1584, 1892, 1487, 2223, 2966, 2448, 2905, 3838, 2917, 3327]
          // data: []
        }]
      },
      options: {
        maintainAspectRatio: false,
        legend: {
          display: false
        },
        tooltips: {
          intersect: false
        },
        hover: {
          intersect: true
        },
        plugins: {
          filler: {
            propagate: false
          }
        },
        scales: {
          xAxes: [{
            reverse: true,
            gridLines: {
              color: "rgba(0,0,0,0.05)"
            }
          }],
          yAxes: [{
            ticks: {
              stepSize: 500
            },
            display: true,
            borderDash: [5, 5],
            gridLines: {
              color: "rgba(0,0,0,0)",
              fontColor: "#fff"
            }
          }]
        }
      }
    }); 
    this.myChart = myLineChart
  },
  methods: {
    addData() {
      this.$swal({
        title: '오늘의 주가를 입력해주세요.',
        inputPlaceholder: '1000',
        input: 'text'        
      }).then((result) => {
      if (result.value) {
        const answers = JSON.stringify(result.value)
        this.$swal({
          title: '주식이 생성됐어요!',
          html: `
            Your answers:
            <pre><code>${answers}</code></pre>
          `,
          confirmButtonText: 'Lovely!'
        })
        const today = new Date();
        const year = today.getFullYear(); // 년도
        const month = today.getMonth() + 1;  // 월
        const date = today.getDate();  // 날짜
        this.myChart.data.labels.push(year + '.' + month + '.' + date);
        this.myChart.data.datasets[0].data.push(Number(result.value));
        this.myChart.update();
        console.log(this.myChart)
      }
      })
    },
    addHintData() {
      this.$swal({
        title: '주식 힌트를 입력해주세요.',
        inputPlaceholder: '오늘 저녁에 배드민턴을 칩니다.',
        input: 'text'        
      }).then((result) => {
      if (result.value) {
        const answers = JSON.stringify(result.value)
        this.$swal({
          title: '주식 힌트가 갱신됐어요!',
          html: `
            Your answers:
            <pre><code>${answers}</code></pre>
          `,
          confirmButtonText: 'Lovely!'
        })
      }
      })
    }
  }
}
</script>

<style>

</style>