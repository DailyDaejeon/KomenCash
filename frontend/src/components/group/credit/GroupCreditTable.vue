<template>
  <div class="card">
    <div class="card-body">
      <div class="flex-fill">
        <h5 class="card-title mb-0">Group Member Credit Info</h5>
        <div class="card-body">
          <div class="row">
            <div class="col">
              <table class="table table-hover text-center">
                <tr>
                  <th>No.</th>
                  <th>신용등급</th>
                  <th>신용점수</th>
                  <th>닉네임</th>
                </tr>
                <tr v-for="(student, index) in studentList" :key="index">
                  <td>{{index+1}}</td>
                  <td>{{student.grade}}</td>
                  <td>{{priceToString(student.creditPoint)}}</td>
                  <td>{{student.studentNickname}}</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props:['studentList'],
  data() {
    return {
      // studentList:[],
    }
  },
  methods:{
    priceToString(price) {
      return price.toLocaleString('ko-KR')
    },
  }
}
</script>

<style>

</style>