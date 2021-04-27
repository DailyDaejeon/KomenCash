<template>
  <div class="row">
    <div class="col-4" v-for="(stock,index) in 10" :key="index">
      <StockItem/>
    </div>
  </div>
</template>

<script>
import StockItem from './StockItem.vue'
export default {
  components: { StockItem },
}
</script>

<style>

</style>