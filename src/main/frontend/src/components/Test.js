import React, { Component } from 'react';
import axios from "axios";
import { GoogleMap, withScriptjs, withGoogleMap, Marker} from 'react-google-maps';


class Test extends Component {
    
    constructor(props){
        super(props);
        this.state = {
            planes:[]
        }
        this.loadData = this.loadData.bind(this);
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
    
    render() {
        
        const WrappeMap = withScriptjs(withGoogleMap((props) =>
            <GoogleMap defaultZoom={5} defaultCenter={{ lat: 40.483011, lng: -4.087557}}>
                {this.state.planes.map((plane) => (
                    <Marker key={plane.icao} position={{ lat: plane.latitude, lng: plane.longitude}}/>
                    )
                )}
            </GoogleMap>
          ))
        
        return (
            <div >
                <WrappeMap googleMapURL={`https://maps.googleapis.com/maps/api/js?key=AIzaSyCYtWGt6BW7bHK-u7emPPVsYWsoMUKODHI&v=3.exp&libraries=geometry,drawing,places`}
                loadingElement={<div style={{ height: `100%` }} />}
                containerElement={<div style={{ height: `400px` }} />}
                mapElement={<div style={{ height: `100%` }} />}
                />
            </div>
        );
      }
}

export default Test
