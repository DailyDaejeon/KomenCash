<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">상품 거래내역</h1>
      <!-- <BankMemberDetail propsData="storeHistoryList"/> -->
      <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>거래내역</th>
          <th>입출금</th>
          <th class="d-none d-md-table-cell">금액(단위:미소)</th>
          <th>거래날짜</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(history,index) in storeHistoryList" :key="index">
          <td>{{history.name}}의 일기면제권</td>
          <td><span class="badge bg-success">입금</span></td>
          <td class="d-none d-md-table-cell">100</td>
          <td class="d-none d-md-table-cell">2021.01.01</td>
        </tr>
      </tbody>
    </table>
    </div>
  </main>
</template>

<script>
// import BankMemberDetail from '@/components/group/bank/BankMemberDetail.vue';
export default {
  // components: { BankMemberDetail },
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
  mounted() {
    
  },
  methods: {
    
  },
};
</script>

<style lang="scss" scoped>

</style>