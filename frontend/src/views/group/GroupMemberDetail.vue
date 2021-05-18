<template>
  <main class="content">
    <div class='d-flex justify-content-end mb-3'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">그룹원 상세 정보</h1>
      <ListItem listType="member" :propsData="students"/>
    </div>
  </main>
</template>

<script>
import ListItem from '@/components/group/ListItem.vue'

import { mapState } from 'vuex'
import { fetchGroupMemberList } from '@/api/student'

export default {
  components: { ListItem },
  data() {
    return {
      students : [{
        id: 0,
        job: {
          group: {
            "code": "string",
            "id": 0,
            "inflationRate": 0,
            "monetary_unit_name": "string",
            "name": "string",
            "tax": 0,
            "tax_rate": 0,
            "teacher": {
              "email": "string",
              "id": 0,
              "nickname": "string",
              "password": "string",
              "phoneNumber": "string"
            }
          },
          id: 0,
          name: "string",
          personnel: 0,
          qualification: "string",
          recruitType: "vote",
          role: "string",
          salary: 0
        },
        nickname: "string"
      }
      ]
    }
  },
  created() {
    this.fetchMemberList()
  },
  watch:{
    $route() {
      // window.location.reload();
    }
  },
  computed:{
    ...mapState({
      groupInfo: state => state.group.groupInfo
    })
  },
  methods : {
        alertTuto() {
      this.$swal.queue([
        {
        title: '그룹원정보 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹원을 관리하는 페이지입니다.</p><p>2. 그룹원의 정보, 잔고, 신용등급, 자격증, 예금, 주식, 경위서, 상품 구매내역 등 모든 정보를 볼 수 있습니다.</p><p>3. "직업변경" 버튼은 그룹원의 직업을 변경할 수 있습니다.</p><p>4. "자격증"칸은 그룹원이 획득한 자격증을 추가 및 삭제를 할 수 있습니다.</p><p>4. "직업변경" 버튼은 그룹원의 직업을 변경할 수 있습니다.</p></div>',
        confirmButtonText: '확인',

      },
      ])
    },
    async fetchMemberList(){
      //그룹원 리스트 조회 api
      const res = await fetchGroupMemberList(this.groupInfo.id);
      console.log(res)
      this.students = res.data
    },
  }
}
</script>

<style>

</style>