const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:'',
    name: '',
    phoneNumber: '',
    region: ['北京市', '北京市', '东城区'],
    detail: ''
  },
  RegionChange: function(e) {
    this.setData({
      region: e.detail.value
    })
  },
  save: function(e) {
    var name = this.data.name;
    var phoneNumber = this.data.phoneNumber;
    var detail = this.data.detail;
    var province = this.data.region[0];
    var city = this.data.region[1];
    var county = this.data.region[2];
    var region = province + "," + city + "," + county;
    var address = {
      name: name,
      phoneNumber: phoneNumber,
      region: region,
      detail: detail
    };

    if (name == "") {
      wx.showModal({
        title: '提示',
        content: '请填写联系人姓名',
        showCancel: false
      })
      return
    }
    if (phoneNumber == "") {
      wx.showModal({
        title: '提示',
        content: '请填写手机号码',
        showCancel: false
      })
      return
    }
    if (detail == "") {
      wx.showModal({
        title: '提示',
        content: '请选择地区',
        showCancel: false
      })
      return
    }
    var self = this
    var cookie = wx.getStorageSync('cookie');
    wx.request({
      url: app.globalData.host + "/Address/insertAddress",
      method: "POST",
      header: {
        'cookie': cookie
      },
      data: address,
      success: function (res) {
        console.log(res.data)
        res = res.data
      }
    })
    app.globalData.addAddressData = this.data; //存储数据到app对象上，作为全局数据
    wx.navigateBack({ })
  },
  onInput(e) {
    // console.log(e);
    this.setData({
      [e.target.id]: e.detail.value//获取到用户输入框的值，设置给data
    });
    console.log(this.data);
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(e) {

    let id = JSON.parse(e.address).id
    id=id+1;
    this.setData({
      id:id
    });
  },

})