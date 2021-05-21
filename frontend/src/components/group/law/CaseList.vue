<template>
  <div>
    <table class="text-center table table-hover my-0">
      <thead>
        <tr>
          <th>No.</th>
          <th>제출자</th>
          <th>이름</th>
          <th>내용</th>
          <th>벌금({{groupInfo.monetaryUnitName}})</th>
          <th>상세보기</th>
        </tr>
      </thead>
      <tbody v-if="caseList.length">
        <tr v-for="(student,index) in paginatedData" :key="index">
          <template v-if="student.accept !== 'before_confirm'">
          <td>{{index+1+10*(pageNum)}}</td>
          <td>{{student.policeNickname}}</td>
          <td>{{student.studentNickname}}</td>
          <td>{{priceToString(student.fine)}}</td>
          <td><button class="btn btn-main" @click="alertDetail(student)">자세히</button></td>
          </template>
        </tr>
      </tbody>
    </table>
    <div  v-if="paginatedData.length" class="btn-cover text-center">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn mr-3">이전</button>
      <span class="page-count mr-3">{{pageNum+1}}/{{pageCount}} 페이지 </span>
      <button :disabled="pageNum >= pageCount-1"  @click="nextPage" class="page-btn">다음</button>
  </div>
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
      caseList : [],
      pageSize:10,
      pageNum:0,
    }
  },
  created() {
    this.fetchCase()
  },
  computed : {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    }),
    pageCount() {
      let listLeng = this.caseList.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if(listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;

      return this.caseList.slice(start, end);
    }
  },
  methods : {
    priceToString(price) {
      return price.toLocaleString('ko-KR')
    },
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
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