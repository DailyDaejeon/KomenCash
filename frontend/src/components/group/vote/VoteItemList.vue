<template>
  <section class="voteList">
    <transition-group name="list" tag="ul">
      <li v-for="(voteItem, index) in this.storedTodoItems" class="shadow" :key="voteItem.item">
        <i class="checkBtn fas fa-check" :class="{checkBtnCompleted: voteItem.completed}" @click="toggleComplete(voteItem, index)"></i>
        <span :class="{textCompleted: voteItem.completed}">{{ index+1 }}. {{ voteItem.item }}</span>
        <span class="removeBtn" @click="removeTodo(voteItem, index)">
          <i class="removeBtn fas fa-trash-alt"></i>
        </span>
      </li>
    </transition-group>
  </section>
</template>

<script>
export default {
  methods: {
    removeTodo(voteItem, index) {
      this.$emit('removeItem', voteItem, index);
    },
    toggleComplete(voteItem, index) {
      this.$emit('toggleItem', voteItem, index);
    }
  },
  props:{
    voteList:Array
  },
  computed: {
    storedTodoItems() {
      // return this.$store.state.todoItems;
      return this.voteList;
    }
  }
}
</script>

<style>
.voteList ul {
  list-style-type: none;
  padding-left: 0px;
  margin-top: 0;
  text-align: left;
}
.voteList li {
  display: flex;
  min-height: 50px;
  height: 50px;
  line-height: 50px;
  margin: 0.5rem 0;
  padding: 0 0.9rem;
  background: white;
  border-radius: 5px;
}
.checkBtn {
  /* line-height: 45px; */
  color: #62acde;
  margin-right: 5px;
  position: relative;
  top: 1rem;
}
.checkBtnCompleted {
  color: #b3adad;
}
.textCompleted {
  text-decoration: line-through;
  color: #b3adad;
}
.removeBtn {
  margin-left: auto;
  color: #de4343;
  cursor: pointer;
}

/* transition css */
.list-enter-active, .list-leave-active {
  transition: all 1s;
}

.list-enter, .list-leave-to { /*.list-leave-active below version 2.1.8 */
  opacity: 0;
  transform: translateY(30px);
}
</style>