<template>
  <div v-on:closeModal="closeModal">
    <transition name="modal" >
      <div class="modal-mask">
        <div class="modal-wrapper">
          <div class="modal-container">

            <div class="modal-header">
              <slot name="header">
                default header
              </slot>
            </div>

            <div class="modal-body">
              <slot name="body">
                default body
              </slot>
            </div>
          </div>
        </div>
      </div>
    </transition>

  </div>

</template>


<script>
export default {
  methods: {
    closeModal(e) {
    console.log(e)
    this.$emit('closeModal')
    },
    
  }
  
}
</script>
<style>
</style>
