import request from '@/utils/request'

export function saveCrops(data) {
  return request({
    url: '/traces/ncp',
    method: 'post',
    data: data,
  })
}

export function listCrops() {
  return request({
    url: '/traces/ncp',
    method: 'get',
  })
}

export function saveProduct(data) {
    return request({
      url: '/traces/process',
      method: 'post',
      data: data,
    })
  }
  
  export function listProduct() {
    return request({
      url: '/traces/process',
      method: 'get',
    })
  }

  export function saveTransport(data) {
    return request({
      url: '/traces/transport',
      method: 'post',
      data: data,
    })
  }
  
  export function listTransport() {
    return request({
      url: '/traces/transport',
      method: 'get',
    })
  }

  export function saveSale(data) {
    return request({
      url: '/traces/sale',
      method: 'post',
      data: data,
    })
  }
  
  export function listSale() {
    return request({
      url: '/traces/sale',
      method: 'get',
    })
  }

  export function trace(id) {
    return request({
      url: '/traces/trace/' + id,
      method: 'get',
    })
  }


