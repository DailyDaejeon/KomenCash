<template>
  <div class="container-fluid p-0">
    <h1 class="h3 mb-3 d-inline-block ">세금사용내역</h1>
    <button  @click="addTaxHistory" class="btn btn-main float-right">세금내역추가</button>
    <table class="text-center table table-hover my-0">
      <thead>
        <tr>
          <th>No.</th>
          <th>내역</th>
          <th class="text-center">상태</th>
          <th class="text-center">금액({{groupInfo.monetaryUnitName}})</th>
          <th class="text-center">거래날짜</th>
          <th class="text-center">잔액({{groupInfo.monetaryUnitName}})</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(history,index) in paginatedData" :key="index">
          <td>{{taxHistoryList.length - (index+1+10*(pageNum))}}</td>
          <td>{{history.content}}</td>
          <template v-if="history.balanceChange>=0">
            <td class="text-center"><span class="badge bg-success">입금</span></td>
          </template>
          <template v-else>
          <td class="text-center"><span class="badge bg-danger">출금</span></td>
          </template>
          <td class="text-center">{{priceToString(history.balanceChange)}}</td>
          <td class="text-center">{{history.createdDate.slice(0,10)}}</td>
          <td class="text-center">{{priceToString(history.balance)}}</td>
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
import { addTaxData } from '@/api/tax';

export default {
  props :['taxHistoryList','groupInfo'],
  data() {
    return {
      pageSize:10,
      pageNum:0,
    }
  },
  computed : {
    pageCount() {
      if (this.taxHistoryList.length) {
        let listLeng = this.taxHistoryList.length,
            listSize = this.pageSize,
            page = Math.floor(listLeng / listSize);

        if(listLeng % listSize > 0) page += 1;

        return page;
      }
      return 0
    },
    paginatedData() {
      if (this.taxHistoryList.length) {
        const start = this.pageNum * this.pageSize,
              end = start + this.pageSize;
        return this.taxHistoryList.slice(start, end);
      } 
      return []
    }
  },
  methods: {
    priceToString(price) {
      return price.toLocaleString('ko-KR')
    },
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
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
  },
};
</script>

<style lang="scss" scoped>

</style>