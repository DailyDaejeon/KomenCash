<template>
  <div>
    <StockItemChart :stockId="stockId" :stockData="stockData"/>
  </div>
</template>

<script>
import StockItemChart from '@/components/group/stock/StockItemChart.vue'
export default {
  components: { StockItemChart },
  props: ['stockId','stockData']

}
</script>

<style>

</style>