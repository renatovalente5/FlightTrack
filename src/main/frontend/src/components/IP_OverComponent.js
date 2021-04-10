<<<<<<< HEAD
import React, { Component, useState, SyntheticEvent  } from "react";
=======
import React from "react";
>>>>>>> parent of 7e48cda... Add map section
import ReactDOM from "react-dom";
import {ButtonToolbar} from 'react-bootstrap';
import {ModalComponent} from './ModalComponent';
import axios from "axios";
import { BrowserRouter as Router, Switch, Route, Link, useRouteMatch } from "react-router-dom";
import styled from 'styled-components';
import Alert from 'react-bootstrap/Alert';
import { GoogleMap, withScriptjs, withGoogleMap, Marker} from 'react-google-maps';

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

class IP_OverComponent extends React.Component {

    constructor(props){
      super(props);
        this.state = {
<<<<<<< HEAD
          planes:[], 
          search: '', 
          searchPlane: []
=======
          planes:[],
          addModalShow: false
>>>>>>> parent of 7e48cda... Add map section
        }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
      setInterval(this.loadData, 10000);
    }

    async loadData() {
        try {
            axios.get("http://localhost:8081/over").then(response => {
                this.setState({ planes: response.data })
            });
        } catch (e) {
            console.log(e);
        }
    }
    
    buttonOnClick(planeIdentifier) {
      window.open(`/${planeIdentifier}`,"_blank");
    }
<<<<<<< HEAD
    
    mySubmitHandler = (event) => {
        event.preventDefault();
        const p = this.state.search;
        
        if (p === "") {
            console.log("OLA");
        
        } else {
            console.log("OLEEE " + p);
            
        }    
    }
    
    myChangeHandler = (event) => {
        this.setState({search: event.target.value});
    }
  
=======
      
>>>>>>> parent of 7e48cda... Add map section
    render(){       
        let addModalClose = () => this.setState({addModalShow:false});

        const WrappeMap = withScriptjs(withGoogleMap((props) =>
            <GoogleMap defaultZoom={7} defaultCenter={{ lat: 40.483011, lng: -4.087557}}>
                {this.state.planes.map((plane) => (
                    <Marker key={plane.icao} position={{ lat: plane.latitude, lng: plane.longitude}}/>
                    )
                )}
            </GoogleMap>
            ))
        
        const verd = "RITA";
        const verm = "AMANTE";
        
        return(
            <div>
                <H0 className="text-center" > All planes <b>over</b> the <b>Irebian Peninsula</b> </H0>
<<<<<<< HEAD
                
                <form onSubmit={this.mySubmitHandler}>
                <p> Search flights by "Callsign": {' '}
                    <input type='text' onChange={this.myChangeHandler} />
                    <input type='submit' />
                </p>                    
                </form>
                
                <input id="myInput" class="prompt" type="text" placeholder="Procurar..."/>
                <i class="search icon"></i>
                 
=======
            
>>>>>>> parent of 7e48cda... Add map section
                <AlertaVerde brand={verd} />
                <AlertaVermelho brand={verm} />
                
                <WrappeMap googleMapURL={`https://maps.googleapis.com/maps/api/js?key=AIzaSyCYtWGt6BW7bHK-u7emPPVsYWsoMUKODHI&v=3.exp&libraries=geometry,drawing,places`}
                            loadingElement={<div style={{ height: `100%` }} />}
                            containerElement={<div style={{ height: `400px` }} />}
                            mapElement={<div style={{ height: `100%` }} />} />
                
                <table className = "table table-striped">
                    <thead>
                        <tr>
                            <td>Icao24</td>
                            <td>Callsign</td>
                            <td>Origin Country</td>
                            <td>Time position</td>
                            <td>Longitude</td>
                            <td>Latitude</td>
                            <td>Altitude</td>
                            <td>Velocity</td>
                            <td>View</td>
                        </tr>
                    </thead>
                    <tbody id="myTable"> { this.state.planes.map( plane =>
                        <tr key = {plane.icao}>
                            <td>{plane.icao}</td>
                            <td>{plane.callsign}</td>
                            <td>{plane.origin_country}</td>
                            <td>{plane.time_position}</td>
                            <td>{plane.longitude}</td>
                            <td>{plane.latitude}</td>
                            <td>{plane.baro_altitude}</td>
                            <td>{plane.velocity}</td>
                            <td> 
                                <ButtonToolbar>
                                    <button variant ="danger" onClick={() => this.buttonOnClick(plane.icao)}> View </button>
                                </ButtonToolbar> 
                            </td>
                        </tr>
                    )
                }
              </tbody>
          </table>
        </div>
      );
    }
}

class AlertaVerde extends React.Component {
  render() {
    return (
        <div>
            <Alert variant="success">
                <Alert.Heading style={{ fontSize: 20}}><b>ALERT</b>!</Alert.Heading>
                <p>The <b>{this.props.brand}</b> plane just entered the Iberian Peninsula.</p>   
            </Alert>
        </div>

    );
  }
}

class AlertaVermelho extends React.Component {
  render() {
    return (
        <div>
            <Alert variant="danger">
                <Alert.Heading style={{ fontSize: 20}}><b>ALERT</b>!</Alert.Heading>
                <p>The <b>{this.props.brand}</b> plane just left the Iberian Peninsula.</p> 
            </Alert>
        </div>

    );
  }
}

<<<<<<< HEAD

export default IP_OverComponent
=======
export default IP_OverComponent


>>>>>>> parent of 7e48cda... Add map section
