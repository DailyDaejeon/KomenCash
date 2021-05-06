<template>
  <div class="card">
    <div class="card-body">
      <div class="flex-fill">
        <h5 class="card-title mb-0">Group Info</h5>
        <div class="card-body">
          <div class="row">
            <div class="col">
              <table class="group-info-table">
                <tr>
                  <th>이름</th>
                  <td class="p-3" v-if="!mActive">{{memberInfo.nickname}}</td>
                  <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputGroupname" v-model="memberName"></td>
                </tr>
                <tr v-if="!mActive">
                  <th>잔고</th>
                  <td class="p-3">{{memberInfo}}</td>
                </tr>
                <tr>
                  <th>자격증</th>
                  <td class="p-3" v-if="!mActive">{{memberInfo}}</td>
                  <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputMonetaryUnitName" v-model="memberMoney"></td>
                </tr>
                <tr>
                  <th>직업</th>
                  <td class="p-3" v-if="!mActive">{{memberInfo.job.name}}</td>
                  <td class="p-3" v-else><input type="text" class="form-control d-inline-block ml-2 w-50" id="inputTaxRate" v-model="jobName"></td>
                </tr>
                <tr v-if="!mActive">
                  <th>예금</th>
                  <td class="p-3">{{memberFinancial}}</td>
                </tr>
                <tr v-if="!mActive">
                  <th>주식</th>
                  <td class="p-3">{{memberStock}}</td>
                </tr>
                <tr v-if="!mActive">
                  <th>신용등급</th>
                  <td class="p-3">{{memberInfo}}</td>
                </tr>
                <tr v-if="!mActive">
                  <th>경위서</th>
                  <td class="p-3">{{memberInfo}}</td>
                </tr>
                <tr v-if="!mActive">
                  <th>상품 구매내역</th>
                  <td class="p-3">{{memberInfo}}</td>
                </tr>
                <tr v-if="!mActive">
                  <th>비밀번호</th>
                  <td class="p-3">
                    <button class="btn btn-main" @click="resetPW(student.id)">초기화하기</button>
                  </td>
                </tr>
                <tr v-if="!mActive">
                  <th>그룹 탈퇴</th>
                  <td class="p-3">
                    <button class="btn btn-main" @click="deleteMember(memberInfo.id)">탈퇴하기</button>
                  </td>
                </tr>
              </table>
            </div>
          </div>
          <button class="btn btn-primary" @click="submitModiInfo" v-if="this.mActive">Save changes</button>
          <button class="btn btn-main" @click="modiGroupInfo" v-if="!mActive">수정</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { deleteGroupMember, fetchGroupMemberDetail, fetchMemberBalance, fetchMemberFinancial, fetchMemberStockDeal, resetGroupMemberPw } from '@/api/student';
import { mapState } from 'vuex';

export default {
  props: ['id','propsData','dataName'],
  data() {
    return {
      mActive: false,
      acceptChk: false,
      memberName:'',
      memberMoney: 0,
      jobName:'',
      memberInfo:[],
      memberFinancial:[],
      memberStock:[]
    }
  },
  created() {
    this.fetchInfo();
  },
  computed: {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    })
  },
  methods: {
    async fetchInfo(){
      const res = await fetchGroupMemberDetail(this.id)
      const remain = await fetchMemberBalance(this.id)
      const financial = await fetchMemberFinancial(this.id)
      const stock = await fetchMemberStockDeal(this.id)
      this.memberInfo = res.data
      this.memberMoney = remain.data
      this.memberFinancial = financial.data
      this.memberStock = stock.data
    },
    modiGroupInfo() {
      this.mActive = !this.mActive;
      // 직업 변경은 무직으로만 바꿀 수 있게 함!
    },
    deleteMember(sId) {
      deleteGroupMember(sId)
    },
    resetPW(sId) {
      resetGroupMemberPw(sId)
      this.$swal({
        icon: 'info',
        text: '비밀번호가 1234로 초기화 됐습니다!',
      })
    }
  }
}
</script>

<style>

</style>