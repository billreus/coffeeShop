var app = getApp();
var api = require('../../../config/api.js');
var check = require('../../../utils/check.js');
var util = require('../../../utils/util.js');
var user = require('../../../utils/user.js');

Page({
  data: {
    mobile: '',
    nickname: '',
    password: '',
    confirmPassword: ''
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    // 页面渲染完成

  },
  onReady: function () {

  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  },

  setPhone: function () {
    var that = this;

    if (this.data.mobile.length == 0) {
      wx.showModal({
        title: '错误信息',
        content: '手机号不能为空',
        showCancel: false
      });
      return false;
    }

    if (!check.isValidPhone(this.data.mobile)) {
      wx.showModal({
        title: '错误信息',
        content: '手机号输入不正确',
        showCancel: false
      });
      return false;
    }

    util.request(api.AuthReset,{
      mobile: that.data.mobile
    },"POST")
    .then(function(res){
      if (res.errno === 0) {
        wx.navigateBack();
      } else {
        wx.showModal({
          title: '手机号重置失败',
          content: res.data.errmsg,
          showCancel: false
        });
      }   
    });
  },
  startReset: function () {
    var that = this;

    if (this.data.password.length < 3) {
      wx.showModal({
        title: '错误信息',
        content: '密码不得少于3位',
        showCancel: false
      });
      return false;
    }

    if (this.data.password != this.data.confirmPassword) {
      wx.showModal({
        title: '错误信息',
        content: '确认密码不一致',
        showCancel: false
      });
      return false;
    }

    util.request(api.AuthReset, {
      password: that.data.password
    }, "POST")
      .then(function (res) {
        if (res.errno === 0) {
          wx.navigateBack();
        } else {
          wx.showModal({
            title: '密码重置失败',
            content: res.data.errmsg,
            showCancel: false
          });
        }
      });
  },
  setNickname: function () {
    var that = this;

    if (this.data.nickname.length == 0) {
      wx.showModal({
        title: '错误信息',
        content: '昵称不能为空',
        showCancel: false
      });
      return false;
    }

    util.request(api.AuthReset, {
      nickname: that.data.nickname
    }, "POST")
      .then(function (res) {
        if (res.errno === 0) {
          wx.navigateBack();
        } else {
          wx.showModal({
            title: '昵称重置失败',
            content: res.data.errmsg,
            showCancel: false
          });
        }
      });
  },
  bindPasswordInput: function (e) {

    this.setData({
      password: e.detail.value
    });
  },
  bindConfirmPasswordInput: function (e) {

    this.setData({
      confirmPassword: e.detail.value
    });
  },
  bindMobileInput: function (e) {

    this.setData({
      mobile: e.detail.value
    });
  },
  bindNicknameInput: function (e) {

    this.setData({
      nickname: e.detail.value
    });
  },
  clearInput: function (e) {
    switch (e.currentTarget.id) {
      case 'clear-password':
        this.setData({
          password: ''
        });
        break;
      case 'clear-confirm-password':
        this.setData({
          confirmPassword: ''
        });
        break;
      case 'clear-mobile':
        this.setData({
          mobile: ''
        });
        break;
      case 'clear-code':
        this.setData({
          code: ''
        });
        break;
    }
  }
})