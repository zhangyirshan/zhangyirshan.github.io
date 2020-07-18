---
title: Antd标签
p: 前端/Antd/Antd标签
date: 2020-07-18 17:02:31
tags: [前端,Antd]
categories: [前端,Antd]
---
## 制作的页面代码

里面有时间，下拉框（可清除），表单分页（子目录）

```js
/*
 * @Description: 奖池分组
 * @version: 1.0.0
 * @Company: sdbean
 * @Author: zhangyi
 * @Date: 2020-7-18
 */

import {IawardGroup, Igachats} from './models/gacha';
import React, {Component} from 'react';
import * as styles from './CoinWeightManager.less';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import {connect} from 'dva';
import {ILogin} from '@/models/login';
import EditorAwardGroup from './EditorAwardGroup';
import {PlusOutlined} from '@ant-design/icons';
import { getGachaSeasonGroup, getGachaSeasonSelectName} from '@/utils/mallTool';
import {
  Button,
  Spin,
  Row,
  Card,
  Col,
  Select,
  Table,
  message
} from 'antd';
import {ColumnProps} from "antd/lib/table";

export const PAGESIZE = 8;

@connect(({loading, login, gacha}: { loading: IdvaLoading; login: ILogin; gacha: Igachats }) => ({
  uid: login.uid,
  isLoading: loading.models.getSeasonList,
  seasonList: gacha.seasonList,
  coinList: gacha.coinList,
  awardList: gacha.awardList,
  modelType: gacha.modelType,
  coinModelType: gacha.coinModelType,
  currentWeightSeasonId: gacha.currentWeightSeasonId,
  simpleSeasonList: gacha.simpleSeasonList,
  simpleSeasonGroupList: gacha.simpleSeasonGroupList,
  currentGroupSeasonId: gacha.currentGroupSeasonId,
  currentGroupId: gacha.currentGroupId,
  groupList: gacha.groupList,
  groupId: gacha.groupId,
}))

class AwardGroup extends Component<Igachats> {

  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      currentPage: 1,
    };
  }

  componentDidMount() {
    const {dispatch} = this.props;

    dispatch({
      type: 'gacha/getSimpleSeasonList',
    });
    dispatch({
      type: 'gacha/getSimpleSeasonCoinList',
    });
    dispatch({
      type: 'gacha/getGachaSeasonAward',
    });
    dispatch({
      type: 'gacha/getGroupList',
      payload: {gacha_season_id: this.props.currentWeightSeasonId, gacha_group_id: this.props.groupId}
    });
  }

  render() {
    const {isLoading} = this.props;

    return (
      <PageHeaderWrapper title="奖池分组" content={this.renderHeader()}>
        <Spin spinning={!!this.props.isLoading}>
          {this.props.currentGroupSeasonId > 0 && <EditorAwardGroup/>}
        </Spin>
        <div className={styles.avswitch}>
          <Row>
            <Col span={2}>
              <Button type="primary" icon={<PlusOutlined/>} size="large" onClick={this.onClickCreate}>添加</Button>
            </Col>
            <Col span={2}>
              <Button type="primary" icon={<PlusOutlined/>} size="large" onClick={this.onClickUpdate}>更新</Button>
            </Col>
          </Row>
        </div>
        <Card>
          <Table
            //  scroll={{ x: 'calc(1800px + 50%)', y: 720 }}
            // scroll={{ x: 2000, y: 720 }}
            className={styles.tableClass}
            rowClassName={this.rowClassName}
            columns={this.columns}
            dataSource={this.props.groupList}
            loading={!!isLoading}
            bordered={true}
            rowKey={(record, index) => index.toString()}
            pagination={{
              pageSize: PAGESIZE,
              current: this.state.currentPage,
              total: this.props.groupList ? this.props.groupList.length : 0,
              onChange: this.changePage,
            }}
          />
        </Card>
      </PageHeaderWrapper>
    );
  }

  rowClassName = (record, index) => {
    return (record.current === true ? styles.current : '')
  }

  changePage = (page) => {
    this.setState({currentPage: page});
  };

  //表格列规则
  columns: Array<ColumnProps<IawardGroup>> = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      align: 'center',
    },
    {
      title: '季度 ID',
      dataIndex: 'gacha_season_id',
      key: 'gacha_season_id',
      align: 'center',
    },
    {
      title: '分组 ID',
      dataIndex: 'gacha_group_id',
      key: 'gacha_group_id',
      align: 'center',
    },
    {
      title: '名称',
      dataIndex: 'item_name',
      key: 'item_name',
      align: 'center',
    },
    {
      title: '操作',
      key: 'action',
      align: 'center',
      render: (value, row, index) => {
        return (
          <div className={styles.cellCenter}>
            <Button type="primary" onClick={() => this.onClickDelete(row)}>删除</Button>
          </div>
        );
      },
    },
    {
      title: '中文备注',
      dataIndex: 'cn_remark',
      key: 'cn_remark',
      align: 'center',
    },
    {
      title: '分类名称',
      dataIndex: 'type_name',
      key: 'type_name',
      align: 'center',
    },
    {
      title: '物品等级',
      dataIndex: 'item_level',
      key: 'item_level',
      align: 'center',
      render: (value) => {
        switch (value) {
          case 0:
            return '无';
          case 1:
            return 'C';
          case 2:
            return 'R';
          case 3:
            return 'SR';
          case 4:
            return 'SSR';
          case 5:
            return 'UR';
        }
      },
    },
  ];

  //渲染头部
  renderHeader() {
    return (
      <div className={styles.avframeheader}>

        <div className={styles.title}>季度选择：</div>
        <Select style={{width: 280}}
                className={styles.actionItem}
                placeholder="请选择要查看的季度"
                onChange={this.onChangeSelect}
                defaultValue={getGachaSeasonSelectName(this.props.currentWeightSeasonId, this.props.simpleSeasonList)}
        >
          {this.props.simpleSeasonList.map((item, index) => {
            return (
              <Option key={index.toString()} value={this.props.simpleSeasonList[index].gacha_season_id}>
                {"季度ID: " + this.props.simpleSeasonList[index].gacha_season_id + " 名称: " + this.props.simpleSeasonList[index].name}
              </Option>
            );
          })}
        </Select>
        <div className={styles.title}>分组选择：</div>
        <Select style={{width: 280}}
                allowClear
                className={styles.actionItem}
                placeholder="请选择要查看的分组"
                onChange={this.onChangeGroupSelect}
                defaultValue={getGachaSeasonGroup(this.props.currentWeightSeasonId, this.props.simpleSeasonGroupList)}
        >
          {
            this.props.simpleSeasonGroupList.map((item, index) => {
            return (
              <Option key={index.toString()} value={this.props.simpleSeasonGroupList[index].gacha_season_group_id}>
                {"分组ID: " + this.props.simpleSeasonGroupList[index].gacha_season_group_id + " 名称: " + this.props.simpleSeasonGroupList[index].name}
              </Option>
            );
          })}
        </Select>
      </div>
    );
  }

  //选择框
  onChangeSelect = value => {
    const {dispatch} = this.props;

    this.changePage(1);
    dispatch({
      type: 'gacha/setCurrentWeight',
      payload: null
    });
    dispatch({
      type: 'gacha/getGroupList',
      payload: {gacha_season_id: value}
    });
    dispatch({
      type: 'gacha/setCurrentWeightSeasonId',
      payload: value
    });
    dispatch({
      type: 'gacha/getSimpleSeasonGroupList',
      payload: {gacha_season_id: value}
    });
  };

  onChangeGroupSelect = value => {
    const {dispatch} = this.props;
    this.changePage(1);
    dispatch({
      type: 'gacha/getGroupList',
      payload: {gacha_season_id: this.props.currentWeightSeasonId, gacha_group_id: value}
    });
    dispatch({
      type: 'gacha/setCurrentGroupId',
      payload: value,
    })
  }

  //点击编辑
  onClickDelete = (row: any) => {
    if (this.props.currentWeightSeasonId <= 0) {
      message.error("请先选择季度");
      return;
    }

    this.props.dispatch({
      type: 'gacha/deleteSeasonGroupAward',
      payload: row
    });

    message.success("删除成功");
    this.props.dispatch({
      type: 'gacha/getGroupList',
      payload: {gacha_season_id: this.props.currentWeightSeasonId, gacha_group_id: row.gacha_group_id}
    });
  };

  onClickCreate = () => {
    if (this.props.currentWeightSeasonId <= 0) {
      message.error("请先选择季度");
      return;
    }
    this.props.dispatch({
      type: 'gacha/getCurrentGroupSeasonId',
      payload: {}
    });
    this.props.dispatch({
      type: 'gacha/getGachaAwardPoolList',
      payload: {gacha_season_id: this.props.currentWeightSeasonId}
    });
  };

  onClickUpdate = () => {
    if (this.props.currentWeightSeasonId <= 0) {
      message.error("请先选择季度");
      return;
    }
    if (this.props.currentGroupId <= 0){
      message.error("请先选择分组");
      return;
    }

    this.props.dispatch({
      type: 'gacha/setCurrentGroupSeasonId',
      payload: [{id:this.props.currentGroupId}]
    });

    this.props.dispatch({
      type: 'gacha/getGachaAwardPoolList',
      payload: {gacha_season_id: this.props.currentWeightSeasonId}
    });

    this.props.dispatch({
      type: 'gacha/getSelectedRowKeys',
      payload: {gacha_season_id: this.props.currentWeightSeasonId,gacha_group_id: this.props.currentGroupId}
    });
  };
}

export default AwardGroup;

```

