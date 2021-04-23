<template>
  <div id="app">
    <div class="wrapper">
      <Sidebar/>
      <div class="main">
        <Navbar/>
        <div class="main">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from './components/common/Navbar.vue'
import Sidebar from './components/common/Sidebar.vue'
export default {
  components: { Sidebar, Navbar },
  name: 'App',
}
</script>

<style>
</style>
