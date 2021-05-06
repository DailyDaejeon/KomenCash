<template>
  <div class="row">
    <div class="col-4" v-for="(stock,index) in stockList" :key="index">
      <StockItem :stockData="stock"/>
    </div>
  </div>
</template>

<script>
import { fetchStockList } from '@/api/stock'
import { mapState } from 'vuex'
import StockItem from './StockItem.vue'

export default {
  components: { StockItem },
  data() {
    return {
      stockList :[]
    }
  },
  created() {
    this.fetchStock()
  },
  computed:{
    ...mapState({
      groupInfo: state => state.group.groupInfo
    })
  },
  methods: {
    async fetchStock() {
      const res = await fetchStockList(this.groupInfo.id)
      this.stockList = res.data
    },

  }
}
</script>

<style>

</style>