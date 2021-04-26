<template>
  <div class="w-100">
    <div class="row">
      <div class="col-4 col-sm-6">
        <RequestItem RequestType="이력서"/>
      </div>
      <div class="col-4 col-sm-6">
        <RequestItem RequestType="직업추가"/>
      </div>
    </div>
  </div>
</template>

<script>
import RequestItem from '../main/RequestItem.vue'
export default {
  components: { RequestItem },

}
</script>

<style>

</style>