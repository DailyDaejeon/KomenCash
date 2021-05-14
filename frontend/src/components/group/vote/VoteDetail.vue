<template>
  <main class="content">
    <div class="container-fluid p-0">
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-sm-block w-100">
          <h3 class="d-inline-block">[{{voteInfo.title}}] 투표 결과</h3>
          <p class="d-inline-block float-right">투표 개최자 : {{voteInfo.studentNickname}}</p>
          <h3 class="d-inline-block">{{voteInfo.content}}</h3>
        </div>
      </div>
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
      <table class="table table-hover text-center">
        <tr>
          <th>No.</th>
          <th>이름</th>
          <th>투표항목</th>
        </tr>
        <tr v-for="(student, index) in studentList" :key="index">
          <td>{{index+1}}</td>
          <td>{{student.studentNickname}}</td>
          <td>{{student.choiceItemContent}}</td>
        </tr>
      </table>
      <!-- <div class="row">
        <div class="vote-result">
          <div class="card-header border" v-for="(item,index) in voteInfo.voteItemResultResponses" :key="item.id">
            <p>{{item.content}}</p>
            <k-progress 
              status="success" 
              type="line"
              :percent="getPercent(index)"
            ></k-progress>
          </div>
        </div>
      </div> -->
    </div>
  </main>
</template>

<script>
import { fetchVoteDetail } from '@/api/vote';
export default {
  props: ['id'],
  data() {
    return {
      lightColor :"#fff0de",
      mainColor :"#e7ab3c",
      voteInfo: {},
      chartData:[],
      chartLabel:[],
      chartBgColor:[],
      studentList:[]
    }
  },
  created() {
    this.fetchVoteInfo();
  },
  methods: {
    async fetchVoteInfo() {
      const res = await fetchVoteDetail(this.id)
      console.log(res)
      this.voteInfo = res.data
      this.chartLabel = []
      this.chartData = []
      this.chartBgColor = []
      this.studentList = res.data.voteAttendFindResponseDtos
      let MAX = [];
      let MAXNUM = 0
      res.data.voteItemResultResponses.forEach((el)=>{
        this.chartLabel.push(el.content)
        this.chartData.push(el.resultCnt)
        if (el.resultCnt > MAXNUM) {
          MAXNUM = el.resultCnt
          MAX = [el.content]
        } else if (el.resultCnt == MAXNUM) {
          MAX.push(el.content)
        } 
      })
      this.chartLabel.forEach((el) => {
        if (MAX.includes(el)) {
          this.chartBgColor.push(this.mainColor)
        } else {
          this.chartBgColor.push(this.lightColor)
        }
      })
      // console.log(this.chartBgColor,'색뭐야')
      this.fetchChart()
    },
    fetchChart() {
      const ctx = this.$refs.barChart.getContext('2d');
      new this.$_Chart(ctx, {
      type: 'bar',
      data: {
        labels: this.chartLabel,
        datasets: [{
          label: "해당투표 명수",
          backgroundColor:this.chartBgColor,
          borderColor: this.mainColor,
          hoverBackgroundColor: this.mainColor,
          hoverBorderColor: this.mainColor,
          data: this.chartData,
          barPercentage: .75,
          categoryPercentage: .5,
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
    },
    getPercent(idx){
      let totalNum = 0;
      for (let index = 0; index < this.voteInfo.voteItemResultResponses.length; index++) {
        totalNum += this.voteInfo.voteItemResultResponses[index].resultCnt;
      }
      console.log("받은 표 : ",this.voteInfo.voteItemResultResponses[idx])
      const pers = (this.voteInfo.voteItemResultResponses[idx].resultCnt / totalNum) * 100;
      console.log(typeof(pers)," ",pers);
      return pers;
    }
  },
}
</script>

<style>

</style>