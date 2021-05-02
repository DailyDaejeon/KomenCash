<template>
  <div>
    <div class="card">
      <div class="card-body">
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
      </div>
    </div>
    <div class="card">
      <div class="card-body">
        <div class="flex-fill">
          <h5 class="card-title mb-0">그룹 삭제</h5>
          <div class="card-body">
            <div class="row">
              <div class="col">
                <h4 class="text-danger font-weight-bold">[Warning!]</h4>
                <p>그룹을 삭제하면 <strong>그룹 내 학생들의 계정을 포함</strong>하여 그룹과 관련된 모든 정보(기본 정보, 활동 데이터 등)는 복구할 수 없습니다. 정말로 삭제하시겠습니까?</p>
                <p>삭제되는 데이터:</p>
                <ul>
                  <li class="delete-item">그룹에 가입한 학생들의 계정</li>
                  <li class="delete-item">그룹 기본 정보</li>
                  <li class="delete-item">그룹 내 활동 데이터(은행, 국회, 상점 등의 내역 및 모든 활동 기록)</li>
                </ul>
                <div>
                  <input type="checkbox" id="acceptDelete" v-model="acceptChk">
                  <label for="acceptDelete">위 주의사항을 모두 확인하였으며, 이에 동의합니다.</label>
                </div>
                <button class="btn btn-secondary float-right" @click="deleteGroup">그룹 삭제</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      mActive: false,
      groupInfo:{},
      acceptChk: false
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
    },
    deleteGroup(){
      if(!this.acceptChk) {
        this.$swal({
          icon: 'warning',
          text: '주의사항을 읽고, 동의여부에 체크해주세요!',
        })
        return;
      }else {
        this.$swal({
          text: '삭제한 데이터는 복구가 불가능합니다. 그룹 삭제를 계속 진행하시겠습니까?',
          showCancelButton: true,
          onfirmButtonText: `예, 그룹을 삭제합니다`,
        }).then((result) => {
          if (result.isConfirmed) {
            //그룹 삭제 api
            this.$swal('그룹이 삭제되었습니다.', '', 'success');
            this.$router.push({name:'GroupList'});
          }
        })
      }
    }
  },
}
</script>

<style>
ul {
  margin-top: 0;
  margin-bottom: 1rem;
  padding-left: 1.5rem;
}

.delete-item {
  margin-top:0.3rem;
}
</style>