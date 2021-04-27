<template>
  <!-- <form-wizard
    ref="formwizard"
    @onComplete="onComplete"
    @onNextStep="nextStep"
    @onPreviousStep="previousStep"
    @onReset="reset"
  >
    <tab-content title="Intro" :selected="true">
      <div class="form-group">
            <p>새로운 그룹을 생성하고, 아이들을 추가하여 경제 학습을 시작하세요.</p>
            <br />
            <p>모범TaxI는 아이들에게 건강한 경제 지식을 길러주기위한 롤플레이형 활동 지원 서비스 입니다.</p>
            <p>선생님(또는 학부모님)께서는 해당 관리자 페이지에서 그룹을 생성하시고, 관리자로써 경제 활동을 지도해주세요.</p>
            <br />
            <p>그룹 생성하기:</p>
            <p>
                <ul>
                    <li class="intro-step">그룹 명을 정해주세요.</li>
                    <ol>(OO초등학교 O학년 O반, 행복한 우리 집 등 어떤 형태든 좋습니다.)</ol>
                    <li class="intro-step">그룹에서 사용할 화폐 단위를 정해주세요(ex. 원, 미소, 꿈 등)</li>
                    <li class="intro-step">그룹 추가하기 버튼을 누르면 그룹이 생성됩니다.</li>
                </ul>
            </p>
      </div>
    </tab-content>
    <tab-content title="그룹 생성하기">
      <div class="form-group">
        <label for="groupName">그룹명</label>
        <input
          type="text"
          class="form-control"
          :class="hasError('groupName') ? 'is-invalid' : ''"
          placeholder="ex) 햇반나라, OO초등학교 O학년 O반"
          v-model="formData.groupName"
        />
        <div v-if="hasError('groupName')" class="invalid-feedback">
          <div class="error" v-if="!$v.formData.groupName.required">
            그룹명을 입력해주세요.
          </div>
        </div>
      </div>
      <div class="form-group">
        <label for="monetaryUnitName">화폐단위</label>
        <input
          type="text"
          class="form-control"
          :class="hasError('monetaryUnitName') ? 'is-invalid' : ''"
          placeholder="ex) 원, 꿈, 미소"
          v-model="formData.monetaryUnitName"
        />
        <div v-if="hasError('monetaryUnitName')" class="invalid-feedback">
          <div class="error" v-if="!$v.formData.monetaryUnitName.required">
            화폐단위를 정해주세요.
          </div>
        </div>
      </div>
    </tab-content>
    <tab-content title="최종 확인">
      <div class="form-group">
        <p>그룹 명   : {{formData.groupName}}</p>
        <p>화폐 단위 : {{formData.monetaryUnitName}}</p>
      </div>
      <div class="form-group form-check">
        <input
          id="chk"
          type="checkbox"
          :class="hasError('terms') ? 'is-invalid' : ''"
          class="form-check-input"
          v-model="formData.terms"
        />
        <label for="chk" class="form-check-label">그룹 명과 화폐 단위를 확인했습니다.</label>
        <div v-if="hasError('terms')" class="invalid-feedback">
          <div class="error" v-if="!$v.formData.terms.required">
            최종 확인 체크를 해주세요.
          </div>
        </div>
      </div>
    </tab-content>
  </form-wizard> -->
  <div>
    
  </div>
</template>

<script>
// import FormWizard from '@/components/group/addGroup/FormWizard.vue';
// import TabContent from '@/components/group/addGroup/TabContent.vue';
// import ValidationHelper from '@/components/group/addGroup/ValidationHelper.vue';
// import { required } from 'vuelidate/lib/validators';
// const checked = (value) => value === true;

// export default {
//   name: 'SimpleStepper',
//   components: {
//     FormWizard,
//     TabContent,
//   },
//   mixins: [ValidationHelper],
//   data() {
//     return {
//       formData: {
//         groupName: '',
//         monetaryUnitName: null,
//         terms: false,
//       },
//       validationRules: [
//         { groupName: { required }, monetaryUnitName: { required } },
//         { terms: { checked } },
//       ],
//     };
//   },
//   methods: {
//     onComplete() {
//       // alert('그룹이 생성되었습니다!');
//       this.$swal({
//         customClass: {
//           container: 'swal2-container'
//         },
//         text: '그룹이 생성되었습니다!',
//         icon: 'success',
//         timer: 1300,
//         showConfirmButton: false,
//       })
//     //   this.$refs.formwizard.changeStatus();
//     // 그룹 리스트로 돌아가는 코드 추가
//     },
//     reset() {
//       for (let field in this.formData) {
//         this.formData[field] = null;
//       }
//     },

//     nextStep() {
//       //alert("On Next Step");
//     },

//     previousStep() {
//       //alert("On Previous Step");
//     },
//   },
// };
</script>

<style>
    /* ul {
        margin-top: 0;
        margin-bottom: 1rem;
        padding-left: 1.5rem;
    }

    p {
        margin-top: 0.2rem;
    }

    .intro-step{
        margin-top:0.3rem;
    }

    .form-group {
        padding: 2rem;
    } */
</style>
