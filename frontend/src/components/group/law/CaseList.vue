<template>
  <div>
    <table class="text-center table table-hover my-0">
      <thead>
        <tr>
          <th>제출자</th>
          <th>이름</th>
          <th>내용</th>
          <th>벌금({{groupInfo.monetaryUnitName}})</th>
          <th>상세보기</th>
        </tr>
      </thead>
      <tbody v-if="caseList.length">
        <tr v-for="(student,index) in caseList" :key="index">
          <template v-if="student.accept !== 'before_confirm'">
          <td>{{student.policeNickname}}</td>
          <td>{{student.studentNickname}}</td>
          <td>{{student.fine}}</td>
          <td><button class="btn btn-main" @click="alertDetail(student)">자세히</button></td>
          </template>
        </tr>
      </tbody>
    </table>
    <p class="h4 mt-2 text-center" v-if="caseList.length == 0">
      아직 접수된 경위서가 없습니다.
    </p>
  </div>
</template>

<script>
import { fetchCaseDetail, fetchCaseList } from '@/api/case';
import { mapState } from 'vuex';
export default {
  data() {
    return {
      caseList : []
    }
  },
  created() {
    this.fetchCase()
  },
  computed : {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    })
  },
  methods : {
    async fetchCase() {
      const res = await fetchCaseList(this.groupInfo.id)
      this.caseList = res.data
    },
    alertDetail(student) {
      fetchCaseDetail(student.id).then((r) => {
        const request = r.data 
        this.$swal({
        title: `경위서`,
        // text: `${request.content}`,
        html: 
          '<div id="swal2-content" class="swal2-html-container" style="display: block;">'
          +'<p class="swal2-text">'+'제출인 : '+`${request.policeNickname}`+'</p>' +
          '<p class="swal2-text">'+'벌금내는 학생 : '+`${request.studentNickname}`+'</p>' +
          '<p class="swal2-text">'+'벌금 : '+`${request.fine}`+'</p>' + 
          '<p class="swal2-text">'+'내용 : '+`${request.content}`+'</p>'
          +'</div>',
        })
      })
      
    }
  }
};
</script>

<style lang="scss" scoped>

</style>