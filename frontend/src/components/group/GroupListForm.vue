<template>
  <div class="container-fluid">
    <!-- 1. OOO선생님의 그룹 리스트 입니다 -->
    <div style="margin-top:13px;">
      <span><h2 style="display:inline-block">{{userInfo}}</h2> 선생님의 그룹 리스트 입니다.</span>
      <p>
        관리를 원하는 그룹을 선택해서 해당 그룹을 관리하세요.
      </p>
    </div>

    <!-- 2. 튜토리얼 -->
    <div>
    </div>
    <hr>
    <!-- 3. 그룹 리스트 -->
    <div class="group-list" v-if="groupList.length != 0">
      <div class="row">
        <div class="group-list-card" v-for="(group,index) in groupList" :key="index">
          <div class="card">
            <div class="card-body" style="padding-top:60px;padding-bottom:60px;text-align:center">
              <h1 class="mt-1 mb-3">{{group.groupName}}</h1>
              <span class="text-danger">{{group.totalCnt}}명</span><br>
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
    </div>
    <div class="group-list" v-else>
      <div class="row not-group">
        <h2>아직 생성한 그룹이 없습니다.</h2>
        <button @click="addGroup()">그룹 생성하기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props:['userInfo', 'groupList'],
  methods: {
    addGroup(){
      this.$router.push('/group/add');
    }
  },
}
</script>

<style>

</style>