<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">상품 거래내역</h1>
      <table class="table table-hover my-0">
      <thead>
        <tr class="text-center">
          <th>거래내역</th>
          <th>구매인</th>
          <th>금액({{groupInfo.monetaryUnitName}})</th>
          <th>거래날짜</th>
        </tr>
      </thead>
      <tbody class="text-center">
        <tr v-for="(history,index) in storeHistoryList" :key="index">
          <td>{{history.name}}</td>
          <td>{{history.studentNickname}}</td>
          <td >{{history.price}}</td>
          <td>{{history.perchaseDate.slice(0,10)}}</td>
        </tr>
      </tbody>
    </table>
    </div>
  </main>
</template>

<script>
import { fetchStoreHistory } from '@/api/store';
import { mapState } from 'vuex';

export default {
  data() {
    return {
      storeHistoryList : [
        {
          id:0,
          name:"김싸피"
        },
        {
          id:1,
          name:"박싸피"
        }
      ]
    };
  },
  created() {
    this.fetchStoreHistoryList()
  },
  computed: {
    ...mapState({
      groupInfo: state=>state.group.groupInfo
    })
  },
  methods: {
    async fetchStoreHistoryList() {
      const res = await fetchStoreHistory(this.groupInfo.id)
      this.storeHistoryList = res.data
    }
  },
};
</script>

<style lang="scss" scoped>

</style>