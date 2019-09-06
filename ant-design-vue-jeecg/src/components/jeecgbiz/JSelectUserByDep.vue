<template>
  <div>
    <a-input-search
      v-model="selectedDepUsers"
      placeholder="请先选择用户"
      disabled
      @search="onSearchDepUser">
      <a-button slot="enterButton" :disabled="disabled">选择用户</a-button>
    </a-input-search>
    <j-select-user-by-dep-modal
      ref="selectModal"
      :modal-width="modalWidth"
      :selectType = "selectType"
      @ok="onSearchDepUserCallBack" />
  </div>
</template>

<script>
  import JSelectUserByDepModal from './modal/JSelectUserByDepModal'
  export default {
    name: 'JSelectUserByDep',
    components: { JSelectUserByDepModal },
    props:{
      modalWidth:{
        type:Number,
        default:1250,
        required:false
      },
      value:{
        type:String,
        required:false
      },
      disabled:{
        type: Boolean,
        required: false,
        default: false
      },
      selectType :{
        type:String,
        default: 'checkbox',
        required: false
      },
      managerId :{
        type:String,
        default: '',
        required: false
      }
    },
    data() {
      return {
        selectedDepUsers:"",
        selectedId: [],
      }
    },
    mounted(){
      this.selectedDepUsers = this.value
    },
    watch:{
      value(val){
        this.selectedDepUsers = val
      }
    },
    model: {
      prop: 'value',
      event: 'change'
    },
    methods: {
      //通过组织机构筛选选择用户
      onSearchDepUser() {
        this.$refs.selectModal.showModal();
        this.onSearchDepUserCallBack('');
      },
      onSearchDepUserCallBack(selectedDepUsers) {
        this.selectedDepUsers = selectedDepUsers;
        this.$emit("change",selectedDepUsers);
        this.$emit("getManagerId",this.$refs.selectModal.selectedRowKeys.join(","));
      }
    }
  }
</script>

<style scoped>

</style>