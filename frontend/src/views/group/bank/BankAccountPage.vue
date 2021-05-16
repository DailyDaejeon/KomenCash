<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">그룹원 전체 계좌</h1>
      <ListItem @updateData="updateData" listType="account" :propsData="students"/>
    </div>
  </main>
</template>

<script>
import ListItem from '@/components/group/ListItem.vue'
import { fetchGroupMemberAccountList } from '@/api/bank'
import { mapState } from 'vuex'

export default {
  components: { ListItem },
  data() {
    return {
      students : []
    }
  },
  created() {
    this.fetchAccountList()
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
    updateData(e) {
      console.log('3여긴 BankAccountPage',e)
      this.fetchAccountList()
      // window.location.reload()
      // this.$router.replace({name:"BankMemberDetail",params:{id:e}})
    },
    async fetchAccountList() {
      const res = await fetchGroupMemberAccountList(this.groupInfo.id)
      this.students = res.data
    }
  }
}
</script>

<style>

</style>