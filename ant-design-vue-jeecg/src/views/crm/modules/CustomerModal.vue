<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="客户编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'customerNo', validatorRules.customerNo]" placeholder="请输入客户编号"></a-input>
        </a-form-item>

        <a-form-item label="客户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入客户名称"></a-input>
        </a-form-item>
        <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag v-model="model.type1Id" placeholder="请选择类型" dictCode="customer_type"/>
        </a-form-item>

        <a-form-item label="类型2" :labelCol="labelCol" :wrapperCol="wrapperCol" v-show="!type2Disabled">
          <a-input v-decorator="[ 'type2Id', validatorRules.type2Id]" placeholder="请输入类型2" ></a-input>
        </a-form-item>

        <a-form-item label="类型3" :labelCol="labelCol" :wrapperCol="wrapperCol" v-show="!type3Disabled">
          <a-input v-decorator="[ 'type3Id', validatorRules.type3Id]" placeholder="请输入类型3" ></a-input>
        </a-form-item>

        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol" v-show="!statusDisabled">
          <a-input v-decorator="[ 'statusId', validatorRules.statusId]" placeholder="请输入状态" ></a-input>
        </a-form-item>

        <a-form-item label="来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag v-model="model.sourceId" placeholder="请选择来源" dictCode="customer_source"/>
        </a-form-item>

        <a-form-item label="最后联系时间" :labelCol="labelCol" :wrapperCol="wrapperCol" v-show="!lastDateDisabled">
          <j-date placeholder="请选择最后联系时间" v-decorator="[ 'lastDate', validatorRules.lastDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>

        <a-form-item label="下次联系时间" :labelCol="labelCol" :wrapperCol="wrapperCol" v-show="!nextDateDisabled">
          <j-date placeholder="请选择下次联系时间" v-decorator="[ 'nextDate', validatorRules.nextDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>

        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['remarks']" rows="4" placeholder="请输入备注"/>
        </a-form-item>

        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!--<a-input v-decorator="[ 'managerId', validatorRules.managerId]" placeholder="请输入负责人"></a-input>-->
          <j-select-user-by-dep v-model="model.managerId_dictText" @getManagerId="getManagerId($event)" :selectType="'radio'" ></j-select-user-by-dep>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'

  export default {
    name: "CustomerModal",
    components: {
      JDate,
      JSelectUserByDep,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        type2Disabled:true,
        type3Disabled:true,
        statusDisabled:true,
        lastDateDisabled:true,
        nextDateDisabled:true,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
          customerNo:{
            rules: [{
              required: true, message: '请输入客户编号!'
            }]
          },
          name:{
            rules: [{
              required: true, message: '请输入客户名称!'
            }]
          },
          type1Id:{},
          type2Id:{},
          type3Id:{},
          statusId:{},
          sourceId:{},
          lastDate:{},
          nextDate:{},
          remarks:{},
          managerId:{},
          managerOrgId:{},
          createBy:{},
          createTime:{},
          updateBy:{},
          updateTime:{},
          delFlag:{},
        },
        url: {
          add: "/crm/customer/add",
          edit: "/crm/customer/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'customerNo','name','type1Id','type2Id','type3Id','statusId','sourceId','lastDate','nextDate','remarks','managerId','managerOrgId','createBy','createTime','updateBy','updateTime','delFlag'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      handleCancel () {
        this.close();
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'customerNo','name','type1Id','type2Id','type3Id','statusId','sourceId','lastDate','nextDate','remarks','managerId','managerOrgId','createBy','createTime','updateBy','updateTime','delFlag'))
      },
      getManagerId(managerId){
        this.model.managerId = managerId;
      }

    }
  }
</script>