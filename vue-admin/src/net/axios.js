import axios from 'axios'
import {takeAccessToken} from "@/net/index";
const instance = axios.create({
    baseURL: 'http://192.168.0.124:8080/api',
    timeout: 6000,
    headers: {
        'Authorization': `Bearer ${takeAccessToken()}`
    }
});

const get = (url,params) => {
    return new Promise((resolve, reject) => {
        instance.get(url,{params})
            .then((res) => {
                resolve(res);
            }).catch((err) => {
                resolve("请求失败")
        })
    })
}

const post = (url,data) => {
    return new Promise((resolve, reject) => {
        instance.post(url, data)
            .then((res) => {
                resolve(res);
            }).catch((err) => {
            resolve("请求失败")
        })
    })
}

const postMultipart = (url,data) => {
    return new Promise((resolve, reject) => {
        instance.post(url,data,{
            headers:{"Content-Type":"multipart/form-data"}
        })
            .then((res) => {
                resolve(res);
            }).catch((err) => {
            resolve("请求失败")
        })
    })
}

export default { get, post, postMultipart }