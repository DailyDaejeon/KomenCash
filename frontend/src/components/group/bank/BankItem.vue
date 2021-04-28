<template>
  <div class="row">
    <div class="col-md-3 col-xl-2">
      <div class="card">
        <div class="card-header">
          <h5 class="card-title mb-0">{{bankTypeName}} 조회</h5>
        </div>
        <div class="pointer list-group list-group-flush" role="tablist" v-for="(data,index) in propsData" :key="index"> 
          <router-link 
          :id="data.name"
          class="list-group-item list-group-item-action" :to="{name:`${bankTypeRouteName}`, params: { id:`${data.id}`, propsData:data,dataName:data.name}}"
          v-slot="{ href, navigate, isActive}" custom
          >
          <span  :active="isActive" :href="href" @click="navigate"
          :class="[isActive && 'active']"
          >{{data.name}}</span
        >
          </router-link>
        </div>
      </div>
    </div>

    <div class="col-md-9 col-xl-10">
      <div class="tab-content">
        <div class="tab-pane fade show active" id="account" role="tabpanel">

          <div class="card">
            <div class="card-header">

              <h5 class="card-title mb-0">{{bankTypeName}}리스트</h5>
            </div>
            <div class="card-body">
              <router-view></router-view>

            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    bankType:String,
    propsData:Array
  },
  data() {
    return {
      bankTypeName:"",
      bankTypeRouteName:"",
    };
  },
  created() {
    this.fetchTypeName();
  },
  methods: {
    fetchTypeName() {
      if (this.bankType === "account") {
        this.bankTypeName = "계좌"
        this.bankTypeRouteName = "BankMemberDetail"
      } else if (this.bankType === "financial") {
        this.bankTypeName = "예적금 상품"
        this.bankTypeRouteName = "BankFinancialDetail"
      }
    }
  },
};
</script>

<style lang="scss" scoped>

</style>