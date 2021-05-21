<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">직업조회</h1>
      <ListItem listType="job" :propsData="jobList"/>
    </div>
  </main>
</template>

<script>
import ListItem from '@/components/group/ListItem.vue'
import { fetchJobList } from '@/api/job' 
import { mapState } from 'vuex'

export default {
  props:['dataName'],
  components: { ListItem },
  data() {
    return {
      jobList : []
    }
  },
  created() {
    this.fetchJob()
  },
  computed:{
    ...mapState({
      groupInfo: state => state.group.groupInfo
    })
  },
  methods : {
    async fetchJob() {
      const res = await fetchJobList(this.groupInfo.id)
      this.jobList = res.data
      console.log(this.jobList)
    }
  }
}
</script>

<style>

</style>