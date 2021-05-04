<template>
  <div class="card flex-fill">
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>이름</th>
          <th class="d-none d-md-table-cell">금리</th>
          <th>금액</th>
        </tr>
      </thead>
      <tbody>
        <tr @click="goDetail(financial.id)" v-for="(financial,index) in studentList" :key="index">
          <td>{{financial.name}}</td>
          <td class="d-none d-md-table-cell">{{financial.rate}}</td>
          <td class="d-none d-md-table-cell">100미소</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  props:['propsData','dataName'],
  data() {
    return {
      financialList : this.propsData,
      financialName: this.dataName,
      studentList:this.propsData.studentsList
    }
  },
  methods: {
    goDetail() {
      
    }
  },
};
</script>

<style lang="scss" scoped>

</style>