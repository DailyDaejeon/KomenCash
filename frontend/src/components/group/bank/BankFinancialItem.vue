<template>
  <div class="card flex-fill">
    <button class="btn btn-main" @click="alertDetail">자세히</button>
    {{propsData}}
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>이름</th>
          <th>신용등급</th>
          <th>금리</th>
          <th>기간</th>
        </tr>
      </thead>
      <tbody>
        <tr @click="goDetail(financial.financialProduct.id)" v-for="(financial,index) in studentList" :key="index">
          <td>{{financial.financialProduct.name}}</td>
          <td>{{financial.creditGrade}}</td>
          <td>{{financial.rate}}</td>
          <td>{{financial.period}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { modifyDetailFinancial } from '@/api/bank';
export default {
  props:['propsData','dataName'],
  data() {
    return {
      financialList : this.propsData,
      financialName: this.dataName,
      studentList:this.propsData.studentsList
    }
  },
  created() {
    // console.log('???',this.propsData)
  },
  methods: {
    alertDetail() {
      this.$swal({
        title: this.propsData.name+' 의 수정',
        confirmButtonText:'수정',
        showCancelButton:true,
        cancelButtonText:'취소',
        html: 
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">'
        +'<input id="swal-input-name" class="swal2-input" type="text" placeholder=' +`"${this.propsData.name}"`+'>'+'<input id="swal-input-period" class="swal2-input" type="text" placeholder='+`"${this.propsData.period}"`+'>'+'<input id="swal-input1" class="swal2-input-custom" min="0" max="100" type="number" placeholder='+`"${this.propsData.rate}"`+'>' +'</div>',
        focusConfirm: false,
        preConfirm: () => {
          return [
            document.getElementById('swal-input-name').value,
            document.getElementById('swal-input-period').value,
            document.getElementById('swal-input1').value,
          ]
        },
        inputValidator: (result) => {
          return !result && '수정할 정보를 적어주세요!'
        }
      }).then((result) => {
        if (result.value) {
        
          const product = {
            groupId: this.groupInfo.id,
            name: result.value[0],
            acquisitionCondition: result.value[1],
            id:this.propsData.id
          }
          modifyDetailFinancial(product).then(()=>{
            this.fetchCerti()
            this.$swal({
              title: '금융상품을 수정했습니다.',
              icon:'success',
              showConfirmButton: false,
              timer: 1500
            })
          })
        }
      })
    },
  },
};
</script>

<style lang="scss" scoped>

</style>