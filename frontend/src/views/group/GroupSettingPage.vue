<template>
  <main class="content">
    <div class="container-fluid p-0">
      <div class="row">
        <div class="col-md-3 col-xl-2">
          <div class="card">
            <div class="card-header">
              <h5 class="card-title mb-0">그룹 관리</h5>
            </div>
            <div class="pointer list-group list-group-flush" role="tablist" v-for="(data,index) in groupData" :key="index"> 
              <router-link 
              :id="data.name"
              active-class="active"
              class="list-group-item list-group-item-action" :to="{name:`${data.name}`}"
              >
              {{data.title}}
              </router-link>
            </div>
          </div>
        </div>

        <div class="col-md-9 col-xl-10">
          <div class="tab-content">
            <div class="tab-pane fade show active" role="tabpanel"> <!--  :id="data.name" -->
              <router-view></router-view>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      groupData:[
        //그룹 정보
        {
          name: "groupInfo",
          title: "그룹 정보",
        },
        //그룹원 정보
        {
          name: "groupMemberList",
          title: "그룹원 정보",
          // groupMember:[
          //   {
          //     job:{
          //       group:{
          //         code:"23133322",
          //         id:1, //그룹 id
          //         monetaryUnitName:"미소",
          //         name:"햇반",
          //         tax:1000,
          //         tax_rate:0,
          //         teacher:{
          //           id:1, //선생님 id
          //           email:"test@test.com",
          //           nickname: "박싸피",
          //           password: "test1234",
          //           phoneNumber: "01012345678"
          //         }
          //       },
          //       id:1, //직업 id
          //       name:"은행원",
          //       personnel: 2, //인원 수
          //       recruitType: "resume",
          //       role: "계좌 관리 및 금융 상품 판매",
          //       salary: 100,
          //     },
          //     id:1, //학생 id
          //     nickname: "김학생"
          //   }
          // ],
        },
        //그룹원 추가 요청 정보
        {
          name: "addMemberRequest",
          title: "그룹원 추가 요청",
          // addMemberRequest:[
          //   {
          //     accept:"before_confirm",
          //     id:1,
          //     nickname:"박학생",
          //     student_id:2,
          //   },
          //   {
          //     accept:"before_confirm",
          //     id:2,
          //     nickname:"최학생",
          //     student_id:3,
          //   }
          // ]
        }
      ],
    }
  },
}
</script>

<style>

</style>