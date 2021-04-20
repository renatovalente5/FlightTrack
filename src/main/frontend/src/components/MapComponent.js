import React, { Component, useState } from "react";
import axios from "axios";
import styled from 'styled-components';
import ReactMapGL, {Marker} from 'react-map-gl';
import {NotificationContainer, NotificationManager} from 'react-notifications';

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
          planes:[],
          message:"",
          aviao:""
        }
      this.loadData = this.loadData.bind(this);
      this.loadMessages = this.loadMessages.bind(this);
    }

    componentDidMount(){
      this.loadData();
      this.loadMessages();
      setInterval(this.loadData, 1000);
      setInterval(this.loadMessages, 1000);

    }

    async loadMessages() {
        try {
            axios.get("http://localhost:8080/msg2").then(response => {
                this.setState({ message: response.data })
            });
        } catch (e) {
            console.log(e);
        }
        if (this.state.aviao != this.state.message && this.state.message != ""){
            NotificationManager.info('',this.state.message ,4000);
            this.state.aviao = this.state.message;
        }
    }

    async loadData() {
        try {
            axios.get("http://localhost:8080/map").then(response => {
                this.setState({ planes: response.data })
            });
        } catch (e) {
            console.log(e);
        }
    }

    render(){
        return(
            <div >
                <NotificationContainer/>

                <ReactMapGL latitude={39.983011} longitude={-4.087557} width="72.3vw" height="67vh" zoom={4.9} mapboxApiAccessToken="pk.eyJ1Ijoicml0YS1hbWFudGU5OTU1IiwiYSI6ImNrbmEyZGpzYzBqcjcybm55Z2NyOTVkazMifQ.oRw17OIsKSA0CeIUG2UC1Q">
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