```js
/*
 * @Description: 订单管理
 * @version: 1.0.0
 * @Company: sdbean
 * @Author: zhangyi
 * @Date: 2020-7-18
 */

import {IgachaOrder, Igachats} from './models/gacha';
import React, {Component} from 'react';
import * as styles from './CoinWeightManager.less';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import {connect} from 'dva';
import {ILogin} from '@/models/login';
import {
  Row,
  Card,
  Col,
  Select,
  DatePicker,
  Table,
} from 'antd';
import moment from "moment";
import {ColumnProps} from "antd/es/table";
import {getGachaSeasonSelectName} from "@/utils/mallTool";

export const PAGESIZE = 8;

@connect(({loading, login, gacha}: { loading: IdvaLoading; login: ILogin; gacha: Igachats }) => ({
  uid: login.uid,
  isLoading: loading.models.getSeasonList,
  orderList: gacha.orderList,
  currentWeightSeasonId: gacha.currentWeightSeasonId,
  simpleSeasonList: gacha.simpleSeasonList,
}))

class OrderManage extends Component<Igachats> {

  constructor(props) {
    super(props);
    this.state = {
      startTime: 0,
      endTime: 0,
      orderState: 2,
      seasonId: 0,
      currentPage: 1,
    };
  }

  componentDidMount() {
    const {dispatch} = this.props;

    dispatch({
      type: 'gacha/getOrderList',
      payload: {start: 0, offset: PAGESIZE}
    });
    dispatch({
      type: 'gacha/getSimpleSeasonList',
    });
  }

  render() {
    const {isLoading} = this.props;

    return (
      <PageHeaderWrapper title="订单管理" content={this.renderHeader()}>
        <Card>
          <Table
            scroll={{x: 'calc(1800px + 50%)', y: 450}}
            className={styles.tableClass}
            rowClassName={this.rowClassName}
            columns={this.columns}
            dataSource={this.props.orderList.data}
            loading={!!isLoading}
            bordered={true}
            rowKey={(record, index) => index.toString()}
            pagination={{
              pageSize: this.props.orderList.pageSize,
              current: this.state.currentPage,
              total: this.props.orderList.count,
              onChange: this.changePage,
            }}
          />
        </Card>
      </PageHeaderWrapper>
    );
  }

  renderHeader() {
    return (
      <Row style={{marginBottom: 15}}>
        <Col span={5}>
          季度选择：
          <Select style={{width: 280}}
                  className={styles.actionItem}
                  placeholder="请选择要查看的季度"
                  allowClear
                  onChange={this.onChangeSelect}
                  defaultValue={getGachaSeasonSelectName(this.props.currentWeightSeasonId, this.props.simpleSeasonList)}
          >
            {this.props.simpleSeasonList.map((item, index) => {
              return (
                <Option key={index.toString()} value={this.props.simpleSeasonList[index].gacha_season_id}>
                  {"季度ID: " + this.props.simpleSeasonList[index].gacha_season_id + " 名称: " + this.props.simpleSeasonList[index].name}
                </Option>
              );
            })}
          </Select>
        </Col>
        <Col span={5}>
          订单状态:
          <Select style={{width: 280}}
                  allowClear
                  onChange={this.onChangeOrderState}>
            <Option value="0">已发放</Option>
            <Option value="1">未发放</Option>
          </Select>
        </Col>
        <Col span={5}>
          创建时间: <DatePicker.RangePicker
          allowClear
          onChange={this.onTimeChange}
          showTime={{
            hideDisabledOptions: true,
            defaultValue: [moment('00:00:00', 'HH:mm:ss'), moment('11:59:59', 'HH:mm:ss')],
          }}
          style={{width: '300px'}}/>
        </Col>
      </Row>
    );
  }

  rowClassName = (record) => {
    return (record.current === true ? styles.current : '')
  }

  changePage = (page) => {
    this.props.dispatch({
      type: 'gacha/getOrderList',
      payload: {
        start: (page - 1) * PAGESIZE,
        offset: PAGESIZE,
        gacha_season_id: this.state.seasonId,
        startTime: this.state.startTime,
        endTime: this.state.endTime,
        orderState: this.state.orderState,
      }
    });
    this.setState({currentPage: page});
  };

  //表格列规则
  columns: Array<ColumnProps<IgachaOrder>> = [
    {
      title: 'ID',
      dataIndex: 'id',
      key: 'id',
      align: 'center',
      render: (value, row, index) => {
        return this.renderRow(value, row, index);
      },
    },
    {
      title: '订单号',
      dataIndex: 'order_no',
      key: 'order_no',
      align: 'center',
      width: 150,
      render: (value, row, index) => {
        return this.renderRow(value, row, index);
      },
    },
    {
      title: '订单状态',
      dataIndex: 'status',
      key: 'status',
      align: 'center',
      render: (value, row, index) => {
        if (row.rowSpan == null) {
          row.rowSpan = 0;
        }
        return {
          children: <div>{value == 0 ? "已发放" : "未发放"}</div>,
          props: {rowSpan: row.rowSpan},
        };
      },
    },
    {
      title: '创建时间',
      dataIndex: 'create_time',
      key: 'create_time',
      align: 'center',
      render: (value, row, index) => {
        if (row.rowSpan == null) {
          row.rowSpan = 0;
        }
        if (!!value) {
          return {
            children: <div>{moment(value).format('YYYY-MM-DD HH:mm:ss')}</div>,
            props: {rowSpan: row.rowSpan},
          };
        } else {
          return {
            children: <div>无</div>,
            props: {rowSpan: row.rowSpan},
          };
        }
      },
      width: 150,
    },
    {
      title: '消耗货币ID',
      dataIndex: 'coin_id',
      key: 'coin_id',
      align: 'center',
      render: (value, row, index) => {
        return this.renderRow(value, row, index);
      },
    },
    {
      title: '消耗货币数量',
      dataIndex: 'coin_num',
      key: 'coin_num',
      align: 'center',
      render: (value, row, index) => {
        return this.renderRow(value, row, index);
      },
    },
    {
      title: '消耗货币名称',
      dataIndex: 'coin_name',
      key: 'coin_name',
      align: 'center',
      render: (value, row, index) => {
        return this.renderRow(value, row, index);
      },
    },
    {
      title: '抽奖次数',
      dataIndex: 'order_number',
      key: 'order_number',
      align: 'center',
      render: (value, row, index) => {
        return this.renderRow(value, row, index);
      },
    },
    {
      title: '用户Id',
      dataIndex: 'user_id',
      key: 'user_id',
      align: 'center',
      render: (value, row, index) => {
        return this.renderRow(value, row, index);
      },
    },
    {
      title: '季度Id',
      dataIndex: 'gacha_season_id',
      key: 'gacha_season_id',
      align: 'center',
      render: (value, row, index) => {
        return this.renderRow(value, row, index);
      },
    },
    {
      title: '道具配置',
      align: 'center',
      children: [
        {
          title: 'gacha赛季奖励Id',
          dataIndex: 'gacha_season_award_id',
          key: 'gacha_season_award_id',
          align: 'center',
        },
        {
          title: '道具DicId',
          dataIndex: 'item_dic_id',
          key: 'item_dic_id',
          align: 'center',
        },
        {
          title: '道具Id',
          dataIndex: 'item_id',
          key: 'item_id',
          align: 'center',
        },
        {
          title: '道具名称',
          dataIndex: 'item_name',
          key: 'item_name',
          align: 'center',
        },
        {
          title: '中文备注',
          dataIndex: 'cn_remark',
          key: 'cn_remark',
          align: 'center',
        },
        {
          title: '道具属性',
          dataIndex: 'property_aging',
          key: 'property_aging',
          align: 'center',
          render: (value) => {
            return value == 0 ? '永久类' : '时效类';
          },
        },
        {
          title: '道具数量',
          dataIndex: 'num',
          key: 'num',
          align: 'center',
        },

      ]
    },
  ];

  renderRow = (value, row, index) => {
    if (row.rowSpan == null) {
      row.rowSpan = 0;
    }
    return {
      children: value,
      props: {rowSpan: row.rowSpan},
    };
  };

  onChangeOrderState = value => {
    this.props.dispatch({
      type: 'gacha/getOrderList',
      payload: {
        start: 0,
        offset: PAGESIZE,
        gacha_season_id: this.state.seasonId,
        startTime: this.state.startTime,
        endTime: this.state.endTime,
        orderState: value,
      }
    });
    this.setState({orderState: value})
  }

  onTimeChange = (time, str) => {

    this.props.dispatch({
      type: 'gacha/getOrderList',
      payload: {
        start: 0,
        offset: PAGESIZE,
        gacha_season_id: this.state.seasonId,
        startTime: new Date(str[0]),
        endTime: new Date(str[1]),
        orderState: this.state.orderState,
      }
    });
    this.setState({startTime: new Date(str[0])});
    this.setState({endTime: new Date(str[1])});
  }

  onChangeSelect = value => {
    this.props.dispatch({
      type: 'gacha/getOrderList',
      payload: {
        start: 0,
        offset: PAGESIZE,
        gacha_season_id: value,
        startTime: this.state.startTime,
        endTime: this.state.endTime,
        orderState: this.state.orderState,
      }
    });
    this.setState({seasonId: value})
  }
}

export default OrderManage;

```

