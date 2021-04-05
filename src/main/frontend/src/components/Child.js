import React from "react";
import ReactDOM from "react-dom";
import PlaneService from "../services/PlaneService";
import { Link, useLocation, BrowserRouter as Router } from "react-router-dom";

function User({ icao }) {
  return <div>ola {icao}</div>;
}

function useQuery() {
  return new URLSearchParams(useLocation().search);
}

class Child extends React.Component {
    
    constructor(props){
      super(props)
        this.state = {
          planes:[]
        }
    }

          
    render(){
      let query = useQuery();
      return(
        <div>
        
          <h3 className="text-center"> All planes </h3>
          <h2>oleeeeeee</h2>
          <User icao={query.get("id")} />
                  
        </div>
      )
    }
}

export default Child


