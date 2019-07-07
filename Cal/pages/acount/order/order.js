// pages/my/order/order.js
const app = getApp()
Page({

  data: {
    currtab:0,
    swipertab: [{ name: '待发货', index: 0 }, { name: '待收货', index: 1 }, { name: '已完成', index: 2 }, { name: '退货', index: 3 }],
    waitDeliveryOrder:[],
    waitArrivalOrder:[],
    okOrder:[],
    retrunOrder:[],
    statusCode:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.currtab)
    this.setData({
      currtab: +options.currtab,
    })

    this.orderShow()
    // setTimeout(() => {//延时调试参数
    //   console.log(typeof options.currtab)
    //   this.setData({
    //     currtab: +options.currtab,
    //   })
    //   this.orderShow()
    // }, 1000)
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    let that = this
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          deviceW: res.windowWidth,
          deviceH: res.windowHeight
        })
      }
    })
  },

  /**
  * @Explain：选项卡点击切换
  */
  tabSwitch: function (e) {
    var that = this
    if (this.data.currtab === e.target.dataset.current) {
      return false
    } else {
      that.setData({
        currtab: e.target.dataset.current
      })
    }
  },

  tabChange: function (e) {

    this.setData({ currtab: e.detail.current })
    this.orderShow()
  },

  orderShow: function () {
    let that = this
    console.log("ordershow"+this.data.currtab)
    switch (this.data.currtab) {
      case 0:
        that.waitDeliveryShow()
        break
      case 1:
        that.waitArrivalShow()
        break
      case 2:
        that.okShow()
        break
      case 3:
        that.retrunShow()
        break
    }
  },
  waitDeliveryShow: function () {
    let statusCode=0;
    var self = this
    var cookie = wx.getStorageSync('cookie')
    wx.request({
      url: app.globalData.host + "/Order/getOrder",
      method: 'POST',
      header: {
        'cookie': cookie
      },
      data: {
        statusCode: statusCode
      },
      success: function (res) {
      console.log(res.data)
      res = res.data
      if (res.status == "1") {
      self.setData({
        waitDeliveryOrder: res.list
      })
      }else{
      }
     }
    })

  },

  waitArrivalShow: function () {
    let statusCode = 1;
    var self = this
    var cookie = wx.getStorageSync('cookie')
    wx.request({
      url: app.globalData.host + "/Order/getOrder",
      method: 'POST',
      header: {
        'cookie': cookie
      },
      data: {
        statusCode: statusCode
      },
      success: function (res) {
        console.log(res.data)
        res = res.data
        if (res.status == "1") {
          self.setData({
            waitArrivalOrder: res.list
          })
        } else {
        }
      }
    })
  },

  okShow: function () {
    let statusCode = 2;
    var self = this
    var cookie = wx.getStorageSync('cookie')
    wx.request({
      url: app.globalData.host + "/Order/getOrder",
      method: 'POST',
      header: {
        'cookie': cookie
      },
      data: {
        statusCode: statusCode
      },
      success: function (res) {
        console.log(res.data)
        res = res.data
        if (res.status == "1") {
          self.setData({
            okOrder: res.list
          })
        } else {
        }
      }
    })
  },

  retrunShow: function () {
    let statusCode = 3;
    var self = this
    var cookie = wx.getStorageSync('cookie')
    wx.request({
      url: app.globalData.host + "/Order/getOrder",
      method: 'POST',
      header: {
        'cookie': cookie
      },
      data: {
        statusCode: statusCode
      },
      success: function (res) {
        console.log(res.data)
        res = res.data
        if (res.status == "1") {
          self.setData({
            retrunOrder: res.list
          })
        } else {
        }
      }
    })
  },

  revoke:function(e){//撤销订单
    let idx = e.currentTarget.dataset.idx
    var id = e.currentTarget.dataset.id
    let list1 = this.data.waitDeliveryOrder
    let list2 = this.data.waitArrivalOrder
    let filter=list1.filter((ele,index)=>{
      return index!=idx
    })
    let filter2 = list2.filter((ele, index) => {
      return index != idx
    })
    var self=this
    wx.showModal({
      title:"运动商品",
      content: '确认是否撤销订单，商品退货不退券！',
      success: function (res) {
        if (res.confirm){
        self.setData({
          waitDeliveryOrder: filter,
          waitArrivalOrder:filter2
        })
        wx.request({
          url: app.globalData.host + "/Order/revoke",
          method: "POST",
          data: {
            id: id
          },
          success: function (res) {
            console.log(res.data)
          },
        })
       } 
      }
    })
  },

  confirm:function(e){//确认收货
    let idx = e.currentTarget.dataset.idx
    var id = e.currentTarget.dataset.id
    let list = this.data.waitArrivalOrder
    let filter = list.filter((ele, index) => {
      return index != idx
    })
    var self=this
    wx.showModal({
      title: "商品："+list[idx].productName,
      content: '是否确认收到商品？',
      success: function (res) {
        if (res.confirm) {
          self.setData({
            waitArrivalOrder: filter
          })
          wx.request({
            url: app.globalData.host + "/Order/confirm",
            method: "POST",
            data: {
              id: id
            },
            success: function (res) {
              console.log(res.data)
            },
          })
        }
      }
    })
  },

  finish:function(e){//收到货后完成商品的订单
    let idx = e.currentTarget.dataset.idx
    var id = e.currentTarget.dataset.id
    let list = this.data.okOrder
    let filter = list.filter((ele, index) => {
      return index != idx
    })
    var self = this
    self.setData({
      okOrder: filter
    })
    wx.request({
      url: app.globalData.host + "/Order/finish",
      method: "POST",
      data: {
        id: id
      },
      success: function (res) {
        console.log(res.data)
      },
    })
  }


})