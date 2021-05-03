<template>
  <div class="card flex-fill">
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>{{JobType}}</th>
          <th>급여</th>
          <th>인원수</th>
          <th>역할</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(job,index) in jobData" :key="index" @click="goDetail(job.id)">
          <td>{{job.name}}</td>
          <td>{{job.salary}}</td>
          <td>{{job.personnel}}</td>
          <td>{{job.role}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props: {
    JobType: {
      type: String
    },
    jobData: {
      type: Array
    }
  },
  methods: {
    goDetail(id) {
      this.$router.push({name:"JobDetail",params: {jobId:id}})
    }
  }
}
</script>

<style>

</style>