<template>
  <div class="card flex-fill">
    <div class="border border-main card flex-fill">
      <div class="card-header">
        <h5 class="card-title mb-0">{{ financialName }} 신청 요청</h5>
      </div>
      <table class="table table-hover my-0">
        <thead>
          <tr>
            <th>요청인</th>
            <th>신청내용</th>
            <th>수락/거절</th>
          </tr>
        </thead>
        <tbody v-if="requestList.length">
          <tr v-for="(request,index) in requestList"
          :key="index">
            <td>{{request.nickname}}</td>
            <td v-if="request.status === 'before_termination'">
              <span class="badge bg-danger">중도해지</span>
            </td>
            <td v-else>
              <span class="badge bg-success">가입신청</span>
            </td>
            <td><button class="btn btn-main" @click="acceptRequest(request)">O</button>
            </td>
          </tr>
        </tbody>
      </table>
      <p class="h4 text-center m-1" v-if="!requestList.length">
      요청 내역이 없습니다.
      </p>
    </div>
    <div class="row">
      <div class="col-6">
        <h3>예금명</h3>
      </div>
      <div class="col-6">
        <span class="h3" v-if="!mActive">{{financialName}}</span>
        <span class="h3" v-else>
          <input class="border border-main" type="text" v-model="financialName">
        </span>
      </div>
      <div class="col-6">
        <h3>기간</h3>
      </div>
      <div class="col-6">
        <span class="h3" v-if="!mActive">{{financialPeriod}}</span>
        <span class="h3" v-else>
          <input class="border border-main" type="text" v-model="financialPeriod">
        </span>
      </div>
      <div class="col-6">
        <h3>신용등급별 금리</h3>
      </div>
      <div class="col-12">
        <table class="table table-hover my-0">
          <thead>
            <tr>
              <th>신용등급</th>
              <th>금리</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(credit,index) in financialList" :key="index">
              <td v-if="!mActive">{{financialCredit[index]}}급</td>
              <td v-else>
                 <input class="border border-main" type="text" v-model="financialCredit[index]">
              </td>
              <td v-if="!mActive">{{financialRateList[index]}}%</td>
              <td v-else>
                <input class="border border-main" type="text" v-model="financialRateList[index]">
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <button class="btn btn-main" @click="modifyDetail">수정</button>
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>이름</th>
          <th>신용등급</th>
          <th>금리</th>
          <th>기간</th>
          <th>금액</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(student,index) in studentList" :key="index">
          <td>{{student.studentNickname}}</td>
          <td>{{student.studentCreditGrade}}</td>
          <td>{{studentRateList[index]}}</td>
          <td>{{student.startDate.slice(0,10)}}</td>
          <td>{{student.principal}}</td>
        </tr>
      </tbody>
    </table>
    <button class="btn btn-danger" @click="deleteProduct">삭제</button>
  </div>
</template>

