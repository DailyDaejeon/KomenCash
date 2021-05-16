<template>
  <div class="row">
    <div class="w-100">
      <div class="card-header">
        <button @click="addLaw" class="btn btn-main">헌법추가</button>
        <div id="tab" class="container">
          <div class="tabs">
            <div class="tabs" >
              <!-- {{lawData}} -->
             
              <span v-for="(law,index) in lawName" :key="index">
              <router-link 
              active-class="active"
              :to="{name:'LawType',params:{lawType:law,lawData:lawData[law]}}">{{law}}
              </router-link>
              </span>
            </div>
            <div class="tabcontent">
                <router-view @updateData="updateData"></router-view>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchLawList,addLawItem } from '@/api/law';
import { mapState } from 'vuex';
export default {
  data() {
    return {
      activetab: 1,
      lawData:{},
      lawName:[]

    }
  },
  created() {
    this.fetchLawData()
  },
  computed: {
    ...mapState({
      groupInfo:state => state.group.groupInfo
    })
  },
  methods : {
    addLaw() {
      this.$swal({
        title: '헌법추가',
        html:
        '<div id="swal2-content" class="swal2-html-container" style="display: block;">추가할 법률을 적어주세요.</div>'+'<input id="swal-input1" class="swal2-input" type="text" placeholder="OO법">' +
        '<input id="swal-input2" class="swal2-input-custom" min="0" type="number" placeholder="0조">'+
        '<input id="swal-input3" class="swal2-input-custom" min="0" type="number" placeholder="0항">'+
        '<input id="swal-input4" class="swal2-input"  type="text" placeholder="그룹의 주권은 국민에게 있고, 모든 권력은 국민으로부터 나온다.">',
        focusConfirm: false,
        preConfirm: () => {
          return [
            document.getElementById('swal-input1').value,
            document.getElementById('swal-input2').value,
            document.getElementById('swal-input3').value,
            document.getElementById('swal-input4').value,
          ]
        },
        confirmButtonText: '추가',
        showCancelButton: true,
      }).then((res) => {
      if (res.value.length===4) {
        const lawData = {
          groupId: this.groupInfo.id,
          lawType: res.value[0],
          article: Number(res.value[1]),
          paragraph: Number(res.value[2]),
          content: res.value[3],
        }
        console.log(lawData,'?')
        addLawItem(lawData).then(() => {
          this.fetchLawData()
          this.$swal({
            text:'법률이 추가 되었습니다.',
            icon:'success'
          })
          // window.location.reload()
        })
      } else {
        this.$swal({
          title:'법률 추가에 실패했습니다. ',
          text:'조건에 맞게 다시 작성해주세요.',
          icon:'error'
          
        })
      }
    })
    },
    updateData() {
      this.fetchLawData()
    },
    async fetchLawData() {
      this.lawData = {};
      this.lawName = [];
      const res = await fetchLawList(this.groupInfo.id)
      res.data.forEach((el) => {
        if (this.lawData[el.lawFindByLawTypeResponseDtoList[0].lawType]) {
          this.lawData[el.lawFindByLawTypeResponseDtoList[0].lawType].push(el.lawFindByLawTypeResponseDtoList)
        } else {
          this.lawData[el.lawFindByLawTypeResponseDtoList[0].lawType] = el.lawFindByLawTypeResponseDtoList
          this.lawName.push(el.lawFindByLawTypeResponseDtoList[0].lawType)
        }
      })
      console.log('헌법리스트',this.lawData)

    },
  }
}
</script>

<style lang="css">
@import url(https://fonts.googleapis.com/css?family=Nunito+Sans);

/* RESET */
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

/* STYLING */
.container {  
    max-width: 100%; 
    min-width: 50px;
    margin: 40px auto;
    font-family: "Nunito Sans", Arial, Helvetica, sans-serif;
    color: #888;
}

/* Style the tabs */
.tabs {
    overflow: hidden;
    margin-left: 20px;
    margin-bottom: -2px; /* hide bottom border */
}

.tabs ul {
    list-style-type: none;
    margin-left: 20px;
}

.tabs a{
    float: left;
    cursor: pointer;
    padding: 6px 24px;
    transition: background-color 0.2s;
    border: 1px solid #ccc;
    border-right: none;
    background-color: #f1f1f1;
    border-radius: 10px 10px 0 0;
    font-weight: bold;
}
.tabs a:last-child { 
    border-right: 1px solid #ccc;
}

/* Change background color of tabs on hover */
.tabs a:hover {
    background-color: #aaa;
    color: #fff;
}

/* Styling for active tab */
.tabs a.active {
    background-color: #fff;
    color: #484848;
    border-bottom: 2px solid #fff;
    cursor: default;
}

/* Style the tab content */
.tabcontent {
    padding: 30px;
    border: 1px solid #ccc;
    border-radius: 10px;
    box-shadow: 3px 3px 6px #e1e1e1
}
</style>