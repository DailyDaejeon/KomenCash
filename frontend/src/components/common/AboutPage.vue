<template>
  <div>
  <section id="about1">
  <!-- version 1 -->
    <h1 class="about1__h1">MONEYJAM
      <div class="about1__button">
        <button class="btn-2" @click="goTeacherPage">TEACHER</button>
        <button class="btn-2" @click="downloadUnity">STUDENT</button>
      </div>
    </h1>
  </section>

    <!-- <button @click="download">DOWNLOAD</button> -->
    <!-- <a @click.prevent="download()" :href="url" download>DOWNLOAD</a> -->
  </div>
</template>

<script>
import { downloadUnity } from '@/api/student'
export default {
  data() {
    return {
      url:''
    }
  },
  methods: {
    downloadUnity() {
      this.$swal({
        title:'학생용 서비스를 받으시겠습니까?',
        text : 'ZIP 파일로 받아지며, 알집 풀기 후 "유니티" 파일을 실행시키면 학생용 서비스를 이용하실 수 있습니다.',
        icon:'question',
        confirmButtonText:'Download',
        showCancelButton:true
      }).then((res)=>{
        if (res.isConfirmed) {
          // 다운받는 API
          this.$swal({
            title:'다운이 완료되었습니다.',
            text:'유니티를 실행시켜주세요!',
            icon:'success'
          })
        }
      })
    },
    goTeacherPage() {
      this.$router.push({name:'Login'})
    },
    download() {
      downloadUnity().then(( res => {
        let blob = new Blob([res.data], { type: 'application/octer-stream' })
        let link = document.createElement('a')
        link.href = window.URL.createObjectURL(blob)
        link.download = 'MoneyJam.zip'
        link.click()
      }))
    },
  },
}
</script>


<style lang="scss" scoped>


</style>

