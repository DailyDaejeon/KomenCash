<template>
  <main class="content">
    <div class="container-fluid p-0">
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-none d-sm-block">
          <h3><strong>Request</strong> Dashboard</h3>
        </div>  
      </div>
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
import RequestItem from '@/components/group/main/RequestItem.vue'
import JobList from '@/components/group/job/JobList.vue'
export default {
  components: { RequestItem, JobList },

}
</script>

<style>

</style>