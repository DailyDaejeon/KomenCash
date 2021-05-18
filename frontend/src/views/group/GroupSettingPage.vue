<template>
  <main class="content">
    <div class='d-flex justify-content-end mb-3'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
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
          name: "GroupInfo",
          title: "그룹 정보",
        },
        {
          name: "UserInfoPage",
          title: "내 정보",
        },
        {
          name: "GroupCertificate",
          title: "자격증 정보",
        },
        //그룹원 정보
        {
          name: "GroupMemberList",
          title: "그룹원 정보",
          groupMember:[
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
        },
        //그룹원 추가 요청 정보
        {
          name: "AddMemberRequest",
          title: "그룹원 추가 요청",
          addMemberRequest:[
            {
              accept:"before_confirm",
              id:1,
              nickname:"박학생",
              student_id:2,
            },
            {
              accept:"before_confirm",
              id:2,
              nickname:"최학생",
              student_id:3,
            }
          ]
        }
      ],
    }
  },
  methods : {
    alertTuto() {
      this.$swal.queue([
        {
        title: '그룹정보 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹을 관리하는 페이지입니다.</p><p>2. "그룹정보"탭에서는 그룹의 정보를 수정 및 삭제할 수 있습니다.</p><p>3. "내정보"탭에서는 선생님의 정보를 수정, 삭제를 할 수 있습니다.</p><p>4. "자격증 정보"탭에서는 그룹의 자격증을 추가, 수정, 삭제를 할 수 있습니다.</p></div>',
        confirmButtonText: '확인',

      },
      ])
    },
  }
}
</script>

<style>

</style>