<template>
  <section @click="goDetail" class="giftcard pointer">
    <section class="giftcard-cover">
      <i class="fas fa-chart-line"></i>
    </section>
    <footer class="giftcard-footer">
      <div class="giftcard-text">
        <h1>{{stockData.name}}</h1>
        <h2>{{priceToString(stockData.price)}}
          <span class="h2 mr-3" :class="arrowColor"> <i :class="arrow"></i></span>
        <span :class="arrowColor" class="h4 mr-3">{{priceToString(changeMoney)}}</span>
        <span :class="arrowColor" class="h4">({{isNaN(priceToString(money)) ? 0 : priceToString(money)}}%)</span>
        </h2>
      </div>
      
    </footer>
  </section>
  <!-- <div class="card" @click="goDetail">
    <div class="card-body">
      <h5 class="card-title mb-4 text-center">{{stockData.name}}</h5>
      <div class="text-center  mt-1 mb-3">
        <span :class="arrowColor" class="h1">{{priceToString(stockData.price)}} </span>
        <div class="mt-3 d-flex justify-content-center">
        <span class="h2 mr-3" :class="arrowColor"> <i :class="arrow"></i></span>
        <span :class="arrowColor" class="h4 mr-3">{{priceToString(changeMoney)}}</span>
        <span :class="arrowColor" class="h4">({{priceToString(money)}}%)</span>
        </div>
      </div>
    </div>
  </div> -->
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
    priceToString(price) {
      return price.toLocaleString('ko-KR')
    },
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