<template>
  <div class="card flex-fill">
    <div class="card-header">
      <h5 class="card-title mb-0">{{propsData.nickname}}님의 은행거래내역</h5>
    </div>
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>거래내역</th>
          <th>입출금</th>
          <th>금액(단위:{{groupInfo.monetaryUnitName}})</th>
          <th>잔액</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(history,index) in propsData.accountHistory" :key="index">
          <td>{{history.content}}</td>
          <td v-if="history.balanceChange >= 0"><span class="badge bg-success">입금</span></td>
          <td v-else><span class="badge bg-danger">출금</span></td>
          <td>{{history.balanceChange}}</td>
          <td>{{history.balance}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  props:['propsData','dataName'],
  data() {
    return {
      studentList : this.propsData,
      studentName: this.dataName
    }
  },
  computed:{
    ...mapState({
      groupInfo:state => state.group.groupInfo
    })
  },
}
</script>

<style>

</style>