```js
import { array } from 'prop-types';
import {
  getGachaAwardPoolList,
  getCurrentGroupSeasonId,
  createGachaSeasonRule,
  updateGachaSeasonRule,
  getGachaSeasonAwardGroup,
  getSimpleSeasonRuleGroupList,
  getSimpleSeasonGroupList,
  getSeasonRuleList,
  gachaRefreshSeasonAwardNum,
  createGachaSeasonAward,
  updateGachaSeasonAward,
  createGachaSeasonAwardCoinWeight,
  updateGachaSeasonAwardCoinWeight,
  getSimpleSeasonList,
  getSimpleSeasonCoinList,
  getGachaSeasonAwardCoinWeight,
  getGachaSeasonAward,
  getItemDicList,
  getItemCateList,
  getGachaAwardPool,
  createGachaAwardPool,
  updateGachaAwardPool,
  getGachaSeasonList,
  updateSeason,
  createSeason,
  getCoinList,
  updateSeasonCoin,
  createSeasonCoin,
  insertGachaSeasonAwardGroup, deleteSeasonGroupAward, getSelectedRowKeys, getOrderList
} from '@/services/gacha';
import moment from 'moment';
import { message } from 'antd';
import { IitemCate, IitemDic } from '@/dto/mallItem';

export const isgiveList = [
  "小于n次不掉落(道具保值)",
  "前n次没有获得的道具,必掉落其中一个(用户保底)",
  "不可重复掉落(道具保值)",
  "前n次没有获得的道具,其权重增加(用户保底)",
  "小于n次权重增增加",
  "每抽n次没有获得的道具,必掉落其中一个,并重新计算n次(用户保底)",
  "每抽n次没有获得的道具,其权重增加并重新计算n次(用户保底)",
  "连抽,某物品不可重复掉落n次(道具保值)",
];

export const opentypeList = [
  "单抽",
  "连抽",
  "全部"
];

export interface Icoin {
  id: number;
  name: number;
}

export interface IsimpleSeason {
  gacha_season_id: number;
  name: number;
}

export interface IsimpleSeasonGroup {
  gacha_season_group_id: number;
  name: number
}

export interface IsimpleSeasonCoin {
  gacha_season_id: number;
  coin_id: number;
  name: number;
}

export interface IgachaSeason {
  id: number;
  name: string;
  starttime: string;
  endtime: string;
  first_per_free: number;
  bg_url: string;
  multi: number;
  pre_free_time: number;
  multi_free_time: number;
  first_multi_free: number;
  seasonCoinId: number;
  num: number;
  coin: number;
  coin_id: number;
  coinName: string;

  rowSpan: number;
  current: boolean;
}

export interface Irule {
  id: number;
  gacha_season_give_id: number;
  gacha_season_id: number;
  isgive: number;
  weight: number;
  freq: number;
  gacha_group_id: number;
  open_type: number;
  coin_id: number;
  group_name: string;
  coin_name: string;
}

export interface IawardPool {
  id: number;
  item_name: string;
  item_dic_id: number;
  item_id: number;
  remark: string;
  cate_id: number;
  item_pic: string;
  item_level: number;
  type_name: string;
  cn_remark: string;
  gacha_season_award_id: number;
  gacha_award_pool_id: number;
  gacha_season_id: number;
  award_level: number;
  num: number;
  valid_num: number;
  per_num: number;
  min_num: number;
  max_num: number;
  isshow: number;
  ischange: number;
  ischange_coin_id: number;
  ischange_coin_num: number;
  coin_name: string;
  delsign: number;
  gacha_season_award_coin_weight_id: number;
  weight: number;
  coin_id: number;
}

export interface IawardGroup {
  id: number;
  gacha_season_id: number;
  gacha_group_id: number;
  item_name: number;
  cn_remark: number;
  type_name: number;
  item_level: number; // 道具等级，0:无，1:C，2:R，3:SR，4:SSR，5:UR
}

export interface IgachaSeasonAward{
  id: number;
}

export interface IorderManage{
  count: number;
  pagesize: number;
  data: IgachaOrder[];
}

export interface IgachaOrder{
  id: number; // 订单Id
  order_no: number; // 订单号
  status: number; // 订单状态 0：以发放 1:未发放
  gacha_season_award_id: number;
  gacha_season_id: number;
  create_time: number;
  user_id: number;
  itemm_dic_id: number;
  item_id: number;
  item_name: string;
  cn_remark: string;
  property_aging: number;
  num: number;
  coin_id: number;
  coin_num: number;
  coin_name: string;
  order_number: number;
  rowSpan: number;
}

export interface IgachaAwardPool {
  id: number;
  item_name: number;
  cn_remark: number;
  type_name: number;
  item_level: number; // 道具等级，0:无，1:C，2:R，3:SR，4:SSR，5:UR
}

export interface IseasonManagerProps {
  uid: number;
  isLoading: boolean;
  dispatch: Function;
  seasonList: IgachaSeason[];
  seasonSimpleList: IgachaSeason[];
  coinList: Icoin[];
  itemDicList: IitemDic[];
  itemCateList: IitemCate[];
  modelType: number;
  coinModelType: number;
  imgModelType: number;
  selectId: number;
  ruleList: Irule[];
}

interface Igachats {
  seasonList: IgachaSeason[];
  seasonSimpleList: IgachaSeason[];
  coinList: Icoin[];
  poolList: IawardPool[];
  awardList: IawardPool[];
  weightList: IawardPool[];
  simpleSeasonList: IsimpleSeason[];
  simpleSeasonCoinList: IsimpleSeasonCoin[];
  itemDicList: IitemDic[];
  itemCateList: IitemCate[];
  modelType: number;
  coinModelType: number;
  imgModelType: number;
  selectId: number;
  poolModelType: number;
  awardModelType: number;
  currentWeightSeasonId: number;
  currentWeight: IawardPool;
  ruleList: Irule[];
  currentRule: Irule;
  simpleRuleGroupList: IsimpleSeasonGroup[],
  // 奖池分组数据
  simpleSeasonGroupList: IsimpleSeasonGroup[],
  groupList: IawardGroup[],
  groupId: number;
  currentGroupSeasonId: number;
  currentGroupId: number;
  gachaAwardPoolList: IgachaAwardPool[];
  selectedRowKeys: IgachaSeasonAward[];
  // 订单管理
  orderList: IorderManage[];
}

const init: Igachats = {
  seasonList: [],
  seasonSimpleList: [],
  coinList: [],
  poolList: [],
  awardList: [],
  weightList: [],
  simpleSeasonList: [],
  simpleSeasonCoinList: [],
  simpleSeasonGroupList: [],
  groupList: [],
  selectedRowKeys: [],
  gachaAwardPoolList: [],
  groupId: 0,
  currentGroupId: 0,
  itemDicList: [],
  itemCateList: [],
  modelType: 0,
  coinModelType: 0,
  imgModelType: 0,
  selectId: -1,
  poolModelType: 0,
  awardModelType: 0,
  currentGroupSeasonId: 0,
  currentWeightSeasonId: 0,
  currentWeight: null,
  ruleList: [],
  currentRule: null,
  simpleRuleGroupList: [],
  orderList: [],
}

function compareDate(date1, date2) {
  date1 = new Date(date1);
  date2 = new Date(date2);
  if (date1.getTime() >= date2.getTime()) {
    return true;
  } else {
    return false;
  }
}

export default {
  namespace: 'gacha',

  state: init,

  effects: {

    * getSeasonList(action, { call, put }) {
      const response: IgachaSeason = yield call(getGachaSeasonList);
      //更新基础状态
      yield put({ type: 'setSeasonList', payload: response });
    },

    * updateSeason({ payload }: { payload: IgachaSeason }, { call, put }) {
      const response = yield call(updateSeason, payload);
      //更新成功
      if (response) {
        message.success("编辑成功");
        yield put({ type: 'setModelType', payload: 0 });
      } else {
        message.error("编辑失败，请重试");
      }
      yield put({ type: 'getSeasonList' })
    },

    * createSeason({ payload }: { payload: IgachaSeason }, { call, put }) {
      const response = yield call(createSeason, payload);
      //更新成功
      if (response) {
        message.success("新建成功");
        yield put({ type: 'setModelType', payload: 0 });
      } else {
        message.error("新建失败，请重试");
      }
      yield put({ type: 'getSeasonList' })
    },

    * getCoinList(action, { call, put }) {
      const response: IgachaSeason = yield call(getCoinList);
      //更新基础状态
      yield put({ type: 'setCoinList', payload: response });
    },

    * updateSeasonCoin({ payload }: { payload: IgachaSeason }, { call, put }) {
      const response = yield call(updateSeasonCoin, payload);
      //更新成功
      if (response) {
        message.success("编辑成功");
        yield put({ type: 'setCoinModelType', payload: 0 });
      } else {
        message.error("编辑失败，请重试");
      }
      yield put({ type: 'getSeasonList' })
    },

    * createSeasonCoin({ payload }: { payload: IgachaSeason }, { call, put }) {
      const response = yield call(createSeasonCoin, payload);
      //更新成功
      if (response) {
        message.success("新建成功");
        yield put({ type: 'setCoinModelType', payload: 0 });
      } else {
        message.error("新建失败，请重试");
      }
      yield put({ type: 'getSeasonList' })
    },

    * getGachaAwardPool(action, { call, put }) {
      const response = yield call(getGachaAwardPool);
      //更新基础状态
      yield put({ type: 'setPoolList', payload: response });
    },

    * createGachaAwardPool({ payload }: { payload: IgachaSeason }, { call, put }) {
      const response = yield call(createGachaAwardPool, payload);
      if (response) {
        message.success("新建成功");
        yield put({ type: 'getGachaAwardPool' });
        yield put({ type: 'setPoolModelType', payload: 0 });
      }
    },

    * updateGachaAwardPool({ payload }: { payload: IgachaSeason }, { call, put }) {
      const response = yield call(updateGachaAwardPool, payload);
      if (response) {
        message.success("编辑成功");
        yield put({ type: 'getGachaAwardPool' });
        yield put({ type: 'setPoolModelType', payload: 0 });
      }
    },

    * getItemCateList({ payload }: { payload }, { call, put }) {
      const response = yield call(getItemCateList, payload);
      if (response) {
        yield put({ type: 'setItemCateList', payload: response });
      }
    },

    * getItemDicList({ payload }: { payload }, { call, put }) {
      const response = yield call(getItemDicList, payload);
      if (response) {
        yield put({ type: 'setItemDicList', payload: response });
      }
    },

    * getGachaSeasonAward({ payload }: { payload }, { call, put }) {
      const response = yield call(getGachaSeasonAward, payload);
      if (response) {
        yield put({ type: 'setAwardList', payload: response });
      }
    },

    * getGachaSeasonAwardCoinWeight({ payload }: { payload }, { call, put }) {
      const response = yield call(getGachaSeasonAwardCoinWeight, payload);
      if (response) {
        yield put({ type: 'setWeightList', payload: response });
      }
    },

    * getSimpleSeasonList({ payload }: { payload }, { call, put }) {
      const response = yield call(getSimpleSeasonList, payload);
      if (response) {
        yield put({ type: 'setSimpleSeasonList', payload: response });
      }
    },
    * getOrderList({ payload }: { payload }, { call, put }) {
      const response = yield call(getOrderList, payload);
      if (response) {
        yield put({ type: 'setOrderList', payload: response });
      }
    },

    * getSimpleSeasonGroupList({ payload }: { payload }, { call, put }) {
      const response = yield call(getSimpleSeasonGroupList, payload);
      if (response) {
        yield put({ type: 'setSimpleSeasonGroupList', payload: response });
      }
    },

    * deleteSeasonGroupAward({ payload }: { payload }, { call, put }) {
      const response = yield call(deleteSeasonGroupAward, payload);
    },

    * getCurrentGroupSeasonId({ payload }: { payload }, { call, put }) {
      const response = yield call(getCurrentGroupSeasonId);
      if (response) {
        yield put({ type: 'setCurrentGroupSeasonId', payload: response });
      }
    },

    * getGroupList({ payload }: { payload }, { call, put }) {
      const response = yield call(getGachaSeasonAwardGroup, payload);
      if (response) {
        yield put({ type: 'setGroupList', payload: response });
      }
    },

    * insertGachaSeasonAwardGroup({ payload }: { payload }, { call, put }) {
      const response = yield call(insertGachaSeasonAwardGroup, payload);//更新成功
      return response;

    },

    * getGachaAwardPoolList({ payload }: { payload }, { call, put }) {
      const response = yield call(getGachaAwardPoolList,payload);
      if (response) {
        yield put({ type: 'setGachaAwardPoolList', payload: response });
      }
    },

    * getSimpleSeasonCoinList({ payload }: { payload }, { call, put }) {
      const response = yield call(getSimpleSeasonCoinList, payload);
      if (response) {
        yield put({ type: 'setSimpleSeasonCoinList', payload: response });
      }
    },

    * getSelectedRowKeys({ payload }: { payload }, { call, put }) {
      const response = yield call(getSelectedRowKeys, payload);
      if (response) {
        yield put({ type: 'setSelectedRowKeys', payload: response });
      }
    },


    *createGachaSeasonAwardCoinWeight({ payload }: { payload: any }, { call, put }) {
      const response = yield call(createGachaSeasonAwardCoinWeight, payload);
      //更新成功
      if (response) {
        message.success("创建成功");
        yield put({ type: 'setCurrentWeight', payload: null });
        yield put({ type: 'getGachaSeasonAwardCoinWeight', payload: { gacha_season_id: payload.gacha_season_id } });
      } else {
        message.error("创建失败，如已经存在此条目，请编辑");
      }
    },

    *updateGachaSeasonAwardCoinWeight({ payload }: { payload: any }, { call, put }) {
      const response = yield call(updateGachaSeasonAwardCoinWeight, payload);
      //更新成功
      if (response) {
        message.success("编辑成功");
        yield put({ type: 'setCurrentWeight', payload: null });
        yield put({ type: 'getGachaSeasonAwardCoinWeight', payload: { gacha_season_id: payload.gacha_season_id } });
      } else {
        message.error("编辑失败，请重试");
      }
    },

    *createGachaSeasonAward({ payload }: { payload: IgachaSeason }, { call, put }) {
      const response = yield call(createGachaSeasonAward, payload);
      if (response) {
        message.success("新建成功");
        yield call(gachaRefreshSeasonAwardNum, { seasonId: payload.gacha_season_id });
        yield put({ type: 'getGachaSeasonAward', payload: { gacha_season_id: payload.gacha_season_id } });
        yield put({ type: 'setAwardModelType', payload: 0 });
      }
    },

    *updateGachaSeasonAward({ payload }: { payload: IgachaSeason }, { call, put }) {
      const response = yield call(updateGachaSeasonAward, payload);
      if (response) {
        message.success("编辑成功");
        yield call(gachaRefreshSeasonAwardNum, { seasonId: payload.gacha_season_id });
        yield put({ type: 'getGachaSeasonAward', payload: { gacha_season_id: payload.gacha_season_id } });
        yield put({ type: 'setAwardModelType', payload: 0 });
      }
    },

    *getSeasonRuleList({ payload }: { payload }, { call, put }) {
      const response = yield call(getSeasonRuleList, payload);
      if (response) {
        yield put({ type: 'setRuleList', payload: response });
      }
    },

    *getSimpleSeasonRuleGroupList({ payload }: { payload }, { call, put }) {
      const response = yield call(getSimpleSeasonRuleGroupList, payload);
      if (response) {
        yield put({ type: 'setSimpleRuleGroupList', payload: response });
      }
    },

    *createGachaSeasonRule({ payload }: { payload: any }, { call, put }) {
      const response = yield call(createGachaSeasonRule, payload);
      //更新成功
      if (response) {
        message.success("创建成功");
        yield put({ type: 'setCurrentRule', payload: null });
        yield put({ type: 'getSeasonRuleList', payload: { gacha_season_id: payload.gacha_season_id } });
      } else {
        message.error("创建失败，如已经存在此条目，请编辑");
      }
    },

    *updateGachaSeasonRule({ payload }: { payload: any }, { call, put }) {
      const response = yield call(updateGachaSeasonRule, payload);
      //更新成功
      if (response) {
        message.success("编辑成功");
        yield put({ type: 'setCurrentRule', payload: null });
        yield put({ type: 'getSeasonRuleList', payload: { gacha_season_id: payload.gacha_season_id } });
      } else {
        message.error("编辑失败，请重试");
      }
    },
  },

  reducers: {

    setSeasonList(state: Igachats, { payload }): Igachats {
      if (!payload) {
        console.info("payload is null", payload);
        return state;
      }

      const { array } = payload;
      const now = new Date();
      let rowCount = 0;
      let nowId = 0;
      const simpleArray = [];
      for (let i = array.length - 1; i >= 0; i--) {
        const item: IgachaSeason = array[i];
        item.starttime = moment(item.starttime, 'YYYY-MM-DD HH:mm:ss').utcOffset(960).format('YYYY-MM-DD HH:mm:ss');
        item.endtime = moment(item.endtime, 'YYYY-MM-DD HH:mm:ss').utcOffset(960).format('YYYY-MM-DD HH:mm:ss');
        if (compareDate(now, item.starttime) && !compareDate(now, item.endtime)) {
          item.current = true;
        } else {
          item.current = false;
        }

        if (nowId == 0) {
          rowCount++;
          nowId = item.id;
          simpleArray[simpleArray.length] = item;
          if (i == 0) {
            item.rowSpan = rowCount;
          }
        } else {
          if (nowId != item.id) {
            const itemLast: IgachaSeason = array[i + 1];
            itemLast.rowSpan = rowCount;
            rowCount = 1;
            nowId = item.id;
            simpleArray[simpleArray.length] = item;
            if (i == 0) {
              item.rowSpan = rowCount;
            }
          } else {
            if (i == 0) {
              item.rowSpan = rowCount + 1;
            } else {
              rowCount++;
              item.rowSpan = 0;
            }
          }
        }
      }

      const simpleArrayOrder = [];
      for (let i = simpleArray.length - 1; i >= 0; i--) {
        const item: IgachaSeason = simpleArray[i];
        simpleArrayOrder[simpleArrayOrder.length] = item;
      }

      return { ...state, seasonList: array, seasonSimpleList: simpleArrayOrder }
    },

    //更新模态页
    setModelType(state: Igachats, { payload }: { payload: number }): Igachats {
      return { ...state, modelType: payload }
    },

    //更新模态页
    setCoinModelType(state: Igachats, { payload }: { payload: number }): Igachats {
      return { ...state, coinModelType: payload }
    },

    //更新选中的id
    setSelectId(state: Igachats, { payload }: { payload: number }): Igachats {
      return { ...state, selectId: payload }
    },

    setCurrentGroupId(state: Igachats, { payload }: { payload: number }): Igachats {
      return { ...state, currentGroupId: payload }
    },

    setCoinList(state: Igachats, { payload }): Igachats {
      if (!payload) {
        console.info("payload is null", payload);
        return state;
      }
      const { array } = payload;
      const free = {
        id: -1,
        name: "免费",
      }
      array.unshift(free);
      return { ...state, coinList: array }
    },

    setPoolList(state: Igachats, { payload }): Igachats {
      if (!payload) {
        console.info("payload is null", payload);
        return state;
      }

      return { ...state, poolList: payload }
    },

    setPoolModelType(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, poolModelType: payload }
    },

    setItemCateList(state: Igachats, { payload }): Igachats {
      return { ...state, itemCateList: payload }
    },

    setItemDicList(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, itemDicList: payload }
    },

    setAwardList(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, awardList: payload }
    },

    setAwardModelType(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, awardModelType: payload }
    },

    setWeightList(state: Igachats, { payload }: { payload }): Igachats {

      if (state.currentWeightSeasonId != 0) {
        let total = 0;
        let weightList = [];
        for (const item of payload) {
          total += item.weight;
        }
        for (const item of payload) {
          item.weightPer = (item.weight / total * 100).toFixed(2) + "%";
          weightList.push(item);
        }
        return { ...state, weightList: weightList }
      } else {
        return { ...state, weightList: payload }
      }

    },

    setSimpleSeasonList(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, simpleSeasonList: payload }
    },

    setSimpleSeasonGroupList(state: Igachats, { payload }: { payload }): Igachats {
      console.log("payload",payload)
      return { ...state, simpleSeasonGroupList: payload }
    },

    setGroupList(state: Igachats, { payload }: { payload }): Igachats {
      if (state.currentWeightSeasonId != 0) {
        let total = 0;
        let groupList = [];
        for (const item of payload) {
          total += item.weight;
        }
        for (const item of payload) {
          item.weightPer = (item.weight / total * 100) + "%";
          groupList.push(item);
        }
        return {...state, groupList: groupList}
      } else {
        return {...state, groupList: payload}
      }
      return { ...state, groupList: payload }
    },

    setGachaAwardPoolList(state: Igachats, {payload}: { payload }): Igachats {
      return {...state, gachaAwardPoolList: payload}
    },

    setSimpleSeasonCoinList(state: Igachats, {payload}: { payload }): Igachats {
      return {...state, simpleSeasonCoinList: payload}
    },

    setCurrentWeightSeasonId(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, currentWeightSeasonId: payload }
    },

    setCurrentGroupSeasonId(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, currentGroupSeasonId: payload[0].id }
    },

    setCurrentWeight(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, currentWeight: payload }
    },

    setRuleList(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, ruleList: payload }
    },

    setCurrentRule(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, currentRule: payload }
    },

    setSimpleRuleGroupList(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, simpleRuleGroupList: payload }
    },

    setSelectedRowKeys(state: Igachats, { payload }: { payload }): Igachats {
      return { ...state, selectedRowKeys: payload }
    },

    setOrderList(state: Igachats, { payload }: { payload }): Igachats {

      const array = payload.data;
      let rowCount = 0;
      let nowId = 0;
      const simpleArray = [];
      for (let i = array.length - 1; i >= 0; i--) {
        const item: IgachaOrder = array[i];
        if (nowId == 0) {
          rowCount++;
          nowId = item.id;
          simpleArray[simpleArray.length] = item;
          if (i == 0) {
            item.rowSpan = rowCount;
          }
        } else {
          if (nowId != item.id) {
            const itemLast: IgachaOrder = array[i + 1];
            itemLast.rowSpan = rowCount;
            rowCount = 1;
            nowId = item.id;
            simpleArray[simpleArray.length] = item;
            if (i == 0) {
              item.rowSpan = rowCount;
            }
          } else {
            if (i == 0) {
              item.rowSpan = rowCount + 1;
            } else {
              rowCount++;
              item.rowSpan = 0;
            }
          }
        }
      }

      const simpleArrayOrder = [];
      for (let i = simpleArray.length - 1; i >= 0; i--) {
        const item: IgachaOrder = simpleArray[i];
        simpleArrayOrder[simpleArrayOrder.length] = item;
      }

      return { ...state, orderList: payload }
    },

  },
};

```