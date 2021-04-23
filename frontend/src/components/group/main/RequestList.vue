<template>
  <div class="row">
    <div class="col-12 col-lg-8 col-xxl-9 d-flex">
      <div class="w-100">
        <div class="row">
          <div class="col-4 col-sm-6">
            <RequestItem RequestType="이력서"/>
          </div>
          <div class="col-4 col-sm-6">
            <RequestItem RequestType="그룹원추가"/>
          </div>
          <div class="col-4 col-sm-6">
            <RequestItem RequestType="경위서"/>
          </div>
        <!-- </div>
      </div>
    </div>
    <div class="col-12 col-lg-8 col-xxl-9 d-flex">
      <div class="w-100">
        <div class="row"> -->
          <div class="col-4 col-sm-6">
            <RequestItem RequestType="직업추가"/>
          </div>
          <div class="col-4 col-sm-6">
            <RequestItem RequestType="법률추가"/>
          </div>
          <div class="col-4 col-sm-6">
            <RequestItem RequestType="상품추가"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import RequestItem from './RequestItem.vue'
export default {
  components: { RequestItem },

}
</script>

<style>

</style>