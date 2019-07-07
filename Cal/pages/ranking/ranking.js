const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    nickname1:'',
    step1:'',
    avatarUrl1:'',
    nickname2: '',
    step2: '',
    avatarUrl2: '',
    nickname3: '',
    step3: '',
    avatarUrl3: '',
    ranking: [
      { },
    ]

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var slef=this;
    wx.request({
      url: app.globalData.host + '/Rank/getRank',
      method: "GET",
      success: function (res) {
        res = res.data
        if (res.status == "1") {
          console.log(res)
          slef.setData({
            nickname1: res.First[0].nickname,
            step1: res.First[0].stepnum,
            avatarUrl1: res.First[0].avatarUrl,
            nickname2: res.Second[0].nickname,
            step2: res.Second[0].stepnum,
            avatarUrl2: res.Second[0].avatarUrl,
            nickname3: res.Third[0].nickname,
            step3: res.Third[0].stepnum,
            avatarUrl3: res.Third[0].avatarUrl,
            ranking:res.Otherlist
          })
        }
      }
    })

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var slef = this;
    wx.request({
      url: app.globalData.host + '/Rank/getRank',
      method: "GET",
      success: function (res) {
        res = res.data
        if (res.status == "1") {
          console.log(res)
          slef.setData({
            nickname1: res.First[0].nickname,
            step1: res.First[0].stepnum,
            avatarUrl1: res.First[0].avatarUrl,
            nickname2: res.Second[0].nickname,
            step2: res.Second[0].stepnum,
            avatarUrl2: res.Second[0].avatarUrl,
            nickname3: res.Third[0].nickname,
            step3: res.Third[0].stepnum,
            avatarUrl3: res.Third[0].avatarUrl,
            ranking: res.Otherlist
          })
        }
      }
    })

  },


})