<template>
  <main class="content">
    <div class="container-fluid p-0 mb-5">
      <div class="mb-2">
        <div class="d-block">
          <h3 class="d-inline-block mr-3"><strong>{{groupInfo.name}}</strong> 세금</h3>
            <button  @click="modifyTax" class="btn btn-main float-right">소득세율수정</button>
          <h1 class="display-4"><i class="fas fa-money-bill-alt"></i>총 {{priceToString(totalTax) || 0}} {{groupInfo.monetaryUnitName}}</h1>
        </div>  
      </div>
    </div>
    <TaxHistory :taxHistoryList="taxList" :groupInfo="groupInfo"/>
  </main>
</template>

<script>
import TaxHistory from '@/components/tax/TaxHistory.vue'
import { mapState } from 'vuex'
import { fetchTaxList, modifyTaxRate } from '@/api/tax'

export default {
  components: { TaxHistory },
  data() {
    return {
      totalTax:0,
      taxList : {
        tax: 0,
        taxHistoryResponses: []
      }
    }
  },
  created() {
    this.fetchTax()
  },
  computed: {
    ...mapState({
      groupInfo : state => state.group.groupInfo
    })
  },
  methods: {
    priceToString(price) {
      return price.toLocaleString('ko-KR')
    },
    async fetchTax() {
      const res = await fetchTaxList(this.groupInfo.id)
      this.taxList = res.data
      console.log(this.taxList)
      this.totalTax = this.taxList[this.taxList.length-1].balance
    },
    modifyTax() {
      this.$swal({
        title: '소득세율 수정',
        text: "소득세율을 수정해주세요!(00%)",
        input: 'text',
        inputPlaceholder: `${this.groupInfo.taxRate } (%)`,
        showCancelButton: true,
      }).then((result) => {
        if (result.value) {
          const ans = {
            groupId: this.groupInfo.id,
            taxRate: result.value
          }
          modifyTaxRate(ans).then(() => {
            this.$swal({
              title:'소득세율이 성공적으로 수정됐습니다.',
              icon:'success',
              timer:1500
            })
            const group = {
            code: this.groupInfo.code,
            id: this.groupInfo.id,
            inflationRate: this.groupInfo.inflationRate,
            monetaryUnitName: this.groupInfo.monetaryUnitName,
            name: this.groupInfo.name,
            taxRate: result.value
          }
          this.$store.commit('setGroupInfo',group)
          })
          
        }
      })
      }
  },
}
</script>

<style>

</style>