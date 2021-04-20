import React from "react";
import axios from "axios";
import styled from 'styled-components';
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

class DataPlaneComponent extends React.Component {

    constructor(props){
        super(props);
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
            var url = "http://localhost:8080/" + this.props.match.params.id;
            axios.get(url).then(response => {
                this.setState({ planes: response.data })
            });
        } catch (e) {
            console.log(e);
        }
    }     
            
    render(){       
        return(
            <div>
                <H0 className="text-center" > <b>{this.props.match.params.id}</b> plane </H0>
                
                <ReactMapGL latitude={39.983011} longitude={-4.087557} width="72.3vw" height="67vh" zoom={5} mapboxApiAccessToken="pk.eyJ1Ijoicml0YS1hbWFudGU5OTU1IiwiYSI6ImNrbmEyZGpzYzBqcjcybm55Z2NyOTVkazMifQ.oRw17OIsKSA0CeIUG2UC1Q">
                    {this.state.planes.map( plane => (
                        <Marker key={plane.icao} latitude={plane.latitude} longitude={plane.longitude}>
                            <img src="point.png" width="5" height="5"/>
                        </Marker>
                        )
                    )}
                </ReactMapGL>
                
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
                    <tbody> {this.state.planes.map( plane =>
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
                    )}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default DataPlaneComponent


