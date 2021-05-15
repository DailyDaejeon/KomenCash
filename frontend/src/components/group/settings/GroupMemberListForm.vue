<template>
  <div class="card">
    <div class="card-body">
      <div class="flex-fill">
        <h5 class="card-title mb-0">Group Member Info</h5>
        <div class="card-body">
          <div class="row">
            <div class="col">
              <table class="table table-hover text-center">
                <tr>
                  <th>No.</th>
                  <th>닉네임</th>
                  <th>직업</th>
                  <th>상세보기</th>
                </tr>
                <tr v-for="(student, index) in paginatedData" :key="index">
                <td>{{index+1+10*(pageNum)}}</td>
                  <td class="cursor-pointer">{{student.nickname}}</td>
                  <td>{{student.job.name}}</td>
                  <td @click="goDetail(student.id)"><button class="btn btn-main">자세히</button></td>
                </tr>
              </table>
              <div  v-if="paginatedData.length" class="btn-cover text-center">
                <button :disabled="pageNum === 0" @click="prevPage" class="page-btn mr-3">이전</button>
                <span class="page-count mr-3">{{pageNum+1}}/{{pageCount}} 페이지 </span>
                <button :disabled="pageNum >= pageCount-1"  @click="nextPage" class="page-btn">다음</button>
            </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchGroupMemberList } from '@/api/student';
import { mapState } from 'vuex';

export default {
  props:{
    pageSize: {
      type:Number,
      required: false,
      default: 10
    },
  },
  data() {
    return {
      pageNum:0,
      studentList:[],
    }
  },
  created() {
    this.fetchMemberList();
  },
  computed : {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    }),
    pageCount() {
      let listLeng = this.studentList.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if(listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;

      return this.studentList.slice(start, end);
    }
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    async fetchMemberList(){
      //그룹원 리스트 조회 api
      const res = await fetchGroupMemberList(this.groupInfo.id);
      this.studentList = res.data
    },
    goDetail(sId){
      this.$router.push({name:"GroupMemberInfo",params: {
          id:sId
        }})
      //학생 상세 정보 조회 api
      // const student = await fetchMemberDetail(sId).data;
      // console.log(sId);
      // this.$swal({
      //   title: this.studentList[0].nickname+' 학생의 상세 정보',
      //   html: 
      //   "<table class='table'>"+
      //   "<tr>"+
      //   "<th>이름</th>"+
      //   "<td>"+this.studentList[0].nickname+"</td>"+
      //   "</tr>"+
      //   "<tr>"+
      //   "<th>직업</th>"+
      //   "<td>"+this.studentList[0].job.name+"</td>"+
      //   "</tr>"+
      //   "<tr>"+
      //   "<th>월급</th>"+
      //   "<td>"+this.studentList[0].job.salary+"</td>"+
      //   "</tr>"+
      //   "<tr>"+
      //   "<th>자격증</th>"+
      //   "<td>수학왕</td>"+
      //   "</tr>"+
      //   "<tr>"+
      //   "<th>통장잔고</th>"+
      //   "<td>1000미소</td>"+
      //   "</tr>"+
      //   "</table>",
      // })
    },
  }
}
</script>

<style>

</style>