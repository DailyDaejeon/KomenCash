<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">온라인스토어</h1>
      <ListItem listType="store" :propsData="storeMenu"/>
    </div>
  </main>
</template>

<script>
import ListItem from '@/components/group/ListItem.vue'
export default {
  components: { ListItem },
  data() {
    return {
      storeMenu : [
        {
          name:"상품리스트"
        },
        {
          name:"상품 거래내역"
        }
      ]
    }
  },
  watch:{
    $route() {
      // window.location.reload();
    }
  }
}
</script>

<style>

</style>