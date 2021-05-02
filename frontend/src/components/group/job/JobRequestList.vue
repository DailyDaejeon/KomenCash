<template>
  <div class="w-100">
    <div class="row">
      <div class="col-4 col-sm-6">
        <RequestItem RequestType="이력서"/>
      </div>
      <div class="col-4 col-sm-6">
        <RequestItem RequestType="직업추가"/>
      </div>
    </div>
  </div>
</template>

<script>
import RequestItem from '../main/RequestItem.vue'
import { fetchJobRequestList, fetchJobResumeList } from '@/api/job'

export default {
  components: { RequestItem },
  data () {
    return {
      resumeList : [],
      jobList : []
    }
  },
  created() {
    this.fetchRequestList()
  },
  methods: {
    async fetchRequestList() {
      const resume = await fetchJobResumeList(1);
      const job = await fetchJobRequestList(1);
      this.resumeList = resume.data;
      this.jobList = job.data;
    }
  }
}
</script>

<style>

</style>