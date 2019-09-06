<template>
  <a-card :bordered="false">
    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>已选择&nbsp;<a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>项&nbsp;&nbsp;
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        bordered
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <template slot="avatarslot" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <a-avatar shape="square" :src="getAvatarView(record.avatar)" icon="user"/>
          </div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <user-modal ref="modalForm" @ok="modalFormOk"></user-modal>

  </a-card>
</template>

<script>
  import UserModal from './system/modules/UserModal'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'
  export default {
    name: "TestUser",
    mixins: [JeecgListMixin],
    components: {
      UserModal,
    },
    data(){
      return {
        description: '这是用户管理页面',
        columns: [
          /*{
           title: '#',
           dataIndex: '',
           key:'rowIndex',
           width:60,
           align:"center",
           customRender:function (t,r,index) {
           return parseInt(index)+1;
           }
           },*/
          {
            title: '用户账号',
            align: "center",
            dataIndex: 'username',
            width: 120
          },
          {
            title: '真实姓名',
            align: "center",
            width: 100,
            dataIndex: 'realname',
          },
//          {
//            title: '头像',
//            align: "center",
//            width: 120,
//            dataIndex: 'avatar',
//            scopedSlots: {customRender: "avatarslot"}
//          },

          {
            title: '性别',
            align: "center",
            width: 80,
            dataIndex: 'sex_dictText',
            sorter: true
          },
          {
            title: '生日',
            align: "center",
            width: 180,
            dataIndex: 'birthday'
          },
          {
            title: '手机号码',
            align: "center",
            width: 100,
            dataIndex: 'phone'
          },
          {
            title: '邮箱',
            align: "center",
            dataIndex: 'email'
          },
          {
            title: '状态',
            align: "center",
            width: 80,
            dataIndex: 'status_dictText'
          },
          /* {
           title: '创建时间',
           align: "center",
           width: 150,
           dataIndex: 'createTime',
           sorter: true
           },*/
          {
            title: '操作',
            dataIndex: 'action',
            scopedSlots: {customRender: 'action'},
            align: "center",
            width: 170
          }

        ],
        url: {
          imgerver: window._CONFIG['domianURL'] + "/sys/common/view",
          syncUser: "/process/extActProcess/doSyncUser",
          list: "/sys/user/list",
          delete: "/sys/user/delete",
          deleteBatch: "/sys/user/deleteBatch",
          exportXlsUrl: "/sys/user/exportXls",
          importExcelUrl: "sys/user/importExcel",
        },
      }
    }
  }
</script>

<style scoped>

</style>