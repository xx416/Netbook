import api from "@/net/axios"

const _URL = '/addr'

export const getListByUid = () => api.get(_URL+"/getListByUId")
export const save = (data) => api.post(_URL+"/save",data)