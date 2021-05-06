<template>
  <main class="content">
    <div @click="createStock">
      <button class="btn btn-main">주식추가</button>
    </div>
    <StockList/>
  </main>
</template>

<script>
import StockList from '@/components/group/stock/StockList.vue'
import { mapState } from 'vuex'
import {addStock} from '@/api/stock'

export default {
  components: { StockList },
  computed:{
    ...mapState({
      groupInfo: state => state.group.groupInfo
    })
  },
  methods :{
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