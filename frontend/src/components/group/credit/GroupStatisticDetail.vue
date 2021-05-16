<template>
  <div>
    
    <h3>제출물 : {{statisticDetail.submitContent}}</h3>
    <div class="mb-3">
      <span class="mr-3">시작일 : {{statisticDetail.startDate.slice(0,10)}}</span>
      <span>종료일 : {{statisticDetail.endDate.slice(0,10)}}</span>
    </div>
    <div class="d-flex flex-row justify-content-end mb-3">
      <button @click="deleteStatis" class="btn btn-danger">제출물 삭제</button>
    </div>
    <table class="text-center table table-hover my-0">
      <thead>
        <tr>
          <th>이름</th>
          <th>제출여부</th>
          <th>제출</th>
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
          <td @click="submitStatis(student)" v-if="!student.submission"><button class="btn btn-main">O</button></td>
          <td v-else>
            <span class="fw-bold">
            제출완료
            </span>
            </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { deleteStatisticItem, fetchStatisticDetail } from '@/api/statistic';
import { acceptStatisDetail } from '@/api/student';
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
    },
    submitStatis(student) {
      const submitData = {
        statisticListId:this.statisticDetail.id,
        studentId:student.studentId
      }
      acceptStatisDetail(submitData).then(()=>{
        this.fetchStatistic()
      })
      // window.location.reload()
    },
    deleteStatis() {
      this.$swal({
        title:'제출물을 삭제하시겠습니까?',
        icon:'warning',
        text:'삭제 시 복구할 수 없습니다.',
        showCancelButton: true,
        cancelButtonText:'취소',
        confirmButtonText:'삭제'
      }).then((res) => {
        if (res.isConfirmed) {
          deleteStatisticItem(this.id).then(()=>{
            this.$swal({
              title:'성공적으로 삭제 됐습니다.',
              icon:'success',
              timer:1500
            })
            window.location.reload()
            // this.$router.go(-1)
          })
        }
      })
    }
  }
};
</script>

<style lang="scss" scoped>

</style>