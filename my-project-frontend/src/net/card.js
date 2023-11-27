import api from "@/net/axios";
import {useGoodCarStore} from "@/store";
const _URL = '/card'
export const saveCard = (url,data) => api.post(_URL+url,data)
export const getCard = (url) => api.get(_URL+url)
export const getCardList = (url,ids) => api.post(_URL+url,ids)

export function intCard(){
    const store = useGoodCarStore();
    const ids = []
    const num = []
    getCard("/getCard")
        .then(r =>{
            for (let i = 0; i < r.data.data.length; i++) {
                ids.push(r.data.data[i].book_id)
                num.push(r.data.data[i].num)
            }
            getCardList("/getCardList",ids)
                .then(res => {
                    for (let i = 0; i < num.length; i++){
                        for (let j = 0; j < num[i]; j++){
                            store.increment(res.data.data[i]);
                        }
                    }
                })
        })
}