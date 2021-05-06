<template>
  <div class="container-fluid p-0">
    <h1 class="h3 mb-3 d-inline-block ">세금사용내역</h1>
    <button  @click="addTaxHistory" class="btn btn-main float-right">세금내역추가</button>
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>내역</th>
          <th>입출금</th>
          <th>금액(단위:미소)</th>
          <th>거래날짜</th>
          <th>잔액</th>
          <th>내역삭제</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(history,index) in taxHistoryList" :key="index">
          <td>{{history.content}}</td>
          <td><span class="badge bg-danger">출금</span></td>
          <td>{{history.balanceChange}}</td>
          <td>{{history.createdDate.slice(0,10)}}</td>
          <td>100,000,000미소</td>
          <td>
            <button @click="removeHistory(history, index)" class="btn-danger">
            <i class="removeBtn fas fa-trash-alt"></i>
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { addTaxData, deleteTaxData } from '@/api/tax';
export default {
  props :['taxHistoryList','groupInfo'],
  methods: {
    addTaxHistory() {
      this.$swal.queue([
        {
          title: '세금내역추가 1단계',
          input: 'text',
          text: '내용을 작성해주세요.',
          inputPlaceholder: '간식비용',
          confirmButtonText: 'Next &rarr;',
          showCancelButton: true,
          progressSteps: ['1', '2'],
          inputValidator: (result) => {
            return !result && '내용을 작성해주세요!'
          }
        },
        {
          title: '세금내역추가 2단계',
          input: 'number',
          text: '금액을 작성해주세요',
          inputPlaceholder: '100',
          confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps:['1', '2'],
        inputValidator: (result) => {
            return !result && '금액을 지정해주세요!'
          }
        }
        ]).then((result) => {
        if (result.value) {
          const answers ={  
            groupId: this.groupInfo.id,
            content: result.value[0],
            balanceChange: result.value[1]
          }
          addTaxData(answers)
        this.$swal({
            title: '세금내역이 추가됐어요!',
            icon:"success",
            confirmButtonText: 'Lovely!'
          })
        }
        })
    },
    removeHistory(history, index) {
      this.$swal({
        title: '삭제하시겠습니까?',
        text:'해당 세금 내역을 삭제하면 복구를 할 수 없습니다.',
        icon:"warning",
        confirmButtonText: '삭제',
        showCancelButton:true,
        cancelButtonText:'취소',
      }).then((result)=>{
        if(result.value) {
        this.taxHistoryList.splice(index,1);
        console.log(history,'지우기')
        deleteTaxData(history.id)
        }
      })
    }
  },
};
</script>

<style lang="scss" scoped>

</style>