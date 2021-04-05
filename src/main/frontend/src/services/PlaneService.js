import axios from "axios";

//const PLANES_REST_API_URL = "http://localhost:8081/planes";
//const PLANES_IP_API_URL = "http://localhost:8081/planes/ip";
//const PLANES_IP_PORTUGAL_API_URL = "http://localhost:8081/planes/ip/portugal";
//const PLANES_IP_SPAIN_API_URL = "http://localhost:8081/planes/ip/spain";
//const PLANES_IP_OVER_API_URL = "http://localhost:8081/planes/ip/over";
//const PLANES_IP_ENTRY_API_URL = "http://localhost:8081/planes/ip/entry";

const PLANES_REST_API_URL = "http://localhost:8081/planes";
const PLANES_IP_API_URL = "http://localhost:8081/ip";
const PLANES_IP_PORTUGAL_API_URL = "http://localhost:8081/portugal";
const PLANES_IP_SPAIN_API_URL = "http://localhost:8081/spain";
const PLANES_IP_OVER_API_URL = "http://localhost:8081/over";
const PLANES_IP_ENTRY_API_URL = "http://localhost:8081/entry";
const PLANES_REST_API_URL_TEST = "http://localhost:8081/test";
//const PLANES_IP_CHILD_API_URL = "http://localhost:8081/child";

class PlaneService {

    getPlanes(){
        return axios.get(PLANES_REST_API_URL);
    }
    
    getTest(){
        return axios.get(PLANES_REST_API_URL_TEST);
    }
    
    getPlanesIp(){
        return axios.get(PLANES_IP_API_URL);
    }
    
    getPlanesIpPortugal(){
        return axios.get(PLANES_IP_PORTUGAL_API_URL);
    }
    
    getPlanesIpSpain(){
        return axios.get(PLANES_IP_SPAIN_API_URL);
    }
    
    getPlanesIpOver(){
        return axios.get(PLANES_IP_OVER_API_URL);
    }
    
    getPlanesIpEntry(){
        return axios.get(PLANES_IP_ENTRY_API_URL);
    }
    
//    getChild(){
//        return axios.get(PLANES_IP_CHILD_API_URL);
//    }

}

export default new PlaneService();
