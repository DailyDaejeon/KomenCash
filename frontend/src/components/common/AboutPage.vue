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
.about_div {
  height:100vh;
  position:'relative';
  display:block;
}


// version 1
@import url('https://fonts.googleapis.com/css2?family=Luckiest+Guy&display=swap');

#about1 {
  height: 100vh;
  background-color: #e7ab3c;
  text-align: center;
  display:flex;
  align-items: center;
  justify-content: center;
}

.btn-2 {
  letter-spacing: 0;
}

.btn-2:hover,
.btn-2:active {
  letter-spacing: 5px;
}

.btn-2:after,
.btn-2:before {
  backface-visibility: hidden;
  border: 1px solid rgba(#fff, 0);
  bottom: 0px;
  content: " ";
  display: block;
  margin: 0 auto;
  position: relative;
  transition: all 280ms ease-in-out;
  width: 0;
}

.btn-2:hover:after,
.btn-2:hover:before {
  backface-visibility: hidden;
  border-color: #fff;
  transition: width 350ms ease-in-out;
  width: 70%;
}

.btn-2:hover:before {
  bottom: auto;
  top: 0;
  width: 70%;
}


.about1 {
  &__button {
    display: grid;
  }

  &__h1 {
  font-size: 144pt;
  font-family: 'Luckiest Guy';
  color: #fff;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-shadow:   0px -6px 0 #212121,  
                 0px -6px 0 #212121,
                 0px  6px 0 #212121,
                 0px  6px 0 #212121,
                -6px  0px 0 #212121,  
                 6px  0px 0 #212121,
                -6px  0px 0 #212121,
                 6px  0px 0 #212121,
                -6px -6px 0 #212121,  
                 6px -6px 0 #212121,
                -6px  6px 0 #212121,
                 6px  6px 0 #212121,
                -6px  18px 0 #212121,
                 0px  18px 0 #212121,
                 6px  18px 0 #212121,
                 0 19px 1px rgba(0,0,0,.1),
                 0 0 6px rgba(0,0,0,.1),
                 0 6px 3px rgba(0,0,0,.3),
                 0 12px 6px rgba(0,0,0,.2),
                 0 18px 18px rgba(0,0,0,.25),
                 0 24px 24px rgba(0,0,0,.2),
                 0 36px 36px rgba(0,0,0,.15);
    & button {
      font-size: 70pt;
      // margin-right: 70px
    }
  }
}


</style>

