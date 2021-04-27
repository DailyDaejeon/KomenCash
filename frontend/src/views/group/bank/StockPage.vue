<template>
  <main class="content">
    <div @click="addStock">
      <button class="btn btn-main">주식추가</button>
    </div>
    <StockList/>
  </main>
</template>

<script>
import StockList from '@/components/group/stock/StockList.vue'
export default {
  components: { StockList },
  methods :{
  addStock() {
    this.$swal.queue([
      {
        title: '주식추가 1단계',
        input: 'text',
        text: '종목을 작성해주세요.',
        inputPlaceholder: '선생님 몸무게',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
      },
      {
        title: '주식추가 2단계',
        input: 'text',
        text: '주가힌트를 작성해주세요',
        inputPlaceholder: '오늘 저녁에 치킨먹어요.',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps:['1', '2']
      },
      ]).then((result) => {
      if (result.value) {
        const answers = JSON.stringify(result.value)
      this.$swal({
          title: '주식이 생성됐어요!',
          html: `
            Your answers:
            <pre><code>${answers}</code></pre>
          `,
          confirmButtonText: 'Lovely!'
        })
      }
      })
  }
}

}
</script>


<style lang="scss">
.swal2-progress-steps .swal2-progress-step.swal2-active-progress-step {
  background: #e7ab3c;
}

.swal2-progress-steps .swal2-progress-step {
    z-index: 20;
    flex-shrink: 0;
    width: 2em;
    height: 2em;
    border-radius: 2em;
    background: #e7ab3c;
    color: #fff;
    line-height: 2em;
    text-align: center;
}

.swal2-progress-steps .swal2-progress-step.swal2-active-progress-step~.swal2-progress-step-line {
    background: #e7e7e7;
}

.swal2-progress-steps .swal2-progress-step.swal2-active-progress-step~.swal2-progress-step {
    background: #bebebe;
    color: #fff;
}

.swal2-progress-steps .swal2-progress-step-line {
    z-index: 10;
    flex-shrink: 0;
    width: 2.5em;
    height: .4em;
    margin: 0 -1px;
    background: #757575;
}
</style>