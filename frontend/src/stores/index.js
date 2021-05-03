import Vue from 'vue';
import Vuex from 'vuex';
import user from "./modules/user.js";
import group from "./modules/group.js";



Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        user,
        group
    },
});

