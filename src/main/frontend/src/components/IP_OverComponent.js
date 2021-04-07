import React from "react";
import ReactDOM from "react-dom";
//import {Button} from 'react-bootstrap';
//import ButtonToolbar from 'react-bootstrap/Button';
import {ButtonToolbar} from 'react-bootstrap';
import {ModalComponent} from './ModalComponent';
import axios from "axios";
import { BrowserRouter as Router, Switch, Route, Link, useRouteMatch } from "react-router-dom";
import styled from 'styled-components';
import Alert from 'react-bootstrap/Alert';

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
          planes:[],
          addModalShow: false,
          idSelectedPlane: "RITA"
        }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
//      setInterval(this.loadData, 10000);
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
      //console.log("Button clicked = " + planeIdentifier);
      //window.open(`/child?id=${planeIdentifier}`, "_blank");
      
      window.open(`/id=${planeIdentifier}`,"_blank");
      //window.alert(planeIdentifier);
    }
      
            
    render(){       
      let addModalClose = () => this.setState({addModalShow:false});
      const {dados} = this.state;
      
        const verd = "RITA";
        const verm = "AMANTE";
      return(
        <div>
            <H0 className="text-center" > All planes <b style={{ color: '#0097a7'}}>over</b> the <b>Irebian Peninsula</b> </H0>
            
        <AlertaVerde brand={verd} />
        <AlertaVermelho brand={verm} />
        
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
              <tbody>
                {
                  this.state.planes.map(
                    plane =>
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
                        <button variant ="danger" onClick={() => this.buttonOnClick(plane.icao)}>
                            View
                        </button>
                       
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
                <Alert.Heading>ALERT !</Alert.Heading>
                <hr />
                <p className="mb-0"> Acabou de <b>sair</b> na Península Ibérica o avião <b> {this.props.brand} </b>. </p>  
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
                <Alert.Heading>ALERT !</Alert.Heading>
                <hr />
                <p className="mb-0"> Acabou de <b>sair</b> na Península Ibérica o avião <b> {this.props.brand} </b>. </p>  
            </Alert>
        </div>

    );
  }
}

export default IP_OverComponent


