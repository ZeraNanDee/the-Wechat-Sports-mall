// pages/acount/acount.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    opacity: 0.8,
    avatarUrl: 'https://wx.qlogo.cn/mmopen/vi_32/r23wEhbaiaR0oWoW8nJ0VHCeicZb87SicpjoGtx8CVBssRZurzmqgZltt5icRZgtF1s96D00eVM4LN61HyCmxE151A/132',
    nickName: '尚未登陆',
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.orderCount();
    var self = this
    var cookie = wx.getStorageSync('cookie')
    wx.request({
      url: app.globalData.host+"/Account/getUser",
      method: "GET",
      header: {
        'cookie': cookie
      },
      success: function(res) {
        console.log(res.data)
        res = res.data
        if (res.status == "1") {
          self.setData({
            avatarUrl: res.avatarUrl,
            nickName: res.nickName
          })
        }
      }
    })

  },
  /**
 * 生命周期函数--监听页面显示
 */
  onShow: function () {
    this.orderCount();
  },
  
  formReset() {//更新用户数据
    var self = this
    var cookie = wx.getStorageSync('cookie') 
    console.log('form发生了reset事件')
    wx.getUserInfo({
      success(res) {
        var userInfo = res.userInfo
        var nickName = userInfo.nickName
        var avatarUrl = userInfo.avatarUrl
        var gender = userInfo.gender // 性别 0：未知、1：男、2：女
        self.setData({
          nickName: nickName,
          avatarUrl: avatarUrl,
          gender: gender
        })
        wx.request({
          url: app.globalData.host + "/Account/reSetUser",
          method: "POST",
          header: {
            'cookie': cookie
          },
          data:{
            nickName: nickName,
            avatarUrl: avatarUrl,
            gender: gender
          },
          success: function (res) {
            console.log(res.data)
          }
        })
      }
    })
  },
  orderCount(){
    var self = this
    var cookie = wx.getStorageSync('cookie') 
    wx.request({
      url: app.globalData.host + "/Order/getOrderSum",
      method: "GET",
      header: {
        'cookie': cookie
      },
      success: function (res) {
        console.log(res.data)
        res=res.data
        if(res.status=="1"){
          self.setData({
            count0:res.count0,
            count1:res.count1,
            count2:res.count2,
            count3:res.count3
          })
        }
      }
    })
  }

})