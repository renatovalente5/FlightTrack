import React, { Component, useState } from "react";
import ReactDOM from "react-dom";
import {ButtonToolbar} from 'react-bootstrap';
import axios from "axios";
import { BrowserRouter as Router, Switch, Route, Link, useRouteMatch } from "react-router-dom";
import styled from 'styled-components';
import Alert from 'react-bootstrap/Alert';
//import { GoogleMap, withScriptjs, withGoogleMap, Marker} from 'react-google-maps';
import ReactMapGL, {Marker} from 'react-map-gl';

const H0 = styled.h1({
    fontSize: 25,
    paddingBottom: 30,
    paddingTop: 20,
    color: 'black',
    textAlign: "center"
});

const H1 = styled.h1({
    paddingBottom: 30,
    fontSize: 12,
    paddingTop: 0,
    textAlign: "center"
});

class MapComponent extends React.Component {

    constructor(props){
      super(props);
        this.state = {
          planes:[]
        }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
      setInterval(this.loadData, 3000);
    }

    async loadData() {
        try {
            axios.get("http://localhost:8081/map").then(response => {
                this.setState({ planes: response.data })
            });
        } catch (e) {
            console.log(e);
        }
    }
    
    render(){                       
        return(
            <div >
                <ReactMapGL latitude={39.983011} longitude={-4.087557} width="72.3vw" height="67vh" zoom={5} mapboxApiAccessToken="pk.eyJ1Ijoicml0YS1hbWFudGU5OTU1IiwiYSI6ImNrbmEyZGpzYzBqcjcybm55Z2NyOTVkazMifQ.oRw17OIsKSA0CeIUG2UC1Q">
                    {this.state.planes.map( plane => (
                        <Marker key={plane.icao} latitude={plane.latitude} longitude={plane.longitude}>
                            <img src="travelling.png" width="15" height="15"/>
                        </Marker>
                        )
                    )}
                </ReactMapGL>
                                
            </div>
      );
    }
}


export default MapComponent