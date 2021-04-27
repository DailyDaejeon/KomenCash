<template>
  <div class="card flex-fill">
    <div class="card-header">
      <h5 class="card-title mb-0">{{studentName}}님의 은행거래내역</h5>
    </div>
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>거래내역</th>
          <th>입출금</th>
          <th class="d-none d-md-table-cell">금액(단위:미소)</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>월급</td>
          <td><span class="badge bg-success">입금</span></td>
          <td class="d-none d-md-table-cell">100</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props:['studentData','memberName'],
  data() {
    return {
      studentList : this.studentData,
      studentName: this.memberName
    }
  }
}
</script>

<style>

</style>