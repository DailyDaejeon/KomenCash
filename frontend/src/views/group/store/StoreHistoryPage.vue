<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">상품 거래내역</h1>
      <table class="text-center table table-hover my-0">
      <thead>
        <tr class="text-center">
          <th>No.</th>
          <th>거래내역</th>
          <th>구매인</th>
          <th>금액({{groupInfo.monetaryUnitName}})</th>
          <th>거래날짜</th>
        </tr>
      </thead>
      <tbody class="text-center">
        <tr v-for="(history,index) in paginatedData" :key="index">
          <td>{{index+1+10*(pageNum)}}</td>
          <td>{{history.name}}</td>
          <td>{{history.studentNickname}}</td>
          <td >{{priceToString(history.price)}}</td>
          <td>{{history.perchaseDate.slice(0,10)}}</td>
        </tr>
      </tbody>
    </table>
    <p class="h4 mt-3 text-center" v-if="!paginatedData.length">
      아직 온라인 상점에서 물건을 구매한 학생이 없습니다.
    </p>
    <div  v-if="paginatedData.length" class="btn-cover text-center">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn mr-3">이전</button>
      <span class="page-count mr-3">{{pageNum+1}}/{{pageCount}} 페이지 </span>
      <button :disabled="pageNum >= pageCount-1"  @click="nextPage" class="page-btn">다음</button>
  </div>
    </div>
  </main>
</template>

<script>
import { fetchStoreHistory } from '@/api/store';
import { mapState } from 'vuex';

export default {
  data() {
    return {
      pageNum:0,
      pageSize:10,
      storeHistoryList : []
    };
  },
  created() {
    this.fetchStoreHistoryList()
  },
  computed: {
    ...mapState({
      groupInfo: state=>state.group.groupInfo
    }),
    pageCount() {
      let listLeng = this.storeHistoryList.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if(listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;

      return this.storeHistoryList.slice(start, end);
    }
  },
  methods: {
    priceToString(price) {
      return price.toLocaleString('ko-KR')
    },
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    async fetchStoreHistoryList() {
      const res = await fetchStoreHistory(this.groupInfo.id)
      this.storeHistoryList = res.data
    }
  },
};
</script>

<style lang="scss" scoped>

</style>