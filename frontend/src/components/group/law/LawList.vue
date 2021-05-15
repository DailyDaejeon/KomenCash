<template>
  <div class="row">
    <div class="w-100">
      <div class="card-header">
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
                <router-view></router-view>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchLawList } from '@/api/law';
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