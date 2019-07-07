const app = getApp()

Page({

  data: {
    address: {},

    delBtnWidth: 180
  },
  onShow: function(e) {
    if (app.globalData.addAddressData) {
      this.add(app.globalData.addAddressData);
      app.globalData.addAddressData = null;
    }
    if (app.globalData.editAddressData) {
      this.edit(app.globalData.editAddressData);
      app.globalData.addAddressData = null;
    }
  },


  /**
   * 获取收货地址 
   */
  getAddresses() {
    var self = this
    var cookie = wx.getStorageSync('cookie');

    wx.request({
      url: app.globalData.host + "/Address/getAddress",
      method: "GET",
      header: {
        'cookie': cookie
      },
      success: function(res) {

        console.log(res.data)
        for (let i = 0; i < res.data.length; i++) {
          self.add(res.data[i])
        }
        if (res.data.length==0){
          wx.navigateTo({
            url: 'edit-address/edit-address'
          })
        }
      }
    })

  },


  select_addr: function(e) {
      this.setData({
        defaultAddressId: e.currentTarget.dataset.id //设置当前事件获取到的地址ID给全局变量
      })
      app.globalData.defaultAddressId = e.currentTarget.dataset.id;
      var addressId = 'AID=' + e.currentTarget.dataset.id + '; path=/'
    wx.setStorageSync('defaultAddressId', e.currentTarget.dataset.id )//付给小程序cookie，下次登录自动加载
  },

  onLoad: function(options) {
    
    this.getAddresses(); //加载时获取地址信息
    this.setData({
      defaultAddressId: getApp().globalData.defaultAddressId //设置全局变量加载页面时，让默认的ID赋值给全局ID
    })
  },

  navigateToEditAddress(e) {
    let index = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: `edit-address/edit-address?address=${JSON.stringify(this.data.address[index])}`,
    })

  },
  navigateToAddAddress(e) {
    let index1 = e.currentTarget.dataset.id;
    console.log(index1)
    wx.navigateTo({
      url: `add-address/add-address?address=${JSON.stringify(this.data.address[index1])}`,
    })

  },

  add: function(address) {
    //添加收货地址
    this.data.address[address.id] = address;
    this.setData({
      address: this.data.address
    });
  },

  edit: function(address) {
    //编辑收货地址 
    this.data.address[address.id] = address;
    this.setData({
      address: this.data.address
    });
  },

  delItem: function(address) {
    //删除收货地址
    var id = address.currentTarget.dataset.id;
    var index = address.currentTarget.dataset.index;
    console.log(index);
    delete this.data.address[index];
    console.log(this.data.address);
    //后台
    var self = this
    var cookie = wx.getStorageSync('cookie');
    wx.request({
      url: app.globalData.host + "/Address/deleteAddress",
      method: "POST",
      header: {
        'cookie': cookie
      },
      data: {
        id: id
      },
      success: function(res) {
        console.log(res.data)
      }
    })
    app.globalData.addAddressData = null
    this.setData({
      address: this.data.address
    })
  },

  touchS: function(e) {
    if (e.touches.length == 1) {
      this.setData({
        //设置触摸起始点水平方向位置
        startX: e.touches[0].clientX
      });
    }
  },

  touchM: function(e) {
    if (e.touches.length == 1) {
      //手指移动时水平方向位置
      var moveX = e.touches[0].clientX;
      //手指起始点位置与移动期间的差值
      var disX = this.data.startX - moveX;
      var delBtnWidth = this.data.delBtnWidth;
      var txtStyle = "";
      if (disX == 0 || disX < 0) { //如果移动距离小于等于0，文本层位置不变
        txtStyle = "left:0rpx";
      } else if (disX > 0) { //移动距离大于0，文本层left值等于手指移动距离
        txtStyle = "left:-" + disX + "rpx";
        if (disX >= delBtnWidth) {
          //控制手指移动距离最大值为删除按钮的宽度
          txtStyle = "left:-" + delBtnWidth + "rpx";
        }
      }
      //获取手指触摸的是哪一项
      var index = e.currentTarget.dataset.index;
      var list = this.data.address;
      list[index]['txtStyle'] = txtStyle;
      //更新列表的状态
      this.setData({
        address: list
      });
    }
  },
  touchE: function(e) {
    if (e.changedTouches.length == 1) {
      //手指移动结束后水平位置
      var endX = e.changedTouches[0].clientX;
      //触摸开始与结束，手指移动的距离
      var disX = this.data.startX - endX;
      var delBtnWidth = this.data.delBtnWidth;
      //如果距离小于删除按钮的1/2，不显示删除按钮
      var txtStyle = disX > delBtnWidth / 2 ? "left:-" + delBtnWidth + "rpx" : "left:0rpx";
      //获取手指触摸的是哪一项
      var index = e.currentTarget.dataset.index;
      var list = this.data.address;
      var del_index = '';
      disX > delBtnWidth / 2 ? del_index = index : del_index = '';
      list[index].txtStyle = txtStyle;
      //更新列表的状态
      this.setData({
        address: list,
        del_index: del_index
      });
    }
  },
})