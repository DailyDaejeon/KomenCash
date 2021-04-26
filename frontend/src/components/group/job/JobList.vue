<template>
	<div class="row">
    <div class="w-100">
      <div class="card-header">
        <h4 class="card-title mb-0">Job list</h4>
        <JobItem JobType="Job"/>
      </div>
      <div class="card-header">
        <h4 class="card-title mb-0">Part-time list</h4>
        <JobItem JobType="PartTime"/>
      </div>
    </div>
  </div>
</template>

<script>
import JobItem from './JobItem.vue'
export default {
  components: { JobItem },

}
</script>

<style>

</style>