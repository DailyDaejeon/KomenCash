<template>
	<div class="row">
    <div class="w-100">
      <div class="card-header">
        <h4 class="card-title mb-0">Job list</h4>
        <JobItem JobType="Job" :jobData="jobList"/>
      </div>
      <div class="card-header">
        <h4 class="card-title mb-0">Part-time list</h4>
        <JobItem JobType="PartTime" :jobData="partTimeList"/>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchJobList, fetchPartTimeList } from '@/api/job';
import JobItem from './JobItem.vue'
import { mapState } from 'vuex';

export default {
  components: { JobItem },
  data() {
    return {
      jobList :[],
      partTimeList :[]

    }
  },
  created() {
    this.fetchJob();
  },
  computed : {
    ...mapState({
      groupInfo: state => state.group.groupInfo,
    }),
  },
  methods: {
    async fetchJob() {
      const job = await fetchJobList(this.groupInfo.id);
      const partTime = await fetchPartTimeList(this.groupInfo.id);
      this.jobList = job.data
      this.partTimeList = partTime.data
    }
  },
}
</script>

<style>

</style>