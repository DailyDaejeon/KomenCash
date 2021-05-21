<template>
  <div>
    <div @click="createStock">
      <button class="btn btn-main">주식추가</button>
    </div>
    <div class="row">
    <div class="col-4" v-for="(stock,index) in stockList" :key="index">
      <StockItem :stockData="stock"/>
    </div>
    </div>
  </div>
</template>

<script>
import { addStock, fetchStockList } from '@/api/stock'
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
      createStock() {
    this.$swal.queue([
      {
        title: '주식추가 1단계',
        input: 'text',
        text: '종목을 작성해주세요.',
        inputPlaceholder: '선생님 몸무게',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
        inputValidator: (result) => {
          return !result && '종목을 지정해주세요!'
        }
      },
      {
        title: '주식추가 2단계',
        input: 'text',
        text: '주가힌트를 작성해주세요',
        inputPlaceholder: '오늘 저녁에 치킨먹어요.',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps:['1', '2'],
      inputValidator: (result) => {
          return !result && '주가힌트를 지정해주세요!'
        }
      },
      ]).then((result) => {
      if (result.value) {
        const stock = {
          groupId: this.groupInfo.id,
          name: result.value[0],
          hint: result.value[1]
        }
        addStock(stock).then(()=>{
          this.fetchStock()
          this.$swal({
              title: '주식이 생성됐어요!',
              icon:"success",
              confirmButtonText: 'Lovely!'
            })
        }).catch(()=>{
           this.$swal({
              title: '주식 추가가 안됐어요!',
              icon:"warning"
            })
        })
      }
      })
  }
  }
}
</script>

<style>

</style>