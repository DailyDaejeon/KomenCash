<template>
  <div>
    <h3>제출물 : {{statisticDetail.submitContent}}</h3>
    <div class="mb-3">
      <span class="mr-3">시작일 : {{statisticDetail.startDate.slice(0,10)}}</span>
      <span>종료일 : {{statisticDetail.endDate.slice(0,10)}}</span>
    </div>
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>이름</th>
          <th>제출여부</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(student,index) in studentList" :key="index">
          <td>{{student.studentNickname}}</td>
          <td v-if="student.submission" >
            <span class="badge bg-success">
            제출
            </span>
          </td>
          <td v-else >
            <span class="badge bg-danger">
            미제출
            </span>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { fetchStatisticDetail } from '@/api/statistic';
export default {
  props:['id','propsData'],
  data() {
    return {
      statisticDetail:[],
      studentList:[]
    }
  },
  created() {
    this.fetchStatistic()
  },
  methods:{
    async fetchStatistic() {
      const res = await fetchStatisticDetail(this.id)
      this.statisticDetail = res.data
      this.studentList = res.data.statisticListDetailFindResponseDtos
    }
  }
};
</script>

<style lang="scss" scoped>

</style>