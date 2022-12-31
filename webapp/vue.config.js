const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    devtool: 'source-map',
    externals: {
      'BMap': 'BMap'
    }
  },
  devServer: {
    port: 8080, // 端口
    proxy: {
      '/user': { //请求路径关键字
        target: 'http://localhost:8081/user', //对应自己的接口
        changeOrigin: true,//是否允许跨域,在本地会创建一个虚拟服务端，然后发送请求的数据，
        ws: true,
        pathRewrite: {
          '^/user': ''
        }
      },
      '/menu': {
        target: 'http://localhost:8081/menu',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/menu': ''
        }
      },
      '/product': {
        target: 'http://localhost:8081/product',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/product': ''
        }
      },
      '/store': {
        target: 'http://localhost:8081/store',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/store': ''
        }
      },
      '/order': {
        target: 'http://localhost:8081/order',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/order': ''
        }
      },
      '/address': {
        target: 'http://localhost:8081/address',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/address': ''
        }
      },
      '/image': {
        target: 'http://localhost:8081/image',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/image': ''
        },
      },
      '/pay': {
        target: 'http://localhost:8081/pay',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/pay': ''
        }
      }
    }
  }

})
