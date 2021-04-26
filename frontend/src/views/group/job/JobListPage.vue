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
        <div class="col-auto" @click="addJob">
          <button class="btn btn-main">직업추가</button>
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
  methods: {
    addJob() {
      this.$swal({
          customClass: {
          container: 'swal2-container'
        },
        title: '직업을 추가해주세요.',
        input: 'text',
        inputLabel: '직업 이름',
        // inputValue: inputValue,
        showCancelButton: true,
        inputValidator: (value) => {
          if (!value) {
            return 'You need to write something!'
          }
        }
      })
    }
  },
}
</script>

<style>

</style>