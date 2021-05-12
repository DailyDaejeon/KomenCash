<template>
  <div class="m-2 border border-main card flex-fill">
    <div class="card-header">
      <h5 class="card-title mb-0">신용등급 점수</h5>
    </div>
    <table class="table table-hover my-0">
      <thead>
        <tr class="text-center">
          <th>1등급</th>
          <th>2등급</th>
          <th>3등급</th>
          <th>4등급</th>
          <th>5등급</th>
          <th>6등급</th>
          <th>7등급</th>
          <th>8등급</th>
          <th>9등급</th>
          <th>10등급</th>
        </tr>
      </thead>
      <tbody>
        <tr class="text-center">
          <td v-for="(credit,index) in creditList"
          :key="index">
          {{credit.minPoint}} ~ {{credit.maxPoint}}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { fetchCreditList } from '@/api/credit';
export default {
  data() {
    return {
      creditList:[]
    }
  },
  created() {
    this.fetchGroupCredit()
  },
  methods:{
    async fetchGroupCredit() {
      const credit = await fetchCreditList()
      this.creditList = credit.data
      console.log(this.creditList,'??')
    }
  }
};
</script>

<style lang="scss" scoped>

</style>