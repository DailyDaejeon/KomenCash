<template>
  <main class="content">
    <button class="mr-3 btn btn-main"  @click="addFinancial">예금상품추가</button>
    <BankItem bankType="financial" :propsData="financialData"/>
  </main>
</template>

<script>
import BankItem from '@/components/group/bank/BankItem.vue';
export default {
  components: { BankItem },
  data() {
    return {
      financialData : [
        {
          id:3,
          name:"새싹적금",
          studentsList : [{
            id:0,
            name:"김싸피",
            rate:"10%"
          },
          {
            id:1,
            name:"박싸피",
            rate:"20%"
          }]
        },
        {
          id:4,
          name:"짧고굵게예금",
          studentsList : [{
            id:2,
            name:"최싸피",
            rate:"30%"
          },
          {
            id:3,
            name:"정싸피",
            rate:"40%"
          }]
        }
      ]
    }
  },
  methods: {
    addFinancial() {
      this.$swal.queue([
      {
        title: '예금상품추가 1단계',
        input: 'text',
        text: '상품명을 지정해주세요.',
        inputPlaceholder: '새싹적금',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2', '3','4'],
        inputValidator: (result) => {
          return !result && '상품명을 지정해주세요!'
        }
      },
      {
        title: '예금상품추가 2단계',
        input: 'textarea',
        text: '상품설명을 작성해주세요.',
        inputPlaceholder: '이상품은 블라블라블라',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3','4'],
      inputValidator: (result) => {
          return !result && '상품설명을 작성해주세요!'
        }
      },
      {
        title: '예금상품추가 3단계',
        input: 'text',
        text: '기간을 적어주세요.',
        inputPlaceholder: '28',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3','4'],
      inputValidator: (result) => {
          return !result && '기간을 적어주세요!'
        }
      },
      {
        title: '예금상품추가 4단계',
        html:
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">신용등급별 이율을 적어주세요.</div>'+'<input id="swal-input1" class="swal2-input-custom" min="0" max="100" type="number" placeholder="1등급">' +
        '<input id="swal-input2" class="swal2-input-custom" min="0" max="100" type="number" placeholder="2등급">'+
        '<input id="swal-input3" class="swal2-input-custom" min="0" max="100" type="number" placeholder="3등급">'+
        '<input id="swal-input4" class="swal2-input-custom" min="0" max="100" type="number" placeholder="4등급">'+
        '<input id="swal-input5" class="swal2-input-custom" min="0" max="100" type="number" placeholder="5등급">'+
        '<input id="swal-input6" class="swal2-input-custom" min="0" max="100" type="number" placeholder="6등급">'+
        '<input id="swal-input7" class="swal2-input-custom" min="0" max="100" type="number" placeholder="7등급">'+
        '<input id="swal-input8" class="swal2-input-custom" min="0" max="100" type="number" placeholder="8등급">'+
        '<input id="swal-input9" class="swal2-input-custom" min="0" max="100" type="number" placeholder="9등급">'+
        '<input id="swal-input10" class="swal2-input-custom" min="0" max="100" type="number" placeholder="10등급">',
        focusConfirm: false,
        preConfirm: () => {
          return [
            document.getElementById('swal-input1').value,
            document.getElementById('swal-input2').value,
            document.getElementById('swal-input3').value,
            document.getElementById('swal-input4').value,
            document.getElementById('swal-input5').value,
            document.getElementById('swal-input6').value,
            document.getElementById('swal-input7').value,
            document.getElementById('swal-input8').value,
            document.getElementById('swal-input9').value,
            document.getElementById('swal-input10').value
          ]
        },
      confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3','4'],
      }
      ]).then((result) => {
      if (result.value) {
        const answers = JSON.stringify(result.value)
      this.$swal({
          title: '직업이 생성됐어요!',
          html: `
            Your answers:
            <pre><code>${answers}</code></pre>
          `,
          confirmButtonText: 'Lovely!'
        })
      }
      })
    }
    }
};
</script>

<style lang="scss" scoped>

</style>