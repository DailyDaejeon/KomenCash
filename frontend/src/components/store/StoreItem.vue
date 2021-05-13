<template>
  <div class="card cursor-pointer">
    <div class="card-body">

      <h3 v-if="modify" class="card-title mb-4">{{product.name}}</h3>
      <input v-else v-model="productName" class="h3 border border-main card-title mb-4" type="text" :placeholder="product.name">

      <h3 v-if="modify" class="mt-1 mb-3">{{product.price}} {{moneyUnit}}</h3>
      <input v-else v-model="productPrice" class="h3 mt-1 mb-3 border border-main" type="text" :placeholder="product.price">

      <div class="mb-1">
        <span v-if="modify" class="text-primary mr-3" @click="modifyStoreItem"><i class="fas fa-pencil-alt"></i>수정</span>
        <span v-else class="text-primary mr-3" @click="completeModify(product)"><i class="fas fa-pencil-alt"></i>수정완료</span>
        
        <span 
        @click="deleteItem(product.id)"
        class="text-danger"><i class="fas fa-trash-alt"></i>삭제</span>
      </div>
    </div>
  </div>
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
      console.log('수정내역',product,'-->',item)
      modifyStoreProduct(item)
      this.modify = true;

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