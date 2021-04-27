<template>
  <div class="row">
    <div class="w-100">
      <div class="card-header">
        <h4 class="card-title mb-0">헌법 리스트</h4>
        <!-- 1. nav(헌법 종류에 따라, 각 헌법의 조항에 따라) -->
        <!-- 2. 해당 헌법 또는 조항에 맞는 내용으로 이동 -->
        <div id="tabs" class="container">
          <div class="tabs">
          <!-- <div class="tabs" v-for="(law, index) in lawList" :key="index"> -->
            <!-- <a @click="activetab=(index+1)" :class="[ activetab === (index+1) ? 'active' : '' ]">{{law.law_type}}</a> -->
            <a @click="activetab=1" :class="[ activetab === 1 ? 'active' : '' ]">교통법</a>
            <a @click="activetab=2" :class="[ activetab === 2 ? 'active' : '' ]">질서법</a>
            <a @click="activetab=3" :class="[ activetab === 3 ? 'active' : '' ]">교육법</a>
            <a @click="activetab=4" :class="[ activetab === 4 ? 'active' : '' ]">형사법</a>
            <a @click="activetab=5" :class="[ activetab === 5 ? 'active' : '' ]">환경법</a>
            <a @click="activetab=6" :class="[ activetab === 6 ? 'active' : '' ]">금융법</a>
          </div>
          <div>
          <!-- <div v-for="(law, index) in lawList" :key="index"> -->
            <!-- <div v-if="activetab === (index+1)" class="tabcontent"> -->
                <!-- 도로법 관련 조항! -->
                <!-- <LawType /> :articles="" -->
            <!-- </div> -->
            <div v-if="activetab === 1" class="tabcontent">
                교통법 관련 조항!
            </div>
            <div v-if="activetab === 2" class="tabcontent">
                질서법 관련 조항!
            </div>
            <div v-if="activetab === 3" class="tabcontent">
                교육법 관련 조항!
            </div>
            <div v-if="activetab === 4" class="tabcontent">
                형사법 관련 조항!
            </div>
            <div v-if="activetab === 5" class="tabcontent">
                환경법 관련 조항!
            </div>
            <div v-if="activetab === 6" class="tabcontent">
                금융법 관련 조항!
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  el:"#tabs",
  data() {
    return {
      activetab: 1,
      lawList: [
        //이걸 어떻게 넣어야할까................
      ],
    }
  },
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