<script>
import { acceptFinancialRequest, deleteFinancial, fetchDetailFinancial,  fetchFinancialRequest,  modifyDetailFinancial, modifyFinancial } from '@/api/bank';
import { mapState } from 'vuex';
export default {
  props:['propsData','dataName'],
  data() {
    return {
      mActive:false,
      financialList : [],
      financialName: '',
      studentList:[],
      financialId:0,
      financialPeriod:0,
      studentRateList : [],
      financialCredit : [],
      financialRateList : [],
      requestList:[]
    }
  },
  created() {
    this.fetchFinList()
  },
  computed: {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    }),
    
  },
  methods: {
    async fetchFinList() {
      console.log('예금 item',this.propsData)
      const res = await fetchDetailFinancial(this.propsData.id)
      this.financialList = res.data.financialProductDetailResponse
      const req = await fetchFinancialRequest(this.propsData.id)
      this.requestList = req.data
      this.studentList = res.data.studentFindFinancialInfoResponse
      this.financialName = res.data.name
      this.financialId = res.data.id
      this.financialPeriod = this.financialList[0].period

      const credit = []
      const creditRate = []
      this.financialList.forEach((el) => {
        credit.push(el.creditGrade)
        creditRate.push(el.rate)
      })
      this.financialCredit = credit
      this.financialRateList = creditRate
      this.studentList.forEach((el) => {
        const temp = []
        let flag = false;
        for (let i = 0; i < this.financialList.length; i++) {
          const element = this.financialList[i];
          if (!flag && el.studentCreditGrade === element.creditGrade) {
            temp.push(element.rate)
            flag = true
          }
        }
        this.studentRateList = temp
      })
      console.log(this.studentRateList)
      // console.log(this.financialList,this.studentList)
    },
    acceptRequest(request) {
      // 수정으로 뭘보내주지? + 금융상품신청조회 학생아이디 보내야되나?
      acceptFinancialRequest(request.id)
    },
    deleteProduct() {
      this.$swal({
        title:'금융상품을 삭제하시겠습니까?',
        icon:'info',
        text:'삭제 시 복구 하실 수 없습니다.',
        confirmButtonText:'삭제',
        showCancelButton:true,
        cancelButtonText:'취소'
      }).then((res) => {
        if (res.isConfirmed) {
          deleteFinancial(this.financialId).then(() => {
            this.$swal({
              title:'성공적으로 삭제 됐습니다.',
              icon:'success'
            })
          })
        }
      })
    },
    financialRate(student) {
      this.financialList.forEach((el) => {
        if (el.creditGrade === student.creditGrade) {
          return el.rate
        }
        return 0
      })
    },
    modifyDetail() {
      this.mActive = !this.mActive
      if (!this.mActive) {
        const fData = {
          groupId: this.groupInfo.id,
          id: this.financialId,
          name: this.financialName
        }
        modifyFinancial(fData)
        for (let i = 0; i < this.financialList.length; i++) {
          const element = this.financialList[i];
          const fDetail = {
          rate: this.financialRateList[i],
          id: element.id,
          period: this.financialPeriod,
          creditGrade: this.financialCredit[i]
        }
        modifyDetailFinancial(fDetail)
        }
        
      }
    },
    alertDetail() {
      console.log(this.financialList)
      this.$swal({
        title: this.financialName+' 의 수정',
        confirmButtonText:'수정',
        showCancelButton:true,
        cancelButtonText:'취소',
        html: 
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">'
        +'<input id="swal-input-name" class="swal2-input" type="text" placeholder=' +`"${this.financialName}"`+'>'+'<input id="swal-input-period" class="swal2-input" type="text" placeholder='+`"${this.financialList[0].period}"`+'>'
        +'<input id="swal-input1" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[0].rate}"`+'>'
        +'<input id="swal-input2" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[1].rate}"`+'>' +'<input id="swal-input3" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[2].rate}"`+'>'+'<input id="swal-input4" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[3].rate}"`+'>'+'<input id="swal-input5" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[4].rate}"`+'>'+'<input id="swal-input6" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[5].rate}"`+'>'+'<input id="swal-input7" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[6].rate}"`+'>'+'<input id="swal-input8" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[7].rate}"`+'>'+'<input id="swal-input9" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[8].rate}"`+'>'+'<input id="swal-input10" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.financialList[9].rate}"`+'>' +'</div>',
        focusConfirm: false,
        preConfirm: () => {
          return [
            document.getElementById('swal-input-name').value,
            document.getElementById('swal-input-period').value,
            document.getElementById('swal-input1').value,
            document.getElementById('swal-input2').value,
            document.getElementById('swal-input3').value,
            document.getElementById('swal-input4').value,
            document.getElementById('swal-input5').value,
            document.getElementById('swal-input6').value,
            document.getElementById('swal-input7').value,
            document.getElementById('swal-input8').value,
            document.getElementById('swal-input9').value,
            document.getElementById('swal-input10').value,
          ]
        },
        inputValidator: (result) => {
          return !result && '수정할 정보를 적어주세요!'
        }
      }).then((result) => {
        console.log(result,'???')
        if (result.value) {
          if (result.value[0]) {
              const productName = {
                groupId : this.groupInfo.id,
                id:this.financialId,
                name:result.value[0]
              }
              modifyFinancial(productName).then(()=>{
                this.$swal({
                  title: '금융상품을 수정했습니다.',
                  icon:'success',
                  showConfirmButton: false,
                  timer: 1500
                })
              })
            }
          for (let i = 0; i < this.financialList.length; i++) {
            const element = this.financialList[i];
            const product = {
              creditGrade: Number(i+1),
              period: Number(result.value[1]),
              rate: Number(result.value[2+i]),
              id:Number(element.id)
            }
            modifyDetailFinancial(product).then(()=>{
              this.$swal({
                title: '금융상품을 수정했습니다.',
                icon:'success',
                showConfirmButton: false,
                timer: 1500
              })
            })
        
          }
          
        } else if (result.value.dismiss !==  "cancel") {
          this.$swal({
            title: '예금상품 수정에 실패했습니다.',
            text:'빈칸을 모두 채워주세요!',
            icon:'error',
            showConfirmButton: false,
            timer: 1500
          })
        }
      })
    },
  },
};
</script>

<style lang="scss" scoped>

</style>