<template>
  <div class="card flex-fill">
    <button class="btn btn-main" @click="alertDetail">자세히</button>
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
          <td>{{financialList[student.studentCreditGrade-1].rate}}</td>
          <td>{{student.startDate}}</td>
          <td>{{student.principal}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { fetchDetailFinancial, modifyDetailFinancial, modifyFinancial } from '@/api/bank';
import { mapState } from 'vuex';
export default {
  props:['propsData','dataName'],
  data() {
    return {
      financialList : [],
      financialName: '',
      studentList:[],
      financialId:0
    }
  },
  created() {
    this.fetchFinList()
  },
  computed: {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    })
  },
  methods: {
    async fetchFinList() {
      const res = await fetchDetailFinancial(this.propsData.id)
      this.financialList = res.data.financialProductDetailResponse
      this.studentList = res.data.studentFindFinancialInfoResponse
      this.financialName = res.data.name
      this.financialId = res.data.id
    },
    alertDetail() {
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