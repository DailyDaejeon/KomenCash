<template>
  <main class="content">
    <div class="container-fluid p-0 mb-5">
      <div class="mb-2">
        <div class="d-none d-sm-block">
          <h3 class="d-inline-block mr-3"><strong>{{groupInfo.name}}</strong> 세금</h3>
            <button  @click="modifyTax" class="btn btn-main float-right">소득세율수정</button>
          <h1 class="display-4"><i class="fas fa-money-bill-alt"></i>총 {{taxList.tax || 0}} {{groupInfo.monetary_unit_name}}</h1>
        </div>  
      </div>
    </div>
    <TaxHistory :taxHistoryList="taxList.taxHistoryResponses" :groupInfo="groupInfo"/>
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
    async fetchTax() {
      const res = await fetchTaxList(this.groupInfo.id)
      this.taxList = res.data
      console.log(this.taxList)
    },
    modifyTax() {
      this.$swal({
        title: '소득세율 수정',
        text: "소득세율을 수정해주세요!(%)",
        input: 'text',
        inputPlaceholder: this.groupInfo.tax_rate,
        showCancelButton: true,
      }).then((result) => {
        if (result.value) {
          const ans = {
            groupId: this.groupInfo.id,
            taxRate: result.value
          }
          modifyTaxRate(ans)
          const group = {
            code: this.groupInfo.code,
            id: this.groupInfo.id,
            inflationRate: this.groupInfo.inflationRate,
            monetary_unit_name: this.groupInfo.monetary_unit_name,
            name: this.groupInfo.name,
            tax: this.groupInfo.tax,
            tax_rate: result.value,
            teacher: this.groupInfo.teacher
          }
          this.$store.commit('setGroupInfo',group)
        }
      })
      }
  },
}
</script>

<style>

</style>