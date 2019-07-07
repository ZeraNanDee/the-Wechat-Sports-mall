// pages/acount/acount.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    cardCur: 0, // cardSwiper
    products: [],
    balance: 0.00,
    cal: 0,
    ticket: 0.00,
    isLogin: false,
    invite_id: null
  },
  cardSwiper(e) {
    this.setData({
      cardCur: e.detail.current
    })
  },
  showModal:function(e) {//图片预览
    var self=this
    var products = self.data.products
    var id=e.currentTarget.dataset.id
    var picture=products[id].picture
    self.setData({
      modalName: e.currentTarget.dataset.target,
      picture:picture
    })
  },
  hideModal(e) {
    this.setData({
      modalName: null
    })
  },
  exchange: function() {
    var self = this
    var cal = self.data.cal
    var cookie = wx.getStorageSync('cookie')
    if (!cookie) {
      wx.showModal({
        title: '卡路里商城',
        content: '请点击下方立即授权，才可以兑换哦！',
        showCancel: false,
        success: function(res) {}
      })
      return;
    }
    if (cal > 0) {
      var ticket = self.data.ticket
      wx.showModal({
        title: '卡路里商城',
        content: '是否立即消耗' + cal + 'cal兑换燃烧券,您可获得' + ticket + '券',
        success: function(res) {
          if (res.confirm) {

            wx.getWeRunData({
              success(res) {
                var encryptedData = res.encryptedData
                var iv = res.iv
                wx.request({
                  url: app.globalData.host + "/Exchange/steps",
                  method: "post",
                  header: {
                    'content-type': 'application/json',
                    'cookie': cookie
                  },
                  success: function(res) {
                    res = res.data
                    console.log(res)
                    if (res.status == "1") {
                      wx.showToast({
                        title: '兑换成功',
                        icon: 'success',
                        duration: 1000
                      })
                      self.setData({
                        cal: 0,
                        balance: res.balance
                      })
                    } else {
                      var msg = res.msg
                      wx.showToast({
                        title: msg,
                        icon: 'none',
                        duration: 2000
                      })
                    }
                  }
                })
              }
            })

          } else if (res.cancel) {}
        }
      })
    } else {
      wx.showModal({
        title: '卡路里商城',
        content: '当前消耗为0Cal，无法兑换燃烧券！',
        showCancel: false,
        success: function(res) {}
      })
    }

  },
  exproduct: function(e) {
    var self = this
    var index = e.currentTarget.dataset.index
    var products = self.data.products
    var balance = parseFloat(self.data.balance)
    var price = products[index].price
    if (balance < price) {
      wx.showModal({
        title: '卡路里运动商城',
        content: '您的燃烧券不足',
        showCancel: false,
        success: function(res) {}
      })
      return;
    }
    wx.showModal({
      title: '卡路里运动商城',
      content: '是否花费' + price + '张燃烧券兑换,点击确认默认地址收货',
      success: function(res) {
        if (res.confirm) {
          var cookie = wx.getStorageSync('cookie')
          var addressid = wx.getStorageSync('defaultAddressId')
          
          wx.request({
            url: app.globalData.host + "/Order/postOrder",
            method: 'POST',
            data: {
              products: products[index]
            },
            header: {
              'cookie': cookie,
              'defaultAddressId': addressid
            },
            success: function(res) {
              console.log(res.data)
              var pCount=products[index].pCount
              res = res.data
              if (res.status == "1") {
                self.setData({
                  balance: res.balance
                })
                wx.showToast({
                  title: '兑换成功',
                  icon: 'success',
                  duration: 2000
                })
              } else {
                var msg = res.msg
                wx.showToast({
                  title: msg,
                  icon: 'none',
                  duration: 2000
                })
              }
              self.setData({
                balance: res.balance
              })
            }
          })
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this //加载轮播图
    wx.request({
      url: app.globalData.host + '/image/banner',
      method: 'GET',
      success: function(res) {
        that.setData({
          'productImage': res.data
        })
      }
    })
    //一进来就先触发一次下拉刷新
    wx.startPullDownRefresh({
      success: function() {
        // doing some thing
      }
    })

    //解析转发的参数

    var invite_id = options.invite_id
    if (invite_id) {
      console.log('赋值了')
      this.setData({
        invite_id: invite_id
      })
    }


  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    var self = this
    var cookie = wx.getStorageSync('cookie')
    if (cookie) {
      wx.request({
        url: app.globalData.host + "/Exchange/refresh",
        method: "get",
        header: {
          'content-type': 'application/json',
          'cookie': cookie
        },
        success: function(res) {
          res = res.data
          if (res.status == "1") {
            self.setData({
              balance: res.balance
            })
          }

        }
      })
      self.setData({
        isLogin: true
      })
    }

    //刷新商品列表
    var self = this

    wx.request({ //请求测试
      url: app.globalData.host + "/Products/getProducts",
      method: "GET",
      success: function(res) {
        res = res.data
        console.log(res)
        self.setData({
          products: res
        })
        setTimeout(() => {
          wx.hideLoading()
          wx.stopPullDownRefresh();
        }, 1000);
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

    wx.getWeRunData({
      success(res) {
        var encryptedData = res.encryptedData
        var iv = res.iv
        wx.request({
          url: app.globalData.host + "/Cal/step",
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
          success: function(res) {
            res = res.data
            console.log(res)
            if (res.status == "1") {
              self.setData({
                cal: res.cal,
                ticket: res.ticket
              })
            }
            setTimeout(() => {
              wx.hideLoading()
              wx.stopPullDownRefresh();
            }, 1000)
          }
        })
      }
    })


  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    var user_id = app.globalData.user_id;
    //console.log('邀请人id：'+user_id)
    return {
      title: '卡路里运动商城',
      path: 'pages/index/index?invite_id=' + user_id //带上user_id作为邀请id凭证
    }

  },
  getUserInfo: function() {
    var code
    var self = this
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        code = res.code
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框 
          wx.getUserInfo({
            success: res => {
              console.log(res);
              var encryptedData = res.encryptedData
              var iv = res.iv
              var invite_id = self.data.invite_id
              console.log('请求的参数：' + invite_id)
              wx.request({
                url: app.globalData.host + "/Cal/login", //dologin是访问后端的方法 
                method: "post",
                header: {
                  'content-type': 'application/json' // post请求
                },
                data: {
                  code: code,
                  encryptedData: encryptedData,
                  iv: iv,
                  invite_id: invite_id
                },
                success: function(ret) {
                  console.log(ret.data)
                  ret = ret.data
                  if (ret.status == "1") //代表授权成
                  {
                    var cookie = 'JSEESION=' + ret.id + '; path=/'
                    wx.setStorageSync('cookie', cookie)
                    app.globalData.user_id = ret.user_id
                    //wx.setStorageSync('user_id', ret.user_id)
                    app.globalData.isLogin = true
                    self.setData({
                      isLogin: true,
                      balance: ret.balance
                    })
                  } else {
                    app.globalData.isLogin = false
                    self.setData({
                      isLogin: false
                    })
                  }
                }
              })
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })

  }
})