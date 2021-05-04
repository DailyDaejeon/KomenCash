<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">그룹원 전체 계좌</h1>
      <ListItem listType="account" :propsData="students"/>
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
      students : [
        {
          id:0,
          name:"김싸피"
        },
        {
          id:1,
          name:"박싸피"
        }
      ]
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
    async fetchAccountList() {
      const res = await fetchGroupMemberAccountList(this.groupInfo.id)
      this.students = res.data
    }
  }
}
</script>

<style>

</style>