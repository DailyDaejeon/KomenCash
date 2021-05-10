<template>
  <div>
    <button 
    class="btn btn-main"
    @click="addCerti">자격증 추가</button>
    <div class="card-body">
          <div class="row">
            <div class="col">
              <table class="table table-hover text-center">
                <tr>
                  <th>No.</th>
                  <th>이름</th>
                  <th>기준</th>
                  <th>수정</th>
                  <th>삭제</th>
                </tr>
                <tr v-for="(certi, index) in certiList" :key="index">
                  <td>{{index+1}}</td>
                  <td class="cursor-pointer">{{certi.name}}</td>
                  <td>{{certi.acquisitionCondition}}</td>
                  <td><button class="btn btn-main" @click="alertDetail(certi)"><i class="fas fa-pencil-alt"></i></button></td>
                  <td><button class="btn btn-danger" @click="deleteCerti(certi)"><i class="fas fa-trash-alt"></i></button></td>
                </tr>
              </table>
            </div>
          </div>
        </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { addCertificate, deleteCertificate, fetchCertiList, modifyCertificate } from '@/api/certificate'

export default {
  data() {
    return {
      certiList : [],
      certiName: new Set()
    }
  },
  created() {
    this.fetchCerti()

  },
  computed:{
    ...mapState({
      groupInfo: state=> state.group.groupInfo
    })
  },
  methods: {
    deleteCerti(certi) {
      this.$swal({
        title:"삭제하시겠습니까?",
        text:"삭제 시 복구할 수 없습니다.",
        icon:"warning",
        showCancelButton:true,
        confirmButtonText:"삭제",
        cancelButtonText:"취소"
      }).then((res)=>{
        if (res.value) {
          // const certiData = Array.from(this.certiList[certi])
          // certiData.forEach((element) => {
          //   deleteCertificate(element.id)
          // })
            deleteCertificate(certi.id)
        }
      })
    },
    async fetchCerti() {
      const res = await fetchCertiList(this.groupInfo.id)
      this.certiList = res.data
      // this.certiList = this.certiList.slice(1)+this.certiList.slice(0,1)
      console.log(this.certiList)

      // res.data.forEach(element => {
      //   if (element.name in this.certiList) {
      //     if (!this.certiList[element.name].has(element)) {
      //       this.certiList[element.name].add(element)
      //     }
          
      //   } else {
      //     this.certiList[element.name] = new Set()
      //     this.certiList[element.name].add(element)
      //     if (!this.certiName.has(element.name)) {
      //       this.certiName.add(element.name)
      //     }
      //   }
      // });
      // this.certiName =Array.from(this.certiName)

    },
    alertDetail(certi) {
      // const certiData = Array.from(this.certiList[certi])
      // certiData.sort(function(a,b) {
      //   return b.acquisitionCondition-a.acquisitionCondition
      // })
      console.log('들어왓니',certi)
      this.$swal({
        title: certi.name+' 자격증의 수정',
        confirmButtonText:'수정',
        showCancelButton:true,
        cancelButtonText:'취소',
        html: 
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">'
        +'<input id="swal-input-name" class="swal2-input" type="text" placeholder="국어왕1급">' +'<input id="swal-input-condition" class="swal2-input" type="text" placeholder="조건을 입력해주세요.(수행평가 평균)">' +'</div>'+
        '<input id="swal-input1" class="swal2-input" min="0" type="number" placeholder="등급 기준을 적어주세요">',
        focusConfirm: false,
        preConfirm: () => {
          return [
            document.getElementById('swal-input1').value,
            document.getElementById('swal-input-name').value,
            document.getElementById('swal-input-condition').value
          ]
        },
      }).then((result) => {
        if (result.value) {
        
          const product = {
            groupId: this.groupInfo.id,
            name: result.value[0],
            acquisitionCondition: result.value[1]+' : '+result.value[2],
            id:certi.id
          }
          modifyCertificate(product).then(()=>{
            this.fetchCerti()
            this.$swal({
              title: '자격증을 수정했습니다.',
              icon:'success',
              showConfirmButton: false,
              timer: 1500
            })
          })
        }
      })
  },
  addCerti() {
    this.$swal.queue([
      {
        title: '자격증추가 1단계',
        input: 'text',
        text: '자격증 이름을 작성해주세요.',
        inputPlaceholder: '수학왕',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2','3'],
        inputValidator: (result) => {
          return !result && '자격증명 지정해주세요!'
        }
      },
      {
        title: '자격증추가 2단계',
        input: 'text',
        text: '자격증 등급 기준을 적어주세요.',
        inputPlaceholder: '1학기 중간고사 기준',
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2','3'],
        inputValidator: (result) => {
          return !result && '자격증명 지정해주세요!'
        }
      },
      {
        title: '자격증추가 3단계',
        html:
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">등급별 기준을 적어주세요.</div>'+'<input id="swal-input1" class="swal2-input-custom" min="0" max="100" type="number" placeholder="1등급">' +
        '<input id="swal-input2" class="swal2-input-custom" min="0" max="100" type="number" placeholder="2등급">'+
        '<input id="swal-input3" class="swal2-input-custom" min="0" max="100" type="number" placeholder="3등급">'+
        '<input id="swal-input4" class="swal2-input-custom" min="0" max="100" type="number" placeholder="4등급">'+
        '<input id="swal-input5" class="swal2-input-custom" min="0" max="100" type="number" placeholder="5등급">'+
        '<input id="swal-input6" class="swal2-input-custom" min="0" max="100" type="number" placeholder="6등급">'+
        '<input id="swal-input7" class="swal2-input-custom" min="0" max="100" type="number" placeholder="7등급">'+
        '<input id="swal-input8" class="swal2-input-custom" min="0" max="100" type="number" placeholder="8등급">'+
        '<input id="swal-input9" class="swal2-input-custom" min="0" max="100" type="number" placeholder="9등급">'+
        '<input id="swal-input10" class="swal2-input-custom" min="0" max="100" type="number" placeholder="10등급">',
        focusConfirm: false,
        preConfirm: () => {
          return [
            document.getElementById('swal-input1').value,
            document.getElementById('swal-input2').value,
            document.getElementById('swal-input3').value,
            document.getElementById('swal-input4').value,
            document.getElementById('swal-input5').value,
            document.getElementById('swal-input6').value,
            document.getElementById('swal-input7').value,
            document.getElementById('swal-input8').value,
            document.getElementById('swal-input9').value,
            document.getElementById('swal-input10').value
          ]
        },
        confirmButtonText: 'Next &rarr;',
        showCancelButton: true,
        progressSteps: ['1', '2','3'],
      }
    ]).then((result) => {
      if (result.value) {
        // const answers = JSON.stringify(result.value)
        for (let i = 0; i < result.value[2].length; i++) {
          const element = result.value[2][i];
          if (element) { 
            const product = {
              groupId: this.groupInfo.id,
              name: result.value[0]+(i+1)+'급',
              acquisitionCondition: result.value[1]+' : '+element
            }
            addCertificate(product)
          }
        }
      }
    })
    }
  }
}
</script>

<style>

</style>