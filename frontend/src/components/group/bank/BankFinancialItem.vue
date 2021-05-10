<template>
  <div class="card flex-fill">
    <button class="btn btn-main" @click="alertDetail(financial)">자세히</button>
    <button class="btn btn-danger" @click="deleteCerti(financial)">삭제</button>
    <table class="table table-hover my-0">
      <thead>
        <tr>
          <th>이름</th>
          <th>신용등급</th>
          <th>금리</th>
          <th>기간</th>
        </tr>
      </thead>
      <tbody>
        <tr @click="goDetail(financial.financialProduct.id)" v-for="(financial,index) in studentList" :key="index">
          <td>{{financial.financialProduct.name}}</td>
          <td>{{financial.creditGrade}}</td>
          <td>{{financial.rate}}</td>
          <td>{{financial.period}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { deleteFinancial, modifyDetailFinancial } from '@/api/bank';
export default {
  props:['propsData','dataName'],
  data() {
    return {
      financialList : this.propsData,
      financialName: this.dataName,
      studentList:this.propsData.studentsList,
      productName: new Set()
    }
  },
  created() {
    // this.fetchProduct()
  },
  methods: {
    async fetchProduct() {
      // const res = await fetchCertiList(this.groupInfo.id)
      // console.log(res.data)
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
          const certiData = Array.from(this.certiList[certi])
          certiData.forEach((element) => {
            deleteFinancial(element.id)
          })
        }
      })
    },
    alertDetail(product) {
      console.log('들어왓니',product)
      this.$swal({
        title: product.financialProduct.name+' 자격증의 상세 정보',
        confirmButtonText:'수정',
        showCancelButton:true,
        cancelButtonText:'취소',
        html: 
        "<table class='table'>"+
        // "<tr>"+
        // "<th>이름</th>"+
        // "<td>"+certi+"</td>"+
        // "</tr>"+
        "<tr>"+
        "<th>1등급</th>"+
        "<td>"+product[0]+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>2등급</th>"+
        "<td>"+product[1]+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>3등급</th>"+
        "<td>"+product[2]+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>4등급</th>"+
        "<td>"+product[3]+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>5등급</th>"+
        "<td>"+product[4]+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>6등급</th>"+
        "<td>"+product[5]+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>7등급</th>"+
        "<td>"+product[6]+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>8등급</th>"+
        "<td>"+product[7]+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>9등급</th>"+
        "<td>"+product[8]+"</td>"+
        "</tr>"+
        "<tr>"+
        "<th>10등급</th>"+
        "<td>"+product[9]+"</td>"+
        "</tr>"+
        "</table>",
      }).then((res) => {
        if (res.value) {
          this.$swal(
            {
        title: '예적금상품 수정',
        html:
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">'
        +'<input id="swal-input-name" class="swal2-input" type="text" placeholder="이름">' +'</div>'+
        '<input id="swal-input1" class="swal2-input-custom" min="0" type="number" placeholder="1등급">' +
        '<input id="swal-input2" class="swal2-input-custom" min="0"  type="number" placeholder="2등급">'+
        '<input id="swal-input3" class="swal2-input-custom" min="0" type="number" placeholder="3등급">'+
        '<input id="swal-input4" class="swal2-input-custom" min="0"type="number" placeholder="4등급">'+
        '<input id="swal-input5" class="swal2-input-custom" min="0"type="number" placeholder="5등급">'+
        '<input id="swal-input6" class="swal2-input-custom" min="0"  type="number" placeholder="6등급">'+
        '<input id="swal-input7" class="swal2-input-custom" min="0"  type="number" placeholder="7등급">'+
        '<input id="swal-input8" class="swal2-input-custom" min="0" type="number" placeholder="8등급">'+
        '<input id="swal-input9" class="swal2-input-custom" min="0"  type="number" placeholder="9등급">'+
        '<input id="swal-input10" class="swal2-input-custom" min="0" type="number" placeholder="10등급">',
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
            document.getElementById('swal-input10').value,
            document.getElementById('swal-input-name').value
          ]
        },
      confirmButtonText: '수정완료',
      showCancelButton: true,
      }).then((result) => {
      if (result.value) {
        // const answers = JSON.stringify(result.value)
        for (let i = 0; i < result.value.length-1; i++) {
          const element = result.value[i];
          const product = {
          groupId: this.groupInfo.id,
          name: result.value[result.value.length-1],
          acquisitionCondition: element,
          id:product[i].id
        }
        modifyDetailFinancial(product).then(()=>{
          this.fetchCerti()
          this.$swal({
          title: '예적금 상품을 수정했습니다.',
          icon:'success',
          showConfirmButton: false,
          timer: 1500
        })
        })
        }
        
      }
    })
        }
      })
    },
  },
};
</script>

<style lang="scss" scoped>

</style>