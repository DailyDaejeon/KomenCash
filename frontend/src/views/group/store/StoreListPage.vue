<template>
  <main class="content">
    <div class='d-flex justify-content-end mb-3'>
      <button @click="alertTuto" class="btn btn-main">튜토리얼</button>
    </div>
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">온라인스토어</h1>
      <div @click="addProduct">
        <button class="btn btn-main">상품추가</button>
      </div>
      <div class="row">
      <div class="col-4" v-for="(product,index) in productList" :key="index">
        <StoreItem @updateData="updateData" :product="product" :moneyUnit="groupInfo.monetaryUnitName"
        />
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import StoreItem from '@/components/store/StoreItem.vue';
import { mapState } from 'vuex';
import { addStoreProduct, fetchStoreList } from '@/api/store'

export default {
  components: { StoreItem },
  data() {
    return {
      productList : []
    }
  },
  created() {
    this.fetchStoreProducts()
  },
  computed: {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    })
  },
  methods: {
    alertTuto() {
      this.$swal.queue([
        {
        title: '온라인스토어 튜토리얼',
        html: 
            '<div id="swal2-content" class="swal2-html-container" style="display: block;"><p>1. 해당 페이지에서는 그룹의 온라인 상품을 관리하는 페이지입니다.</p><p>2. "상품추가"버튼을 통해 새로운 상품을 등록할 수 있습니다.</p><p>3. 연필모양을 클릭하면 상품을 수정하고, 삭제할 수 있습니다.</p><p>4. "상품 거래내역 관리" 탭은 그룹원들이 온라인 스토어에서 구매했던 내역을 조회할 수 있습니다.</p></div>',
        confirmButtonText: '확인',
      },
      ])
    },
    updateData() {
      this.fetchStoreProducts()
    },
    async fetchStoreProducts() {
      const res = await fetchStoreList(this.groupInfo.id)
      this.productList = res.data;
    },
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
        const product = {
          groupId: this.groupInfo.id,
          id: null,
          name: result.value[0],
          price: result.value[1]
        }
        addStoreProduct(product).then(()=> {
          this.productList.push(product)

          this.$swal({
          title: '상품이 생성됐어요!',
          icon:'success',
          timer:1500
        })
        })
      }
      })
    },
  },
};
</script>

<style lang="scss" scoped>

</style>