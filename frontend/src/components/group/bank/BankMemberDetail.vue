<template>
  <div class="card flex-fill">
    <div class="card-header">
      <h5 class="card-title mb-0">{{propsData.nickname}}님의 은행거래내역</h5>
    </div>
    <table class="text-center table table-hover my-0">
      <thead>
        <tr>
          <th>No.</th>
          <th>거래내역</th>
          <th>입출금</th>
          <th>금액({{groupInfo.monetaryUnitName}})</th>
          <th>잔액({{groupInfo.monetaryUnitName}})</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(history,index) in paginatedData" :key="index">
          <td>{{index+1+10*(pageNum)}}</td>
          <td>{{history.content}}</td>
          <td v-if="history.balanceChange >= 0"><span class="badge bg-success">입금</span></td>
          <td v-else><span class="badge bg-danger">출금</span></td>
          <td>{{history.balanceChange}}</td>
          <td>{{history.balance}}</td>
        </tr>
      </tbody>
    </table>
    <div  v-if="paginatedData.length" class="btn-cover text-center">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn mr-3">이전</button>
      <span class="page-count mr-3">{{pageNum+1}}/{{pageCount}} 페이지 </span>
      <button :disabled="pageNum >= pageCount-1"  @click="nextPage" class="page-btn">다음</button>
  </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  props:['propsData','dataName'],
  data() {
    return {
      pageSize:10,
      pageNum:0,
      studentList : this.propsData,
      studentName: this.dataName
    }
  },
  computed:{
    ...mapState({
      groupInfo:state => state.group.groupInfo
    }),
    pageCount() {
      let listLeng = this.propsData.accountHistory.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if(listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;

      return this.propsData.accountHistory.slice(start, end);
    }
  },
  methods: {
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
  }
}
</script>

<style>

</style>