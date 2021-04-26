<template>
  <div class="card flex-fill">
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>{{JobType}}</th>
          <th>급여</th>
          <th>인원수</th>
          <th class="d-none d-md-table-cell">이름</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>김싸피</td>
          <td class="d-none d-md-table-cell">100미소</td>
          <td class="d-none d-md-table-cell">1</td>
          <td class="d-none d-md-table-cell">박싸피</td>
        </tr>
        <tr>
          <td>박싸피</td>
          <td class="d-none d-md-table-cell">90미소</td>
          <td class="d-none d-md-table-cell">2</td>
          <td class="d-none d-md-table-cell">은행원</td>
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
    }
  }
}
</script>

<style>

</style>