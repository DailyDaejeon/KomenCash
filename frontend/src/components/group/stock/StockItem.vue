<template>
  <div class="card" @click="goDetail">
    <div class="card-body">
      <h5 class="card-title mb-4">{{stockData.name}}</h5>
      <h1 class="mt-1 mb-3">{{stockData.price}}</h1>
      <div class="mb-1">
        <span class="text-danger"> <i class="mdi mdi-arrow-bottom-right"></i> 상승하락폭?-3.65% </span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props : ['stockData'],
  methods : {
    goDetail() {
      this.$router.push({name:"StockDetailPage",params:{stockId:this.stockData.id,stockData:this.stockData}})
    }
  }
}
</script>

<style>

</style>