import { defineStore } from "pinia";
import {getCard, getCardList, saveCard} from "@/net/card";
//引入pinia
export let useGoodCarStore = defineStore("goodCar", {
    id: "demo",//缓存的key
    state: () => ({
        switch: false,
        count: 0,
        listArr: [],
        selectListArr: []
    }),
    getters: {
        double: (state) => state.count * 2,
        // 购物车中的总数量
        totalNum: (state) => {
            let num = 0;
            if (state.selectListArr.length > 0) {
                num = state.selectListArr.reduce((acc, cur) => {
                    return acc + cur.num;
                }, 0);
            }
            return num;
        },
        // 购物车中的总价格
        totalPrice: (state) => {
            let price = 0;
            if (state.selectListArr.length > 0) {
                price = state.selectListArr.reduce((acc, cur) => {
                    return acc + cur.num * cur.price;
                }, 0);
            }
            return price;
        }
    },
    actions: {
        increment(state) {
            if (this.listArr.length == 0) {
                this.listArr.push({
                    ...state,
                    num: 1,
                });
            } else {
                let index = this.listArr.findIndex((item) => item.bookId == state.bookId);
                if (index != -1) {
                    this.listArr[index].num = this.listArr[index].num + 1;
                } else {
                    this.listArr.push({
                        ...state,
                        num: 1,
                    });
                }
            }
        },
        delGood(id){
            let index = this.listArr.findIndex(item => item.bookId == id)
            this.listArr.splice(index,1)
        },
        // 选中购物车中的商品
        selectGoodCar(state) {
            this.selectListArr = state;
        },
        //保存购物车数据到数据库
        saveCard(){
            let list = []
            this.listArr.forEach((item,index) => {
                list.push({
                        book_id: item.bookId,
                        num: item.num
                })
            })
            saveCard("/save",list)
                .then(res => {})
        },
        updateSwitch(){
            if (this.switch){
                this.switch = false
            }else{
                this.switch = true
            }
        }
    },
    // 开启数据持久化
    persist: false,
});

export let useBookStore = defineStore("BookStore",{
    id:'bookstore',
    state:() => ({
        list:[],
        baseURL: "http://192.168.0.124:8080",
        countPage: {
            // 显示第几页 （可修改）
            currentPage: 1,
            // 默认每页显示的条数（可修改）
            pageSize: 12,
            // 总数据 (数据库获取)
            tableData: null,
            // 总条数，根据接口获取数据长度(注意：这里不能为空)  (数据库获取)
            totalCount: null,
            // 总页数 (数据库返回)
            pageCount: null
        },
        selectValue: ""
    }),
    getters:{

    },
    actions:{

    },
    // 开启数据持久化
    persist: false,
})

