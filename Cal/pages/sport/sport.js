var wxCharts = require('../../utils/wxcharts.js')
var app = getApp();
var lineChart = null;
Page({
  data: {
    x: 60,
    y: 0,
    list: [
      {
        name: 'scale-up',
        color: 'blue'
      }
    ],
    signed: false,
    total_sign:'',
    percent:'',
    longText:'',
    recordtext:'',
    rank:''
  },
  toggle(e) {
    console.log(e);
    var animation = e.currentTarget.dataset.class;
    var that = this;
    that.setData({
      animation: animation,
      x: 60,
      y: 100,
    })
    //保存签到记录（插入数据到mysqsl）
    var cookie = wx.getStorageSync('cookie');
    wx.request({
      url: app.globalData.host + "/Sign/insertSign",
      method: "GET",
      header: {
        'cookie': cookie
      },
      success: function (res) {
        res = res.data
        if (res.status == "1") {
          console.log(res.balance)
          that.setData({
            total_sign: res.signtotal
          })
        }
      }
    })
    setTimeout(function () {
      that.setData({
        animation: '',
        opacity: 0,
        signed: true
      })
      wx.showToast({
        title: '+2.55燃烧券',
        icon: 'success',
        duration: 2000
      })
    }, 1300)
  },
  touchHandler: function (e) {
    console.log(lineChart.getCurrentDataIndex(e));
    lineChart.showToolTip(e, {
      // background: '#7cb5ec',
      format: function (item, category) {
        return category + ' ' + item.name + ':' + item.data
      }
    });
  },
  createSimulationData: function () {
    //此处应放request

  },

  onLoad: function (e) {
  /**页面进来就先访问服务器，加载后若服务器内判断
   * （select签到表查询日期）后台操作 如果存在此今日的签到记录表，
   * return 出sign为true，然后计算同一个用户的sign_total
   * setdata（{ sign :res.xxx.xxx sign_total:res.xxx.xxx}）
   **/
  var self = this
  var cookie = wx.getStorageSync('cookie')
  wx.request({
    url: app.globalData.host + "/Sign/getTodaySign",
      method: "GET",
      header: {
        'cookie': cookie
      },
      success: function (res) {
        console.log(res.data)
        res = res.data
        if (res.status == "1") {
          self.setData({
            total_sign: res.signtotal,
            signed: res.sign,
            rank:res.rank
          })
      }
    }
  })
   var code
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        code = res.code
      }
    })
    var categories = [];
    var data = [];
    var cookie = wx.getStorageSync('cookie')
    if (!cookie)
      return;
    wx.getWeRunData({
      success(res) {
        var encryptedData = res.encryptedData
        var iv = res.iv
        wx.request({
          url: app.globalData.host + "/record/weekstep",
          method: "post",
          header: {
            'content-type': 'application/json',
            'cookie': cookie
          },
          data: {
            code: code,
            encryptedData: encryptedData,
            iv: iv
          },
          success: function (res) {
            res = res.data
            if (res.status=="1") {
              self.setData({
                percent: res.percent,
                longText: res.longText,
                recordtext: res.recordtext
              })
              res = res.data
              for (var key in res) {
                categories.push(res[key].date);
                data.push(res[key].cal);
              }
            } else {
            }
            var simulationData = {
              categories: categories,
              data: data
            }
            console.log(simulationData)
            self.main(simulationData)
          }
        })
      }
    })


  },
  main: function (simulationData) {
    console.log(simulationData)
    var windowWidth = 320;
    try {
      var res = wx.getSystemInfoSync();
      windowWidth = res.windowWidth;
    } catch (e) {
      console.error('getSystemInfoSync failed!');
    }
    lineChart = new wxCharts({
      canvasId: 'lineCanvas',
      type: 'line',
      categories: simulationData.categories,
      animation: true,
      series: [{
        name: '近一周消耗的卡路里',
        data: simulationData.data,
        format: function (val, name) {
          return val.toFixed(2) + 'cal';
        }
      }
      ],
      xAxis: {
        fontColor: '#ffffff',
        disableGrid: true
      },
      yAxis: {
        fontColor: '#ffffff',
        title: '卡路里消耗量（Cal）',
        titleFontColor: '#ffffff',
        format: function (val) {
          return val;
        },
        min: 0
      },
      width: windowWidth,
      height: 200,
      dataLabel: false,
      dataPointShape: true,
      extra: {
        lineStyle: 'curve'
      }
    });
  }
});