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
    <div class="card">
    <div class="card-body">
      <div class="flex-fill">
        <h5 class="card-title mb-0">Group Member Credit Info</h5>
        <div class="card-body">
          <div class="row">
            <div class="col">
              <table class="table table-hover text-center">
                <tr>
                  <th>No.</th>
                  <th>신용등급</th>
                  <th>신용점수</th>
                  <th>닉네임</th>
                </tr>
                <tr v-for="(student, index) in memberData" :key="index">
                  <td>{{index+1}}</td>
                  <td>{{student.grade}}</td>
                  <td>{{priceToString(student.creditPoint)}}</td>
                  <td>{{student.studentNickname}}</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </main>
</template>

<script>
import { fetchMemeberCreditList } from '@/api/credit'
import { mapState } from 'vuex'

export default {
  data() {
    return {
      creditList : {
        "1":[],
        "2":[],
        "3":[],
        "4":[],
        "5":[],
        "6":[],
        "7":[],
        "8":[],
        "9":[],
        "10":[],
      },
      mainColor :"#e7ab3c",
      chartData:[],
      memberData:[],
      barChart:''
    }
  },
  created() {
    this.fetchCredit()
  },
  mounted() {
    if (this.propsdata.length) {
    this.fetchChart()
    }
  },
  computed: {
    ...mapState({
      groupInfo: state=>state.group.groupInfo
    })
  },
  methods: {
    async fetchCredit() {
      const res = await fetchMemeberCreditList(this.groupInfo.id)
      this.memberData = res.data

      this.memberData.forEach(element => {
        this.creditList[String(element.grade)].push(element)
      });
      for (let i = 1; i < 11; i++) {
        const element = this.creditList[String(i)].length;
        this.chartData.push(element)
        
      }
      this.fetchChart()
    },
    priceToString(price) {
      return price.toLocaleString('ko-KR')
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
          data: this.chartData,
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