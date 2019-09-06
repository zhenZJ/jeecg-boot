<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="客户编号">
              <a-input placeholder="请输入客户编号" v-model="queryParam.customerNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="客户名称">
              <a-input placeholder="请输入客户名称" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="类型">
                <!--<a-input placeholder="请输入类型" v-model="queryParam.type1Id"></a-input>-->
                <j-dict-select-tag v-model="queryParam.type1Id" placeholder="请选择类型" dictCode="customer_type"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="状态">
                <a-input placeholder="请输入状态" v-model="queryParam.statusId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="来源">
                <j-dict-select-tag v-model="queryParam.sourceId" placeholder="请选择来源" dictCode="customer_source"/>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="16">
              <a-form-item label="下次联系时间">
                <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.nextDate_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.nextDate_end"></j-date>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('customer')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <customer-modal ref="modalForm" @ok="modalFormOk"></customer-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CustomerModal from './modules/CustomerModal'
  import JDate from '@/components/jeecg/JDate.vue'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  export default {
    name: "CustomerList",
    mixins:[JeecgListMixin],
    components: {
      JDate,
      CustomerModal,
      JDictSelectTag
    },
    data () {
      return {
        description: 'customer管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'客户编号',
            align:"center",
            dataIndex: 'customerNo'
          },
          {
            title:'客户名称',
            align:"center",
            dataIndex: 'name'
          },
          {
            title:'类型',
            align:"center",
            dataIndex: 'type1Id_dictText',
          },

          /*{
            title:'类型2',
            align:"center",
            dataIndex: 'type2Id'
          },
          {
            title:'类型3',
            align:"center",
            dataIndex: 'type3Id'
          },*/
          {
            title:'状态',
            align:"center",
            dataIndex: 'statusId'
          },
          {
            title:'来源',
            align:"center",
            dataIndex: 'sourceId_dictText'
          },
          {
            title:'最后联系时间',
            align:"center",
            dataIndex: 'lastDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'下次联系时间',
            align:"center",
            dataIndex: 'nextDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'remarks'
          },
          {
            title:'负责人',
            align:"center",
            dataIndex: 'managerId_dictText'
          },
          /*
          {
            title:'负责人所在部门',
            align:"center",
            dataIndex: 'managerOrgId'
          },
          {
            title:'createBy',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'createTime',
            align:"center",
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'updateBy',
            align:"center",
            dataIndex: 'updateBy'
          },
          {
            title:'updateTime',
            align:"center",
            dataIndex: 'updateTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'delFlag',
            align:"center",
            dataIndex: 'delFlag'
          },
      */
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/crm/customer/list",
          delete: "/crm/customer/delete",
          deleteBatch: "/crm/customer/deleteBatch",
          exportXlsUrl: "/crm/customer/exportXls",
          importExcelUrl: "crm/customer/importExcel",
        },
        dictOptions:{
        } 
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>