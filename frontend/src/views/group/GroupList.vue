<template>
  <div class="container-fluid">
    <!-- 1. OOO선생님의 그룹 리스트 입니다 -->
    <div style="margin-top:13px;">
      <span><h2 style="display:inline-block">박싸피</h2> 선생님의 그룹 리스트 입니다.</span>
      <p>
        관리를 원하는 그룹을 선택해서 해당 그룹을 관리하세요.
      </p>
    </div>

    <!-- 2. 튜토리얼 -->
    <div>
      
    </div>
    <hr>
    <!-- 3. 그룹 리스트 -->
    <div class="group-list">
      <div class="row"> <!-- class="row" -->
        <div class="group-list-card"> <!-- class="col-sm-2" -->
          <div class="card">
            <!-- <div class="more-icon">
              <font-awesome-icon :icon="['fas', 'ellipsis-v']" />
            </div> -->
            <div class="card-body" style="padding-top:60px;padding-bottom:60px;text-align:center">
              <h1 class="mt-1 mb-3">그룹명</h1> <!--  style="display:inline-block;margin-right:10px" -->
              <span class="text-danger">25명</span><br>
              <!-- <button>관리하기</button> -->
            </div>
          </div>
        </div>
        <div class="add-group-card" @click="addGroup()">
          <div class="card">
            <div class="card-body" style="padding-top:73px;padding-bottom:73px;text-align:center">
              <h2 class="mt-1 mb-3" style="display:inline-block;margin-right:10px">그룹 추가하기</h2>
            </div>
          </div>
        </div>
      </div>
      <!-- <div class="row">
        <div class="col-sm-4">
          <div class="card">
            <div class="card-body" style="padding-top:50px;padding-bottom:50px">
              <h1 class="mt-1 mb-3" style="display:inline-block;margin-right:10px">그룹 추가하기</h1>
            </div>
          </div>
        </div>
      </div> -->
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    addGroup(){
      // this.$router.push('/group/addGroup');
    }
  },
}
</script>

<style>

</style>