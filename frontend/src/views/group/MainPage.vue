<template>
  <main class="content">
    <div class="container-fluid p-0">
      <div class="row mb-2 mb-xl-3">
        <div class="col-auto d-none d-sm-block">
          <h1>{{groupName}} 그룹</h1>
          <h3><strong>Request</strong> Dashboard</h3>
        </div>  
      </div>
      <RequestList/>
    </div>
  </main>
</template>

<script>
import RequestList from '@/components/group/main/RequestList.vue'
import  store  from '@/stores/modules/group'

export default {
  components: { RequestList },
  props: {
    id : {
      default:store.state.groupInfo.id
    },
    groupData : {
      default: store.state.groupInfo
    }
  },
  data() {
    return {
      groupName : this.groupData.name
    }
  }
}
</script>

<style>

</style>