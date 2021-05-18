<template>
  <main class="content">
    <div class='d-flex justify-content-end mb-3'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
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
    alertTuto() {
      this.$swal.queue([
        {
        title: '은행 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹원의 전체 계좌를 조회할 수 있는 페이지입니다.</p><p>2. 그룹원의 통장내역을 확인할 수 있고, "입출금"버튼을 통해 입금 또는 출금을 할 수 있습니다.</p><p>3. "세금에서 입출금 하시겠습니까?" 를 체크하면, 세금내역에도 기록됩니다.</p></div>',
        confirmButtonText: '확인',
      },
      ])
    },
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