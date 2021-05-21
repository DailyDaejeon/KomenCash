<template>
<section class="giftcard pointer">
  <section class="giftcard-cover">
    <i class="fas fa-gift"></i>
  </section>
  <footer class="giftcard-footer">
    <div class="giftcard-text">
      <template v-if="modify">
      <h1>{{product.name}}</h1>
      <h2>{{priceToString(product.price)}} {{moneyUnit}} <button
      @click="modifyStoreItem" class="text-main"><i class="fas fa-pencil-alt"></i></button></h2>
      </template>
      <template v-else>
        <input v-model="productName" class="border border-main" type="text" :placeholder="product.name">
        <button class="text-main mr-3" @click="completeModify(product)"><i class="fas fa-pencil-alt"></i>수정완료</button>
        <input v-model="productPrice" class="border border-main" type="text" :placeholder="product.price">
        <button 
        @click="deleteItem(product.id)"
        class="text-danger"><i class="fas fa-trash-alt"></i>삭제</button>
      </template>
    </div>
    
  </footer>
  </section>
</template>

<script>
import { deleteStoreProduct, modifyStoreProduct } from '@/api/store';
import { mapState } from 'vuex';

export default {
  props:['product','moneyUnit'],
  data() {
    return {
      productInfo:this.product,
      modify : true,
      productName : this.product.name,
      productPrice : this.product.price,
      gId:this.groupId
    }
  },
  computed: {
  ...mapState({
    groupInfo : state => state.group.groupInfo
  })
  },
  methods: {
    priceToString(price) {
      return price.toLocaleString('ko-KR')
    },
    modifyStoreItem() {
      this.modify = false;
    },
    completeModify(product) {
      const item = {
        // groupId: Number(this.groupInfo.id),
        id: Number(product.id),
        name: this.productName,
        price: Number(this.productPrice)
      }
      modifyStoreProduct(item).then(()=>{
        this.$emit('updateData')
        this.modify = true;
      })

    },
  deleteItem(id) {
    this.$swal({
      title:'상품을 삭제하시겠습니까?',
      icon:'warning',
      text:'상품 삭제 시 복구할 수 없습니다.',
      showCancleButton: true,
      confirmButtonText:'삭제',
      cancelButtonText:'취소'
    })
    .then((res) => {
      if (res.isConfirmed) {
        deleteStoreProduct(id).then(() => {
          this.$emit("updateData")
          this.$swal({
            title:'상품 삭제가 완료됐습니다.',
            icon:'success',
            timer: 1500
          })
        })
      }
    })
    }
  },
};
</script>

<style lang="scss" scoped>

</style>