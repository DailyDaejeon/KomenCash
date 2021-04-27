<template>
  <div class="card flex-fill">
    <div class="card-header">
      <h5 class="card-title mb-0">{{ RequestType }} Requests</h5>
    </div>
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>Name</th>
          <!-- <th class="d-none d-xl-table-cell">Date</th> -->
          <th>Date</th>
          <th class="d-none d-md-table-cell">Assignee</th>
        </tr>
      </thead>
      <tbody>
        <tr @click="goDetail()">
          <td>요청내용 title</td>
          <!-- <td class="d-none d-xl-table-cell">날짜</td> -->
          <td><span class="badge bg-success">날짜</span></td>
          <td class="d-none d-md-table-cell">누가요청했는지</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props:{
    RequestType : {
      type:String
    }
  },
  data() {
    return {
      RequestName: ""
    }
  },
  created() {
    this.fetchRequest();
  },
  methods : {
    fetchRequest() {
      this.RequestName = this.RequestType;
    },
    goDetail() {
      // 각요청별로 detail 보내는것 다르게 표시
      this.$router.push(`/`)
    }
  }
}
</script>

<style>

</style>