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
                <tr v-for="(student, index) in studentList" :key="index">
                  <td>{{studentList.length-index}}</td>
                  <td class="cursor-pointer">{{student.nickname}}</td>
                  <td>{{student.job.name}}</td>
                  <td @click="goDetail(student.id)"><button class="btn btn-main">자세히</button></td>
                </tr>
              </table>
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
  data() {
    return {
      studentList:[
        {
          job:{
            group:{
              code:"23133322",
              id:1, //그룹 id
              monetaryUnitName:"미소",
              name:"햇반",
              tax:1000,
              tax_rate:0,
              teacher:{
                id:1, //선생님 id
                email:"test@test.com",
                nickname: "박싸피",
                password: "test1234",
                phoneNumber: "01012345678"
              }
            },
            id:1, //직업 id
            name:"은행원",
            personnel: 2, //인원 수
            recruitType: "resume",
            role: "계좌 관리 및 금융 상품 판매",
            salary: 100,
          },
          id:1, //학생 id
          nickname: "김학생"
        }
      ],
    }
  },
  created() {
    this.fetchMemberList();
  },
  computed : {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    })
  },
  methods: {
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