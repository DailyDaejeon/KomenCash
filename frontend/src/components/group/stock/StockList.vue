<template>
  <div class="row">
    <div class="col-4" v-for="(stock,index) in 10" :key="index" @click="goDetail()">
      <StockItem/>
    </div>
  </div>
</template>

<script>
import StockItem from './StockItem.vue'
export default {
  components: { StockItem },
  methods: {
    goDetail() {
      this.$router.push({name:"StockDetailPage"})
    }
  }
}
</script>

<style>

</style>