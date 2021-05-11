<template>
  <div class="card-body">
    <div class="row">
      <div class="col-6">
        <h3>이름</h3>
      </div>
      <div class="col-6">
        <span class="h3" v-if="!mActive">{{memberInfo.nickname}}</span>
        <span v-else>
          <input type="text" class="form-control d-inline-block ml-2 w-50" id="inputGroupname" v-model="memberName">
        </span>
      </div>
      <div class="col-6">
        <h3>비밀번호</h3>
      </div>
      <div class="col-6">
        <span class="h3" >
         <button class="btn btn-main" @click="resetPW(student.id)">초기화하기</button>
        </span>
      </div>
      <div class="col-6">
        <h3>직업</h3>
      </div>
      <div class="col-6">
        <span class="h3" v-if="!mActive">{{memberInfo.job.name}}</span>
        <span v-else>
          <button class="btn btn-main">
            무직으로 변경
          </button>
        </span>
      </div>
      <div class="col-6">
        <h3>잔고</h3>
      </div>
      <div class="col-6">
        <span class="h3">
          {{memberMoney}} {{groupInfo.monetaryUnitName}}
        </span>
      </div>
      <div class="col-6">
        <h3>신용등급</h3>
      </div>
      <div class="col-6">
        <span class="h3" >
          {{memberCredit.creditGrade}}등급 (신용점수: {{memberCredit.point}})
        </span>
      </div>
      <div class="col-6">
        <h3>자격증</h3>
      </div>
      <div class="col-6">
          <button class="btn btn-main">자격증 추가</button>
      </div>
      <div class="col-12">
        <table v-if="memberInfo.certificateSelectResponseList.name" class="table table-hover my-0">
          <thead>
            <tr>
              <th>자격증명</th>
              <th>수정</th>
              <th>삭제</th>
              </tr>
          </thead>
          <tbody>
            <tr v-for="(certi,index) in memberInfo.certificateSelectResponseList" 
            :key="index">
              <td>{{certi.name}}</td>
              <td><button class="btn btn-main">수정</button></td>
              <td><button class="btn btn-danger">삭제</button></td>
            </tr>
          </tbody>
        </table>
        <span v-else>
          등록된 자격증이 없습니다.
        </span>
      </div>
      <div class="col-6">
        <h3>예금</h3>
      </div>
      <div class="col-12">
        <table class="table table-hover my-0">
          <thead>
            <tr>
              <th>예금상품명</th>
              <th>상태</th>
              <th>시작일</th>
              <th>만기일</th>
              <th>원금</th>
              </tr>
          </thead>
          <tbody>
            <tr v-for="(product,index) in memberFinancial" :key="index">
              <td>{{product.financialProductName}}</td>
              <td>{{product.status}}</td>
              <td>{{product.startDate.slice(0,10)}}</td>
              <td>{{product.endDate.slice(0,10)}}</td>
              <td>{{product.principal}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="col-6">
        <h3>주식</h3>
      </div>
      <div class="col-12">
        <table class="table table-hover my-0">
          <thead>
            <tr>
              <th>주식명</th>
              <th>주가</th>
              <th>수량</th>
              <th>매수일</th>
              </tr>
          </thead>
          <tbody>
            <tr v-for="(product,index) in memberStock" :key="index">
              <td>{{product.stockName}}</td>
              <td>{{product.price}}</td>
              <td>{{product.amount}}</td>
              <td>{{product.createdDate.slice(0,10)}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <div class="col-6">
        <h3>경위서</h3>
      </div>
      <div class="col-12">
        <table class="table table-hover my-0">
          <thead>
            <tr>
              <th>사건명</th>
              <th>내용</th>
              <th>벌금</th>
              <th>신청여부</th>
              </tr>
          </thead>
          <tbody>
            <tr v-for="(caseItem,index) in memberCaseList" :key="index">
              <td>{{caseItem.policeNickname}}</td>
              <td>{{caseItem.content}}</td>
              <td>{{product.fine}}</td>
              <td>{{product.accept}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="col-6">
        <h3>상품 구매내역</h3>
      </div>
      <div class="col-12">
        <table class="table table-hover my-0">
          <thead>
            <tr>
              <th>상품명</th>
              <th>가격</th>
              <th>구매일</th>
              </tr>
          </thead>
          <tbody>
            <tr v-for="(shop,index) in memberShopList" :key="index">
              <td>{{shop.name}}</td>
              <td>{{shop.price}} {{groupInfo.monetaryUnitName}}</td>
              <td>{{shop.perchaseDate.slice(0,10)}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      
      <div class="col-6">
        <h3>그룹 탈퇴</h3>
      </div>
      <div class="col-6">
        <span class="h3" >
          <button class="btn btn-main" @click="deleteMember(memberInfo.id)">탈퇴하기</button>
        </span>
      </div>

        
    </div>
    <button class="btn btn-primary" @click="submitModiInfo" v-if="this.mActive">Save changes</button>
    <button class="btn btn-main" @click="modiGroupInfo" v-if="!mActive">수정</button>
  </div>
</template>

<script>
import { deleteGroupMember, fetchGroupMemberDetail, fetchGroupMemeberCase, fetchGroupMemeberStoreHistory, fetchMemberBalance, fetchMemberCredit, fetchMemberFinancial, fetchMemberStockDeal, resetGroupMemberPw } from '@/api/student';
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
      memberStock:[],
      memberCredit:0,
      memberCaseList : [],
      memberShopList: []
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
      const credit = await fetchMemberCredit(this.id)
      const caseList = await fetchGroupMemeberCase(this.id)
      const shop = await fetchGroupMemeberStoreHistory(this.id) 
      
      this.memberInfo = res.data
      this.memberMoney = remain.data
      this.memberFinancial = financial.data
      this.memberStock = stock.data
      this.memberCredit = credit.data
      this.memberCaseList = caseList.data
      this.memberShopList = shop.data
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