import FormWizard from "@/components/group/addGroup/FormWizard.vue";
import TabContent from "@/components/group/addGroup/TabContent.vue";
import ValidationHelper from '@/components/group/addGroup/ValidationHelper.vue';

const VueStepWizard = {
 install(Vue) {
  // Let's register our component globally
  // https://vuejs.org/v2/guide/components-registration.html
    Vue.component("form-wizard", FormWizard);
    Vue.component("tab-content", TabContent);
 }
};

// Automatic installation if Vue has been added to the global scope.
if (typeof window !== 'undefined' && window.Vue) {
    window.Vue.use(VueStepWizard);
}

export default VueStepWizard
export {
    FormWizard,
    TabContent,
    ValidationHelper
  }