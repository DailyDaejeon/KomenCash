<template>
  <div id="app">
    <router-link :to="{name:'Signup'}">SIGNUP</router-link>
    |
    <router-link :to="{name:'Login'}">Login</router-link>
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
}
</script>

<style>
</style>
