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
                  <th>비밀번호 초기화</th>
                </tr>
                <tr v-for="(student, index) in studentList" :key="index">
                  <td>{{studentList.length-index}}</td>
                  <td class="cursor-pointer" @click="goDetail(student.id)">{{student.nickname}}</td>
                  <td>{{student.job.name}}</td>
                  <td><button class="btn btn-main" @click="resetPW(student.id)">초기화하기</button></td>
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
    //this.fetchMemberList();
  },
  methods: {
    fetchMemberList(){
      //그룹원 리스트 조회 api
    },
    goDetail(sId){
      //학생 상세 정보 조회 api
      // const student = await fetchMemberDetail(sId).data;
      console.log(sId);
      this.$swal({
        title: this.studentList[0].nickname+' 학생의 상세 정보',
        html: 
        "<table class='table'>"+
        "<tr>"+
        "<th>이름</th>"+
        "<td>"+this.studentList[0].nickname+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>직업</th>"+
        "<td>"+this.studentList[0].job.name+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>월급</th>"+
        "<td>"+this.studentList[0].job.salary+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>자격증</th>"+
        "<td>수학왕</td>"+
        "</tr>"+
        "<tr>"+
        "<th>통장잔고</th>"+
        "<td>1000미소</td>"+
        "</tr>"+
        "</table>",
      })
    }
  }
}
</script>

<style>

</style>