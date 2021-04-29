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
          <td>혜림이 {{history.name}} 간식</td>
          <td><span class="badge bg-danger">출금</span></td>
          <td>100</td>
          <td>2021.01.01</td>
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
export default {
  data() {
    return {
      taxHistoryList : [
        {
          id:0,
          name:"김싸피"
        },
        {
          id:1,
          name:"박싸피"
        }
      ]
    };
  },
  mounted() {
    
  },
  methods: {
    addTaxHistory() {
      this.$swal.queue([
        {
          title: '세금내역추가 1단계',
          input: 'text',
          text: '내용을 작성해주세요.',
          inputPlaceholder: '혜림이 간식비용',
          confirmButtonText: 'Next &rarr;',
          showCancelButton: true,
          progressSteps: ['1', '2','3'],
          inputValidator: (result) => {
            return !result && '내용을 작성해주세요!'
          }
        },
        {
          title: '세금내역추가 2단계',
          input: 'text',
          text: '금액을 작성해주세요',
          inputPlaceholder: '100',
          confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps:['1', '2','3'],
        inputValidator: (result) => {
            return !result && '금액을 지정해주세요!'
          }
        },
        {
          title: '세금내역추가 3단계',
          input: 'text',
          text: '날짜를 작성해주세요',
          inputPlaceholder: '2021.04.29',
          confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps:['1', '2','3'],
        inputValidator: (result) => {
            return !result && '날짜를 지정해주세요!'
          }
        },
        ]).then((result) => {
        if (result.value) {
          const answers = JSON.stringify(result.value)
        this.$swal({
            title: '주식이 생성됐어요!',
            html: `
              Your answers:
              <pre><code>${answers}</code></pre>
            `,
            confirmButtonText: 'Lovely!'
          })
        }
        })
    },
    removeHistory(history, index) {
      this.taxHistoryList.splice(index,1);
      console.log(history,'지우기')
    }
  },
};
</script>

<style lang="scss" scoped>

</style>