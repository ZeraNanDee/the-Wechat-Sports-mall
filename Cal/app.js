//app.js
App({
  onLaunch: function() {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    var cookie = wx.getStorageSync('cookie')
    var self = this
    if (cookie) {
      console.log('本地缓存未过期');
       self.globalData.isLogin = true;
    }else{
      self.globalData.isLogin = false;
    }
  },
  
  
 
  globalData: {//全局数据
    host: "http://localhost:8080",
    userInfo: null,
    isLogin: false,
    user_id: null,
    addAddressData: null,
    defaultAddressId: wx.getStorageSync("defaultAddressId"),
    editAddressData: null,
  },
  
  wxLogin: function() { //全局的登录
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
          this.globalData.isLogin = true
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框 
          wx.getUserInfo({
            success: res => {
              console.log(res);
              var encryptedData = res.encryptedData
              var iv = res.iv
              wx.request({
                url: "http://192.168.0.118:8080/Cal/login", //dologin是访问后端的方法 
                method: "post",
                header: {
                  'content-type': 'application/json' // post请求
                },
                data: {
                  code: code,
                  encryptedData: encryptedData,
                  iv: iv
                },
                success: function(ret) {
                  console.log(ret.data)
                  ret = ret.data
                  if (ret.status=="1") //代表授权成功
                  {
                    var cookie = 'JSEESION=' + ret.id + '; path=/'
                    wx.setStorageSync('cookie', cookie)
                    self.globalData.isLogin = true
                    self.globalData.user_id = ret.user_id
                  } else {
                    self.globalData.isLogin = false
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