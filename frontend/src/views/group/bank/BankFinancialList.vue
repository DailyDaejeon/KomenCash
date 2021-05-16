<template>
  <main class="content">
    <button class="mr-3 btn btn-main"  @click="createFinancial">예금상품추가</button>
    <ListItem @updateData="updateData" listType="financial" :propsData="financialData"/>
  </main>
</template>

<script>
import ListItem from '@/components/group/ListItem.vue';
import { addFinancial, detailFinancial, fetchFinancialList } from '@/api/bank';
import { mapState } from 'vuex';

export default {
  components: { ListItem },
  data() {
    return {
      financialData : []
    }
  },
  created() {
    this.fetchFinancialData()
  },
  computed: {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    })
  },
  methods: {
    updateData(e) {
      console.log(e)
      this.fetchFinancialData()
      this.$router.push({name:"BankFinancialPage"})
    },
    async fetchFinancialData() {
      const res = await fetchFinancialList(this.groupInfo.id)
      this.financialData = res.data
      // console.log('부모finan',this.financialData)
    },
    createFinancial() {
      this.$swal.queue([
      {
        title: '예금상품추가 1단계',
        input: 'text',
        text: '상품명을 지정해주세요.',
        inputPlaceholder: '새싹예금',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2', '3'],
        inputValidator: (result) => {
          return !result && '상품명을 지정해주세요!'
        }
      },
      {
        title: '예금상품추가 2단계',
        input: 'text',
        text: '기간을 적어주세요.',
        inputPlaceholder: '28',
        confirmButtonText: 'Next &rarr;',
      showCancelButton: true,
      progressSteps: ['1', '2', '3'],
      inputValidator: (result) => {
          return !result && '기간을 적어주세요!'
        }
      },
      {
        title: '예금상품추가 3단계',
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
      progressSteps: ['1', '2', '3'],
      }
      ]).then((result) => {
      if (result.value) {
        // console.log('이름 잘들어오니?',result.value[0])
        const item = {
          groupId: this.groupInfo.id,
          name: result.value[0]
        }
        addFinancial(item).then((res) => {
        // console.log(res.data,'금융상품생성')
        for (let i = 0; i < result.value[2].length; i++) {
          const element = result.value[2][i];
          const financialInfo = {
          creditGrade: i+1,
            financialProduct: {
              id: res.data,
              name:result.value[0],
            },
            period: Number(result.value[1]),
            rate: Number(element)
          }
          // console.log('순서',i,financialInfo)
          detailFinancial(financialInfo)
        }
      })
      this.fetchFinancialData()
      this.$swal({
          title: '예금상품이 생성됐어요!',
          icon:'success',
          timer:1500
      })
        window.location.reload()
      }
      })
    }
    }
};
</script>

<style lang="scss" scoped>

</style>