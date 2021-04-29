<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">온라인스토어</h1>
      <div @click="addProduct">
        <button class="btn btn-main">상품추가</button>
      </div>
      <div class="row">
      <div class="col-4" v-for="(product,index) in 10" :key="index" @click="goDetail()">
        <StoreItem/>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import StoreItem from '@/components/store/StoreItem.vue';
export default {
  components: { StoreItem },
  data() {
    return {
      
    };
  },
  methods: {
    addProduct() {
          this.$swal.queue([
      {
        title: '상품추가 1단계',
        input: 'text',
        text: '상품명을 작성해주세요.',
        inputPlaceholder: '일기면제 1회권',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2'],
        inputValidator: (result) => {
          return !result && '상품명을 지정해주세요!'
        }
      },
      {
        title: '상품추가 2단계',
        input: 'text',
        text: '가격을 작성해주세요',
        inputPlaceholder: '100미소',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps:['1', '2'],
      inputValidator: (result) => {
          return !result && '가격을 지정해주세요!'
        }
      },
      ]).then((result) => {
      if (result.value) {
        const answers = JSON.stringify(result.value)
      this.$swal({
          title: '상품이 생성됐어요!',
          html: `
            Your answers:
            <pre><code>${answers}</code></pre>
          `,
          confirmButtonText: 'Lovely!'
        })
      }
      })
    }
  },
};
</script>

<style lang="scss" scoped>

</style>