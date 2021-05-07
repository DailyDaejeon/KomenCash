<template>
  <main class="content">
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