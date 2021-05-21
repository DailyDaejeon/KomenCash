<template>
  <div class="inputBox shadow">
    <input type="text" v-model="newItem" @keyup.enter="addItem">
    <span class="addContainer" @click="addItem">
      <i class="addBtn fas fa-plus" aria-hidden="true"></i>
    </span>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newItem: '',
    }
  },
  methods: {
    addItem() {
      if (this.newItem !== '') {
        const item = this.newItem.trim();
        console.log("vote item input page : ",item);
        this.$emit('addItem',item);
        this.clearInput();
      }
    },
    clearInput() {
      this.newItem = '';
    }
  },
}
</script>

<style>
input:focus {
  outline: none;
}
.inputBox {
  background: white;
  height: 50px;
  /* line-height: 50px; */
  /* border-radius: 5px; */
}

.inputBox input {
  border-style: none;
  font-size: 0.9rem;
  border-radius: .1875em 0 0 .1875em !important;
}

.addContainer {
  /* float: right; */
  background: linear-gradient(to right, #6478FB, #8763FB);
  display: inline-block;
  width: 3rem;
  border-radius: 0 5px 5px 0; 
  padding: 4.5px;
  text-align: center;
  cursor: pointer;
}

.addBtn {
  color: white;
  vertical-align: middle;
}
</style>