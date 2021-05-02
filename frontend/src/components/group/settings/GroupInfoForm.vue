<template>
  <div class="flex-fill">
    <h5 class="card-title mb-0">Group Info</h5>
    <div class="card-body">
      <div class="row">
        <div class="col">
          <table class="group-info-table">
            <tr>
              <th>그룹명</th>
              <td class="p-3" v-if="!this.mActive">{{groupInfo.groupName}}</td>
              <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputGroupname" v-model="groupInfo.groupName"></td>
            </tr>
            <tr>
              <th>화폐단위</th>
              <td class="p-3" v-if="!this.mActive">{{groupInfo.monetaryUnitName}}</td>
              <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputMonetaryUnitName" v-model="groupInfo.monetaryUnitName"></td>
            </tr>
            <tr>
              <th>세율</th>
              <td class="p-3" v-if="!this.mActive">{{groupInfo.taxRate}}(%)</td>
              <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputTaxRate" v-model="groupInfo.taxRate"></td>
            </tr>
            <tr v-if="!this.mActive">
              <th>그룹원</th>
              <td class="p-3" @click="goMemberInfo">{{gCnt}} 명</td>
            </tr>
          </table>
        </div>
      </div>
      <button class="btn btn-primary" @click="submitModiInfo" v-if="this.mActive">Save changes</button>
      <button class="btn btn-main" @click="modiGroupInfo" v-if="!this.mActive">수정</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      mActive: false,
      groupInfo:{},
    }
  },
  created() {
    this.fetchInfo();
    this.getMemberCnt();
  },
  methods: {
    fetchInfo(){
      this.groupInfo = {
        groupName: "햇반",
        monetaryUnitName: "미소",
        taxRate:0
      }
    },
    getMemberCnt(){
      //학생 조회 api 받고
      this.gCnt = 1;
    },
    modiGroupInfo() {
      this.mActive = !this.mActive;
    },
    submitModiInfo() {
      //modify api
      this.mActive = !this.mActive;
    },
    goMemberInfo() {
      this.$router.push({ name:'groupMemberList' });
    }
  },
}
</script>

<style>

</style>