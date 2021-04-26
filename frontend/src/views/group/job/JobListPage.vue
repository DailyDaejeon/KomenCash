<template>
  <main class="content">
    <div class="container-fluid p-0">
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-none d-sm-block">
          <h3><strong>Request</strong> Dashboard</h3>
        </div>  
      </div>
      <JobRequestList/>
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-none d-sm-block">
          <h3><strong>Job</strong> Dashboard</h3>
        </div>  
      </div>
      <JobList/>
    </div>
  </main>
</template>

<script>
import JobList from '@/components/group/job/JobList.vue'
import JobRequestList from '@/components/group/job/JobRequestList.vue'
export default {
  components: { JobList, JobRequestList },

}
</script>

<style>

</style>