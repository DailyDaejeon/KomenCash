<template>
  <div>
    <table class="text-center table table-hover my-0">
      <thead>
        <tr>
          <th>제출자(경찰)</th>
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
          <td><button class="btn btn-main">자세히</button></td>
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
import { fetchCaseList } from '@/api/case';
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
    }
  }
};
</script>

<style lang="scss" scoped>

</style>