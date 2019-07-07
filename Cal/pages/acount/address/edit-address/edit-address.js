const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: '', 
    name: '',
    phoneNumber: '',
    region: '',
    detail: ''
  },
  RegionChange: function (e) {
    this.setData({
      region: e.detail.value
    })
  },
  
  update: function (e) {
    var name = this.data.name;
    var phoneNumber = this.data.phoneNumber;
    var detail = this.data.detail;
    var province = this.data.region[0];
    var city = this.data.region[1];
    var county = this.data.region[2];
    var region=province+","+city+","+county;
    var address ={
      name:name,
      phoneNumber:phoneNumber,
      region:region,
      detail:detail
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
      url: app.globalData.host + "/Address/updateAddress",
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
    //修改地址信息保存到数据库wx.request（{}）
    app.globalData.editAddressData=this.data;
    wx.navigateBack({})
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
  onLoad: function (options) {
    /*
    通过wx.request从后台取到用户地址的id，设置选中的id
    */

    //取部分的值可用
    // var { id, name } = JSON.parse(options.address);
    console.log(options);
    this.setData(JSON.parse(options.address));

    // console.log(id);
    // console.log(name);
  },

})