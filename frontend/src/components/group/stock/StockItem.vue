<template>
  <div class="card" @click="goDetail">
    <div class="card-body">
      <h5 class="card-title mb-4 text-center">{{stockData.name}}</h5>
      <div class="text-center  mt-1 mb-3">
        <span :class="arrowColor" class="h1">{{stockData.price}} </span>
        <div class="mt-3 d-flex justify-content-center">
        <span class="h2 mr-3" :class="arrowColor"> <i :class="arrow"></i></span>
        <span :class="arrowColor" class="h4 mr-3">{{changeMoney}}</span>
        <span :class="arrowColor" class="h4">({{money}}%)</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props : ['stockData'],
  data() {
    return {
      changeMoney:0,
      money : 0,
      arrow:"",
      arrowColor:"",
      downArrow:"fas fa-sort-down",
      upArrow:"fas fa-sort-up",
      same:"fas fa-minus"
    }
  },
  created() {
    this.fetchMoney()
  },
  methods : {
    fetchMoney() {
      this.changeMoney = Math.abs(this.stockData.price - this.stockData.prePrice)
      this.money = (((this.stockData.price - this.stockData.prePrice)/this.stockData.prePrice)*100).toFixed(2)
      if (this.money < 0 ) {
        this.arrow = this.downArrow
        this.arrowColor = "text-primary"
      } else if (this.money === 0) {
        this.arrow = this.same
        this.arrowColor = "text-secondary"
      } else {
        this.arrow = this.upArrow
        this.arrowColor = "text-danger"
      }
    },
    goDetail() {
      this.$router.push({name:"StockDetailPage",params:{stockId:this.stockData.id,stockData:this.stockData}})
    }
  }
}
</script>

<style>

</style>