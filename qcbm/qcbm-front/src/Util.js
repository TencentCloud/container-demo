import cookie from 'react-cookies';
import intl from 'react-intl-universal';

export default class Util {

    static concatUrl(url, key, val) {
        if (url.indexOf('?') > 0) {
            url = url + "&" + key + "=" + encodeURIComponent(val);
        } else {
            url = url + "?" + key + "=" + encodeURIComponent(val);
        }
        return url;
    }
    static async request(method, service, url, params) {
        //localDev
        let lUrl = '/api/' + service + url;
        params = params || {};

        const ticket = cookie.load('ticket');
        const tenant_id = cookie.load('tenant_id');
        if (ticket) {
            lUrl = Util.concatUrl(lUrl, "ticket", ticket);
        }
        if (tenant_id) {
            lUrl = Util.concatUrl(lUrl, "tenant_id", tenant_id);
            lUrl = Util.concatUrl(lUrl, "tenant_open_id", tenant_id);
        }

        if (method === "GET") {
            let fullUrl = params && Object.keys(params).map(key => key + '=' + params[key]).join('&');
            if (lUrl.indexOf('?') > 0) {
                fullUrl = lUrl + "&" + fullUrl;
            } else {
                fullUrl = lUrl + "?" + fullUrl;
            }
            const response = await fetch(fullUrl);
            return await response.json();

        } else {
            const response_1 = await fetch(lUrl, {
                method: method,
                body: JSON.stringify(params),
                headers: new Headers({
                    'Content-Type': 'application/json'
                })
            });
            return await response_1.json();
        }

    }

    static async uploadEve(service, url, formData) {
        let lUrl = service + url;
        const ticket = cookie.load('ticket');
        const tenant_id = cookie.load('tenant_id');
        if (ticket) {
            lUrl = Util.concatUrl(lUrl, "ticket", ticket);
        }
        if (tenant_id) {
            lUrl = Util.concatUrl(lUrl, "tenant_id", tenant_id);
        }
        const response = await fetch(lUrl, {
            method: 'POST',
            body: formData
        });
        return await response.json();
    }

    static async getEve(url, params) {
        return await Util.request("GET", "", url, params);
    }
    static async postEve(url, params) {
        return await Util.request("POST", "", url, params);
    }
    static async putEve(url, params) {
        return await Util.request("PUT", "", url, params);
    }
    static async deleteEve(url, params) {
        return await Util.request("DELETE", "", url, params);
    }
    static formatCurrency(value) {
        return intl.get("IntlCurrency", { value });
    }
    static parseQuery() {
        var query = window.location.search.substring(1);
        var vars = query.split('&');
        var params = {}
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split('=');
            var key = decodeURIComponent(pair[0]), value = decodeURIComponent(pair[1]);
            if (params.hasOwnProperty(key)) {
                if (Array.isArray(params[key])) {
                    params[key].push(value);
                } else {
                    params[key] = [params[key], value];
                }
            } else {
                params[key] = value;
            }
        }
        return params;
    }

    static setCookie(key, value, expireSecond) {
        const expires = new Date(1000 * expireSecond)
        cookie.save(key, value, { path: '/', expires });
    }
    static removeCookie(key) {
        cookie.remove(key, { path: '/' });
    }
    static getLang() {
        return cookie.load("lang");
    }

    static setLang(xlang) {
        Util.setCookie("lang", xlang, 9587047844);
    }
    static getTicket() {
        return cookie.load("ticket");
    }
    static getTenant() {
        return {
            tenant_title: cookie.load("tenant_title"),
            tenant_id: cookie.load("tenant_id")
        };
    }
    static getTenantId() {
        return cookie.load("tenant_id");
    }
    static getUser() {
        return {
            nick: cookie.load("nick"),
            open_id: cookie.load("open_id"),
            email: cookie.load("email"),
            mobile: cookie.load("mobile"),
            avatar: cookie.load("avatar"),
        }
    }
    /**
     * 把含有 %s 字符串格式化，如 "abc %s %s", 仅支持 %s
     * @param {}} string 
     * @param  {...any} args 
     */
    static formatString(oriString, ...args) {
        let i = 0;
        return oriString.replace(/(%s)/ig, () => {
            return args[i++] || "";
        });
    }
}