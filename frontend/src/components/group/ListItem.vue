<template>
  <div class="row">
    <div class="col-md-3 col-xl-2">
      <div class="card">
        <div class="card-header">
          <h5 class="card-title mb-0">{{listTypeName}} 조회</h5>
        </div>
        <div class="pointer list-group list-group-flush" role="tablist" v-for="(data,index) in propsData" :key="index"> 
          <!-- <router-link 
          :id="data.name"
          active-class="active"
          class="list-group-item list-group-item-action" :to="{name:`${listTypeRouteName}`, params: { id:`${data.id}`, propsData:data,dataName:data.name}}"
          
          > -->
          <router-link 
          :id="data.name"
          active-class="active"
          class="list-group-item list-group-item-action" :to="{name:listTypeRouteName, params: { id:data.id, propsData:data,dataName:data.name}}"
          
          >
          <span v-if="listTypeRouteName==='GroupMemberInfo'">
            {{data.nickname}}
          </span>
          <span v-else>
          {{data.name}}
          </span>
          </router-link>
        </div>
      </div>
    </div>

    <div class="col-md-9 col-xl-10">
      <div class="tab-content">
        <div class="tab-pane fade show active" id="account" role="tabpanel">

          <div class="card">
            <div class="card-header">

              <h5 class="card-title mb-0">{{listTypeName}}리스트</h5>
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
    listType:String,
    propsData:Array
  },
  data() {
    return {
      listTypeName:"",
      listTypeRouteName:"",
    };
  },
  created() {
    this.fetchTypeName();
  },
  methods: {
    fetchTypeName() {
      if (this.listType === "account") {
        this.listTypeName = "계좌"
        this.listTypeRouteName = "BankMemberDetail"
      } else if (this.listType === "financial") {
        this.listTypeName = "예금 상품"
        this.listTypeRouteName = "BankFinancialDetail"
      } else if (this.listType === "member") {
        this.listTypeName = "그룹원"
        this.listTypeRouteName = "GroupMemberInfo"
      } else if (this.listType === "store") {
        this.listTypeName = "온라인 상품"
        this.listTypeRouteName = "StorePage"
      }
    }
  },
};
</script>

<style lang="scss" scoped>

</style>