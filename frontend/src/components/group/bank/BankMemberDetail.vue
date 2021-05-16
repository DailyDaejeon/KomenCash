<template>
  <div class="card flex-fill">
    <div class="card-header">
      <h5 class="card-title mb-0">{{propsData.nickname}}님의 은행거래내역
        <button @click="alertSend" class="float-right btn btn-main">입금</button>
      </h5>
    </div>
    <table class="text-center table table-hover my-0">
      <thead>
        <tr>
          <th>No.</th>
          <th>거래내역</th>
          <th>입출금</th>
          <th>금액({{groupInfo.monetaryUnitName}})</th>
          <th>잔액({{groupInfo.monetaryUnitName}})</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(history,index) in paginatedData" :key="index">
          <td>{{reverseData.length - (index+10*(pageNum))}}</td>
          <td>{{history.content}}</td>
          <td v-if="history.balanceChange >= 0"><span class="badge bg-success">입금</span></td>
          <td v-else><span class="badge bg-danger">출금</span></td>
          <td>{{priceToString(history.balanceChange)}}</td>
          <td>{{priceToString(history.balance)}}</td>
        </tr>
      </tbody>
    </table>
    <div  v-if="paginatedData.length" class="btn-cover text-center">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn mr-3">이전</button>
      <span class="page-count mr-3">{{pageNum+1}}/{{pageCount}} 페이지 </span>
      <button :disabled="pageNum >= pageCount-1"  @click="nextPage" class="page-btn">다음</button>
  </div>
  </div>
</template>

<script>
import { sendMoney } from '@/api/bank';
import { mapState } from 'vuex'
import { addTaxData } from '@/api/tax';
export default {
  props:['propsData','dataName','id'],
  data() {
    return {
      reverseData:[],
      pageSize:10,
      pageNum:0,
      studentList : this.propsData,
      studentName: this.dataName,
    }
  },
  created() {
    this.fetchData()
  },
  computed:{
    ...mapState({
      groupInfo:state => state.group.groupInfo
    }),
    pageCount() {
      let listLeng = this.reverseData.length,
          listSize = this.pageSize,
          page = Math.floor(listLeng / listSize);

      if(listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
            end = start + this.pageSize;
      return this.reverseData.slice(start, end);
    }
  },
  methods: {
    fetchData() {
      this.reverseData = this.propsData.accountHistory.reverse()
    },
    priceToString(price) {
      return price.toLocaleString('ko-KR')
    },
    alertSend() {
      this.$swal({
        title:`${this.propsData.nickname}님에게 입금`,
        html:
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">입금 내역을 적어주세요.</div>'+'<input id="swal-input-content" class="swal2-input" type="text" placeholder="방과후 청소 아르바이트">'
        +'<input id="swal-input-balance" class="swal2-input" type="number" placeholder="100">'+
        '<label class="ml-2">'+
        '<input id="swal-input-tax" type="checkbox" value="useTax">세금에서 출금할까요?</label>',
        focusConfirm: false,
        preConfirm: () => {
          return [
            document.getElementById('swal-input-content').value,
            document.getElementById('swal-input-balance').value,
            document.getElementById('swal-input-tax').checked,
          ]
        },
        confirmButtonText: '입금',
        showCancelButton: true,
      }).then((res)=>{
        console.log('swal',res)
        if (res.value[0] && res.value[1] && res.isConfirmed) {
          if (res.value[2]) {
            console.log('세금에서도 나감')
            const taxData = {
              balanceChange: Number(res.value[1])*(-1),
              content: res.value[0],
              groupId: this.groupInfo.id
            }
            addTaxData(taxData)
          } 
          const sendData = {
            balanceChange: res.value[1],
            content: res.value[0],
            studentId: this.propsData.student_id
          }
          sendMoney(sendData).then(()=>{
            console.log('1여긴 BankMemberDetail')
            this.$emit('updateData',this.propsData.student_id)
            this.$swal({
              title:'입금이 완료되었습니다.',
              icon:'success',
              timer:1500
            })
            // window.location.reload()
            // this.$router.push({name:"BankMemberDetail",params:{id:`${this.id}`}})
          })
          
        } else {
          this.$swal({
            title:'빈칸을 모두 채워 주세요.',
            icon:'error',
            timer:1500
          })
        }
      })
    },
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
  }
}
</script>

<style>

</style>