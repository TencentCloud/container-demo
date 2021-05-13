// https://github.com/chimurai/http-proxy-middleware
// https://create-react-app.dev/docs/proxying-api-requests-in-development/

const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(
        '/api',
        createProxyMiddleware({
            // target: 'http://175.27.183.165:8080',
            target: 'http://127.0.0.1:8080',
            changeOrigin: true,
        })
    );
};
