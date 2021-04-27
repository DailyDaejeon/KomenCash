<template>
  <main class="content">
    <div class="container-fluid p-0">
      <h1 class="h3 mb-3">그룹원 전체 계좌</h1>
      <div class="row">
        <div class="col-md-3 col-xl-2">
          <div class="card">
            <div class="card-header">
              <h5 class="card-title mb-0">그룹원 리스트</h5>
            </div>
            <div class="pointer list-group list-group-flush" role="tablist" v-for="(student,index) in students" :key="index"> 
              <router-link 
              :id="student.name"
              class="list-group-item list-group-item-action" :to="{path:`/group/bank/${student.name}`, props:{studentData:student,memberName:student.name}}"
              v-slot="{ href, navigate, isActive}" custom
              >
              <span :active="isActive" :href="href" @click="navigate"
              :class="[isActive && 'active']"
              >{{student.name}}</span
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

                  <h5 class="card-title mb-0">계좌리스트</h5>
                </div>
                <div class="card-body">
                  <router-view></router-view>

                </div>
              </div>

            </div>
          </div>
        </div>
      </div>

    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      students : [
        {
          id:0,
          name:"김싸피"
        },
        {
          id:1,
          name:"박싸피"
        }
      ]
    }
  },
  watch:{
    $route() {
      // window.location.reload();
    }
  }
}
</script>

<style>

</style>