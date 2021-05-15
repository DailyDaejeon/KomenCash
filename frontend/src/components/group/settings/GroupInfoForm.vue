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
                    <td class="p-3" v-if="!mActive">{{groupInfo.name}}</td>
                    <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputGroupname" v-model="groupName"></td>
                  </tr>
                  <tr>
                    <th>화폐단위</th>
                    <td class="p-3" v-if="!mActive">{{groupInfo.monetaryUnitName}}</td>
                    <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputMonetaryUnitName" v-model="monetaryUnitName"></td>
                  </tr>
                  <tr>
                    <th>세율</th>
                    <td class="p-3" v-if="!mActive">{{groupInfo.taxRate}}(%)</td>
                    <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputTaxRate" v-model="taxRate"></td>
                  </tr>
                  <tr>
                    <th>물가상승율</th>
                    <td class="p-3" v-if="!mActive">{{groupInfo.inflationRate }}(%)</td>
                    <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputInflationRate" v-model="groupInflationRate"></td>
                  </tr>
                  <tr v-if="!mActive">
                    <th>그룹코드</th>
                    <td class="p-3">{{groupInfo.code}} <button @click="copyCode(groupInfo.code)" class="ml-2 btn btn-main"><i class="fas fa-copy"></i> 그룹코드복사</button></td>
                  </tr>
                  <tr v-if="!mActive">
                    <th>그룹원</th>
                    <td class="p-3" @click="goMemberInfo">{{groupMemberCnt}} 명</td>
                  </tr>
                </table>
              </div>
            </div>
            <button class="btn btn-primary" @click="submitModiInfo" v-if="this.mActive"> 변경내역저장</button>
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
                <button class="btn btn-secondary float-right" @click="deleteGroupInfo">그룹 삭제</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { deleteGroup, modifyGroup } from '@/api/group';
import { mapState } from 'vuex';
import { modifyInfRate } from '@/api/tax';

export default {
  data() {
    return {
      mActive: false,
      acceptChk: false,
      groupName:'',
      groupInflationRate: '',
      monetaryUnitName: '',
      groupTax:'',
      taxRate:'',
      // userInfo:'',
    }
  },
  created() {
    this.fetchInfo();
  },
  computed: {
    ...mapState({
      groupInfo : state => state.group.groupInfo,
      groupMemberCnt : state => state.group.groupMemberCnt,
      userInfo: state=> state.user.userInfo
    })
  },
  methods: {
    fetchInfo(){
      console.log(this.groupInfo)
      this.groupName = this.groupInfo.name
      this.groupInflationRate= this.groupInfo.inflationRate 
      this.monetaryUnitName= this.groupInfo.monetaryUnitName
      // this.groupTax=this.groupInfo.tax
      this.taxRate=this.groupInfo.taxRate
      // this.userInfo=this.groupInfo.teacher
    },
    modiGroupInfo() {
      this.mActive = !this.mActive;
    },
    submitModiInfo() {
      //modify api
      const groupInfo = {
        code: this.groupInfo.code,
        id: this.groupInfo.id,
        inflationRate: this.groupInflationRate ,
        monetaryUnitName: this.monetaryUnitName,
        name: this.groupName,
        taxRate: this.taxRate,
        teacherId: this.userInfo.id
      }
      const infData = {
        groupId : this.groupInfo.id,
        taxRate: this.groupInflationRate 
      }
      modifyInfRate (infData)
      console.log(groupInfo)
      modifyGroup(groupInfo)
      this.$store.commit('setGroupInfo',groupInfo)
      this.fetchInfo()
      this.mActive = !this.mActive;
    },
    goMemberInfo() {
      this.$router.push({ name:'groupMemberList' });
    },
    deleteGroupInfo(){
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
            deleteGroup(this.groupInfo.id)
            this.$swal('그룹이 삭제되었습니다.', '', 'success');
            this.$router.push({name:'GroupList'});
          }
        })
      }
    },
    copyToClipboard(code) {
      const text = document.createElement('textarea');
      document.body.appendChild(text);
      text.value = code;
      text.select();
      document.execCommand('copy');
      document.body.removeChild(text)
    },
    copyCode(code) {
      this.copyToClipboard(code)
      console.log('복사됨!!')
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