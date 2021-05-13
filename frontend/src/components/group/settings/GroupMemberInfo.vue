<template>
  <div class="card-body">
    <div class="row">
      <div class="col-6">
        <h3>이름</h3>
      </div>
      <div class="col-6">
        <span class="h3">{{memberInfo.nickname}}</span>
      </div>
      <div class="col-6">
        <h3>비밀번호</h3>
      </div>
      <div class="col-6">
        <span class="h3" >
         <button class="btn btn-main" @click="resetPW(memberInfo.id)">초기화하기</button>
        </span>
      </div>
      <div class="col-6">
        <h3>직업</h3>
      </div>
      <div class="col-6">
        <span class="h3">{{memberInfo.job.name}}</span>
        <span v-if="memberInfo.job.name !== '무직'">
          <button class="ml-3 btn btn-main" @click="jobFire(memberInfo.id)">
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
          <button class="btn btn-main" @click="addCerti">자격증 추가</button>
      </div>
      <div class="col-12">
        <table v-if="memberInfo.certificateSelectResponseList.length" class="table table-hover my-0">
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
              <td><button class="btn btn-main"><i class="fas fa-edit"></i></button></td>
              <td><button class="btn btn-danger"><i class="fas fa-trash-alt"></i></button></td>
            </tr>
          </tbody>
        </table>
        <p class="h5" v-else>
          등록된 자격증이 없습니다.
        </p>
      </div>
      <div class="col-6">
        <h3>예금</h3>
      </div>
      <div class="col-12">
        <table v-if="memberFinancial.length" class="table table-hover my-0">
          <thead>
            <tr>
              <th>예금상품명</th>
              <!-- <th>상태</th> -->
              <th>시작일</th>
              <th>만기일</th>
              <th>원금</th>
              </tr>
          </thead>
          <tbody>
            <tr v-for="(product,index) in memberFinancial" :key="index"
            v-show="product.status === 'deposit'"
            >
              <td>{{product.financialProductName}}</td>
              <td>{{product.startDate.slice(0,10)}}</td>
              <td>{{product.endDate.slice(0,10)}}</td>
              <td>{{product.principal}}</td>
            </tr>
          </tbody>
        </table>
        <p class="h5" v-else>
          가입한 예금상품이 없습니다.
        </p>
      </div>
      <div class="col-6">
        <h3>주식</h3>
      </div>
      <div class="col-12">
        <table v-if="memberStock.length" class="table table-hover my-0">
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
        <p class="h5" v-else>
          매수한 주식이 없습니다.
        </p>
      </div>
      
      <div class="col-6">
        <h3>경위서</h3>
      </div>
      <div class="col-12">
        <table v-if="memberCaseList.length" class="table table-hover my-0">
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
        <p class="h5" v-else>
          사고를 친 적이 없습니다.
        </p>
      </div>
      <div class="col-6">
        <h3>상품 구매내역</h3>
      </div>
      <div class="col-12">
        <table v-if="memberShopList.length" class="table table-hover my-0">
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
        <p class="h5" v-else>
          상점에서 구매한 내역이 없습니다.
        </p>
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
  </div>
</template>

<script>
import { deleteGroupMember, fetchGroupMemberDetail, fetchGroupMemeberCase, fetchGroupMemeberStoreHistory, fetchMemberBalance, fetchMemberCredit, fetchMemberFinancial, fetchMemberStockDeal, modifyGroupMemberJobFire, resetGroupMemberPw } from '@/api/student';
import { mapState } from 'vuex';
import { addCertiIssue, fetchCertiList } from '@/api/certificate';

export default {
  props: ['id','propsData','dataName'],
  data() {
    return {
      acceptChk: false,
      memberName:'',
      memberMoney: 0,
      jobName:'',
      memberInfo:[],
      memberFinancial:[],
      memberStock:[],
      memberCredit:0,
      memberCaseList : [],
      memberShopList: [],
      groupCertiList : [],
      groupCertiName:[]
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
      const certi = await fetchCertiList(this.groupInfo.id)
      
      this.memberInfo = res.data
      this.memberMoney = remain.data
      this.memberFinancial = financial.data
      this.memberStock = stock.data
      this.memberCredit = credit.data
      this.memberCaseList = caseList.data
      this.memberShopList = shop.data
      this.groupCertiList = certi.data
      certi.data.forEach((el)=>{
        const name= `${el.name}(${el.acquisitionCondition})`
        this.groupCertiName.push(name)
      })
    },
    jobFire(sId) {
      this.$swal({
        icon: 'info',
        text: `${this.memberInfo.nickname}님의 직업을 ${this.memberInfo.job.name}에서 '무직'으로 변경 하시겠습니까?`,
        confirmButtonText:'변경',
        showCancelButton: true,
        cancelButtonText:'취소'
      }).then((res)=>{
        if (res.value) {
          modifyGroupMemberJobFire(sId).then(() => {
            this.$swal({
              icon: 'success',
              text: `'${this.memberInfo.nickname}님의 직업이 '무직'으로 변경됐습니다.'`,
            })
          })
        }
      })
    },
    addCerti() {
      this.$swal({
        title: '자격증 추가',
        input: 'select',
        inputOptions:this.groupCertiName,
        inputPlaceholder: '추가할 자격증을 선택해 주세요.',
        confirmButtonText: '추가',
        showCancelButton: true,
      }).then((res)=>{
        if (res.value && res.isConfirmed) {
          console.log('자격증추가',this.groupCertiList[Number(res.value)])
          const certi = {
            certificateId:this.groupCertiList[Number(res.value)].id,
            studentId:this.memberInfo.id
          }
          addCertiIssue(certi)
        }
      })
    },
    deleteMember(sId) {
      this.$swal({
        icon: 'info',
        text: `'${this.memberInfo.nickname}님을 ${this.groupInfo.name}에서 탈퇴시키겠습니까?'`,
        confirmButtonText:'탈퇴',
        showCancelButton: true,
        cancelButtonText:'취소'
      }).then((res)=>{
        if (res.value) {
          deleteGroupMember(sId).then(() => {
            this.$swal({
              icon: 'success',
              text: `'${this.memberInfo.nickname}님이 ${this.groupInfo.name}에서 탈퇴됐습니다.'`,
            })
          })
        }
      })
    },
    resetPW(sId) {
      resetGroupMemberPw(sId)
      this.$swal({
        icon: 'info',
        text: '비밀번호를 초기화 하시겠습니까?',
        confirmButtonText:'초기화',
        showCancelButton: true,
        cancelButtonText:'취소'
      }).then((res)=>{
        if (res.value) {
          this.$swal({
            icon: 'success',
            text: '비밀번호가 1234로 초기화 됐습니다!',
          })
        }
      })
      
    }
  }
}
</script>

<style>

</style>