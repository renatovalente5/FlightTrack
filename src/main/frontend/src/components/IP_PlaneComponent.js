import React from "react";
import axios from "axios";
import {ButtonGroup, Button, Dropdown} from 'react-bootstrap';
import { BrowserRouter as Router, Switch, Route, Link, useRouteMatch } from "react-router-dom";
import styled from 'styled-components';

const H0 = styled.h1({
    fontSize: 25,
    paddingBottom: 10,
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


class IP_PlaneComponent extends React.Component {

    constructor(props){
      super(props)
        this.state = {
          planes:[]
        }
      this.loadData = this.loadData.bind(this);
    }

    componentDidMount(){
      this.loadData();
      setInterval(this.loadData, 10000);
    }

    async loadData() {
        try {
            axios.get("http://localhost:8081/ip").then(response => {
                this.setState({ planes: response.data })
            });
        } catch (e) {
            console.log(e);
        }
    }
      
    render(){
        return(
            <div>
                <H0 className="text-center" > All planes with <b style={{ color: '#0097a7'}}>origin</b> in the <b>Irebian Peninsula</b> </H0>

                <H1 style={{textAlign: 'center', paddingBottom:'30'}}>
                    <Link to={"/ip"} style={{ color: '#0097a7'}}> All planes with <b>origin</b> in the Irebian Peninsula</Link>{' • '}
                    <Link to={"/over"} style={{ color: '#0097a7'}}> All planes <b>over</b> Irebian Peninsula </Link>
                </H1>
                
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
                    </tr>
                    </thead>
                    <tbody>
                        { this.state.planes.map( plane =>
                        <tr key = {plane.icao}>
                            <td>{plane.icao}</td>
                            <td>{plane.callsign}</td>
                            <td>{plane.origin_country}</td>
                            <td>{plane.time_position}</td>
                            <td>{plane.longitude}</td>
                            <td>{plane.latitude}</td>
                            <td>{plane.baro_altitude}</td>
                            <td>{plane.velocity}</td>
                        </tr>
                        ) }
                    </tbody>
                </table>
            </div>
        )
    }
}

export default IP_PlaneComponent


