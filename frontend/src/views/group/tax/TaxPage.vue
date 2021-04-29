<template>
  <main class="content">
    <div class="container-fluid p-0 mb-5">
      <div class="mb-2">
        <div class="d-none d-sm-block">
          <h3 class="d-inline-block mr-3"><strong>몽글몽글 혜림이네</strong> 세금</h3>
            <button  @click="modifyTax" class="btn btn-main float-right">소득세율수정</button>
          <h1 class="display-4"><i class="fas fa-money-bill-alt"></i>총 100,000,000미소</h1>
        </div>  
      </div>
    </div>
    <TaxHistory/>
  </main>
</template>

<script>
import TaxHistory from '@/components/tax/TaxHistory.vue'
export default {
  components: { TaxHistory },
  methods: {
    modifyTax() {
      this.$swal({
        title: '소득세율 수정',
        text: "소득세율을 수정해주세요!(%)",
        input: 'text',
        inputPlaceholder: '10',
        showCancelButton: true,
      })
      }
  },
}
</script>

<style>

</style>