<template>
  <main class="content">
    <div class="mb-3">
      <button class="mr-3 btn btn-main"  @click="modifyStockName">이름변경</button>
      <button class="mr-3 btn btn-main"  @click="addData">데이터추가</button>
      <button class="mr-3 btn btn-main" @click="addHintData">힌트변경</button>
      <button class="btn btn-danger" @click="removeStock">주식삭제</button>
    </div>
    <div class="container-fluid p-0">
      <div class="d-flex justify-content-between align-items-center">
      <div>
      <p class="h1 mb-3 fw-bold">{{stockName}}</p>
      <p class="h2">오늘의 HINT : {{stockHint}}</p>
      </div>
      <div class="text-center d-flex mt-1 mb-3 align-items-baseline">
        <span :class="arrowColor" class="display-3">{{stockData.price}} </span>
        <div>
        <span class="h1 mr-3" :class="arrowColor"> <i :class="arrow"></i></span>
        <span :class="arrowColor" class="h1 mr-3">{{changeMoney}}</span>
        <span :class="arrowColor" class="h1">({{money}}%)</span>
        </div>
      </div>
      </div>
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
import { addStockData, modifyStock, fetchStockHistory, deleteStock } from '@/api/stock';
import { mapState } from 'vuex';

export default {
  props:['propsdata','stockId','stockData'],
  data () {
    return {
      changeMoney:0,
      money : 0,
      arrow:"",
      arrowColor:"",
      downArrow:"fas fa-sort-down",
      upArrow:"fas fa-sort-up",
      same:"fas fa-minus",
      myChart : "",
      stockHint:"",
      stockDataList:[],
      labelList:[],
      stockName:'',
      mActive:false
    }
  },
  created() {
    this.fetchStockMoney()
    this.fetchMoney()

  },
  mounted() {
    this.stockName = this.stockData.name;
    this.stockHint = this.stockData.hint;
    const ctx = this.$refs.lineChart.getContext('2d');
    const myLineChart = new this.$_Chart(ctx, {
      type: 'line',
      data: {
        labels: this.labelList,
        // labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        datasets: [{
          label: "주식종목 ($)",
          fill: true,
          backgroundColor: "transparent",
          borderColor: "#e7ab3c",
          // data: [2115, 1562, 1584, 1892, 1487, 2223, 2966, 2448, 2905, 3838, 2917, 3327]
          data: this.stockDataList
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
  computed:{
    ...mapState({
      groupInfo: state => state.group.groupInfo
    })
  },
  methods: {
    async fetchStockMoney() {
      const res = await fetchStockHistory(this.stockData.id)
      res.data.forEach(element => {
        this.stockDataList.push(Number(element.price))
        this.labelList.push(element.createdDate)
      });
    },
    fetchMoney() {
      this.changeMoney = Math.abs(this.stockData.price - this.stockData.prePrice)
      this.money = (((this.stockData.price - this.stockData.prePrice)/this.stockData.prePrice)*100).toFixed(2)
      if (this.money < 0 ) {
        this.arrow = this.downArrow
        this.arrowColor = "text-primary"
      } else if (this.money === 0) {
        this.arrow = this.same
        this.arrowColor = "text-secondary"
      } else {
        this.arrow = this.upArrow
        this.arrowColor = "text-danger"
      }
    },
    addData() {
      this.$swal({
        title: '오늘의 주가를 입력해주세요.',
        inputPlaceholder: '1000',
        input: 'text'        
      }).then((result) => {
      if (result.value) {
        const data = {
          price: result.value,
          stockId: this.stockId
        }
        addStockData(data)
        this.$swal({
          title: '오늘의 주가가 추가됐어요!',
          info:"success",
          confirmButtonText: 'Lovely!'
        })
        const today = new Date();
        const year = today.getFullYear(); // 년도
        const month = today.getMonth() + 1;  // 월
        const date = today.getDate();  // 날짜
        this.myChart.data.labels.push(year + '.' + month + '.' + date);
        this.myChart.data.datasets[0].data.push(Number(result.value));
        this.myChart.update();
        // console.log(this.myChart)
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
        const answers = {
          groupId: this.groupInfo.id,
          hint: result.value,
          id: this.stockId,
          name: this.stockData.name
        }
        modifyStock(answers).then(()=>{
          this.$swal({
            title: '주식 힌트가 갱신됐어요!',
            icon:"success",
            confirmButtonText: 'Lovely!'
          })
        this.stockHint = result.value

        }).catch(()=>{
          this.$swal({
            title: '주식 힌트를 다시입력해주세요!',
            icon:"info",
            confirmButtonText: 'Lovely!'
          })
        })
      }
      })
    },
    modifyStockName() {
      this.$swal({
        title: '이름을 변경하시겠습니까?',
        input:'text',
        inputPlaceholder:this.stockName,
        confirmButtonText: '수정',
        showCancelButton:true,
        cancelButtonText:'취소'
      }).then((result) => {
        if (result.value) {
          const stock = {
            groupId: this.groupInfo.id,
            hint: this.stockHint,
            id: this.stockData.id,
            name: result.value
          }
          modifyStock(stock)
          this.$swal({
            title: '주식이 수정됐습니다.',
            icon:"success",
            confirmButtonText: 'Lovely!'
          })
          this.stockName = result.value
        }
      })
    },
    removeStock() {
      this.$swal({
        title: '삭제하시겠습니까?',
        text:'해당 주식을 복구할 수 없습니다.',
        info:"warning",
        confirmButtonText: '삭제',
        showCancelButton:true,
        cancelButtonText:'취소'
      }).then((result) => {
        if (result.value) {
          deleteStock(this.stockData.id)
          this.$swal({
            title: '주식이 삭제됐습니다.',
            icon:"success",
            confirmButtonText: 'Lovely!'
          })
          this.$router.push({name:"StockPage"})
        }
      })
    }
  }
}
</script>

<style>

</style>